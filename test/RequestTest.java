import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Stack;

public class RequestTest {

    @Test
    public void searchInNetwork(){

        int queryEventId = 3;
        Node newNode = null;
        Node currentNode = new Node(new Position(2,5));
        Event eventOne = new Event(1, 5, currentNode);
        Event event = new Event(3,6, currentNode);
        currentNode.addEvent(event);


        if (currentNode.getEventTold().containsKey(queryEventId)) {

                if (currentNode.getEventTold().containsKey(queryEventId)) {

                    newNode = currentNode;

                }
            }


        assertNotNull(newNode);

    }

    @Test
    public void sendAnswer() throws CloneNotSupportedException {

        int queryEventId = 3;

        Event eventFound = null;
        Node newNode;
        Node currentNode = new Node(new Position(2,5));
        Event eventOne = new Event(1, 5, currentNode);
        Event event = new Event(3,6, currentNode);
        currentNode.addEvent(event);


        if (currentNode.getEventTold().containsKey(queryEventId)) {

            if (currentNode.getEventTold().containsKey(queryEventId)) {

                newNode = currentNode;

                if (newNode.getEventTold().get(queryEventId).getStepsToEvent() == 0) {

                    eventFound = (Event) newNode.getEventTold().get(queryEventId).clone();

                }
            }
        }
        assertNotNull(eventFound);
    }


    @Test
    public void getRoutes() {

        int queryEventId = 3;
        Stack<Node> routes = new Stack<>();
        Node currentNode = new Node(new Position(2, 5));
        Event eventOne = new Event(1, 5, currentNode);
        currentNode.addEvent(eventOne);
        Event event = new Event(3, 6, currentNode);
        currentNode.addEvent(event);


        if (currentNode.getEventTold().containsKey(queryEventId)) {

            if (currentNode.getEventTold().containsKey(queryEventId) && currentNode.getEventTold().get(queryEventId).getStepsToEvent() == 0) {

                routes.push(currentNode);

            }
        }
        assertNotNull(routes);

    }

}
