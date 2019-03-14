package gr.kalymnos.sk3m3l10.ichoremotetest;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 13715;

    private Toolbar toolbar;
    private ImageView bluetoothImage;

    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver stateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFields();
        setSupportActionBar(toolbar);
        exitIfBluetoothNotSupported();
        enableBluetooth();
    }

    private void initFields() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothImage = findViewById(R.id.bluetooth_image);
        toolbar = findViewById(R.id.toolBar);
        stateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean stateChanged = intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED);
                if (stateChanged) {
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_DISCONNECTED);
                    if (state == BluetoothAdapter.STATE_CONNECTED) {
                        setUiToEnabled();
                    } else {
                        setUiToDisabled();
                    }
                }
            }

            private void setUiToDisabled() {
                getSupportActionBar().setSubtitle(R.string.bluetooth_disabled);
                bluetoothImage.setBackgroundResource(R.drawable.ic_bluetooth_disabled_black_24dp);
            }

            private void setUiToEnabled() {
                getSupportActionBar().setSubtitle(R.string.bluetooth_enabled);
                bluetoothImage.setBackgroundResource(R.drawable.ic_bluetooth_black_24dp);
            }
        };
    }

    private void exitIfBluetoothNotSupported() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void enableBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!(resultCode == RESULT_OK)) {
            Toast.makeText(this, "You have to enable bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onSendClick(View view) {

    }
}
