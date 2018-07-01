package codefathers.tripalert.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

import javax.annotation.Nullable;

@IgnoreExtraProperties
public class Location implements Serializable
{
    protected String address;
    protected String lang;
    protected String lat;

    public String getLang() {
        return lang;
    }

    public String getLat() {
        return lat;
    }

    public Location() {
    }



    public Location(String lang, String lat){
        this.lang = lang;
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
