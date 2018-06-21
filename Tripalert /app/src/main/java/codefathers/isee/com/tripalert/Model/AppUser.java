package codefathers.isee.com.tripalert.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class AppUser {
    public String appUserId;
    public String appUserName;
    public Track ownTrack;
    public Track othersTrack;

    public AppUser() {
    }

    public AppUser(String appUserId) {
        this.appUserId = appUserId;
    }

    public AppUser(String appUserId, String appUserName, Track ownTrack, Track othersTrack) {
        this.appUserId = appUserId;
        this.appUserName = appUserName;
        this.ownTrack = ownTrack;
        this.othersTrack = othersTrack;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public Track getOwnTrack() {
        return ownTrack;
    }

    public void setOwnTrack(Track ownTrack) {
        this.ownTrack = ownTrack;
    }

    public Track getOthersTrack() {
        return othersTrack;
    }

    public void setOthersTrack(Track othersTrack) {
        this.othersTrack = othersTrack;
    }
}
