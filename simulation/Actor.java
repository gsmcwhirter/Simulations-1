import javafx.scene.paint.Color;
/**
 * Actors represent agents in the world that interact with each other
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public abstract class Actor implements Block
{   
    /**
     *  Handles the general life-cycle of the actor (eating food, etc.)
     */
    abstract void act();
    
    /**
     *  Determines whether or not the actor is considered dead
     *  @return true if the actor is dead, false otherwise
     */
    abstract boolean isDead();
    
    private Cell location;
    private World world;
    private int id;
    
    private static int nextId = 1;
    
    /**
     *  Create a new actor in the world
     *  @param w The world
     */
    public Actor(World w)
    {
        world = w;
        id = nextId;
        nextId++;
    }
    
    /**
     *  Determines if two actors are equal by comparing their internal ID numbers
     *  @param other The object (hopefully Actor) to compare to
     *  @return true if the object equals the current Actor, false otherwise
     */
    public boolean equals(Object other)
    {
        if (!(other instanceof Actor))
        {
            return false;
        }
        
        return this.id == ((Actor)other).id;
    }
    
    /**
     *  Returns a string representation of the actor
     *  @return the string representation
     */
    public String toString()
    {
        return "(actor id "+id+")";
    }
    
    /**
     *  Gets the world the current actor lives in
     *  @return the world of the actor
     */
    public World getWorld()
    {
        return world;
    }
    
    /**
     *  Gets the location of the current actor in the world
     *  @return the actor's location
     */
    @Override //implements Block interface
    public Cell getLocation()
    {
        return location;
    }
    
    /**
     *  Commands the actor to die. This removes references to the
     *  actor's location and the world it lives in.
     */
    public void die()
    {
        location = null;
        world = null;
    }
    
    /**
     *  Sets the location of the actor, if allowed.
     *  Checks to see if loc isPassable in the World.
     *  @param loc the location to set the actor to
     *  @return true if the actor was set to the new location, false otherwise
     */
    @Override //implements Block interface
    public boolean setLocation(Cell loc)
    {
        if (world.isPassable(loc))
        {
            location = loc;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     *  A method to implement the Block interface. 
     *  By default, actors can move through each other.
     *  Can be overridden by subclasses
     *  @return true (other actors can pass through this actor)
     */
    @Override //implements Block interface
    public boolean isPassable()
    {
        return true;
    }
    
    /**
     *  Picks a random Cell nearby, anywhere within range squares
     *  @param range the distance away from the current location to pick from
     *  @return a random location in range (maybe the same as the current one)
     */
    public Cell getRandomLocationNearby(int range)
    {
        return getRandomLocationNearby(range, false);
    }
    
    /**
     *  Picks a random Cell nearby, anywhere within range squares
     *  @param range the distance away from the current location to pick from
     *  @param forMovement indicates that the selection is for movement and will
     *                     therefore be in a straight line
     *  @return a random location in range (maybe the same as the current one)
     */
    public Cell getRandomLocationNearby(int range, boolean forMovement)
    {
        int dir, spd;
        if (forMovement)
        {
            dir = 1;
            spd = range;
        }
        else
        {
            dir = range;
            spd = 1;
        }
        int[] delta = pickDirection(dir);
        
        return getWorld().calculateLocation(delta[0] * spd + getLocation().getX(),
                                            delta[1] * spd + getLocation().getY());
    }
    
    /**
     *  A helper method to pick a random direction
     *  @param spread how far away to pick a direction
     *  @return a 2-element array of differences, each of 
     *          which has an int value in [-spread, spread]
     */
    public static int[] pickDirection(int spread)
    {
        int[] direction = {0, 0};
        
        direction[0] = (int)(Math.random() * (2* spread + 1)) - spread;
        direction[1] = (int)(Math.random() * (2* spread + 1)) - spread;
        
        return direction;
    }
}
