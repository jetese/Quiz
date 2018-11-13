package smd.quizpro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import smd.quizpro.Questions;

import static smd.quizpro.Questions.VIDEO_TYPE;
import static smd.quizpro.Questions.AUDIO_TYPE;
import static smd.quizpro.Questions.RADIAL_TYPE;
import static smd.quizpro.Questions.EASY_GROUP;
import static smd.quizpro.Questions.HARD_GROUP;

public class Intro extends AppCompatActivity {
    TextView tx;
    private PlayerDao mPlayerDao;
    private QuestionDao mQuestionDao;
    private final static String anon = "anonimous";

    PlayerRoomDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        db = PlayerRoomDatabase.getDatabase(this);
        //Desactivar Action Bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AddFont();
        CreateAnonUser();
        CreateQuestions();
    }

    //Añadir fuente a los elementos de la actividad
    private void AddFont(){
        tx = (TextView)findViewById(R.id.textView);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.textView2);
        tx.setTypeface(custom_font);
    }

    //Botón de comenzar
    public void ButtonStart(View v){
        startActivity(new Intent(Intro.this, Menu.class));
    }

    //Crear el usuario anonimo si no está creado
    private void CreateAnonUser(){
        //Seleccionar base de datos

        mPlayerDao = db.playerDao();

        if(mPlayerDao.selectPlayer(anon) == null){
            Drawable drawable = this.getDrawable(R.drawable.spider);
            // convert drawable to bitmap
            String bitmap = BitMapToString(((BitmapDrawable)drawable).getBitmap());
            Player j = new Player(anon,0,0,bitmap);
            mPlayerDao.insert(j);
        }
    }

    protected void CreateQuestions(){

        mQuestionDao = db.questionDao();

        List<Question> allQuestions = new ArrayList<>();

        allQuestions = mQuestionDao.selectAll();

        if(allQuestions.size() == 0) {

            String respuestas = "";
            respuestas += "Los Ramones;";
            respuestas += "Eric Clapton;";
            respuestas += "Quentin Tarantino;";
            respuestas += "Rosalia";
            String media = "android.resource://" + getPackageName() + "/" + R.raw.ramones_intro;

            Question q = new Question("¿Quien compuso esta canción de Spiderman?", VIDEO_TYPE, EASY_GROUP, respuestas, 0, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Electro;";
            respuestas += "J.J. Jameson;";
            respuestas += "Tio Ben;";
            respuestas += "Norman Osborn";

            media = "android.resource://" + getPackageName() + "/" + R.raw.jonah;

            q = new Question("¿Con quien está hablando Peter Parker?", AUDIO_TYPE, EASY_GROUP, respuestas, 1, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Venom;";
            respuestas += "Duende Verde;";
            respuestas += "Electro;";
            respuestas += "Rhino";
            media = "android.resource://" + getPackageName() + "/" + R.drawable.electro5;


            q = new Question("¿Quien es este enemigo de Spiderman?", RADIAL_TYPE, EASY_GROUP, respuestas, 2, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Gwen Stacy;";
            respuestas += "May Parker;";
            respuestas += "Viuda Negra;";
            respuestas += "Mary Jane Watson";

            media = "android.resource://" + getPackageName() + "/" + R.raw.kiss;

            q = new Question("¿Quien besa a Spiderman en esta escena?", VIDEO_TYPE, EASY_GROUP, respuestas, 3, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "The Amazing Spiderman;";
            respuestas += "Spiderman And His Amazing Friends;";
            respuestas += "The Special Spiderman;";
            respuestas += "The Great Spiderman And Buddies";

            media = "android.resource://" + getPackageName() + "/" + R.raw.spidermanfriends;

            q = new Question("¿A que serie pertenece esta introduccion?", VIDEO_TYPE, HARD_GROUP, respuestas, 1, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "1977;";
            respuestas += "1995;";
            respuestas += "2001;";
            respuestas += "1960";

            media = "android.resource://" + getPackageName() + "/" + R.raw.series1977;

            q = new Question("¿De que año es esta serie de Spiderman?", VIDEO_TYPE, HARD_GROUP, respuestas, 0, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Mysterio;";
            respuestas += "Kingpin;";
            respuestas += "Duende Verde;";
            respuestas += "Daredevil";

            media = "android.resource://" + getPackageName() + "/" + R.raw.green_goblin;

            q = new Question("¿Quien es el siguiente enemigo del trepamuros?", VIDEO_TYPE, EASY_GROUP, respuestas, 2, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Peter Parker;";
            respuestas += "Miles Morales;";
            respuestas += "Otto Octavius;";
            respuestas += "Arthur Morgan";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.miles;

            q = new Question("¿Quien es el siguiente personaje?", RADIAL_TYPE, EASY_GROUP, respuestas, 1, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Daily Bugle;";
            respuestas += "New York Times;";
            respuestas += "Marvel Gacette;";
            respuestas += "ABC";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.periodico;

            q = new Question("¿Como se llama el periodico en el que escribe Peter Parker?", RADIAL_TYPE, EASY_GROUP, respuestas, 0, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Dmitri Anatoly;";
            respuestas += "Sergei Kravinoff;";
            respuestas += "Aleksei Sytsevich;";
            respuestas += "Joaquin";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.rhino;

            q = new Question("¿Cual es el nombre real de este villano?", RADIAL_TYPE, HARD_GROUP, respuestas, 2, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Tony Stark;";
            respuestas += "Capitan America;";
            respuestas += "Elon Musk;";
            respuestas += "Nick Fury";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.civil_war_suit;

            q = new Question("¿Quien diseño este traje para Spiderman?", RADIAL_TYPE, EASY_GROUP, respuestas, 0, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Spiderman Negro;";
            respuestas += "Eddie Brock;";
            respuestas += "Buitre;";
            respuestas += "Venom";

            media = "android.resource://" + getPackageName() + "/" + R.raw.venom;

            q = new Question("¿A quien pertenece la voz del siguiente personaje?", AUDIO_TYPE, EASY_GROUP, respuestas, 3, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Futurama;";
            respuestas += "Los Simpsons;";
            respuestas += "Yo y el Mundo;";
            respuestas += "Padre de Familia";

            media = "android.resource://" + getPackageName() + "/" + R.raw.spidercerdo;

            q = new Question("¿A que famosa familia de TV pertenece esta parodia?", AUDIO_TYPE, EASY_GROUP, respuestas, 1, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Suckerpunch;";
            respuestas += "ThatGameCompany;";
            respuestas += "Rockstar San Diego;";
            respuestas += "Insomniac";

            media = "android.resource://" + getPackageName() + "/" + R.raw.spidermanps4;

            q = new Question("¿Quien ha desarrollado este juego de Spiderman?", VIDEO_TYPE, EASY_GROUP, respuestas, 3, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Harry Osborn;";
            respuestas += "Edward Osborn;";
            respuestas += "Norman Osborn;";
            respuestas += "Benjen Osborn";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.harry_osborn;

            q = new Question("¿Como se llama el hijo de Norman Osborn?", RADIAL_TYPE, EASY_GROUP, respuestas, 0, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "The Amazing Spiderman #1;";
            respuestas += "Detective Comics #10;";
            respuestas += "Amazing Fantasy #15;";
            respuestas += "Fantastic Four #20";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.amazing_spiderman1;

            q = new Question("¿Donde sucedio la primera aparicion del hombre araña?", RADIAL_TYPE, HARD_GROUP, respuestas, 2, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Stan Lee;";
            respuestas += "John Romita;";
            respuestas += "Jack Kirby;";
            respuestas += "Steve Ditko";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.stan_lee;

            q = new Question("¿Cual de las siguientes personas no participo en la creacion del personaje?", RADIAL_TYPE, EASY_GROUP, respuestas, 1, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Peter Parker;";
            respuestas += "Peter Benjamin Parker;";
            respuestas += "Peter Jack Parker;";
            respuestas += "Peter Lee Parker";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.peter_parker_comic;

            q = new Question("¿Cual es el nombre completo de Spiderman?", RADIAL_TYPE, EASY_GROUP, respuestas, 1, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Scorpion;";
            respuestas += "Shocker;";
            respuestas += "Black Cat;";
            respuestas += "Joker";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.black_cat;

            q = new Question("¿Cual de los siguientes no es un enemigo de Spiderman?", RADIAL_TYPE, EASY_GROUP, respuestas, 3, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Atari 2600;";
            respuestas += "Commodore 64;";
            respuestas += "PS One;";
            respuestas += "NES";

            media = "android.resource://" + getPackageName() + "/" + R.raw.atari;

            q = new Question("¿En que consola se publico el primer juego de Spiderman?", VIDEO_TYPE, EASY_GROUP, respuestas, 0, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "The Amazing Spiderman;";
            respuestas += "Spiderman 2;";
            respuestas += "Spiderman: Into the Spiderverse;";
            respuestas += "Spiderman Homecoming";

            media = "android.resource://" + getPackageName() + "/" + R.raw.homecoming;

            q = new Question("¿A que pelicula de Spiderman pertenecen las siguientes imagenes?", VIDEO_TYPE, EASY_GROUP, respuestas, 3, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "DC;";
            respuestas += "Panini;";
            respuestas += "Marvel;";
            respuestas += "Sony";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.spiderman2;

            q = new Question("¿Que productora creo a Spiderman?", RADIAL_TYPE, EASY_GROUP, respuestas, 3, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Stalin;";
            respuestas += "Stan Lee;";
            respuestas += "Tio Ben;";
            respuestas += "Batman";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.stan_lee;

            q = new Question("¿Quien es la persona de la foto?", RADIAL_TYPE, EASY_GROUP, respuestas, 1, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "1950;";
            respuestas += "1997;";
            respuestas += "1963;";
            respuestas += "1890";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.amazing_spiderman1;

            q = new Question("¿En que año se publico el siguiente comic?", RADIAL_TYPE, HARD_GROUP, respuestas, 2, media);

            allQuestions.add(q);

            respuestas = "";
            respuestas += "Una araña;";
            respuestas += "Un mosquito;";
            respuestas += "Un dinosaurio;";
            respuestas += "Jesucristo";

            media = "android.resource://" + getPackageName() + "/" + R.drawable.spidey_3;

            q = new Question("¿Cual es el animal que concede poderes a Spiderman?", RADIAL_TYPE, EASY_GROUP, respuestas, 0, media);

            allQuestions.add(q);


            mQuestionDao.deleteAll();

            for (Question ques : allQuestions) {
                mQuestionDao.insert(ques);
            }

            System.out.println("Inserted " + allQuestions.size() + " questions");
        }
        else{
            System.out.println("Las preguntas ya estaban creadas");
        }

    }

    //Pasar BitMap a String
    private String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
