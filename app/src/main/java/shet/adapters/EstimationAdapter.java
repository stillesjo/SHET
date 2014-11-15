package shet.adapters;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import still.interactive.shet.R;

/**
 * Created by stillesjo on 14-11-11.
 */
public class EstimationAdapter extends BaseAdapter{

    private final String[] mEstimations;
    private final LayoutInflater mInflater;
    private final Resources mResources;

    public EstimationAdapter(LayoutInflater inflater, String[] strings, Resources resources) {
        mEstimations = strings;
        mInflater = inflater;
        mResources = resources;
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
        if (position % 2 == 0) {
            // FIXME Another color could be wise here. Might look as though the item is selected
            view.setBackgroundColor(mResources.getColor(R.color.estimation_list_secondary_color));
        }
        return view;
    }

    public String getString(int position) {
        if (position > getCount()) {
            return "???";
        }
        return mEstimations[position];
    }
}
