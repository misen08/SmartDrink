package xyris.smartdrink;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import ar.edu.xyris.smartdrinks.messages.eliminacion.bebida.EliminaBebidaRequest;
import ar.edu.xyris.smartdrinks.messages.preparacion.PreparaBebidaRequest;
import xyris.smartdrink.entities.Bebida;
import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.entities.PedidoBebida;
import xyris.smartdrink.entities.SaborEnBebida;
import xyris.smartdrink.http.WebServiceClient;

public class ListaDeTragos extends AppCompatActivity {

    TextView textViewListaBebidas;
    Drawable infoImage;
    Drawable deleteImage;
    ListView lv;

    JSONObject responseReader;

    ArrayList<Bebida> listBebida = new ArrayList<Bebida>();
    //Se crea el array de itemsProgramados (bebidas)
    ArrayList<CategoryList> items = new ArrayList<CategoryList>();
    ArrayList<String> nombreBebidasExistentes = new ArrayList<String>();

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    private static final int CREAR_TRAGO_ACTIVITY = 2;
    private static final int OPCIONES_ADICIONALES_ACTIVITY = 3;

    String codigoErrorEliminarBebida;
    String descripcionErrorEliminarBebida;
    private String idDevice;
    private String modoViernesStatus;
    private String resPantalla;
    SharedPreferences sp;
    SharedPreferences.Editor modoViernesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        modoViernesStatus = sp.getString("modoViernes", "ERROR");
        resPantalla = sp.getString("resolucionPantalla", "ERROR");

        if (resPantalla.equals("800")) {
            if (modoViernesStatus.equals("activado")) {
                setContentView(R.layout.lista_de_tragos_tablet_viernes);
            } else {
                setContentView(R.layout.lista_de_tragos_tablet);
            }
        } else {
            if (modoViernesStatus.equals("activado")) {
                setContentView(R.layout.lista_de_tragos_viernes);
            } else {
                setContentView(R.layout.lista_de_tragos);
            }
        }

        textViewListaBebidas = (TextView) findViewById(R.id.textViewLista);
        textViewListaBebidas.setText("Lista de bebidas");
        FloatingActionButton botonCrearTrago = findViewById(R.id.botonCrearTrago);
        infoImage = getResources().getDrawable(R.drawable.info_icon);
        deleteImage = getResources().getDrawable(R.drawable.delete_icon);

        idDevice = sp.getString("idDevice","ERROR");

