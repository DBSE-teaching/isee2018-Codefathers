package codefathers.tripalert.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.signin.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import codefathers.tripalert.adapters.HomesPagerAdapter;
import codefathers.tripalert.R;
import codefathers.tripalert.models.AppUser;
import codefathers.tripalert.models.TrackingStatus;
import codefathers.tripalert.viewModels.HomeScreenViewModel;


public class HomeScreen extends AppCompatActivity implements MyTracking.OnFragmentInteractionListener, FollowedTrackings.OnFragmentInteractionListener{
    public HomeScreenViewModel viewModel;
    String TAG = "HOMESCREEN";
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(HomeScreenViewModel.class);

        ///check authentication status, redirect to login page if not authenticated
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null || mUser.getPhoneNumber().isEmpty()){
            startActivity(new Intent(this, PhoneAuthActivity.class));
        }else{
            AppUser user = new AppUser();
            user.setPhoneNumber(mUser.getPhoneNumber());
            //whatever we need to set we do that here.
            viewModel.setUser(user);
        }
        setContentView(R.layout.activity_home_screen);
        goToSettings();
        makeTabs();

    }

    private void goToSettings(){
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( HomeScreen.this, Settings.class));
            }
        });

    }

    private void makeTabs(){
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab( tabLayout.newTab().setText(R.string.myTrackings));
        tabLayout.addTab( tabLayout.newTab().setText(R.string.followed));
        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final HomesPagerAdapter homesPagerAdapter = new HomesPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(homesPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // the follows sets the naem of the tabs that the addTab failed to do before for some reason;
        tabLayout.getTabAt(0).setText(getResources().getText(R.string.myTrackings));
        tabLayout.getTabAt(1).setText(getResources().getText(R.string.followed));


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
        viewModel.changeCreatedTrackingtatus(TrackingStatus.EMERGENCY);
    }
    public void onFinish(){
        viewModel.changeCreatedTrackingtatus(TrackingStatus.FINISHED);
    }
    public void onDelay(){
        viewModel.changeCreatedTrackingtatus(TrackingStatus.DELAYED);
    }
    public void onAbort(){
        viewModel.changeCreatedTrackingtatus(TrackingStatus.ABORTED);
    }
    public void onNotResponding(){
        viewModel.changeCreatedTrackingtatus(TrackingStatus.NOT_RESPONDING);
    }


    public void onSignOut(View view) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        if(mAuth.getCurrentUser()==null) {
            startActivity(new Intent(HomeScreen.this, PhoneAuthActivity.class));
        }
    }


}
