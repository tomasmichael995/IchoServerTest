package gr.kalymnos.sk3m3l10.ichoremotetest;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_views.MainScreenViewMvc;
import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_views.MainScreenViewMvcImpl;

import static android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED;
import static android.bluetooth.BluetoothAdapter.EXTRA_STATE;
import static android.bluetooth.BluetoothAdapter.STATE_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_ON;

public class MainActivity extends AppCompatActivity implements MainScreenViewMvc.OnSendClickListener {
    private static final int REQUEST_ENABLE_BT = 13715;
    private static final String BLUETOOTH_ENABLED = "Bluetooth enabled.";
    private static final String BLUETOOTH_DISABLED = "Bluetooth disabled.";
    private static final String BLUETOOTH_CONNECTED = "Bluetooth connected.";

    private MainScreenViewMvc viewMvc;
    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver stateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFields();
        exitIfBluetoothUnsupported();
        setupUi();
    }

    private void initFields() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        stateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case ACTION_STATE_CHANGED:
                        int state = intent.getIntExtra(EXTRA_STATE, STATE_OFF);
                        setUiFrom(state);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown action.");
                }
            }

            private void setUiFrom(int state) {
                if (state == STATE_ON) {
                    viewMvc.showBluetooth();
                    viewMvc.setToolbarSubtitle(BLUETOOTH_ENABLED);
                } else {
                    viewMvc.showBluetoothDisabled();
                    viewMvc.setToolbarSubtitle(BLUETOOTH_DISABLED);
                }
            }
        };
        initViewMvc();
    }

    private void initViewMvc() {
        viewMvc = new MainScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnSendClickListener(this);
    }

    private void exitIfBluetoothUnsupported() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupUi() {
        setContentView(viewMvc.getRootView());
        setSupportActionBar(viewMvc.getToolbar());
        viewMvc.setToolbarTitle(getString(R.string.app_name));
        displayBluetoothStatus();
    }

    private void displayBluetoothStatus() {
        if (bluetoothAdapter.isEnabled()) {
            viewMvc.showBluetooth();
            viewMvc.setToolbarSubtitle(BLUETOOTH_ENABLED);
        } else {
            viewMvc.showBluetoothDisabled();
            viewMvc.setToolbarSubtitle(BLUETOOTH_DISABLED);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(stateReceiver, new IntentFilter(ACTION_STATE_CHANGED));
        if (!bluetoothAdapter.isEnabled())
            requestBluetoothEnable();
    }

    private void requestBluetoothEnable() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(stateReceiver);
    }

    @Override
    public void onSendClick() {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_SHORT).show();
    }
}
