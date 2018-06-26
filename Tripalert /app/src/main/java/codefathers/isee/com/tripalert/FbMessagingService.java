package codefathers.isee.com.tripalert;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class FbMessagingService extends FirebaseMessagingService {
    String TAG = "Fb Messaging Service";
    @Override
    public void onCreate() {
        super.onCreate();
    }
//

    public void sendSubscribe(View view) {
        String topic = "notif";
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "SUBSCRIBE ok");
                String msg = "getString(R.string.msg_subscribed)";
                if (!task.isSuccessful()) {
                    msg = "//getString(R.string.msg_subscribe_failed)";
                }
                Log.d(TAG, msg);

            }
        });

        //
        //FirebaseMessaging.getInstance().unsubscribeFromTopic(“your_topic”); this to unsubs
        //oast.makeText(getApplicationContext(), "Topic Subscribed", Toast.LENGTH_LONG).show();
        //FirebaseMessaging.getInstance().unsubscribeFromTopic(“your_topic”); this to unsubs
       }


    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);

        FbNotificationManager.getInstance(this).displayNotification("Send broadcast", "Hello how are you?");
    }
    //

    public void sendNotice(Intent intent) {
        super.sendBroadcast(intent);
       FbNotificationManager nManager = FbNotificationManager.getInstance(this);
       nManager.createNotificationChannel();
       nManager.displayNotification("Send broadcast", "Hello how are you?");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().size() > 0){
            //handle the data message here
        }


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            //getting the title and the body
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

        //then here we can use the title and body to build a notification
        FbNotificationManager.getInstance(getApplicationContext())
                .displayNotification(title, body);

    }
  }

}