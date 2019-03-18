package gr.kalymnos.sk3m3l10.ichoremotetest.Bluetooth;

import android.content.Context;

public class BluetoothUtils {
    public static String localMacAddress(Context context) {
        //  Workaround from StackOverflow (search for get bluetooth local mac address in Marshmallow
        //  Reason at https://developer.android.com/about/versions/marshmallow/android-6.0-changes#behavior-hardware-id
        return android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
    }
}
