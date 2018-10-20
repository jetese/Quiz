package smd.quizpro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Intro extends AppCompatActivity {
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //Desactivar Action Bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Añadir fuente
        tx = (TextView)findViewById(R.id.textView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.textView2);
        tx.setTypeface(custom_font);


        //Añadir click a botón
        ImageButton btn = (ImageButton)findViewById(R.id.startButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tx.setTextColor(Color.parseColor("#bdbdbd"));
                startActivity(new Intent(Intro.this, Menu.class));
            }
        });
    }
}
