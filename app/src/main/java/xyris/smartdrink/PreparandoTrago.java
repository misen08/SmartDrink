package xyris.smartdrink;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.common.api.Api;

import java.util.ArrayList;

import static java.lang.Boolean.TRUE;

public class PreparandoTrago  extends AppCompatActivity {

    Button btnCerrar;
    ImageButton btnMusic;
    String botonStartMusicStatus = "true";
    String modoViernesStatus;
//    Intent svcBrunoMars;
//    Intent svcMaluma;
    MediaPlayer songBrunoMars;
    MediaPlayer songMaluma;

    private static final String STATUS_TRUE = "true";
    private static final String STATUS_FALSE = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preparando_trago);
        btnMusic = findViewById(R.id.btnMusic);
        btnCerrar = findViewById(R.id.btnCerrar);

        songBrunoMars = MediaPlayer.create(PreparandoTrago.this,R.raw.song);
        songMaluma = MediaPlayer.create(PreparandoTrago.this,R.raw.maluma);

        modoViernesStatus = getIntent().getExtras().getString("modoViernes");
        Toast.makeText(this, "modo viernes: " + modoViernesStatus, Toast.LENGTH_SHORT).show();

//        svcBrunoMars = new Intent(this, BackgroundSoundService.class);
//        svcMaluma = new Intent(this, BackgroundSoundServiceModoViernes.class);

        if("desactivado".equals(modoViernesStatus)){
            songBrunoMars.start();
//            startService(svcBrunoMars);
        } else {
            songMaluma.start();
//            startService(svcMaluma);
        }

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (botonStartMusicStatus) {
                    case STATUS_TRUE:
                        botonStartMusicStatus = "false";
                        btnMusic.setImageResource(R.drawable.audio_si);
                        if("desactivado".equals(modoViernesStatus)){
                            songBrunoMars.start();
                        } else {
                            songMaluma.start();
                        }
                        break;

                    case STATUS_FALSE:
//                        stopService(svcBrunoMars);
//                        stopService(svcMaluma);
                        songMaluma.pause();
                        songBrunoMars.pause();
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
                songBrunoMars.stop();
                songMaluma.stop();
                finish();
            }
        });

    }
}

