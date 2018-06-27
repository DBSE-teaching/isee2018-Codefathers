package codefathers.tripalert.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FollowerModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;
    private String phoneNumber;
    private Boolean isFollowing;

    FollowerModel(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isFollowing = false;
    }


    public String getName() {
        return name;
    }

    public Boolean getFollowing() {
        return isFollowing;
    }

    public void setFollowing(Boolean following) {
        isFollowing = following;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
