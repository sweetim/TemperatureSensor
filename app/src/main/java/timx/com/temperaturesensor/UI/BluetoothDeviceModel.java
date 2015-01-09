package timx.com.temperaturesensor.UI;

/**
 * ZMP Inc
 * Created by Tim on 09/01/2015.
 */
public class BluetoothDeviceModel {
    public BluetoothDeviceModel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public BluetoothDeviceModel() {}

    public String name;
    public String address;
}
