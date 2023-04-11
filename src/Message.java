/*
 * Author        : Omar Ibrahim
 * date          : 2022-05-12
 * Version       : v1.0
 * Cs name       : hed21oil
 */

import java.util.*;

/**
 *  A super class which sends between nodes. It knows the lifetime for a message to
 *  reach the destination before it died. It has a relationship with Node class that
 *  it record all nodes that have been visited.
 */
public abstract class Message {

    private int TTL;
    private Node destinationNode;
    private Stack<Node> visitedNodes;



    /**
     * A constructor of Message class. Initiate class's fields.
     *
     * @param TTL Shows how many jumps a package is allowed "Time-to-Live" to
     *            be forwarded between different routers before it is removed
     *            from the network.
     */
    public Message(int TTL){

        this.TTL = TTL;
        this.visitedNodes = new Stack<>();

    }

    /**
     * A method that returns a list of visited nodes.
     * @return a list of visited nodes.
     */
    public Stack<Node> getVisitedNodes(){
        return this.visitedNodes;
    }

    /**
     * A method that update the time everytime methods is called.
     */
    public void updateTime(){
        TTL--;
    }

    /**
     * A method to add nodes in the list, the type parameter makes the field store
     * any type of variable.
     * @param recentNode to be stored in the list.
     */
    public void addVisitedNodes(Node recentNode){

        visitedNodes.add(recentNode);
    }

    /**
     * A method that check the time/or number of jumps to be forwarded between different
     * routers before it is removed from the network.
     * @return true if the time is not ended.
     */
    public boolean timeIsEnd(){
        return TTL == 0;
    }

    /**
     * An abstract method of Message class.
     * return position of a node.
     * @throws CloneNotSupportedException exception if it's not possible to clone an event
     */
    public abstract void searchInNetwork() throws CloneNotSupportedException;

    /**
     * Method that returns time-to-live
     * @return  int
     */
    public int getTTL(){
        return this.TTL;
    }

    /**
     * A method to return the node that detects the event.
     * @return the node that has the requested event
     */
    public Node getEvent() {
        return this.destinationNode;
    }

    /**
     * A method to set an event.
     * @param destinationNode the node that has the chosen event.
     */
    public void setEvent(Node destinationNode){

        this.destinationNode = destinationNode;
    }

}