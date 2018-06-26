package codefathers.isee.com.tripalert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static codefathers.isee.com.tripalert.GeoLocationService.CHANNEL_ONE_ID;

public class FbNotificationManager extends Service {
    public FbNotificationManager() {
    }

    @Override
    public IBinder onBind(Intent intent) { return null;

    }

    //private int NOTIFICATION = R.string.local_service_started;
    String TAG = "Fb Messaging Service";
    private int NOTIFICATION = 12; //R.string.local_service_started;
    private Context mContext;  //this should be replaced by callers context.
    private Context mCtx;
    private static FbNotificationManager mNMInstance;

    private FbNotificationManager(Context context) {
        mContext = context;
    }

    public static synchronized FbNotificationManager getInstance(Context context) {
        if (mNMInstance == null) {
            mNMInstance = new FbNotificationManager(context);
        }
        return mNMInstance;
    }


///


    protected void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "getString(R.string.channel_one)";
            String description = "getString(R.string.channel_one_description)";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ONE_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    ////




    public void displayNotification(String title, String body) {

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(mCtx, FireSettings.CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_tracker)
                            .setContentTitle(title)
                            .setContentText(body);




            Intent resultIntent = new Intent(getApplicationContext(), GpsTrackActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(pendingIntent);

            NotificationManager mNotifyMgr =
                    (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);

            /*
             * The first parameter is the notification id
             * better don't give a literal here (right now we are giving a int literal)
             * because using this id we can modify it later
             * */
            if (mNotifyMgr != null) {
                mNotifyMgr.notify(FireSettings.NOTIFICATION_ID, mBuilder.build());
            }
        }

    }





    ////////////////////////////


  /*

    CharSequence text = "gzzz"; //getText(R.string.local_service_started);

    public void displayNotification(String title, String body) {
        String stop = "stop";
        CharSequence text = "gzzz"; //getText(R.string.local_service_started);
        mContext.registerReceiver(stopReceiver, new IntentFilter(stop));
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext, FireSettings.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_tracker)  // the status icon
                        .setTicker(text)  // the status text
                        .setWhen(System.currentTimeMillis())  // the time stamp
                        .setContentTitle(title)  // the label of the entry
                        .setContentText(text);  // the contents of the entry
                        ////.setContentIntent(pendingIntent); // The intent to send when the entry is click
        startForeground(1,mBuilder.build());

        ////////////

    Intent resultIntent = new Intent(mContext, MainActivity.class);

    PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

    NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        if (mNotifyMgr != null) {
            startForeground(111,mBuilder.build());

        //mNotifyMgr.notify(FireSettings.NOTIFICATION_ID, mBuilder.build());
    } else {
        Log.d("TAG", "NOTIFICATION MANAGER NOT BUILT");
    }


    //startForeground(1, builder.build());
}

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "received stop broadcast");
            // Stop the service when the notification is tapped
            context.unregisterReceiver(stopReceiver);
            context.stopService(intent);
        }
    };




*/
    //////////////port fb messaging
