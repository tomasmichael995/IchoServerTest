package gr.kalymnos.sk3m3l10.ichoremotetest;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView bluetoothImage;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFields();
        exitIfBluetoothNotSupported();
    }

    private void exitIfBluetoothNotSupported() {
        if (bluetoothAdapter == null){
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initFields() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        toolbar = findViewById(R.id.toolBar);
        bluetoothImage = findViewById(R.id.bluetooth_image);
    }

    public void onSendClick(View view) {

    }
}
