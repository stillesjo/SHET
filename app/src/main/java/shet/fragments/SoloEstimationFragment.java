package shet.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.stillesjo.shet.R;

import shet.activities.ScrumCardActivity;
import shet.adapters.EstimationAdapter;

public class SoloEstimationFragment extends BaseFragment {
    private static final String SOLO_TAG = "SHET.SoloEstimationFragment";
    private EstimationAdapter mAdapter;

    public static SoloEstimationFragment newInstance(Bundle bundle) {
        SoloEstimationFragment fragment = new SoloEstimationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public SoloEstimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(SOLO_TAG,"On create view");
        View view = inflater.inflate(R.layout.fragment_solo_estimation, container, false);
        setHasOptionsMenu(true);
        ListView list = (ListView)view.findViewById(R.id.estimate_list);

        mAdapter = new EstimationAdapter(getActivity().getLayoutInflater(), getActivity().getResources().getStringArray(R.array.estimation_array));

        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new SoloItemClickListener());

        return view;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_add_members).setVisible(false);
        menu.findItem(R.id.action_estimate).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    private class SoloItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), ScrumCardActivity.class);
            String string = mAdapter.getString(position);
            Log.i("XALEOSK","String: " + string);
            if (string.toUpperCase().indexOf("COFFEE") >= 0) {
                // Start activity with coffee cup
                intent.putExtra(ScrumCardActivity.COFFEE_CUP,"TRUE");
            } else {
                if (string.toLowerCase().indexOf("point") > 0) {
                    string = string.substring(0,string.indexOf(" "));
                }
                // Start activity with string
                intent.putExtra(ScrumCardActivity.ESTIMATE_STRING, string);
            }
            startActivity(intent);
        }
    }

}
