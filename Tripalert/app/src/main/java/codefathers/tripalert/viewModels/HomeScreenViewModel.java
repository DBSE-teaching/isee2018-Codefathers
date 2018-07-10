package codefathers.tripalert.viewModels;

import android.app.Application;
import android.app.NotificationManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.LogItem;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.TrackingStatus;
import codefathers.tripalert.services.DatabaseService;

import static codefathers.tripalert.FireSettings.CHANNEL_ID;

public class HomeScreenViewModel extends AndroidViewModel {

    private AppUser user;
    private MutableLiveData<List<Tracking>> followedTrackings;
    private MutableLiveData<Tracking> createdTracking;
    private MutableLiveData<List<LogItem>> logItems;
    public   int TIME_LIMIT;
    public   int TIME_LOCATTION_LIMIT;
    public   int LOCATION_LIMIT ;
    public   int DELAY_LIMIT;
    public   boolean HAS_LOCATION_ENABLED;

    //private AppDatabase appDatabase;
    public HomeScreenViewModel(@NonNull Application application) {
        super(application);
        // this.appDatabase = AppDatabase.getDatabase(this.getApplication());
    }


    public AppUser getUser(){
        return user;
    }
    public void unfollowTracking(Tracking tracking) {
      FirebaseDatabase.getInstance().getReference("trackings").child(tracking.getCreator()+"/followers/" + user.getPhoneNumber()).setValue(false);
    }

    public void addSituationLog(LogItem log) {
        if(log.getRecievers() == null && createdTracking !=null){
            if(createdTracking.getValue().getFollowers() != null)log.setRecievers(createdTracking.getValue().getFollowers());
        };
        if(log.getCreator() == null && createdTracking != null ){
            log.setCreator(createdTracking.getValue().getCreator());
        }

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("logItems");
        String id = dbRef.push().getKey();
        dbRef.child(id).setValue(log);
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public MutableLiveData<List<LogItem>> getLogItems(){
     if(logItems == null ){
         logItems = new MutableLiveData<List<LogItem>>();
         loadLogItems();
     }
     return logItems;
    }
    public MutableLiveData<List<Tracking>> getFollowedTrackings() {
        // followedTrackings = appDatabase.trackingModel().getFollowedTrackings();
        if (followedTrackings == null) {
            followedTrackings = new MutableLiveData<List<Tracking>>();
            loadFollowedTrackings();
        }
        return followedTrackings;
    }

    public MutableLiveData<Tracking> getCreatedTracking() {
        if (createdTracking == null) {
            createdTracking = new MutableLiveData<Tracking>();
            loadCreatedTracking();
        }
        //createdTracking = appDatabase.trackingModel().getCreatedTracking();
        return createdTracking;
    }

    private void loadLogItems(){
        Query dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("logItems").orderByChild("recievers/" + user.getPhoneNumber())
                .equalTo(true);
        dbRef.addChildEventListener(new ChildEventListener() {
            List<LogItem> logItemsList= new ArrayList<>();

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                LogItem item = dataSnapshot.getValue(LogItem.class);

                logItemsList.add(0,item);
                logItems.setValue(logItemsList);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                LogItem item = dataSnapshot.getValue(LogItem.class);
                logItemsList.remove(item);
                logItems.setValue(logItemsList);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void loadFollowedTrackings() {
        Query dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("trackings").orderByChild("followers/" + user.getPhoneNumber())
                .equalTo(true);
        dbRef.addChildEventListener(new ChildEventListener() {
            List<Tracking> trackings = new ArrayList<>();

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Tracking tracking = dataSnapshot.getValue(Tracking.class);
                trackings.add(tracking);
                followedTrackings.setValue(trackings);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Tracking tracking = dataSnapshot.getValue(Tracking.class);
                trackings.set(trackings.indexOf(tracking), tracking);
                followedTrackings.setValue(trackings);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Tracking tracking = dataSnapshot.getValue(Tracking.class);
                trackings.remove(tracking);
                followedTrackings.setValue(trackings);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        List<Tracking> list = new ArrayList<Tracking>();
        followedTrackings.setValue(list);
    }

    public void changeCreatedTrackingtatus(int status) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("trackings");
        dbRef.child(user.getPhoneNumber()).child("status").setValue(status);
    }

    public void changeCreatedTrackingEstimatedTime(int time){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("trackings");
        dbRef.child(user.getPhoneNumber()).child("estimatedTime").setValue(time);


    }
    public void removeCreatedTracking(){
      FirebaseDatabase.getInstance().getReference("trackings").child(user.getPhoneNumber()).setValue(null);
    }
    private void loadCreatedTracking() {
        Query dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("trackings").orderByKey().equalTo(user.getPhoneNumber());
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Tracking tracking = dataSnapshot.getValue(Tracking.class);
                if (tracking != null) {
                    createdTracking.setValue(tracking);
                } else {
                    createdTracking.setValue(null);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Tracking tracking = dataSnapshot.getValue(Tracking.class);
                if (tracking != null) {
                    createdTracking.setValue(tracking);
                } else {
                    createdTracking.setValue(null);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                createdTracking.setValue(null);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
