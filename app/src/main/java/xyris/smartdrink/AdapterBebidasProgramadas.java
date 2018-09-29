package xyris.smartdrink;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterBebidasProgramadas extends BaseAdapter {

    private String modoViernesStatus;
    private String resPantalla;

    protected Activity activity;
    protected ArrayList<CategoryListBebidasProgramadas> itemsProgramados;
    SharedPreferences sp;

    public AdapterBebidasProgramadas(Activity activity, ArrayList<CategoryListBebidasProgramadas> itemsProgramados) {
        this.activity = activity;
        this.itemsProgramados = itemsProgramados;
    }

    @Override
    public int getCount() {
        return itemsProgramados.size();
    }

    public void clear() {
        itemsProgramados.clear();
    }

    public void addAll(ArrayList<CategoryListBebidasProgramadas> category) {
        for (int i = 1 ; i < category.size(); i++) {
            itemsProgramados.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return itemsProgramados.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            sp = PreferenceManager.getDefaultSharedPreferences(this.activity);

            modoViernesStatus = sp.getString("modoViernes", "ERROR");
            resPantalla = sp.getString("resolucionPantalla", "ERROR");

            if (resPantalla.equals("800")) {
                if (modoViernesStatus.equals("activado")) {
                    v = inf.inflate(R.layout.item_category_bebidas_programadas_viernes_tablet, null);
                } else {
                    v = inf.inflate(R.layout.item_category_bebidas_programadas_tablet, null);
                }
            } else {
                if (modoViernesStatus.equals("activado")) {
                    v = inf.inflate(R.layout.item_category_bebidas_programadas_viernes, null);
                } else {
                    v = inf.inflate(R.layout.item_category_bebidas_programadas, null);
                }
            }
        }

        CategoryListBebidasProgramadas dir = itemsProgramados.get(position);

        TextView tvNombreBebidaProgramada = (TextView) v.findViewById(R.id.textViewBebida);
        tvNombreBebidaProgramada.setText(dir.getNombreBebidaProgramada());

        TextView tvFechaHora = (TextView) v.findViewById(R.id.textViewFechaHora);
        tvFechaHora.setText(dir.getFechaHora());

        TextView tvHielo = (TextView) v.findViewById(R.id.textViewHielo);
        tvHielo.setText(dir.getHielo());

        TextView tvAgitado = (TextView) v.findViewById(R.id.textViewAgitado);
        tvAgitado.setText(dir.getAgitado());

        ImageView ivEditImage = (ImageView) v.findViewById(R.id.buttonEdit);
        ivEditImage.setImageDrawable(dir.getButtonEdit());

        ivEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String categoryId = itemsProgramados.get(position).getCategoryId();
                Log.d("id",categoryId);
                ((BebidasProgramadas)activity).clickHandlerEditButton(v, position);
            }
        });

        ImageView ivDeleteImage = (ImageView) v.findViewById(R.id.buttonDelete);
        ivDeleteImage.setImageDrawable(dir.getButtonDelete());

        ivDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // itemsProgramados.remove(position);
                ((BebidasProgramadas)activity).clickHandlerDeleteButton(v, position, itemsProgramados);
            }
        });

        return v;
    }
}