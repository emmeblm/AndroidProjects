package com.example.lemme.medidordenivelyvelocidad;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lemme on 5/7/15.
 */
public class ConnectedThread extends Thread {
    private final InputStream readerStream;
    private final OutputStream writerStream;
    private static Handler handler;

    public ConnectedThread(BluetoothSocket bluetoothSocket) {
        InputStream reader = null;
        OutputStream writer = null;
        try {
            reader = bluetoothSocket.getInputStream();
            writer = bluetoothSocket.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        readerStream = reader;
        writerStream = writer;

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Utilities.RECEIVE_MESSAGE:
                        byte[] readBuffer = (byte[]) msg.obj;
                        String incomingString = new String(readBuffer, 0, msg.arg1);
                        Log.d(Utilities.TAG, incomingString);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void run() {
        byte[] buffer = new byte[256];
        int bytes;
        while(true) {
            try{
                bytes = readerStream.read(buffer);
                handler.obtainMessage(Utilities.RECEIVE_MESSAGE, bytes, -1, buffer).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void write(String message) {
        byte[] messageBuffer = message.getBytes();
        try{
            writerStream.write(messageBuffer);
        } catch (IOException e) {
            Log.e(Utilities.TAG, Utilities.ERROR_SENDING_DATA);
        }
    }
}
