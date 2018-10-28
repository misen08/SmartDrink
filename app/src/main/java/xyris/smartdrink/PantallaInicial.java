package xyris.smartdrink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import xyris.smartdrink.http.Configuracion;

/**

 * La clase PantallaInicial permite seleccionar la opción para leer
 * el código QR incorporado en la máquina y poder establecer la
 * conexión con el Web Service embebido en la placa Arduino.
 * En caso de que no se quiera leer el código QR, se toca el botón
 * Salir y la aplicación finaliza
 *
 * @author Federico Garayalde
 */

public class PantallaInicial extends AppCompatActivity {

    /** */
    public final static int QRcodeWidth = 350;
    /** Componente Button que permite cerrar la aplicación */
    Button botonSalir;
    /** Componente Button que permite leer el código QR incorporado en la máquina */
    Button botonLeerQR;

    /** Variable Shared Preferences en donde se guardan persistentemente varias variables. */
    SharedPreferences sp;
    /** Variable para guardar el tamaño de la pantalla obtenido de Shared Preferences*/
    private String resPantalla;

    /**
     * Método onCreate que carga el layout de la pantalla inicial
     * y prepara los botones a la espera de que se haga click sobre
     * alguno de ellos.
     * @param savedInstanceState
     * @author Federico Garayalde
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        resPantalla = sp.getString("resolucionPantalla", "ERROR");

        if (resPantalla.equals("800")) {
            setContentView(R.layout.activity_pantalla_inicial_tablet);
        } else {
            setContentView(R.layout.activity_pantalla_inicial);
        }

        botonSalir = (Button) findViewById(R.id.buttonCloseApp);
        // Con finishAffinity se cierra totalmente la aplicación
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        botonLeerQR = (Button) findViewById(R.id.buttonReadQR);
        // Al tocar el botón "Leer código QR" se abrirá la cámara.
        botonLeerQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(PantallaInicial.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Escaneando");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorPrimary):getResources().getColor(R.color.colorPrimaryDark);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        String ipLeida;

        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");
            } else {
                Log.e("Scan", "Scanned");

                // Guardo la dirección IP obtenida del código QR en una variable
                ipLeida = result.getContents();
                editor.putString("ipPlaca", ipLeida);
                editor.commit();

                Configuracion.getInstance().setIp(ipLeida);

                if(!sp.getString("ipPlaca", "ERROR").equals("ERROR")) {
                    // Seteo la dirección ip leída
                    Configuracion.getInstance().setIp(ipLeida);
                    Intent listaTragos = new Intent(PantallaInicial.this, ListaDeTragos.class);
                    finish();
                    startActivity(listaTragos);
                } else {
                    Toast.makeText(this, "Las direcciones no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}