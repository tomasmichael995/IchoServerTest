package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import gr.kalymnos.sk3m3l10.ichoremotetest.Bluetooth.ConnectionStatus;
import gr.kalymnos.sk3m3l10.ichoremotetest.Bluetooth.ServerStatus;
import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_view.MainScreenViewMvc;

public class MainHandler extends Handler {
    private static final String TAG = "MainHandler";

    private MainScreenViewMvc viewMvc;

    public MainHandler(MainScreenViewMvc viewMvc) {
        super(Looper.getMainLooper());
        this.viewMvc = viewMvc;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case ServerStatus.UP:
                viewMvc.setServerStatusUp();
                break;
            case ServerStatus.DOWN:
                break;
            case ServerStatus.ERROR:
                break;
            case ConnectionStatus.CONNECTED:
                break;
            case ConnectionStatus.DISSCONNECTED:
                break;
            case ConnectionStatus.ERROR:
                break;
            default:
                throw new IllegalArgumentException(TAG + ": Unknown Message.what argument");
        }
    }
}
