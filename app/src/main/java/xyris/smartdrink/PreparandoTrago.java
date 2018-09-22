package xyris.smartdrink;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class PreparandoTrago  extends AppCompatActivity {

    Button btnCerrar;
    ImageButton btnMusic;
    String botonStartMusicStatus = "false";
    String modoViernesStatus;
    MediaPlayer songModoNormal;
    MediaPlayer songModoViernes;

    private static final String STATUS_TRUE = "true";
    private static final String STATUS_FALSE = "false";

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        modoViernesStatus = sp.getString("modoViernes", "ERROR");

        if(modoViernesStatus.equals("activado")) {
            setContentView(R.layout.preparando_trago);
        } else {
            setContentView(R.layout.preparando_trago);
        }

        btnMusic = findViewById(R.id.btnMusic);
        btnCerrar = findViewById(R.id.btnCerrar);

        songModoNormal = MediaPlayer.create(PreparandoTrago.this,R.raw.song_default);
        songModoViernes = MediaPlayer.create(PreparandoTrago.this,R.raw.song_viernes);

        if("desactivado".equals(modoViernesStatus)){
            songModoNormal.start();
        } else {
            songModoViernes.start();
        }

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (botonStartMusicStatus) {
                    case STATUS_TRUE:
                        botonStartMusicStatus = "false";
                        btnMusic.setImageResource(R.drawable.audio_si);
                        if("desactivado".equals(modoViernesStatus)){
                            songModoNormal.setVolume(1,1);
                        } else {
                            songModoViernes.setVolume(1,1);
                        }
                        break;

                    case STATUS_FALSE:

                        songModoNormal.setVolume(0,0);
                        songModoViernes.setVolume(0,0);
                        botonStartMusicStatus = "true";
                        btnMusic.setImageResource(R.drawable.audio_no);
                        break;
                    default:
                        break;
                }
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent returnIntent = new Intent();
                boolean result = true;
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("HOME PRESSED", "MYonStop is called");
        songModoNormal.pause();
        songModoViernes.pause();
    }

    @Override
    public void onBackPressed() {
        songModoNormal.stop();
        songModoViernes.stop();
        finish();
    }
}

