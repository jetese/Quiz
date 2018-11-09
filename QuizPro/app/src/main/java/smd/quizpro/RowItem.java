package smd.quizpro;

public class RowItem {
    private String nickname;
    //private int pic_id;
    private String pic_id;
    private String points;
    private String date;
    private String number;

    /*public RowItem (String nickname,int pic_id,String points,String number, String date){
        this.nickname = nickname;
        this.pic_id = pic_id;
        this.points = points;
        this.date = date;
        this.number = number;
    }*/

    public RowItem (String nickname,String pic_id,String points,String number, String date){
        this.nickname = nickname;
        this.pic_id = pic_id;
        this.points = points;
        this.date = date;
        this.number = number;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /*public int getPicid(){
        return pic_id;
    }

    public void setPicid(int pic_id){
        this.pic_id = pic_id;
    }*/

    public String getPicid(){
        return pic_id;
    }

    public void setPicid(String pic_id){
        this.pic_id = pic_id;
    }
    public String getPoints(){
        return points;
    }

    public void setPoints(String points){
        this.points = points;
    }
    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }
    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }
}
