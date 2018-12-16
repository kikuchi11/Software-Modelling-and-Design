/**
 * Group 63: Software modeling design project
 */

package mycontroller;

import tiles.*;
import utilities.Coordinate;
import world.WorldSpatial;
import java.util.ArrayList;
import java.util.HashMap;
import static mycontroller.MyAIController.*;

/**
 * This Class handles all the updates on the map and retrieves all the required information
 */
public class UpdateMap {

	public static final int DAMAGE_LAVA = 5;
	public static final int GRID_SIZE_VIEW = 9;

	/**
	 * Checks for possible edge tiles
	 * @param map The map containing information relates to tiles and their coordinates
	 * @param carCoordinate present coordinate of the car
	 * @param view List of MapTiles in a grid of coordinates surrounding the car's current coordinate
	 */
	public static void updateMap(Map map, Coordinate carCoordinate, HashMap<Coordinate, MapTile> view) {

		// new probable tiles on the edge addition
		addingProbableEdges(map, carCoordinate);

		// All the explored tiles added to the map
		addTiles2Map(map, view);

		// This checks all the edges and its coordinates
		edgesValidation(map);
	}


	/**
	 * Validates the edge tiles to ensure that all coordinates in the list are in fact edges
	 * @param map A map containing all the information about tiles and their respective coordinates
	 */
	private static void edgesValidation(Map map) {

		boolean hasAdjoiningUnexploredTile = false;
		ArrayList<Coordinate> nonEdgeCoordinates = new ArrayList<Coordinate>();

		for (Coordinate coordinate : map.getEdgeTileCoordinates()) {

			hasAdjoiningUnexploredTile = false; 

			MapTile tile = map.getTheCurrentMap().get(coordinate).getTile();

			// Ensuring about walls and empty tiles being edges or not
			if (tile.isType(MapTile.Type.WALL) || tile.isType(MapTile.Type.EMPTY)) {
				nonEdgeCoordinates.add(coordinate);
			}
			// Ensuring each tiles has adjoining unexplored edge tile
			else {

				for (int offset : new int[]{-1, 1}) {
					if ((map.insideTheBounds(coordinate.x + offset, coordinate.y) && !map.inTheExploredMap(coordinate.x + offset, coordinate.y))
							|| (map.insideTheBounds(coordinate.x, coordinate.y+ offset) && !map.inTheExploredMap(coordinate.x , coordinate.y + offset))) {
						hasAdjoiningUnexploredTile = true; 
					}
				}

				if (!hasAdjoiningUnexploredTile) nonEdgeCoordinates.add(coordinate);
			}
		}

		// Remove all non-edges from the list of edge coordinates
		for (Coordinate coordinate : nonEdgeCoordinates) {
			map.getEdgeTileCoordinates().remove(coordinate);
		}
	}

	/**
	 * Investigates if the tile is useful;has exit,key or health and adds to respective lists
	 * @param map map containing information about tiles and coordinates 
	 * @param coordinate The coordinate of the MapTile 
	 * @param tile MapTile to be analyzed
	 */
	private static void analyzeTile(Map map, Coordinate coordinate, MapTile tile){

		if (tile.isType(MapTile.Type.FINISH)) {
			map.getTheExitTileCoordinates().add(coordinate);
		}else if(tile.isType(MapTile.Type.TRAP)){

			if (((TrapTile)tile).getTrap() == "lava" && ((LavaTrap)tile).getKey() > 0) {
				map.getKeyTileCoordinates().add(coordinate);
			} else if(((TrapTile)tile).getTrap() == "health" ) {
				map.getTheHealthTileCoordinates().add(coordinate);
			}
		} 
	}

	/**
	 * @param map Map with all the information of tiles and their respective coordinates
	 * @param carSpeed Current speed of car
	 * @param carCoordinate Current coordinate of car
	 * @param carDirection Current orientation of car
	 * @param movingForward If the car is moving forward or not
	 */
	public static void updatingScores(Map map, float carSpeed, Coordinate carCoordinate,
			WorldSpatial.Direction carDirection, boolean movingForward) {

		map.resetTileScores();
		updateAdjoiningTiles(map, carSpeed, carCoordinate, carDirection, movingForward);

		ArrayList<Coordinate> traversableCoordinates = getNoGrassTileCoordinates(map);
		traversableCoordinates.remove(carCoordinate);

		findTheShortestDistance(map, traversableCoordinates);
	}

