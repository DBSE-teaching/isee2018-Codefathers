package codefathers.isee.com.tripalert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import codefathers.isee.com.tripalert.Model.AppUser;
import codefathers.isee.com.tripalert.Model.GpsCoordinates;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        GpsCoordinates gps = new GpsCoordinates(longi.toString(),lati.toString());
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
        GpsCoordinates gps = new GpsCoordinates("999longiz","777lat");
        AppUser appUser = new AppUser("alab23");
        mDataStoreService.dbWrite(gps, appUser);
    }

}
