package com.example.stillesjo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ChooseEstimationActivity extends Activity implements AdapterView.OnItemClickListener {

    final static String[] SCRUM_POINTS = {"???","Coffee break","0 points", "1 point","2 points","3 points","5 points","8 points","13 points","20 points","40 points","100 points"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_estimation);
        ListView view = (ListView) findViewById(R.id.scrum_list);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.scrum_menu_item,SCRUM_POINTS);
        view.setAdapter(myAdapter);
        view.setOnItemClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_estimation, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView _view = (TextView) view;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(null,null);
        Parcel parc = Parcel.obtain();
        parc.writeValue("stuff");

        intent.putExtra(MainActivity.ESTIMATE_RESULT,_view.getText());
        setResult(MainActivity.ESTIMATE_REQUEST,intent);

        finish();
    }
}
