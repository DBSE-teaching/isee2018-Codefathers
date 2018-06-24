package codefathers.isee.com.tripalert;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    // Database listener

}


