package timx.com.temperaturesensor.UI;

import android.bluetooth.BluetoothDevice;

/**
 * ZMP Inc
 * Created by Tim on 09/01/2015.
 */
public class BluetoothDeviceModel {
    public BluetoothDeviceModel(String name, String address, Boolean isPaired) {
        this.name = name;
        this.address = address;
        this.paired = isPaired;
    }

    public BluetoothDeviceModel(BluetoothDevice device, Boolean isPaired) {
        this.name = device.getName();
        this.address = device.getAddress();
        this.paired = isPaired;
    }

    public BluetoothDeviceModel() {}

    public String name;
    public String address;
    public Boolean paired;
}
