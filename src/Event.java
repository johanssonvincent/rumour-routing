/**
 * @author  Vincent Johansson
 * @since   2022-06-24
 */

import java.util.Stack;

/**
 * Event class.
 * Generates an event and saves the information of the event
 * to an EventDetails class object which will be used to send
 * the information about the event to other nodes.
 */
public class Event implements Cloneable{

    /* Variable declarations. */
    private int eventID;
    private int timeOfEvent;
    private int stepsToEvent;
    private Position position;
    private Stack<Node> pathToEvent;

    /**
     * Class constructor.
     *
     * @param id    The ID for the event.
     * @param time  Time event occurred.
     * @param origin Node where event occurred.
     */
    public Event(int id, int time, Node origin){
        this.eventID = id;
        this.timeOfEvent = time;
        this.position = origin.getPosition();
        this.stepsToEvent = 0;
        pathToEvent = new Stack<>();
    }

    /**
     * Method that returns the eventID.
     *
     * @return  int eventID
     */
    public int getEventID(){
        return eventID;
    }

    /**
     * Method that returns the amount of steps
     * to the origin of the event.
     *
     * @return  int Amount of steps to event origin.
     */
    public int getStepsToEvent(){
        return stepsToEvent;
    }

    /**
     * Method to increase distance to event.
     */
    public void addStepsToEvent(){
        stepsToEvent++;
    }

    /**
     * Method that adds a node to the route back to the event.
     * @param node  node to be added to the route
     */
    public void addToRoute(Node node){
        pathToEvent.push(node);
    }

    /**
     * Method that returns stack with path to event.
     * @return pathToEvent stack containing the path to the event
     */
    public Stack<Node> getPathToEvent(){
        return pathToEvent;
    }

    /**
     * Method that returns the latest node added in pathToEvent stack.
     * @return  returns the head of pathToEvent stack
     */
    public Node nextStep(){
        return pathToEvent.pop();
    }
    /**
     * Method that returns the position where the event occurred.
     *
     * @return  Position    The position of node where event occurred.
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Method that returns the time the event occurred.
     *
     * @return  int Time event occurred.
     */
    public int getTime(){
        return this.timeOfEvent;
    }

    /**
     * Method to clone an event.
     *
     * @return  Event object    Copy of the event.
     * @throws java.lang.CloneNotSupportedException error of clone isn't supported
     */
    @Override
    public Object clone() throws java.lang.CloneNotSupportedException{
        return super.clone();
    }
}
