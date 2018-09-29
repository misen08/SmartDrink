package xyris.smartdrink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import ar.edu.xyris.smartdrinks.messages.preparacion.CancelaPedidoRequest;
import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.entities.PedidoBebida;
import xyris.smartdrink.entities.PedidoAgendado;
import xyris.smartdrink.http.WebServiceClient;

public class BebidasProgramadas extends AppCompatActivity {

    JSONObject responseReader;
    public int resultCode;
    private static final int MODIFICAR_BEBIDAS_PROGAMADAS = 3;

    Drawable editImage;
    Drawable deleteImage;
    ListView lvBebidasProgramadas;
    ArrayList<PedidoAgendado> listBebidasProgramadas = new ArrayList<PedidoAgendado>();
    //Se crea el array de itemsProgramados (bebidas)
    ArrayList<CategoryListBebidasProgramadas> itemsProgramados = new ArrayList<CategoryListBebidasProgramadas>();

    private String idDevice;
    private String modoViernesStatus;
    private String resPantalla;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        modoViernesStatus = sp.getString("modoViernes", "ERROR");
        resPantalla = sp.getString("resolucionPantalla", "ERROR");

        if (resPantalla.equals("800")) {
            if (modoViernesStatus.equals("activado")) {
                setContentView(R.layout.bebidas_programadas_viernes_tablet);
            } else {
                setContentView(R.layout.bebidas_programadas_tablet);
            }
        } else {
            if(modoViernesStatus.equals("activado")) {
                setContentView(R.layout.bebidas_programadas_viernes);
            } else {
                setContentView(R.layout.bebidas_programadas);
            }
        }

        editImage = getResources().getDrawable(R.drawable.edit_icon);
        deleteImage = getResources().getDrawable(R.drawable.delete_icon);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        idDevice = sp.getString("idDevice","ERROR");

        obtenerListaBebidasAgendadas();

        Button btnVolver = (Button) findViewById(R.id.buttonBack);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void obtenerListaBebidasAgendadas(){
        Thread thread = new Thread(){
            public void run(){
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("idDispositivo",idDevice);
                //Se obtiene la fecha y hora actual y se le aplica el formato que necesita recibir el mensaje.
                //A "fechaHoraPeticion" se deberá asignar "currentFormattedDate".
                params.put("fechaHoraPeticion", new FechaHora().formatDate(Calendar.getInstance().getTime()));

                //TODO: CAMBIAR NOMBRE DE PEDIDOS AGENDADOS POR EL CORRESPONDIENTE
                WebServiceClient cli = new WebServiceClient("/consultarPedidosAgendados", new JSONObject(params));

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

        listBebidasProgramadas = new PedidoBebida().parsearBebidasAgendadas(responseReader.toString());

        itemsProgramados.clear();

        for(int i=0; i< listBebidasProgramadas.size(); i++){
            //Se llena el array de itemsProgramados (bebidas programadas)
            itemsProgramados.add(new CategoryListBebidasProgramadas(
                    listBebidasProgramadas.get(i).getIdBebida(),
                    listBebidasProgramadas.get(i).getDescripcion(),
                    fechaHoraFormateada(listBebidasProgramadas.get(i).getFechaHoraAgendado()),
                    listBebidasProgramadas.get(i).getHielo(),
                    listBebidasProgramadas.get(i).getAgitado(),
                    listBebidasProgramadas.get(i).getIdPedido(),
                    editImage,
                    deleteImage));
        }

        lvBebidasProgramadas = (ListView) findViewById(R.id.listaBebidasProgramadas);

        lvBebidasProgramadas.setAdapter(new AdapterBebidasProgramadas(this, itemsProgramados));

    }

    public String fechaHoraFormateada(String fechaHoraFormatoDB){

        String[] div = fechaHoraFormatoDB.split("T");
        String fecha = div[0]; // 2018-08-17
        String hora = div[1]; // 14:53

        String[] f = fecha.split("-");
        String anio = f[0]; // 2018
        String mes = f[1]; // 08
        String dia = f[2]; // 17

        String[] h = hora.split(":");
        String hh = h[0]; // 14
        String mm = h[1]; // 53

        String fechaHora = dia + "/" + mes + "/" + anio + "  " +  hh + ":" + mm;

        return fechaHora;
    }

    public void enviarMensajeCancelarPedidoAgendado(String idPedido){

        CancelaPedidoRequest request = new CancelaPedidoRequest();
        request.setIdDispositivo(idDevice);

        //Se obtiene la fecha y hora actual y se le aplica el formato que necesita recibir el mensaje.
        //A "fechaHoraPeticion" se deberá asignar "currentFormattedDate".
        request.setFechaHoraPeticion(new FechaHora().formatDate(Calendar.getInstance().getTime()));
        request.setIdPedido(idPedido);

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

                WebServiceClient cli = new WebServiceClient("/cancelarPedidoAgendado", finalObject);

                responseReader = (JSONObject) cli.getResponse();

                Log.d("CANCELAR_PEDIDO","CANCELAR_PEDIDO: " + responseReader.toString());
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickHandlerEditButton(View v, int position) {
        Intent modificarPedido = new Intent(this, ModificarBebidasProgramadas.class);
        modificarPedido.putExtra("idPedido", itemsProgramados.get(position).getIdPedido());
        modificarPedido.putExtra("idBebida", itemsProgramados.get(position).getCategoryId());
        modificarPedido.putExtra("nombreBebida", itemsProgramados.get(position).getNombreBebidaProgramada());
        modificarPedido.putExtra("hielo", itemsProgramados.get(position).getHielo());
        modificarPedido.putExtra("agitado", itemsProgramados.get(position).getAgitado());
        modificarPedido.putExtra("fechaHoraAgendado", itemsProgramados.get(position).getFechaHora());
        startActivityForResult(modificarPedido, 3);

        if (resultCode == RESULT_OK) {
            obtenerListaBebidasAgendadas();
            lvBebidasProgramadas.setAdapter(new AdapterBebidasProgramadas(this, itemsProgramados));
        }
    }

    public void clickHandlerDeleteButton(View v, final int i, final ArrayList<CategoryListBebidasProgramadas> itemsProgramados) {

        String titleDelete = "Cancelar pedido";
        String messageDelete = "¿Está seguro que desea cancelar este pedido agendado?";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (titleDelete != null) builder.setTitle(titleDelete);

        builder.setMessage(messageDelete);
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idPedido = listBebidasProgramadas.get(i).getIdPedido();
                enviarMensajeCancelarPedidoAgendado(idPedido);
                obtenerListaBebidasAgendadas();
                Toast.makeText(BebidasProgramadas.this, "El pedido fue cancelado.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();

        lvBebidasProgramadas.setAdapter(new AdapterBebidasProgramadas(this, itemsProgramados));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MODIFICAR_BEBIDAS_PROGAMADAS:
                if (resultCode == RESULT_OK && null != data) {
                    obtenerListaBebidasAgendadas();
                }
                break;
            default:
                break;
        }
    }
}