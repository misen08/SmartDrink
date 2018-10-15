package xyris.smartdrink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

public class PantallaSplash extends AppCompatActivity {

    //String ipPlaca = "52.204.131.123";
    //String ipPlaca = "192.168.0.35";
    private final String ipPlaca = "192.168.0.10";
    private final int DURACION_SPLASH = 1000;
    SharedPreferences.Editor editor;
    Integer res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.pantalla_splash);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        // Se obtiene resolución de pantalla para cargar el layout correspondiente
        res = this.getResources().getConfiguration().screenWidthDp;
        editor = sp.edit();
        editor.putString("resolucionPantalla", res.toString());
        editor.putString("ipPlaca", ipPlaca);
        editor.commit();

        final String ipLeida = ipPlaca; //sp.getString("IP","ERROR");

        // Inicializar modo viernes como desactivado
        if(sp.getString("modoViernes", "ERROR").equals("ERROR")) {
            editor = sp.edit();
            editor.putString("modoViernes", "desactivado");
            editor.commit();
        }

        // Se obtiene el id device del dispositivo y se almacena para ser usado en otras clases
        String idDevice = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        SharedPreferences.Editor editorIdDevice = sp.edit();
        editorIdDevice.putString("idDevice", idDevice);
        editorIdDevice.commit();

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
        Intent pantallaInicial = new Intent(this, PantallaInicial.class);
        startActivity(pantallaInicial);
        finish();
    }

    public void abrirPantallaTragos() {
        Intent abrirListaTragos = new Intent(PantallaSplash.this, ListaDeTragos.class);
        startActivity(abrirListaTragos);
        finish();
    }
}