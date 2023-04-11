//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class NodeTest {
//
//    @Test
//    Event eventStart(int i, int i1, Position position) {
//        Node node = new Node(new Position(2,9));
//        node.addEvent(eventStart(123,10,new Position(2,9)));
//        assertFalse(node.getEventTold().isEmpty());
//
//
//        return null;
//    }
//
//    @Test
//    void isBusy() {
//        Node node = new Node(new Position(2,9));
//        assertFalse(node.isBusy());
//    }
//
//    @Test
//    void setBusy() {
//        Node node = new Node(new Position(2,9));
//        node.setBusy(true);
//        assertTrue(node.isBusy());
//    }
//
//    @Test
//    void getPosition() {
//        Node node = new Node(new Position(2,9));
//        Position position = new Position(2,9);
//        assertEquals(position,node.getPosition());
//    }
//
////    @Test
////    void addEvent() {
////        Node node = new Node(new Position(2,9));
////        Event event = new Event(123,10,new Position(2,9));
////        node.addEvent(event);
////        assertTrue(node.getEventTold().size()>0);
//    }
//
////    @Test
////    void eventDetect() {
////        Node node = new Node(new Position(2,9));
////        Event event = new Event(123,10,new Position(2,9));
////        node.addEvent(event);
////        assertTrue(node.eventDetect(123));
////    }
//
////    @Test
////    void getRequestNode() {
////        Node node = new Node(new Position(2,9));
////        assertFalse(node.getRequestNode());
////    }
////
////    @Test
////    void setRequestNode() {
////        Node node = new Node(new Position(2,9));
////        node.setRequestNode();
////        assertTrue(node.getRequestNode());
////
////    }
////
////    @Test
////    void getEventTold() {
////        Node node = new Node(new Position(2,9));
////        assertTrue(node.getEventTold().isEmpty());
////    }
//
//}