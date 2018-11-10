package smd.quizpro;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class Questions extends AppCompatActivity {

    public final static int VIDEO_TYPE = 1;
    public final static int RADIAL_TYPE = 2;
    public final static int AUDIO_TYPE =3;

    public final static int EASY_GROUP = 0;
    public final static int HARD_GROUP = 1;

    /*BASE DE DATOS */

    private  QuestionDao qDao;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        normalQuestion = (RelativeLayout) findViewById(R.id.content_question_layout);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");

        numberQuestion = findViewById(R.id.question_number);
        correctIncorrect = findViewById(R.id.correct_answers);


        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(this);
        qDao = db.questionDao();

        setSpidermanFontMain();

        allQuestions = (ArrayList) qDao.selectAll();

        if(allQuestions.size() == 0) {
            createQuestions();
        }


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
        respuestas += "Venom;";
        respuestas += "Duende Verde;";
        respuestas += "Electro;";
        respuestas += "Rhino";
        media = "android.resource://" + getPackageName() + "/" + R.drawable.electro5;


        q = new Question("¿Quién es este enemigo de Spiderman?", RADIAL_TYPE, EASY_GROUP, respuestas, 2, media);

        allQuestions.add(q);

        qDao.deleteAll();

        for(Question ques : allQuestions){
            qDao.insert(ques);
        }

        System.out.println("Inserted questions");

    }


    public void setQuestion(int qNumber){
        numberQuestion.setText("Pregunta: "+ qNumber +1 +"/"+ totalQuestions);

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

        }

    }

    public void setVideoQuestion(Question q){

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

    public void setRadialQuestion(Question q){

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
        Question actualQ = allQuestions.get(actualQuestionIndex);
        RadioGroup rg= findViewById(R.id.rgroup);
        if(actualQ.getType()== VIDEO_TYPE){
            video.stopPlayback();
            rg = findViewById(R.id.rgroup);
        }
        else if(actualQ.getType() == RADIAL_TYPE){
            rg = findViewById(R.id.radial_group);
        }


        int buttonId = rg.getCheckedRadioButtonId();
        RadioButton rb = findViewById(buttonId);
        String buttonText = rb.getText().toString();
        String[] answers = actualQ.getArrayAnswers();
        String correctAnswer = answers[actualQ.getCorrectAnswerId()];

        if(buttonText.equals(correctAnswer)){
            correctQuestions++;
            System.out.println("Respuesta correcta");
        }
        else{
            incorrectQuestions++;
            System.out.println("Respuesta incorrecta");
        }

        correctIncorrect.setText("Aciertos/Fallos: "+correctQuestions+"/"+ incorrectQuestions);

        actualQuestionIndex++;

        setQuestion(actualQuestionIndex);


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
}
