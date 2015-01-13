package timx.com.temperaturesensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import timx.com.temperaturesensor.Connectivity.Bluetooth.BTServiceConstant;
import timx.com.temperaturesensor.Connectivity.Bluetooth.BluetoothService;


public class TemperatureActivity extends ActionBarActivity {
    private String btAddress = "";

    private BluetoothAdapter mBTAdapter = null;
    private BluetoothService mBTService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        Intent intent = getIntent();
        btAddress = intent.getStringExtra(MainActivity.ACTIVITY_BT_ADDRESS);

        Toolbar main_toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        if (main_toolbar != null) {
            main_toolbar.setNavigationIcon(R.drawable.action_back);
            main_toolbar.setSubtitle(btAddress);
            setSupportActionBar(main_toolbar);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mBTService == null) {
            mBTService = new BluetoothService(this, btHandler);
            connectDevice(btAddress, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mBTService != null) {
            if (mBTService.getState() == BluetoothService.STATE_NONE) {
                mBTService.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBTService != null) {
            mBTService.stop();
        }
    }

    private final Handler btHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case BTServiceConstant.MESSAGE_STATE_CHANGE:
                {
                    switch (msg.arg1) {
                        case BluetoothService.STATE_NONE:
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Log.d("hehe", "connecting");
                            break;
                        case BluetoothService.STATE_CONNECTED:
                            Log.d("hehe", "con");
                            break;
                    }
                }
                    break;
                case BTServiceConstant.MESSAGE_WRITE:
                    break;
                case BTServiceConstant.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    break;
                case BTServiceConstant.MESSAGE_DEVICE_NAME:
                    Log.d("hehe", "name");
                    break;
                case BTServiceConstant.MESSAGE_TOAST:
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_temperature, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reconnect) {
            return true;
        } else if (id == R.id.action_disconnect) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendData(byte[] data) {
        if (data.length > 0) {
            mBTService.write(data);
        }
    }

    private void connectDevice(String address, boolean secure) {
        BluetoothDevice device = mBTAdapter.getRemoteDevice(address);
        mBTService.connect(device, secure);
    }
}
