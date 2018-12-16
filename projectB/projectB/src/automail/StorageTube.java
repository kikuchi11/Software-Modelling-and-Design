package automail;

import exceptions.FragileItemBrokenException;
import exceptions.TubeFullException;

import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube {
	public final int maximumCapacity;
    public final int MAXIMUM_CAPACITY = 4;
    public final int MAXIMUM_CAPACITY_CAREFUL = 3;
    public final int MAXIMUM_CAPACITY_BIG = 6;
    public Stack<MailItem> tube;
    private boolean fragileItem;

    /**
     * Constructor for the storage tube
     */
    public StorageTube(boolean big, boolean careful){
    	if(big && !careful) {
    		this.maximumCapacity= MAXIMUM_CAPACITY_BIG;
    	}
    	else if (!big && careful) {
    		this.maximumCapacity= MAXIMUM_CAPACITY_CAREFUL;
    	}
    	else this.maximumCapacity = MAXIMUM_CAPACITY;
        this.tube = new Stack<MailItem>();
        fragileItem = false;
    }

    /**
     * @return boolean value representing whether this object has a fragile item
     */
    public boolean hasFragileItem() {
    	return fragileItem;
    }
    
    /**
     * @param boolean value representing whether this object has a fragile item
     */
    public void updateFragileItem(boolean fragileItem) {
    	this.fragileItem = fragileItem;
    }
    
    /**
     * @return if the storage tube is full
     */
    public boolean isFull(){
        return tube.size() == maximumCapacity;
    }

    /**
     * @return if the storage tube is empty
     */
    public boolean isEmpty(){
        return tube.isEmpty();
    }
    
    /**
     * @return the first item in the storage tube (without removing it)
     */
    public MailItem peek() {
    	return tube.peek();
    }

    /**
     * Add an item to the tube
     * @param item The item being added
     * @param robot whose tubes The item being added to
     * @throws TubeFullException thrown if an item is added which exceeds the capacity
     */
    public void addItem(MailItem item, Robot robot) throws TubeFullException, FragileItemBrokenException {
        if(tube.size() < maximumCapacity){
        	if (tube.isEmpty()) {
        		tube.add(item);
        	} 
        	else if ((item.getFragile() || tube.peek().getFragile())&& !robot.isCareful() ) {
        		 throw new FragileItemBrokenException();
        	 } 
        	else {
        		tube.add(item);
        	}
        }
        else {
          throw new TubeFullException();
        }
    }

    /** @return the size of the tube **/
    public int getSize(){
    	return tube.size();
    }
    
    /** 
     * @return the first item in the storage tube (after removing it)
     */
    public MailItem pop(){
        return tube.pop();
    }

}
