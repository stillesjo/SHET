package com.example.stillesjo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener{

    static final int    ESTIMATE_REQUEST = 1;
    static final String ESTIMATE_RESULT = "ESTIMATE_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ListView view = (ListView) findViewById(R.id.scrum_list);
//        String[] scrum_points = {"?","c","0", "1","2","3","5","8","13","20","40","100"};
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.scrum_menu_item,scrum_points);
//        view.setAdapter(myAdapter);
//        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView _view = (TextView) view;
//
//                Log.v("Stuff", _view.getText().toString());
//            }
//        });


        Button estimateButton = (Button) findViewById(R.id.choose_estimation_button);
        estimateButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ESTIMATE_REQUEST) {
            Log.i(this.getClass().getName(),"Got result from estimate request.");
            if (data != null) {
                Log.i(this.getClass().getName(), "Got this result: " + data.getStringExtra(ESTIMATE_RESULT));
            } else {
               Log.e(getClass().getName(),"data is null!");
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        intent = new Intent(this, ChooseEstimationActivity.class);
        startActivityForResult(intent, ESTIMATE_REQUEST);
    }
}
