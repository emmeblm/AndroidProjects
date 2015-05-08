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
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;


public class MainActivity extends Activity {

    Button connectBluetooth;
    Button disconnectBluetooth;
    Button goToSpeedChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectBluetooth = (Button) this.findViewById(R.id.btnConnectToBluetooth);
        disconnectBluetooth = (Button) this.findViewById(R.id.btnDisconnectFromBluetooth);
        goToSpeedChart = (Button) this.findViewById(R.id.btnSpeedChart);
        setEnabledStateToButtons(true, false, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setEnabledStateToButtons(Boolean enableConnect, Boolean enableDisconnect, Boolean enableCharts) {
        connectBluetooth.setEnabled(enableConnect);
        disconnectBluetooth.setEnabled(enableDisconnect);
        goToSpeedChart.setEnabled(enableCharts);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void disconnectFromBluetoothModule() {
        if(Utilities.bluetoothSocket != null) {
            try {
                Utilities.bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        setEnabledStateToButtons(false, true, true);

    }

    public void onClickDisconnectFromBluetoothModule(View view) {
        disconnectFromBluetoothModule();
        setEnabledStateToButtons(true, false, false);
    }

    public void onClickStartActivitySpeedometer(View view) {
        Intent intentProfile = new Intent(this, MedidorVelocidadActivity.class);
        startActivity(intentProfile);
        setEnabledStateToButtons(false, true, true);
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
        if(Build.VERSION.SDK_INT >= Utilities.MINIMUM_SDK_VERSION) {
            tryToCreateInsecureRfCommSocketToServiceRecord(bluetoothDevice);
        } else
            Utilities.bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(Utilities.SPP_UUID_SERVICE);
    }

    private void tryToCreateInsecureRfCommSocketToServiceRecord(BluetoothDevice bluetoothDevice) {
        try {
            final Method method = bluetoothDevice.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
            Utilities.bluetoothSocket = (BluetoothSocket) method.invoke(bluetoothDevice, Utilities.SPP_UUID_SERVICE);
        } catch (Exception e) {
            Log.e(Utilities.TAG, Utilities.INSECURE_RFCOMM_CONNECTION_FAILED, e);
        }
    }

    private void establishConnectionWithTheBluetoothModule() {
        try {
            Utilities.bluetoothSocket.connect();
            Log.d(Utilities.TAG, Utilities.SUCCESSFUL_CONNECTION);
        } catch (IOException e) {
            Log.e(Utilities.TAG, Utilities.CONNECTION_FAILED, e);
            try {
                Utilities.bluetoothSocket.close();
            } catch (IOException ex) {
                exitAppWithError(Utilities.FATAL_ERROR, Utilities.UNABLE_TO_CLOSE_SOCKET);
            }
        }
    }
}
