package com.example.array;



import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Resources res = getResources();
		String[] arreglo = res.getStringArray(R.array.color);
		
		for(int i=0 ; i<arreglo.length; i++)
		{
			
			System.out.println(arreglo[i]);
			System.out.print(arreglo[i]);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void mostrar(){
		
		
		Resources res = getResources();
		String[] arreglo = res.getStringArray(R.array.color);
		
		for(int i=0 ; i<arreglo.length; i++)
		{
			
			System.out.println(arreglo[i]);
			System.out.print(arreglo[i]);
			
		}
	}

}
