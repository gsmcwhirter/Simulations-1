import javafx.scene.paint.Color;
/**
 * A class representing a Human in a world
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Human extends Actor implements Movable, CanEat, Edible
{
    
    
    /**
     *  Returns a string representation of the human
     *  @return the string representation
     */
    @Override //normal
    public String toString()
    {
        return "Human "+super.toString();
    }
    
    /**
     *  Generates a new human at the current location
     */
    public void spawn()
    {
        getWorld().addHuman(getLocation());
    }
    
    /**
     *  Gets the color of the current block
     *  @return the color of the block (javafx.scene.paint.Color)
     */
    @Override //implements Block interface
    public Color getColor()
    {
        return Color.BLUE;
    }
}
