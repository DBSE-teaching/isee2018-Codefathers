package codefathers.tripalert.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codefathers.tripalert.models.Tracking;

public class HomeScreenViewModel extends AndroidViewModel {

    private LiveData<List<Tracking>> followedTrackings;
    private LiveData<Tracking> createdTracking;
    //private AppDatabase appDatabase;

    public HomeScreenViewModel(@NonNull Application application) {
        super(application);
       // this.appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void deleteTracking(Tracking tracking){
       // appDatabase.tracking().deleteTracking(tracking);
    }

    public LiveData<List<Tracking>> getFollowedTrackings() {
       // followedTrackings = appDatabase.trackingModel().getFollowedTrackings();
        loadFollowedTrackings();
        return followedTrackings;
    }

    public LiveData<Tracking> getCreatedTracking() {
        //createdTracking = appDatabase.trackingModel().getCreatedTracking();
        return createdTracking;
    }

    public void loadFollowedTrackings(){

    }
}
