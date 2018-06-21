package codefathers.isee.com.tripalert.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class GpsCoordinates {
    public String longitude;
    public String latitude;

    public GpsCoordinates() {
    }

    public GpsCoordinates(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
