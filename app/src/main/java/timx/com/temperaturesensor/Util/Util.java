package timx.com.temperaturesensor.Util;

/**
 * Created by Lenovo on 15/1/2015.
 */
public class Util {
    public static int makeInt(byte[] data, int length) {
        int x = 0;

        for (int i = 0; i < length; i++) {
            x |= (data[i] & 0xFF) << (((length - 1) - i) * 8);
        }

        return x;
    }
}
