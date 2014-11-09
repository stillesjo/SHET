package com.example.stillesjo.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final int    ESTIMATE_REQUEST = 1;
    public static final int SYNC_REQUEST = 2;

    public static final String ESTIMATE_RESULT = "ESTIMATE_RESULT";

    private ScrumAdapter mScrumAdapter;
    private HashMap<String, String> mAddressNameHash;
    private String mAddress;
    private String mUsername;
    private ScrumMember mCurrentUser;
    private boolean mEstimationOngoing;
    private Button mEstimateButton;
    private Button mSyncWithNewMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEstimationOngoing = false;

        // Add listener to buttons
        mEstimateButton = (Button) findViewById(R.id.choose_estimation_button);
        mEstimateButton.setOnClickListener(this);

        mSyncWithNewMembers = (Button) findViewById(R.id.scrum_sync_button);
        mSyncWithNewMembers.setOnClickListener(this);

        updateButtons();

        // Initiate hash
        mAddressNameHash = new HashMap<String, String>();

        // Create a list view with a scrum adapter
        ListView memberList = (ListView) findViewById(R.id.scrum_member_list);
        ArrayList<ScrumMember> members = new ArrayList<ScrumMember>();

        mScrumAdapter = new ScrumAdapter(getLayoutInflater(),members);
        memberList.setAdapter(mScrumAdapter);
        memberList.setOnItemClickListener(this);

        // Adds current user to list
        addCurrentUser();
    }

    /**
     * Add the current user to the hash and list of users
     */
    private void addCurrentUser() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        mUsername = adapter.getName();
        mAddress = adapter.getAddress();
        if (mUsername == null)
            mUsername = mAddress;
        mCurrentUser = new ScrumMember(mUsername, mAddress, true);
        addNewUser(mCurrentUser);
    }

    /**
     * Adds a new member to the hash and to the ScrumAdapter
     * @param newMember
     */
    private void addNewUser(ScrumMember newMember) {
        if (mAddressNameHash.containsKey(newMember.getAddress())) {
            Log.e(getClass().getName(), String.format("Found duplicates of users in the hash. Name: %s, Address: %s", newMember.getName(), newMember.getAddress()));
        } else {
            mAddressNameHash.put(newMember.getAddress(), newMember.getName());
            mScrumAdapter.add(newMember);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ESTIMATE_REQUEST) {
            Log.i(this.getClass().getName(),"Got result from estimate request.");
            if (data != null) {
                Log.i(this.getClass().getName(), "Got this result: " + data.getStringExtra(ESTIMATE_RESULT));
                mScrumAdapter.updateEstimation(data.getStringExtra(ESTIMATE_RESULT),mCurrentUser.getAddress());
            } else {
               Log.e(getClass().getName(), "data is null!");
            }
        }
    }



    private void handleEvent(View v) {
        switch (v.getId()) {
            case R.id.choose_estimation_button:
            case R.id.scrum_member_list:
                //if (!mEstimationOngoing)
                //    break;
                if (!mEstimationOngoing) {
                    initiateEstimation();
                    break;
                }
                startEstimationActivity();

                break;
            case R.id.scrum_sync_button:
                startSyncActivity();
                updateButtons();
                break;
            default:
                Log.i(getClass().getName(),"Unknown view for event. Event ID: " + v.getId());

        }
    }

    private void startEstimationActivity() {
        startActivityForResult(new Intent(this, ChooseEstimationActivity.class), ESTIMATE_REQUEST);
    }

    private void startSyncActivity() {
        startActivityForResult(new Intent(this, ScrumSyncActivity.class), SYNC_REQUEST);
    }

    private void updateButtons() {
        mEstimateButton.setEnabled(true);
        mSyncWithNewMembers.setEnabled(!mEstimationOngoing);
    }

    /**
     * Initiate estimation. Will first start a dialog asking the user whether to go ahead and start with the
     */
    private void initiateEstimation() {
        if (!checkConnectedToUsers()) {
            Log.e(getClass().getName(), "Preconditions aren't met, can't start estimation.");
            Toast.makeText(this, "Not connected to any user, cannot start estimation.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mEstimationOngoing) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            mEstimationOngoing = true;
                            initiateEstimation();
                            break;
                        default:
                            Log.i("MainActivity", "Won't start estimation");
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Want to start estimation?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
        } else {
            // Check pre-conditions

            updateButtons();
            startEstimationActivity();
        }
    }

    /**
     * Returns true if you're connected to other users.
     * @return
     */
    private boolean checkConnectedToUsers() {
        return mAddressNameHash.size() > 1;
    }

    @Override
    public void onClick(View v) {
        handleEvent(v);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(getClass().getName(),"onItemClick");
        handleEvent(parent);
    }
}
