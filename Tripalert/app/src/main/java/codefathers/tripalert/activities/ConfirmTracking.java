package codefathers.tripalert.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import codefathers.tripalert.R;
import codefathers.tripalert.interfaces.NextStepActivity;
import codefathers.tripalert.models.Tracking;

public class ConfirmTracking extends AppCompatActivity implements NextStepActivity{
    private Tracking tracking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_tracking);
        tracking = (Tracking)getIntent().getSerializableExtra("tracking");
    }

    @Override
    public void onNext(View v) {

        // logic behind the submit
        startActivity(new Intent(ConfirmTracking.this, HomeScreen.class ));
    }

    @Override
    public void onCancel(View v) {

        startActivity(new Intent(ConfirmTracking.this, HomeScreen.class ));
    }

    @Override
    public void getData() {

    }

    @Override
    public void saveData() {

    }
}
