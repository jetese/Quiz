package smd.quizpro;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Questions extends AppCompatActivity {

    public final static int VIDEO_TYPE = 1;
    public final static int RADIAL_TYPE = 2;
    public final static int AUDIO_TYPE =3;

    public final static int EASY_GROUP = 0;
    public final static int HARD_GROUP = 1;

    /*BASE DE DATOS */

    private  QuestionDao qDao;
    private PlayerDao pDao;

    /* VARIABLES DE LA VISTA */
    RelativeLayout normalQuestion;
    Typeface custom_font;
    TextView tx;
    ArrayList<Question> allQuestions;
    VideoView video;
    TextView numberQuestion, correctIncorrect;

    /* VARIABLES DE USUARIO */
    int punt = 0;
    int correctQuestions = 0;
    int incorrectQuestions = 0;
    int totalQuestions = 10;
    int actualQuestionIndex = 0;

    /* AUDIO PLAYER */

    private boolean prepared = true;
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        normalQuestion = (RelativeLayout) findViewById(R.id.content_question_layout);
        normalQuestion.removeAllViews();
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");

        setSpidermanFontMain();

        numberQuestion = findViewById(R.id.question_number);
        correctIncorrect = findViewById(R.id.correct_answers);


        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(this);
        qDao = db.questionDao();
        pDao = db.playerDao();

        int difficulty = pDao.selectDifficulty(Configuration.user);
        int questions = pDao.selectQuestions(Configuration.user);

        System.out.println("Question group: "  + questions);



        allQuestions = (ArrayList) qDao.selectAll();

        createQuestions();

/*
        if(allQuestions.size() == 0) {
            createQuestions();
        }*/


        switch (difficulty){
            case 0:
                allQuestions = (ArrayList) qDao.selectWithLimit(5, questions);
                break;
            case 1:
                allQuestions = (ArrayList) qDao.selectWithLimit(10, questions);
                break;
            case 2:
                allQuestions = (ArrayList) qDao.selectWithLimit(15, questions);
                break;
            default:
                allQuestions = (ArrayList) qDao.selectWithLimit(15, questions);
                break;
        }

        totalQuestions = allQuestions.size();
        System.out.println("Recuperadas " + totalQuestions + " preguntas");
        setQuestion(actualQuestionIndex);

    }

    protected void createQuestions(){

        allQuestions = new ArrayList<>();

        String respuestas = "";
        respuestas += "Los Ramones;";
        respuestas += "Eric Clapton;";
        respuestas += "Quentin Tarantino;";
        respuestas += "Rosalía";
        String media = "android.resource://" + getPackageName() + "/" + R.raw.ramones_intro;

        Question q = new Question("¿Quién compuso esta canción de Spiderman?", VIDEO_TYPE, EASY_GROUP, respuestas, 0, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas += "Electro;";
        respuestas += "J.J. Jameson;";
        respuestas += "Tio Ben;";
        respuestas += "Norman Osborn";

        media = "android.resource://" + getPackageName() + "/" + R.raw.jonah;

        q = new Question("¿Con quién está hablando Peter Parker?", AUDIO_TYPE, EASY_GROUP, respuestas, 1, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas += "Venom;";
        respuestas += "Duende Verde;";
        respuestas += "Electro;";
        respuestas += "Rhino";
        media = "android.resource://" + getPackageName() + "/" + R.drawable.electro5;


        q = new Question("¿Quién es este enemigo de Spiderman?", RADIAL_TYPE, EASY_GROUP, respuestas, 2, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas+= "Gwen Stacy;";
        respuestas+= "May Parker;";
        respuestas+= "Viuda Negra;";
        respuestas+= "Mary Jane Watson";

        media = "android.resource://" + getPackageName() + "/" + R.raw.kiss;

        q = new Question("¿Quién besa a Spiderman en esta escena?", VIDEO_TYPE, EASY_GROUP, respuestas, 3, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas+= "The Amazing Spiderman;";
        respuestas+= "Spiderman And His Amazing Friends;";
        respuestas+= "The Special Spiderman;";
        respuestas+="The Great Spiderman And Buddies";

        media = "android.resource://" + getPackageName() + "/" + R.raw.spidermanfriends;

        q = new Question("¿A qué serie pertenece esta introducción?", VIDEO_TYPE, EASY_GROUP, respuestas, 1, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas+= "1977;";
        respuestas+= "1995;";
        respuestas+= "2001;";
        respuestas+= "1960";

        media = "android.resource://" + getPackageName() + "/" + R.raw.series1977;

        q = new Question("¿De qué año es esta serie de Spiderman?", VIDEO_TYPE, EASY_GROUP, respuestas, 0, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas +="Mysterio;";
        respuestas += "Kingpin;";
        respuestas += "Duende Verde;";
        respuestas += "Daredevil";

        media = "android.resource://" + getPackageName() + "/" + R.raw.green_goblin;

        q = new Question("¿Quién es el siguiente enemigo del trepamuros?", VIDEO_TYPE, EASY_GROUP, respuestas, 2, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas += "Peter Parker;";
        respuestas += "Miles Morales;";
        respuestas += "Otto Octavius;";
        respuestas += "Arthur Morgan";

        media = "android.resource://" + getPackageName() + "/" + R.drawable.miles;

        q = new Question("¿Quién es el siguiente personaje?", RADIAL_TYPE, EASY_GROUP, respuestas, 1, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas += "Daily Bugle;";
        respuestas += "New York Times;";
        respuestas += "Marvel Gacette;";
        respuestas += "ABC";

        media = "android.resource://" + getPackageName() + "/" + R.drawable.periodico;

        q = new Question("¿Cómo se llama el periódico en el que escribe Peter Parker?", RADIAL_TYPE, EASY_GROUP, respuestas, 0, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas += "Dmitri Anatoly;";
        respuestas += "Sergei Kravinoff;";
        respuestas += "Aleksei Sytsevich;";
        respuestas += "Joaquín";

        media = "android.resource://" + getPackageName() + "/" + R.drawable.rhino;

        q = new Question("¿Cuál es el nombre real de este villano?", RADIAL_TYPE, EASY_GROUP, respuestas, 2, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas += "Tony Stark;";
        respuestas += "Capitan America;";
        respuestas += "Elon Musk;";
        respuestas += "Nick Fury";

        media = "android.resource://" + getPackageName() + "/" + R.drawable.civil_war_suit;

        q = new Question("¿Quién diseño este traje para Spiderman?", RADIAL_TYPE, EASY_GROUP, respuestas, 0, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas += "Spiderman Negro;";
        respuestas += "Eddie Brock;";
        respuestas += "Buitre;";
        respuestas += "Venom";

        media = "android.resource://" + getPackageName() + "/" + R.raw.venom;

        q = new Question("¿A quién pertenece la voz del siguiente personaje?", AUDIO_TYPE, EASY_GROUP, respuestas, 3, media);

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

        q = new Question("¿Quién ha desarrollado este juego de Spiderman?", VIDEO_TYPE, EASY_GROUP, respuestas, 3, media);

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

        q = new Question("¿Donde sucedió la primera aparición del hombre araña?", RADIAL_TYPE, EASY_GROUP, respuestas, 2, media);

        allQuestions.add(q);

        respuestas = "";
        respuestas += "Stan Lee;";
        respuestas += "John Romita;";
        respuestas += "Jack Kirby;";
        respuestas += "Steve Ditko";

        media = "android.resource://" + getPackageName() + "/" + R.drawable.stan_lee;

        q = new Question("¿Cual de las siguientes personas no participó en la creación del personaje?", RADIAL_TYPE, EASY_GROUP, respuestas, 1, media);

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


        qDao.deleteAll();

        for(Question ques : allQuestions){
            qDao.insert(ques);
        }

        System.out.println("Inserted " + allQuestions.size() + " questions");

    }


    public void setQuestion(int qNumber){
        int actualQuestion = qNumber + 1;
        numberQuestion.setText("Pregunta: "+ actualQuestion +"/"+ totalQuestions);

        Question q = allQuestions.get(actualQuestionIndex);
        TextView tv = findViewById(R.id.question_title);
        tv.setText(q.getText());
        switch(q.getType()){
            case VIDEO_TYPE:
                setVideoQuestion(q);
                break;
            case RADIAL_TYPE:
                setRadialQuestion(q);
                break;
            case AUDIO_TYPE:
                setAudioQuestion(q);
                break;

        }

    }

    public void setVideoQuestion(Question q){

        normalQuestion.removeAllViews();
        View.inflate(this, R.layout.video_layout, normalQuestion);

        video = (VideoView) findViewById(R.id.videoView);
        video.setVideoURI(Uri.parse(q.getMedia()));
        setSpidermanFontVideo();

        String[] answers = q.getArrayAnswers();

        tx = (TextView) findViewById(R.id.video_answer_1);
        tx.setText(answers[0]);
        tx = (TextView) findViewById(R.id.video_answer_2);
        tx.setText(answers[1]);
        tx = (TextView) findViewById(R.id.video_answer_3);
        tx.setText(answers[2]);
        tx = (TextView) findViewById(R.id.video_answer_4);
        tx.setText(answers[3]);

        MediaController mc = new MediaController(this);
        video.setMediaController(mc);
        mc.setAnchorView(video);
        video.start();
    }

    public void setAudioQuestion(Question q){
        normalQuestion.removeAllViews();
        View.inflate(this, R.layout.audio_layout, normalQuestion);
        setSpidermanFontAudio();

        prepared = true;

        String[] answers = q.getArrayAnswers();

        tx = (TextView) findViewById(R.id.audio_answer_1);
        tx.setText(answers[0]);
        tx = (TextView) findViewById(R.id.audio_answer_2);
        tx.setText(answers[1]);
        tx = (TextView) findViewById(R.id.audio_answer_3);
        tx.setText(answers[2]);
        tx = (TextView) findViewById(R.id.audio_answer_4);
        tx.setText(answers[3]);

        Button play = findViewById(R.id.play_button);
        Button pause = findViewById(R.id.pause_button);
        Button stop = findViewById(R.id.stop_button);

        mp = MediaPlayer.create(Questions.this, Uri.parse(q.getMedia()));
        mp.setVolume(2000, 2000);
        mp.start();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!prepared) {
                    try {
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    prepared = true;
                }
                mp.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mediaPlayer.seekTo(0);
                //mediaPlayer.pause();
                mp.stop();
                prepared = false;
            }
        });
    }

    public void setRadialQuestion(Question q){

        normalQuestion.removeAllViews();
        View.inflate(this, R.layout.radialbutton_layout, normalQuestion);

        ImageView iv = findViewById(R.id.radialImage);
        iv.setImageURI(Uri.parse(q.getMedia()));

        String[] answers = q.getArrayAnswers();

        tx = (TextView) findViewById(R.id.radio_answer_1);
        tx.setText(answers[0]);
        tx = (TextView) findViewById(R.id.radio_answer_2);
        tx.setText(answers[1]);
        tx = (TextView) findViewById(R.id.radio_answer_3);
        tx.setText(answers[2]);
        tx = (TextView) findViewById(R.id.radio_answer_4);
        tx.setText(answers[3]);

        setSpidermanFontRadial();

    }

    public void checkCorrectQuestion(View v){
        if(actualQuestionIndex == totalQuestions - 1) {
            mp.stop();
            video.stopPlayback();
            punt = (correctQuestions * 10) - (incorrectQuestions * 5);
            if(punt<0)
                punt = 0;
            pDao.updateScoreGreater(Configuration.user, punt);
            startActivity(new Intent(Questions.this, Clasification.class));

            System.out.println("Terminada partida");
        }
        else {
            Question actualQ = allQuestions.get(actualQuestionIndex);
            RadioGroup rg = findViewById(R.id.rgroup);
            if (actualQ.getType() == VIDEO_TYPE) {
                video.stopPlayback();
                rg = findViewById(R.id.rgroup);
            } else if (actualQ.getType() == RADIAL_TYPE) {
                rg = findViewById(R.id.radial_group);
            } else if (actualQ.getType() == AUDIO_TYPE) {
                mp.stop();
                rg = findViewById(R.id.audio_rgroup);
            }


            int buttonId = rg.getCheckedRadioButtonId();
            RadioButton rb = findViewById(buttonId);
            String buttonText = rb.getText().toString();
            String[] answers = actualQ.getArrayAnswers();
            String correctAnswer = answers[actualQ.getCorrectAnswerId()];

            if (buttonText.equals(correctAnswer)) {
                correctQuestions++;
                System.out.println("Respuesta correcta");
            } else {
                incorrectQuestions++;
                System.out.println("Respuesta incorrecta");
            }

            correctIncorrect.setText("Aciertos/Fallos: " + correctQuestions + "/" + incorrectQuestions);

            actualQuestionIndex++;

            setQuestion(actualQuestionIndex);
        }
    }

    protected void setSpidermanFontMain(){
        tx = (TextView) findViewById(R.id.question_number);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.correct_answers);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.question_title);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.nextQuestion);
        tx.setTypeface(custom_font);
    }

    protected void setSpidermanFontRadial(){
        tx = (TextView) findViewById(R.id.radio_answer_1);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.radio_answer_2);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.radio_answer_3);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.radio_answer_4);
        tx.setTypeface(custom_font);
    }

    protected void setSpidermanFontVideo(){
        tx = (TextView) findViewById(R.id.video_answer_1);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.video_answer_2);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.video_answer_3);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.video_answer_4);
        tx.setTypeface(custom_font);
    }

    protected void setSpidermanFontAudio(){
        tx = (TextView) findViewById(R.id.audio_answer_1);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.audio_answer_2);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.audio_answer_3);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.audio_answer_4);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.play_button);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.stop_button);
        tx.setTypeface(custom_font);
        tx = (TextView) findViewById(R.id.pause_button);
        tx.setTypeface(custom_font);
    }
}
