import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MessageTest {

    @Test
    void getVisitedNodes() {
        Message message = new Message(100) {
            @Override
            public void searchInNetwork() {

            }
        };
        Node node = new Node(new Position(2,9));
        message.addVisitedNodes(node);
        assertTrue(message.getVisitedNodes().size()>0);
    }

    @Test
    void updateTime() {
        Message message = new Message(100) {
            @Override
            public void searchInNetwork() {

            }
        };
        assertEquals(100, message.getTTL());
        message.updateTime();
        assertEquals(99,message.getTTL());
    }

    @Test
    void addVisitedNodes() {
    }

    @Test
    void timeIsEnd() {
    }

    @Test
    void searchInNetwork() {
    }

    @Test
    void getEvent() {
    }

    @Test
    void setEvent() {
    }

    @Test
    void messageIsAlive() {
    }
}
