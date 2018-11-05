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

    public Player(String mNick, int mScore, int mDifficulty) {
        this.mNick = mNick;
        this.mScore = mScore;
        this.mDifficulty = mDifficulty;
    }

    public String getNick(){return this.mNick;}
    public void setNick(String name){this.mNick = name;}
    public int getScore(){return this.mScore;}
    public int getDifficulty(){return this.mDifficulty;}
    public void setScore(int punt){this.mScore = punt;}
    public void setDifficulty(int dif){this.mDifficulty=dif;}
}
