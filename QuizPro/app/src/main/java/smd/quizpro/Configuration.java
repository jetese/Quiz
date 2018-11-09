package smd.quizpro;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Configuration extends AppCompatActivity {
    public static final String EXTRA_REPLY = "smd.quizpro.android.playerlistsql.REPLY";
    private EditText mEditPlayerView;
    private PlayerDao mPlayerDao;
    private static String anon = "anonimous";
    public static String user="anonimous";

    private String[] dif= {
            "5 preguntas",
            "10 preguntas",
            "15 preguntas"
    };

    private String[] quest= {
            "Conjunto 1",
            "Conjunto 2"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        //Seleccionar base de datos
        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(this);
        mPlayerDao = db.playerDao();

        //Eliminar barra de herramientas
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Añadir fuente a los diferentes elementos
        TextView tx = (TextView)findViewById(R.id.textView3);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.textView4);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.textView5);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.send);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.dif);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.textView6);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.questions);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.plain_text_input);
        tx.setTypeface(custom_font);
        tx.setText(user);

        //Crear el usuario anonimo si no está creado
        if(mPlayerDao.selectPlayer(anon) == null){
            Drawable drawable = this.getDrawable(R.drawable.spider);
        // convert drawable to bitmap
            String bitmap = BitMapToString(((BitmapDrawable)drawable).getBitmap());
            Player j = new Player(anon,0,0,bitmap);
            mPlayerDao.insert(j);
        }


        Button buttondif = findViewById(R.id.dif);
        int difficulty = mPlayerDao.selectDifficulty(user);
        buttondif.setText(dif[difficulty]);

        Button ButtonQuest = findViewById(R.id.questions);
        int questions = mPlayerDao.selectQuestions(user);
        ButtonQuest.setText(quest[questions]);

    }

    public void chooseProfile (View v){
        startActivity(new Intent(Configuration.this, Profile.class));
    }

    public void setDifficulty(View v){
        Button button = findViewById(R.id.dif);
        int difficulty = mPlayerDao.selectDifficulty(user);
        difficulty = (difficulty+1)%dif.length;
        mPlayerDao.updateDifficulty(difficulty,user);
        button.setText(dif[difficulty]);
    }

    public void setQuestions(View v){
        Button button = findViewById(R.id.questions);
        int questions = mPlayerDao.selectQuestions(user);
        questions = (questions+1)%quest.length;
        mPlayerDao.updateQuestions(questions,user);
        button.setText(quest[questions]);
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
