package com.example.playware_final_project;

import static com.example.playware_final_project.Sounds.context;
import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_INDIGO;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class PianoTiles extends Game {
    MotoConnection connection = MotoConnection.getInstance();

    ArrayList<Integer> color_order = new ArrayList(42);
    //Twinkle twinkle has 42 notes
//    public static MediaPlayer mediaPlayer;

    int currentTile = 0;
    int currentSound = 0;


    PianoTiles() {
        setName("Piano Tiles");

        GameType gt = new GameType(1, GameType.GAME_TYPE_SCORE, 42, "twinkle twinkle little star", 1);
        addGameType(gt);

    }

    @Override
    public void onGameStart() {




        super.onGameStart();
        Log.d("@@@", "Starting game");



        connection.setAllTilesIdle(LED_COLOR_OFF);
        connection.setTileColor(1, connection.connectedTiles.get(0));
        connection.setTileColor(2, connection.connectedTiles.get(1));
        connection.setTileColor(3, connection.connectedTiles.get(2));
        connection.setTileColor(4, connection.connectedTiles.get(3));

        Log.d("@@@", "Preparing song");


        //Fill random color orders
        for (int i = 0; i < 42; i++) {
            color_order.add(getRandomNumber(0, 4));

        }



       // Sounds.playSound(0);
    }



    @Override
    public void onGameUpdate(byte[] message) {

        super.onGameUpdate(message);
        super.onGameUpdate(message);

        int tileId = AntData.getId(message);
        int event = AntData.getCommand(message);
        //int colour = AntData.getColorFromPress(message);


        if (event == EVENT_PRESS) {
            // Correct tile block
            int tileNeeded = color_order.get(currentTile);
            if (tileId == tileNeeded) // To check if the correct tile has been pressed, we check the tile id
            {
                Log.d("@@@", "Correct");
                //Sounds.playSound(sounds_order.get(currentSound));
                currentTile++;
//                currentSound++;
                incrementPlayerScore(1, 1);
            } else // Incorrect tile block
            {
                Log.d("@@@", "inCorrect");
               // Sounds.playSound(9);
            }
        } else // No attempt block
        {
            Log.d("@@@", "no attempt");
            incrementPlayerScore(0, 1); // No change to the score

        }
    }

    @Override
    public void onGameEnd() {
        super.onGameEnd();
        connection.setAllTilesBlink(5, LED_COLOR_INDIGO);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}







