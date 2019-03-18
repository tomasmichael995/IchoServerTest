package gr.kalymnos.sk3m3l10.ichoremotetest.Bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

public class BluetoothServerConnection {
    private static final String TAG = "BluetoothServerConnecti";

    private Handler handler;
    private BluetoothSocket socket;
    private OutputStream out;

    BluetoothServerConnection(BluetoothSocket socket, android.os.Handler handler) {
        this.socket = socket;
        this.handler = handler;
        out = getOutputStreamFrom(socket);
    }

    private OutputStream getOutputStreamFrom(BluetoothSocket socket) {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error obtaining outStream from socket", e);
            return null;
        }
    }

    public void send(String message) {
        byte[] data = message.getBytes();
        try {
            out.write(data);
        } catch (IOException e) {
            Log.d(TAG, "OutputStream#write() might be closed: " + e.getMessage());
            disconnect();
        }
    }

    private void disconnect() {
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            Log.d(TAG, "Error while closing outputStream or Socket " + e.getMessage());
        }
    }
}
