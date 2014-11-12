package shet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stillesjo.shet.R;

import java.util.List;

import shet.ScrumMember;

/**
 * Created by stillesjo on 14-11-08.
 */
public class ScrumAdapter extends BaseAdapter {

    private boolean mEstimationOngoing;
    private final LayoutInflater mInflater;
    private List<ScrumMember> mScrumMembers;

    public ScrumAdapter(LayoutInflater inflater, List<ScrumMember> scrumMemberList) {
        super();
        mScrumMembers = scrumMemberList;
        mInflater = inflater;
        mEstimationOngoing = false;
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

        View returnView = convertView == null ? mInflater.inflate(R.layout.scrum_member_item, parent, false) : convertView;
        TextView text = (TextView) returnView.findViewById(R.id.scrum_member_name);
        TextView scrumPoints = (TextView) returnView.findViewById(R.id.scrum_menu_points);
        ProgressBar bar = (ProgressBar) returnView.findViewById(R.id.scrum_progressBar);
        ImageView syncImg = (ImageView) returnView.findViewById(R.id.scrum_sync_img);

        // Set member text
        if (member.isCurrentUser())
            text.setText(member.getName() + " (me) ");
        else
            text.setText(member.getName());

        // Set scrum point text
        scrumPoints.setText(mEstimationOngoing ? member.getEstimation() : ScrumMember.NO_ESTIMATE);

        // Check whether the member is in sync
        boolean inSync = member.inSync();
        bar.setVisibility(inSync || !mEstimationOngoing ?         ProgressBar.INVISIBLE : ProgressBar.VISIBLE);
        syncImg.setVisibility( !inSync || !mEstimationOngoing?    ImageView.INVISIBLE :   ImageView.VISIBLE);

        return returnView;
    }


    public void add(ScrumMember scrumMember) {
        mScrumMembers.add(scrumMember);
    }

    public void updateEstimation(String estimation, String address) {
        for (ScrumMember member : mScrumMembers) {
            if (member.getAddress().equals(address)) {
                member.setEstimation(estimation);
                break;
            }
        }
        notifyDataSetChanged();
    }



    public void setEstimationOngoing(boolean ongoing) {
        mEstimationOngoing = ongoing;
    }
}
