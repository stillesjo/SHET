package shet.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import still.interactive.shet.R;


public class ScrumCardActivity extends ActionBarActivity {

    public static final String COFFEE_CUP = "COFFEE";
    public static final String ESTIMATE_STRING = "ESTIMATION";
    public static final String COFFEE_CUP_TRUE = "TRUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrum_card);
        ActionBar bar = getSupportActionBar();
        bar.hide();

        String estimation = getIntent().getStringExtra(ESTIMATE_STRING);
        boolean coffee = getIntent().getStringExtra(COFFEE_CUP) != null;

        ImageView coffeeImage = (ImageView) findViewById(R.id.coffee_cup);
        coffeeImage.setVisibility(coffee ? ImageView.VISIBLE : ImageView.INVISIBLE);

        if (estimation != null) {
            TextView estimationText = (TextView) findViewById(R.id.estimation_text);
            estimationText.setVisibility(TextView.VISIBLE);
            estimationText.setText(estimation);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrum_card, menu);
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
}
