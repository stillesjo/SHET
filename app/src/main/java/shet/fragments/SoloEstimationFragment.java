package shet.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stillesjo.shet.R;

import shet.adapters.EstimationAdapter;

public class SoloEstimationFragment extends BaseFragment {
    private static final String SOLO_TAG = "SHET.SoloEstimationFragment";

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

        ListView list = (ListView)view.findViewById(R.id.estimate_list);
        list.setAdapter(new EstimationAdapter(getActivity().getLayoutInflater()));

        return view;
    }





}
