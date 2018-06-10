package xyris.smartdrink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class ProgramarBebida  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programar_bebida);

        final Button botonConfirmarHorario = (Button) findViewById(R.id.botonConfirmarHorario);


        botonConfirmarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logida de guardado del horario programado
                Intent volverListaTragos = new Intent(ProgramarBebida.this, listaDeTragos.class);
                Toast.makeText(botonConfirmarHorario.getContext(),"Se programó su bebida en el horario seleccionado",Toast.LENGTH_SHORT).show();
                startActivity(
                        volverListaTragos
                );
            }
        });
    }

}
