package smd.quizpro;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName= "question_table")
public class Question {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="text")
    private String text;
    @ColumnInfo(name="type")
    private int type;
    @ColumnInfo(name="group")
    private int group;
    @ColumnInfo(name="answers")
    private String answers;
    @ColumnInfo(name="correctAnswerId")
    private int correctAnswerId;
    @ColumnInfo(name="media")
    private String media;



    public Question(String text, int type, int group, String answers, int correctAnswerId, String media) {
        this.text = text;
        this.type = type;
        this.group = group;
        this.answers = answers;
        this.correctAnswerId = correctAnswerId;

        this.media = media;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setCorrectAnswerId(int correctAnswerId) {

        this.correctAnswerId = correctAnswerId;
    }

    public String[] getArrayAnswers(){
        String[] ret = answers.split(";");
        return ret;
    }
}
