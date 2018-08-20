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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import xyris.smartdrink.entities.SaborEnBotella;
import xyris.smartdrink.http.WebServiceClient;

//import xyris.smartdrink.entities.Bebida;
//import xyris.smartdrink.entities.SaborEnBebida;

public class CrearTragos extends AppCompatActivity {

    Integer porcentajeTotal = 0;
    String responseSabores;
    JSONObject responseReader;

    TextView tvNombreGusto1;
    TextView tvNombreGusto2;
    TextView tvNombreGusto3;
    TextView tvNombreGusto4;
    TextView tvNombreGusto5;
    TextView tvNombreGusto6;

    HashMap<String, Integer> configTrago = new HashMap<String, Integer>();

    ArrayList<SaborEnBotella> listSaborEnBotella = new ArrayList<SaborEnBotella>();
    Integer[] porcentajeGustos = {0, 0, 0, 0, 0, 0};
    Integer[] porcentajes = {0,10,20,30,40,50,60,70,80,90,100};

    String nombreBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tragos);

       Thread thread = new Thread(){
           public void run(){
               HashMap<String,String> params = new HashMap<String,String>();
               params.put("idDispositivo","8173924678916234");
               params.put("fechaHoraPeticion", "2018-08-04T15:22:00");

               WebServiceClient cli = new WebServiceClient("/consultarSabores", new JSONObject(params));

               responseReader = (JSONObject) cli.getResponse();

               Log.d("SMARTDRINKS","RESPUESTA: " + responseReader.toString());
           }
       };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Log.d("jsonObject", ""+ jsonObject.toString());
         listSaborEnBotella = parsearSaborEnBotella(responseReader.toString());

        for(int i=0; i < listSaborEnBotella.size() ; i++ ){
            configTrago.put(listSaborEnBotella.get(i).getDescripcion(), porcentajeGustos[i]);
        }

        final Button botonCrear = (Button) findViewById(R.id.botonAgregar);
        final Button botonVolver = (Button) findViewById(R.id.botonVolver);
        final EditText editTextNombreBebida = (EditText) findViewById(R.id.editTextNombreBebida);

        // Creacion de los TextView estáticos de la pantalla crear tragos
        tvNombreGusto1 = (TextView) findViewById(R.id.textViewGusto1);
        tvNombreGusto2 = (TextView) findViewById(R.id.textViewGusto2);
        tvNombreGusto3 = (TextView) findViewById(R.id.textViewGusto3);
        tvNombreGusto4 = (TextView) findViewById(R.id.textViewGusto4);
        tvNombreGusto5 = (TextView) findViewById(R.id.textViewGusto5);
        tvNombreGusto6 = (TextView) findViewById(R.id.textViewGusto6);

        // Asignación de los nombres de cada uno de los TextView
        // Se obtinen desde la BD
        tvNombreGusto1.setText(listSaborEnBotella.get(0).getDescripcion());
        tvNombreGusto2.setText(listSaborEnBotella.get(1).getDescripcion());
        tvNombreGusto3.setText(listSaborEnBotella.get(2).getDescripcion());
        tvNombreGusto4.setText(listSaborEnBotella.get(3).getDescripcion());
        tvNombreGusto5.setText(listSaborEnBotella.get(4).getDescripcion());
        tvNombreGusto6.setText(listSaborEnBotella.get(5).getDescripcion());

        Spinner listaGusto1 = (Spinner) findViewById(R.id.spinnerPorcentajesGusto1);
        listaGusto1.setAdapter(new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, porcentajes));
        listaGusto1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
                porcentajeGustos[0] = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
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
                porcentajeGustos[1] = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
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
                porcentajeGustos[2] = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
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
                porcentajeGustos[3] = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
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
                porcentajeGustos[4] = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
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
                porcentajeGustos[5] = Integer.parseInt(arg0.getItemAtPosition(pos).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreBebida = editTextNombreBebida.getText().toString();
                porcentajeTotal = 0;

                for(int i =0; i < porcentajeGustos.length; i++){
                    porcentajeTotal += porcentajeGustos[i];
                }

                Log.d("nombre2", nombreBebida);
                Log.d("test", "test");


                    if ((nombreBebida.isEmpty()) || porcentajeTotal != 100) {

                        if (nombreBebida.isEmpty()) {
                            Toast.makeText(botonCrear.getContext(), "Por favor asigne un nombre a su bebida.", Toast.LENGTH_SHORT).show();
                        }

                        if (porcentajeTotal != 100) {
                            Toast.makeText(botonCrear.getContext(), "El porcentaje es distinto de 100.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        for(int i = 0; i < listSaborEnBotella.size() ; i++) {
                            configTrago.put(listSaborEnBotella.get(i).getDescripcion(), porcentajeGustos[i]);

                        }
                        enviarMensajeAgregarBebida();
                        Toast.makeText(botonCrear.getContext(),"Agregado a la lista",Toast.LENGTH_SHORT).show();
                        Log.d("tag", configTrago.toString());
                        Log.d("nombre", nombreBebida);
                        finish();
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

    public void mandarMensaje(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.35:8080/consultarSabores";
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


    public void enviarMensajeAgregarBebida(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.35:8080/consultarSabores";
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("idDispositivo","8173924678916234");
        params.put("fechaHoraPeticion", "2018-08-04T15:22:00");

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override public void onResponse(JSONObject response)
                    {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
//                            Toast.makeText(getApplicationContext(),"Response:%n %s" + response.toString(4),
//                                    Toast.LENGTH_SHORT).show();
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



    public void enviarMensajeConsultarSabores(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.35:8080/consultarSabores";
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("idDispositivo","8173924678916234");
        params.put("fechaHoraPeticion", "2018-08-04T15:22:00");


        try {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                VolleyLog.v("Response:%n %s", response.toString(4));
                                responseSabores = response.toString();
                                Log.d("tag", "fs" + responseSabores);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    Toast.makeText(getApplicationContext(), "Response:%n %s" + error.getMessage() + error.getStackTrace(),
                            Toast.LENGTH_SHORT).show();
                }
            });
// Add the request to the RequestQueue.
            queue.add(req);
        }catch (Exception e){
            Log.d("DEBUG","FALLE!!",e);
        }
    }


    public ArrayList<SaborEnBotella> parsearSaborEnBotella (String response) {

        ArrayList<SaborEnBotella> listSaboresEnBotella = new ArrayList<SaborEnBotella>();

        try {
            responseReader = new JSONObject(response);
            String codigoError = responseReader.getString("codigoError");

            if("0".equals(codigoError.toString())){
                // Se obtiene el nodo del array "sabores"
                JSONArray sabores = responseReader.getJSONArray("sabores");

                // Ciclando en todos los sabores
                for (int i = 0; i < sabores.length(); i++) {
                    JSONObject sabor = sabores.getJSONObject(i);
                    String idSabor = sabor.getString("idSabor");
                    String descripcion = sabor.getString("descripcion");
                    String habilitado = sabor.getString("habilitado");

                    SaborEnBotella saborEnBotella = new SaborEnBotella(idSabor, descripcion, habilitado);
                    listSaboresEnBotella.add(saborEnBotella);
                }
            } else {
                // TODO: manejar codigos de error de consultarSabores
            }

        } catch (JSONException e) { e.printStackTrace(); }

        return listSaboresEnBotella;
    }

}
