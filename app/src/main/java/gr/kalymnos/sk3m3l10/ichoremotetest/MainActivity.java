package gr.kalymnos.sk3m3l10.ichoremotetest;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_views.MainScreenViewMvc;

public class MainActivity extends AppCompatActivity implements MainScreenViewMvc.OnSendClickListener{
    private static final int REQUEST_ENABLE_BT = 13715;

    private MainScreenViewMvc viewMvc;
    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver stateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSendClick() {

    }
}
