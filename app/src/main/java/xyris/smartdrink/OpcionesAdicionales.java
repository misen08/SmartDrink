package xyris.smartdrink;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class OpcionesAdicionales  extends AppCompatActivity {

    Button botonProgramarBebida;
    Button botonPrepararAhora;
    CheckBox agregarHielo;
    CheckBox mezclarBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_adicionales);

        String urlGif = "https://domain.com/myanimatedgif.gif";
        //Agregar implementacion Glide dentro de archivo build.gradle.
        ImageView imgIceCube = (ImageView)findViewById(R.id.imageView2);
        Uri uri = Uri.parse(urlGif);
        //Glide.with(getApplicationContext()).load(uri).into(imgIceCube);

        botonProgramarBebida = (Button) findViewById(R.id.botonProgramarBebida);
        botonPrepararAhora = (Button) findViewById(R.id.botonPrepararAhora);

        botonProgramarBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volverPantallaPrincipal = new Intent(OpcionesAdicionales.this, ProgramarBebida.class);
                startActivity(volverPantallaPrincipal);
            }
        });

        botonPrepararAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirPreparandoTrago = new Intent(OpcionesAdicionales.this, PreparandoTrago.class);
                startActivity(abrirPreparandoTrago);
                if(agregarHielo.isEnabled())
                    Toast.makeText(OpcionesAdicionales.this, "Hielo", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(OpcionesAdicionales.this, "Sin hielo", Toast.LENGTH_SHORT).show();

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

    public void mandarMensaje() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.35:8080/consultarBebidas";
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("idDispositivo","8173924678916234");
        params.put("fechaHoraPeticion", "2018-08-04T15:22:00");

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override public void onResponse(JSONObject response)
                    {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            Toast.makeText(getApplicationContext(),"Response:%n %s" + response.toString(4),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) { e.printStackTrace(); } }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),"Response:%n %s" + error.getMessage() + error.getStackTrace(),
                        Toast.LENGTH_SHORT).show();
            } });
// Add the request to the RequestQueue.
        queue.add(req);
    }
}
