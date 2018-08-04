//package xyris.smartdrink;
//
//import android.app.Activity;
//import android.content.ActivityNotFoundException;
//import android.content.Intent;
//import android.os.Bundle;
//import android.speech.RecognizerIntent;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//public class ReconocimientoDeVoz extends Activity {
//
//    TextView grabar;
//
//    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reconocimiento_de_voz);
//
//        //Texto en donde se mostrará lo que se grabe
//        grabar = (TextView) findViewById(R.id.txtGrabarVoz);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case RECOGNIZE_SPEECH_ACTIVITY:
//
//                if (resultCode == RESULT_OK && null != data) {
//
//                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    String strSpeech2Text = speech.get(0);
//                    grabar.setText(strSpeech2Text);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    public void onClickImgBtnHablar(View v) {
//
//        //ACTION_RECOGNIZE_SPEECH: Starts an activity that will prompt the user for speech and send it through a speech recognizer.
//        Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//
//        // Configura el Idioma (Español-México)
//        intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"es-MX");
//
//        try {
//            startActivityForResult(intentActionRecognizeSpeech,
//                    RECOGNIZE_SPEECH_ACTIVITY);
//        } catch (ActivityNotFoundException a) {
//            Toast.makeText(getApplicationContext(),
//                    "Tú dispositivo no soporta el reconocimiento por voz",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//}
