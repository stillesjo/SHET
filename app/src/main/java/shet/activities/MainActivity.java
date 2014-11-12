package shet.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.stillesjo.shet.R;

import java.util.SortedMap;
import java.util.TreeMap;

import shet.adapters.DrawerAdapter;
import shet.fragments.AboutApplicationFragment;
import shet.fragments.BaseFragment;
import shet.fragments.SoloEstimationFragment;


public class MainActivity extends FragmentActivity implements BaseFragment.OnFragmentInteractionListener {

    public static final int    ESTIMATE_REQUEST = 1;
    public static final int SYNC_REQUEST = 2;

    public static final String ESTIMATE_RESULT = "ESTIMATE_RESULT";
    public static final String SCRUM_SERVICE_NAME = "_scrumestimation._tcp.";
    public static final String USERNAME_KEY = "USERNAME_KEY";
    private static final String MAIN_ACTIVITY_TAG = "SHET.MainActivity";

    private String[] mDrawerChoices;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager_main);
        mViewPager.setAdapter(new ScrumFragmentPagerAdapter(getSupportFragmentManager()));

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }
        };


        mViewPager.setOnPageChangeListener(onPageChangeListener);

        mTitle = mDrawerTitle = getTitle();
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener myListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
        };
        actionBar.addTab(actionBar.newTab().setText(getResources().getString(R.string.estimate_yourself)).setIcon(R.drawable.ic_action_person).setTabListener(myListener));
        actionBar.addTab(actionBar.newTab().setText(getResources().getString(R.string.estimate_others)).setIcon(R.drawable.ic_action_add_group).setTabListener(myListener));

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        AboutApplicationFragment startFragment = AboutApplicationFragment.newInstance(getIntent().getExtras());
        startFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.pager_main,startFragment);



        mDrawerChoices = getResources().getStringArray(R.array.choice_array);



        SortedMap<String, Integer> sortedMap = new TreeMap<String, Integer>();
        sortedMap.put(getResources().getString(R.string.about_application),new Integer(R.drawable.ic_action_about));
        sortedMap.put(getResources().getString(R.string.estimate_yourself),new Integer(R.drawable.ic_action_person));
        sortedMap.put(getResources().getString(R.string.estimate_others), new Integer(R.drawable.ic_action_group));


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


    private class DrawerClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void startFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.drawer_frame,fragment).commit();
    }

    private void selectItem(int position) {
        Log.i("XALEOS","Got position: " + Integer.toString(position));
    }

    private class ScrumFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public ScrumFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return SoloEstimationFragment.newInstance(getIntent().getExtras());
                case 1:
                    return AboutApplicationFragment.newInstance(getIntent().getExtras());
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
    }


}
