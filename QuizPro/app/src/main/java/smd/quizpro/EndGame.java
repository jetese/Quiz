package smd.quizpro;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

    TextView tx;
    Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");

        int punt = getIntent().getIntExtra("PUNTUATION", 0);

        tx = (TextView) findViewById(R.id.puntVal);
        tx.setText(Integer.toString(punt));
        setSpidermanFontEnd();
    }

    protected void setSpidermanFontEnd(){
        tx = (TextView) findViewById(R.id.EndText);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.puntText);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.puntVal);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.back_menu);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.back_clasif);
        tx.setTypeface(custom_font);
    }

    public void goToMenu(View v){
        startActivity(new Intent(EndGame.this, Menu.class));
    }

    public void goToClasif(View v){
        startActivity(new Intent(EndGame.this, Clasification.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EndGame.this, Menu.class));
    }
}
