package xyris.smartdrink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.xyris.smartdrinks.messages.eliminacion.bebida.EliminaBebidaRequest;
import xyris.smartdrink.entities.Bebida;
import xyris.smartdrink.entities.PedidoBebida;
import xyris.smartdrink.entities.SaborEnBotella;
import xyris.smartdrink.http.WebServiceClient;

public class BebidasProgramadas extends AppCompatActivity {

    JSONObject responseReader;

    Drawable editImage;
    Drawable deleteImage;
    ListView lvBebidasProgramadas;
    ArrayList<PedidoBebida> listBebidasProgramadas = new ArrayList<PedidoBebida>();
    //Se crea el array de itemsProgramados (bebidas)
    ArrayList<CategoryListBebidasProgramadas> itemsProgramados = new ArrayList<CategoryListBebidasProgramadas>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebidas_programadas);
        editImage = getResources().getDrawable(R.drawable.edit_icon);
        deleteImage = getResources().getDrawable(R.drawable.delete_icon);

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
                params.put("idDispositivo","SmartDrinksApp");
                params.put("fechaHoraPeticion", "2018-08-04T15:22:00");

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

        listBebidasProgramadas = parsearBebidasAgendadas(responseReader.toString());
        itemsProgramados.clear();
        for(int i=0; i< listBebidasProgramadas.size(); i++){
            //Se llena el array de itemsProgramados (bebidas programadas)
            itemsProgramados.add(new CategoryListBebidasProgramadas(
                    listBebidasProgramadas.get(i).getIdBebida(),"Nombre",
                    fechaHoraFormateada(listBebidasProgramadas.get(i).getFechaHoraAgendado()),
                    listBebidasProgramadas.get(i).getHielo(), listBebidasProgramadas.get(i).getAgitado(),
                    editImage, deleteImage));
        }

        String fechaHoraFormatoDB = "2018-08-17T14:53";

//        itemsProgramados.add(new CategoryListBebidasProgramadas("1", "BEBIDA_1",
//                fechaHoraFormateada(fechaHoraFormatoDB),"Con hielo", "Sin agitar", editImage, deleteImage));
//        itemsProgramados.add(new CategoryListBebidasProgramadas("2", "BEBIDA_2",
//                fechaHoraFormateada(fechaHoraFormatoDB), "Sin hielo", "Agitado", editImage, deleteImage));
//        itemsProgramados.add(new CategoryListBebidasProgramadas("3", "BEBIDA_3",
//                fechaHoraFormateada(fechaHoraFormatoDB), "Con hielo", "Agitado", editImage, deleteImage));


        lvBebidasProgramadas = (ListView) findViewById(R.id.listaBebidasProgramadas);

        lvBebidasProgramadas.setAdapter(new AdapterBebidasProgramadas(this, itemsProgramados));

    }

    public ArrayList<PedidoBebida> parsearBebidasAgendadas (String response) {

        ArrayList<PedidoBebida> listBebidasAgendadas = new ArrayList<PedidoBebida>();

        try {
            responseReader = new JSONObject(response);
            String codigoError = responseReader.getString("codigoError");

            if("0".equals(codigoError.toString())){
                // Se obtiene el nodo del array "pedidoBebida"
                JSONArray pedidoBebida = responseReader.getJSONArray("pedidos");

                // Ciclando en todos los pedidos de bebida agendados
                for (int i = 0; i < pedidoBebida.length(); i++) {
                    String hielo="";
                    String agitado="";
                    JSONObject bebidaAgendada = pedidoBebida.getJSONObject(i);
                    String idBebida = bebidaAgendada.getString("idBebida");
                    if("true".equals(bebidaAgendada.getString("hielo").toString())){
                        hielo = "Con hielo";
                    } else {
                        hielo = "Sin hielo";
                    }
                    if("true".equals(bebidaAgendada.getString("agitado").toString())){
                        agitado = "Agitado";
                    } else {
                        agitado = "Sin agitar";
                    }
                    String agendado = bebidaAgendada.getString("agendado");
                    String fechaHoraAgendado = bebidaAgendada.getString("fechaHoraAgendado");

                    PedidoBebida bebida = new PedidoBebida(idBebida, hielo, agitado, agendado, fechaHoraAgendado);

                    listBebidasAgendadas.add(bebida);
                }
            } else {
                // TODO: manejar codigos de error de consultarPedidos
            }

        } catch (JSONException e) { e.printStackTrace(); }

        return listBebidasAgendadas;
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

    public void enviarMensajeCancelarPedidoAgendado(String idBebida){

        EliminaBebidaRequest request = new EliminaBebidaRequest();
        request.setIdDispositivo("SmartDrinksApp");
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
        modificarPedido.putExtra("idBebida", itemsProgramados.get(position).getCategoryId());
        modificarPedido.putExtra("nombreBebida", itemsProgramados.get(position).getNombreBebidaProgramada());
        modificarPedido.putExtra("hielo", itemsProgramados.get(position).getHielo());
        modificarPedido.putExtra("agitado", itemsProgramados.get(position).getAgitado());
        modificarPedido.putExtra("fechaHoraAgendado", itemsProgramados.get(position).getFechaHora());
        startActivityForResult(modificarPedido, 3);
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
                String idBebida = listBebidasProgramadas.get(i).getIdBebida();
                enviarMensajeCancelarPedidoAgendado("2");//idBebida.toString());
                obtenerListaBebidasAgendadas();
                Toast.makeText(BebidasProgramadas.this, "El pedido fue cancelado.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();

        //TODO: Traer nuevamente la lista de bebidas actualizada de la base de datos
        lvBebidasProgramadas.setAdapter(new AdapterBebidasProgramadas(this, itemsProgramados));
    }

}
