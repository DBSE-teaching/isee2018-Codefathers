package codefathers.isee.com.tripalert;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;


public class FbNotificationManager {

    private Context mCtx;
    private static FbNotificationManager mInstance;

    private FbNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized FbNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FbNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx, FireSettings.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_tracker)
                        .setContentTitle(title)
                        .setContentText(body);
        Intent resultIntent = new Intent(mCtx, MainActivity.class);

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

