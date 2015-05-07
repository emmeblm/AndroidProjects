package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;


public class MainActivity extends Activity {

    private BluetoothSocket bluetoothSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        disconnectFromBluetoothModule();
    }

    private void disconnectFromBluetoothModule() {
        if(bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickStartActivitySpeedometer(View view) {
        createDataStreamToTalkToTheServer();
        Intent intentProfile = new Intent(this, MedidorVelocidadActivity.class);
        startActivity(intentProfile);
    }

    public void onClickConnectToBluetoothDevice(View view) {
        BluetoothAdapterHandler.setDefaultBluetoothAdapter();
        Boolean bluetoothAdapterSupported = BluetoothAdapterHandler.validateIfBluetoothAdapterIsSupported();

        if(bluetoothAdapterSupported)
            checkBluetoothAdapterState();
        else
            exitAppWithError(Utilities.FATAL_ERROR, Utilities.BLUETOOTH_NOT_SUPPORTED);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapterHandler.getBluetoothAdapter();
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(Utilities.BLUETOOTH_MODULE_MAC_ADDRESS);
        tryToCreateBluetoothSocket(bluetoothDevice);
        bluetoothAdapter.cancelDiscovery();
        establishConnectionWithTheBluetoothModule();
    }

    public void onClickDisconnectFromBluetoothModule(View view) {
        disconnectFromBluetoothModule();
    }

    private void checkBluetoothAdapterState() {
        Boolean bluetoothAdapterEnabled = BluetoothAdapterHandler.validateBluetoothAdapterState();
        if(!bluetoothAdapterEnabled)
            promptUserToEnableBluetooth();
    }

    private void promptUserToEnableBluetooth() {
        Intent bluetoothEnableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(bluetoothEnableIntent, 1);
    }

    private void exitAppWithError(String errorType, String errorMessage) {
        String messageToShow = errorType + Utilities.ERROR_MESSAGE_JOIN_CHAR + errorMessage;
        Toast.makeText(getBaseContext(), messageToShow, Toast.LENGTH_SHORT);
    }

    private void tryToCreateBluetoothSocket(BluetoothDevice bluetoothDevice) {
        try {
            createBluetoothSocket(bluetoothDevice);
        } catch (IOException e) {
            exitAppWithError(Utilities.FATAL_ERROR, e.getMessage());
        }
    }

    private void createBluetoothSocket(BluetoothDevice bluetoothDevice) throws IOException {
        if(Build.VERSION.SDK_INT >= Utilities.MINIMUM_SDK_SUPPORTED_VERSION) {
            tryToCreateInsecureRfCommSocketToServiceRecord(bluetoothDevice);
        } else
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(Utilities.SPP_UUID_SERVICE);
    }

    private void tryToCreateInsecureRfCommSocketToServiceRecord(BluetoothDevice bluetoothDevice) {
        try {
            final Method method = bluetoothDevice.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
            bluetoothSocket = (BluetoothSocket) method.invoke(bluetoothDevice, Utilities.SPP_UUID_SERVICE);
        } catch (Exception e) {
            Log.e(Utilities.TAG, Utilities.INSECURE_RFCOMM_CONNECTION_FAILED, e);
        }
    }

    private void establishConnectionWithTheBluetoothModule() {
        try {
            bluetoothSocket.connect();
            Log.d(Utilities.TAG, Utilities.SUCCESSFUL_CONNECTION);
        } catch (IOException e) {
            Log.e(Utilities.TAG, Utilities.CONNECTION_FAILED, e);
            try {
                bluetoothSocket.close();
            } catch (IOException ex) {
                exitAppWithError(Utilities.FATAL_ERROR, Utilities.UNABLE_TO_CLOSE_SOCKET);
            }
        }
    }

    private void createDataStreamToTalkToTheServer() {
        ConnectedThread connectedThread = new ConnectedThread(bluetoothSocket);
        connectedThread.start();
    }
}
