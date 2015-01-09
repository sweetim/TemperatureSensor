package timx.com.temperaturesensor;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class TemperatureActivity extends ActionBarActivity {
    private String btAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        Intent intent = getIntent();
        btAddress = intent.getStringExtra(MainActivity.ACTIVITY_BT_ADDRESS);

        Toolbar main_toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        main_toolbar.setNavigationIcon(R.drawable.action_back);
        if (main_toolbar != null) {
            setSupportActionBar(main_toolbar);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
