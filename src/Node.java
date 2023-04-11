/**
 * @Author        : Adrian Boman, Erik Simonsson
 * @date          : 2022-05
 * @Version       : v1.0
 * @Cs name       : Et20abn, Id16esn
 * @Description   :
 *
 *
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Node {

    private Position position;
    public ArrayList<Node> listOfNeighbours = new ArrayList<Node>();
    private HashMap<Integer,Event> eventTold = new HashMap<Integer, Event>();
    private boolean requestNode = false;
    private boolean busy = false;
    private Queue<Object> queuedMessages = new LinkedList<Object>();

    /**
     * Class constructor
     * @param position  position for created node
     */
    public Node (Position position){
        this.position = position;
    }

    /**
     * Method that checks if node is able to receive
     * a message, if it's busy save message to a queue.
     * @param message   message being received
     * @return  true/false
     */
    public boolean isMovable(Message message) {
        if(!busy && queuedMessages.isEmpty()) {
            return true;
        }
        else if(!busy && queuedMessages.peek() != null && queuedMessages.peek().equals(message)) {
            queuedMessages.remove();
            return true;
        }
        else {
            if(!queuedMessages.contains(message)) {
                queuedMessages.add(message);
            }
            return false;
        }
    }

    /**
     * Method to set busy status of a node.
     * @param status    true/false
     */
    public void setBusy(boolean status){
        busy = status;
    }

    /**
     * Method that returns the position of a node.
     * @return  position
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Method that adds a given event to a node' event HashMap
     * @param eventInfo event that is to be added
     */
    public void addEvent(Event eventInfo){
        eventTold.put(eventInfo.getEventID(),eventInfo);
    }

    /**
     * Method that checks if eventTold HashMap contains a specified event
     * @param eventID   EventID to look for in HashMap
     * @return  true/false depending on if HashMap contains event or not
     */
    public boolean searchEvent(int eventID) {
        return eventTold.containsKey(eventID);
    }

    /**
     * Method that creates a new request to be sent
     * @param timeToLive    How many time-steps request is supposed to live
     * @param eventID       ID of event to look for
     * @param node          Node the request is being sent from
     * @param extended      true/false depending on if the request is an extension of previous request
     * @param time          time-step request is being created and sent
     * @return              Request object
     */
    public Request sendRequest(int timeToLive, int eventID, Node node, boolean extended, int time){
        return new Request(timeToLive, node, eventID, extended, time);
    }

    /**
     * Method that returns true if a node is a request node
     * otherwise return false
     * @return  true/false
     */
    public boolean getRequestNode(){
        return requestNode;
    }

    /**
     * Method that sets a node to be a request node
     */
    public void setRequestNode(){
        requestNode = true;
    }

    /**
     * Method that returns the getEventTold Hashmap
     * @return  eventTold
     */
    public HashMap<Integer,Event> getEventTold(){
        return this.eventTold;
    }

    /**
     * Method that returns a nodes list of neighbours.
     * @return  listOfNeighbours
     */
    public ArrayList<Node> getNeighbours(){
        return this.listOfNeighbours;
    }
}
