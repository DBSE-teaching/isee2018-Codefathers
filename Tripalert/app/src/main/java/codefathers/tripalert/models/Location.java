package codefathers.tripalert.models;

import java.io.Serializable;

public class Location implements Serializable
{
    private String address;
    private String Long;
    private String Lat;

    public Location(String Long, String Lat){
        this.Long = Long;
        this.Lat = Lat;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLong() {
        return Long;
    }

    public String getLat() {
        return Lat;
    }
}
