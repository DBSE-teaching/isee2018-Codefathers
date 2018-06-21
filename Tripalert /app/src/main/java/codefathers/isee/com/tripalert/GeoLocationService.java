package codefathers.isee.com.tripalert;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



        import android.app.NotificationChannel;
        import android.app.NotificationManager;
        import android.app.Service;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Build;
        import android.os.IBinder;
//above 3 are the original import
        import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationCallback;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationResult;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.location.LocationSettingsRequest;
        import com.google.android.gms.location.LocationSettingsResponse;
        import com.google.android.gms.location.SettingsClient;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import android.app.PendingIntent;
        import android.app.Service;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.Manifest;
        import android.os.IBinder;
        import android.support.v4.app.NotificationCompat;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;

public class GeoLocationService extends Service {
        private static final String TAG = codefathers.isee.com.tripalert.GpsTrackService.class.getSimpleName();
        //Set the channel’s ID//
        public static final String CHANNEL_ONE_ID = "com.joal.myapplication.eins";

        private LocationCallback mLocationCallback;

        public GeoLocationService() {
        }

        @Override
        public IBinder onBind(Intent intent) {
                return null;
        }

        @Override
        public void onCreate() {
                super.onCreate();
                //if (mRequestingLocationUpdates) {
                //   startLocationUpdates();
        }


        ///////////////
        protected void createLocationRequest() {
                LocationRequest mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(10000);
                mLocationRequest.setFastestInterval(5000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                        .addLocationRequest(mLocationRequest);


                SettingsClient client = LocationServices.getSettingsClient(this);
                Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

                //check if satisfied
                Task<LocationSettingsResponse> result =
                        LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

                //LocationSettingsResponse locationSettingsResponse = new LocationSettingsResponse();
                //locationSettingsResponse.getLocationSettingsStates();

                //implement the LocationCallback interfa
                mLocationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                                if (locationResult == null) {
                                        return;
                                }
                                for (Location location : locationResult.getLocations()) {
                                        // Update UI with location data
                                        // ...
                                }
                        }

                        ;
                };


        }
}