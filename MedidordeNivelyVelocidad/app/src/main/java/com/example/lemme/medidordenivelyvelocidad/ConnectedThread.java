package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lemme on 5/7/15.
 */
public class ConnectedThread extends Thread {
    private final BluetoothSocket bluetoothSocket;
    private Chart chart;
    private StringBuffer stringBuffer;

    private final InputStream readerStream;
    private final OutputStream writerStream;

    private static Handler handler;
    private volatile boolean running = true;

    public ConnectedThread(final BluetoothSocket bluetoothSocket, final Chart chart) {
        this.bluetoothSocket = bluetoothSocket;
        this.chart = chart;
        stringBuffer = new StringBuffer();
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

        initializeHandler();
    }

    private void initializeHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Utilities.RECEIVE_MESSAGE:
                        byte[] readBuffer = (byte[]) msg.obj;
                        stringBuffer.append(new String(readBuffer, 0, msg.arg1));
                        int endOfLine = stringBuffer.indexOf("\r\n");
                        if(endOfLine > 0){
                            String subString = stringBuffer.substring(0, endOfLine);
                            stringBuffer.delete(0, stringBuffer.length());
                            Log.d(Utilities.TAG, subString);
                            chart.getSerie().addSensorLecture(Float.valueOf(subString));
                            chart.updateChart();
                        }
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
        while(running) {
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

    public void terminate() {
        running = false;
    }
}
