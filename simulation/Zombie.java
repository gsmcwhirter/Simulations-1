import javafx.scene.paint.Color;
/**
 * Write a description of class Zombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombie extends Actor implements Movable, CanEat, Growable
{
    /**
     *  Upper limit on zombie speed
     */
    public static final int SPEED_LIMIT = 4;
    
    /**
     *  Number of turns without food before starving
     */
    private int timeToStarve = 10;
    
    /**
     *  Current food level
     */
    private int foodLevel = 5;
    
    /**
     *  How many turns starving already
     */
    private int starvingTurns = 0;
    
    /**
     *  Current size
     */
    private int size = 1;
    
    /**
     *  Creates a new zombie
     */
    public Zombie(World w)
    {
        super(w);
    }
    
    /**
     *  Returns a string representation of the zombie
     *  @return the string representation
     */
    @Override //normal
    public String toString()
    {
        return "Zombie "+super.toString();
    }
    
    /**
     *  Generates a new zombie at the current location
     */
    public void spawn()
    {
        getWorld().addZombie(getLocation());
    }
    
    /**
     *  Gets the speed of the zombie
     *  
     *  Larger zombies are slower
     *  
     *  @return the speed of the zombie [1,3]
     */
    @Override //implements Movable interface
    public int getSpeed()
    {
        return Math.max(SPEED_LIMIT-size, 1);
    }
    
    /**
     *  Commands the zombie to move once
     *  
     *  Tries to move to a random location within range
     *  
     *  @return true if the object has moved, false otherwise
     */
    @Override //implements Movable interface
    public boolean move()
    {
        Cell newLoc = getRandomLocationNearby(getSpeed(), true);
        
        if (getWorld().isPassable(getLocation(), newLoc))
        {
            setLocation(newLoc);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     *  Determines whether the zombie is starving or not
     *  
     *  @return true if the zombie is starving, false otherwise
     */
    @Override //implements CanEat interface
    public boolean isStarving()
    {
        return foodLevel <= 0;
    }
    
    /**
     *  Commands the zombie to eat once
     *  
     *  Asks the world to see if there is a human at the current location,
     *  and if there is, eats it
     *  
     *  If a human was eaten (for the first time for that human), spawns a new zombie
     *  
     *  @return true if the zombie ate, false otherwise
     */
    @Override //implements CanEat interface
    public boolean eat()
    {
        for (Actor a : getWorld().getActors(getLocation()))
        {
            if (a instanceof Human)
            {
                foodLevel += ((Human)a).getFoodAmount();
                if ( ((Human)a).beEaten() )
                {
                    spawn();
                }
                return true;
            }
        }
        
        return false;
    }
    
    /**
     *  Tells the zombie to shrink one step.
     *  
     *  Zombies cannot shrink past size 1
     *  
     *  @return true if the zombie shrunk, false otherwise
     */
    @Override //implements Growable interface
    public boolean shrink()
    {
        if (size > 1)
        {
            size--;
            return true;
        }
        
        return true;
    }
    
    /**
     *  Tells the zombie to grow one step.
     *   
     *  @return true if the object grew, false otherwise
     */
    @Override //implements Growable interface
    public boolean grow()
    {
        if (foodLevel > 1)
        {
            size++;
        }
        return true;
    }
    
    /**
     *  Gets the size of the zombie
     *  
     *  @return the size of the zombie
     */
    @Override //implements Growable interface
    public int getSize()
    {
        return size;
    }
    
    /**
     *  Gets the color of the current block
     *  @return the color of the block (javafx.scene.paint.Color)
     */
    @Override //implements Block interface
    public Color getColor()
    {
        return Color.RED;
    }
}
