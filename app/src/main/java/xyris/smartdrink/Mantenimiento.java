package xyris.smartdrink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import ar.edu.xyris.smartdrinks.messages.preparacion.PreparaBebidaRequest;
import xyris.smartdrink.http.WebServiceClient;

public class Mantenimiento extends AppCompatActivity {

    String cantidad = "0";
    String cantidadBebidasPreparadas = "Bebidas preparadas: ";
    ArrayList<String> listFechas = new ArrayList<String>();
    ListView lvFechas;
    ArrayList<String> items = new ArrayList<String>();

    JSONObject responseReader;
    private String idDevice;
    private String modoViernesStatus;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        modoViernesStatus = sp.getString("modoViernes", "ERROR");

        if(modoViernesStatus.equals("activado")) {
            setContentView(R.layout.mantenimiento_viernes);
        } else {
            setContentView(R.layout.mantenimiento);
        }

        final Button buttonMantenimientoVolver = (Button) findViewById(R.id.buttonMantenimientoVolver);
        final Button buttonMantenimientoRealizado = (Button) findViewById(R.id.buttonMantenimientoRealizado);
        cantidadBebidasPreparadas = findViewById(R.id.tvCantidadBebidasPreparadas).toString();

        idDevice = sp.getString("idDevice","ERROR");

        //Se obtiene la cantidad de bebidas preparadas de la base de datos.
//        obtenerCantidadBebidasPreparadas();

        items.add("16/09/2018");
        items.add("18/09/2018");
        items.add("20/09/2018");

        lvFechas = (ListView) findViewById(R.id.lvFechaNotificaciones);

        lvFechas.setAdapter(new AdapterMantenimiento(this, items));

        buttonMantenimientoRealizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo: Mantenimiento realizado - enviar mensaje para resetear contador & vaciar lista de fechas.
                //enviarMensajeLimpiezaRealizada();

            }
        });

        buttonMantenimientoVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void obtenerCantidadBebidasPreparadas(){
        Thread thread = new Thread(){
            public void run(){
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("idDispositivo",idDevice);
                //Se obtiene la fecha y hora actual y se le aplica el formato que necesita recibir el mensaje.
                //A "fechaHoraPeticion" se deberá asignar "currentFormattedDate ".
                Date currentDate = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String currentFormattedDate  = df.format(currentDate);
                params.put("fechaHoraPeticion", currentFormattedDate);

                WebServiceClient cli = new WebServiceClient("/consultarCantidadBebidasPreparadas", new JSONObject(params));

                responseReader = (JSONObject) cli.getResponse();

                Log.d("SMARTDRINKS_CANTBEBIDAS","CANTIDAD_BEBIDAS_PREPARADAS: " + responseReader.toString());
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cantidad = responseReader.toString();
    }


    public void enviarMensajeLimpiezaRealizada(){
        //PreparaBebidaRequest request = new PreparaBebidaRequest();


        Thread thread = new Thread(){
            public void run(){
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("idDispositivo",idDevice);
                //Se obtiene la fecha y hora actual y se le aplica el formato que necesita recibir el mensaje.
                //A "fechaHoraPeticion" se deberá asignar "currentFormattedDate ".
                Date currentDate = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String currentFormattedDate  = df.format(currentDate);
                params.put("fechaHoraPeticion", currentFormattedDate);
                params.put("limpiezaRealizada", "true");

                ObjectMapper mapper = new ObjectMapper();
                JSONObject object = null;
                try {
                    //object = new JSONObject(mapper.writeValueAsString(request));
                } catch (Exception e) {

                }

                WebServiceClient cli = new WebServiceClient("/consultarBebidas", new JSONObject(params));

                responseReader = (JSONObject) cli.getResponse();

                Log.d("SMARTDRINKS_BEBIDAS","RESPUESTA_BEBIDAS: " + responseReader.toString());
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