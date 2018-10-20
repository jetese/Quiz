package smd.quizpro;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        TextView tx = (TextView)findViewById(R.id.textView3);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.button);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.button2);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.button3);
        tx.setTypeface(custom_font);
    }
}
