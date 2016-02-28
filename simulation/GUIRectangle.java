import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * A custom Rectangle class that handles color-coding based on
 * its population.
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public class GUIRectangle extends Rectangle
{
    private Color terrainColor = Color.BLACK;
    private double rTotal = 0;
    private double gTotal = 0;
    private double bTotal = 0;
    private int count = 0;
    
    /**
     *  Creates a new rectangle. 
     *  The stroke is set to black
     *  The StrokeType is set to INSIDE
     *  The fill is set to black
     */
    public GUIRectangle()
    {
        super();
        
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.INSIDE);
        update();
    }
    
    /**
     *  Sets the basic color of the rectangle (terrain).
     *  This is overriden entirely if an entity is on the rectangle.
     *  @param c The color of the terrain
     */
    public void setTerrainColor(Color c)
    {
        terrainColor = c;
    }
    
    /**
     *  Resets the entity-specific information on the rectangle
     */
    public void resetEntities()
    {
        rTotal = 0;
        gTotal = 0;
        bTotal = 0;
        count = 0;
    }
    
    /**
     *  Adds an entity with a given color to the rectangle
     *  @param c The color of the entity
     */
    public void addEntity(Color c)
    {
        count++;
        rTotal += c.getRed();
        gTotal += c.getGreen();
        bTotal += c.getBlue();
        
        //System.out.print(count + " ");
        //System.out.print(rTotal + " ");
        //System.out.print(gTotal + " ");
        //System.out.println(bTotal + " ");
    }
    
    /**
     *  Updates the rectangle to have the fill color that is the average color of all
     *  entities currently on the rectangle (or the terrain color if no entities are on)
     */
    public void update()
    {
        if (count > 0)
        {
            double impassFactor = 1.0;
            if (terrainColor == Color.BLACK)
            {
                impassFactor = 0.5;
            }
            setFill(new Color(rTotal / count * impassFactor, 
                              gTotal / count * impassFactor, 
                              bTotal / count * impassFactor, 
                              1.0));
        }
        else
        {
            setFill(terrainColor);
        }
    }
}
