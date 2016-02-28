import javafx.scene.paint.Color;

/**
 * The class representing a Plant
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Plant extends Actor implements Growable, Edible
{
    
    
    /**
     *  Returns a string representation of the plant
     *  @return the string representation
     */
    @Override //normal override
    public String toString()
    {
        return "Plant "+super.toString();
    }
    
    /**
     *  Generates a new plant at a random, nearby location
     */
    public void spawn()
    {
        Cell newLoc = getRandomLocationNearby(spawnRange);
                                                   
        getWorld().addPlant(newLoc);
    }
    
    /**
     *  Gets the color of the current block
     *  @return the color of the block (javafx.scene.paint.Color)
     */
    @Override //implements Block interface
    public Color getColor()
    {
        return Color.GREEN;
    }
}
