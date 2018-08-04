package xyris.smartdrink;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static java.security.AccessController.getContext;
import static xyris.smartdrink.QRReader.direccionIP;
import android.provider.Settings.Secure;
import android.widget.Toast;


public class pantallaInicial extends AppCompatActivity {

  //  private String android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        Button botonSalir = (Button) findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SystemClock.sleep(3000);
        String ip = "";
        if(!direccionIP.isEmpty())
            ip = direccionIP;

        if(ip.isEmpty()){
            Intent leerCodigo = new Intent(pantallaInicial.this, QRReader.class);
            startActivity(leerCodigo);
        }
        else{
            Intent ingresoTragos = new Intent(pantallaInicial.this, listaDeTragos.class);
            startActivity(ingresoTragos);
        }

//        Log.d("device", android_id);

    }
}
