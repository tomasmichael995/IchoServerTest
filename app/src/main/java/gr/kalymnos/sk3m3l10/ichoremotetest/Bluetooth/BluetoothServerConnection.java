package gr.kalymnos.sk3m3l10.ichoremotetest.Bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

import static gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller.status.ConnectionStatus.CONNECTED;
import static gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller.status.ConnectionStatus.DISSCONNECTED;
import static gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller.status.ConnectionStatus.ERROR;

public class BluetoothServerConnection {
    private static final String TAG = "BluetoothServerConnecti";

    private Handler handler;
    private BluetoothSocket socket;
    private OutputStream out;

    BluetoothServerConnection(BluetoothSocket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
        out = getOutputStreamFrom(socket);
        reportStatusFromOutputStream();
    }

    private OutputStream getOutputStreamFrom(BluetoothSocket socket) {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error obtaining outStream from socket", e);
            return null;
        }
    }

    private void reportStatusFromOutputStream() {
        if (out != null) {
            report(CONNECTED);
        } else {
            report(ERROR);
        }
    }

    private void report(int status) {
        Message msg = handler.obtainMessage();
        msg.what = status;
        msg.sendToTarget();
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

    public void disconnect() {
        try {
            out.close();
            socket.close();
            report(DISSCONNECTED);
        } catch (IOException e) {
            Log.d(TAG, "Error while closing outputStream or Socket " + e.getMessage());
        }
    }
}
