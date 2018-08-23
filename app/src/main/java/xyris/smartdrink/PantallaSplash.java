package xyris.smartdrink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.view.WindowManager;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;

import javax.xml.datatype.Duration;


public class PantallaSplash extends AppCompatActivity {

    static final String ipPlaca = "192.168.1.3";
    private final int DURACION_SPLASH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.pantalla_splash);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String ipLeida = ipPlaca; //sp.getString("IP","ERROR");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO:Chequear si el servidor está corriendo
                // Comparar con la ip de la placa.
                // FUNCIONA BIEN GUARDAR CUANDO CIERRO APP
                if(!ipLeida.equals(ipPlaca)) {
                    abrirPantallaInicial();
                } else {
                    abrirPantallaTragos();
                }
            };
        }, DURACION_SPLASH);
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
