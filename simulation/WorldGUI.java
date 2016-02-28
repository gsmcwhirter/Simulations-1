import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

/**
 * The simulation GUI
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public class WorldGUI extends Application
{
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;
    
    private static Class<? extends World> worldClass = World.class;

    private World world;
    private GUIRectangle[][] rectangles;
    
    /**
     *  Sets the class for the simulation world. The init method creates an instance.
     *  @param wc The Class of the world object to use
     */
    public static void setWorldClass(Class<? extends World> wc)
    {
        worldClass = wc;
    }

    /**
     *  Initializes the application state by creating an instance of the world class
     */
    @Override
    public void init()
    {
        try {
            world = worldClass.newInstance();
            world.init();
        }
        catch (Exception e)
        {
            System.out.println("Unable to instantiate "+worldClass);
            e.printStackTrace();
            Platform.exit();
        }
    }

    /**
     *  Starts the application. Called by javafx internally.
     *  @param primaryStage The primary stage to use for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Plants, Zombies, and Humans");
        
        Group root = new Group();
        
        int[] mapSize = world.getSize();
        rectangles = new GUIRectangle[mapSize[0]][mapSize[1]];
        int rwidth = WINDOW_WIDTH / mapSize[0];
        int rheight = WINDOW_HEIGHT / mapSize[1];
        
        for (int i = 0; i < mapSize[0]; i++)
        {
            for (int j = 0; j < mapSize[1]; j++)
            {
                rectangles[i][j] = new GUIRectangle();
                rectangles[i][j].setWidth(rwidth);
                rectangles[i][j].setHeight(rheight);
                rectangles[i][j].setX(i*rwidth);
                rectangles[i][j].setY(j*rheight);
                root.getChildren().add(rectangles[i][j]);
            }
        }
        
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLACK);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(WorldGUI.class.getResource("GUI.css").toExternalForm());
        primaryStage.show();
        
        tick();
        
    }
    
    /**
     *  This method takes care of scheduling steps of the simulation to run in the future.
     */
    public void tick()
    {
        WorldGUI gui = this;
        
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                if (world.act(gui))
                {
                    gui.tick();
                }
                else
                {
                    System.out.println("Done.");
                }
            }
        });
    }
    
    /**
     *  Informs the GUI that the world display is ready to be updated and updates the display
     */
    public void update()
    {
        for (GUIRectangle[] row : rectangles)
        {
            for (GUIRectangle rect : row)
            {
                rect.resetEntities();
            }
        }
        
        for (Terrain b : world.getTerrain())
        {
            rectangles[b.getLocation().getX()][b.getLocation().getY()].setTerrainColor(b.getColor());
        }
        
        for (Actor a : world.getActors())
        {
            rectangles[a.getLocation().getX()][a.getLocation().getY()].addEntity(a.getColor());
        }
        
        for (GUIRectangle[] row : rectangles)
        {
            for (GUIRectangle rect : row)
            {
                rect.update();
            }
        }
    }
}
