package xyris.smartdrink;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import java.util.Calendar;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import ar.edu.xyris.smartdrinks.messages.creacion.bebida.CreaBebidaRequest;
import xyris.smartdrink.entities.Bebida;
import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.entities.SaborEnBebida;
import xyris.smartdrink.entities.SaborEnBotella;
import xyris.smartdrink.http.WebServiceClient;

public class CrearTragos extends AppCompatActivity {

    Integer porcentajeTotal = 0;
    JSONObject responseReader;

    TextView tvNombreGusto1;
    TextView tvNombreGusto2;
    TextView tvNombreGusto3;
    TextView tvNombreGusto4;
    TextView tvNombreGusto5;
    TextView tvNombreGusto6;

    HashMap<String, Integer> configTrago = new HashMap<String, Integer>();

    Boolean nombreBebidasExists = false;

    ArrayList<String> nombreBebidasExistentes = new  ArrayList<String>();
    ArrayList<SaborEnBotella> listSaborEnBotella = new ArrayList<SaborEnBotella>();
    Integer[] porcentajeGustos = {0, 0, 0, 0, 0, 0};
    Integer[] porcentajes = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

    String nombreBebida = "";
    private String idDevice;
    private String modoViernesStatus;
    private String resPantalla;
    SharedPreferences sp;
    SharedPreferences.Editor modoViernesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        resPantalla = sp.getString("resolucionPantalla", "ERROR");

        if (resPantalla.equals("800")) {
            if (sp.getString("modoViernes", "ERROR").equals("activado")) {
                setContentView(R.layout.activity_crear_tragos_viernes);
            } else {
                setContentView(R.layout.activity_crear_tragos);
            }
        } else {
            if (sp.getString("modoViernes", "ERROR").equals("activado")) {
                setContentView(R.layout.activity_crear_tragos_viernes);
            } else {
                setContentView(R.layout.activity_crear_tragos);
            }
        }

        nombreBebidasExistentes = getIntent().getExtras().getStringArrayList("nombreBebidasExistentes");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        idDevice = sp.getString("idDevice", "ERROR");

        Thread thread = new Thread() {
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("idDispositivo", idDevice);
                params.put("fechaHoraPeticion", new FechaHora().formatDate(Calendar.getInstance().getTime()));

                WebServiceClient cli = new WebServiceClient("/consultarSabores", new JSONObject(params));

                responseReader = (JSONObject) cli.getResponse();

                Log.d("SMARTDRINKS", "RESPUESTA: " + responseReader.toString());
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listSaborEnBotella = new SaborEnBotella().parsearSaborEnBotella(responseReader.toString());

        for (int i = 0; i < listSaborEnBotella.size(); i++) {
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
        // Se obtienen desde la BD
        tvNombreGusto1.setText(listSaborEnBotella.get(0).getDescripcion());
        tvNombreGusto2.setText(listSaborEnBotella.get(1).getDescripcion());
        tvNombreGusto3.setText(listSaborEnBotella.get(2).getDescripcion());
        tvNombreGusto4.setText(listSaborEnBotella.get(3).getDescripcion());
        tvNombreGusto5.setText(listSaborEnBotella.get(4).getDescripcion());
        tvNombreGusto6.setText(listSaborEnBotella.get(5).getDescripcion());

        Spinner listaGusto1 = (Spinner) findViewById(R.id.spinnerPorcentajesGusto1);
        listaGusto1.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, porcentajes));
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
        listaGusto2.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, porcentajes));
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
        listaGusto3.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, porcentajes));
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
        listaGusto4.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, porcentajes));
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
        listaGusto5.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, porcentajes));
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
        listaGusto6.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, porcentajes));
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

                ArrayList<SaborEnBebida> saboresNuevos = new ArrayList<SaborEnBebida>();

                for(int j = 0; j < nombreBebidasExistentes.size(); j++) {
                    if (editTextNombreBebida.getText().toString().equals(nombreBebidasExistentes.get(j))) {
                        nombreBebidasExists = true;
                    }
                }
                if(!nombreBebidasExists){
                    nombreBebida = editTextNombreBebida.getText().toString();
                }

                porcentajeTotal = 0;

                for (int i = 0; i < porcentajeGustos.length; i++) {
                    if (porcentajeGustos[i] != 0) {
                        SaborEnBebida sabor = new SaborEnBebida(listSaborEnBotella.get(i).getIdSabor(), listSaborEnBotella.get(i).getDescripcion(), porcentajeGustos[i].toString());
                        saboresNuevos.add(sabor);
                    }
                    porcentajeTotal += porcentajeGustos[i];
                }

                Log.d("nombre2", nombreBebida);
                Log.d("test", "test");

                if ((nombreBebida.isEmpty()) || porcentajeTotal != 100 || saboresNuevos.size() > 4) {

                    if (nombreBebida.isEmpty()) {
                        if (nombreBebidasExists) {
                            Toast.makeText(botonCrear.getContext(), "El nombre ingresado ya existe. " +
                                    "Por favor asigne otro nombre a su bebida.", Toast.LENGTH_SHORT).show();
                            nombreBebidasExists = false;
                        } else {
                            Toast.makeText(botonCrear.getContext(), "Por favor asigne un nombre a su bebida.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (porcentajeTotal != 100) {
                        Toast.makeText(botonCrear.getContext(), "El porcentaje es distinto de 100.", Toast.LENGTH_SHORT).show();
                    }

                    if (saboresNuevos.size() > 4) {
                        Toast.makeText(botonCrear.getContext(), "No puede utilizar más de 4 gustos.", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    for (int i = 0; i < listSaborEnBotella.size(); i++) {
                        configTrago.put(listSaborEnBotella.get(i).getDescripcion(), porcentajeGustos[i]);
                    }

                    Bebida bebidaNueva = new Bebida("0", nombreBebida, "disponible", saboresNuevos);
                    enviarMensajeAgregarBebida(bebidaNueva);
                    Toast.makeText(botonCrear.getContext(), "Agregado a la lista", Toast.LENGTH_SHORT).show();
                    Log.d("tag", configTrago.toString());
                    Log.d("nombre", nombreBebida);

                    Intent returnIntent = new Intent();
                    boolean result = true;
                    returnIntent.putExtra("result", result);
                    setResult(Activity.RESULT_OK, returnIntent);
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

    public void enviarMensajeAgregarBebida(Bebida bebida) {

        CreaBebidaRequest request = new CreaBebidaRequest();
        request.setBebida(bebida);
        request.setIdDispositivo(idDevice);
        request.setFechaHoraPeticion(new FechaHora().formatDate(Calendar.getInstance().getTime()));
        ObjectMapper mapper = new ObjectMapper();
        JSONObject object = null;
        try {
            object = new JSONObject(mapper.writeValueAsString(request));
        } catch (Exception e) {

        }

        final JSONObject finalObject = object;
        Thread thread = new Thread() {
            public void run() {

                WebServiceClient cli = new WebServiceClient("/crearBebida", finalObject);

                responseReader = (JSONObject) cli.getResponse();

                Log.d("SMARTDRINKS_BEBIDAS", "RESPUESTA_BEBIDAS: " + responseReader.toString());
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}