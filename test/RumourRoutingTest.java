import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RumourRoutingTest {
    @Test
    public void testFieldInit() throws FileNotFoundException, CloneNotSupportedException {
        File nodeFile = new File("nodes.rtf");
        Scanner nodeScan = new Scanner(nodeFile);
        Field field = new Field(nodeScan);
        field.cycleNodes(1);
        Random randomize = new Random();
        for (int i = 0; i < 100; i++) {
            int randNode = randomize.nextInt(field.field.size());
            assertNotNull(field.field.get(randNode));
        }
    }

    @Test
    public void testEventSpread() throws IOException, CloneNotSupportedException {
        File nodeFile = new File("nodes.rtf");
        Scanner nodeScan = new Scanner(nodeFile);
        Field field = new Field(nodeScan);
        for (int time = 1; time <= 10000; time++) {
            field.cycleNodes(time);
            field.cycleRequests(time);
            field.cyclesAgents();
        }

        int averageEvents = 0;
        for (Node node : field.field) {
            averageEvents = averageEvents + node.getEventTold().size();
        }

        averageEvents = averageEvents / field.field.size();
        assertTrue(0 < averageEvents);
        System.out.printf("A single node sees %d events on average in one run.\n", averageEvents);
    }

    @Test
    public void testMovement() throws IOException, CloneNotSupportedException{
        File nodeFile = new File("nodes.rtf");
        Scanner nodeScan = new Scanner(nodeFile);
        Field field = new Field(nodeScan);
        Random randIndex = new Random();
        Node randNode = field.field.get(randIndex.nextInt(field.field.size()));

        Request testReq = new Request(45, randNode, 1, false, 1);

        while(!testReq.timeIsEnd()){
            testReq.searchInNetwork();
        }

        assertFalse(testReq.getIsHome());
        assertEquals(0, testReq.getTTL());
        System.out.printf("Request TTL: %d.\nIs request at home node? %b.\n",
                testReq.getTTL(), testReq.getIsHome());

        randNode = field.field.get(randIndex.nextInt(field.field.size()));
        Event testEvent = new Event(1, 1, randNode);
        Agent testAgent = new Agent(testEvent, randNode);

        while(!testAgent.timeIsEnd()){
            testAgent.searchInNetwork();
        }

        assertEquals(0, testAgent.getTTL());
        assertEquals(50, testAgent.getVisited().size());
        System.out.printf("Agent TTL: %d.\nAmount of nodes visited while spreading message: %d",
                testAgent.getTTL(), testAgent.getVisited().size());

        Set<Node> testSet = new HashSet<Node>(testAgent.getVisited());
        assertEquals(testSet.size(), testAgent.getVisited().size());

    }

    @Test
    public void testEventDetection() throws IOException, CloneNotSupportedException{
        File nodeFile = new File("small-nodes.rtf");
        Scanner nodeScan = new Scanner(nodeFile);
        Field field = new Field(nodeScan);
        Random randIndex = new Random();
        Node randNode = field.field.get(randIndex.nextInt(5));
        System.out.printf("Event start pos: %d, %d.\n",
                randNode.getPosition().getX(), randNode.getPosition().getY());

        Event testEvent = new Event(1, 1, randNode);
        Agent testAgent = new Agent(testEvent, randNode);

        while(!testAgent.timeIsEnd()){
            testAgent.searchInNetwork();
        }

        randNode = field.field.get(randIndex.nextInt(5));
        Request testReq = new Request(45, randNode, 1, false, 51);

        while(!testReq.timeIsEnd() && !testReq.hasFoundEventInfo()){
            testReq.searchInNetwork();
        }
        assertTrue(testReq.hasFoundEventInfo());

        while(!testReq.getIsHome()){
            testReq.searchInNetwork();
        }
        assertTrue(testReq.getIsHome());
        testReq.sendAnswer();
    }
}