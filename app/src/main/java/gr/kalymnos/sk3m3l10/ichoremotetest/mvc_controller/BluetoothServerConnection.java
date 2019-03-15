package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_controller;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class BluetoothServerConnection extends Thread {
    private static final String TAG = "BluetoothServerConnecti";

    private BluetoothSocket socket;
    private PrintWriter out;

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

    @Override
    public void run() {
        out.println("Hello Icho!");
        closeSocket();
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
