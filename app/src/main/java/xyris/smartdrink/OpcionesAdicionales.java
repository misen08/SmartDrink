package xyris.smartdrink;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ar.edu.xyris.smartdrinks.messages.preparacion.PreparaBebidaRequest;
import xyris.smartdrink.entities.PedidoBebida;
import xyris.smartdrink.http.WebServiceClient;


public class OpcionesAdicionales  extends AppCompatActivity {

    Boolean conHielo;
    Boolean agitado;
    Button botonProgramarBebida;
    Button botonPrepararAhora;
    CheckBox agregarHielo;
    CheckBox agitarBebida;
    String idBebida;
    String modoViernesStatus;
    private String idDevice;


    JSONObject responseReader;

    private static final int PROGRAMAR_BEBIDA_ACTIVITY = 3;

    SharedPreferences sp;
    SharedPreferences.Editor modoViernesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        modoViernesStatus = sp.getString("modoViernes", "ERROR");

        if(modoViernesStatus.equals("activado")) {
            setContentView(R.layout.opciones_adicionales_viernes);
        } else {
            setContentView(R.layout.opciones_adicionales);
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        idDevice = sp.getString("idDevice","ERROR");

        modoViernesStatus = getIntent().getExtras().getString("modoViernes");
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
                verficarFlags();
                progBebida.putExtra("hielo", conHielo.toString());
                progBebida.putExtra("agitado", agitado.toString());
                progBebida.putExtra("idBebida", getIntent().getExtras().getString("idBebida"));
                startActivityForResult(progBebida, 3);
            }
        });

        botonPrepararAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idBebida = getIntent().getExtras().getString("idBebida");
                verficarFlags();
                String hielo = conHielo.toString();
                String esAgitado = agitado.toString();
                enviarMensajePrepararBebidaAhora(idBebida, hielo, esAgitado);
                Intent prepararTrago = new Intent(OpcionesAdicionales.this, PreparandoTrago.class);
                prepararTrago.putExtra("modoViernes", modoViernesStatus);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PROGRAMAR_BEBIDA_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {
                    finish();
                }
                break;

            default:
                break;
        }
    }


    public void enviarMensajePrepararBebidaAhora(String idBebida, String hielo, String agitado){

        PreparaBebidaRequest request = new PreparaBebidaRequest();

        //La fecha y hora no se tienen en cuenta ya que el pedido se preparará en el momento.
        //Agendado posee valor "FALSE".
        PedidoBebida pedidoBebida = new PedidoBebida(idBebida, hielo, agitado,
                "false", "");

        request.setPedidoBebida(pedidoBebida);
        request.setIdDispositivo(idDevice);
        //Se obtiene la fecha y hora actual y se le aplica el formato que necesita recibir el mensaje.
        //A "fechaHoraPeticion" se deberá asignar "currentFormattedDate".
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentFormattedDate = df.format(currentDate);
        request.setFechaHoraPeticion(currentFormattedDate);
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
