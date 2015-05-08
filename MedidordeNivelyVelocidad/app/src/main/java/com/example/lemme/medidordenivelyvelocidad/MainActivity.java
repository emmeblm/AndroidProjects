package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;


public class MainActivity extends Activity {

    private Button connectBluetooth;
    private Button disconnectBluetooth;
    private Button goToSpeedChart;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectBluetooth = (Button) this.findViewById(R.id.btnConnectToBluetooth);
        disconnectBluetooth = (Button) this.findViewById(R.id.btnDisconnectFromBluetooth);
        goToSpeedChart = (Button) this.findViewById(R.id.btnSpeedChart);
        setEnabledStateToButtons(true, false, false);
    }

    private void setEnabledStateToButtons(Boolean ... enableButtons) {
        connectBluetooth.setEnabled(enableButtons[0]);
        disconnectBluetooth.setEnabled(enableButtons[1]);
        goToSpeedChart.setEnabled(enableButtons[2]);
    }

    public void onClickConnectToBluetoothDevice(View view) {
        setEnabledStateToButtons(false, false, false);

        BluetoothAdapterHandler.setDefaultBluetoothAdapter();
        Boolean bluetoothAdapterSupported = BluetoothAdapterHandler.validateIfBluetoothAdapterIsSupported();

        Boolean bluetoothAdapterEnabled = false;
        if(bluetoothAdapterSupported)
            bluetoothAdapterEnabled = checkBluetoothAdapterState();
        else
            exitAppWithError(Utilities.FATAL_ERROR, Utilities.BLUETOOTH_NOT_SUPPORTED);

        if(bluetoothAdapterEnabled)
            continueConnecting();
    }

    private Boolean checkBluetoothAdapterState() {
        Boolean bluetoothAdapterEnabled = BluetoothAdapterHandler.validateBluetoothAdapterState();
        if(!bluetoothAdapterEnabled)
            promptUserToEnableBluetooth();
        return bluetoothAdapterEnabled;
    }

    private void promptUserToEnableBluetooth() {
        Intent bluetoothEnableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        this.startActivityForResult(bluetoothEnableIntent, Utilities.ENABLE_BLUETOOTH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Utilities.ENABLE_BLUETOOTH_REQUEST && resultCode == RESULT_OK) {
            if(checkBluetoothAdapterState())
                continueConnecting();
        }
        if(resultCode == RESULT_CANCELED)
            setEnabledStateToButtons(true, false, false);
    }

    private void exitAppWithError(String errorType, String errorMessage) {
        String messageToShow = errorType + Utilities.ERROR_MESSAGE_JOIN_CHAR + errorMessage;
        Toast.makeText(getBaseContext(), messageToShow, Toast.LENGTH_SHORT);
    }

    private void continueConnecting() {
        createBroadcastReceiverToListenToBluetoothAdapterState();
        registerBroadcastReceiverToBluetoothEvents();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapterHandler.getBluetoothAdapter();
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(Utilities.BLUETOOTH_MODULE_MAC_ADDRESS);
        tryToCreateBluetoothSocket(bluetoothDevice);
        bluetoothAdapter.cancelDiscovery();
        establishConnectionWithTheBluetoothModule();
        setEnabledStateToButtons(false, true, true);
    }

    private void createBroadcastReceiverToListenToBluetoothAdapterState() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Boolean bluetoothAdapterOFF = (state == BluetoothAdapter.STATE_OFF ||  state == BluetoothAdapter.STATE_TURNING_OFF);
                if(action == BluetoothAdapter.ACTION_STATE_CHANGED && bluetoothAdapterOFF) {
                    onClickDisconnectFromBluetoothModule(new View(getApplicationContext()));
                }
            }
        };
    }

    private void registerBroadcastReceiverToBluetoothEvents() {
        IntentFilter bluetoothStateChangedFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(broadcastReceiver, bluetoothStateChangedFilter);
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

    public void onClickDisconnectFromBluetoothModule(View view) {
        disconnectFromBluetoothModule();
        try {
            this.unregisterReceiver(broadcastReceiver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setEnabledStateToButtons(true, false, false);
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

    public void onClickStartActivitySpeedometer(View view) {
        Intent intentProfile = new Intent(this, SpeedometerActivity.class);
        startActivity(intentProfile);
        setEnabledStateToButtons(false, true, true);
    }

    @Override
    public void onBackPressed() {
        onClickDisconnectFromBluetoothModule(new View(this));
        super.onBackPressed();
    }
}
