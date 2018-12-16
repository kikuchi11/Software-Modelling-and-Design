package strategies;

import automail.IMailDelivery;
import automail.Robot;

public class Automail {
	      
    public Robot[] robot;
    public IMailPool mailPool;
    
    public Automail(IMailPool mailPool, IMailDelivery delivery) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	this.mailPool = mailPool;
    	
        /** Initialize the RobotAction */
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building
    	boolean careful = true; // Can handle fragile items
    	boolean notCareful = false; // Cannot handle fragile items
    	boolean big = true; // Can handle any weight that arrives at the building, and it has more tubes
    	boolean small = false; // Robots other than the big robot
    	
    	/** Initialize robots */
    	robot = new Robot[3];
    	
    	/*
    	robot[0] = new Robot(delivery, mailPool, weak, notCareful, small);
    	robot[1] = new Robot(delivery, mailPool, strong, notCareful, small);
    	robot[2] = new Robot(delivery, mailPool, strong, notCareful, small);
    	*/
    	
    	robot[0] = new Robot(delivery, mailPool, strong, notCareful, big);
    	robot[1] = new Robot(delivery, mailPool, strong, notCareful, small);
    	robot[2] = new Robot(delivery, mailPool, strong, careful, small);
    	
    }
    
}
