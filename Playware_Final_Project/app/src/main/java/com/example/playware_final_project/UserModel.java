package com.example.playware_final_project;

import com.google.gson.annotations.SerializedName;

public class UserModel {

        @SerializedName("username")
        private String username;

        @SerializedName("highscore")
        private Integer highscore;


        public UserModel(String name, Integer highscore) {
            this.username = name;
            this.highscore = highscore;
        }

        public String getName() {
            return username;
        }
        public Integer getHighscore() {
            return highscore;
        }

}
