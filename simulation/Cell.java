import java.util.HashMap;
import java.util.Map;

/**
 * Represents a cell on the simulation board.
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public class Cell
{
    private int x;
    private int y;
    
    private static Map<Integer, Map<Integer, Cell>> cells = new HashMap<Integer, Map<Integer, Cell>>();
    
    /**
     *  Generates a new cell and caches the result
     *  @param x the x-coordinate of the cell
     *  @param y the y-coordinate of the cell
     *  @return the Cell object
     */
    public static Cell getOrCreateCell(Integer x, Integer y)
    {
        if (!cells.containsKey(x))
        {
            cells.put(x, new HashMap<Integer, Cell>());
            cells.get(x).put(y, new Cell(x, y));
        }
        else if (!cells.get(x).containsKey(y)) 
        {
            cells.get(x).put(y, new Cell(x, y));
        }
        
        return cells.get(x).get(y);
    }

    /**
     *  Creates a new cell
     */
    private Cell(int _x, int _y)
    {
        // initialise instance variables
        x = _x;
        y = _y;
    }

    /**
     *  Gets the x-coordinate of the cell
     */
    public int getX()
    {
        return x;
    }
    
    /**
     *  Gets the y-coordinate of the cell
     */
    public int getY()
    {
        return y;
    }
    
    /**
     *  Compares an object to this cell to see if they are equal
     *  Defers to equals(Cell other)
     *  @param other The other object
     *  @return true if the other is a Cell with the same coordinates, false otherwise
     */
    public boolean equals(Object other)
    {
        try
        {
            return equals((Cell)other);
        }
        catch (ClassCastException e)
        {
            return false;
        }
    }
    
    /**
     *  Compares the cell coordinates to see if they are identical
     *  @param other The other cell
     *  @return true of the other cell has the same coordinates, false otherwise
     */
    public boolean equals(Cell other)
    {
        return other.x == x && other.y == y;
    }
    
    /**
     *  Returns a string representation of the cell.
     *  @return the string representation of the cell
     */
    public String toString()
    {
        return "("+x+","+y+")";
    }
}
