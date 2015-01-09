package timx.com.temperaturesensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import timx.com.temperaturesensor.UI.BluetoothDeviceAdapter;
import timx.com.temperaturesensor.UI.BluetoothDeviceModel;
import timx.com.temperaturesensor.UI.DividerItemDecorator;
import timx.com.temperaturesensor.UI.RecyclerItemClickListener;


public class MainActivity extends ActionBarActivity {
    private BluetoothAdapter btAdapter;

    private ProgressBar progressBar_btDiscoveryStatus;
    private RecyclerView bluetoothRecylerView;
    private RecyclerView.Adapter bluetoothRecylerViewAdapter;
    private RecyclerView.LayoutManager bluetoothRecylerViewManager;

    private List<BluetoothDeviceModel> mBluetoothDevices = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar main_toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        if (main_toolbar != null) {
            setSupportActionBar(main_toolbar);
        }

        progressBar_btDiscoveryStatus = (ProgressBar) findViewById(R.id.progressBar_btDiscoveryStatus);

        bluetoothRecylerView = (RecyclerView) findViewById(R.id.recylerView_bluetooth_device);
        bluetoothRecylerView.setHasFixedSize(true);
        bluetoothRecylerView.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));
        bluetoothRecylerViewManager = new LinearLayoutManager(this);
        bluetoothRecylerView.setLayoutManager(bluetoothRecylerViewManager);
        bluetoothRecylerViewAdapter = new BluetoothDeviceAdapter(mBluetoothDevices);
        bluetoothRecylerView.setAdapter(bluetoothRecylerViewAdapter);
        bluetoothRecylerView.setItemAnimator(new DefaultItemAnimator());

        bluetoothRecylerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Log.d("haha", Integer.toString(position));
            }
        }));

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this    .registerReceiver(mBTReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mBTReceiver, filter);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevice = btAdapter.getBondedDevices();

        if (bondedDevice.size() > 0) {
            for (BluetoothDevice device: bondedDevice) {
                BluetoothDeviceModel newDevice = new BluetoothDeviceModel(device.getName(), device.getAddress(), true);
                mBluetoothDevices.add(newDevice);
            }
            bluetoothRecylerViewAdapter.notifyDataSetChanged();
        }
        startBluetoothDiscovery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_refresh) {
            startBluetoothDiscovery();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (!isBTDeviceSame(device)) {
                    BluetoothDeviceModel newDevice = new BluetoothDeviceModel(device, false);
                    mBluetoothDevices.add(newDevice);
                    bluetoothRecylerViewAdapter.notifyItemInserted(mBluetoothDevices.size());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                progressBar_btDiscoveryStatus.setVisibility(View.INVISIBLE);
            }
        }
    };

    private boolean isBTDeviceSame(BluetoothDevice newDevice) {
        for (BluetoothDeviceModel device : mBluetoothDevices) {
            if (device.name.contentEquals(newDevice.getName()) &&
                    device.address.contentEquals(newDevice.getAddress())) {
                return true;
            }
        }

        return false;
    }

    private void startBluetoothDiscovery() {
        progressBar_btDiscoveryStatus.setVisibility(View.VISIBLE);
        if (btAdapter.isDiscovering()) {
            btAdapter.cancelDiscovery();
        }

        btAdapter.startDiscovery();
    }
}
