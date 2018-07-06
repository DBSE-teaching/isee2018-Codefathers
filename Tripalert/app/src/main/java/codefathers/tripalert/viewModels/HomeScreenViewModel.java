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
