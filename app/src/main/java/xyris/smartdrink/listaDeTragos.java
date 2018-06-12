package xyris.smartdrink;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class listaDeTragos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_tragos);
        FloatingActionButton botonCrearTrago = findViewById(R.id.botonCrearTrago);
        final Button botonOpcionesAdicionales = findViewById(R.id.buttonOpcionesAdicionales);
        ListView listViewTragos = findViewById(R.id.listaTragos);
        //AdapterItem adapter = new AdapterItem(this, listaTragos);
        //lv.setAdapter(adapter);
        // ArrayList<CategoryList> listaTragos = new ArrayList<CategoryList>();
        ArrayList<String> listaTragos = new ArrayList<String>();

        //Adaptador que maneja los datos del listView
        ArrayAdapter<String> adapter;

        // Adapter: Recibe 3 paràmetros:
        // - Contexto
        // - ID del layout (en este caso "my_text_view"
        // - El array de datos
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_text_view, listaTragos);

        // Se setean los datos en el listView
        listViewTragos.setAdapter(adapter);

        // Se agregan valores en la lista
        listaTragos.add("Banaranja");
        listaTragos.add("Naranfru");
        listaTragos.add("Manzana");
        listaTragos.add("Frutipera");
        listaTragos.add("Peranzana");
        listaTragos.add("Tropical");
        // Verifica si el adapter cambiò
        adapter.notifyDataSetChanged();



        botonCrearTrago.setOnClickListener( new View.OnClickListener()
        {
            public void onClick (View v){
                abrirCrearTragos(v);
            }
        });

        botonOpcionesAdicionales.setOnClickListener( new View.OnClickListener()
        {
            public void onClick (View v){
                abrirOpcionesAdicionales(v);
            }
        });
    }

    public void abrirOpcionesAdicionales(View v) {
        Intent intent = new Intent(this, OpcionesAdicionales.class);
        startActivity(intent);
    }

    public void abrirCrearTragos(View v) {
        Intent intent = new Intent(this, crearTragos.class);
        startActivity(intent);
    }
}
