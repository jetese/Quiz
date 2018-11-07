package smd.quizpro;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
            Player j = new Player(anon,0,0);
            mPlayerDao.insert(j);
        }
        /*mEditPlayerView = findViewById(R.id.plain_text_input);
        final Button button = findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditPlayerView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = mEditPlayerView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);
                    word = word.toLowerCase();
                    Player p;
                    p = mPlayerDao.selectPlayer(word);
                    if (p == null){
                        Player jugador = new Player(word,0,0);
                        mPlayerDao.insert(jugador);
                    }
                    user = word;
                }
                finish();
            }
        });*/

        Button buttondif = findViewById(R.id.dif);
        int difficulty = mPlayerDao.selectDifficulty(user);
        buttondif.setText(dif[difficulty]);

        Button ButtonQuest = findViewById(R.id.questions);
        int questions = mPlayerDao.selectQuestions(user);
        ButtonQuest.setText(quest[questions]);

        /*buttondif.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int difficulty = mPlayerDao.selectDifficulty(user);
                difficulty = (difficulty+1)%3;
                mPlayerDao.updateDifficulty(difficulty,user);
                buttondif.setText(dif[difficulty]);

            }
        });*/
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
}
