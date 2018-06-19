package codefathers.tripalert.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codefathers.tripalert.database.AppDatabase;
import codefathers.tripalert.database.TrackingModel;

public class HomeScreenViewModel extends AndroidViewModel {

    private final LiveData<List<TrackingModel>> followedTrackings;
    private final LiveData<List<TrackingModel>> createdTracking;
    private AppDatabase appDatabase;

    public HomeScreenViewModel(@NonNull Application application) {
        super(application);
        this.appDatabase = AppDatabase.getDatabase(this.getApplication());
        createdTracking = appDatabase.trackingModel().getCreatedTracking();
        followedTrackings = appDatabase.trackingModel().getAllTrackings();

    }
    public void deleteTracking(TrackingModel trackingModel){
        appDatabase.trackingModel().deleteTracking(trackingModel);
    }
}
