/**
 * @Author        : Omar Ibrahim / Erik Simonsson / Vincent Johansson
 * @Date          : 2022-06-23
 * @Version       : v1.0
 * @Cs name       : hed21oil / id16esn / dv14vjn
 * @Description   : when a node witnesses an event it adds it to its event table,set a distance of zero
 *                 and generate an agent packet. Agent packet is a long-lived packet, contain an event
 *                 table, Travels the network for some number of hops (TTL) and then dies, Propagating
 *                 information about local events to distant nodes It informs every node that it visits
 *                 of any events in its route
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * An extension of the Message class. Agent carries information about an event
 * and updates the information in nodes it visits about that event.
 * It also updates its own information as it passes through nodes with information
 * about the event.
 */
public class Agent extends Message{

    private ArrayList<Node> visitedNodes = new ArrayList<>();
    private HashMap<Integer,Event> agentEvent = new HashMap<>();
    private Event event;
    private Node currentNode;
    private Node nextNode;

    /**
     * Class constructor.
     * @param event Event that is being sent
     * @param currentNode   Node where event occurred
     * @throws CloneNotSupportedException   exception if it's not possible to clone an event
     */
    public Agent(Event event, Node currentNode) throws CloneNotSupportedException{

        super(50);
        agentEvent.put(event.getEventID(), event);
        this.currentNode = currentNode;
        setEvent(this.currentNode);
        this.event = (Event)event.clone();
        nextNode = null;
    }

    /**
     * Method that performs the spreading of the agent through the network of nodes
     */
    @Override
    public void searchInNetwork() throws CloneNotSupportedException {
        if(!timeIsEnd()) {
            if(nextNode == null) {
                boolean unvisitedOption = false;
                for (Node neighbour : currentNode.getNeighbours()) {
                    if (!visitedNodes.contains(neighbour)) {
                        unvisitedOption = true;
                        break;
                    }
                }
                boolean moved = false;
                while (!moved) {
                    Random random = new Random();
                    int randomized = random.nextInt(currentNode.getNeighbours().size());
                    if (unvisitedOption) {
                        if (!visitedNodes.contains(currentNode.getNeighbours().get(randomized))) {
                            if (currentNode.getNeighbours().get(randomized).isMovable(this)) {
                                move(currentNode.getNeighbours().get(randomized));
                            } else {
                                nextNode = currentNode.getNeighbours().get(randomized);
                                currentNode.setBusy(false);
                                currentNode = null;
                            }
                            moved = true;
                        }
                    } else {
                        if (currentNode.getNeighbours().get(randomized).isMovable(this)) {
                            move(currentNode.getNeighbours().get(randomized));
                        }
                        else {
                            nextNode = currentNode.getNeighbours().get(randomized);
                            currentNode.setBusy(false);
                            currentNode = null;
                        }
                        moved = true;
                    }
                }
            }
            else {
                if(nextNode.isMovable(this)) {
                    move(nextNode);
                    nextNode = null;
                }
            }
        }
    }

    /**
     * Method that moves the agent to the next node
     * @param node node to move to
     * @throws CloneNotSupportedException exception if it's not possible to clone an event
     */
    private void move(Node node) throws CloneNotSupportedException{
        if(currentNode != null) {
            currentNode.setBusy(false);
        }
        currentNode = node;
        if(getTTL() > 1) {
            currentNode.setBusy(true);
        }
        visitedNodes.add(currentNode);
        this.event.addToRoute(node);
        updateTime();
        updateNodeTable(currentNode);
        updateAgentTable(currentNode);
    }

    /**
     * A method to update the table of a node
     * @param   nextHub node agent moved to that is to be updated
     * @throws  CloneNotSupportedException  exception if it's not possible to clone an event
     */
    private void updateNodeTable(Node nextHub) throws CloneNotSupportedException {
        for(Event agentEvent : this.agentEvent.values()){
            if(nextHub.getEventTold().containsKey(agentEvent.getEventID())) {
                if(agentEvent.getStepsToEvent() < nextHub.getEventTold().get(agentEvent.getEventID()).getStepsToEvent()) {
                    nextHub.addEvent((Event)agentEvent.clone());
                }
            }else{
                nextHub.addEvent((Event)agentEvent.clone());
            }
        }
    }

    /**
     * A method to update the table of agent
     * @param   nextHub node to update from
     * @throws  CloneNotSupportedException  exception if it's not possible to clone an event
     */
    private void updateAgentTable(Node nextHub) throws CloneNotSupportedException{
        for (Event nodeEvent : nextHub.getEventTold().values()) {
            if(agentEvent.containsKey(nodeEvent.getEventID())) {
                if(nodeEvent.getStepsToEvent() < agentEvent.get(nodeEvent.getEventID()).getStepsToEvent()) {
                    agentEvent.put(nodeEvent.getEventID(), nodeEvent);
                }
                else {
                    Event tempEvent = (Event)agentEvent.get(nodeEvent.getEventID()).clone();
                    tempEvent.addStepsToEvent();
                    agentEvent.put(nodeEvent.getEventID(), tempEvent);
                }
            }
            else {
                agentEvent.put(nodeEvent.getEventID(), nodeEvent);
            }
        }
    }

    /**
     * Method that returns the ArrayList containing a list of
     * all the nodes the agent has visited.
     * @return  visitedNodes ArrayList
     */
    public ArrayList<Node> getVisited() {
        return visitedNodes;
    }
}