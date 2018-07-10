package codefathers.tripalert.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import codefathers.tripalert.adapters.HomesPagerAdapter;
import codefathers.tripalert.R;
import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.models.LogItem;
import codefathers.tripalert.models.TrackingStatus;
import codefathers.tripalert.viewModels.HomeScreenViewModel;


public class HomeScreen extends AppCompatActivity implements MyTracking.OnFragmentInteractionListener, FollowedTrackings.OnFragmentInteractionListener ,SituationLog.OnFragmentInteractionListener{
    public HomeScreenViewModel viewModel;
    String TAG = "HOMESCREEN";
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(HomeScreenViewModel.class);
        updatePreferences();
        ///check authentication status, redirect to login page if not authenticated
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null || mUser.getPhoneNumber().isEmpty()){
            startActivity(new Intent(this, PhoneAuthActivity.class));
        }else{
            AppUser user = new AppUser();
            user.setPhoneNumber(mUser.getPhoneNumber());
            viewModel.setUser(user);
            setContentView(R.layout.activity_home_screen);
            goToSettings();
            makeTabs();
        }
    }

    private void goToSettings(){
            Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
            settingsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewModel.getCreatedTracking().getValue() == null ) {
                        startActivity(new Intent(HomeScreen.this, Settings.class));
                    }else{
                        Toast.makeText(getApplicationContext(), "You can't edit the preferences when you have an ongoing tracking.", Toast.LENGTH_LONG).show();
                    }
                }
            });



    }

    private void makeTabs(){
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab( tabLayout.newTab().setText(R.string.myTrackings));
        tabLayout.addTab( tabLayout.newTab().setText(R.string.followed));
        tabLayout.addTab( tabLayout.newTab().setText("Messages"));
        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final HomesPagerAdapter homesPagerAdapter = new HomesPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
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

    public void onEmergency(View view){
        String creator = viewModel.getCreatedTracking().getValue().getCreator();
        viewModel.changeCreatedTrackingtatus(TrackingStatus.EMERGENCY);
        LogItem logItem = new LogItem(TrackingStatus.EMERGENCY, creator+"is on emergency");
        logItem.setCreator(creator);
        viewModel.addSituationLog(logItem);
    }

    public void updatePreferences(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        viewModel.TIME_LIMIT =  Integer.valueOf(sharedPref.getString("inactivityAllowance","5"));
        viewModel.TIME_LOCATTION_LIMIT =Integer.valueOf(sharedPref.getString("inactivityMeters", "10"));
        viewModel.LOCATION_LIMIT =  Integer.valueOf(sharedPref.getString("reachDestinationRadius", "10"));
        viewModel.HAS_LOCATION_ENABLED = sharedPref.getBoolean("sharedLocation", false);
        viewModel.DELAY_LIMIT = Integer.valueOf(sharedPref.getString("delayedAllowance","0"));
    }
    public void onSignOut(View view) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        if(mAuth.getCurrentUser()==null) {
            startActivity(new Intent(HomeScreen.this, PhoneAuthActivity.class));
        }
    }


}
