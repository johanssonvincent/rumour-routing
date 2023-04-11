import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FieldTest {

    @Test
    public void testCreate() throws IOException {
        File testFile = new File("nodes.rtf");
        Scanner scanner = new Scanner(testFile);
        Field testField = new Field(scanner);
        assertNotNull(testField);
    }

    @Test
    public void testCycleNodes() throws IOException, CloneNotSupportedException{
        File testFile = new File("nodes.rtf");
        Scanner scanner = new Scanner(testFile);
        Field testField = new Field(scanner);
        assertTrue(testField.events.isEmpty());
        assertTrue(testField.agents.isEmpty());
        assertTrue(testField.requests.isEmpty());

        testField.cycleNodes(1);

        assertNotNull(testField.events);
        assertNotNull(testField.agents);
        assertNotNull(testField.requests);
    }
}
