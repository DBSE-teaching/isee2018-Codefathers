package codefathers.isee.com.tripalert;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class MessageGroup {
    public List<String> Followers;


    public MessageGroup() {
        // Default constructor required for calls to DataSnapshot.getValue(String.class)
    }

    public MessageGroup(List<String> followers) {
        Followers = followers;
    }

    public List<String> getFollowers() {
        return Followers;
    }

    public void setFollowers(List<String> followers) {
        Followers = followers;
    }
}