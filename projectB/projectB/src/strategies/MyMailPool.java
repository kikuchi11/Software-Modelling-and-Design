package strategies;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.function.Consumer;

import automail.MailItem;
import automail.PriorityMailItem;
import automail.Robot;
import automail.StorageTube;
import exceptions.TubeFullException;
import exceptions.FragileItemBrokenException;

public class MyMailPool implements IMailPool {

	private class Item {
		int priority;
		int destination;
		boolean heavy;
		boolean fragility;
		MailItem mailItem;
		// Use stable sort to keep arrival time relative positions
		
		public Item(MailItem mailItem) {
			priority = (mailItem instanceof PriorityMailItem) ? ((PriorityMailItem) mailItem).getPriorityLevel() : 1;
			heavy = mailItem.getWeight() >= 2000;
			destination = mailItem.getDestFloor();
			fragility = mailItem.getFragile();
			this.mailItem = mailItem;
		}
	}
	
	public class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2) {
			int order = 0;
			// Put at the end of the list if it's fragile.
			if (i1.priority < i2.priority) {
				order = 1;
			} else if (i1.priority > i2.priority) {
				order = -1;
			} else if (i1.destination < i2.destination) {
				order = 1;
			} else if (i1.destination > i2.destination) {
				order = -1;
			}
			return order;
		}
	}
	
	private LinkedList<Item> pool;
	private static final int MAX_TAKE = 4;
	private static final int MAX_TAKE_CAREFUL = 3;
	private static final int MAX_TAKE_BIG = 6;
	private LinkedList<Robot> robots;
	private int lightCount;

	public MyMailPool(){
		// Start empty
		pool = new LinkedList<Item>();
		lightCount = 0;
		robots = new LinkedList<Robot>();
	}

	public void addToPool(MailItem mailItem) {
		Item item = new Item(mailItem);
		pool.add(item);
		if (!item.heavy) lightCount++;
		pool.sort(new ItemComparator());
	}
	
	@Override
	public void step() throws FragileItemBrokenException {
		for (Robot robot: (Iterable<Robot>) robots::iterator) { fillStorageTube(robot); }
	}
	
	private void fillStorageTube(Robot robot) throws FragileItemBrokenException {
		StorageTube tube = robot.getTube();
		StorageTube temp = new StorageTube(robot.isBig(),robot.isCareful());
		try { // Get as many items as available or as fit
				if (robot.isCareful()) {
					while(temp.getSize() < MAX_TAKE_CAREFUL && !pool.isEmpty()) {
						if (pool.getFirst().fragility == true && temp.hasFragileItem() == true) {
							break;
						}
						else {
							Item item = pool.remove();
							if (!item.heavy) lightCount--;
							temp.addItem(item.mailItem, robot);
						}
					}
				}
				else if (robot.isBig()) {
					while(temp.getSize() < MAX_TAKE_BIG && !pool.isEmpty() && pool.getFirst().fragility == false) {
						Item item = pool.remove();
						if (!item.heavy) lightCount--;
						temp.addItem(item.mailItem, robot);
					}
				}
				else if (robot.isStrong()) {
					while(temp.getSize() < MAX_TAKE && !pool.isEmpty() && pool.getFirst().fragility == false) {
						Item item = pool.remove();
						if (!item.heavy) lightCount--;
						temp.addItem(item.mailItem, robot);
					}
				} else {
					ListIterator<Item> i = pool.listIterator();
					while(temp.getSize() < MAX_TAKE && lightCount > 0 && pool.getFirst().fragility == false) {
						Item item = i.next();
						if (!item.heavy) {
							temp.addItem(item.mailItem, robot);
							i.remove();
							lightCount--;
						}
					}
				}
				if (temp.getSize() > 0) {
					while (!temp.isEmpty()) tube.addItem(temp.pop(), robot);
					robot.dispatch();
				}
		}
		catch(TubeFullException e){
			e.printStackTrace();
		}
	}

	@Override
	public void registerWaiting(Robot robot) { // assumes won't be there
		if (robot.isStrong()) {
			robots.add(robot); 
		} else {
			robots.addLast(robot); // weak robot last as want more efficient delivery with highest priorities
		}
	}

	@Override
	public void deregisterWaiting(Robot robot) {
		robots.remove(robot);
	}

}
