package xyris.smartdrink;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
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
    TextView tvMusica;
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
    String codigoErrorPrepararBebida = "1";
    String descripcionPrepararBebida;

    private String idDevice;
    private String resPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        modoViernesStatus = sp.getString("modoViernes", "ERROR");
        idDevice = sp.getString("idDevice", "ERROR");
        resPantalla = sp.getString("resolucionPantalla", "ERROR");

        if (resPantalla.equals("800")) {
            if (sp.getString("modoViernes", "ERROR").equals("activado")) {
                setContentView(R.layout.preparando_trago_tablet);
            } else {
                setContentView(R.layout.preparando_trago_tablet);
            }
        } else {
            if (sp.getString("modoViernes", "ERROR").equals("activado")) {
                setContentView(R.layout.preparando_trago_viernes);
            } else {
                setContentView(R.layout.preparando_trago);
            }
        }

        tvPreparandoTrago = (TextView) findViewById(R.id.textViewPreparando);
        descripcionBebida = getIntent().getExtras().get("descripcionBebida").toString();
        tvPreparandoTrago.setText("Tu bebida \n \"" + descripcionBebida + "\" \n está siendo preparada");

        tvMusica = (TextView) findViewById(R.id.textViewMusica);
        tvMusica.setText("Desactivar música");
        btnMusic = findViewById(R.id.btnMusic);
        if (resPantalla.equals("800"))
            btnMusic.setImageResource(R.drawable.audio_no_tablet);
        else btnMusic.setImageResource(R.drawable.audio_no);

        songModoNormal = MediaPlayer.create(PreparandoTrago.this, R.raw.song_default);
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


        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (botonStartMusicStatus) {
                    case STATUS_TRUE:
                        botonStartMusicStatus = "false";
                        if (resPantalla.equals("800"))
                            btnMusic.setImageResource(R.drawable.audio_no_tablet);
                        else btnMusic.setImageResource(R.drawable.audio_no);
                        tvMusica.setText("Desactivar musica");
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
                        if (resPantalla.equals("800"))
                            btnMusic.setImageResource(R.drawable.audio_si_tablet);
                        else btnMusic.setImageResource(R.drawable.audio_si);
                        tvMusica.setText("Activar musica");
                        break;
                    default:
                        break;
                }
            }
        });

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                enviarMensajePrepararBebidaAhora(idBebida, hielo, esAgitado);
            }
        });
        t.start();

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


    public void enviarMensajePrepararBebidaAhora(final String idBebida, String hielo, String agitado) {

        PreparaBebidaRequest request = new PreparaBebidaRequest();

        //La fecha y hora no se tienen en cuenta ya que el pedido se preparará en el momento.
        //Agendado posee valor "FALSE".
        PedidoBebida pedidoBebida = new PedidoBebida(idBebida, hielo, agitado,
                "false", "");

        request.setPedidoBebida(pedidoBebida);
        request.setIdDispositivo(idDevice);
        request.setFechaHoraPeticion(new FechaHora().formatDate(Calendar.getInstance().getTime()));

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

        try {
            codigoErrorPrepararBebida = responseReader.getString("codigoError");
            descripcionPrepararBebida = responseReader.getString("descripcionError");
        } catch (JSONException e) { e.printStackTrace(); }


        if(codigoErrorPrepararBebida.equals("0")) {
            Intent returnIntent = new Intent();
            boolean result = true;
            returnIntent.putExtra("result", result);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            Intent returnIntent = new Intent();
            boolean result = false;
            returnIntent.putExtra("result", result);
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }
    }


//    public void abrirCuadroDialogo() {
//
//        String titleDelete = "Preparación finalizada";
//        String messageDelete = "Su bebida se encuentra lista!";
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        if (titleDelete != null) builder.setTitle(titleDelete);
//
//        builder.setMessage(messageDelete);
//        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                Intent returnIntent = new Intent();
//               boolean result = true;
//                returnIntent.putExtra("result", result);
//                setResult(Activity.RESULT_OK, returnIntent);
//                finish();
//
//                if ("0".equals(codigoErrorPrepararBebida)) {
//                    Toast.makeText(PreparandoTrago.this, "BEBIDA PREPARADA CORRECTAMENTE.",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(PreparandoTrago.this, descripcionPrepararBebida + " " +
//                                    "Código de error: " + codigoErrorPrepararBebida,
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        builder.show();
//    }
}