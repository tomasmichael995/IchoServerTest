package gr.kalymnos.sk3m3l10.ichoremotetest;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_views.MainScreenViewMvc;
import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_views.MainScreenViewMvcImpl;

public class MainActivity extends AppCompatActivity implements MainScreenViewMvc.OnSendClickListener{
    private static final int REQUEST_ENABLE_BT = 13715;

    private MainScreenViewMvc viewMvc;
    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver stateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewMvc();
        setupUi();
    }

    private void initViewMvc() {
        viewMvc = new MainScreenViewMvcImpl(LayoutInflater.from(this),null);
        viewMvc.setOnSendClickListener(this);
    }

    private void setupUi() {
        setContentView(viewMvc.getRootView());
        setSupportActionBar(viewMvc.getToolbar());
    }

    @Override
    public void onSendClick() {
        Toast.makeText(this, "Button pressed", Toast.LENGTH_SHORT).show();
    }
}
