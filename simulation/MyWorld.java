import java.util.ArrayList;

/**
 * A particular world to simulate behavior in
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    
    /**
     *  The maximum number of actors that can be added to the world
     *  This cap exists for performance reasons
     */
    private static final int MAX_ACTORS = 8000;
    
    /** 
     *  Initializes the world state with fixed default population sizes
     */
    @Override //implement abstract
    public void init()
    {
        init(DEFAULT_ZOMBIES, DEFAULT_HUMANS, DEFAULT_ZOMBIES);
    }
    
    /** 
     *  Initializes the world state with given populations
     *  after generating the terrain.
     *  
     *  @param zombies the number of zombies
     *  @param humans the number of humans
     *  @param plants the number of plants
     */
    public void init(int zombies, int humans, int plants)
    {
        
    }
    
    /**
     *  Adds a plant at loc, if allowed
     *  @param loc the location to add the plant
     *  @return true if the plant was added, false otherwise
     */
    @Override //implement abstract
    public boolean addPlant(Cell loc)
    {
        if (getActors().size() > MAX_ACTORS) return false;
        if (getActors(loc, Plant.class).size() > 0) return false;
        Plant z = new Plant(this);
        if (z.setLocation(loc))
        {
            getMap().add(z);
            getGrowables().add(z);
            getActors().add(z);
            return true;
        }
        return false;
    }
    
    /**
     *  Gets the size of the current map
     *  @return a 2-entry array; the first element is the x-size and the second the y-size
     */
    @Override //normal override
    public int[] getSize()
    {
        return MAP_SIZE;
    }
    
    /**
     *  Gets the maximum number of steps to run the simulation
     *  @return the number of steps to run before stopping
     */
    @Override //normal override
    public int getMaxSteps()
    {
        return MAX_STEPS;
    }
    
    /**
     *  Determines if there is a passable path between two cells
     *  
     *  Precondition: from and to are either horizontal, vertical, or exactly diagonal
     *                  from one another
     *  
     *  @param from the starting cell
     *  @param to the ending cell
     *  @return true if there is a passable path, false otherwise
     */
    @Override //implement abstract
    public boolean isPassable(Cell from, Cell to)
    {   
        // assumes |dx| == |dy|, or dx == 0, or dy == 0
        
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        int dxsgn = (int)Math.signum(dx);
        int dysgn = (int)Math.signum(dy);
        
        if (dx == 0 && dy == 0)
        {
            return true;
        }
        else
        {
            for (int d = 0; d < Math.max(dx, dy); d++)
            {
                if (!isPassable(calculateLocation(from.getX() + d * dxsgn, from.getY() + d * dysgn)))
                {
                    return false;
                }
            }
            
            return true;
        }
    }    
}
