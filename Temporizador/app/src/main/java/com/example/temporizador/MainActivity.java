package com.example.temporizador;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;

import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.temporizador.ServiceTemporizador.LocalBinder;

public class MainActivity extends Activity {
	EditText editTemporizador;
	EditText editTemporizador2;
	ServiceTemporizador servicio;
	ServiceTemporizador servicio2;
	boolean mAtar;//si me ate o no al servicio

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editTemporizador=(EditText)this.findViewById(R.id.editTextTemporizador);
		editTemporizador2=(EditText)this.findViewById(R.id.editTextTemporizador2);
		Intent intent=new Intent(this, ServiceTemporizador.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		if(mAtar)
		{
			unbindService(mConnection);
		}
	}
	@Override
	protected void onResume()
	{
		super.onResume();
		registerReceiver(receiver, new IntentFilter("servicio"));
		
		
		
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		unregisterReceiver(receiver);
		
	}
	
	//SETEA LA VARIABLE CON
	public void onClickiniciar(View v)
	{
		servicio.count=Integer.parseInt(editTemporizador.getText().toString());
		servicio.count2=Integer.parseInt(editTemporizador2.getText().toString());
	}
	public void onClickDetener(View v)
	{
		
		
	}
	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			// We've bound to LocalService, cast the IBinder and get
			// LocalService instance
			LocalBinder binder = (LocalBinder) service;
			servicio = binder.getService();
			mAtar = true;
			Log.i("servicio", "conectado");
			if (!servicio.indicador) {
				Intent servicio = new Intent(MainActivity.this,ServiceTemporizador.class);
				startService(servicio);
				Log.i("SERVICIO", "NO CORRIENDO");
			} else if (servicio.indicador)
				Log.i("SERVICIO", "CORRIENDO");
		}

		public void onServiceDisconnected(ComponentName arg0) {
			mAtar = false;
			Log.i("servicio", "desconectado");
		}
	};
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	      Bundle bundle = intent.getExtras();
	      if (bundle != null) {
	        String cuenta = String.valueOf(bundle.getInt("segundos"));
	        String cuenta2 = String.valueOf(bundle.getInt("minutos"));
	        editTemporizador.setText(cuenta);
	        editTemporizador2.setText(cuenta2);
	      }
	    }
	  };
	

	
	
}
