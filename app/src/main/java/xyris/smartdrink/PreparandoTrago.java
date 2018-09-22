package xyris.smartdrink;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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

        songBrunoMars = MediaPlayer.create(PreparandoTrago.this,R.raw.brunomars);
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
//                            songBrunoMars.start();
                            songBrunoMars.setVolume(100,100);

                        } else {
//                            songMaluma.start();

                            songMaluma.setVolume(100,100);

                        }
                        break;

                    case STATUS_FALSE:
//                        stopService(svcBrunoMars);
//                        stopService(svcMaluma);
//                        songMaluma.pause();
//                        songBrunoMars.pause();
                        songMaluma.setVolume(0,0);

                        songBrunoMars.setVolume(0,0);
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

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("HOME PRESSED", "MYonStop is called");
        songBrunoMars.pause();
        songMaluma.pause();
    }

    @Override
    public void onBackPressed() {
        songBrunoMars.stop();
        songMaluma.stop();
        finish();
    }
}

