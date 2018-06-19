package codefathers.tripalert;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class PagerAdapter extends FragmentPagerAdapter {

    private int  numOfTabs;

    public PagerAdapter(FragmentManager fm , int numOfTabs) {
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
