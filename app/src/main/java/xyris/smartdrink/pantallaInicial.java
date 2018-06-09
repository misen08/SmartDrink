package xyris.smartdrink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class pantallaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        String ip;
        ip = "";

        if(ip.isEmpty()){
            Intent leerCodigo = new Intent(pantallaInicial.this, QRReader.class);
            startActivity(leerCodigo);
        }
        else{
            Intent ingresoTragos = new Intent(pantallaInicial.this, listaDeTragos.class);
            startActivity(ingresoTragos);
        }

        //final Button botonLogin = (Button) findViewById(R.id.botonIngresar);
        //botonLogin.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        String ip = ((EditText) findViewById(R.id.direccionIP)).getText().toString();
        //        if(!ip.isEmpty()){
        //            Intent ingresoCorrecto = new Intent(activity_pantalla_inicial.this, QRReader.class);
        //            startActivity(ingresoCorrecto);
        //        }
        //        else{
        //            Toast.makeText(botonLogin.getContext(),"IP incorrecta",Toast.LENGTH_SHORT).show();
        //        }
        //    }
        //});

        Button botonSalir = (Button) findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
