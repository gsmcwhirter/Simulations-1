
/**
 * This class holds testing methods while we are
 * working on the simulation.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SimulationTester
{
    public static void main(String[] args)
    {
        System.out.println("Starting tests....");
        testTerrain();
        testPlant();
    }
    
    public static void testTerrain()
    {
        System.out.println("Starting terrain test...");
        Terrain t = new DirtTerrain(null);
        System.out.println(t);
        System.out.println("Passable? " + t.isPassable());
        System.out.println("Mutated? " + t.randomMutation());
        System.out.println("Passable now? " + t.isPassable());
        System.out.println("Set impassable? " + t.setPassable(false));
        System.out.println("Passable now? " + t.isPassable());
    }
    
    public static void testPlant()
    {
        System.out.println("Starting plant test...");
        Plant p = new Plant(null);
        System.out.println(p);
        Growable g = p;
        System.out.println(g);
        Edible ed = (Edible)g;
        System.out.println(ed);
        
        System.out.println("Dead? " + p.isDead());
        
        System.out.println("Size? " + g.getSize());
        System.out.println("Grew? " + g.grow());
        System.out.println("Size now? " + g.getSize());
        System.out.println("Shrunk? " + g.shrink());
        System.out.println("Size now? " + g.getSize());
        System.out.println("Shrunk? " + g.shrink());
        System.out.println("Size now? " + g.getSize());
        
        System.out.println("Eaten? " + ed.beEaten());
        System.out.println("Eaten again? " + ed.beEaten());
        
        System.out.println("Size now? " + g.getSize());
        System.out.println("Dead now? " + p.isDead());
        
        System.out.println("Nutrition? " + ed.getFoodAmount());
        
    }
}
