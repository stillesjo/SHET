package shet.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import shet.fragments.BaseFragment;
import still.interactive.shet.R;


public class MainActivity extends ActionBarActivity implements BaseFragment.OnFragmentInteractionListener {


    public static final String ESTIMATE_RESULT = "ESTIMATE_RESULT";
    private static final String MAIN_ACTIVITY_TAG = "SHET.MainActivity";
    public static final String SILENT_MODE_STRING = "SILENT_MODE";
    public static final String SHARED_PREFERENCES = "scrumhelperestimationtoolsharedpreferences";
    public static final String PREFERENCE_USERNAME = "scrumhelperestimationtoolusername";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, 0);
        String username = prefs.getString(PREFERENCE_USERNAME, null);
        if (username == null) {
            Intent intent = new Intent(this, FirstTimeActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = prefs.edit();
            // Temporary solution in order to not the first time activity pop-up every time
            // Will be used later when we have group estimations.
            editor.putString(PREFERENCE_USERNAME, "TempValue");
            editor.apply();
        } else {
            Log.i(MAIN_ACTIVITY_TAG, "Username wasn't null: " + username);
        }
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
        switch (item.getItemId()) {
//            case R.id.action_debug_reset_name:
//                Not in live use!
//                SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, 0);
//                if (prefs != null) {
//                    SharedPreferences.Editor editor = prefs.edit();
//                    editor.remove(PREFERENCE_USERNAME);
//                    editor.apply();
//                }
//                break;
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onFragmentInteraction(Bundle result) {
        Log.i(MAIN_ACTIVITY_TAG, "onFragmentInteraction(bundle)");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(MAIN_ACTIVITY_TAG, "onFragmentInteraction(uri)");
    }
}