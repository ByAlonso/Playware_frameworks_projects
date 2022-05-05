package com.example.playware_final_project;

import android.content.Context;
import android.media.MediaPlayer;

public class Sounds {
    public static Context context;
    public static MediaPlayer mediaPlayer;

    public Sounds(Context context){
        this.context = context;
    }

    public static void playSound(int sound){
        switch(sound) {
            case 0:
                mediaPlayer = MediaPlayer.create(context, R.raw.a5);
                break;
            case 1:
                mediaPlayer = MediaPlayer.create(context, R.raw.c4);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(context, R.raw.c4_long);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(context, R.raw.d4);
                break;
            case 4:
                mediaPlayer = MediaPlayer.create(context, R.raw.d4_long);
                break;
            case 5:
                mediaPlayer = MediaPlayer.create(context, R.raw.e4);
                break;
            case 6:
                mediaPlayer = MediaPlayer.create(context, R.raw.f4);
                break;
            case 7:
                mediaPlayer = MediaPlayer.create(context, R.raw.g4);
                break;
            case 8:
                mediaPlayer = MediaPlayer.create(context, R.raw.g4_long);
                break;
            case 9:
                mediaPlayer = MediaPlayer.create(context, R.raw.error);
                break;
            default:
                break;
        }
        mediaPlayer.start();
        //Check if we need a delay to let the sound be played
        //mediaPlayer.release();
        //mediaPlayer = null;
    }
}
