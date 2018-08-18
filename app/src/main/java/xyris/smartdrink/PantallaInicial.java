package xyris.smartdrink;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static xyris.smartdrink.QRReader.direccionIP;


public class PantallaInicial extends AppCompatActivity {

  //  private String android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

    Button botonSalir;
    Button botonLeerQR;
    static String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        botonSalir = (Button) findViewById(R.id.buttonCloseApp);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        String ip = "-";
        if(!direccionIP.isEmpty())
            ip = direccionIP;

        //TODO: Chequear que la ip leída del QR coincida con la de la placa

        if(ip.isEmpty()){
            Intent leerCodigo = new Intent(PantallaInicial.this, QRReader.class);
            startActivity(leerCodigo);
        }
        else{
            Intent ingresoTragos = new Intent(PantallaInicial.this, ListaDeTragos.class);
            startActivity(ingresoTragos);
        }
//        Log.d("device", android_id);
    }
}