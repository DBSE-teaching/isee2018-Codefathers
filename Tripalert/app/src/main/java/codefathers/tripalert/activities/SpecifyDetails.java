package codefathers.tripalert.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import codefathers.tripalert.R;
import codefathers.tripalert.interfaces.NextStepActivity;
import codefathers.tripalert.models.Location;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.User;

public class SpecifyDetails extends AppCompatActivity implements NextStepActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_details);
    }

    @Override
    public void onNext(View v) {

        Intent intent = new Intent(SpecifyDetails.this, SelectContacts.class);
        Location loc = new Location("0000","0000");
        loc.setAddress("hello motherfucka");
        Location loc2 = new Location("000","0000");
        loc2.setAddress("22222");
        User creator = new User("39473957403","mitsaras","mitsos14@hotmail.gr");
        Tracking temp = new Tracking(loc,loc2,0,12, creator);
        Bundle bundle = new Bundle();
        bundle.putSerializable("tracking",temp);
        intent.putExtras(bundle);
        startActivity(intent);
    }

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

    }
}
