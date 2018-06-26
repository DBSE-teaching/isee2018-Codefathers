package codefathers.isee.com.tripalert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import codefathers.isee.com.tripalert.Model.AppUser;
import codefathers.isee.com.tripalert.Model.GpsCoordinates;

import static android.content.Intent.EXTRA_TEXT;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private BoundMsgService boundMsgService;
    private Boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set listener to button
        findViewById(R.id.anyText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic("topic");
                //FirebaseMessaging.getInstance().unsubscribeFromTopic(“your_topic”); this to unsubs
                Toast.makeText(getApplicationContext(), "You just did something: Topic Subscribed", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity","onStart called");
        Intent intent = new Intent(this,BoundMsgService.class);
        //start service with binding
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        isBound=true;
    }

    public void sendBroadcast(View view) {
        EditText editText = (EditText) findViewById(R.id.timeText);
        String message = editText.getText().toString();

        String textMessage = message+"ups gooooooood";
        Intent sendIntent = new Intent(this, NotificationService.class);
        sendIntent.setAction(Intent.ACTION_MAIN);
        sendIntent.putExtra(EXTRA_TEXT, textMessage);
        sendIntent.setType("text/plain");

        //intent.putExtra(EXTRA_MESSAGE, message);
        Log.d("Intent nio","about to call inten11");
        sendBroadcast(sendIntent);

    }

    public void sendNotification(View view) {
        final String TITLE = "codefathers.isee.com.tripalert.extra.TITLE";
        final String BODY = "codefathers.isee.com.tripalert.extra.BODY";

        EditText editText = (EditText) findViewById(R.id.timeText);
        String message = editText.getText().toString();

        String textMessage = message+"ups gooooooood";
        Intent sendIntent = new Intent(this, NotificationService.class);
        sendIntent.setAction(Intent.ACTION_MAIN);
        sendIntent.putExtra(EXTRA_TEXT, textMessage);
        sendIntent.setType("text/plain");


        //intent.putExtra(EXTRA_MESSAGE, message);
        Log.d("Intent nio","about to call inten11");

        startService(sendIntent);

        Context context = getApplicationContext();
        CharSequence text = "Hello toast hoho!";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(this, text, duration).show();

    }



    public void sendToast(View view) {

        EditText editText = (EditText) findViewById(R.id.timeText);
        Log.d("sendToast", "eof2222 ");
        String message = editText.getText().toString();

        String title = "gooooooood";
        String body = "bodycontents";

        Log.d("sendToast", "eof333 ");
        NotificationService.sendToast(this, title, body);

    }

    public void sendToast2(View view) {
        //Context context;
        final String TITLE = "codefathers.isee.com.tripalert.extra.TITLE";
        final String BODY = "codefathers.isee.com.tripalert.extra.BODY";

        Log.d("sendToast", "eof1111 ");

        EditText editText = (EditText) findViewById(R.id.timeText);
        Log.d("sendToast", "eof2222 ");
        String message = editText.getText().toString();

        String textMessage = message+"ups gooooooood";

        Log.d("sendToast", "eof333 ");
        NotificationService.startActionToast(this, TITLE, BODY);

    }

    public void setupTracking(View view) {
        Intent intent = new Intent(this, GpsTrackActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }

    public void sendDestinationSetting(View view) {
        //Intent intent = new Intent(this, GpsTrackActivity.class);
        Button mButton = (Button) findViewById(R.id.btnCoord);
        final EditText mLongitude = (EditText) findViewById(R.id.longText);
        final EditText mLatitude = (EditText) findViewById(R.id.latText);

        DataStoreService mDataStoreService = new DataStoreService();
        Double lati = Double.parseDouble(mLatitude.getText().toString());
        Double longi = Double.parseDouble(mLongitude.getText().toString());

        Log.d("Dest Longitude", longi.toString());
        Log.d("Dest Latitude", lati.toString());

        GpsCoordinates gps = new GpsCoordinates(longi.toString(), lati.toString());
        AppUser appUser = new AppUser("alab23");
        mDataStoreService.dbWrite(gps, appUser);
    }

    public void sendTimeEst(View view) {
        //Intent intent = new Intent(this, GpsTrackActivity.class);
        Button mButton = (Button) findViewById(R.id.btnTime);
        final EditText mTimeEst = (EditText) findViewById(R.id.timeText);
        Log.d("Estimated Time", mTimeEst.getText().toString());
    }

    public void sendText(View view) {
        //Intent intent = new Intent(this, GpsTrackActivity.class);
        final EditText anyText = (EditText) findViewById(R.id.anyText);
        String dText = anyText.getText().toString();
        Log.d("Message here", anyText.getText().toString());
        DataStoreService mDataStoreService = new DataStoreService();
        GpsCoordinates gps = new GpsCoordinates("999longiz", "777lat");
        AppUser appUser = new AppUser("alab23");
        mDataStoreService.dbWrite(gps, appUser);
    }

    public void sendMessageDD(View view) {

         EditText editMessage = (EditText) findViewById(R.id.anyText);
         TextView textView = (TextView) findViewById(R.id.dispText);

        //get text from edittext and convert it to string
        String messageString = editMessage.getText().toString();

        //set string from edittext to textview
        textView.setText(messageString);

        //clear edittext after sending text to message
        editMessage.setText("");


    }

    public void sendMessage(View view) {

        EditText editMessage = (EditText) findViewById(R.id.anyText);
        TextView textView = (TextView) findViewById(R.id.dispText);

        DataStoreService mDataStoreService = new DataStoreService();

        mDataStoreService.writeMessageDb(editMessage, textView);

        //get text from edittext and convert it to string

    }

    ////////BINDING SERvICES

    private boolean mShouldUnbind;
    String TAG = "MainActivity";

    private static final int PERMISSIONS_REQUEST = 1;
    /////////////////


    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            BoundMsgService.MsgBinder binder = (BoundMsgService.MsgBinder)service;
            boundMsgService = binder.getService();
            //isBound =True
            Toast.makeText(MainActivity.this, "main_service_connected",
                    Toast.LENGTH_SHORT).show();
        }
        public void onServiceDisconnected(ComponentName className) {
            boundMsgService = null;
            Toast.makeText(MainActivity.this, "R.string.local_service_disconnected",
                    Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {

        if (bindService(new Intent(MainActivity.this, FbMessagingService.class),
                mConnection, Context.BIND_AUTO_CREATE)) {
            mShouldUnbind = true;
        } else {
            Log.e("MY_APP_TAG", "Error: The requested service doesn't " +
                    "exist, or this client isn't allowed access to it.");
        }
    }

    void doUnbindService() {
        if (mShouldUnbind) {
            // Release information about the service's state.
            unbindService(mConnection);
            mShouldUnbind = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }


    public void testBroadcast(View v){

        boundMsgService.sendBroadcast(new Intent(MainActivity.this, FbMessagingService.class));

    }

    public void displayTrackingNotice(Context context){
        Log.d(TAG,"diplay tracking notification");
        boundMsgService.testBroadcast(context);
        //sendBroadcast(new Intent(MainActivity.this, BoundMsgService.class));

    }



}


