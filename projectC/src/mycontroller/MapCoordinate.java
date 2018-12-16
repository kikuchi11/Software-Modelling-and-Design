/**
 * Group 63: Software modeling design project
 */

package mycontroller;

import tiles.MapTile;
import utilities.Coordinate;
import java.util.ArrayList;


/**
 * This class is used as a data storing space about a coordinate
 * which is used to determine the most efficient path
 */
public class MapCoordinate {

    private int damg;
    private int distance;
    private MapTile tile;
    private ArrayList<Coordinate> route;

    public MapCoordinate(MapTile tile) {
        this.tile = tile;
        damg = Integer.MAX_VALUE;
        distance = Integer.MAX_VALUE;
        route = new ArrayList<Coordinate>();
    }

    /**
     * Looks at the current values and analyzes them for a potential damage or route
     * distance. If everything seems fine continues otherwise deviates the path
     * @param Amount of damage taken to the coordinates
     * @param distance taken to reach a certain coordinate
     * @param Alternate route to the next coordinates
     *      */
    public void tileUpdate(int possibleDamage, int possibleDistance, ArrayList<Coordinate> possiblePath) {

        if ((possibleDamage < damg) || (possibleDamage == damg && possibleDistance < distance)) {
            damg = possibleDamage;
            distance = possibleDistance;
            route = new ArrayList<Coordinate>(possiblePath);
        }
    }

    
    
    /**
     * @return damage carried forward to this coordinate
     */
    public int getDamage() {
        return damg;
    }
    
    /**
     * @return Map tile's coordinate
     */
    public MapTile getTile() {
        return tile;
    }
    
    /**
     * @param Setting the damage involved on moving to this coordinate
     */
    public void setDamage(int damage) {
        this.damg = damage;
    }
    
    /**
     * @return Distance to the coordinate below
     */
    public int getDistance() {
        return distance;
    }
    
    /**
     * @param Sets the distance to a certain coordinate
     */
    public void setDistance(int distance) {
        this.distance= distance;
    }


    /**
     * Alter the the current path to a new economical path
     * @param Coordinates list for the new one
     */
    public void alterRoute(ArrayList<Coordinate> path) {
        this.route = new ArrayList<Coordinate>(path);
    }

    /**
     * Gets the route to a  certain tile
     * @return Coordinates list for the new route
     */
    public ArrayList<Coordinate> getRoute() {
        return route;
    }

    /**
     * @return Type of tile returned
     */
    public MapTile.Type getType() {
        return tile.getType();
    }

    /**
     * Both damage and distance values reset to default
     */
    public void resetScore() {
        this.damg = Integer.MAX_VALUE;
        this.distance= Integer.MAX_VALUE;
    }

}
