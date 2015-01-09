package timx.com.temperaturesensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import timx.com.temperaturesensor.UI.BluetoothDeviceAdapter;
import timx.com.temperaturesensor.UI.BluetoothDeviceModel;
import timx.com.temperaturesensor.UI.DividerItemDecorator;


public class MainActivity extends BaseActivity {
    private RecyclerView bluetoothRecylerView;
    private RecyclerView.Adapter bluetoothRecylerViewAdapter;
    private RecyclerView.LayoutManager bluetoothRecylerViewManager;

    private List<BluetoothDeviceModel> mBluetoothDevices = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bluetoothRecylerView = (RecyclerView) findViewById(R.id.recylerView_bluetooth_device);
        bluetoothRecylerView.setHasFixedSize(true);
        bluetoothRecylerView.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));
        bluetoothRecylerViewManager = new LinearLayoutManager(this);
        bluetoothRecylerView.setLayoutManager(bluetoothRecylerViewManager);
        bluetoothRecylerViewAdapter = new BluetoothDeviceAdapter(mBluetoothDevices);
        bluetoothRecylerView.setAdapter(bluetoothRecylerViewAdapter);
        bluetoothRecylerView.setItemAnimator(new DefaultItemAnimator());

        bluetoothRecylerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });


        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                BluetoothDeviceModel newDevice = new BluetoothDeviceModel(device.getName(), device.getAddress());
                mBluetoothDevices.add(newDevice);
            }

            bluetoothRecylerViewAdapter.notifyItemInserted(mBluetoothDevices.size());
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
