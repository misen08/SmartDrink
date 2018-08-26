package xyris.smartdrink;


import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.xyris.smartdrinks.messages.creacion.bebida.CreaBebidaRequest;
import ar.edu.xyris.smartdrinks.messages.preparacion.PreparaBebidaRequest;
import xyris.smartdrink.entities.Bebida;
import xyris.smartdrink.entities.PedidoBebida;
import xyris.smartdrink.http.WebServiceClient;


public class OpcionesAdicionales  extends AppCompatActivity {

    Boolean conHielo;
    Boolean agitado;
    Button botonProgramarBebida;
    Button botonPrepararAhora;
    CheckBox agregarHielo;
    CheckBox agitarBebida;

    JSONObject responseReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_adicionales);

        //String urlGif = "https://domain.com/myanimatedgif.gif";
        //Agregar implementacion Glide dentro de archivo build.gradle.
        //ImageView imgIceCube = (ImageView)findViewById(R.id.imageView2);
        //Uri uri = Uri.parse(urlGif);
        //Glide.with(getApplicationContext()).load(uri).into(imgIceCube);

        botonProgramarBebida = (Button) findViewById(R.id.botonProgramarBebida);
        botonPrepararAhora = (Button) findViewById(R.id.botonPrepararAhora);
        agregarHielo = (CheckBox)findViewById(R.id.checkBoxAgregarHielo);
        agitarBebida = (CheckBox)findViewById(R.id.checkBoxMezclarBebida);
        botonProgramarBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent progBebida = new Intent(OpcionesAdicionales.this, ProgramarBebida.class);
                startActivity(progBebida);
            }
        });

        botonPrepararAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idBebida = getIntent().getExtras().getString("idBebida");
                verficarFlags();
                String hielo = conHielo.toString();
                String esAgitado = agitado.toString();
                enviarMensajePrepararBebida(idBebida, hielo, esAgitado);
                Intent prepararTrago = new Intent(OpcionesAdicionales.this, PreparandoTrago.class);
                startActivity(prepararTrago);
            }
        });
    }

    //Se obtiene el estado de las opciones "AgregarHielo" y "MezclarBebida".
    public void verficarFlags() {
        conHielo = agregarHielo.isChecked();
        agitado = agitarBebida.isChecked();
    }

    public void abrirPreparandoTrago(View v) {
        Intent intent = new Intent(this, PreparandoTrago.class);
        startActivity(intent);
    }


    public void enviarMensajePrepararBebida(String idBebida, String hielo, String agitado){

        PreparaBebidaRequest request = new PreparaBebidaRequest();

        //La fecha y hora no se tienen en cuenta ya que el pedido se preparará en el momento.
        PedidoBebida pedidoBebida = new PedidoBebida(idBebida, hielo, agitado,
                "false", "2018-01-01T00:00:00");

        request.setPedidoBebida(pedidoBebida);
        request.setIdDispositivo("compu_Fede");
        request.setFechaHoraPeticion("2018-08-04T15:22:00");
        ObjectMapper mapper = new ObjectMapper();
        JSONObject object = null;
        try {
            object = new JSONObject(mapper.writeValueAsString(request));
        } catch (Exception e) {

        }

        final JSONObject finalObject = object;
        Thread thread = new Thread(){
            public void run(){

                WebServiceClient cli = new WebServiceClient("/prepararBebida", finalObject);

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
