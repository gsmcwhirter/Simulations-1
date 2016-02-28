
/**
 * Describes things that are capable of growing
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public interface Growable
{
    /**
     *  Tells the object to grow one step.
     *   
     *  @return true if the object grew, false otherwise
     */
    boolean grow();
    
    /**
     *  Gets the size of the object
     *  
     *  @return the size of the object
     */
    int getSize();
    
    /**
     *  Tells the object to shrink one step.
     *  
     *  @return true if the object shrunk, false otherwise
     */
    boolean shrink();
}
