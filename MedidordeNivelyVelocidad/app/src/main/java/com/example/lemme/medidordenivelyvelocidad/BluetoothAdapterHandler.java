package com.example.lemme.medidordenivelyvelocidad;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

/**
 * Created by lemme on 5/7/15.
 */
public class BluetoothAdapterHandler {
    private static BluetoothAdapter bluetoothAdapter;
    private static BluetoothAdapterHandler bluetoothAdapterHandler = new BluetoothAdapterHandler();

    public static void setDefaultBluetoothAdapter() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public static Boolean validateIfBluetoothAdapterIsSupported() {
        if(bluetoothAdapter == null)
            return false;
        return true;
    }

    public static Boolean validateBluetoothAdapterState() {
        if(bluetoothAdapter.isEnabled()) {
            bluetoothAdapterHandler.showMessageInLog(Utilities.BLUETOOTH_ON);
            return true;
        }
        return false;
    }

    private void showMessageInLog(String message) {
        Log.d(Utilities.TAG, message);
    }
}
