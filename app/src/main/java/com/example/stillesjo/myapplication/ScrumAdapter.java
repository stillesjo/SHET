package com.example.stillesjo.myapplication;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by stillesjo on 14-11-08.
 */
public class ScrumAdapter extends BaseAdapter{


    private final LayoutInflater mInflater;
    private List<ScrumMember> mScrumMembers;

    public ScrumAdapter(LayoutInflater inflater, List<ScrumMember> scrumMemberList) {
        super();
        mScrumMembers = scrumMemberList;
        mInflater = inflater;
    }

    @Override
    public int getCount() {
        return mScrumMembers.size();
    }

    @Override
    public Object getItem(int position) {
        if (position > mScrumMembers.size())
            return mScrumMembers.get(mScrumMembers.size()-1);
        return mScrumMembers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScrumMember member = (ScrumMember) getItem(position);

        View returnview = convertView == null ? mInflater.inflate(R.layout.scrum_member_item, parent, false) : convertView;
        TextView text = (TextView) returnview.findViewById(R.id.scrum_member_name);
        TextView scrumPoints = (TextView) returnview.findViewById(R.id.scrum_menu_points);
        ProgressBar bar = (ProgressBar) returnview.findViewById(R.id.scrum_progressBar);
        ImageView syncImg = (ImageView) returnview.findViewById(R.id.scrum_sync_img);

        // Set member text
        text.setText(member.getName());

        // Set scrum point text
        scrumPoints.setText(member.getEstimation());

        boolean inSync = member.inSync();
        bar.setVisibility(inSync ? ProgressBar.INVISIBLE :  ProgressBar.VISIBLE);

        syncImg.setVisibility(inSync ? ImageView.VISIBLE : ImageView.INVISIBLE);

        return returnview;
    }


}
