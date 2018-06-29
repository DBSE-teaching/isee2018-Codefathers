package codefathers.tripalert.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import codefathers.tripalert.R;
import codefathers.tripalert.interfaces.NextStepActivity;
import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.User;

public class SpecifyDetails extends AppCompatActivity implements NextStepActivity{

    private Location destination = null ;
    private Location startingPoint = null ;
    private int estimatedTime = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_details);

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
