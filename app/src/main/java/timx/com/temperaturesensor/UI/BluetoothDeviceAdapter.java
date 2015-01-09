package timx.com.temperaturesensor.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import timx.com.temperaturesensor.R;

/**
 * Created by Lenovo on 9/1/2015.
 */
public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceAdapter.ViewHolder> {
    private List<BluetoothDeviceModel> mBluetoothDevices;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView_name;
        public TextView mTextView_address;

        public ViewHolder(View v) {
            super(v);
            mTextView_name = (TextView) v.findViewById(R.id.textView_bluetooth_row_name);
            mTextView_address = (TextView) v.findViewById(R.id.textView_bluetooth_row_address);
        }
    }

    public BluetoothDeviceAdapter(List<BluetoothDeviceModel> devices) {
        mBluetoothDevices = devices;
    }

    @Override
    public BluetoothDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bluetooth_row_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BluetoothDeviceAdapter.ViewHolder viewHolder, int i) {
        BluetoothDeviceModel device = mBluetoothDevices.get(i);

        viewHolder.mTextView_name.setText(device.name);
        viewHolder.mTextView_address.setText(device.address);
    }

    @Override
    public int getItemCount() {
        return mBluetoothDevices.size();
    }
}
