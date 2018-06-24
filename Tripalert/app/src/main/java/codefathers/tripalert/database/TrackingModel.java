package codefathers.tripalert.database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * This class is used to provide the structure of the corresponding
 * table inside the SQLite db. We also have annotated with @Entity
 */
@Entity
public class TrackingModel {

    /**
     * id will be used as the primary key of the TrackingModel
     * table in sqlite, and as identifier;
     */
    @PrimaryKey(autoGenerate = true)
    public int id;
    /**
     * the address of the destination
     */
    private String destinationName;

    /**
     * the starting point address name
     */
    private String startingPoint;

    /**
     * the coordinates of the the destination  encoded as "long;lang"
     */
    private String destinationCords;

    /**
     * the coordinates of the the starting point encoded as "long;lang"
     */
    private String startingPointCords;

    /**
     * the phone number that each tracking is ascociated to
     */
    private String phoneNumber;

    /**
     * the time that the user exepcts a trip to last. The time
     * is in minutes.
     */
    private int estimatedTime;

    /** the status of the current tracking. We use here the specified
     * code of each status.
     */
    private int status;         //todo: see if its possible to do this in enum.

    /**
     * if the tracking is created or t
     */
    private Boolean isCreated;


    public  TrackingModel(String destinationCords, String destinationName,
                          String startingPointCords, String startingPoint,
                          int status, int estimatedTime, String phoneNumber, Boolean isCreated )
    {
        this.destinationCords = destinationCords;
        this.destinationName = destinationName;
        this.startingPointCords = startingPointCords;
        this.startingPoint = startingPoint;
        this.estimatedTime = estimatedTime;
        this.status = status;
        this.isCreated = isCreated;
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

    public String getDestinationCords() {
        return destinationCords;
    }

    public static TrackingModel[] populateData(){
        return new TrackingModel[]{
            new TrackingModel("0000:0000","To Somewhere","0000:0000", "From Somewhere ",1 ,20 , "6943227389", true ),
            new TrackingModel("0000:0000","To Somewhere","0000:0000", "From Somewhere ",3 ,20 , "6979738129", false ),
            new TrackingModel("0000:0000","To Somewhere","0000:0000", "From Somewhere ",1 ,20 , "6979738231", false ),
            new TrackingModel("0000:0000","To Somewhere","0000:0000", "From Somewhere ",2 ,20 , "6979735637", false )
        };
    }

    public Boolean getCreated() {
        return isCreated;
    }
}
