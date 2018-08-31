package xyris.smartdrink;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

    TextView tvNombreBebida;
    EditText etFechaAgendada;
    EditText etHoraAgendada;
    CheckBox cbHielo;
    CheckBox cbAgitado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_bebidas_programadas);

        tvNombreBebida = (TextView) findViewById(R.id.textViewNombreBebida);
        tvNombreBebida.setText(getIntent().getStringExtra("nombreBebida"));

        String fechaHora [] = getIntent().getStringExtra("fechaHoraAgendado").split(" ");
        String fecha = fechaHora[0];
        String hora = fechaHora[2];

        etFechaAgendada = (EditText) findViewById(R.id.editTextFechaAgendada);
        etFechaAgendada.setText(fecha);

        etHoraAgendada = (EditText) findViewById(R.id.editTextHoraAgendada);
        etHoraAgendada.setText(hora);

        cbHielo = (CheckBox) findViewById(R.id.checkBoxOpcionHielo);
        if(getIntent().getStringExtra("hielo").equals("Con hielo"))
            cbHielo.setChecked(true);

        cbAgitado = (CheckBox) findViewById(R.id.checkBoxOpcionAgitado);
        if(getIntent().getStringExtra("agitado").equals("Agitado"))
            cbAgitado.setChecked(true);

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
        //TODO: Enviar datos del pedido a la BD y actualizar la lista
        Calendar fechaActual = Calendar.getInstance();

        // Obtengo la feha y hora actuales
        Integer diaActual = fechaActual.get(Calendar.DAY_OF_MONTH);
        Integer mesActual = fechaActual.get(Calendar.MONTH)+1;
        Integer anioActual = fechaActual.get(Calendar.YEAR);
        Integer horaActual = fechaActual.get(Calendar.HOUR_OF_DAY);
        Integer minutoActual = fechaActual.get(Calendar.MINUTE);

        // Formateo de acuerdo al formato que tienen los textView para comparar
        String diaFormateado = (diaActual < 10) ? String.valueOf(CERO + diaActual.toString()) : String.valueOf(diaActual.toString());
        String mesFormateado = (mesActual < 10) ? String.valueOf(CERO + mesActual.toString()) : String.valueOf(mesActual.toString());
        String horaFormateada = (horaActual < 10) ? String.valueOf(CERO + horaActual.toString()) : String.valueOf(horaActual.toString());
        String minutoFormateado = (minutoActual < 10) ? String.valueOf(CERO + minutoActual.toString()) : String.valueOf(minutoActual.toString());

        // Odeno la fecha en el formato yyyy/MM/dd para comparar String
        String hoy = anioActual.toString() + BARRA + mesFormateado + BARRA + diaFormateado;
        String ahora = horaFormateada + DOS_PUNTOS + minutoFormateado;

        String fechaIngresada = etFechaAgendada.getText().toString();
        String horaIngresada = etHoraAgendada.getText().toString();

        // Valido que el usuario haya ingresado fecha y hora
        if(fechaIngresada.isEmpty())
            Toast.makeText(this, "La fecha no puede ser vacía", Toast.LENGTH_SHORT).show();
        if(horaIngresada.isEmpty())
            Toast.makeText(this, "La hora no puede ser vacía", Toast.LENGTH_SHORT).show();

        // Valido que fecha y hora sean posteriores a ahora.
        if(!fechaIngresada.isEmpty() && !horaIngresada.isEmpty()) {
            String fechaDiv[] = fechaIngresada.split("/");
            fechaIngresada = fechaDiv[2] + BARRA + fechaDiv[1] + BARRA + fechaDiv[0];

            if ((fechaIngresada.compareTo(hoy) == 0 && horaIngresada.compareTo(ahora) > 0)
                    || (fechaIngresada.compareTo(hoy) > 0)) {

                //fechaHoraAgendado = anioActual + "-" +  mesFormateado + "-" + diaFormateado +
                //        "T" + horaFormateada + ":" + minutoFormateado + ":00";

                //Se envía el mensaje para programar la bebida
                //enviarMensajePrepararBebidaProgramada(idBebida, hielo, agitado, fechaHoraAgendado);

                Toast.makeText(this, "Pedido modificado", Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                boolean result = true;
                returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            } else if (fechaIngresada.compareTo(hoy) < 0) {
                Toast.makeText(this, "La fecha no puede ser anterior a hoy", Toast.LENGTH_SHORT).show();
            } else if (fechaIngresada.compareTo(hoy) == 0 && ahora.compareTo(horaIngresada) >= 0) {
                Toast.makeText(this, "La hora no puede ser anterior a ahora", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
