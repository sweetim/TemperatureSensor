package timx.com.temperaturesensor.Temperature;

import timx.com.temperaturesensor.Util.Util;

/**
 * Created by Lenovo on 15/1/2015.
 */
public class Temperature {
    public static float convertRawData(byte[] data) {
        return Util.makeInt(data, 2) / 8.2f;
    }
}
