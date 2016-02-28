
/**
 * Runs the simulation
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public class SimulationDriver
{
    public static void main(String[] args)
    {
        WorldGUI.setWorldClass(MyWorld.class);
        WorldGUI.launch(WorldGUI.class, args);
    }
}
