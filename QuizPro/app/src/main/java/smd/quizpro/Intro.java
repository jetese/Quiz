package smd.quizpro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Intro extends AppCompatActivity {
    TextView tx;
    private PlayerDao mPlayerDao;
    private final static String anon = "anonimous";
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

        //Seleccionar base de datos
        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(this);
        mPlayerDao = db.playerDao();

        //Crear el usuario anonimo si no está creado
        if(mPlayerDao.selectPlayer(anon) == null){
            Drawable drawable = this.getDrawable(R.drawable.spider);
            // convert drawable to bitmap
            String bitmap = BitMapToString(((BitmapDrawable)drawable).getBitmap());
            Player j = new Player(anon,0,0,bitmap);
            mPlayerDao.insert(j);
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
