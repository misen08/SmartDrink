package xyris.smartdrink;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import xyris.smartdrink.entities.Bebida;

public class BebidasProgramadas extends AppCompatActivity {

    JSONObject responseReader;

    Drawable editImage;
    Drawable deleteImage;
    ListView lvBebidasProgramadas;
    ArrayList<Bebida> listBebidasProgramadas = new ArrayList<Bebida>();
    //Se crea el array de items (bebidas)
    ArrayList<CategoryListBebidasProgramadas> itemsProgramados = new ArrayList<CategoryListBebidasProgramadas>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebidas_programadas);
        editImage = getResources().getDrawable(R.drawable.edit_icon);
        deleteImage = getResources().getDrawable(R.drawable.delete_icon);


        Thread thread = new Thread(){
            public void run(){
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("idDispositivo","8173924678916234");
                params.put("fechaHoraPeticion", "2018-08-04T15:22:00");

//                WebServiceClient cli = new WebServiceClient("/consultarBebidas", new JSONObject(params));

 //               responseReader = (JSONObject) cli.getResponse();

//                Log.d("SMARTDRINKS_BEBIDAS","RESPUESTA_BEBIDAS: " + responseReader.toString());
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        itemsProgramados.clear();

        itemsProgramados.add(new CategoryListBebidasProgramadas("1", "BEBIDA_1", "2018-08-04T15:22:00", editImage, deleteImage));
        itemsProgramados.add(new CategoryListBebidasProgramadas("2", "BEBIDA_2", "2018-08-04T15:22:00", editImage, deleteImage));
        itemsProgramados.add(new CategoryListBebidasProgramadas("3", "BEBIDA_3", "2018-08-04T15:22:00", editImage, deleteImage));



        for(int i=0; i< listBebidasProgramadas.size(); i++){
            //Se llena el array de items (bebidas) - el ID de bebida y el nombre debe tomarlo de la DB
            itemsProgramados.add(new CategoryListBebidasProgramadas("2", "BEBIDA", "2018-08-04T15:22:00", editImage, deleteImage));
           // itemsProgramados.add(new CategoryListBebidasProgramadas(listBebidasProgramadas.get(i).getIdBebida(), listBebidasProgramadas.get(i).getDescripcion();
        }

        lvBebidasProgramadas = (ListView) findViewById(R.id.listaBebidasProgramadas);

        lvBebidasProgramadas.setAdapter(new AdapterBebidasProgramadas(this, itemsProgramados));



        Button btnVolver = (Button) findViewById(R.id.buttonBack);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
