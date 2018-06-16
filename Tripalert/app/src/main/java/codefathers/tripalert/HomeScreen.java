package codefathers.tripalert;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity implements MyTracking.OnFragmentInteractionListener, FollowedTrackings.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        goToSettings();
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab( tabLayout.newTab().setText(R.string.myTrackings));
        tabLayout.addTab( tabLayout.newTab().setText(R.string.followed));
        System.out.print("will this work?");
        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.print("tab selected");
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
        //i have no idea how this works :P but lets hope it does.
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
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


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //replace the bar with the title with the main menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
   public boolean onOptionsItemSelected(MenuItem item) {
        //actions for menu
        switch (item.getItemId()){
            case R.id.settingsMenu:
                //this is how we change from one screen to another)
                Intent intent1 = new Intent(this,Settings.class);
                this.startActivity(intent1);
                return  true;
            case R.id.aboutMenu:
                Intent intent2 = new Intent(this,About.class);
                this.startActivity(intent2);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
}
