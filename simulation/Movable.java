
/**
 * An interface describing something that can move
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public interface Movable
{
    /**
     *  Commands the object to move once
     *  
     *  @return true if the object has moved, false otherwise
     */
    boolean move();
    
    /**
     *  Gets the speed of the object
     *  
     *  @return the speed of the object
     */
    int getSpeed();
    
}
