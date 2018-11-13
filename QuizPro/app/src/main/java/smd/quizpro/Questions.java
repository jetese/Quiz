package smd.quizpro;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Questions extends AppCompatActivity {

    public static final int VIDEO_TYPE = 1;
    public static final int RADIAL_TYPE = 2;
    public static final int AUDIO_TYPE =3;

    public static final int EASY_GROUP = 0;
    public static final int HARD_GROUP = 1;

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


    long startTime = 0;
    TextView timerTextView;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_questions);
        normalQuestion = (RelativeLayout) findViewById(R.id.content_question_layout);
        normalQuestion.removeAllViews();
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");

        timerTextView = findViewById(R.id.timer_text);
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        setSpidermanFontMain();

        numberQuestion = findViewById(R.id.question_number);
        correctIncorrect = findViewById(R.id.correct_answers);


        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(this);
        qDao = db.questionDao();
        pDao = db.playerDao();

        int difficulty = pDao.selectDifficulty(Configuration.user);
        int questions = pDao.selectQuestions(Configuration.user);

        System.out.println("Question group: "  + questions);

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
            this.endGame();
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

            correctIncorrect.setText("A/F: " + correctQuestions + "/" + incorrectQuestions);

            actualQuestionIndex++;

            setQuestion(actualQuestionIndex);
        }
    }

    protected void endGame(){
        if(mp != null) {
            mp.stop();
        }
        if(video != null) {
            video.stopPlayback();
        }
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) (millis / 1000);
        System.out.println("Terminada la partida en " + seconds + " segundos");

        punt = (int)(correctQuestions*100/seconds);
        System.out.println("Punt: " + punt);
        if(punt<0)
            punt = 0;
        System.out.println("Puntuación: " + punt);

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        pDao.updateScoreGreater(Configuration.user, punt);
        pDao.updateNumberGames(Configuration.user);
        pDao.updateDate(Configuration.user, date);

        Intent endGame = new Intent(Questions.this, EndGame.class);
        endGame.putExtra("PUNTUATION", punt);
        startActivity(endGame);
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
        tx = (TextView) findViewById(R.id.timer_text);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Questions.this, Menu.class));
    }
}
