package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_views;

public interface MainScreenViewMvc extends ViewMvcWithToolbar {
    interface OnSendClickListener {
        void onSendClick();
    }

    void setOnSendClickListener(OnSendClickListener listener);

    void displayBluetoothImg();

    void displaBluetoothDisabledImg();

    void displaBluetoothConnectedImg();
}
