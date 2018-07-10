package codefathers.tripalert.models;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@IgnoreExtraProperties
public class LogItem implements Serializable {
    /**
     * the status that logItem belongs
     * ex: status 0: the tracking has created;
     */
    private int status;
    private String message;
    private String createdAt;
    private String creator;
    private String name;
    private Map<String,Boolean> recievers ;
    private String key;

    public LogItem(){

    }
    public LogItem(int status, String message) {
        this.status = status;
        this.message = message;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.GERMANY);
        this.createdAt = formatter.format(new Date());
    }


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Map<String, Boolean> getRecievers() {
        return recievers;
    }

    public void setRecievers(Map<String, Boolean> recievers) {
        this.recievers = recievers;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        int hash1 = getCreator() != null? getCreator().hashCode():0;
        int hash2 = getCreatedAt() != null? getCreatedAt().hashCode():0;
        int hash3 = getMessage() != null? getMessage().hashCode():0;
        hash = hash + hash1 + hash2 + hash3;
        return  hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean state2 = this.getCreator().equals( ((LogItem) obj).getCreator());
        boolean state3 = this.getCreatedAt().equals(((LogItem) obj).getCreatedAt());
        boolean state1 = this.getMessage().equals(((LogItem) obj).getMessage());
        return state1 && state2 && state3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
