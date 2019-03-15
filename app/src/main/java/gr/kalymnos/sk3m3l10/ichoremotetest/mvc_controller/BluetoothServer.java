package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

public class BluetoothServer extends Thread implements BluetoothServerConnection.OnClientDisconnectionListener{
    private static final String TAG = "BluetoothServer";
    public static String UUID = "390f542b-629b-4076-b874-a690f781c894";

    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private BluetoothConnectionListener connectionListener;
    private BluetoothServerConnection serverConnection;

    public interface BluetoothConnectionListener {
        void onConnection();

        void onDisconnection();

        void onConnectionError();

        void onDisconnectionError();
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
                connectionListener.onConnectionError();
            }
        }
    }

    private void startConnection(BluetoothSocket socket) {
        serverConnection = new BluetoothServerConnection(socket);
        connectionListener.onConnection();
    }

    @Override
    public void onClientDisconnected() {
        connectionListener.onDisconnection();
    }

    @Override
    public void onClientDisconnectionError() {
        connectionListener.onDisconnectionError();
    }
}
