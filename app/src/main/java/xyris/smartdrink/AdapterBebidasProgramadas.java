package xyris.smartdrink;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterBebidasProgramadas extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<CategoryListBebidasProgramadas> items;

    public AdapterBebidasProgramadas(Activity activity, ArrayList<CategoryListBebidasProgramadas> items) {
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

    public void addAll(ArrayList<CategoryListBebidasProgramadas> category) {
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
            v = inf.inflate(R.layout.item_category_bebidas_programadas, null);
        }

        CategoryListBebidasProgramadas dir = items.get(position);

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
               // items.remove(position);
               // ((ListaDeTragos)activity).clickHandlerDeleteButton(v, position, items);
            }
        });

        return v;
    }
}