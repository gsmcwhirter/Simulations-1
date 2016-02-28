/**
 * An abstract class representing a terrain object (part of the map)
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public abstract class Terrain implements Block
{
    /**
     *  Tells the terrain to become passable or impassable
     *  @param yes if true, becomes passable, if false, impassable
     *  @return true if the terrain passability was set, false otherwise
     */
    abstract boolean setPassable(boolean yes);
    
    /**
     *  Tells the terrain to attempt to randomly mutate
     *  (each terrain can choose whether to actually change and how)
     *  @return true if the terrain mutated, false otherwise
     */
    abstract boolean randomMutation();
    
    private World world;
    private Cell location;
    private int id;
    
    private static int nextId = 1;
    
    /**
     *  Create a new terrain in the world
     *  @param w The world
     */
    public Terrain(World w)
    {
        world = w;
        id = nextId;
        nextId++;
    }
    
    /**
     *  Determines if two terrains are equal by comparing their internal ID numbers
     *  @param other The object (hopefully Terrain) to compare to
     *  @return true if the object equals the current Terrain, false otherwise
     */
    public boolean equals(Object other)
    {
        if (!Terrain.class.isInstance(other))
        {
            return false;
        }
        
        return this.id == ((Terrain)other).id;
    }
    
    /**
     *  Returns a string representation of the actor
     *  @return the string representation
     */
    public String toString()
    {
        return "(terrain id "+id+")";
    }
    
    /**
     *  Gets the world the current terrain lives in
     *  @return the world of the terrain
     */
    public World getWorld()
    {
        return world;
    }
    
    /**
     *  Gets the location of the current terrain in the world
     *  @return the terrain's location
     */
    @Override //implements Block interface
    public Cell getLocation()
    {
        return location;
    }
    
    /**
     *  Sets the location of the terrain
     *  Checks to see if other terrains are already present.
     *  @param loc the location to set the terrain to
     *  @return true if the terrain was set to the new location, false otherwise
     */
    @Override //implements Block interface
    public boolean setLocation(Cell loc)
    {
        if (world.getTerrain(loc).size() > 0)
        {
            return false;
        }
        
        location = loc;
        return true;
    }
}
