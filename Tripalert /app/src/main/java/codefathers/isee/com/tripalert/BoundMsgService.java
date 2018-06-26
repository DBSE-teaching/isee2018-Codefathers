package codefathers.isee.com.tripalert;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;

public class BoundMsgService extends Service {
    public BoundMsgService() {
    }

    String TAG = "BoundMessageService";


    private Binder mBinder = new MsgBinder();

    public class MsgBinder extends Binder {
        BoundMsgService getService() {
            return BoundMsgService.this;
        }

    }@Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
            Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return mBinder;
    }


    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    //private final IBinder msgBinder = new BoundMsgService().MsgBinder();

        public void  testBroadcast(Context context) {
            Log.d(TAG, "MSG BOUND OK1");
            FbNotificationManager nManager =FbNotificationManager.getInstance(getApplicationContext());
            Log.d(TAG, "MSG BOUND OK2");
            if(nManager != null) {
                Log.d(TAG, "MSG BOUND OK2.4");
                nManager.createNotificationChannel();
                Log.d(TAG, "MSG BOUND OK3");
                nManager.displayNotification("Send broadcast", "Hello how are you?");
            } else {
                Log.d(TAG,"nullllllll");
            }

    }



    }

