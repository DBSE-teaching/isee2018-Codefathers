package codefathers.isee.com.tripalert.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Settings {
    private  String userId;


    public Settings() {
    }

    public Settings(String userId) {
        this.userId = userId;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
