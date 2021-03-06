package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import gr.kalymnos.sk3m3l10.ichoremotetest.R;

public class MainScreenViewMvcImpl implements MainScreenViewMvc {
    private View rootView;
    private Toolbar toolbar;
    private FloatingActionButton sendButton;
    private TextView macAddressTxt, connectionStatus, serverStatus;
    private ImageView bluetoothImage;
    private EditText message;

    public MainScreenViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        initViews(inflater, container);
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);
        toolbar = rootView.findViewById(R.id.toolBar);
        sendButton = rootView.findViewById(R.id.send);
        bluetoothImage = rootView.findViewById(R.id.bluetooth_image);
        macAddressTxt = rootView.findViewById(R.id.mac_address);
        message = rootView.findViewById(R.id.message);
        connectionStatus = rootView.findViewById(R.id.connection_status);
        serverStatus = rootView.findViewById(R.id.server_status);
    }

    @Override
    public void setOnSendClickListener(OnSendClickListener listener) {
        sendButton.setOnClickListener(view -> {
            if (listener != null)
                listener.onSendClick();
        });
    }

    @Override
    public void showBluetooth() {
        bluetoothImage.setImageResource(R.drawable.ic_bluetooth_black_24dp);
    }

    @Override
    public void showBluetoothDisabled() {
        bluetoothImage.setImageResource(R.drawable.ic_bluetooth_disabled_black_24dp);
    }

    @Override
    public void showBluetoothConnected() {
        bluetoothImage.setImageResource(R.drawable.ic_bluetooth_connected_black_24dp);
    }

    @Override
    public void bindMacAddress(String address) {
        macAddressTxt.setText(address);
    }

    @Override
    public String getMessage() {
        return message.getText().toString();
    }

    @Override
    public void setConnectionStatusToConnected() {
        connectionStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward_black_24dp, 0);
    }

    @Override
    public void setConnectionStatusToDisconnected() {
        connectionStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward_black_24dp, 0);
    }

    @Override
    public void setConnectionStatusToError() {
        connectionStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_black_24dp, 0);
    }

    @Override
    public void setServerStatusToUp() {
        serverStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward_black_24dp, 0);
    }

    @Override
    public void setServerStatusToDown() {
        serverStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward_black_24dp, 0);
    }

    @Override
    public void setServerStatusToError() {
        serverStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_black_24dp, 0);
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setToolbarSubtitle(String subtitle) {
        toolbar.setSubtitle(subtitle);
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}