	/**
	 * This updates the distance, damage and route for the tiles adjoining to the car
	 * Updates the tiles dependent on direction, car speed and direction
	 * @param map Map with all the information of tiles and their respective coordinates
	 * @param carSpeed Current speed of car
	 * @param carCoordinate Current coordinate of car
	 * @param carDirection Current orientation of car
	 * @param movingForward If the car is moving forward or not
	 */
	private static void updateAdjoiningTiles(Map map, float carSpeed, Coordinate carCoordinate,
			WorldSpatial.Direction carDirection, boolean movingForward){

		// Initializes the starting coordinates
		//Sets carCoordinates distance and damage to zero
		ArrayList<Coordinate> tempPath = new ArrayList<>();
		map.getTheCurrentMap().get(carCoordinate).setDamage(0);
		map.getTheCurrentMap().get(carCoordinate).setDistance(0);


		// if the car is moving
		// update the front and sides as normal cost
		// update rear as cost of stopping plus the normal cost
		if (carSpeed > 0) {
			int tempDamage = 0;
			if (map.getTheCurrentMap().get(carCoordinate).getTile() instanceof LavaTrap) tempDamage = DAMAGE_LAVA;

			//moving forward
			if(movingForward) {
				updateScore(map, checkBehind(carCoordinate, carDirection), carCoordinate ,tempDamage, 1, tempPath);
				updateTheScore(map, checkFront(carCoordinate, carDirection), carCoordinate);
			}
			else {
				// moving reverse
				updateScore(map, checkFront(carCoordinate, carDirection), carCoordinate ,tempDamage, 1, tempPath);
				updateTheScore(map, checkBehind(carCoordinate, carDirection), carCoordinate);
			}

			// update only if the car is not on grass as it can't steer on grass on halt
			if(!(map.getTheCurrentMap().get(carCoordinate).getTile() instanceof GrassTrap)) {
				updateTheScore(map, checkLeft(carCoordinate, carDirection), carCoordinate);
				updateTheScore(map, checkRight(carCoordinate, carDirection), carCoordinate);
			}
		}

		// if motionless
		// update front and behind with normal costs
		else {
			updateTheScore(map, checkFront(carCoordinate, carDirection), carCoordinate);
			updateTheScore(map, checkBehind(carCoordinate, carDirection), carCoordinate);
		}
	}

	/**
	 * returns an Arraylist of all coordinates excluding grass tiles 
	 * @param map Map with all the information of tiles and their respective coordinates
	 * @return a list of non-grass tiles coordinates 
	 */
	private static ArrayList<Coordinate> getNoGrassTileCoordinates(Map map) {

		ArrayList<Coordinate> nonGrassTileCoordinates = new ArrayList<>();

		// if tile not a grass tile, add to list
		for (Coordinate coordinate : map.getAlTheCoordinates()) {
			if (!(map.getTile(coordinate) instanceof GrassTrap)){
				nonGrassTileCoordinates.add(coordinate);
			}
		}
		return nonGrassTileCoordinates;
	}

	/**
	 * Updates all tiles as source coordinates
	 * Updates the lowest scoring  coordinates
	 * Ignores unreachable coordinates
	 * @param map Map with all the information of tiles and their respective coordinates
	 * @param Coordinates
	 */
	private static void findTheShortestDistance(Map map, ArrayList<Coordinate> Coordinates) {
		Coordinate currentCoordinate;

		while (Coordinates.size() != 0) {
			// the lowest scoring coordinate, null if the coordinate is unreachable or list is empty
			currentCoordinate = CalculateOptimalMove.lowestScore(map, Coordinates);
			if (currentCoordinate != null) {
				//updates all the adjoining tiles 
				updateTheScore(map, checkNorth(currentCoordinate), currentCoordinate);
				updateTheScore(map, checkSouth(currentCoordinate), currentCoordinate);
				updateTheScore(map, checkEast(currentCoordinate), currentCoordinate);
				updateTheScore(map, checkWest(currentCoordinate), currentCoordinate);
				Coordinates.remove(currentCoordinate);
			}
			else {
				// if no possible coordinate, break
				break;
			}
		}
	}

	/**
	 * Calculates the probable path from source coordinates to the target coordinates
	 * @param map Map with all the information of tiles and their respective coordinates
	 * @param targetCoordinate Coordinate of the target point
	 * @param sourceCoordinate Coordinate of the initial/starting point
	 */
	private static void updateTheScore(Map map, Coordinate targetCoordinate, Coordinate sourceCoordinate) {

		if(map.getAlTheCoordinates().contains(sourceCoordinate)) {
			ArrayList<Coordinate> potentialPath = new ArrayList<Coordinate>();
			updateScore(map, targetCoordinate, sourceCoordinate, map.getTheDamage(sourceCoordinate) ,
					map.getTheDistance(sourceCoordinate), potentialPath );
		}
	}

	/**
	 * Adds up the damage, distance and the route to the route from source to the target coordinates
	 * Update tile is called to compare the damage and distance to target
	 * Checks if the tile is a grass tile and calls the associated function
	 * @param map Map with all the information of tiles and their respective coordinates
	 * @param targetCoordinate Coordinate of the target point
	 * @param sourceCoordinate Coordinate of the initial/starting point
	 * @param damage 
	 * @param distance
	 * @param route
	 */
	private static void updateScore(Map map, Coordinate targetCoordinate, Coordinate sourceCoordinate, int damage, int distance, ArrayList<Coordinate> route ) {

		//the loop checks if the tile is on the current map or not

		if (map.getAlTheCoordinates().contains(targetCoordinate)) {
			ArrayList<Coordinate> potentialPath = new ArrayList<>(route);
			int probableDistance = distance;
			int probablelDamage = damage;
			potentialPath.add(sourceCoordinate);

			//Update board if current map not a Wall or MudTrap
			if( map.getType(targetCoordinate) != MapTile.Type.WALL && !(map.getTile(targetCoordinate) instanceof MudTrap)) {


				//if there is a grass tile, keep going straight until a tile with no grass is reached
				if(map.getTile(targetCoordinate) instanceof GrassTrap) {
					updateGrassScore(map, targetCoordinate, sourceCoordinate, probablelDamage, probableDistance, potentialPath);
				}
				else {
					probableDistance ++;
					//if there is a lava tile, increase the damage
					if(map.getTile(targetCoordinate) instanceof LavaTrap){
						probablelDamage= probablelDamage + DAMAGE_LAVA;
					}
					map.getData(targetCoordinate).tileUpdate(probablelDamage, probableDistance, potentialPath);
				}
			}

		}
	}

