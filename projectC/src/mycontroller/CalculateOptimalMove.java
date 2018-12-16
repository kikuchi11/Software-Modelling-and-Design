/**
 * Group 63: Software modeling design project
 */

package mycontroller;

import tiles.HealthTrap;
import utilities.Coordinate;

import java.util.ArrayList;

/**
 * Class for determining the most efficient move so that all keys are collected
 * and an escape from the map is success
 * Find keys if keys not enough keys
 * Go to exit if all the keys are found
 * If no keys found, drive around and explore the map
 * Collect health if it goes below threshold amount needed for an escape
 */
public class CalculateOptimalMove implements Strategy {
	
	public CalculateOptimalMove() {};

	@Override
    public Coordinate nextMove(Map map, int noOfKeysCollected, int totalKeys,
    		Coordinate carCoordinets, float health) {

        // finding the lowest score for tiles for individual targets(ie. trap, 
		// edge, health, keys, exits)
		// first analyzes the damages and then prioritizes the distance 
		// Null keys if no targets 
        Coordinate targetKey = lowestScore(map, map.getKeyTileCoordinates());
        Coordinate targetEdge = lowestScore(map, map.getEdgeTileCoordinates());
        Coordinate targetHealth = lowestScore(map, map.getTheHealthTileCoordinates());
        Coordinate targetExit  = lowestScore(map, map.getTheExitTileCoordinates());

        Coordinate target = null;

        // Remaining keys to be set as target. 
        // If all keys found, set exit as the target
        if (noOfKeysCollected < totalKeys) {
            target = targetKey;
        }
        else{
            target = targetExit;
        }
       
        // If no reachable target, explore the edges and other areas
        if (target == null) {
            	target = targetEdge;
        }    
        // Print negative if there is nothing left to explore
        if(target == null) {
            System.out.println("No place or tile to explore!!");
            System.exit(0);
        }
        // Gather as much health needed as possible to get another set of keys
        // and return back and aim for exit
        // additional health collected which is equivalent to survive 5*lava 
        if (map.getTile(carCoordinets) instanceof HealthTrap && health != 100) {
        	if(health < (2 * map.getTheDamage(target) + map.getTheDamage(targetHealth) + 5*UpdateMap.DAMAGE_LAVA)){
            	return carCoordinets;
        	}
        }

        // Prioritize health if health is less than the threshold. Keep the car at current health tile
        if (targetHealth != null && health < (2 * map.getTheDamage(target) + map.getTheDamage(targetHealth) + 20)) {
        	target = targetHealth;
        }
        return map.findThePath(target, carCoordinets);
    }

    /**
     * Lowest score of Map tile found
     * @param map containing list of coordinates to follow
     * @param Coordinates containing health, traps, exit etc.
     * @return Coordinates of tile with lowest score
     */
    public static Coordinate lowestScore(Map map, ArrayList<Coordinate> coordinates){

        int tempoDamage = Integer.MAX_VALUE;
        int tempoDistance = Integer.MAX_VALUE;
        Coordinate nextCoord = null;

        // damage to distance ratio calculation
        for (Coordinate coord : coordinates) {

            MapCoordinate tile = map.getData(coord);

            if ((tile.getDamage() < tempoDamage) ||
                    (tile.getDamage() == tempoDamage && tempoDamage < Integer.MAX_VALUE &&
                            tile.getDistance() < tempoDistance)) {

                tempoDamage = tile.getDamage();
                tempoDistance = tile.getDistance();
                nextCoord = coord;
            }
        }

        return nextCoord;
    }
}
