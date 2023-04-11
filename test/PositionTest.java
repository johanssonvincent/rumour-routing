import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    //Testing so getX works as intended
    @Test
    public void getXTest(){
        Position pos = new Position(2, 9);
        assertEquals(2, pos.getX());
    }

    //Testing so getY works as intended
    @Test
    public void getYTest(){
        Position pos = new Position(2, 9);
        assertEquals(9, pos.getY());
    }

    //Testing so getPosToNorth works as intended
    @Test
    public void testGetPosToNorth() {
        Position pos = new Position(2, 19);
        assertEquals(9,pos.getPosToNorth().getY());
    }

    //Testing so getPosToSouth works as intended
    @Test
    public void testGetPosToSouth() {
        Position pos = new Position(2, 19);
        assertEquals(29,pos.getPosToSouth().getY());
    }

    //Testing so getPosToEast works as intended
    @Test
    public void testGetPosToEast() {
        Position pos = new Position(12, 19);
        assertEquals(22,pos.getPosToEast().getX());
    }

    //Testing so getPosToWest works as intended
    @Test
    public void testGetPosToWest() {
        Position pos = new Position(12, 19);
        assertEquals(2,pos.getPosToWest().getX());
    }

    //Testing so equals works as intended
    @Test
    public void testEquals() {
        Position pos = new Position(2, 19);
        Position pos2 = new Position(2, 19);

        assertEquals(true,pos.equals(pos2));
    }
}