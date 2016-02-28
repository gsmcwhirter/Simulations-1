import javafx.scene.paint.Color;

/**
 * DirtTerrain is a standard, easy to move through terrain
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DirtTerrain extends Terrain
{  
    /**
     *  Represents the probability that the piece of terrain will randomly
     *  toggle between passable and impassable.
     */
    public static final double MUTATION_CHANCE = 0.001;
    
    /**
     *  Determines whether the terrain piece is currently passable
     */
    private boolean passable = true;
    
    /**
     *  Constructs a new DirtTerrain object
     */
    public DirtTerrain(World w)
    {
        super(w);
    }
    
    /**
     *  Tells the terrain to become passable or impassable
     *  @param yes if true, becomes passable, if false, impassable
     *  @return true if the terrain passability was set, false otherwise
     */
    @Override //implements abstract
    public boolean setPassable(boolean yes, boolean no)
    {
        passable = yes;
        return true;
    }
    
    /**
     *  Returns whether or not the terrain is passable
     *  @return true if the terrain is passable, false otherwise
     */
    @Override //implements Block interface
    public boolean isPassable()
    {
        return passable;
    }
    
    /**
     *  Returns the color associated with the current terrain
     *  @return the color of the terrain (javafx.scene.paint.Color)
     */
    @Override //implements Block interface
    public Color getColor()
    {
        if (isPassable())
            return Color.TAN;
        else
            return Color.BLACK;
    }
}
