package codefathers.isee.com.tripalert.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Track {
    private String trackId;
    private String userId;
    private GpsCoordinates origin;
    private GpsCoordinates lastKnownLocation;
    private GpsCoordinates destination;
    private List<AppUser> subscribers;

    public Track() {
    }

    public Track(GpsCoordinates origin) {
        this.origin = origin;
    }

    public Track(String trackId, GpsCoordinates origin, GpsCoordinates lastKnownLocation, GpsCoordinates destination, List<AppUser> subscribers) {
        this.trackId = trackId;
        this.origin = origin;
        this.lastKnownLocation = lastKnownLocation;
        this.destination = destination;
        this.subscribers = subscribers;
    }

    public static void start() {

    }
    public static void stop() {

    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public GpsCoordinates getOrigin() {
        return origin;
    }

    public void setOrigin(GpsCoordinates origin) {
        this.origin = origin;
    }

    public GpsCoordinates getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(GpsCoordinates lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public GpsCoordinates getDestination() {
        return destination;
    }

    public void setDestination(GpsCoordinates destination) {
        this.destination = destination;
    }

    public List<AppUser> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<AppUser> subscribers) {
        this.subscribers = subscribers;
    }
}
