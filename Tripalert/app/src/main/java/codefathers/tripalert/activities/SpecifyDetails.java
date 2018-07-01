package codefathers.tripalert.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import codefathers.tripalert.R;
import codefathers.tripalert.interfaces.NextStepActivity;
import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.services.DatabaseService;

public class SpecifyDetails extends AppCompatActivity implements NextStepActivity{

    private Tracking tracking;
    String TAG = "SPECIFY DETAILS";
    DatabaseService databaseService;


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
        //destination get  input from user
        tracking = new Tracking();
        final EditText start = (EditText) findViewById(R.id.editLocation);
        String Coord = start.getText().toString();
        String[] CoordArr =Coord.split(",");
        String longitude = CoordArr[0];
        String latitude = CoordArr[1];
        Location coordinates = new Location(longitude,latitude);
        tracking.setDestination(coordinates);

        //destination get  input from user
        final EditText addr = (EditText) findViewById(R.id.editStartingPoint);
        String address = start.getText().toString();
        tracking.setStartingPoint(coordinates);
        //estimated time
        final EditText estTimeText = (EditText) findViewById(R.id.estTime);
        Integer estimatedTime = Integer.parseInt(estTimeText.getText().toString());
        tracking.setEstimatedTime(estimatedTime);
        //check if every input is specified
        if(tracking != null ){
            //saveInDB
            databaseService = new DatabaseService();
            databaseService.writeTracking(tracking);
            //create the intent and pass the data.
            Intent intent = new Intent(SpecifyDetails.this, SelectContacts.class);
            intent.putExtras(makeBundle(tracking));
            startActivity(intent);

        }else{
            //show a relative message.
            String msg = getString(R.string.cannotProcceedDetails);
            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

     private  Bundle makeBundle(Tracking tracking){
         //todo: pass the user object from homescreen.
         AppUser creator = new AppUser("39473957403","mitsaras","mitsos14@hotmail.gr");
         //Tracking temp = new Tracking(startingPoint,destination,0,estimatedTime, creator);
         Tracking temp = new Tracking();
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

     */
    private void setDestination(){

      }

    /**
     * function to call in order to set the Starting Point
        */
    private void setStartingPoint(Tracking tracking){
   }

    /**
     * function to call in order to set the estimated Time
     * @param minutes
     */
    private void setEstimatedTime(int minutes){

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
        //testing data list fetch

 //TODO
    }

    @Override
    public void saveData() {
        // im not sure if we are going to use this, only if a back button is impleemnted

    }
}
