package codefathers.tripalert.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
import codefathers.tripalert.services.DatabaseService;

public class HomeScreenViewModel extends AndroidViewModel {

    private AppUser user;
    private MutableLiveData<List<Tracking>> followedTrackings;
    private MutableLiveData<Tracking> createdTracking;

    //private AppDatabase appDatabase;
    public HomeScreenViewModel(@NonNull Application application) {
        super(application);
        // this.appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void unfollowTracking(Tracking tracking) {
      FirebaseDatabase.getInstance().getReference("trackings").child(tracking.getCreator()+"/followers/" + user.getPhoneNumber()).setValue(false);
    }

    public void addSituationLog(LogItem log) {

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("trackings");

        String id = dbRef.push().getKey();
        dbRef.child(user.getPhoneNumber()).child("situationLog").child(id).setValue(log);
    }

    public void setUser(AppUser user) {
        this.user = user;
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

    private void loadCreatedTracking() {
        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("trackings").child(user.getPhoneNumber());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Tracking tracking = dataSnapshot.getValue(Tracking.class);
                if (tracking != null) {
                    createdTracking.setValue(tracking);
                } else {
                    createdTracking.setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
