package xyris.smartdrink;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterMantenimiento extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<String> items;
    SharedPreferences sp;

    public AdapterMantenimiento (Activity activity, ArrayList<String> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<String> category) {
        for (int i = 1 ; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
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

            if(sp.getString("modoViernes", "ERROR").equals("activado")) {
                v = inf.inflate(R.layout.item_fechas_mantenimiento_viernes, null);
            } else {
                v = inf.inflate(R.layout.item_fechas_mantenimiento, null);
            }
        }

        String dir = items.get(position);

        TextView tvNombreBebidaProgramada = (TextView) v.findViewById(R.id.tvFechaNotificacion);
        tvNombreBebidaProgramada.setText(dir.toString());

        return v;
    }
}