package smd.quizpro;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Desactivar Action Bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Añadir fuente
        AddFont();
    }

    //Añadir fuente
    private void AddFont(){
        TextView tx = (TextView)findViewById(R.id.textView3);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.play);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.clasi);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.back_menu);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.confirm);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.yes);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.no);
        tx.setTypeface(custom_font);
    }

    //Click botón configuración
    public void Configuration(View v){
        startActivity(new Intent(Menu.this, Configuration.class));
    }

    //Click botón Jugar
    public void Play(View v){
        if(Configuration.user == "anonimous"){
            View b = findViewById(R.id.play);
            b.setVisibility(View.INVISIBLE);
            b = findViewById(R.id.textView3);
            b.setVisibility(View.INVISIBLE);
            b = findViewById(R.id.clasi);
            b.setVisibility(View.INVISIBLE);
            b = findViewById(R.id.back_menu);
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

    //Botón clasificación
    public void Clasification(View v){
        startActivity(new Intent(Menu.this, Clasification.class));
    }

    //Confimar empezar como anónimo
    public void Confirm(View v){
        startActivity(new Intent(Menu.this, Questions.class));
    }

    //Denegar empezar como anónimo
    public void Deny(View v){
        View b = findViewById(R.id.play);
        b.setVisibility(View.VISIBLE);
        b = findViewById(R.id.textView3);
        b.setVisibility(View.VISIBLE);
        b = findViewById(R.id.clasi);
        b.setVisibility(View.VISIBLE);
        b = findViewById(R.id.back_menu);
        b.setVisibility(View.VISIBLE);
        b = findViewById(R.id.yes);
        b.setVisibility(View.INVISIBLE);
        b = findViewById(R.id.no);
        b.setVisibility(View.INVISIBLE);
        b = findViewById(R.id.confirm);
        b.setVisibility(View.INVISIBLE);
    }


}
