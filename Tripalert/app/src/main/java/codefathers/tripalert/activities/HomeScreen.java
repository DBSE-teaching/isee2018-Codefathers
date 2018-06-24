package codefathers.tripalert.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import codefathers.tripalert.adapters.HomesPagerAdapter;
import codefathers.tripalert.R;
import codefathers.tripalert.models.HomeScreenViewModel;

public class HomeScreen extends AppCompatActivity implements MyTracking.OnFragmentInteractionListener, FollowedTrackings.OnFragmentInteractionListener{
    public HomeScreenViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        viewModel = ViewModelProviders.of(this).get(HomeScreenViewModel.class);
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

}
