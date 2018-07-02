package codefathers.tripalert.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import codefathers.tripalert.R;
import codefathers.tripalert.interfaces.NextStepActivity;
import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.User;

public class SpecifyDetails extends AppCompatActivity implements NextStepActivity{

    private Location destination = null ;
    private Location startingPoint = null ;
    private int estimatedTime = 0 ;

    private TextView tv_StartingPoint, tv_Destination;
    private ImageView iv_StartingPoint, iv_Destination;
    private int PLACE_PICKER_REQUEST_1 = 1;
    private int PLACE_PICKER_REQUEST_2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_details);

        tv_StartingPoint = (TextView) findViewById(R.id.tv_StartingPoint);
        tv_Destination = (TextView) findViewById(R.id.tv_Destination);
        iv_StartingPoint = (ImageView) findViewById(R.id.iv_StartingPoint);
        iv_Destination = (ImageView) findViewById(R.id.iv_Destination);

        iv_StartingPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(SpecifyDetails.this), PLACE_PICKER_REQUEST_1);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        iv_Destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(SpecifyDetails.this), PLACE_PICKER_REQUEST_2);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST_1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(SpecifyDetails.this, toastMsg, Toast.LENGTH_LONG).show();

                String s = place.getLatLng().toString();
                String[] latLng = s.substring(10, s.length() - 1).split(",");
                String LatStartingPoint = latLng[0];
                String LongStartingPoint = latLng[1];
                setStartingPoint(LongStartingPoint, LatStartingPoint, place.getAddress().toString());

                tv_StartingPoint.setText("Starting Point: " + place.getName());
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST_2) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(SpecifyDetails.this, toastMsg, Toast.LENGTH_LONG).show();

                String s = place.getLatLng().toString();
                String[] latLng = s.substring(10, s.length() - 1).split(",");
                String LatDestination = latLng[0];
                String LongDestination = latLng[1];
                setDestination(LongDestination, LatDestination, place.getAddress().toString());

                tv_Destination.setText("Destination: " + place.getName());
            }
        }
    }


    /**
     * event that handles the transition to the next step, and passes data.
     * @param v
     */
    @Override
    public void onNext(View v) {
        //check if every input is specified
        if(destination != null && startingPoint != null && estimatedTime == 0 ){
            //create the intent and pass the data.
            Intent intent = new Intent(SpecifyDetails.this, SelectContacts.class);
            intent.putExtras(makeBundle());
            startActivity(intent);

        }else{
            //show a relative message.
            String msg = getString(R.string.cannotProcceedDetails);
            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

     private  Bundle makeBundle(){
         //todo: pass the user object from homescreen.
         User creator = new User("39473957403","mitsaras","mitsos14@hotmail.gr");
         Tracking temp = new Tracking(startingPoint,destination,0,estimatedTime, creator);
         Bundle bundle = new Bundle();
         bundle.putSerializable("tracking",temp);
        return bundle;
    }

    /**
     * make sure to disable going back.
     */
    @Override
    public void onBackPressed()
    {
        String msg = getString(R.string.cannotProcceedGoBack);
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * function to call in order to set the destination
     * @param lang
     * @param lat
     * @param address
     */
    private void setDestination(String lang, String lat, String address){
        this.destination = new Location(lang, lat);
        this.destination.setAddress(address);
    }

    /**
     * function to call in order to set the Starting Point
     * @param lang
     * @param lat
     * @param address
     */
    private void setStartingPoint(String lang, String lat, String address){
        this.startingPoint = new Location(lang, lat);
        this.startingPoint.setAddress(address);
    }

    /**
     * function to call in order to set the estimated Time
     * @param minutes
     */
    private void setEstimatedTime(int minutes){
        this.estimatedTime = minutes;
    }
    /**
     * event that handles the on back click event.
     * @param v
     */
    @Override
    public void onCancel(View v) {
        Intent intent = new Intent(SpecifyDetails.this, HomeScreen.class);
        startActivity(intent);
    }

    @Override
    public void getData() {

    }

    @Override
    public void saveData() {
        // im not sure if we are going to use this, only if a back button is impleemnted

    }
}
