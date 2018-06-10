package xyris.smartdrink;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class listaDeTragos extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_tragos);


        FloatingActionButton botonCrearTrago = (FloatingActionButton) findViewById(R.id.botonCrearTrago);
        final Button botonOpcionesAdicionales = (Button) findViewById(R.id.buttonOpcionesAdicionales);


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

        ArrayList<CategoryList> listaTragos = new ArrayList<CategoryList>();
        ListView lv = (ListView) findViewById(R.id.listaTragos);
        AdapterItem adapter = new AdapterItem(this, listaTragos);
        lv.setAdapter(adapter);
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
