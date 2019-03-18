package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import gr.kalymnos.sk3m3l10.ichoremotetest.Bluetooth.BluetoothServer;
import gr.kalymnos.sk3m3l10.ichoremotetest.Bluetooth.BluetoothUtils;
import gr.kalymnos.sk3m3l10.ichoremotetest.R;
import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_view.MainScreenViewMvc;
import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_view.MainScreenViewMvcImpl;

import static android.bluetooth.BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
import static android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED;
import static android.bluetooth.BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION;
import static android.bluetooth.BluetoothAdapter.EXTRA_STATE;
import static android.bluetooth.BluetoothAdapter.STATE_OFF;
import static android.bluetooth.BluetoothAdapter.STATE_ON;

public class MainActivity extends AppCompatActivity implements MainScreenViewMvc.OnSendClickListener {
    private static final String TAG = "MainActivity";
    private static final String BLUETOOTH_ENABLED = "Bluetooth enabled.";
    private static final String BLUETOOTH_DISABLED = "Bluetooth disabled.";
    private static final String BLUETOOTH_CONNECTED = "Bluetooth connected.";
    private static final int DISCOVERABLE_DURATION = 300;
    private static final int REQUEST_DISCOVERABLE = 132;

    private MainScreenViewMvc viewMvc;
    private BroadcastReceiver stateReceiver;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothServer server;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFields();
        exitIfBluetoothUnsupported();
        setupUi();
    }

    private void initFields() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        initStateReceiver();
        initViewMvc();
        handler = new MainHandler(viewMvc);
    }

    private void initStateReceiver() {
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
                    displayBluetoothEnabled();
                } else {
                    displayBluetoothDisabled();
                }
            }
        };
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
        viewMvc.bindMacAddress("MAC:" + BluetoothUtils.localMacAddress(this));
        displayBluetoothStatus();
    }

    private void displayBluetoothStatus() {
        if (bluetoothAdapter.isEnabled()) {
            displayBluetoothEnabled();
        } else {
            displayBluetoothDisabled();
        }
    }

    private void displayBluetoothEnabled() {
        viewMvc.showBluetooth();
        viewMvc.setToolbarSubtitle(BLUETOOTH_ENABLED);
    }

    private void displayBluetoothDisabled() {
        viewMvc.showBluetoothDisabled();
        viewMvc.setToolbarSubtitle(BLUETOOTH_DISABLED);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(stateReceiver, new IntentFilter(ACTION_STATE_CHANGED));
        requestDiscoverable();
    }

    private void requestDiscoverable() {
        Intent intent = new Intent(ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);
        startActivityForResult(intent, REQUEST_DISCOVERABLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == DISCOVERABLE_DURATION) {
            server = BluetoothServer.Factory.createInstance(handler);
            server.start();
        } else if (resultCode == RESULT_CANCELED) {
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(stateReceiver);
        server.disconnect();
    }

    @Override
    public void onSendClick() {
        if (server != null) {

        }
    }
}
