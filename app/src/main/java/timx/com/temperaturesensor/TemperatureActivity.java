package timx.com.temperaturesensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import timx.com.temperaturesensor.Connectivity.Bluetooth.BTServiceConstant;
import timx.com.temperaturesensor.Connectivity.Bluetooth.BluetoothService;


public class TemperatureActivity extends ActionBarActivity {
    private String btAddress = "";

    private BluetoothAdapter mBTAdapter = null;
    private BluetoothService mBTService = null;

    private LineChart chart_temperature;

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

        chart_temperature = (LineChart) findViewById(R.id.chart_temperature);
        chart_temperature.setStartAtZero(true);
        chart_temperature.setDrawYValues(false);
        chart_temperature.setDrawXLabels(false);
        chart_temperature.setDrawYLabels(false);
        chart_temperature.setDrawHorizontalGrid(false);
        chart_temperature.setDrawBorder(false);
        chart_temperature.setDrawLegend(false);
        chart_temperature.setDragEnabled(true);
        chart_temperature.setScaleEnabled(true);
        chart_temperature.setPinchZoom(false);
        chart_temperature.setDrawGridBackground(false);
        chart_temperature.setDrawVerticalGrid(false);

        setData(10, 10);

    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(Integer.toString(i));
        }

        ArrayList<Entry> vals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * 10);
            vals1.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(vals1, "DataSet 1");
        set1.setDrawCubic(true);
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setLineWidth(2f);
        set1.setCircleSize(5f);
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.rgb(104, 241, 175));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1);

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        chart_temperature.setData(data);
        chart_temperature.invalidate();
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
                            Log.d("hehe", "connected");
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
