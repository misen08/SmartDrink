package xyris.smartdrink;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import xyris.smartdrink.entities.Botella;
import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.entities.SaborEnBotella;
import xyris.smartdrink.http.WebServiceClient;

public class ViewPagerAdapterCelular extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.botella_icon, R.drawable.botella_icon, R.drawable.botella_icon, R.drawable.botella_icon};
    public ArrayList<SaborEnBotella> listSabores;
    public ArrayList<Botella> botellas = new ArrayList<Botella>();

    public int imagenSabor;

    JSONObject responseReader;

    public ViewPagerAdapterCelular(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        final String idDevice = sp.getString("idDevice", "ERROR");

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);

        // Se obtienen las botellas con sus respectivos sabores
        Thread threadBotellas = new Thread() {
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("idDispositivo", idDevice);
                params.put("fechaHoraPeticion", new FechaHora().formatDate(Calendar.getInstance().getTime()));

                WebServiceClient cli = new WebServiceClient("/consultarBotellas", new JSONObject(params));

                responseReader = (JSONObject) cli.getResponse();

                Log.d("SMARTDRINKS", "RESPUESTA: " + responseReader.toString());
            }
        };

        threadBotellas.start();
        try {
            threadBotellas.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Botella> saboresEnBotella = new Botella().parsearSabor(responseReader.toString());


        // Se otienen los sabores de las botellas y se carga la respectiva imagen en cada una
        botellas.clear();

        for(int i = 0; i < saboresEnBotella.size(); i++) {
            // Si hay un sabor disponible, busca el nombre y carga la imagen;
            if(saboresEnBotella.get(i).getDisponible().equals("true")) {
                Botella botella = new Botella(saboresEnBotella.get(i).getIdBotella(), saboresEnBotella.get(i).getIdSabor(), "true");
                botellas.add(botella);
                imagenSabor = cargarImagenBotella(saboresEnBotella.get(i).getIdSabor());
            } else {
                Botella botella = new Botella(saboresEnBotella.get(i).getIdBotella(), saboresEnBotella.get(i).getIdSabor(), "false");
                botellas.add(botella);
                imagenSabor = cargarImagenBotella("0");
            }

            images[i] = imagenSabor;
        }

        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

    public int cargarImagenBotella(String idSabor) {
        switch (idSabor) {
            case "0":
                return R.drawable.botella_icon;
            case "1":
                return R.drawable.botella_naranja;
            case "2":
                return R.drawable.botella_manzana;
            case "3":
                return R.drawable.botella_anana;
            case "4":
                return R.drawable.botella_durazno;
            case "5":
                return R.drawable.botella_frutilla;
            case "6":
                return R.drawable.botella_pomelo;
            case "7":
                return R.drawable.botella_icon;
        }

        return R.drawable.botella_icon;
    }
}