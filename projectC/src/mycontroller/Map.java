/**
 * Group 63: Software modeling design project
 */

package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Map {

	public HashMap<Coordinate, MapCoordinate> currentMap = new HashMap<>();
	
	// Coordinates of all the useful tiles
	public ArrayList<Coordinate> keyTileCoordinates = new ArrayList<>();
	public ArrayList<Coordinate> edgeTileCoordinates = new ArrayList<>();
	public ArrayList<Coordinate> healthTileCoordinates = new ArrayList<>();
	public ArrayList<Coordinate> exitTileCoordinates = new ArrayList<>(); 

	private final int WIDTH;
	private final int HEIGHT;

	public Map(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
	}

	/**
	 * @return list of coordinates of the edge tiles
	 */
	public ArrayList<Coordinate> getEdgeTileCoordinates() {
		return edgeTileCoordinates;
	}
	
	/**
	 * @return list of coordinates of the key tiles
	 */
	public ArrayList<Coordinate> getKeyTileCoordinates() {
		return keyTileCoordinates;
	}
	
	/**
	 * Commit list of updated key tile coordinates 
	 * @param keysTileCoordinates List of updated key tile coordinates
	 */
	public void setTheKeyTileCoordinates(ArrayList<Coordinate> keysTileCoordinates) {
		this.keyTileCoordinates = keysTileCoordinates;
	}
	
	/**
	 * @return list of coordinates on updated health tile 
	 */
	public ArrayList<Coordinate> getTheHealthTileCoordinates() {
		return healthTileCoordinates;
	}
	
	/**
	 * @return list of exit tile coordinates
	 */
	public ArrayList<Coordinate> getTheExitTileCoordinates() {
		return exitTileCoordinates;
	}
	
	/**
	 * @return Hash-map to store all coordinates data
	 */
	public HashMap<Coordinate, MapCoordinate> getTheCurrentMap() {
		return currentMap;
	}
	
	/**
	 * @param coordinate Coordinate on the map
	 * @return MapCoordinate for the required coordinate
	 */
	public MapCoordinate getData(Coordinate coordinate) {
		return currentMap.get(coordinate);
	}
	
	/**
	 * @return Set of all the map coordinates
	 */
	public Set<Coordinate> getAlTheCoordinates(){
		return currentMap.keySet();
	}
	
	/**
	 * @param coordinate Coordinate on the map
	 * @return MapTile of the required coordinate
	 */
	public MapTile getTile(Coordinate coordinate) {
		return currentMap.get(coordinate).getTile();
	}
	
	/**
	 * @param coordinate Coordinate on the map
	 * @return MapTile type of the required coordinate
	 */
	public MapTile.Type getType(Coordinate coordinate) {
		return currentMap.get(coordinate).getType();
	}

	/**
	 * @param coordinate Coordinate on the map
	 * @return amount of damage to reach the coordinates 
	 */
	public int getTheDamage(Coordinate coordinate) {
		return currentMap.get(coordinate).getDamage();
	}
	
	/**
	 * @param coordinate Coordinate on the map
	 * @return Distance to reach coordinate
	 */
	public int getTheDistance(Coordinate coordinate) {
		return currentMap.get(coordinate).getDistance();
	}

	/**
	 * Function to reset all the scores of tiles to their default values
	 */
	public void resetTileScores() {
		for (MapCoordinate coordinate: currentMap.values()) {
			coordinate.resetScore();
		}
	}
	

	/**
	 * Checks to see if a certain coordinate has been explored
	 * @param x x_component of the coordinate
	 * @param y y_component of the coordinate
	 * @return True if the coordinate has been explored
	 */
	public boolean inTheExploredMap(int x, int y) {
		return inTheExploredMap(new Coordinate(x, y));
	}
	
	/**
	 * Checks to see if a certain coordinate has been explored
	 * @param coordinate A coordinate
	 * @return True if the coordinate has been explored
	 */
	public boolean inTheExploredMap(Coordinate coordinate) {
		return currentMap.containsKey(coordinate);
	}


	/**
	 * Check to see if a coordinate is within the bounds of the map
	 * @param x x_component of the coordinate
	 * @param y y_component of the coordinate
	 * @return True if the coordinate is within map boundaries
	 */
	public boolean insideTheBounds(int x , int y) {
		return x < WIDTH && x >= 0 && y < HEIGHT && y >= 0;
	}
	
	/**
	 * Check to see if a coordinate is within the bounds of the map
	 * @param coordinate A coordinate
	 * @return True if the coordinate is within map boundaries
	 */
	public boolean insideTheBounds(Coordinate coordinate) {
		return insideTheBounds(coordinate.x, coordinate.y);
	}
	
	/**
	 * Checks all the paths until the starting point is a car coordinate
	 * returns initial coordinate required to travel towards the coordinate target
	 * @param targetCoordinate Coordinates towards which the car needs to move forward
	 * @param carCoordinate current coordinate of the car
	 * @return returns initial coordinates in the path
	 */
	public Coordinate findThePath(Coordinate targetCoordinate, Coordinate carCoordinate) {
		
		Coordinate currentCoordinate = targetCoordinate;
		Coordinate nextCoordinate = currentMap.get(currentCoordinate).getRoute().get(0);
		
		//Loops to find the next tile
		while (!nextCoordinate.equals(carCoordinate)) {
			currentCoordinate = nextCoordinate;
			nextCoordinate = currentMap.get(currentCoordinate).getRoute().get(0);

		}
		if (currentMap.get(currentCoordinate).getRoute().size() > 1) {
			return (currentMap.get(currentCoordinate).getRoute().get(1));
		} else {
			return (currentCoordinate);
		}
	}

}

