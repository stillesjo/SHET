package com.example.stillesjo.myapplication;

/**
 * Created by stillesjo on 14-11-08.
 */
public class ScrumMember {
    public static final String NO_ESTIMATE = "No Estimate";
    private boolean mCurrentUser = false;
    private String mUsername;
    private String mAddress;
    private String mEstimation;

    public ScrumMember(String username, String address) {
        mUsername = username;
        mAddress = address;
        mEstimation = NO_ESTIMATE;
    }
    public ScrumMember(String username, String address, boolean currentUser) {
        this(username, address);
        mCurrentUser = currentUser;
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

    public String getAddress() {
        return mAddress;
    }

    public boolean isCurrentUser() {
        return mCurrentUser;
    }

    public void setEstimation(String estimation) {
        this.mEstimation = estimation;
    }
}
