package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class BluetoothServerConnection {
    private static final String TAG = "BluetoothServerConnecti";

    private BluetoothSocket socket;
    private PrintWriter out;
    private OnClientDisconnectionListener disconnectionListener;

    public interface OnClientDisconnectionListener {
        void onClientDisconnected();

        void onClientDisconnectionError();
    }

    BluetoothServerConnection(BluetoothSocket socket) {
        initFields(socket);
    }

    private void initFields(BluetoothSocket socket) {
        this.socket = socket;
        out = new PrintWriter(getOutputStreamFrom(socket), true);
    }

    private OutputStream getOutputStreamFrom(BluetoothSocket socket) {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error obtaining outStream from socket", e);
            return null;
        }
    }

    public void setDisconnectionListener(OnClientDisconnectionListener listener) {
        disconnectionListener = listener;
    }

    public void send(String message) {
        out.println(message);
    }

    private void disconnect() {
        try {
            out.close();
            socket.close();
            disconnectionListener.onClientDisconnected();
        } catch (IOException e) {
            Log.d(TAG, "Error while closing outputStream or Socket " + e.getMessage());
            disconnectionListener.onClientDisconnectionError();
        }
    }
}
