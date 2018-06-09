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



    final FloatingActionButton botonCrearTrago =  findViewById(R.id.botonCrearTrago);

//    botonCrearTrago.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //String ip = ((EditText) findViewById(R.id.direccionIP)).getText().toString();
////                if(!ip.isEmpty()){
////                    Intent ingresoCorrecto = new Intent(listaDeTragos.this, crearTragos.class);
////                    startActivity(ingresoCorrecto);
////                }
//
//            }
//        });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_tragos);
        }



}
