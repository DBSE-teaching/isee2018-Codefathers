package codefathers.isee.com.tripalert;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import codefathers.isee.com.tripalert.Model.GpsCoordinates;
import codefathers.isee.com.tripalert.Model.AppUser;

import static android.content.ContentValues.TAG;

public class DataStoreService extends Service {
    private DatabaseReference mDatabaseRef;
    
    public DataStoreService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void dbWrite(GpsCoordinates gpsCoordinates, AppUser appUser) {
        Log.d(TAG,"BEFORE CRASH");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
       // mDatabaseRef.child("Moiusers").child("userId").setValue(user);
        GpsCoordinates origin = new GpsCoordinates();
        GpsCoordinates lastKnownLocation = new GpsCoordinates();
        GpsCoordinates destination = new GpsCoordinates();

        mDatabaseRef.child("users").child("userId").setValue(appUser.appUserId);

        mDatabaseRef.child("users").child("userId").child("track").child("destination").child("longitude").setValue(gpsCoordinates.longitude);
        mDatabaseRef.child("users").child("userId").child("track").child("destination").child("latitude").setValue(gpsCoordinates.latitude);
//
        mDatabaseRef.child("users").child("userId").child("track").child("origin").setValue(gpsCoordinates);
       mDatabaseRef.child("users").child("userId").child("track").child("lastKnownLocation").setValue(gpsCoordinates);
        //mDatabaseRef.child("location").child("destination").child("latitude").setValue(mData.latitude);

        //
        Log.d(TAG,"DB WRITE OK");
        ValueEventListener locationListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                GpsCoordinates gps = dataSnapshot.getValue(GpsCoordinates.class);
                //String dummy = dataSnapshot.getValue(String.class);
                //GpsCoordinates destination = dataSnapshot.getValue(GpsCoordinates.class);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
       // mDatabaseRef.addValueEventListener(locationListener);
        //just checking. d 1 line above is ok
        System.out.println(mDatabaseRef.addValueEventListener(locationListener).hashCode());



    }
}