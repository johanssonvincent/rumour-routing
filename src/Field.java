import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * @author : Erik Simonsson (id16esn), Vincent Johansson (dv14vjn)
 * @since : 2022-07-14
 * Note: Class Field is meant to control the environment of the simulation and keep track
 * of all nodes, events and messages that are generated and make sure that they do their job.
 */

public class Field {
    private int amountOfNodes;
    private int requestsFulfilled = 0;
    public ArrayList<Node> field = new ArrayList<>();
    public ArrayList<Event> events = new ArrayList<>();
    public ArrayList<Request> requests = new ArrayList<>();
    public ArrayList<Agent> agents = new ArrayList<>();

    /**
     * Method that initializes the field of nodes.
     * @param nodes node information
     */
    public Field(Scanner nodes) {
        //Read in the total amount of nodes and create an ArrayList of all nodes
        try {
            while (nodes.hasNextLine()) {
                String fileLine = nodes.nextLine();
                if(fileLine.contains(",")) {
                    String[] coordinates = fileLine.split(",");
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    Position position = new Position(x, y);
                    Node node = new Node(position);
                    field.add(node);
                }
                else {
                    amountOfNodes = Integer.parseInt(fileLine);
                }
            }
            nodes.close();
        }
        catch(NumberFormatException e) {
            System.out.println("Integer contained unexpected characters");
        }
        //Assign all neighbouring nodes for all nodes
        for (Node node : field) {
            node.listOfNeighbours = assignNodeNeighbours(node);
        }
        assignRequestNodes();
    }

    /**
     * Method that assigns which nodes will be request nodes.
     */
    private void assignRequestNodes() {
        int amountRequestNodes = 0;
        Random randNode = new Random();
        while(amountRequestNodes < 4) {
            int randomized = randNode.nextInt(field.size());
            Node node = field.get(randomized);
            if(!node.getRequestNode()) {
                node.setRequestNode();
                amountRequestNodes++;
            }
        }
    }

    /**
     * Method that assigns neighbours to a node.
     * @param node  node where neighbours are to be assigned
     * @return  returns the ArrayList containing the nodes neighbours
     */
    private ArrayList<Node> assignNodeNeighbours(Node node) {
        ArrayList<Node> neighbours = new ArrayList<>();
        for (Node neighbourCompare : field) {
            if (neighbourCompare.getPosition().equals(node.getPosition().getPosToNorthWest())) {
                neighbours.add(neighbourCompare);
            }
            if (neighbourCompare.getPosition().equals(node.getPosition().getPosToNorth())) {
                neighbours.add(neighbourCompare);
            }
            if (neighbourCompare.getPosition().equals(node.getPosition().getPosToNorthEast())) {
                neighbours.add(neighbourCompare);
            }
            if (neighbourCompare.getPosition().equals(node.getPosition().getPosToWest())) {
                neighbours.add(neighbourCompare);
            }
            if (neighbourCompare.getPosition().equals(node.getPosition().getPosToEast())) {
                neighbours.add(neighbourCompare);
            }
            if (neighbourCompare.getPosition().equals(node.getPosition().getPosToSouthWest())) {
                neighbours.add(neighbourCompare);
            }
            if (neighbourCompare.getPosition().equals(node.getPosition().getPosToSouth())) {
                neighbours.add(neighbourCompare);
            }
            if (neighbourCompare.getPosition().equals(node.getPosition().getPosToSouthEast())) {
                neighbours.add(neighbourCompare);
            }
        }
        return neighbours;
    }

    /**
     * Method that cycles through the nodes each time-step
     * and initializes new events, agents and requests.
     * @param time  time-step
     * @throws CloneNotSupportedException   exception if it's not possible to clone an event
     */
    public void cycleNodes(int time) throws CloneNotSupportedException{
        for(int i = 0; i < amountOfNodes; i++) {
            boolean agentSent = false;
            Node node = field.get(i);
            Random randEvent = new Random();
            int randEventStartChance = randEvent.nextInt(10000);
            if(randEventStartChance == 0) {
                Random sendAgent = new Random();
                int randAgentSend = sendAgent.nextInt(2);
                //Add event to node
                Event newEvent = new Event(events.size(), time, node);
                events.add(newEvent);
                node.addEvent(newEvent);
                if (randAgentSend == 0) {
                    Agent agent = new Agent(newEvent, node);
                    agents.add(agent);
                }
                //Add event to neighbouring nodes
                Event sendEvent = (Event)newEvent.clone();
                sendEvent.addStepsToEvent();
                for(int n = 0; n < node.getNeighbours().size(); n++) {
                    if(!agentSent){
                        Node neighbourNode = node.getNeighbours().get(n);
                        neighbourNode.addEvent(newEvent);
                        int neighbourRandAgentSend = sendAgent.nextInt(2);
                        if (neighbourRandAgentSend == 0) {
                            Agent agent = new Agent(sendEvent, node);
                            agents.add(agent);
                            agentSent = true;
                        }
                    }else{
                        break;
                    }

                }
            }
            if(node.getRequestNode() && time % 400 == 0) {
                Random randRequest = new Random();
                int randomized = randRequest.nextInt(events.size());
                requests.add(node.sendRequest(45, events.get(randomized).getEventID(), node, false, time));
            }
        }
    }

    /**
     * Method that cycles through current requests to see if
     * any events has been found.
     * @param time  time-step
     * @throws CloneNotSupportedException   exception if it's not possible to clone an event
     */
    public void cycleRequests(int time) throws CloneNotSupportedException{
        for(int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            if(request.hasFoundEventInfo() && request.getIsHome()) {
                request.sendAnswer();
                requests.remove(request);
                requestsFulfilled++;
            }
            else if(!request.hasFoundEventInfo() && request.getIsHome()) {
                requests.remove(request);
            }
            else if(time - request.getTimeSentAt() >= 360 && !request.getExtendedStatus()) {
                requests.add(request.getHomeNode().sendRequest(45, request.getRequestEventID(),
                        request.getHomeNode(),true, time));
                requests.remove(request);
            }
            else {
                request.searchInNetwork();
            }
        }
    }

    /**
     * Method that cycles through current agents and
     * makes sure they move through the network, if an agents TTL has ended
     * removes it from the agents ArrayList and sets the last visited node's
     * busy status to false.
     * @throws CloneNotSupportedException   exception if it's not possible to clone an event
     */
    public void cyclesAgents() throws CloneNotSupportedException {
        for(int i = 0; i < agents.size(); i++) {
            Agent agent = agents.get(i);
            if(!agent.timeIsEnd()) {
                agent.searchInNetwork();
            }
            else {
                if(!agent.getVisited().isEmpty()) {
                    agent.getVisited().get(agent.getVisited().size()-1).setBusy(false);
                }
                agents.remove(agent);
            }
        }
    }

    /**
     * Method that prints out the results after
     * program finish running.
     */
    public void printResults() {
        System.out.println("Number of requests sent: 100\n" +
                "Number of events found: " + requestsFulfilled);
    }
}