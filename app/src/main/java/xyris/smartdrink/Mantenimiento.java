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
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import ar.edu.xyris.smartdrinks.messages.Mantenimiento.RealizaMantenimientoRequest;
import ar.edu.xyris.smartdrinks.messages.creacion.bebida.CreaBebidaRequest;
import ar.edu.xyris.smartdrinks.messages.modificacion.ReseteaContadorRequest;
import ar.edu.xyris.smartdrinks.messages.preparacion.PreparaBebidaRequest;
import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.http.WebServiceClient;

public class Mantenimiento extends AppCompatActivity {

    String cantidad = "0";
    String cantidadBebidasPreparadas;
    TextView tvCantidadBebidasPreparadas;
    TextView tvFechaNotificacionMantenimiento;
    ArrayList<String> listFechas = new ArrayList<String>();
    ListView lvFechas;
    ArrayList<String> items = new ArrayList<String>();

    JSONObject responseReader;
    private String idDevice;
    private String modoViernesStatus;
    SharedPreferences sp;
    SharedPreferences.Editor dateMantenimientoEditor;
    String[] dateMantenimiento = new String[5];

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
        tvCantidadBebidasPreparadas = (TextView) findViewById(R.id.tvCantidadBebidasPreparadas);
        tvFechaNotificacionMantenimiento = (TextView) findViewById(R.id.tvFechaNotificacionMantenimiento);


        idDevice = sp.getString("idDevice","ERROR");

        String spDateMantenimiento = sp.getString("FECHAS_MANTENIMIENTO","ERROR");

        dateMantenimiento = spDateMantenimiento.split(",");
        Log.d("dateMantenimiento", dateMantenimiento[0]);

        //Se obtiene la cantidad de bebidas preparadas de la base de datos.
        obtenerCantidadBebidasPreparadas();
        tvCantidadBebidasPreparadas.setText("Cantidad de bebidas preparadas: " + cantidadBebidasPreparadas);



        //ToDo: Cada vez que se reciba una notificacion, se debe tomar la fecha actual y agregarla al listado.

        for(int i=0; i < dateMantenimiento.length ; i++){
            items.add(dateMantenimiento[i]);
        }

        if(spDateMantenimiento.equals("ERROR")){
            items.clear();
        }

        if(items.isEmpty()){
            tvFechaNotificacionMantenimiento.setText("No hay notificaciones");
        } else {
            tvFechaNotificacionMantenimiento.setText("Fecha de notificaciones de mantenimiento");
        }

        lvFechas = (ListView) findViewById(R.id.lvFechaNotificaciones);

        lvFechas.setAdapter(new AdapterMantenimiento(this, items));

        buttonMantenimientoRealizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo: Mantenimiento realizado - enviar mensaje para resetear contador & vaciar lista de fechas.
                enviarMensajeLimpiezaRealizada();
                vaciarListaFechas();
                Toast.makeText(Mantenimiento.this, "Se reseteo el contador.", Toast.LENGTH_SHORT).show();
                dateMantenimientoEditor = sp.edit();
                //Se borran todas las fechas del editor y se guarda el editor vacio mediante el commit.
                dateMantenimientoEditor.clear();
                dateMantenimientoEditor.commit();

//                finish();

            }
        });

        buttonMantenimientoVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void vaciarListaFechas(){
        items.clear();
        lvFechas.setAdapter(new AdapterMantenimiento(this, items));
        tvFechaNotificacionMantenimiento.setText("No hay notificaciones");
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

                WebServiceClient cli = new WebServiceClient("/consultarContadores", new JSONObject(params));
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

        try {

            cantidadBebidasPreparadas = responseReader.getString("cantidadBebidasPreparadas");

        } catch (JSONException e) { e.printStackTrace(); }


    }


    public void enviarMensajeLimpiezaRealizada(){
        ReseteaContadorRequest request = new ReseteaContadorRequest();

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

                WebServiceClient cli = new WebServiceClient("/resetearContador", finalObject);

                responseReader = (JSONObject) cli.getResponse();

                Log.d("SMARTDRINKS_RESETEA_CON", "RESETEAR_CONTADOR: " + responseReader.toString());
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

