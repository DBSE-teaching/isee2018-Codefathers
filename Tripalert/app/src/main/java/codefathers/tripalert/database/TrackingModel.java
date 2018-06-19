package codefathers.tripalert.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TrackingModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    // we will ignore for the moment the fact that this is no fucking way in 3NF or equivalent
    private String destinationName;
    private String startingPoint;
    private  String destinationCords;
    private String startingPointCords;
    private int estimatedTime;
    private int status;
    private Boolean isCreated;
    private String phoneNumber;

    public  TrackingModel(String destinationCords, String destinationName, String startingPointCords, String startingPoint, int status, int estimatedTime, String phoneNumber)
    {
        this.destinationCords = destinationCords;
        this.destinationName = destinationName;
        this.startingPointCords = startingPointCords;
        this.startingPoint = startingPoint;
        this.estimatedTime = estimatedTime;
        this.status = status;
        this.isCreated = false;
        this.phoneNumber = phoneNumber;
    }
    public String getDestinationName() {
        return destinationName;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getStartingPointCords() {
        return startingPointCords;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getStatus() {
        return status;
    }
}
