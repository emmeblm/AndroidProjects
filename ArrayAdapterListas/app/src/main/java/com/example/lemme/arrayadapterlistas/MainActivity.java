package com.example.lemme.arrayadapterlistas;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private ListView listViewContinentes;
    private String[] arrayContinentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarListViewContinentes();
    }

    private void inicializarListViewContinentes() {
        listViewContinentes = (ListView) this.findViewById(R.id.simpleView);
        ArrayAdapter<String> adaptador = crearAdaptadorListaContinentes(R.array.continentes);
        listViewContinentes.setAdapter(adaptador);
        listViewContinentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast desplegarContinente = Toast.makeText(getApplicationContext(), arrayContinentes[position], Toast.LENGTH_SHORT);
                desplegarContinente.show();
            }
        });
    }

    private ArrayAdapter<String> crearAdaptadorListaContinentes(int continentes) {
        arrayContinentes = this.getResources().getStringArray(continentes);
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayContinentes);
        return adaptador;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
