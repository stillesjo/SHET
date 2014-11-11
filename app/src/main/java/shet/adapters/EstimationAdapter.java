package shet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stillesjo.shet.R;

/**
 * Created by stillesjo on 14-11-11.
 */
public class EstimationAdapter extends BaseAdapter{

    private final String[] mEstimations;
    private final LayoutInflater mInflater;

    public EstimationAdapter(LayoutInflater inflater) {
        mEstimations = new String[] {"hej","svejs"};
        mInflater = inflater;
    }

    @Override
    public int getCount() {
        return mEstimations.length;
    }

    @Override
    public Object getItem(int position) {
        return mEstimations[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = convertView == null ? (TextView) mInflater.inflate(R.layout.estimation_item, parent, false) : (TextView) convertView;
        view.setText(mEstimations[position]);
        return view;
    }
}
