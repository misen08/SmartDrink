package xyris.smartdrink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class crearTragos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tragos);

        String[] porcentajes = {"","10%","20%","30%","40%","50%","60%","70%","80%","90%"};

        final Button botonCrear = (Button) findViewById(R.id.botonAgregar);
        final Button botonVolver = (Button) findViewById(R.id.botonVolver);


        Spinner listaGusto1 = (Spinner) findViewById(R.id.listaPorcentajes1);
        listaGusto1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
        Spinner listaGusto2 = (Spinner) findViewById(R.id.listaPorcentajes2);
        listaGusto2.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
        Spinner listaGusto3 = (Spinner) findViewById(R.id.listaPorcentajes3);
        listaGusto3.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
        Spinner listaGusto4 = (Spinner) findViewById(R.id.listaPorcentajes4);
        listaGusto4.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
        Spinner listaGusto5 = (Spinner) findViewById(R.id.listaPorcentajes5);
        listaGusto5.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
        Spinner listaGusto6 = (Spinner) findViewById(R.id.listaPorcentajes6);
        listaGusto6.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
        Spinner listaGusto7 = (Spinner) findViewById(R.id.listaPorcentajes7);
        listaGusto7.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
        Spinner listaGusto8 = (Spinner) findViewById(R.id.listaPorcentajes8);
        listaGusto8.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
        Spinner listaGusto9 = (Spinner) findViewById(R.id.listaPorcentajes9);
        listaGusto9.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
        Spinner listaGusto10 = (Spinner) findViewById(R.id.listaPorcentajes10);
        listaGusto10.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));


        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if suma de porcentajes distinto de 0 -> Mal armado!!!
                Toast.makeText(botonCrear.getContext(),"Agregado a la lista",Toast.LENGTH_SHORT).show();
            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volverPantallaPrincipal = new Intent(crearTragos.this, listaDeTragos.class);
                startActivity(
                        volverPantallaPrincipal
                );
            }
        });
    }
}
