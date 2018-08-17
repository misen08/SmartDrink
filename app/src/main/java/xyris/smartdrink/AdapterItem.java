package xyris.smartdrink;

import android.app.Activity;
        import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
        import java.util.ArrayList;

public class AdapterItem extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<CategoryList> items;

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
            v = inf.inflate(R.layout.item_category, null);
        }

        CategoryList dir = items.get(position);

        TextView title = (TextView) v.findViewById(R.id.category);
        title.setText(dir.getTitle());

        ImageView infoImage = (ImageView) v.findViewById(R.id.buttonInfo);
        infoImage.setImageDrawable(dir.getButtonInfo());
        infoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListaDeTragos)activity).clickHandlerInfoButton(v);
                    String categoryId = items.get(position).getCategoryId();

            }
        });

        ImageView deleteImage = (ImageView) v.findViewById(R.id.buttonDelete);
        deleteImage.setImageDrawable(dir.getButtonDelete());
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                ((ListaDeTragos)activity).clickHandlerDeleteButton(v, position, items);
            }
        });

        return v;
    }








}