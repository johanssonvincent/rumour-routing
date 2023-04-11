import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testCreate(){
        Node node = new Node(new Position(0,1));
        Event testEvent = new Event(1, 1, node);
        assertNotNull(testEvent);
    }

    @Test
    public void testGetEventID(){
        Node node = new Node(new Position(0, 1));
        Event testEvent = new Event(1, 1, node);
        assertEquals(1, testEvent.getEventID());
    }

    @Test
    public void testGetSteps(){
        int stepsTest = 0;
        Node node = new Node(new Position(0, 1));
        Event testEvent = new Event(1, 1, node);
        assertEquals(stepsTest, testEvent.getStepsToEvent());

        while(stepsTest != 5){
            stepsTest++;
            testEvent.addStepsToEvent();
        }

        assertEquals(stepsTest, testEvent.getStepsToEvent());
    }

    @Test
    public void testGetTime(){
        Node node = new Node(new Position(0, 1));
        Event testEvent = new Event(1, 1, node);
        assertEquals(1, testEvent.getTime());
    }

    @Test
    public void testClone() throws java.lang.CloneNotSupportedException{
        Event eventClone;
        Node node = new Node(new Position(0, 1));
        Event testEvent = new Event(1, 1, node);
        eventClone = (Event)testEvent.clone();

        assertEquals(testEvent.getEventID(), eventClone.getEventID());
    }
}
