package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import gr.kalymnos.sk3m3l10.ichoremotetest.R;

public class MainScreenViewMvcImpl implements MainScreenViewMvc {
    private View rootView;
    private Toolbar toolbar;
    private FloatingActionButton sendButton;
    private ImageView bluetoothImage;

    public MainScreenViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        initViews(inflater, container);
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);
        toolbar = rootView.findViewById(R.id.toolBar);
        sendButton = rootView.findViewById(R.id.send);
        bluetoothImage = rootView.findViewById(R.id.bluetooth_image);
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
