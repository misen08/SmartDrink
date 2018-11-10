package xyris.smartdrink;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.Calendar;

import ar.edu.xyris.smartdrinks.messages.preparacion.PreparaBebidaRequest;
import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.entities.PedidoBebida;
import xyris.smartdrink.http.WebServiceClient;

public class OpcionesAdicionales  extends AppCompatActivity {

    Boolean conHielo;
    Boolean agitado;
    Button botonProgramarBebida;
    Button botonPrepararAhora;
    CheckBox agregarHielo;
    CheckBox agitarBebida;
    TextView tvOpcionesAdicionales;

    int dialogTitleSize = 18;
    int dialogMessageSize = 16;
    int dialogYesBtnSize = 16;
    int dialogNoBtnSize = 16;
    int dialogAceptarBtnSize = 16;

    private String idDevice;
    private String modoViernesStatus;
    private String resPantalla;

    JSONObject responseReader;

    private static final int PROGRAMAR_BEBIDA_ACTIVITY = 3;
    private static final int PREPARAR_BEBIDA_AHORA_ACTIVITY = 4;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        idDevice = sp.getString("idDevice","ERROR");
        modoViernesStatus = sp.getString("modoViernes", "ERROR");
        resPantalla = sp.getString("resolucionPantalla", "ERROR");

        if (resPantalla.equals("800")) {
            //Si el dispositivo es la tablet, se asigna el tamaño grande para los textos de los dialog.
            dialogTitleSize = 38;
            dialogMessageSize = 32;
            dialogYesBtnSize = 32;
            dialogNoBtnSize = 32;
            dialogAceptarBtnSize = 32;

            if (modoViernesStatus.equals("activado")) {
                setContentView(R.layout.opciones_adicionales_tablet_viernes);
            } else {
                setContentView(R.layout.opciones_adicionales_tablet);
            }
        } else {
            if (modoViernesStatus.equals("activado")) {
                setContentView(R.layout.opciones_adicionales_viernes);
            } else {
                setContentView(R.layout.opciones_adicionales);
            }
        }

        tvOpcionesAdicionales = (TextView) findViewById(R.id.textViewOpcionesAdicionales);
        String descripcionBebida = getIntent().getExtras().get("descripcionBebida").toString();
        tvOpcionesAdicionales.setText("Completá tu bebida \n \"" + descripcionBebida + "\"");

        botonProgramarBebida = (Button) findViewById(R.id.botonProgramarBebida);
        botonPrepararAhora = (Button) findViewById(R.id.botonPrepararAhora);
        agregarHielo = (CheckBox)findViewById(R.id.checkBoxAgregarHielo);
        agitarBebida = (CheckBox)findViewById(R.id.checkBoxMezclarBebida);

        botonProgramarBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent progBebida = new Intent(OpcionesAdicionales.this, ProgramarBebida.class);
                verificarFlags();
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
                String descripcionBebida = getIntent().getExtras().getString("descripcionBebida");
                verificarFlags();
                Intent prepararTrago = new Intent(OpcionesAdicionales.this, PreparandoTrago.class);
                prepararTrago.putExtra("hielo", conHielo.toString());
                prepararTrago.putExtra("agitado", agitado.toString());
                prepararTrago.putExtra("idBebida", idBebida);
                prepararTrago.putExtra("descripcionBebida", descripcionBebida);
                startActivityForResult(prepararTrago, 4);
            }
        });
    }

    // Se obtiene el estado de las opciones "AgregarHielo" y "AgitarBebida".
    public void verificarFlags() {
        conHielo = agregarHielo.isChecked();
        agitado = agitarBebida.isChecked();
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

            case PREPARAR_BEBIDA_AHORA_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {
                    Intent returnIntent = new Intent();
                    boolean result = true;
                    returnIntent.putExtra("result",result);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                } else {
                    if(resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "Hay una bebida preparandose", Toast.LENGTH_SHORT).show();
                        String titleBebidaEnCurso = "No se puede preparar la bebida\n";
                        String messageBebidaEnCurso = "La bebida no puede prepararse debido a que" +
                                " existe otra preparación en curso";

                        AlertDialog alertDialog = new AlertDialog.Builder(OpcionesAdicionales.this).create();

                        // Set Custom Title
                        TextView title = new TextView(OpcionesAdicionales.this);
                        // Title Properties
                        title.setText(titleBebidaEnCurso);
                        title.setGravity(Gravity.CENTER);
                        title.setTextColor(Color.BLACK);
                        title.setTextSize(dialogTitleSize);
                        alertDialog.setCustomTitle(title);

                        // Set Message
                        TextView msg = new TextView(OpcionesAdicionales.this);
                        // Message Properties
                        msg.setText(messageBebidaEnCurso);
                        msg.setGravity(Gravity.CENTER_HORIZONTAL);
                        msg.setTextSize(dialogMessageSize);
                        alertDialog.setView(msg);

                        // Set Button
                        // you can more buttons
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        new Dialog(getApplicationContext());
                        alertDialog.show();

                        // Propiedades del botón "SI"
                        final Button yesBT = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        LinearLayout.LayoutParams yesBtnLP = (LinearLayout.LayoutParams) yesBT.getLayoutParams();
                        yesBtnLP.gravity = Gravity.FILL_HORIZONTAL;
                        yesBT.setPadding(50, 10, 10, 10);   // Set Position
                        yesBT.setTextSize(dialogYesBtnSize);
                        yesBT.setLayoutParams(yesBtnLP);

                        finish();
                    }
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
    }
}