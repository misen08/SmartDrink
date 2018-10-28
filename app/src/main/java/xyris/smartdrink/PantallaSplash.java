package xyris.smartdrink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import xyris.smartdrink.http.Configuracion;

/**

 * La clase PantallaSplash se encarga de insertar en las variables
 * Shared Preferences el idDevice del dispositivo, la resolución de
 * la pantalla y la dirección ip del Web Service embebido en la placa
 * Arduino. Si la dirección leida y la del Web Service coinciden se
 * establece la conexión automaticamente. Caso contrario, se abre la clase
 * PantallaInicial para poder leer el código QR incorporado en la máquina.
 *
 * @author Federico Garayalde
 */

public class PantallaSplash extends AppCompatActivity {

    /** Variable utilizada para establecer la duración de la pantalla Splash. */
    private final int DURACION_SPLASH = 1000;
    /** Variable en donde se va a guardar la ip obtenida del Web Service. */
    String ipPlaca;
    /** Variable Shared Preferences en donde se guardan persistentemente varias variables. */
    SharedPreferences sp;
    /** Variable Editor para modificar los valores de la variable Shared Preferences. */
    SharedPreferences.Editor editor;
    /** Variable donde se guardará la resolución de la pantalla del dispositivo. */
    Integer resolucionPantalla;

    /**
     * Método onCreate que lanza la aplicación y carga el layout de la pantalla Splash
     * @param savedInstanceState
     * @author Federico Garayalde
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.pantalla_splash);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        // Se obtiene resolución de pantalla para cargar el layout correspondiente
        resolucionPantalla = this.getResources().getConfiguration().screenWidthDp;
        // Se obtiene de la variable Shared Preferences el valor de la ip guardado
        // En el caso de que no haya nada, se carga el valor "ERROR"
        ipPlaca = sp.getString("ipPlaca", "ERROR");
        // Setea el editor para poder modificarlo
        editor = sp.edit();
        editor.putString("resolucionPantalla", resolucionPantalla.toString());
        // Si la ip no está cargada, se setea el valor "ERROR"
        // Caso contrario, se carga el valor guardado en la variable Shared Preferences
        // Finalmente, se setea la ip
        if(ipPlaca.equals("ERROR")) {
            editor.putString("ipPlaca", "ERROR");
        } else {
            editor.putString("ipPlaca", ipPlaca);
            Configuracion.getInstance().setIp(ipPlaca);
        }
        // Se guardan los cambios del editor
        editor.commit();

        // Inicializar modo viernes como desactivado
        if(sp.getString("modoViernes", "ERROR").equals("ERROR")) {
            editor = sp.edit();
            editor.putString("modoViernes", "desactivado");
            editor.commit();
        }

        // Se obtiene el id device del dispositivo y se almacena en la variable Shared Preferences
        String idDevice = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        SharedPreferences.Editor editorIdDevice = sp.edit();
        editorIdDevice.putString("idDevice", idDevice);
        editorIdDevice.commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ipPlaca.equals("ERROR")) {
                    abrirPantallaInicial();
                } else {
                    abrirPantallaTragos();
                }
            };
        }, DURACION_SPLASH);
    }

    /**
     * Método que abre la pantalla inicial (las direcciones ip no coinciden)
     * @author Federico Garayalde
     */
    public void abrirPantallaInicial() {
        Intent pantallaInicial = new Intent(this, PantallaInicial.class);
        startActivity(pantallaInicial);
        finish();
    }

    /**
     * Método que abre la pantalla con la lista de tragos (las direcciones ip coinciden)
     * @author Federico Garayalde
     */
    public void abrirPantallaTragos() {
        Intent abrirListaTragos = new Intent(PantallaSplash.this, ListaDeTragos.class);
        startActivity(abrirListaTragos);
        finish();
    }
}