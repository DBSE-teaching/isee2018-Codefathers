package codefathers.tripalert.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class Settings extends AppCompatActivity {
    String TAG = "SETTINGS";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();        //show the next button on bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onSignOut(View view) {
        mAuth.signOut();
        Log.d(TAG,"User signed out");
        startActivity(new Intent( Settings.this, PhoneAuthActivity.class));

    }
}
