package xyris.smartdrink;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CambiarSaborEnBotella extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sabores_en_botellas);

        ImageView botella1 = findViewById(R.id.imageViewBotella1);
        ImageView botella2 = findViewById(R.id.imageViewBotella2);
        ImageView botella3 = findViewById(R.id.imageViewBotella3);
        ImageView botella4 = findViewById(R.id.imageViewBotella4);

        obtenerSaboresEnBotella();

        botella1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CambiarSaborEnBotella.this, "Botella 1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerSaboresEnBotella() {

    }
}
