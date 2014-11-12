package shet.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stillesjo.shet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupEstimationFragment extends BaseFragment {

    private ListView mListView;

    public static GroupEstimationFragment newInstance(Bundle extras) {
        GroupEstimationFragment fragment = new GroupEstimationFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    public GroupEstimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_estimation, container, false);

        mListView = (ListView) view.findViewById(R.id.group_estimation_list);
        mListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[] {"List item 1","List item 2"} ));

        return view;
    }


}
