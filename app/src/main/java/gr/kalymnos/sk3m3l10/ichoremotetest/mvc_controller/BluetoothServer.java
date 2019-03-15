package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

public class BluetoothServer extends Thread {
    private static final String TAG = "BluetoothServer";
    public static String UUID = "390f542b-629b-4076-b874-a690f781c894";

    private BluetoothServerSocket serverSocket;
    private BluetoothConnectionListener connectionListener;
    private BluetoothServerConnection serverConnection;

    public interface BluetoothConnectionListener {
        void onConnected();

        void onConnectionError();
    }

    public BluetoothServer(BluetoothServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setConnectionListener(BluetoothConnectionListener listener) {
        connectionListener = listener;
    }

    @Override
    public void run() {
        while (true) {
            try {
                BluetoothSocket socket = serverSocket.accept();
                if (socket != null) {
                    startConnection(socket);
                    serverSocket.close();
                    break;
                }
            } catch (IOException e) {
                Log.e(TAG, "Error obtaining or closing socket " + e.getMessage());
            }
        }
    }

    private void startConnection(BluetoothSocket socket) {
        serverConnection = new BluetoothServerConnection(socket);
        serverConnection.start();
        connectionListener.onConnected();
    }
}
