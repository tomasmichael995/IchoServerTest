package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller.status.ConnectionStatus;
import gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller.status.ServerStatus;
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
                viewMvc.setServerStatusToUp();
                break;
            case ServerStatus.DOWN:
                viewMvc.setServerStatusToDown();
                break;
            case ServerStatus.ERROR:
                viewMvc.setServerStatusToError();
                break;
            case ConnectionStatus.CONNECTED:
                viewMvc.setConnectionStatusToConnected();
                break;
            case ConnectionStatus.DISSCONNECTED:
                viewMvc.setConnectionStatusToDisconnected();
                break;
            case ConnectionStatus.ERROR:
                viewMvc.setConnectionStatusToError();
                break;
            default:
                throw new IllegalArgumentException(TAG + ": Unknown Message.what argument");
        }
    }
}
