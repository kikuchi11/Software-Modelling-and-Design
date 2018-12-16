/**
 * Group 63: Software modeling design project
 */

package mycontroller;

import utilities.Coordinate;

/**
 * This interface is used to see the perfect way to escape the map by determining 
 * the best route.
 */
public interface Strategy {
	
	/**
	 * Determines the next coordinate to move to
	 * @param map Tile information
	 * @param nCollectedKeys Number of keys that have already been collected
	 * @param totalKeys Total number is keys needed to escape the map
	 * @param carCoord Current coordinate of car
	 * @param health Current health of car
	 * @return The coordinate towards next move
	 */
	 public abstract Coordinate nextMove(Map map, int nCollectedKeys, int totalKeys, Coordinate carCoord, float health);
}
