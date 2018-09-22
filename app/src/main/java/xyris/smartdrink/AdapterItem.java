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

import java.util.ArrayList;

public class AdapterItem extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<CategoryList> items;
    SharedPreferences sp;

    public AdapterItem (Activity activity, ArrayList<CategoryList> items) {
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

    public void addAll(ArrayList<CategoryList> category) {
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

            String resPantalla = sp.getString("resolucionPantalla", "ERROR");

            if (resPantalla.equals("800")) {
                if (sp.getString("modoViernes", "ERROR").equals("activado")) {
                    v = inf.inflate(R.layout.item_category_viernes, null);
                } else {
                    v = inf.inflate(R.layout.item_category_tablet, null);
                }
            } else {
                if (sp.getString("modoViernes", "ERROR").equals("activado")) {
                    v = inf.inflate(R.layout.item_category_viernes, null);
                } else {
                    v = inf.inflate(R.layout.item_category, null);
                }
            }

        }

        CategoryList dir = items.get(position);

        TextView tvTitle = (TextView) v.findViewById(R.id.textViewBebida);
        tvTitle.setText(dir.getTitle());

        ImageView ivInfoImage = (ImageView) v.findViewById(R.id.buttonEdit);
        ivInfoImage.setImageDrawable(dir.getButtonInfo());

        ivInfoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ((ListaDeTragos)activity).clickHandlerInfoButton(v);
                String categoryId = items.get(position).getCategoryId();
                Log.d("id",categoryId);
                ((ListaDeTragos)activity).clickHandlerInfoButton(v, position);
            }
        });

        ImageView ivDeleteImage = (ImageView) v.findViewById(R.id.buttonDelete);
        ivDeleteImage.setImageDrawable(dir.getButtonDelete());

        ivDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // itemsProgramados.remove(position);
                ((ListaDeTragos)activity).clickHandlerDeleteButton(v, position, items);
            }
        });

        return v;
    }
}