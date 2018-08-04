package xyris.smartdrink;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class OpcionesAdicionales  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_adicionales);


        String urlGif = "https://domain.com/myanimatedgif.gif";
        //Agregar implementacion Glide dentro de archivo build.gradle.
        ImageView imgIceCube = (ImageView)findViewById(R.id.imageView2);
        Uri uri = Uri.parse(urlGif);
        //Glide.with(getApplicationContext()).load(uri).into(imgIceCube);



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
                Intent abrirPreparandoTrago = new Intent(OpcionesAdicionales.this, PreparandoTrago.class);
                startActivity(
                        abrirPreparandoTrago
                );
            }
        });



    }

    public void abrirProgramarBebida(View v) {
        Intent intent = new Intent(this, OpcionesAdicionales.class);
        startActivity(intent);
    }

    public void abrirPreparandoTrago(View v) {
        Intent intent = new Intent(this, PreparandoTrago.class);
        startActivity(intent);
    }

}
