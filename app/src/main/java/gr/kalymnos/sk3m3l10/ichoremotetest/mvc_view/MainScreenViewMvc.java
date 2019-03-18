package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_view;

public interface MainScreenViewMvc extends ViewMvcWithToolbar {
    interface OnSendClickListener {
        void onSendClick();
    }

    void setOnSendClickListener(OnSendClickListener listener);

    void showBluetooth();

    void showBluetoothDisabled();

    void showBluetoothConnected();

    void bindMacAddress(String address);

    String getMessage();

    void setConnectionStatusToConnected();

    void setConnectionStatusToDisconnected();

    void setConnectionStatusToError();

    void setServerStatusToUp();

    void setServerStatusToDown();

    void setServerStatusToError();
}
