package xyris.smartdrink;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ModificarBebidasProgramadas extends AppCompatActivity implements View.OnClickListener {

    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";

    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes =  c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    EditText etFechaAgendada;
    EditText etHoraAgendada;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_bebidas_programadas);

        etFechaAgendada = (EditText) findViewById(R.id.editTextFechaAgendada);
        etHoraAgendada = (EditText) findViewById(R.id.editTextHoraAgendada);

        Button botonModificarFecha = (Button) findViewById(R.id.buttonModificarFecha);
        botonModificarFecha.setOnClickListener(this);

        Button botonModificarHorario = (Button) findViewById(R.id.buttonModificarHora);
        botonModificarHorario.setOnClickListener(this);

        Button botonCancelar = (Button) findViewById(R.id.buttonCancelarModificacion);
        botonCancelar.setOnClickListener(this);

        Button botonAceptar = (Button) findViewById(R.id.buttonAceptarModificacion);
        botonAceptar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonModificarFecha:
                modificarFecha();
                break;
            case R.id.buttonModificarHora:
                modificarHorario();
                break;
            case R.id.buttonAceptarModificacion:
                modificarPedidoAgendado();
                break;
            case R.id.buttonCancelarModificacion:
                finish();
                break;
        }
    }

    public void modificarFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                etFechaAgendada.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);

        recogerFecha.getDatePicker().setMinDate(System.currentTimeMillis());
        recogerFecha.show();
    }

    private void modificarHorario(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                etHoraAgendada.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, true);

        recogerHora.show();
    }

    public void modificarPedidoAgendado() {
        Toast.makeText(this, "Pedido modificado", Toast.LENGTH_SHORT).show();
        finish();
    }
}
