package codefathers.tripalert.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.Tracking;

public class HomeScreenViewModel extends AndroidViewModel {

    /*
    TODO: [ALABA or ME or ANYONE:P ] fetch the current tracking and followed Trackings from Firebase
    The time has come, the most important part of the app, and the one that actually made
    me anxious in the past.We shall not let the fear take over, lets go and implement
    the most crucial part of the application!!

    in order for the user to get only the trackings that follows/owns, we need to use the
    query functions of firebase library like orderBy child.equalsTo, for the owner of the
    tracking there is the child owner with value that equals to the phonenumber. that
    should be very easy to implement...

    For the followers we have the followers child which contains each follower as a child,
    the child name is the phone of the follower, the value is true (or false if the follower
    unfollows) so somehow we have to query the trackings by getting the child inside followers
    (which should be the phone of the follower, and then this child should equals to true;
    check more details here:
    https://firebase.google.com/docs/database/android/lists-of-data


    */
    private MutableLiveData<List<Tracking>> followedTrackings;
    private MutableLiveData<Tracking> createdTracking;
    //private AppDatabase appDatabase;
    public HomeScreenViewModel(@NonNull Application application) {
        super(application);
       // this.appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void unfollowTracking (Tracking tracking){
       // appDatabase.tracking().deleteTracking(tracking);
    }

    public MutableLiveData<List<Tracking>> getFollowedTrackings() {
       // followedTrackings = appDatabase.trackingModel().getFollowedTrackings();
        if(followedTrackings == null){
            followedTrackings = new MutableLiveData <List<Tracking>>();
            loadFollowedTrackings();
        }
        return followedTrackings;
    }

    public MutableLiveData<Tracking> getCreatedTracking() {
        if(createdTracking == null ){
            createdTracking = new MutableLiveData<Tracking>();
            loadCreatedTracking();
        }
        //createdTracking = appDatabase.trackingModel().getCreatedTracking();
        return createdTracking;
    }

    private void loadFollowedTrackings(){
        List<Tracking> list = new ArrayList<Tracking>();
        followedTrackings.setValue(list);
    }

    private void loadCreatedTracking(){

    }
}
