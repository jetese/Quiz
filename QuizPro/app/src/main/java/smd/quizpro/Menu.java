package smd.quizpro;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Desactivar Action Bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Añadir fuente
        TextView tx = (TextView)findViewById(R.id.textView3);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.play);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.clasi);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.config);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.confirm);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.yes);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.no);
        tx.setTypeface(custom_font);

        //Añadir click a botón
        Button btn = (Button)findViewById(R.id.config);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tx.setTextColor(Color.parseColor("#bdbdbd"));
                startActivity(new Intent(Menu.this, Configuration.class));
            }
        });

        Button play = (Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tx.setTextColor(Color.parseColor("#bdbdbd"));
                if(Configuration.user == "anonimous"){
                    View b = findViewById(R.id.play);
                    b.setVisibility(View.INVISIBLE);
                    b = findViewById(R.id.textView3);
                    b.setVisibility(View.INVISIBLE);
                    b = findViewById(R.id.clasi);
                    b.setVisibility(View.INVISIBLE);
                    b = findViewById(R.id.config);
                    b.setVisibility(View.INVISIBLE);
                    b = findViewById(R.id.yes);
                    b.setVisibility(View.VISIBLE);
                    b = findViewById(R.id.no);
                    b.setVisibility(View.VISIBLE);
                    b = findViewById(R.id.confirm);
                    b.setVisibility(View.VISIBLE);

                }
                else{
                    startActivity(new Intent(Menu.this, Questions.class));
                }

            }
        });

        //Añadir click a botón
        Button btnclasi = (Button)findViewById(R.id.clasi);

        btnclasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tx.setTextColor(Color.parseColor("#bdbdbd"));
                startActivity(new Intent(Menu.this, Clasification.class));
            }
        });

        Button yes = (Button)findViewById(R.id.yes);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tx.setTextColor(Color.parseColor("#bdbdbd"));
                startActivity(new Intent(Menu.this, Questions.class));
            }
        });

        Button no = (Button)findViewById(R.id.no);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View b = findViewById(R.id.play);
                b.setVisibility(View.VISIBLE);
                b = findViewById(R.id.textView3);
                b.setVisibility(View.VISIBLE);
                b = findViewById(R.id.clasi);
                b.setVisibility(View.VISIBLE);
                b = findViewById(R.id.config);
                b.setVisibility(View.VISIBLE);
                b = findViewById(R.id.yes);
                b.setVisibility(View.INVISIBLE);
                b = findViewById(R.id.no);
                b.setVisibility(View.INVISIBLE);
                b = findViewById(R.id.confirm);
                b.setVisibility(View.INVISIBLE);
            }
        });
    }
}
