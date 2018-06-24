package codefathers.tripalert.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codefathers.tripalert.database.AppDatabase;
import codefathers.tripalert.database.TrackingModel;

public class HomeScreenViewModel extends AndroidViewModel {

    private LiveData<List<TrackingModel>> followedTrackings;
    private LiveData<TrackingModel> createdTracking;
    private AppDatabase appDatabase;

    public HomeScreenViewModel(@NonNull Application application) {
        super(application);
        this.appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void deleteTracking(TrackingModel trackingModel){
        appDatabase.trackingModel().deleteTracking(trackingModel);
    }

    public LiveData<List<TrackingModel>> getFollowedTrackings() {
        followedTrackings = appDatabase.trackingModel().getFollowedTrackings();
        return followedTrackings;
    }

    public LiveData<TrackingModel> getCreatedTracking() {
        createdTracking = appDatabase.trackingModel().getCreatedTracking();
        return createdTracking;
    }
}
