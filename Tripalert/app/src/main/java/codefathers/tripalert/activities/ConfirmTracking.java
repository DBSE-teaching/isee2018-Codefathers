package codefathers.tripalert.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import codefathers.tripalert.R;
import codefathers.tripalert.interfaces.NextStepActivity;
import codefathers.tripalert.models.LogItem;
import codefathers.tripalert.models.LogMessages;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.TrackingStatus;
import codefathers.tripalert.viewModels.ConfirmTrackingVIewModel;

public class ConfirmTracking extends AppCompatActivity implements NextStepActivity {
    private Tracking tracking;
    private ConfirmTrackingVIewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO: Create the UI for this activity that displays the tracking
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_tracking);
        viewModel = ViewModelProviders.of(this).get(ConfirmTrackingVIewModel.class);
        tracking = (Tracking) getIntent().getSerializableExtra("tracking");
        viewModel.setTracking(tracking);
    }

    @Override
    public void onNext(View v) {
        // logic behind the submit
        viewModel.isWritten().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.addSituationLog(new LogItem(TrackingStatus.STARTED,
                            LogMessages.getOnStart(
                                    tracking.getStartingPoint().getAddress(),
                                    tracking.getDestination().getAddress(),
                                    tracking.getEstimatedTime()
                            ))
                    );
                    startActivity(new Intent(ConfirmTracking.this, HomeScreen.class));
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.errorDB), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        viewModel.writeTrackingToDb();


    }

    @Override
    public void onCancel(View v) {

        startActivity(new Intent(ConfirmTracking.this, HomeScreen.class));
    }

    @Override
    public void getData() {

    }


}
