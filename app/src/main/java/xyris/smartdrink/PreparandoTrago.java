package xyris.smartdrink;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import ar.edu.xyris.smartdrinks.messages.preparacion.PreparaBebidaRequest;
import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.entities.PedidoBebida;
import xyris.smartdrink.http.WebServiceClient;

public class PreparandoTrago  extends AppCompatActivity {

    TextView tvPreparandoTrago;
    Button btnCerrar;
    ImageButton btnMusic;
    String botonStartMusicStatus = "false";
    String modoViernesStatus;
    MediaPlayer songModoNormal;
    MediaPlayer songModoViernes;

    String hielo;
    String esAgitado;
    String idBebida;
    String descripcionBebida;

    private static final String STATUS_TRUE = "true";
    private static final String STATUS_FALSE = "false";

    SharedPreferences sp;
    JSONObject responseReader;
    String codigoErrorPrepararBebida;
    String descripcionPrepararBebida;

    private String idDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        modoViernesStatus = sp.getString("modoViernes", "ERROR");
        idDevice = sp.getString("idDevice", "ERROR");

        if (modoViernesStatus.equals("activado")) {
            setContentView(R.layout.preparando_trago);
        } else {
            setContentView(R.layout.preparando_trago);
        }

        tvPreparandoTrago = (TextView) findViewById(R.id.textViewPreparando);
        descripcionBebida = getIntent().getExtras().get("descripcionBebida").toString();
        tvPreparandoTrago.setText("Tu bebida \"" + descripcionBebida + "\" está siendo preparada.");

        btnMusic = findViewById(R.id.btnMusic);
        btnCerrar = findViewById(R.id.btnCerrar);

        songModoNormal = MediaPlayer.create(PreparandoTrago.this, R.raw.song_default)
        ;
        songModoViernes = MediaPlayer.create(PreparandoTrago.this, R.raw.song_viernes);

        if ("desactivado".equals(modoViernesStatus)) {
            songModoNormal.start();
        } else {
            songModoViernes.start();
        }


        //Se obtienen los valores para preparar la bebida.
        idBebida = getIntent().getExtras().get("idBebida").toString();
        hielo = getIntent().getExtras().get("hielo").toString();
        esAgitado = getIntent().getExtras().get("agitado").toString();

        enviarMensajePrepararBebidaAhora(idBebida, hielo, esAgitado);

        while(!("0".equals(codigoErrorPrepararBebida))){
            try {
                Thread.sleep(10000);
                Toast.makeText(this, "Sleep", Toast.LENGTH_SHORT).show();

                codigoErrorPrepararBebida = "0";
            } catch (Exception e) {
                ;
            }
        }


        String titleDelete = "Preparación finalizada";
        String messageDelete = "Su bebida se encuentra lista!";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (titleDelete != null) builder.setTitle(titleDelete);

        builder.setMessage(messageDelete);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent returnIntent = new Intent();
                boolean result = true;
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

                if("0".equals(codigoErrorPrepararBebida)){

                    Toast.makeText(PreparandoTrago.this, "BEBIDA PREPARADA CORRECTAMENTE.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PreparandoTrago.this, descripcionPrepararBebida + " " +
                                    "Código de error: " + codigoErrorPrepararBebida,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.show();



        if ("0".equals(codigoErrorPrepararBebida)) {

            Toast.makeText(PreparandoTrago.this, "Finalizó la preparación de la bebida.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(PreparandoTrago.this, descripcionPrepararBebida + " " +
                            "Código de error: " + codigoErrorPrepararBebida,
                    Toast.LENGTH_LONG).show();
        }

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (botonStartMusicStatus) {
                    case STATUS_TRUE:
                        botonStartMusicStatus = "false";
                        btnMusic.setImageResource(R.drawable.audio_si);
                        if ("desactivado".equals(modoViernesStatus)) {
                            songModoNormal.setVolume(1, 1);
                        } else {
                            songModoViernes.setVolume(1, 1);
                        }
                        break;

                    case STATUS_FALSE:

                        songModoNormal.setVolume(0, 0);
                        songModoViernes.setVolume(0, 0);
                        botonStartMusicStatus = "true";
                        btnMusic.setImageResource(R.drawable.audio_no);
                        break;
                    default:
                        break;
                }
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent returnIntent = new Intent();
                boolean result = true;
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("HOME PRESSED", "MYonStop is called");
        songModoNormal.pause();
        songModoViernes.pause();
    }

    @Override
    public void onBackPressed() {
        songModoNormal.stop();
        songModoViernes.stop();
        finish();
    }


    public void enviarMensajePrepararBebidaAhora(String idBebida, String hielo, String agitado) {
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

                WebServiceClient cli = new WebServiceClient("/prepararBebida", new JSONObject(params));

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

        try {
            codigoErrorPrepararBebida = responseReader.getString("codigoError");
            descripcionPrepararBebida = responseReader.getString("descripcionError");
        } catch (JSONException e) { e.printStackTrace(); }


        }

    }






