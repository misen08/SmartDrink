package xyris.smartdrink;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ListaDeTragos extends AppCompatActivity {

    String idDevice;
    TextView grabar;
    Drawable infoImage;
    Drawable deleteImage;
    ImageView buttonInfoImgView;
    ImageView buttonDeleteImgView;


    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_tragos);
        FloatingActionButton botonCrearTrago = findViewById(R.id.botonCrearTrago);
        //FloatingActionButton botonReconocimientoDeVoz = findViewById(R.id.buttonMic);
        infoImage = getResources().getDrawable(R.drawable.info_icon);
        deleteImage = getResources().getDrawable(R.drawable.delete_icon);


        buttonInfoImgView = (ImageView)findViewById(R.id.buttonInfo);
        buttonDeleteImgView = (ImageView)findViewById(R.id.buttonDelete);


        //Se crea el array de items (bebidas)
        ArrayList<CategoryList> items = new ArrayList<CategoryList>();
        //Se llena el array de items (bebidas) - el ID de bebida y el nombre debe tomarlo de la DB
        items.add(new CategoryList("0", "Naranja Full", infoImage, deleteImage));
        items.add(new CategoryList("1", "Frutilla Full", infoImage, deleteImage));
        items.add(new CategoryList("2", "Anana Full", infoImage, deleteImage));
        items.add(new CategoryList("3", "Manzana Full", infoImage, deleteImage));



        ListView lv = (ListView) findViewById(R.id.listaTragos);

        AdapterItem adapter = new AdapterItem(this, items);

        lv.setAdapter(adapter);

        idDevice = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        //Texto en donde se mostrará lo que se grabe
//        grabar = (TextView) findViewById(R.id.txtGrabarVoz);

        final Button botonOpcionesAdicionales = findViewById(R.id.buttonOpcionesAdicionales);








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
        Intent intent = new Intent(this, CrearTragos.class);
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

    public void testImgButton(View v){
        Intent intent = new Intent(this, OpcionesAdicionales.class);
        startActivity(intent);
    }

    public void removeItem(Integer i, ArrayList<CategoryList> l){
        String position = l.get(i).getCategoryId();
        i = Integer.parseInt(position);
        l.remove(i);
    }



    public void getButtonID(){
        Integer test = buttonDeleteImgView.getId();
        String testStr = Integer.toString(test);
        Log.d("tag", testStr);
    }

}
