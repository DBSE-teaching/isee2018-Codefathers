package codefathers.tripalert.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import codefathers.tripalert.R;
import codefathers.tripalert.interfaces.NextStepActivity;

public class SpecifyDetails extends AppCompatActivity implements NextStepActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_details);
    }

    @Override
    public void onNext(View v) {
        startActivity(new Intent(SpecifyDetails.this, ConfirmTracking.class));
    }

    @Override
    public void onCancel(View v) {
        startActivity(new Intent(SpecifyDetails.this, HomeScreen.class));
    }

    @Override
    public void getData() {

    }

    @Override
    public void saveData() {

    }
}
