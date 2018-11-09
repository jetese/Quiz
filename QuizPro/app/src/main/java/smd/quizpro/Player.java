package smd.quizpro;
import android.arch.persistence.room.ColumnInfo;

import android.arch.persistence.room.Entity;

import android.arch.persistence.room.PrimaryKey;

import android.support.annotation.NonNull;

@Entity(tableName = "player_table")
public class Player {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Nick")
    private String mNick;
    @ColumnInfo(name = "Score")
    private int mScore;
    @ColumnInfo(name = "Difficulty")
    private int mDifficulty;
    @ColumnInfo(name = "Questions")
    private int mQuestions;
    @ColumnInfo(name = "Date")
    private String mDate;
    @ColumnInfo(name = "NumberGames")
    private int mNumberGames;
    @ColumnInfo(name = "Photo")
    private String mPhoto;

    public Player(String mNick, int mDifficulty, int mQuestions, String mPhoto) {
        this.mNick = mNick;
        this.mScore = 0;
        this.mDifficulty = mDifficulty;
        this.mQuestions = mQuestions;
        this.mDate = "";
        this.mNumberGames = 0;
        this.mPhoto = mPhoto;
    }

    public String getNick(){return this.mNick;}
    public void setNick(String name){this.mNick = name;}
    public int getScore(){return this.mScore;}
    public int getDifficulty(){return this.mDifficulty;}
    public void setScore(int punt){this.mScore = punt;}
    public void setDifficulty(int dif){this.mDifficulty=dif;}
    public int getQuestions(){return this.mQuestions;}
    public void setQuestions(int quest){this.mQuestions = quest;}
    public String getDate(){return this.mDate;}
    public void setDate(String date){this.mDate = date;}
    public int getNumberGames(){return  this.mNumberGames;};
    public void setNumberGames(int number){this.mNumberGames = number;}
    public String getPhoto(){return this.mPhoto;}
    public void setmPhoto(String photo){this.mPhoto = photo;}
}
