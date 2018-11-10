package xyris.smartdrink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import ar.edu.xyris.smartdrinks.messages.asignacion.AsignaBotellaRequest;
import xyris.smartdrink.entities.Botella;
import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.entities.SaborEnBotella;
import xyris.smartdrink.http.WebServiceClient;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.botella_icon, R.drawable.botella_icon, R.drawable.botella_icon, R.drawable.botella_icon};
    public ArrayList<SaborEnBotella> listSabores;
    public ArrayList<Botella> botellas = new ArrayList<Botella>();

    public int imagenSabor;

    JSONObject responseReader;

    public ViewPagerAdapter(Context context) {
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

        // Se obtienen los sabores en un responseReader
        Thread thread = new Thread() {
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("idDispositivo", idDevice);
                params.put("fechaHoraPeticion", new FechaHora().formatDate(Calendar.getInstance().getTime()));

                WebServiceClient cli = new WebServiceClient("/consultarSabores", new JSONObject(params));

                responseReader = (JSONObject) cli.getResponse();

                Log.d("SMARTDRINKS", "RESPUESTA: " + responseReader.toString());
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Se parsean los sabores para trabajar individualmente cada campo
        listSabores = new SaborEnBotella().parsearSaborEnBotella(responseReader.toString());

        final String[] sabores = new String[listSabores.size()];

        // Se obtienen los nombres de los sabores para cargar las botellas
        for (int i = 0; i < listSabores.size(); i++) {
            sabores[i] = listSabores.get(i).getDescripcion();
        }

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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    onCreateDialog(listSabores, sabores, botellas, (position + 1), idDevice, imageView);
                } else if (position == 1) {
                    onCreateDialog(listSabores, sabores, botellas, (position + 1), idDevice, imageView);
                } else if (position == 2) {
                    onCreateDialog(listSabores, sabores, botellas, (position + 1), idDevice, imageView);
                } else if (position == 3) {
                    onCreateDialog(listSabores, sabores, botellas, (position + 1), idDevice, imageView);
                }
            }
        });

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

    public void onCreateDialog(final ArrayList<SaborEnBotella> listSabores, final String[] sabores,
                               final ArrayList<Botella> botellas, final Integer pos,
                               final String idDevice, final ImageView imageView) {

        ContextThemeWrapper cw = new ContextThemeWrapper( this.context, R.style.AlertDialogTheme );
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(cw);

        String titleDialog = "Seleccioná el sabor\n";

        // Set Custom Title
        TextView title = new TextView(this.context);
        // Title Properties
        title.setText(titleDialog);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(38);
        alertDialog.setCustomTitle(title);


        // Set Message
        TextView msg = new TextView(this.context);
        // Message Properties
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextSize(32);
        alertDialog.setView(msg);

        new Dialog(this.context);

        alertDialog.setItems(sabores, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                boolean existeSabor = false;
                int flag = 0;
                // Si se elige "Sin sabor" se pone la imagen de la botella vacía
                if(sabores[i].equals("Sin sabor")) {
                    images[pos - 1] = cargarImagenBotella("0");
                    imageView.setImageResource(images[pos - 1]);
                    Botella botella = new Botella(pos.toString(), "7", "false");
                    //Si la botella ya fue asignada, modifico el valor de la posición

                    for(int j = 0; j < botellas.size() ; j++){
                        if((pos-1) == j){
                            botellas.set(j, botella);
                            flag = 1;
                        }
                    }

                    if(flag == 0){
                        botellas.add(botella);
                    }

                } else {

                    // Si se elige un sabor, se chequea que no exista el sabor en otra botella
                    for (int j = 0; j < botellas.size(); j++) {
                        if (botellas.get(j).getIdSabor().equals(listSabores.get(i).getIdSabor()))
                            existeSabor = true;
                    }
                }

                // Si el sabor no existe en otra botella y no es "Sin sabor" se asigna a la botella correspondiente
                if (!existeSabor) {
                    if(!sabores[i].equals("Sin sabor")) {
                        Botella botella = new Botella(pos.toString(), listSabores.get(i).getIdSabor(), "true");

                        //Si la botella ya fue asignada, modifico el valor de la posición
                        for(int j = 0; j < botellas.size() ; j++){
                            if((pos-1) == j){
                                botellas.set(j, botella);
                                flag = 1;
                            }
                        }

                        if(flag == 0){
                            botellas.add(botella);
                        }

                        // Se carga la imagen del sabor elegido en la botella
                        images[pos - 1] = cargarImagenBotella(listSabores.get(i).getIdSabor());
                        imageView.setImageResource(images[pos - 1]);
                    }

                    AsignaBotellaRequest request = new AsignaBotellaRequest();
                    request.setBotellas(botellas);
                    request.setIdDispositivo(idDevice);
                    request.setFechaHoraPeticion(new FechaHora().formatDate(Calendar.getInstance().getTime()));

                    ObjectMapper mapper = new ObjectMapper();
                    JSONObject object = null;
                    try {
                        object = new JSONObject(mapper.writeValueAsString(request));
                    } catch (Exception e) {

                    }

                    final JSONObject finalObject = object;
                    Thread thread = new Thread() {
                        public void run() {

                            WebServiceClient cli = new WebServiceClient("/asignarBotella", finalObject);

                            responseReader = (JSONObject) cli.getResponse();

                            Log.d("SMARTDRINKS_BEBIDAS", "RESPUESTA_BEBIDAS: " + responseReader.toString());
                        }
                    };

                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(context, "Ese sabor ya está asignado en otra botella", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.show();
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