        obtenerLista();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                abrirOpcionesAdicionales(view, position);
                //TODO: Abrir opciones adicionales luego de seleccionar el trago
            }
        });

        //idDevice = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if(modoViernesStatus.equals("activado")) {
            menu.getItem(3).setTitle("Desactivar modo viernes");
        } else {
            menu.getItem(3).setTitle("Activar modo viernes");
        }
        return true;
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
            case R.id.cargar_sabores:
                Intent cargarSabores = new Intent(this, SaboresEnBotellas.class);
                startActivity(cargarSabores);
                break;
            case R.id.modo_viernes:
                //ToDo: ver cómo se va a implementar el "modo viernes".

                modoViernesStatus = sp.getString("modoViernes", "ERROR");
                modoViernesEditor = sp.edit();

                if(modoViernesStatus.equals("desactivado")) {
                    modoViernesStatus = "activado";
                    modoViernesEditor.putString("modoViernes",modoViernesStatus);
                    modoViernesEditor.commit();
                    Intent activarModoViernes = new Intent(this, ListaDeTragos.class);
                    startActivity(activarModoViernes);
                    finish();
                    Toast.makeText(this, "Modo viernes: Activado", Toast.LENGTH_LONG).show();
                } else {
                    modoViernesStatus = "desactivado";
                    modoViernesEditor.putString("modoViernes",modoViernesStatus);
                    modoViernesEditor.commit();
                    Intent desactivarModoViernes = new Intent(this, ListaDeTragos.class);
                    startActivity(desactivarModoViernes);
                    finish();
                    Toast.makeText(this, "Modo viernes: Desactivado", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.mantenimiento:
                Intent intent = new Intent(this, Mantenimiento.class);
                startActivity(intent);
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
        intent.putExtra("descripcionBebida", listBebida.get(i).getDescripcion());
        intent.putExtra("modoViernes", modoViernesStatus);

        startActivityForResult(intent, 3);
    }

    public void abrirCrearTragos(View v) {
        Intent intent = new Intent(this, CrearTragos.class);
        intent.putExtra("nombreBebidasExistentes", nombreBebidasExistentes);
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
                    // La variable "strSpeech2Text" contiene el texto obtenido del comando de voz
                    String strSpeech2Text = speech.get(0);

                    String strSpeech2TextUpperCase = strSpeech2Text.toUpperCase();

                    String itemBebida = "";
                    String idBebida = "-1";
                    String hielo = "false";
                    String agitado = "false";
                    while("-1".equals(idBebida)){
                        Toast toast = Toast.makeText(this, "Pedido por voz: "+ strSpeech2Text.toUpperCase(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        for(int j=0; j < listBebida.size(); j++) {
                            itemBebida = listBebida.get(j).getDescripcion().toUpperCase();
                            boolean isFound = strSpeech2TextUpperCase.contains(itemBebida);
//                            Toast.makeText(this, "ACA_1", Toast.LENGTH_SHORT).show();

                            if (isFound) {
//                                Toast.makeText(this, "ACA_2", Toast.LENGTH_SHORT).show();

                                idBebida = listBebida.get(j).getIdBebida();
                                boolean boolHielo = strSpeech2TextUpperCase.contains("con hielo".toUpperCase());
                                if(boolHielo){
                                    hielo = "true";
                                }
                                boolean boolAgitado = strSpeech2TextUpperCase.contains("agitado".toUpperCase());
                                if(boolAgitado){
                                    agitado = "true";
                                }

                                Toast.makeText(this, "SUPER!!", Toast.LENGTH_LONG).show();
                                Toast.makeText(this, "yeay " + idBebida + " " + itemBebida +
                                        "Hielo: " + hielo + "Agitado: " + agitado, Toast.LENGTH_LONG).show();

                                Intent prepararTrago = new Intent(ListaDeTragos.this, PreparandoTrago.class);
                                prepararTrago.putExtra("hielo", hielo);
                                prepararTrago.putExtra("agitado", agitado);
                                prepararTrago.putExtra("idBebida", idBebida);
                                prepararTrago.putExtra("descripcionBebida", itemBebida);
                                startActivityForResult(prepararTrago, 4);

                            }
                        }
                        if("-1".equals(idBebida)) {
                            Toast.makeText(this, "BEBIDA NO ENCONTRADA", Toast.LENGTH_SHORT).show();
                            idBebida = "0";
                        }
                    }
                    //enviarMensajePrepararBebidaAhoraPorVoz(idBebida, hielo, agitado);

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
                params.put("idDispositivo",idDevice);
                //Se obtiene la fecha y hora actual y se le aplica el formato que necesita recibir el mensaje.
                //A "fechaHoraPeticion" se deberá asignar "currentFormattedDate ".
                Date currentDate = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String currentFormattedDate  = df.format(currentDate);
                params.put("fechaHoraPeticion", currentFormattedDate);

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
        String [] bebidas = new String[listBebida.size()];
        items.clear();
        for(int i=0; i< listBebida.size(); i++){
            //Se llena el array de itemsProgramados (bebidas) - el ID de bebida y el nombre debe tomarlo de la DB
            items.add(new CategoryList(listBebida.get(i).getIdBebida(), listBebida.get(i).getDescripcion(), infoImage, deleteImage));
            //Se obtiene el nombre de las bebidas existentes para validar cuando se crea una nueva bebida.
            nombreBebidasExistentes.add(listBebida.get(i).getDescripcion());
            bebidas[i] = listBebida.get(i).getDescripcion();
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
                enviarMensajeEliminarBebida(idBebida);
                if("0".equals(codigoErrorEliminarBebida)){
                    obtenerLista();
                    Toast.makeText(ListaDeTragos.this, "Se eliminó la bebida seleccionada.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListaDeTragos.this, descripcionErrorEliminarBebida + " " +
                            "Código de error: " + codigoErrorEliminarBebida,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();

        //TODO: Traer nuevamente la lista de bebidas actualizada de la base de datos
        lv.setAdapter(new AdapterItem(this, items));
    }

    public void enviarMensajeEliminarBebida(String idBebida){

        EliminaBebidaRequest request = new EliminaBebidaRequest();
        request.setIdDispositivo(idDevice);
        //Se obtiene la fecha y hora actual y se le aplica el formato que necesita recibir el mensaje.
        //A "fechaHoraPeticion" se deberá asignar "currentFormattedDate".
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentFormattedDate = df.format(currentDate);
        request.setFechaHoraPeticion(currentFormattedDate);
        // request.setFechaHoraPeticion("2018-08-08T20:20:20");

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

        try {

            codigoErrorEliminarBebida = responseReader.getString("codigoError");
            descripcionErrorEliminarBebida = responseReader.getString("descripcionError");

        } catch (JSONException e) { e.printStackTrace(); }
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

    public void enviarMensajePrepararBebidaAhoraPorVoz(String idBebida, String hielo, String agitado){

        PreparaBebidaRequest request = new PreparaBebidaRequest();

        //La fecha y hora no se tienen en cuenta ya que el pedido se preparará en el momento.
        //Agendado posee valor "FALSE".


        PedidoBebida pedidoBebida = new PedidoBebida(idBebida, hielo, agitado,
                "false", "2018-01-01T00:00:00");

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