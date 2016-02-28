import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Handles the manipulation of the world overall
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public abstract class World
{   
    /** 
     *  Initializes the world state with fixed or random populations
     */
    abstract void init();
    
    /**
     *  Has all growables grow, if able
     */
    abstract void growAll();
    
    /**
     *  Has all movables move, if able
     */
    abstract void moveAll();
    
    /**
     *  Has all eaters eat, if able
     */
    abstract void eatAll();
    
    /**
     *  Has all actors act, if able
     */
    abstract void actAll();
    
    /**
     *  Adds a zombie at loc, if allowed
     *  @param loc the location to add the zombie
     *  @return true if the zombie was added, false otherwise
     */
    abstract boolean addZombie(Cell loc);
    
    /**
     *  Adds a human at loc, if allowed
     *  @param loc the location to add the human
     *  @return true if the human was added, false otherwise
     */
    abstract boolean addHuman(Cell loc);
    
    /**
     *  Adds a plant at loc, if allowed
     *  @param loc the location to add the plant
     *  @return true if the plant was added, false otherwise
     */
    abstract boolean addPlant(Cell loc);
    
    /**
     *  Determines if a cell is considered passable
     *  @param loc The cell to consider
     *  @return true if the cell is passable, false otherwise
     */
    abstract boolean isPassable(Cell loc);
    
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
    abstract boolean isPassable(Cell from, Cell to);
    
    private static final int MAX_STEPS = 0;
    private static final int[] MAP_SIZE = {10, 10};
    
    private List<Movable> movables = new ArrayList<Movable>();
    private List<Growable> growables = new ArrayList<Growable>();
    private List<CanEat> eaters = new ArrayList<CanEat>();
    private List<Actor> actors = new ArrayList<Actor>();
    private List<Block> blocks = new ArrayList<Block>();
    private List<Object> killStack = new ArrayList<Object>();
    
    private int currentStep = 0;
    
    /** Takes a step on the world timeline.
     * 
     *  1. Actors act
     *  2. Movers move
     *  3. Growers grow
     *  4. Eaters eat
     *  5. Dead things removed
     *  6. Terrain mutation chance
     * 
     *  @param gui The WorldGUI (to update after the step is done)
     *  @return true if another step should be taken
     *          false if no more steps should occur
     */
    public boolean act(WorldGUI gui)
    {
        currentStep++;
        System.out.println(this);
        actAll();
        moveAll();
        growAll();
        eatAll();
        killAll();
        
        for (Terrain t : getTerrain())
        {
            t.randomMutation();
        }
        
        gui.update();
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {}
        
        return currentStep <= getMaxSteps();
    }
    
    /** Schedules an object (Actor) to be killed at the end of the current step
     *  @param o The object (Actor) to be killed (removed)
     *  
     */
    public void kill(Object o)
    {
        killStack.add(o);
    }
    
    private void killAll()
    {
        Object o;
        for (int k = killStack.size() - 1; k >= 0; k--)
        {
            o = killStack.get(k);
            
            for (int i = 0; i < movables.size(); i++)
            {
                if (movables.get(i).equals(o))
                {
                    movables.remove(i);
                    i--;
                }
            }
            
            for (int i = 0; i < growables.size(); i++)
            {
                if (growables.get(i).equals(o))
                {
                    growables.remove(i);
                    i--;
                }
            }
            
            for (int i = 0; i < eaters.size(); i++)
            {
                if (eaters.get(i).equals(o))
                {
                    eaters.remove(i);
                    i--;
                }
            }
            
            for (int i = 0; i < actors.size(); i++)
            {
                if (actors.get(i).equals(o))
                {
                    actors.get(i).die();
                    actors.remove(i);
                    i--;
                }
            }
            
            for (int i = 0; i < blocks.size(); i++)
            {
                if (blocks.get(i).equals(o))
                {
                    blocks.remove(i);
                    i--;
                }
            }
            
            killStack.remove(k);
        }
    }
    
    /**
     *  Gets the list of CanEat objects in the world
     *  @return the list of CanEat objects in the world
     */
    public List<CanEat> getEaters()
    {
        return eaters;
    }
    
    /**
     *  Gets the list of Movable objects in the world
     *  @return the list of Movable objects in the world
     */
    public List<Movable> getMovables()
    {
        return movables;
    }
    
    /**
     *  Gets the list of Growable objects in the world
     *  @return the list of Growable objects in the world
     */
    public List<Growable> getGrowables()
    {
        return growables;
    }
    
    /**
     *  Gets the list of Actor objects in the world
     *  @return the list of Actor objects in the world
     */
    public List<Actor> getActors()
    {
        return actors;
    }
    
    /**
     *  Gets the list of Actor objects in the world at a specific Cell
     *  @param loc The Cell to look for
     *  @return the list of Actor objects in the world at loc
     */
    public List<Actor> getActors(Cell loc)
    {
        if (loc == null) System.out.println("null input");
        return actors.stream().filter(e -> e.getLocation().equals(loc)).collect(Collectors.toList());
    }
    
    /**
     *  Gets the list of Actor objects in the world at a specific Cell that are a specific Class
     *  @param loc The Cell to look for
     *  @param klass The Class of Actors to look for
     *  @return the list of Actor objects in the world at loc that are klass objects
     */
    public List<Actor> getActors(Cell loc, Class klass)
    {
        return getActors(loc).stream().filter(e -> klass.isInstance(e)).collect(Collectors.toList());
    }
    
    /**
     *  Gets the list of Block objects in the world
     *  @return the list of Actor objects in the world
     */
    public List<Block> getMap()
    {
        return blocks;
    }
    
    /**
     *  Gets the list of Terrain objects in the world
     *  @return the list of Terrain objects in the world
     */
    public List<Terrain> getTerrain()
    {
        return blocks.stream().filter(e -> e instanceof Terrain).map(e -> (Terrain)e).collect(Collectors.toList());
    }
    
    /**
     *  Gets the list of Terrain objects in the world at loc
     *  @param loc the location to check
     *  @return the list of Terrain objects in the world
     */
    public List<Terrain> getTerrain(Cell loc)
    {
        return getTerrain().stream().filter(e -> e.getLocation().equals(loc)).collect(Collectors.toList());
    }
    
    /**
     *  Gets the size of the current map
     *  @return a 2-entry array; the first element is the x-size and the second the y-size
     */
    public int[] getSize()
    {
        return MAP_SIZE;
    }
    
    /**
     *  Gets the maximum number of steps to run the simulation
     *  @return the number of steps to run before stopping
     */
    public int getMaxSteps()
    {
        return MAX_STEPS;
    }
    
    /**
     *  Gets a Cell given a targetted x and y value
     *  @param x the x-coordinate (can be negative or too big -- wraps around)
     *  @param y the y-coordinate (can be negative or too big -- wraps around)
     *  @return the Cell with the appropriate coordiantes
     */
    public Cell calculateLocation(int x, int y)
    {
        int[] sizes = getSize();
        while (x < 0) { x += sizes[0]; } //Because java's % is stupid with negative numbers
        x %= sizes[0];
        
        while (y < 0) { y += sizes[1]; } //Because java's % is stupid with negative numbers
        y %= sizes[1];
        
        return Cell.getOrCreateCell(x, y);
    }
    
    /**
     *  Adds a zombie at (x, y)
     *  
     *  Defers to addZombie(Cell loc) after generating the Cell
     *  
     *  @param x the x-coordinate (will auto-wrap)
     *  @param y the y-coordinate (will auto-wrap)
     *  @return true if the zombie was added, false otherwise
     */
    public boolean addZombie(int x, int y)
    {
        return addZombie(calculateLocation(x, y));
    }
    
    /**
     *  Adds a human at (x, y)
     *  
     *  Defers to addHuman(Cell loc) after generating the Cell
     *  
     *  @param x the x-coordinate (will auto-wrap)
     *  @param y the y-coordinate (will auto-wrap)
     *  @return true if the human was added, false otherwise
     */
    public boolean addHuman(int x, int y)
    {
        return addHuman(calculateLocation(x, y));
    }
    
    /**
     *  Adds a plant at (x, y)
     *  
     *  Defers to addPlant(Cell loc) after generating the Cell
     *  
     *  @param x the x-coordinate (will auto-wrap)
     *  @param y the y-coordinate (will auto-wrap)
     *  @return true if the plant was added, false otherwise
     */
    public boolean addPlant(int x, int y)
    {
        return addPlant(calculateLocation(x, y));
    }
    
    /** 
     *  Gets a string representation of the world.
     *  @return the string representation of the world
     */
    public String toString()
    {
        return "World with M " + movables.size() + 
                        ", G " + growables.size() + 
                        ", E " + eaters.size() + 
                        ", A " + actors.size() + 
                        ", B " + blocks.size() + 
                        ", K " + killStack.size() +
                        " at step " + currentStep;
    }
    
    /**
     *  Returns a map whose keys are the names of the classes in the world
     *  and whose values are the number of entities of that class.
     *  @return the map
     */
    public Map<String, Long> populationInformation()
    {
        return blocks.stream()
                     .map(e -> e.getClass().toString())
                     .collect(Collectors.groupingBy(String::toString, Collectors.counting()));
    }
    
    /**
     *  Prints out information about the population of the world
     *  (see populationInformation)
     */
    public void printPopulations()
    {
        for (Map.Entry<String, Long> entry : populationInformation().entrySet())
        {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
