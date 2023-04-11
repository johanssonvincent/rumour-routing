/**
 * @Author        : Omar Ibrahim / Vincent Johansson
 * @date          : 2022-06-24
 * @Version       : v1.0
 * @Cs name       : hed21oil / dv14vjn
 * @Description   : Any node may generate a query has a route to the event,forward the query
 *                 in a random direction, if TTL expires,the query reaches target event, if
 *                 a query does not reach Event will retransmit, give up or under most
 *                 circumstances the percent of undelivered queries is very low
 */

import java.util.*;

/**
 *  This class inherits from Message class, it knows which event to search for,
 *  it can see nodes contain information, it knows which way to go if event
 *  trail is found. it searches the route to event.
 */
public class Request extends Message {

    private int requestEventID;
    private Event eventFound;
    private Node currentNode;           // Can be a start node if it matches event's ID
    private Node nextNode;
    private Node homeNode;
    private int timeSentAt;
    private boolean eventInfoFound;
    private boolean destinationFound;
    private boolean isHome;
    private boolean isExtended;
    private Stack<Node> route;           // Will need when the request get the answer in order to return
                                       // in the same way that the request took it.

    int TTL;                           // Time expires

    /**
     *
     * @param TTL   time-to-live
     * @param currentNode   current node
     * @param requestEventID    event ID to look for
     * @param isExtended    extended status
     * @param timeSentAt    time request was sent
     */
    public Request(int TTL, Node currentNode,int requestEventID, boolean isExtended, int timeSentAt) {
        super(TTL);
        this.requestEventID = requestEventID;
        eventInfoFound = false;
        destinationFound = false;
        this.route = new Stack<>();
        this.route.push(currentNode);
        this.TTL = TTL;
        this.currentNode = currentNode;
        homeNode = currentNode;
        this.isExtended = isExtended;
        this.timeSentAt = timeSentAt;
    }

    /**
     * Method that implements the behaviour of request and sends
     * it around the network looking for the event.
     * @throws CloneNotSupportedException   exception if it's not possible to clone an event
     */
    @Override
    public void searchInNetwork() throws CloneNotSupportedException{
        if(!timeIsEnd() && !eventInfoFound) {
            updateTime();
            if (nextNode == null) {
                if (currentNode.searchEvent(requestEventID)) {
                    eventInfoFound = true;
                    eventFound = (Event)currentNode.getEventTold().get(requestEventID).clone();
                    if (eventFound.getPathToEvent().isEmpty()) {
                        destinationFound = true;
                    } else if (eventFound.getPathToEvent().peek().isMovable(this)) {
                        returnMove(eventFound.nextStep());
                    } else {
                        nextNode = eventFound.nextStep();
                        currentNode.setBusy(false);
                        currentNode = null;
                    }
                } else {
                    Random random = new Random();
                    int randomized = random.nextInt(currentNode.getNeighbours().size());
                    if(!currentNode.getNeighbours().get(randomized).isMovable(this)) {
                        move(currentNode.getNeighbours().get(randomized));
                    }
                    else {
                        nextNode = currentNode.getNeighbours().get(randomized);
                        currentNode.setBusy(false);
                        currentNode = null;
                    }
                }
            } else {
                if(nextNode.isMovable(this)) {
                    if(nextNode.searchEvent(requestEventID)){
                        eventInfoFound = true;
                        eventFound = (Event)nextNode.getEventTold().get(requestEventID).clone();
                    }
                    move(nextNode);
                    nextNode = null;
                }
            }
            // If info of event has been found follow the path to the event origin.
        }else if(eventInfoFound && !destinationFound && !timeIsEnd()){
            if(nextNode == null){
                if(!eventFound.getPathToEvent().isEmpty()){
                    if(eventFound.getPathToEvent().peek().isMovable(this)){
                        move(eventFound.nextStep());
                    }else{
                        nextNode = eventFound.nextStep();
                        currentNode.setBusy(false);
                        currentNode = null;
                    }
                }else if(currentNode.getPosition() == eventFound.getPosition()){
                    destinationFound = true;
                }
            }else{
                if(nextNode.isMovable(this)){
                    move(nextNode);
                    nextNode = null;
                }
            }
        }else if(timeIsEnd()){
            return;
        }else if(destinationFound){     // Return to home node after reaching the event node.
            if(nextNode == null) {
                if (!route.isEmpty()) {
                    if((route.peek()).isMovable(this)) {
                        returnMove(route.pop());
                    }
                    else {
                        nextNode = route.pop();
                        currentNode.setBusy(false);
                        currentNode = null;
                    }
                }
                else{
                    isHome = true;
                }
            }else {
                if(nextNode.isMovable(this)) {
                    returnMove(nextNode);
                    nextNode = null;
                }
            }
        }
    }

    /**
     * Method that moves the request to a new node.
     * @param node  node to move to
     */
    private void move(Node node) {
        if(currentNode != null) {
            currentNode.setBusy(false);
        }
        currentNode = node;
        if(getTTL() >= 1) {
            currentNode.setBusy(true);
        }
        route.push(currentNode);
    }

    /**
     * Alternative move method used when info about requested event has been found.
     * Does not save the path like the regular move method.
     * @param node  node to move to
     */
    private void returnMove(Node node){
        if(currentNode != null){
            currentNode.setBusy(false);
        }
        currentNode = node;
        currentNode.setBusy(true);
    }

    /**
     * A method that prints information about the event if found.
     */
    public void sendAnswer() {
        System.out.printf("Event origin identified at x: %d and y: %d. Event started at time %d, with id %d\n",
                eventFound.getPosition().getX(),
                eventFound.getPosition().getY(),
                eventFound.getTime(),
                requestEventID);
    }

    /**
     * A method to check if the request has reached the event.
     * @return  true if the event is found.
     */
    public boolean hasFoundEventInfo(){
        return eventInfoFound;
    }

    /**
     * Method to check if request has been resent after not returning.
     * @return  isExtended  true/false
     */
    public boolean getExtendedStatus() {
        return isExtended;
    }

    /**
     * Method that returns the ID of the event the request is looking for.
     * @return  requestEventID
     */
    public int getRequestEventID() {
        return requestEventID;
    }

    /**
     * Method that returns the node where the request was sent from.
     * @return  homeNode
     */
    public Node getHomeNode() {
        return homeNode;
    }

    /**
     * Method that returns true if the request is at the home node.
     * @return  isHome  boolean
     */
    public boolean getIsHome() {
        return isHome;
    }

    /**
     * Method that returns at what time the request was sent.
     * @return  timeSentAt
     */
    public int getTimeSentAt() {
        return timeSentAt;
    }
}