/**
 * Group 63: Software modeling design project
 */

package mycontroller;

import controller.CarController;
import tiles.MapTile;
import tiles.LavaTrap;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial;
import java.util.ArrayList;


/**
 * This class is used control the car when it is trying to escape the given maze
 */
public class MyAIController extends CarController{

	// Car information presently
	private WorldSpatial.Direction carDirection;
	private Coordinate carCoordinate;
	private float carSpeed;
	private boolean moving;

	private Map map;
	private CalculateOptimalMove optimalPathFinder = new CalculateOptimalMove();

	public MyAIController(Car car) {

		super(car);

		map = new Map(mapWidth(), mapHeight());

		// Add the type wall to the map
		UpdateMap.add2Map(map, getMap(), MapTile.Type.WALL);
	}

	/**
	 * Performs different actions like acceleration/deceleration/braking/steering for the car
	 * @param destination The next coordinate
	 */
	private void MakeAction(Coordinate destination) {

		// Stop if the destination is reached
		if (destination.equals(carCoordinate)) {
			applyBrake();
			return;
		}

		// Perform appropriate action on the basis of the relative car's position to destination
		if (carSpeed > 0) {
			if (destination.equals(checkLeft(carCoordinate, carDirection))) {
				turnLeft();
			} else if (destination.equals(checkRight(carCoordinate, carDirection))) {
				turnRight();
			} else if (destination.equals(checkFront(carCoordinate, carDirection))) {
				if (!moving) applyBrake();
			} else if (destination.equals(checkBehind(carCoordinate, carDirection))) {
				if (moving) applyBrake();
			}
		} else {
			if (destination.equals(checkFront(carCoordinate, carDirection))) {
				moving = true;
				applyForwardAcceleration();
			} else if (destination.equals(checkBehind(carCoordinate, carDirection))) {
				moving = false;
				applyReverseAcceleration();
			}
		}
	}
	
	@Override
	public void update() {

		// Get the current car information based on the current position on map
		carCoordinate = new Coordinate(getPosition());
		carDirection = getOrientation();
		carSpeed = getSpeed();

		// update the key tile list if the car is presently on a key tile
		if (map.getKeyTileCoordinates().contains(carCoordinate)) {
			map.setTheKeyTileCoordinates(getNotcollectedKeyCoordinates());
		}

		// Find any unexplored areas
		UpdateMap.updateMap(map, carCoordinate, getView());

		// Update all scores of the explored tiles on map
		UpdateMap.updatingScores(map, carSpeed, carCoordinate, carDirection, moving);

		// Determine the next move
		MakeAction(optimalPathFinder.nextMove(map, getKeys().size(), numKeys(), carCoordinate, getHealth()));
	}


	/**
	 * Scans all the key tile coordinates
	 * @return list of coordinates of key tiles that haven't been collected
	 */
	private ArrayList<Coordinate> getNotcollectedKeyCoordinates() {

		ArrayList<Coordinate> remainingKeyCoordinates = new ArrayList<Coordinate>();

		for (Coordinate coordinate : map.getKeyTileCoordinates()) {

			// add uncollected key's coordinate to the list
			if(!getKeys().contains(((LavaTrap)map.getTheCurrentMap().get(coordinate).getTile()).getKey())) {
				remainingKeyCoordinates.add(coordinate);
			}
		}

		return remainingKeyCoordinates;
	}
	
	
	// checks left
	public static Coordinate checkLeft(Coordinate coordinate , WorldSpatial.Direction orientation) {
		switch (orientation){
		case EAST:
			return checkNorth(coordinate);
		case NORTH:
			return checkWest(coordinate);
		case SOUTH:
			return checkEast(coordinate);
		case WEST:
			return checkSouth(coordinate);
		default:
			return null;
		}
	}

	// checks front
	public static Coordinate checkFront(Coordinate coordinate , WorldSpatial.Direction orientation) {
		switch (orientation){
		case EAST:
			return checkEast(coordinate);
		case NORTH:
			return checkNorth(coordinate);
		case SOUTH:
			return checkSouth(coordinate);
		case WEST:
			return checkWest(coordinate);
		default:
			return null;
		}
	}

	// checks behind
	public static Coordinate checkBehind(Coordinate coordinate , WorldSpatial.Direction orientation) {
		switch (orientation){
		case EAST:
			return checkWest(coordinate);
		case NORTH:
			return checkSouth(coordinate);
		case SOUTH:
			return checkNorth(coordinate);
		case WEST:
			return checkEast(coordinate);
		default:
			return null;
		}
	}

	/**
	 * Returns the coordinate's East coordinate
	 * @param coordinate Current coordinate of the car
	 * @param orientation Current Orientation
	 * @return the coordinate's east coordinate
	 */
	public static Coordinate checkEast(Coordinate coordinate) { return new Coordinate(coordinate.x+1, coordinate.y); }

	/**
	 * Returns the coordinate's west coordinate
	 * @param coordinate Current coordinate of the car
	 * @return the coordinate's west coordinate
	 */
	public static Coordinate checkWest(Coordinate coordinate) { return new Coordinate(coordinate.x-1, coordinate.y); }

	/**
	 * Returns the coordinate's north coordinate
	 * @param coordinate Current coordinate of the car
	 * @return the coordinate's north coordinate
	 */
	public static Coordinate checkNorth(Coordinate coordinate) { return new Coordinate(coordinate.x, coordinate.y+1); }

	/**
	 * Returns the coordinate's south coordinate
	 * @param coordinate Current coordinate of the car
	 * @return the coordinate's south coordinate 
	 */
	public static Coordinate checkSouth(Coordinate coordinate) { return new Coordinate(coordinate.x, coordinate.y-1); }

	/**
	 * Returns the coordinate's right coordinate
	 * @param coordinate Current coordinate of the car
	 * @param orientation Current Orientation
	 * @return the coordinate's right coordinate
	 */
	public static Coordinate checkRight(Coordinate coordinate , WorldSpatial.Direction orientation) {
		switch (orientation){
		case EAST:
			return checkSouth(coordinate);
		case NORTH:
			return checkEast(coordinate);
		case SOUTH:
			return checkWest(coordinate);
		case WEST:
			return checkNorth(coordinate);
		default:
			return null;
		}
	}
}
