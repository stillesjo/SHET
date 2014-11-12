package shet.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import shet.fragments.AboutApplicationFragment;
import shet.fragments.BaseFragment;
import shet.fragments.GroupEstimationFragment;
import shet.fragments.SoloEstimationFragment;
import still.interactive.shet.R;


public class MainActivity extends FragmentActivity implements BaseFragment.OnFragmentInteractionListener {

    public static final int    ESTIMATE_REQUEST = 1;

    public static final String ESTIMATE_RESULT = "ESTIMATE_RESULT";
    public static final String SCRUM_SERVICE_NAME = "_scrumestimation._tcp.";
    private static final String MAIN_ACTIVITY_TAG = "SHET.MainActivity";

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AboutApplicationFragment startFragment = AboutApplicationFragment.newInstance(getIntent().getExtras());
        startFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.pager_main, startFragment);

        mViewPager = (ViewPager) findViewById(R.id.pager_main);
        mViewPager.setAdapter(new ScrumFragmentPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.pager_slider);
        tabs.setOnPageChangeListener(new ScrumOnPageChangeListener());
        tabs.setViewPager(mViewPager);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // return super.onPrepareOptionsMenu(menu);
        if (super.onPrepareOptionsMenu(menu))
            return true;
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(MAIN_ACTIVITY_TAG,"Got fragment interaction.. yippi...");
    }


    private class ScrumFragmentPagerAdapter extends FragmentPagerAdapter {

        public ScrumFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return SoloEstimationFragment.newInstance(getIntent().getExtras());
                case 1:
                    return GroupEstimationFragment.newInstance(getIntent().getExtras());
                default:
                    Log.i(MAIN_ACTIVITY_TAG, "Unknown choice... " + Integer.toString(position));
                    break;

            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return getResources().getString(R.string.estimate_yourself);
                case 1:
                    return getResources().getString(R.string.estimate_others);
            }
            return "";
        }
    }

    /**
     * Private class, does nothing at the moment
     */
    private class ScrumOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {}

        @Override
        public void onPageScrollStateChanged(int state) {}
    }


}
