package xyris.smartdrink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaInicial extends AppCompatActivity {

    //private String android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

    Button botonSalir;
    Button botonLeerQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        botonSalir = (Button) findViewById(R.id.buttonCloseApp);
        // Con finishAffinity se cierra totalmente la aplicación
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        botonLeerQR = (Button) findViewById(R.id.buttonReadQR);
        botonLeerQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent leerCodigo = new Intent(PantallaInicial.this, QRReader.class);
                startActivity(leerCodigo);
            }
        });
    }
}