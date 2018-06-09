package xyris.smartdrink;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class listaDeTragos extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_tragos);


        FloatingActionButton botonCrearTrago = (FloatingActionButton) findViewById(R.id.botonCrearTrago);

        botonCrearTrago.setOnClickListener( new View.OnClickListener()
        {
            public void onClick (View v){
                abrirCrearTragos(v);
            }
        });
    }

    public void abrirCrearTragos(View v) {
        Intent intent = new Intent(this, crearTragos.class);
        startActivity(intent);
    }






}