	/**
	 * Keeps going straight while updating the score of the grass tiles until
	 * a non-grass tile is reached and also updates that tile's score
	 * @param map Map with all the information of tiles and their respective coordinates
	 * @param targetCoordinate Coordinate of the target point
	 * @param sourceCoordinate Coordinate of the initial/starting point
	 * @param probableDamage
	 * @param probableDistance
	 * @param route
	 */
	private static void updateGrassScore(Map map, Coordinate targetCoordinate, Coordinate sourceCoordinate, int potentialDamage, int potentialDistance, ArrayList<Coordinate> path) {
		int xdiff = targetCoordinate.x -sourceCoordinate.x;
		int ydiff = targetCoordinate.y - sourceCoordinate.y;
		ArrayList<Coordinate> potentialPath = new ArrayList<>(path);
		Coordinate currentCoordinate= targetCoordinate;

		//ensures that tiles outside the map are not indexed
		while(map.getAlTheCoordinates().contains(currentCoordinate)) {

			potentialDistance++;
			potentialPath.add(currentCoordinate);

			if(map.getTile(currentCoordinate) instanceof GrassTrap) {
				//updates the grass tiles
				map.getData(currentCoordinate).tileUpdate(potentialDamage, potentialDistance, potentialPath);
			}
			else if(map.getType(currentCoordinate) != MapTile.Type.WALL && !(map.getTile(currentCoordinate) instanceof MudTrap)) {
				//updates the tile after the grass tile
				//if there is a lava tile, increase the damage
				if(map.getTile(currentCoordinate) instanceof LavaTrap){
					potentialDamage= potentialDamage + DAMAGE_LAVA;
				}
				map.getData(currentCoordinate).tileUpdate(potentialDamage, potentialDistance, potentialPath);
				break;
			}
			else {
				break;
			}
			//coordinate is incremented in a straight line
			currentCoordinate = new Coordinate(currentCoordinate.x + xdiff, currentCoordinate.y + ydiff);
		}

	}
	/**
	 * Viewing the 9x9 coordinates around the car
	 * @param map containing all the tile and coordinate information
	 * @param carCoordinate present position and coordinate of car
	 */
	private static void addingProbableEdges(Map map, Coordinate carCoordinate) {

		for (int xOffset = -GRID_SIZE_VIEW/2; xOffset <=GRID_SIZE_VIEW/2; xOffset++) {
			for (int yOffset = -GRID_SIZE_VIEW/2; yOffset <= GRID_SIZE_VIEW/2; yOffset ++) {

				// Ignore all non-boundary coordinates
				if (xOffset > -GRID_SIZE_VIEW/2 && xOffset < GRID_SIZE_VIEW/2 && yOffset >= -(GRID_SIZE_VIEW/2 - 1) && yOffset <= (GRID_SIZE_VIEW/2 - 1)) continue;

				Coordinate coordinate = new Coordinate(carCoordinate.x + xOffset, carCoordinate.y + yOffset);
				if (map.insideTheBounds(coordinate) && !map.inTheExploredMap(coordinate)) map.edgeTileCoordinates.add(coordinate);
			}
		}
	}


	/**
	 * Notes special tiles coordinates and add tiles to the maptile
	 * @param map containing all tile and respective coordinate information
	 * @param providedMap containing wall tiles 
	 */
	public static void addTiles2Map(Map map, HashMap<Coordinate, MapTile> providedMap) {

		for (Coordinate coordinate : providedMap.keySet()) {

			// Analyse unexplored tiles to check if they are useful
			if(map.insideTheBounds(coordinate) && !map.inTheExploredMap(coordinate)) {
				map.getTheCurrentMap().put(coordinate, new MapCoordinate(providedMap.get(coordinate)));
				analyzeTile(map, coordinate, providedMap.get(coordinate));
			}
		}
	}

	/**
	 * Adds MapTiles of a certain type to the map
	 * @param map A map containing all the information about tiles and their respective coordinates
	 * @param providedMap The initial map that was provided
	 * @param type Type of MapTile
	 */
	public static void add2Map(Map map, HashMap<Coordinate, MapTile> providedMap, MapTile.Type type) {

		for (Coordinate coordinate : providedMap.keySet()) {
			if (providedMap.get(coordinate).getType() == type) map.getTheCurrentMap().put(coordinate, new MapCoordinate(providedMap.get(coordinate)));
		}
	}
}