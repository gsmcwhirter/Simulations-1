
/**
 * An interface describing something that can eat
 * 
 * @author Gregory McWhirter
 * @version 0.1
 */
public interface CanEat
{
    /**
     *  Commands the object to eat once
     *  
     *  @return true if the object ate, false otherwise
     */
    boolean eat();
    
    /**
     *  Determines whether the object is starving or not
     *  
     *  @return true if the object is starving, false otherwise
     */
    boolean isStarving();
}
