/**
 * @Author        : Adrian Boman
 * @date          : 2022-05
 * @Version       : v1.0
 * @Cs name       : Et20abn
 * @Description   :
 */


import java.util.Objects;

/**
 * Class for position, including x and y.
 */
public class Position {

    private int x;
    private int y;

    /**
     * Class constructor.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method that returns x-coordinate
     * @return  x
     */
    public int getX() {
        return x;
    }

    /**
     * Method that returns y-coordinate
     * @return  y
     */
    public int getY() {
        return y;
    }

    /**
     * Method that returns the position south of current position.
     * @return  south neighbour position
     */
    public Position getPosToSouth() {
        if(y+10<=490) {
            return new Position(x, y + 10);
        }else{
            return new Position(x, y);
        }
    }

    /**
     * Method that returns the position north of current position.
     * @return  north neighbour position
     */
    public Position getPosToNorth() {
        if(y-10>=0){
            return new Position(x, y - 10);
        }else{
            return new Position(x, y);
        }

    }

    /**
     * Method that returns the position west of current position.
     * @return  West neighbour position
     */
    public Position getPosToWest() {
        if(x-10>=0){
            return new Position(x - 10, y);
        }else{
            return new Position(x, y);
        }
    }

    /**
     * Method that returns the position east of current position.
     * @return  east neighbour position
     */
    public Position getPosToEast() {
        if(x+10<=490){
            return new Position(x + 10, y);
        }else{
            return new Position(x, y);
        }
    }

    /**
     * Method that returns the position south-west of current position.
     * @return  south-west neighbour position
     */
    public Position getPosToSouthWest() {
        if(y+10<=490 && x-10>=0) {
            return new Position(x-10, y + 10);
        }else{
            return new Position(x, y);
        }
    }

    /**
     * Method that returns the position south-east of current position.
     * @return  south-east neighbour position
     */
    public Position getPosToSouthEast() {
        if(y+10<=490 && x+10<=490) {
            return new Position(x + 10, y + 10);
        }else{
            return new Position(x, y);
        }
    }

    /**
     * Method that returns the position north-west of current position.
     * @return  north-west neighbour position
     */
    public Position getPosToNorthWest() {
        if(y-10>=0 && x-10>=0) {
            return new Position(x-10, y - 10);
        }else{
            return new Position(x, y);
        }
    }

    /**
     * Method that returns the position north-east of current position.
     * @return  north-east neighbour position
     */
    public Position getPosToNorthEast() {
        if(y-10>=0 && x+10<=490) {
            return new Position(x+10, y - 10);
        }else{
            return new Position(x, y);
        }
    }

    /**
     * Method that returns true if two objects are equal.
     * @param o Object to be compared to
     * @return  true/false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    /**
     * Method that returns the hashcode of an object.
     * @return  Hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}