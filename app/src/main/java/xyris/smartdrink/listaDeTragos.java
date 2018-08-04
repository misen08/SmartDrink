package xyris.smartdrink;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class listaDeTragos extends AppCompatActivity {


    TextView grabar;

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_tragos);
        FloatingActionButton botonCrearTrago = findViewById(R.id.botonCrearTrago);
        //FloatingActionButton botonReconocimientoDeVoz = findViewById(R.id.buttonMic);

        //Texto en donde se mostrará lo que se grabe
        grabar = (TextView) findViewById(R.id.txtGrabarVoz);

        final Button botonOpcionesAdicionales = findViewById(R.id.buttonOpcionesAdicionales);
        ListView listViewTragos = findViewById(R.id.listaTragos);
        //AdapterItem adapter = new AdapterItem(this, listaTragos);
        //lv.setAdapter(adapter);
        // ArrayList<CategoryList> listaTragos = new ArrayList<CategoryList>();
        ArrayList<String> listaTragos = new ArrayList<String>();

        //Adaptador que maneja los datos del listView
        ArrayAdapter<String> adapter;

        // Adapter: Recibe 3 paràmetros:
        // - Contexto
        // - ID del layout (en este caso "my_text_view"
        // - El array de datos
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_text_view, listaTragos);

        // Se setean los datos en el listView
        listViewTragos.setAdapter(adapter);

        // Se agregan valores en la lista
        //Estos valores deben recibirse de la DB
        listaTragos.add("Banaranja");
        listaTragos.add("Naranfru");
        listaTragos.add("Manzana");
        listaTragos.add("Frutipera");
        listaTragos.add("Peranzana");
        listaTragos.add("Tropical");
        // Verifica si el adapter cambiò
        adapter.notifyDataSetChanged();

        botonCrearTrago.setOnClickListener( new View.OnClickListener()
        {
            public void onClick (View v){
                abrirCrearTragos(v);
            }
        });

        botonOpcionesAdicionales.setOnClickListener( new View.OnClickListener()
        {
            public void onClick (View v){
                abrirOpcionesAdicionales(v);
            }
        });
    }

    public void abrirOpcionesAdicionales(View v) {
        Intent intent = new Intent(this, OpcionesAdicionales.class);
        startActivity(intent);
    }

    public void abrirCrearTragos(View v) {
        Intent intent = new Intent(this, crearTragos.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);
                    grabar.setText(strSpeech2Text);
                }
                break;
            default:
                break;
        }
    }

    public void onClickBotonReconocimientoDeVoz(View v) {

        //ACTION_RECOGNIZE_SPEECH: Starts an activity that will prompt the user for speech and send it through a speech recognizer.
        Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Configura el Idioma (Español-España)
        intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"es-ES");

        try {
            startActivityForResult(intentActionRecognizeSpeech,
                    RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Tú dispositivo no soporta el reconocimiento por voz",
                    Toast.LENGTH_SHORT).show();
        }
    }



}
