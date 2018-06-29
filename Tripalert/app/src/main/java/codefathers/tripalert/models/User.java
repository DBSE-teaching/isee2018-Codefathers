package codefathers.tripalert.models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{
    /**
     * phone number of the user. Important to cross check with a user's
     * contacts
     */
    private String phoneNumber;
    /**
     * user name of the user, todo: specify if we really need this
     */
    private String userName;
    /**
     * the tracking that a user has created, null if no tracking
     */
    private Tracking createdTracking = null;
    /**
     * the lists of followers that following his tracking, null
     * if there are no followers.
     */
    private List<User> followers = null;
    /**
     * email of google account;
     */
    private String email;

    /**
     * the users that this user following, null if no users
     */
    private List<User> following = null ;

    public User(String phoneNumber, String userName, String email){
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public Tracking getCreatedTracking() {
        return createdTracking;
    }

    public void setCreatedTracking(Tracking createdTracking) {
        this.createdTracking = createdTracking;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }
}
