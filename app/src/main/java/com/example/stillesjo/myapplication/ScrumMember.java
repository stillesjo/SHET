package com.example.stillesjo.myapplication;

/**
 * Created by stillesjo on 14-11-08.
 */
public class ScrumMember {
    private static final String NO_ESTIMATE = "No Estimate";
    private String mUsername;
    private String mAddress;
    private String mEstimation;

    public ScrumMember(String username, String address) {
        mUsername = username;
        mAddress = address;
        mEstimation = NO_ESTIMATE;
    }

    public String getName() {
        return mUsername;
    }

    public String getEstimation() {
        return mEstimation;
    }

    public boolean inSync() {
        return true;
    }
}
