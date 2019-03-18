package gr.kalymnos.sk3m3l10.ichoremotetest.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import gr.kalymnos.sk3m3l10.ichoremotetest.BuildConfig;

public class BluetoothServer extends Thread {
    private static final String TAG = "BluetoothServer";
    public static final String UUID_STRING = "390f542b-629b-4076-b874-a690f781c894";

    private Handler handler;
    private BluetoothServerSocket serverSocket;
    private BluetoothServerConnection serverConnection;

    private BluetoothServer(BluetoothServerSocket serverSocket, Handler handler) {
        this.serverSocket = serverSocket;
        this.handler = handler;
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
                break;
            }
        }
    }

    private void startConnection(BluetoothSocket socket) {
        serverConnection = new BluetoothServerConnection(socket, handler);
    }

    public void send(String message) {
        serverConnection.send(message);
    }

    public static class Factory {
        public static BluetoothServer createInstance(Handler handler) {
            BluetoothServerSocket serverSocket = createServerSocket();
            return new BluetoothServer(serverSocket, handler);
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
