package shet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stillesjo.shet.R;

import java.util.SortedMap;

/**
 * Created by stillesjo on 14-11-11.
 */
public class DrawerAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private SortedMap<String,Integer> mMap;
    private String[] mSortedKeys;

    public DrawerAdapter(LayoutInflater inflater, SortedMap<String, Integer> map, String[] stringArray) {
        mMap = map;
        mInflater = inflater;
        mSortedKeys = stringArray;
    }

    @Override
    public int getCount() {
        return mSortedKeys.length;
    }

    @Override
    public Object getItem(int position) {
        return mMap.get(mSortedKeys[position]);
    }

    @Override
    public long getItemId(int position) {
        return ((Integer)getItem(position)).longValue();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.drawer_list_item,null);

        ImageView img = (ImageView) view.findViewById(R.id.drawer_image);
        img.setImageResource(((Integer)getItem(position)).intValue());

        TextView text = (TextView) view.findViewById(R.id.drawer_text);
        text.setText(mSortedKeys[position]);

        return view;
    }
}
