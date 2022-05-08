package com.example.playware_final_project;

import com.google.gson.annotations.SerializedName;

public class UserModel {

        @SerializedName("username")
        private String username;

        @SerializedName("highscore")
        private Integer highscore;

        @SerializedName("song")
        private String song;


        public UserModel(String name, String song,Integer highscore) {
            this.username = name;
            this.highscore = highscore;
            this.song = song;
        }

        public String getName() {
            return username;
        }
        public String getSong() {
            return song;
        }
        public Integer getHighscore() {
            return highscore;
        }

}
