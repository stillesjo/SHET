package shet.activities;

import android.app.Fragment;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
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

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mDrawerChoices;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        AboutApplicationFragment startFragment = AboutApplicationFragment.newInstance(getIntent().getExtras());
        startFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().add(R.id.drawer_frame,startFragment).commit();

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setHomeButtonEnabled(true);

        mDrawerChoices = getResources().getStringArray(R.array.choice_array);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        mDrawerList.setOnItemClickListener(new DrawerClickListener());


        SortedMap<String, Integer> sortedMap = new TreeMap<String, Integer>();
        sortedMap.put(getResources().getString(R.string.about_application),new Integer(R.drawable.ic_action_about));
        sortedMap.put(getResources().getString(R.string.estimate_yourself),new Integer(R.drawable.ic_action_person));
        sortedMap.put(getResources().getString(R.string.estimate_others), new Integer(R.drawable.ic_action_group));

        DrawerAdapter adapter = new DrawerAdapter(getLayoutInflater(),sortedMap, getResources().getStringArray(R.array.choice_array));

        mDrawerList.setAdapter(adapter);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // return super.onPrepareOptionsMenu(menu);
        if (super.onPrepareOptionsMenu(menu))
            return true;
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_add_members).setVisible(!drawerOpen);
        menu.findItem(R.id.action_estimate).setVisible(!drawerOpen);
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
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            Log.i("XALEOSK","Stuff happens here...");
            return true;
        }
        Log.i("XALEOSK", "Stuff DOESN'T happen here");
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
        switch(position) {
            case 0:
                Log.i(MAIN_ACTIVITY_TAG,"Starting estimate self fragment");
                startFragment(SoloEstimationFragment.newInstance(getIntent().getExtras()));
                break;
            case 1:
                Log.i(MAIN_ACTIVITY_TAG,"Startime estimate with others fragment.");
                break;
            case 2:
                Log.i(MAIN_ACTIVITY_TAG,"Starting about application fragment.");
                startFragment(AboutApplicationFragment.newInstance(getIntent().getExtras()));
                break;
            default:
                Log.e(MAIN_ACTIVITY_TAG,"Unknown or unimplemented list item: " + Integer.toString(position));
                break;
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);

    }


}
