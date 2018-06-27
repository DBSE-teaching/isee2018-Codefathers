package codefathers.tripalert.models;

import java.util.List;

/**
 * This class is used to provide the structure of the corresponding
 * table inside the SQLite db. We also have annotated with @Entity
 */
public class Tracking {

    /**
     * id will be used as the primary key of the Tracking
     * todo:do we need a primary key for NoSql?
     */
    public int id;
    /**
     * starting point Location details
     */
    private Location startingPoint;
    /**
     * destination Location details
     */
    private Location destination;

    /**
     * holds the data of the creator, like phonenumber etc...
     */
    private User creator;
    /**
     * the time that the user exepcts a trip to last. The time
     * is in minutes.
     */
    private int estimatedTime;

    /**
     * the status of the current tracking. We use here the specified
     * code of each status.
     */
    private int status;         //todo: see if its possible to do this in enum.

    /**
     * if the tracking is created or t
     */

    private List<LogItem> situationLog;

    public Tracking(Location startingPoint, Location destination, int status, int estimatedTime, User creator) {
        this.startingPoint = startingPoint;
        this.estimatedTime = estimatedTime;
        this.status = status;
        this.creator = creator;
        this.destination = destination;
    }


    public Location getStartingPoint() {
        return startingPoint;
    }


    public int getEstimatedTime() {
        return estimatedTime;
    }


    public int getStatus() {
        return status;
    }

    public User getCreator() {
        return creator;
    }


    public Location getDestination() {
        return destination;
    }
}

