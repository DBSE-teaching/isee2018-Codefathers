package codefathers.tripalert.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.models.Tracking;

import static android.content.ContentValues.TAG;

public class DatabaseService extends Service {
    String TAG = "DB WRITE";
    private DatabaseReference dbRef;
    private DatabaseReference userRef;
    public DatabaseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


    public void writeTracking(Tracking tracking) {
        Log.d(TAG,"db write tracking");


        dbRef = FirebaseDatabase.getInstance().getReference("tracks");
        String trackId = dbRef.push().getKey();
        dbRef.child(trackId).setValue(tracking);
        // add value listener
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   if(dataSnapshot.exists() && dataSnapshot != null) {

                    for (DataSnapshot trackingSnapshot: dataSnapshot.getChildren()) {
                        Tracking tracking1 = trackingSnapshot.getValue(Tracking.class);
                        Log.d(TAG, "DATA CHANGED");
                        Log.d(TAG, tracking1.toString());
                    }

                } else {
                    Log.d(TAG, "NuLLLL");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });       //
    }


    /*
    public void dbWrite(AppUser appUser) {
        Log.d(TAG,"db write tracking");
        dbRef = FirebaseDatabase.getInstance().getReference("users");
        String trackId = dbRef.push().getKey();
        dbRef.child(trackId).setValue(appUser);
        // add value listener
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppUser user1 = dataSnapshot.getValue(AppUser.class);
                for (DataSnapshot msgSnapshot: dataSnapshot.getChildren()) {
                    String email = (String) msgSnapshot.child("email").getValue();
                    String token = (String) msgSnapshot.child("token").getValue();
                    Log.d(TAG, "DATA CHANGED");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });       //
    }
*/
    public void dbWriteToken(String email, String token) {
        Log.d(TAG,"db write fb appUser tooooken tracking");
        dbRef = FirebaseDatabase.getInstance().getReference("tokenRegister");
        String uId = dbRef.push().getKey();

        dbRef.child(uId).child("token").setValue(token);
        dbRef.child(uId).child("email").setValue(token);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tracking user1 = dataSnapshot.getValue(Tracking.class);
                Log.d(TAG,"DATA CHANGED");
                Log.d(TAG,user1.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });       //
    }


    public  void writeUser(AppUser appUser){
        Log.d(TAG,"db write tracking");
        dbRef = FirebaseDatabase.getInstance().getReference("users");
        String trackId = dbRef.push().getKey();
        dbRef.child(trackId).setValue(appUser);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                AppUser user = dataSnapshot.getValue(AppUser.class);
                Log.d(TAG, "user>>>added:" + user.getEmail());
                Log.d(TAG, "user>>>added:" + user.getToken());

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                AppUser newUser = dataSnapshot.getValue(AppUser.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String userKey = dataSnapshot.getKey();
                AppUser user = dataSnapshot.getValue(AppUser.class);
                Log.d(TAG, "user>>>removed:" + user.getEmail());
                Log.d(TAG, "user>>>removed:" + user.getToken());



                // ....
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                AppUser user = dataSnapshot.getValue(AppUser.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }
        };
        dbRef.addChildEventListener(childEventListener);
    }

public void readUsersFromDb(){
    dbRef = FirebaseDatabase.getInstance().getReference("users");
    Query userQuery = dbRef
            .limitToFirst(100);
    // My top posts by number of stars
    userQuery.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot uSnapshot: dataSnapshot.getChildren()) {
                AppUser user = uSnapshot.getValue(AppUser.class);
                int i=0; i= 1+i;
                Log.d(TAG, i + "DATA<<<<loading");

                Log.d(TAG, user.getEmail());
                Log.d(TAG, user.getToken());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    });


}


    public void readTracksFromDb(){
        dbRef = FirebaseDatabase.getInstance().getReference("tracks");
        Query trackingQuery = dbRef
                .limitToFirst(100);
        // My top posts by number of stars
        trackingQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot uSnapshot: dataSnapshot.getChildren()) {
                    Tracking tracking = uSnapshot.getValue(Tracking.class);
                     Log.d(TAG, "DATA<<<<loading");

                    Log.d(TAG, tracking.getEstimatedTime()+" mins");
                    Log.d(TAG, tracking.getDestination().getLat());
                    Log.d(TAG, tracking.getDestination().getLang());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


    }

}





