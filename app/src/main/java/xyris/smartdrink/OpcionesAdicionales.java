package xyris.smartdrink;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class OpcionesAdicionales  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_adicionales);

        Button botonProgramarBebida = (Button) findViewById(R.id.botonProgramarBebida);
        final Button botonPrepararAhora = (Button) findViewById(R.id.botonPrepararAhora);




    botonProgramarBebida.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent volverPantallaPrincipal = new Intent(OpcionesAdicionales.this, ProgramarBebida.class);
            startActivity(
                    volverPantallaPrincipal
            );
        }
    });



        botonPrepararAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if suma de porcentajes distinto de 0 -> Mal armado!!!
                Toast.makeText(botonPrepararAhora.getContext(),"Comienza el preparado de su bebida",Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void abrirProgramarBebida(View v) {
        Intent intent = new Intent(this, OpcionesAdicionales.class);
        startActivity(intent);
    }

}
