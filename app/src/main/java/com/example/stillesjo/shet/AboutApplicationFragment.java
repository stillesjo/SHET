package com.example.stillesjo.shet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutApplicationFragment extends BaseFragment {
    private static final String ABOUT_TAG = "SHET.AboutApplicationFragment";

    public static AboutApplicationFragment newInstance(Bundle bundle) {
        AboutApplicationFragment fragment = new AboutApplicationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public AboutApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(ABOUT_TAG,"On create view");
        return inflater.inflate(R.layout.fragment_about_application, container, false);
    }





}
