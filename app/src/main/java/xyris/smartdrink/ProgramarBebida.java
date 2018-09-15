package xyris.smartdrink;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ar.edu.xyris.smartdrinks.messages.preparacion.PreparaBebidaRequest;
import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.entities.PedidoBebida;
import xyris.smartdrink.http.WebServiceClient;

import static java.lang.System.currentTimeMillis;

public class ProgramarBebida extends AppCompatActivity implements View.OnClickListener {

    public SimpleDateFormat fecha;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes =  c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    //Widgets
    EditText etFecha;
    EditText etHora;
    ImageButton ibObtenerFecha;
    ImageButton ibObtenerHora;
    Button btnAceptar;
    TextView tvFecha;
    TextView tvHora;

    JSONObject responseReader;

    private String idDevice;

    String hielo, agitado, idBebida, fechaHoraAgendado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programar_bebida);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        idDevice = sp.getString("idDevice","ERROR");

        Toast.makeText(this, "id" + idDevice, Toast.LENGTH_SHORT).show();

        tvFecha = (TextView) findViewById(R.id.textViewFecha);
        tvHora = (TextView) findViewById(R.id.textViewHora);

        //Widget EditText donde se mostrara la fecha obtenida
        etFecha = (EditText) findViewById(R.id.fecha);
        etFecha.setOnClickListener(this);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = (ImageButton) findViewById(R.id.buttonCalendar);
        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(this);

        //Widget EditText donde se mostrara la hora obtenida
        etHora = (EditText) findViewById(R.id.hora);
        etHora.setOnClickListener(this);
        //Widget ImageButton del cual usaremos el evento clic para obtener la hora
        ibObtenerHora = (ImageButton) findViewById(R.id.buttonHora);
        //Evento setOnClickListener - clic
        ibObtenerHora.setOnClickListener(this);

        btnAceptar = (Button) findViewById(R.id.buttonAcceptTime);

        btnAceptar.setOnClickListener(this);

        idBebida = getIntent().getExtras().getString("idBebida");
        hielo = getIntent().getExtras().getString("hielo");
        agitado = getIntent().getExtras().getString("agitado");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCalendar:
                obtenerFecha();
                break;
            case R.id.fecha:
                obtenerFecha();
                break;
            case R.id.buttonHora:
                obtenerHora();
                break;
            case R.id.hora:
                obtenerHora();
                break;
            case R.id.buttonAcceptTime:
                aceptarPrograma();
                break;
        }
    }

    private void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(diaFormateado + "/" + mesFormateado + "/" + year);
            }
        },anio, mes, dia);

        recogerFecha.getDatePicker().setMinDate(System.currentTimeMillis());
        //Muestro el widget
        recogerFecha.show();
    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf("0" + minute):String.valueOf(minute);
                //Muestro la hora con el formato deseado
                etHora.setText(horaFormateada + ":" + minutoFormateado);
            }
        }, hora, minuto, true);

        recogerHora.show();
    }

    public void aceptarPrograma(){
        Calendar fechaActual = Calendar.getInstance();

        // Obtengo la feha y hora actuales
        Integer diaActual = fechaActual.get(Calendar.DAY_OF_MONTH);
        Integer mesActual = fechaActual.get(Calendar.MONTH)+1;
        Integer anioActual = fechaActual.get(Calendar.YEAR);
        Integer horaActual = fechaActual.get(Calendar.HOUR_OF_DAY);
        Integer minutoActual = fechaActual.get(Calendar.MINUTE);

        // Formateo de acuerdo al formato que tienen los textView para comparar
        String diaFormateado = (diaActual < 10) ? String.valueOf("0" + diaActual.toString()) : String.valueOf(diaActual.toString());
        String mesFormateado = (mesActual < 10) ? String.valueOf("0" + mesActual.toString()) : String.valueOf(mesActual.toString());
        String horaFormateada = (horaActual < 10) ? String.valueOf("0" + horaActual.toString()) : String.valueOf(horaActual.toString());
        String minutoFormateado = (minutoActual < 10) ? String.valueOf("0" + minutoActual.toString()) : String.valueOf(minutoActual.toString());

        // Odeno la fecha en el formato yyyy/MM/dd para comparar String
        String hoy = anioActual.toString() + "/" + mesFormateado + "/" + diaFormateado;
        String ahora = horaFormateada + ":" + minutoFormateado;

        String fechaIngresada = etFecha.getText().toString();
        String horaIngresada = etHora.getText().toString();

        // Valido que el usuario haya ingresado fecha y hora
        if(fechaIngresada.isEmpty())
            Toast.makeText(this, "La fecha no puede ser vacía", Toast.LENGTH_SHORT).show();
        if(horaIngresada.isEmpty())
            Toast.makeText(this, "La hora no puede ser vacía", Toast.LENGTH_SHORT).show();

        // Valido que fecha y hora sean posteriores a ahora.
        if(!fechaIngresada.isEmpty() && !horaIngresada.isEmpty()) {
            String fechaDiv [] = fechaIngresada.split("/");
            fechaIngresada = fechaDiv[2] + "/" + fechaDiv[1] + "/" + fechaDiv[0];

            if ((fechaIngresada.compareTo(hoy) == 0 && horaIngresada.compareTo(ahora) > 0)
                    || (fechaIngresada.compareTo(hoy) > 0)) {

                //Se envía el mensaje para programar la bebida
                enviarMensajePrepararBebidaProgramada(idBebida, hielo, agitado, (new FechaHora().fechaHoraFormateada(fechaIngresada, horaIngresada)));

                Toast.makeText(this, "Tu bebida fue programada", Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                boolean result = true;
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            } else if (fechaIngresada.compareTo(hoy) < 0) {
                Toast.makeText(this, "La fecha no puede ser anterior a hoy", Toast.LENGTH_SHORT).show();
            } else if (fechaIngresada.compareTo(hoy) == 0 && ahora.compareTo(horaIngresada) >= 0) {
                Toast.makeText(this, "La hora no puede ser anterior a ahora", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void enviarMensajePrepararBebidaProgramada(String idBebida, String hielo, String agitado, String fechaHoraAgendado){

        PreparaBebidaRequest request = new PreparaBebidaRequest();

        //La fecha y hora sí se tienen en cuenta ya que el pedido se agendará para prepararse con posterioridad.
        //Agendado posee valor "TRUE".
        PedidoBebida pedidoBebida = new PedidoBebida(idBebida, hielo, agitado,
                "true", fechaHoraAgendado);

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