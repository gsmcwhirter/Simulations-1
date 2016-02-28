
/**
 * An interface describing something that can be eaten
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public interface Edible
{
    /**
     *  Commands the object to be eaten once
     *  
     *  @return true if the object was eaten, false otherwise
     */
    boolean beEaten();
    
    /** 
     *  Returns the nutritional value of the food
     *  
     *  @return the nutritional value (usually 1)
     */
    int getFoodAmount();
}
