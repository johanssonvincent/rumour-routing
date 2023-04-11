/**
 * @Author        : Omar Ibrahim
 * @date          : 2022-05-12
 * @Version       : v1.0
 * @Cs name       : hed21oil
 * @Description   : Test the Agent class
 */



import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;


/**
 * This class tests the Agent class.
 */
public class AgentTest {

    /**
     * A method to test Agent's " searchInNetwork() " method.
     */
    @Test
    public void searchInNetwork(){

        Node currentNode = new Node(new Position(2,5));
        Event eventOne = new Event(1, 5, currentNode);
        currentNode.addEvent(eventOne);
        Event event = new Event(3,6, currentNode);
        currentNode.addEvent(event);
        HashMap<Integer, Event> nodeEvents = currentNode.getEventTold();

        assertEquals(nodeEvents.containsKey(3), true);

    }

    @Test
    public void updateAgentTable(){

        Node currentNode = new Node(new Position(2,5));
        Event eventOne = new Event(1, 5, currentNode);
        currentNode.addEvent(eventOne);
        Event event = new Event(3,6, currentNode);
        currentNode.addEvent(event);
        HashMap<Integer, Event> nodeEvents = currentNode.getEventTold();
        HashMap<Integer,Event> agentEvent = new HashMap<>();
        agentEvent.put(event.getEventID(),event);
        assertEquals(agentEvent.get(event.getEventID()).getStepsToEvent(),nodeEvents.get(event.getEventID()).getStepsToEvent());

    }


}
