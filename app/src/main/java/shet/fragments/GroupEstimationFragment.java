package shet.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

import shet.activities.MainActivity;
import shet.network.ConnectionHandler;
import still.interactive.shet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupEstimationFragment extends BaseFragment implements ConnectionHandler.ScrumServiceListener {

    private static final String GROUP_ESTIMATION_TAG = GroupEstimationFragment.class.getName();
    private ListView mListView;

    ConnectionHandler mConnectionHandler;

    public static GroupEstimationFragment newInstance(Bundle extras) {
        GroupEstimationFragment fragment = new GroupEstimationFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    public GroupEstimationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_estimation, container, false);

        // Get username from preferences
        SharedPreferences prefs = getActivity().getSharedPreferences(MainActivity.SHARED_PREFERENCES, 0);
        String username = prefs.getString(MainActivity.PREFERENCE_USERNAME, "Username not found :(");

        // Username will be used as preference in the connection handler
        mConnectionHandler = new ConnectionHandler((NsdManager) getActivity().getApplicationContext().getSystemService(Context.NSD_SERVICE), username, this);
        try {
            mConnectionHandler.initializeAndStartService();
        } catch (IOException e) {
            Log.e(GROUP_ESTIMATION_TAG, "IO Exception why trying to initialize NSD Service", e);
        }

        // Find list view and set adapter
        mListView = (ListView) view.findViewById(R.id.group_estimation_list);
        mListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[] {"List item 1","List item 2"} ));

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mConnectionHandler.pauseService();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mConnectionHandler.pauseService();
    }

    @Override
    public void connectionSuccessful(String user) {

    }

    @Override
    public void connectionFailed(String user) {

    }

    @Override
    public void foundUser(String user) {

    }

    @Override
    public void lostUser(String user) {

    }
}
