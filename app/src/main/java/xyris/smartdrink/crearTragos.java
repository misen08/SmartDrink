package xyris.smartdrink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class crearTragos extends AppCompatActivity {

    Integer porcentajeTotal = 100;
    HashMap<String, Integer> configTrago = new HashMap<String, Integer>() {
        //Deberian ser valores que se obtengan de la DB. Hardcodeados de momento para ir probando..
        {
            put("Naranja", 0);
            put("Manzana", 0);
            put("Durazno", 0);
            put("Pera", 0);
            put("Pomelo blanco", 0);
            put("Pomelo rosado", 0);
        }
    };

    Integer porcentajeGusto1 = 0,
            porcentajeGusto2 = 0,
            porcentajeGusto3 = 0,
            porcentajeGusto4 = 0,
            porcentajeGusto5 = 0,
            porcentajeGusto6 = 0;
    Integer[] porcentajes = {0,10,20,30,40,50,60,70,80,90,100};

    String nombreBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tragos);

        //String[] porcentajes = {"","10%","20%","30%","40%","50%","60%","70%","80%","90%", "100%"};

        final Button botonCrear = (Button) findViewById(R.id.botonAgregar);
        final Button botonVolver = (Button) findViewById(R.id.botonVolver);
        final EditText editTextNombreBebida = (EditText) findViewById(R.id.editTextNombreBebida);

        nombreBebida = editTextNombreBebida.getText().toString();

        Spinner listaGusto1 = (Spinner) findViewById(R.id.listaPorcentajes1);
        listaGusto1.setAdapter(new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, porcentajes));
        listaGusto1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                porcentajeGusto1 = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
                //textGusto1 = arg0.getItemAtPosition(pos).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        Spinner listaGusto2 = (Spinner) findViewById(R.id.listaPorcentajes2);
        listaGusto2.setAdapter(new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, porcentajes));
        listaGusto2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                porcentajeGusto2 = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
                //textGusto1 = arg0.getItemAtPosition(pos).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        Spinner listaGusto3 = (Spinner) findViewById(R.id.listaPorcentajes3);
        listaGusto3.setAdapter(new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, porcentajes));
        listaGusto3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                porcentajeGusto3 = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
                //textGusto1 = arg0.getItemAtPosition(pos).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        Spinner listaGusto4 = (Spinner) findViewById(R.id.listaPorcentajes4);
        listaGusto4.setAdapter(new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, porcentajes));

        listaGusto4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                porcentajeGusto4 = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        Spinner listaGusto5 = (Spinner) findViewById(R.id.listaPorcentajes5);
        listaGusto5.setAdapter(new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, porcentajes));

        listaGusto5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                porcentajeGusto5 = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
                //textGusto1 = arg0.getItemAtPosition(pos).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        Spinner listaGusto6 = (Spinner) findViewById(R.id.listaPorcentajes6);
        listaGusto6.setAdapter(new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, porcentajes));

        listaGusto6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                porcentajeGusto6 = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


//        Spinner listaGusto7 = (Spinner) findViewById(R.id.listaPorcentajes7);
//        listaGusto7.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
//        Spinner listaGusto8 = (Spinner) findViewById(R.id.listaPorcentajes8);
//        listaGusto8.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
//        Spinner listaGusto9 = (Spinner) findViewById(R.id.listaPorcentajes9);
//        listaGusto9.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));
//        Spinner listaGusto10 = (Spinner) findViewById(R.id.listaPorcentajes10);
//        listaGusto10.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, porcentajes));



        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                porcentajeTotal = porcentajeGusto1 + porcentajeGusto2 + porcentajeGusto3 + porcentajeGusto4 + porcentajeGusto5 + porcentajeGusto6;

                Log.d("nombre2", nombreBebida);
                Log.d("test", "test");
                if(porcentajeTotal == 100){
                    if(!nombreBebida.isEmpty()){
                        configTrago.put("Naranja",porcentajeGusto1);
                        configTrago.put("Manzana",porcentajeGusto2);
                        configTrago.put("Durazno",porcentajeGusto3);
                        configTrago.put("Pera",porcentajeGusto4);
                        configTrago.put("Pomelo rosado",porcentajeGusto5);
                        configTrago.put("Pomelo blanco",porcentajeGusto6);

                        Log.d("tag", configTrago.toString());
                        Log.d("nombre", nombreBebida);
                        Toast.makeText(botonCrear.getContext(),"Agregado a la lista",Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(botonCrear.getContext(),"Por favor asigne un nombre a su bebida.",Toast.LENGTH_SHORT).show();
                    }
                }
                    else {
                        Toast.makeText(botonCrear.getContext(),"El porcentaje es distinto de 100",Toast.LENGTH_SHORT).show();
                    }
            }
        });

//        botonVolver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent abrirOpcionesAdicionales = new Intent(crearTragos.this, OpcionesAdicionales.class);
//                startActivity(
//                        abrirOpcionesAdicionales
//                );
//            }
//        });


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
