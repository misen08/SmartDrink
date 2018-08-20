package xyris.smartdrink;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyris.smartdrink.entities.Bebida;
import xyris.smartdrink.entities.SaborEnBebida;
import xyris.smartdrink.entities.SaborEnBotella;
import xyris.smartdrink.http.WebServiceClient;

public class ListaDeTragos extends AppCompatActivity {

    String idDevice;
    TextView tvGrabar;
    Drawable infoImage;
    Drawable deleteImage;
    ListView lv;

    String responseBebidas;
    JSONObject responseReader;

    ArrayList<Bebida> listBebida = new ArrayList<Bebida>();

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_tragos);
        FloatingActionButton botonCrearTrago = findViewById(R.id.botonCrearTrago);
        infoImage = getResources().getDrawable(R.drawable.info_icon);
        deleteImage = getResources().getDrawable(R.drawable.delete_icon);

        Thread thread = new Thread(){
            public void run(){
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("idDispositivo","8173924678916234");
                params.put("fechaHoraPeticion", "2018-08-04T15:22:00");

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
        //Log.d("jsonObject", ""+ jsonObject.toString());
        listBebida = parsearBebidas(responseReader.toString());

        //Se crea el array de items (bebidas)
        ArrayList<CategoryList> items = new ArrayList<CategoryList>();

        for(int i=0; i< listBebida.size(); i++){
            //Se llena el array de items (bebidas) - el ID de bebida y el nombre debe tomarlo de la DB
            items.add(new CategoryList(listBebida.get(i).getIdBebida(), listBebida.get(i).getDescripcion(), infoImage, deleteImage));
        }

        lv = (ListView) findViewById(R.id.listaTragos);

        lv.setAdapter(new AdapterItem(this, items));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                abrirOpcionesAdicionales(view, id);
                //TODO: Abrir opciones adicionales luego de seleccionar el trago
            }
        });

        idDevice = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        //Texto en donde se mostrará lo que se grabe
        //grabar = (TextView) findViewById(R.id.txtGrabarVoz);

        botonCrearTrago.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                abrirCrearTragos(v);
            }
        });
    }

    public void abrirOpcionesAdicionales(View v, long i) {
        Intent intent = new Intent(this, OpcionesAdicionales.class);
        startActivity(intent);
    }

    public void abrirCrearTragos(View v) {
        Intent intent = new Intent(this, CrearTragos.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);
                    tvGrabar.setText(strSpeech2Text);
                }
                break;
            default:
                break;
        }
    }

    public void onClickBotonReconocimientoDeVoz(View v) {

        //ACTION_RECOGNIZE_SPEECH: Starts an activity that will prompt the user for speech and send it through a speech recognizer.
        Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Configura el Idioma (Español-España)
        intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"es-ES");

        try {
            startActivityForResult(intentActionRecognizeSpeech,
                    RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Tu dispositivo no soporta el reconocimiento por voz",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void infoBebida(){
        AlertDialog cuadroDialogo = new AlertDialog.Builder(this).create();
        cuadroDialogo.setTitle("Naranja");
        cuadroDialogo.setMessage("Naranja 100%");
        cuadroDialogo.show();
    }


    public void clickHandlerInfoButton(View v) {
        switch (v.getId()) {
            case 0:
                infoBebida();
                break;
            case 1:
//                infoBebida2();
                break;
        }
        Log.d("Info button", "Button info");
    }

    public void clickHandlerDeleteButton(View v, int i, ArrayList<CategoryList> items) {
        lv.setAdapter(new AdapterItem(this, items));
    }



    public void enviarMensajeConsultarBebidas(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.35:8080/consultarBebidas";
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
                                responseBebidas = response.toString();
                                Log.d("tag", "fs" + responseBebidas);
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


    public ArrayList<Bebida> parsearBebidas (String response) {

        ArrayList<Bebida> listBebida = new ArrayList<Bebida>();
        ArrayList<SaborEnBebida> listSaboresEnBebida = new ArrayList<SaborEnBebida>();

        try {
            responseReader = new JSONObject(response);
            String codigo = responseReader.getString("codigoError");

            if("0".equals(codigo)){
                // Se obtiene el nodo del array "bebidas"
                JSONArray bebidas = responseReader.getJSONArray("bebidas");

                // Ciclando en todas las bebidas
                for (int i = 0; i < bebidas.length(); i++) {
                    JSONObject bebidaJson = bebidas.getJSONObject(i);
                    String idBebida = bebidaJson.getString("idBebida");
                    String descripcion = bebidaJson.getString("descripcion");
                    String disponible = bebidaJson.getString("disponible");

                    // Se obtiene el nodo del array "sabores" para cada bebida
                    JSONArray sabores = bebidaJson.getJSONArray("sabores");

                    // Ciclando en todos los sabores de cada bebida
                    for (int j = 0; j < sabores.length(); j++) {
                        JSONObject sabor = sabores.getJSONObject(i);
                        String idSabor = sabor.getString("idSabor");
                        String descripcionSabor = sabor.getString("descripcion");
                        String porcentajeSabor = sabor.getString("porcentaje");
                        SaborEnBebida saborEnBebida = new SaborEnBebida(idSabor, descripcionSabor, porcentajeSabor);
                        listSaboresEnBebida.add(saborEnBebida);
                    }

                    Bebida bebida = new Bebida(idBebida, descripcion, disponible, listSaboresEnBebida);
                    listBebida.add(bebida);
                }

            } else {
                // TODO: manejar codigos de error de consultarSabores
            }

        } catch (JSONException e) { e.printStackTrace(); }

        return listBebida;
    }

}
