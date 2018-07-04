package codefathers.tripalert.models;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;


@IgnoreExtraProperties
public class AppUser implements Serializable{
    public AppUser() {
    }

    /**
     * phone number of the user. Important to cross check with a user's
     * contacts
     */
    private String token;

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
    private List<AppUser> followers = null;
    /**
     * email of google account;
     */
    private String email;

    /**
     * the users that this user following, null if no users
     */
    private List<AppUser> following = null ;

    //constructor for sign in
    public AppUser(String email, String token) {
        this.token = token;
        this.email = email;
    }

    //constructor for contacts
    public AppUser(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
         this.phoneNumber = phoneNumber;
    }
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public Tracking getCreatedTracking() {
        return createdTracking;
    }

    public void setCreatedTracking(Tracking createdTracking) {
        this.createdTracking = createdTracking;
    }

    public List<AppUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<AppUser> followers) {
        this.followers = followers;
    }

    public List<AppUser> getFollowing() {
        return following;
    }

    public void setFollowing(List<AppUser> following) {
        this.following = following;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
