package xyris.smartdrink;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ListaDeTragos extends AppCompatActivity {

    String idDevice;
    TextView tvGrabar;
    Drawable infoImage;
    Drawable deleteImage;
    ListView lv;

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_tragos);
        FloatingActionButton botonCrearTrago = findViewById(R.id.botonCrearTrago);
        infoImage = getResources().getDrawable(R.drawable.info_icon);
        deleteImage = getResources().getDrawable(R.drawable.delete_icon);
        

    //    buttonInfoImgView = (ImageView) findViewById(R.id.buttonInfo);
    //    buttonDeleteImgView = (ImageView) findViewById(R.id.buttonDelete);

        //Se crea el array de items (bebidas)
        ArrayList<CategoryList> items = new ArrayList<CategoryList>();
        //Se llena el array de items (bebidas) - el ID de bebida y el nombre debe tomarlo de la DB
        items.add(new CategoryList("0", "Naranja Full", infoImage, deleteImage));
        items.add(new CategoryList("1", "Frutilla Full", infoImage, deleteImage));
        items.add(new CategoryList("2", "Anana Full", infoImage, deleteImage));
        items.add(new CategoryList("3", "Manzana Full", infoImage, deleteImage));

        lv = (ListView) findViewById(R.id.listaTragos);

        lv.setAdapter(new AdapterItem(this, items));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                abrirOpcionesAdicionales(view, id);
                //TODO: Abrir opciones adicionales luego de seleccionar el trago
            }
        });

        idDevice = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        //Texto en donde se mostrará lo que se grabe
        //grabar = (TextView) findViewById(R.id.txtGrabarVoz);

        botonCrearTrago.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                abrirCrearTragos(v);
            }
        });

//        botonOpcionesAdicionales.setOnClickListener( new View.OnClickListener()
//        {
//            public void onClick (View v){
//                abrirOpcionesAdicionales(v);
//            }
//        });
    }

    public void abrirOpcionesAdicionales(View v, long i) {
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
                    tvGrabar.setText(strSpeech2Text);
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
                    "Tu dispositivo no soporta el reconocimiento por voz",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //public void actualizarLista(ListView l) {
    //    int tam = cantidaditemsBD;
    //    String nombreTrago;
    //    int categoryId;
    //    ArrayList<CategoryList> items = new ArrayList<CategoryList>();

    //    items.clear();
    //    for(int i=0; i<tam; i++) {
    //        categoryId = buscarIdBD;
    //        nombreTrago = buscarNombreBD;
    //        items.add(new CategoryList(categoryId, nombreTrago, infoImage, deleteImage));
    //    }
    //}


    public void infoBebida(){
        AlertDialog cuadroDialogo = new AlertDialog.Builder(this).create();
        cuadroDialogo.setTitle("Naranja");
        cuadroDialogo.setMessage("Naranja 100%");
        cuadroDialogo.show();
    }

    public void infoBebida2(){
        AlertDialog cuadroDialogo = new AlertDialog.Builder(this).create();
        cuadroDialogo.setTitle("Manzana");
        cuadroDialogo.setMessage("Manzana 100%");
        cuadroDialogo.show();
    }

    public void clickHandlerInfoButton(View v) {
        switch (v.getId()) {
            case 0:
                infoBebida();
                break;
            case 1:
                infoBebida2();
                break;
        }
        Log.d("Info button", "Button info");
    }

    public void clickHandlerDeleteButton(View v, int i, ArrayList<CategoryList> items) {
        lv.setAdapter(new AdapterItem(this, items));
    }
}
