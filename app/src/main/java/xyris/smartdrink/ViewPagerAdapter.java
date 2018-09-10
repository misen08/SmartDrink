package xyris.smartdrink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import xyris.smartdrink.entities.FechaHora;
import xyris.smartdrink.entities.SaborEnBotella;
import xyris.smartdrink.http.WebServiceClient;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.botella_icon, R.drawable.botella_icon, R.drawable.botella_icon, R.drawable.botella_icon};
    public ArrayList<SaborEnBotella> listSabores;
    private String idDevice;
    private String saborSeleccionado;
    private String sabor;
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

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        Thread thread = new Thread(){
            public void run(){
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("idDispositivo",idDevice);
                params.put("fechaHoraPeticion", new FechaHora().formatDate(Calendar.getInstance().getTime()));

                WebServiceClient cli = new WebServiceClient("/consultarSabores", new JSONObject(params));

                responseReader = (JSONObject) cli.getResponse();

                Log.d("SMARTDRINKS","RESPUESTA: " + responseReader.toString());
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listSabores = new SaborEnBotella().parsearSaborEnBotella(responseReader.toString());

        final String [] sabores = new String[listSabores.size()];

        for(int i = 0; i < listSabores.size(); i++) {
            sabores[i] = listSabores.get(i).getDescripcion();
        }

        //TODO:Acceder al metodo para agregar sabor a botella en cada click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0) {
                    saborSeleccionado = onCreateDialog(sabores);
                    Toast.makeText(context, "Sabor: "+saborSeleccionado, Toast.LENGTH_SHORT).show();
                } else if(position == 1) {
                    saborSeleccionado = onCreateDialog(sabores);
                    Toast.makeText(context, "Sabor: "+saborSeleccionado, Toast.LENGTH_SHORT).show();
                } else if(position == 2) {
                    saborSeleccionado = onCreateDialog(sabores);
                    Toast.makeText(context, "Sabor: "+saborSeleccionado, Toast.LENGTH_SHORT).show();
                } else {
                    saborSeleccionado = onCreateDialog(sabores);
                    Toast.makeText(context, "Sabor: "+saborSeleccionado, Toast.LENGTH_SHORT).show();
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

    public String onCreateDialog (final String [] listSabores) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        String titleDialog = "Seleccioná el sabor";

        builder.setTitle(titleDialog);
        builder.setItems(listSabores, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sabor = listSabores[i];
            }
        });
        builder.show();
        return sabor;
    }
}