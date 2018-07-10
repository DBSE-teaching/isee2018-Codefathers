package codefathers.tripalert.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

import codefathers.tripalert.adapters.HomesPagerAdapter;
import codefathers.tripalert.R;
import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.models.LogItem;
import codefathers.tripalert.models.Tracking;
import codefathers.tripalert.models.TrackingStatus;
import codefathers.tripalert.viewModels.HomeScreenViewModel;


public class HomeScreen extends AppCompatActivity implements MyTracking.OnFragmentInteractionListener, FollowedTrackings.OnFragmentInteractionListener, SituationLog.OnFragmentInteractionListener {
    public HomeScreenViewModel viewModel;
    String TAG = "HOMESCREEN";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(HomeScreenViewModel.class);
        updatePreferences();
        setContacts();
        ///check authentication status, redirect to login page if not authenticated
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null || mUser.getPhoneNumber().isEmpty()) {
            startActivity(new Intent(this, PhoneAuthActivity.class));
        } else {
            AppUser user = new AppUser();
            user.setPhoneNumber(mUser.getPhoneNumber());
            viewModel.setUser(user);
            setContentView(R.layout.activity_home_screen);
            goToSettings();
            makeTabs();
        }

        TextView countDown = (TextView) findViewById(R.id.countDown);
        countDown.setText("5");
        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {

                countDown.setText(Long.toString(millisUntilFinished / 1000));
            }
            public void onFinish() {
                findViewById(R.id.overlayEmergency).setVisibility(View.INVISIBLE);
                findViewById(R.id.viewPager).setVisibility(View.VISIBLE);
                countDown.setText("5");
                onEmergency();
            }
        };
        findViewById(R.id.emergencyBtn).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    countDown.setText("5");
                    findViewById(R.id.viewPager).setVisibility(View.INVISIBLE);
                    findViewById(R.id.overlayEmergency).setVisibility(View.VISIBLE);
                    timer.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    timer.cancel();
                    findViewById(R.id.viewPager).setVisibility(View.VISIBLE);
                    findViewById(R.id.overlayEmergency).setVisibility(View.INVISIBLE);
                    countDown.setText("5");
                    return true;
                }

                return true;
            }
        });
        viewModel.getCreatedTracking().observe(this, new Observer<Tracking>() {
                    @Override
                    public void onChanged(@Nullable Tracking tracking) {
                        if (tracking == null) {
                            findViewById(R.id.emergencyBtn).setVisibility(View.INVISIBLE);
                            findViewById(R.id.settingsBtn).setVisibility(View.VISIBLE);
                            findViewById(R.id.signOutBtn).setVisibility(View.VISIBLE);
                        } else {
                            findViewById(R.id.emergencyBtn).setVisibility(View.VISIBLE);
                            findViewById(R.id.settingsBtn).setVisibility(View.INVISIBLE);
                            findViewById(R.id.signOutBtn).setVisibility(View.INVISIBLE);
                            countDown.setText("5");

                        }
                    }
                });
    }

    private void goToSettings() {
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getCreatedTracking().getValue() == null) {
                    startActivity(new Intent(HomeScreen.this, Settings.class));
                } else {
                    Toast.makeText(getApplicationContext(), "You can't edit the preferences when you have an ongoing tracking.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void makeTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.myTrackings));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.followed));
        tabLayout.addTab(tabLayout.newTab().setText("Messages"));
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        final HomesPagerAdapter homesPagerAdapter = new HomesPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(homesPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // the follows sets the naem of the tabs that the addTab failed to do before for some reason;
        tabLayout.getTabAt(0).setText(getResources().getText(R.string.myTrackings));
        tabLayout.getTabAt(1).setText(getResources().getText(R.string.followed));
        tabLayout.getTabAt(2).setText("Messages");


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onEmergency() {
        Tracking tracking = viewModel.getCreatedTracking().getValue();
        String creator = tracking.getCreator();
        String location = "?";
        if(viewModel.getCurrentLocation() !=null){
             location = viewModel.getCurrentLocation();
        }
        viewModel.changeCreatedTrackingtatus(TrackingStatus.EMERGENCY);
        String message ="";
        if(viewModel.HAS_LOCATION_ENABLED) {
            message = creator + "is on emergency on Location: " + location+"  please contact him as soon as possible";
        }else{
            message = creator + "is on emergency please contact him as soon as possible";
        }
        LogItem logItem = new LogItem(TrackingStatus.EMERGENCY, message);

        logItem.setCreator(creator);
        viewModel.addSituationLog(logItem);
    }

    public void updatePreferences() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        viewModel.TIME_LIMIT = Integer.valueOf(sharedPref.getString("inactivityAllowance", "5"));
        viewModel.TIME_LOCATTION_LIMIT = Integer.valueOf(sharedPref.getString("inactivityMeters", "10"));
        viewModel.LOCATION_LIMIT = Integer.valueOf(sharedPref.getString("reachDestinationRadius", "10"));
        viewModel.HAS_LOCATION_ENABLED = sharedPref.getBoolean("sharedLocation", false);
        viewModel.DELAY_LIMIT = Integer.valueOf(sharedPref.getString("delayedAllowance", "0"));
    }

    public void onSignOut(View view) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(HomeScreen.this, PhoneAuthActivity.class));
        }
    }

    private void setContacts(){
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if(hasPhoneNumber > 0)
                {
                    Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[] {id}, null);
                    while(cursor2.moveToNext())
                    {
                        String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        AppUser user = new AppUser(phoneNumber.replaceAll("[\\s\\-()]", ""));
                        user.setUserName(name);
                        user.setChecked(false);
                        viewModel.addContact(user);
                    }
                    cursor2.close();
                }
            }
        }
        cursor.close();

    }


}
