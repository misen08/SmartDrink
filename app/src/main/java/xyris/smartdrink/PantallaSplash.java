package xyris.smartdrink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.multi.qrcode.QRCodeMultiReader;

public class PantallaSplash extends AppCompatActivity {

    static final String ipPlaca = "192.168.1.3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_splash);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String ipLeida = ipPlaca; //sp.getString("IP","ERROR");

        // Comparar con la ip de la placa.
        // FUNCIONA BIEN GUARDAR CUANDO CIERRO APP
        if(!ipLeida.equals(ipPlaca)) {
            abrirPantallaInicial();
        } else {
            Toast.makeText(this, ipLeida, Toast.LENGTH_LONG).show();
            abrirPantallaTragos();
        }
    }

    public void abrirPantallaInicial() {
        Intent pantallaInicial = new Intent(PantallaSplash.this, PantallaInicial.class);
        startActivity(pantallaInicial);
    }

    public void abrirPantallaTragos() {
        Intent abrirListaTragos = new Intent(PantallaSplash.this, ListaDeTragos.class);
        startActivity(abrirListaTragos);
    }
}
