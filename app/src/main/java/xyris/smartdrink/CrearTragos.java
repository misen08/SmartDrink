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

import java.util.ArrayList;
import java.util.HashMap;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

//import xyris.smartdrink.entities.Bebida;
//import xyris.smartdrink.entities.SaborEnBebida;

public class CrearTragos extends AppCompatActivity {

    //ArrayList<SaborEnBebida> listSaborEnBebida;

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

    //public ArrayList<SaborEnBebida> getListSaborEnBebida() {
    //    return listSaborEnBebida;
    //}

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

        Spinner listaGusto1 = (Spinner) findViewById(R.id.spinnerPorcentajesGusto1);
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


        Spinner listaGusto2 = (Spinner) findViewById(R.id.spinnerPorcentajesGusto2);
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

        Spinner listaGusto3 = (Spinner) findViewById(R.id.spinnerPorcentajesGusto3);
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

        Spinner listaGusto4 = (Spinner) findViewById(R.id.spinnerPorcentajesGusto4);
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


        Spinner listaGusto5 = (Spinner) findViewById(R.id.spinnerPorcentajesGusto5);
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


        Spinner listaGusto6 = (Spinner) findViewById(R.id.spinnerPorcentajesGusto6);
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


        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                porcentajeTotal = porcentajeGusto1 + porcentajeGusto2 + porcentajeGusto3 + porcentajeGusto4 + porcentajeGusto5 + porcentajeGusto6;

                Log.d("nombre2", nombreBebida);
                Log.d("test", "test");
                if(porcentajeTotal == 100){

                    if(1==1){ //!nombreBebida.isEmpty()
                        configTrago.put("Naranja",porcentajeGusto1);
                        configTrago.put("Manzana",porcentajeGusto2);
                        configTrago.put("Durazno",porcentajeGusto3);
                        configTrago.put("Pera",porcentajeGusto4);
                        configTrago.put("Pomelo rosado",porcentajeGusto5);
                        configTrago.put("Pomelo blanco",porcentajeGusto6);

                        Log.d("tag", configTrago.toString());
                        Log.d("nombre", nombreBebida);

                        mandarMensaje();

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

    botonVolver.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }

    });
}

    private void mandarMensaje(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.35:8080/consultarBebidas";
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("idDispositivo","8173924678916234");
        params.put("fechaHoraPeticion", "2018-08-04T15:22:00");

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
            @Override public void onResponse(JSONObject response)
            {
                try {
                    VolleyLog.v("Response:%n %s", response.toString(4));
                    Toast.makeText(getApplicationContext(),"Response:%n %s" + response.toString(4),
                            Toast.LENGTH_SHORT).show();
                } catch (JSONException e) { e.printStackTrace(); } }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),"Response:%n %s" + error.getMessage() + error.getStackTrace(),
                        Toast.LENGTH_SHORT).show();
            } });
// Add the request to the RequestQueue.
        queue.add(req);

    }
}
