import javafx.scene.paint.Color;
/**
 * Interface for a thing that could be on the grid
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public interface Block
{
    /**
     *  Determines whether objects can pass through the block
     *  @return true if objects can pass through, false otherwise
     */
    boolean isPassable();
    
    /**
     *  Determines the current location of the block
     *  @return the Cell location of the block
     */
    Cell getLocation();
    
    /**
     *  Sets the current location of the block
     *  @return true if the location was changed, false otherwise
     */
    boolean setLocation(Cell loc);
    
    /**
     *  Gets the color of the current block
     *  @return the color of the block (javafx.scene.paint.Color)
     */
    Color getColor();
}
