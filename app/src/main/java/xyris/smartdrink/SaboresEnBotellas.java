package xyris.smartdrink;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SaboresEnBotellas extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotsPanel;
    Button btnAceptarSabores;
    private int dotsCount;
    private ImageView [] dots;

    private String modoViernesStatus;
    private String resPantalla;
    SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        modoViernesStatus = sp.getString("modoViernes", "ERROR");
        resPantalla = sp.getString("resolucionPantalla", "ERROR");

        if (resPantalla.equals("800")) {
            if (modoViernesStatus.equals("activado")) {
                setContentView(R.layout.cargar_sabores_viernes_tablet);
            } else {
                setContentView(R.layout.cargar_sabores_tablet);
            }
        } else {
            if (modoViernesStatus.equals("activado")) {
                setContentView(R.layout.cargar_sabores_viernes);
            } else {
                setContentView(R.layout.cargar_sabores);
            }
        }

       if(resPantalla.equals("800")) {
           viewPager = (ViewPager) findViewById(R.id.ViewPagerSabores);

           sliderDotsPanel = (LinearLayout) findViewById(R.id.SliderDots);

           ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

           viewPager.setAdapter(viewPagerAdapter);

           dotsCount = viewPagerAdapter.getCount();

       } else {
           viewPager = (ViewPager) findViewById(R.id.ViewPagerSabores);

           sliderDotsPanel = (LinearLayout) findViewById(R.id.SliderDots);

           ViewPagerAdapterCelular viewPagerAdapterCelular = new ViewPagerAdapterCelular(this);

           viewPager.setAdapter(viewPagerAdapterCelular);

           dotsCount = viewPagerAdapterCelular.getCount();
           TextView tvCargarSabores = (TextView) findViewById(R.id.textViewCargarSabores);
           tvCargarSabores.setText("");
       }

        // Las imagenes pasan automaticamente
        //Timer timer = new Timer();
        //timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        dots = new ImageView[dotsCount];

        for(int i = 0; i < dotsCount; i++){
            if(modoViernesStatus.equals("activado")) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot_viernes));
            } else {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotsPanel.addView(dots[i], params);
        }

        if(modoViernesStatus.equals("activado")) {
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot_viernes));
        } else  {
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i < dotsCount; i++) {
                    if(modoViernesStatus.equals("activado")) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot_viernes));
                    } else {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                    }
                }

                if(modoViernesStatus.equals("activado")) {
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot_viernes));
                } else {
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnAceptarSabores = (Button) findViewById(R.id.buttonAceptarSabores);
        btnAceptarSabores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

//    public class MyTimerTask extends TimerTask {
//
//        @Override
//        public void run() {
//
//            SaboresEnBotellas.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    if(viewPager.getCurrentItem() == 0) {
//                        viewPager.setCurrentItem(1);
//                    } else if(viewPager.getCurrentItem() == 1) {
//                        viewPager.setCurrentItem(2);
//                    } else {
//                        viewPager.setCurrentItem(0);
//                    }
//
//                }
//            });
//
//        }
//    }
}