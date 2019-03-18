package gr.kalymnos.sk3m3l10.ichoremotetest.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import gr.kalymnos.sk3m3l10.ichoremotetest.BuildConfig;

public class BluetoothServer extends Thread implements BluetoothServerConnection.OnClientDisconnectionListener {
    private static final String TAG = "BluetoothServer";
    public static final String UUID_STRING = "390f542b-629b-4076-b874-a690f781c894";

    private BluetoothServerSocket serverSocket;
    private BluetoothConnectionListener connectionListener;
    private BluetoothServerConnection serverConnection;

    public interface BluetoothConnectionListener {
        void onConnection();

        void onDisconnection();

        void onConnectionError();

        void onDisconnectionError();
    }

    private BluetoothServer(BluetoothServerSocket serverSocket) {
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
                break;
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

    public void send(String message) {
        serverConnection.send(message);
    }

    public static class Factory{
        public static BluetoothServer createInstance(BluetoothConnectionListener listener) {
            BluetoothServerSocket serverSocket = createServerSocket();
            BluetoothServer instance = new BluetoothServer(serverSocket);
            instance.setConnectionListener(listener);
            return instance;
        }

        private static BluetoothServerSocket createServerSocket() {
            String name = BuildConfig.APPLICATION_ID;
            UUID uuid = UUID.fromString(UUID_STRING);
            try {
                return BluetoothAdapter.getDefaultAdapter().listenUsingInsecureRfcommWithServiceRecord(name, uuid);
            } catch (IOException e) {
                Log.d(TAG, "Error creating server socket.", e);
                return null;
            }
        }
    }
}
