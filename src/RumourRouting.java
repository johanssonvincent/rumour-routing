import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author : Erik Simonsson (id16esn)
 * @since 2022-05-25
 * Note: Class RumourRouting is the main class of the program. Keeps track of time.
 */

public class RumourRouting {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        File nodeFile = new File(args[0]);
        Scanner nodeScan = new Scanner(nodeFile);
        Field field = new Field(nodeScan);
        for(int time = 1; time <= 10000; time++) {
            field.cycleNodes(time);
            field.cycleRequests(time);
            field.cyclesAgents();
        }
        field.printResults();
    }
}
