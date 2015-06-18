package com.example.temporizador;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ServiceTemporizador extends Service {
	private Timer timer;
	private final IBinder mBinder = new LocalBinder();//clase para atar al servicio
	private static final String TAG = "EjemploServicio";
	public int count=0;
	public int count2=0;
	NotificationManager notManager;
	public boolean indicador = false;
	
	public ServiceTemporizador() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public class LocalBinder extends Binder {
		public ServiceTemporizador getService() {
			// Return this instance of LocalService so clients can call public
			// methods
			return ServiceTemporizador.this;
		}
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Servicio creado");
		timer = new Timer();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Avoids service termination
		//cambiar
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	count++;
   
            	if(count==60)
            	{
            		count2++;
            		count=0;
            		
            	}
            	Log.i(TAG, String.valueOf(count));
            	if (count>=0){
            		publishResults(count, count2);
            	}
//            	if (count == 0){
//            		SendNotificationtoolbar();
//            	}
            }
        }, 1000, 1* 1 *1000);//si esta60*60 seria una hora y un minuto
		//cambiar
		indicador = true;
		Log.i(TAG, "Servicio iniciado");
		this.startForeground(0, null);		
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Servicio destruido");
	}

//para enviar notificaciones
	public void SendNotificationtoolbar(){	
		notManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Intent notIntent = new Intent(this,MainActivity.class);
		//Bundle bundle = new Bundle();
    	//bundle.putString("PROMOCION_NOMBRE",promotion.getCommerceName());
		//notIntent.putExtras( bundle );
		long[] pattern = new long[]{1000,500,1000};
		Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		PendingIntent contIntent = PendingIntent.getActivity(this, 0, notIntent, 0);		 
		Notification n  = new NotificationCompat.Builder(this)
        .setContentTitle("Alerta")//cambiar titulo
        .setContentText("Se ha producido una alerta")//cambiar mensaje
        .setSmallIcon(R.drawable.ic_launcher)//cambiar icono
        .setContentIntent(contIntent)
        .setSound(defaultSound)
        .setVibrate(pattern)//si no quiero la vibracion la puedo borrar
        .setAutoCancel(true).build();		
		notManager.notify(1, n);	
	}
	
	 private void publishResults(int count,int count2) {
		    Intent intent = new Intent("servicio");
		    Bundle bundle = new Bundle();
	    	bundle.putInt("segundos", count);
	    	bundle.putInt("minutos", count2);
		    intent.putExtras(bundle);
		    sendBroadcast(intent);
		  }
	 
}
