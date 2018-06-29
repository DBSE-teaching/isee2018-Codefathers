package codefathers.tripalert.models;

import java.io.Serializable;

public class Location implements Serializable
{
    private String address;
    private String lang;
    private String lat;

    public Location(String lang, String lat){
        this.lang = lang;
        this.lat = lat;
    }


    public String getLang() {
        return lang;
    }

    public String getLat() {
        return lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
