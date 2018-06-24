package codefathers.tripalert.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import codefathers.tripalert.activities.FollowedTrackings;
import codefathers.tripalert.activities.MyTracking;

import static android.app.PendingIntent.getActivity;

public class HomesPagerAdapter extends FragmentPagerAdapter {

    private int  numOfTabs;

    public HomesPagerAdapter(FragmentManager fm , int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new MyTracking();
            case 1:
                return new FollowedTrackings();
            default :
                return  null;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
