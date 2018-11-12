package smd.quizpro;

public class RowClasi {

        private String nickname;
        private String pic_id;
        private String points;


        public RowClasi (String nickname,String pic_id,String points){
            this.nickname = nickname;
            this.pic_id = pic_id;
            this.points = points;
        }

        public String getNickname(){
            return nickname;
        }

        public void setNickname(String nickname){
            this.nickname = nickname;
        }


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

}
