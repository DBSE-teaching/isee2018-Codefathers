package codefathers.tripalert.services;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import codefathers.tripalert.models.AppUser;

import static android.content.ContentValues.TAG;

public class MyMessagingService extends FirebaseMessagingService {
    String TAG = "MESSAGING SERVICE";
    private FirebaseAuth mAuth;
    private AppUser aUser;
    private FirebaseUser mUser;
    private DatabaseService databaseService;

    @Override
    public void onCreate() {

        super.onCreate();


        }
//

}