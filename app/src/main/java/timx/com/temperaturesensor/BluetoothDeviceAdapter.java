package timx.com.temperaturesensor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Lenovo on 9/1/2015.
 */
public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceAdapter.ViewHolder> {
    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public BluetoothDeviceAdapter(String[] myDataset) {
        mDataset = myDataset;
    }


    @Override
    public BluetoothDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        return null;
    }

    @Override
    public void onBindViewHolder(BluetoothDeviceAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
