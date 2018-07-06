package codefathers.tripalert.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is used to provide the structure of the corresponding
 * table inside the SQLite db. We also have annotated with @Entity
 */
@IgnoreExtraProperties
public class Tracking implements Serializable{

    /**
     * id will be used as the primary key of the Tracking
     * todo:do we need a primary key for NoSql?
     */
    public int id;

    public Tracking() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartingPoint(Location startingPoint) {
        this.startingPoint = startingPoint;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    public void setStatus(int status) {
        this.status = status;
    }

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
    private String  creator;

    private Map<String,Boolean> followers; //phonenumbers
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

    public Tracking(Location startingPoint, Location destination, int status, int estimatedTime, String creator) {
        this.startingPoint = startingPoint;
        this.estimatedTime = estimatedTime;
        this.status = status;
        this.creator = creator;
        this.destination = destination;
        this.followers = null;
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

    public String  getCreator() {
        return creator;
    }

    public Location getDestination() {
        return destination;
    }

    public List<LogItem> getSituationLog() {
        return situationLog;
    }

    public void setSituationLog(List<LogItem> situationLog) {
        this.situationLog = situationLog;
        
    }

    public Map<String,Boolean> getFollowers() {
        return followers;
    }

    public void setFollowers(Map<String,Boolean> followers) {
        this.followers = followers;
    }
}

