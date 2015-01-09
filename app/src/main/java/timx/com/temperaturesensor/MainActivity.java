package timx.com.temperaturesensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Set;


public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView bluetoothRecylerView;
    private RecyclerView.Adapter bluetoothRecylerViewAdapter;
    private RecyclerView.LayoutManager bluetoothRecylerViewManager;

    private static final int DATASET_COUNT = 10;
    private String[] mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bluetoothRecylerView = (RecyclerView) findViewById(R.id.recylerView_bluetooth_device);
        bluetoothRecylerView.setHasFixedSize(true);
        bluetoothRecylerViewManager = new LinearLayoutManager(this);

        mDataSet = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataSet[i] = "This is " + i;
        }

        bluetoothRecylerView.setLayoutManager(bluetoothRecylerViewManager);
        bluetoothRecylerViewAdapter = new BluetoothDeviceAdapter(mDataSet);
        bluetoothRecylerView.setAdapter(bluetoothRecylerViewAdapter);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                Log.d("Temp", device.getName() + " " + device.getAddress());
            }
        }

    }

    @Override protected int getLayoutResource() {
        return R.layout.activity_main;
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
}
