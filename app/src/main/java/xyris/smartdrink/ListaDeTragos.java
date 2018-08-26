package xyris.smartdrink;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.edu.xyris.smartdrinks.messages.eliminacion.bebida.EliminaBebidaRequest;
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
    //Se crea el array de items (bebidas)
    ArrayList<CategoryList> items = new ArrayList<CategoryList>();

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    private static final int CREAR_TRAGO_ACTIVITY = 2;
    private static final String urlPlaca = "52.204.131.123:50000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_tragos);
        FloatingActionButton botonCrearTrago = findViewById(R.id.botonCrearTrago);
        infoImage = getResources().getDrawable(R.drawable.info_icon);
        deleteImage = getResources().getDrawable(R.drawable.delete_icon);

        obtenerLista();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                abrirOpcionesAdicionales(view, position);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Opciones del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.bebidas_programadas:
                Intent verBebidasProgramadas = new Intent(this, BebidasProgramadas.class);
                startActivity(verBebidasProgramadas);
                break;
            case R.id.mantenimiento:
                Toast.makeText(this, "Ver tema mantenimiento", Toast.LENGTH_SHORT).show();
                break;
            case R.id.aboutUs:
                Toast.makeText(this, "Grupo Xyris", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                olvidarDireccionPlaca();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void abrirOpcionesAdicionales(View v, int i) {
        Intent intent = new Intent(this, OpcionesAdicionales.class);
        intent.putExtra("idBebida", listBebida.get(i).getIdBebida());
        startActivity(intent);
    }

    public void abrirCrearTragos(View v) {
        Intent intent = new Intent(this, CrearTragos.class);
        startActivityForResult(intent, 2);
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

            case CREAR_TRAGO_ACTIVITY:
                if (resultCode == RESULT_OK && null != data) {
                    obtenerLista();
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


    public void infoBebida(int pos){
        AlertDialog cuadroDialogo = new AlertDialog.Builder(this).create();
        String messageTemp = "";
        String message = "";
        //Se obtiene el nombre de la bebida de la base de datos.
        cuadroDialogo.setTitle(listBebida.get(pos).getDescripcion());

        ArrayList<SaborEnBebida> sabor = listBebida.get(pos).getSabores();
        //Se muestra el porcentaje de cada sabor.
        for (int i = 0 ; i < sabor.size(); i++){


            messageTemp = sabor.get(i).getDescripcion() + ": " +
                    sabor.get(i).getPorcentaje() + "%" + "\n";
            message = message + messageTemp;
        }

        cuadroDialogo.setMessage(message);
        cuadroDialogo.show();
    }


    public void obtenerLista(){
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

        listBebida = parsearBebidas(responseReader.toString());
        items.clear();
        for(int i=0; i< listBebida.size(); i++){
            //Se llena el array de items (bebidas) - el ID de bebida y el nombre debe tomarlo de la DB
            items.add(new CategoryList(listBebida.get(i).getIdBebida(), listBebida.get(i).getDescripcion(), infoImage, deleteImage));
        }

        lv = (ListView) findViewById(R.id.listaTragos);

        lv.setAdapter(new AdapterItem(this, items));
    }

    public void clickHandlerInfoButton(View v, int position) {
        infoBebida(position);
        Log.d("Info button", "Button info");
    }

        public void clickHandlerDeleteButton(View v, final int i, ArrayList<CategoryList> items) {

        String titleDelete = "Eliminar bebida";
        String messageDelete = "¿Está seguro que desea eliminar esta bebida?";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (titleDelete != null) builder.setTitle(titleDelete);

        builder.setMessage(messageDelete);
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idBebida = listBebida.get(i).getIdBebida();
                enviarMensajeEliminarBebida(idBebida.toString());
                obtenerLista();
                Toast.makeText(ListaDeTragos.this, "Borrado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();

        //TODO: Traer nuevamente la lista de bebidas actualizada de la base de datos
        lv.setAdapter(new AdapterItem(this, items));
    }



    //Dialog builder con dos botones para solicitar confirmación cuando se elimina una bebida.
//    public void showDialogDeleteButton(Activity activity, String title, CharSequence message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//
//        if (title != null) builder.setTitle(title);
//
//        builder.setMessage(message);
//        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                //TODO: Eliminar bebida de la base de datos
//                Toast.makeText(ListaDeTragos.this, "Borrado", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        builder.setNegativeButton("No", null);
//        builder.show();
//    }


public void enviarMensajeEliminarBebida(String idBebida){

    EliminaBebidaRequest request = new EliminaBebidaRequest();
    request.setIdDispositivo("compu_Mica");
    request.setFechaHoraPeticion("2018-08-04T15:22:00");
    request.setIdBebida(idBebida);

    ObjectMapper mapper = new ObjectMapper();
    JSONObject object = null;
    try {
        object = new JSONObject(mapper.writeValueAsString(request));
    } catch (Exception e) {
        Log.d("ELIMINAR_BEBIDA","ELIMINAR_BEBIDAS: " + e.getMessage());
    }

    final JSONObject finalObject = object;
    Thread thread = new Thread(){
        public void run(){

            WebServiceClient cli = new WebServiceClient("/eliminarBebida", finalObject);

            responseReader = (JSONObject) cli.getResponse();

            Log.d("ELIMINAR_BEBIDA","ELIMINAR_BEBIDAS: " + responseReader.toString());
        }
    };

    thread.start();
    try {
        thread.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

    public void enviarMensajeConsultarBebidas(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = urlPlaca + "/consultarBebidas";
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

            queue.add(req);
        }catch (Exception e){
            Log.d("DEBUG","FALLE!!",e);
        }
    }

    public ArrayList<Bebida> parsearBebidas (String response) {

        ArrayList<Bebida> listBebida = new ArrayList<Bebida>();

        try {
            responseReader = new JSONObject(response);
            String codigo = responseReader.getString("codigoError");

            if("0".equals(codigo)){
                // Se obtiene el nodo del array "bebidas"
                JSONArray bebidas = responseReader.getJSONArray("bebidas");

                // Ciclando en todas las bebidas
                for (int i = 0; i < bebidas.length(); i++) {

                    ArrayList<SaborEnBebida> listSaboresEnBebida = new ArrayList<SaborEnBebida>();

                    listSaboresEnBebida.clear();
                    JSONObject bebidaJson = bebidas.getJSONObject(i);
                    String idBebida = bebidaJson.getString("idBebida");
                    String descripcion = bebidaJson.getString("descripcion");
                    String disponible = bebidaJson.getString("disponible");

                    // Se obtiene el nodo del array "sabores" para cada bebida
                    JSONArray sabores = bebidaJson.getJSONArray("sabores");

                    // Ciclando en todos los sabores de cada bebida
                    for (int j = 0; j < sabores.length(); j++) {

                        JSONObject sabor = sabores.getJSONObject(j);
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

    public void olvidarDireccionPlaca() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("IP",null);
        editor.commit();

        Intent pantallaInicial = new Intent(ListaDeTragos.this, PantallaInicial.class);
        finish();
        startActivity(pantallaInicial);
    }
}
