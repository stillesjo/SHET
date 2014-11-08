package com.example.stillesjo.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity implements View.OnClickListener{

    static final int    ESTIMATE_REQUEST = 1;
    static final String ESTIMATE_RESULT = "ESTIMATE_RESULT";

    private ScrumAdapter mScrumAdapter;
    private HashMap<String, String> mAddressNameHash;
    private String mAddress;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add listener to button
        Button estimateButton = (Button) findViewById(R.id.choose_estimation_button);
        estimateButton.setOnClickListener(this);

        // Initiate hash
        mAddressNameHash = new HashMap<String, String>();

        // Create a list view with an empty adapter
        ListView memberList = (ListView) findViewById(R.id.scrum_member_list);
        ArrayList<ScrumMember> members = new ArrayList<ScrumMember>();
        members.add(new ScrumMember("a", "123123"));
        members.add(new ScrumMember("b", "123123"));
        members.add(new ScrumMember("c", "123123"));

        mScrumAdapter = new ScrumAdapter(getLayoutInflater(),members);
        memberList.setAdapter(mScrumAdapter);

        // Adds user to list
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
        addNewUser(mUsername, mAddress);
    }

    private void addNewUser(String username, String address) {
        if (mAddressNameHash.containsKey(address)) {
            Log.e(getClass().getName(), String.format("Found duplicates of users in the hash. Name: %s, Address: %s",username, address));
        } else {
            mAddressNameHash.put(address, username);
            //mScrumAdapter.add(username);
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
            } else {
               Log.e(getClass().getName(),"data is null!");
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        intent = new Intent(this, ChooseEstimationActivity.class);
        startActivityForResult(intent, ESTIMATE_REQUEST);
    }
}
