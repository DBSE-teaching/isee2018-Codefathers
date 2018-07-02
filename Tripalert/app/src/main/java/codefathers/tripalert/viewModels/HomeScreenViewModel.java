package codefathers.tripalert.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.User;

public class HomeScreenViewModel extends AndroidViewModel {

    private MutableLiveData<List<Tracking>> followedTrackings;
    private LiveData<User> user;
    //private AppDatabase appDatabase;
    public HomeScreenViewModel(@NonNull Application application) {
        super(application);

       // this.appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void unfollowTracking (Tracking tracking){
       // appDatabase.tracking().deleteTracking(tracking);
    }
    public void abortTracking(){

    }

    public LiveData<List<Tracking>> getFollowedTrackings() {
       // followedTrackings = appDatabase.trackingModel().getFollowedTrackings();
        if(followedTrackings == null){
            followedTrackings = new MutableLiveData <List<Tracking>>();
            loadFollowedTrackings();
        }
        return followedTrackings;
    }

    public LiveData<User> getUser() {
        if(user == null ){
            user = new MutableLiveData<User>();
            loadUser();
        }
        //createdTracking = appDatabase.trackingModel().getCreatedTracking();
        return user;
    }

    private void loadFollowedTrackings(){

        /**
         * logic behind firebase
         */
        Location tempStart = new Location("000","0000");
        tempStart.setAddress(" Location Address Street");

        Location tempDest = new Location("0000","00000");
        tempDest.setAddress(" Destination Address Street");
        List<Tracking> list = new ArrayList<Tracking>();
        Tracking tracking = new Tracking(tempStart,tempDest,0,25,new User("694633453","maraki<3","maraki@fraoulitsa.gr"));
        list.add(tracking);
        list.add(
                new Tracking(tempStart,tempDest,2,25,new User("694633453","maraki<3","maraki@fraoulitsa.gr"))
        );
        list.add(
                new Tracking(tempStart,tempDest,3,25,new User("694633453","maraki<3","maraki@fraoulitsa.gr"))
        );

        followedTrackings.setValue(list);
    }

    private void loadUser(){
        /**
         *
         * logic behind firebase
         * **/

    }
}
