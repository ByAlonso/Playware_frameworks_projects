package com.example.playware_final_project;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_INDIGO;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.ArrayList;
import java.util.Random;

public class PianoTiles extends Game
{
    MotoConnection connection = MotoConnection.getInstance();
    int random_tile_id;
    ArrayList<Integer> sounds_order = new ArrayList(42);
    ArrayList<Integer> color_order = new ArrayList(42);
    //Twinkle twinkle has 42 notes
    public static MediaPlayer mediaPlayer;

    PianoTiles()
    {
        setName("Piano Tiles");

        GameType gt = new GameType(1, GameType.GAME_TYPE_SCORE, 42, "twinkle twinkle little star",1);
        addGameType(gt);

    }

    @Override
    public void onGameStart()
    {
        super.onGameStart();
        connection.setAllTilesIdle(LED_COLOR_OFF);
        connection.setTileColor(1,1);
        connection.setTileColor(2,2);
        connection.setTileColor(3,3);
        connection.setTileColor(4,4);


        //Adding the order of the sounds to the array
        sounds_order.add(1);
        sounds_order.add(1);
        sounds_order.add(7);
        sounds_order.add(7);
        sounds_order.add(0);
        sounds_order.add(0);
        sounds_order.add(8);

        sounds_order.add(6);
        sounds_order.add(6);
        sounds_order.add(5);
        sounds_order.add(5);
        sounds_order.add(3);
        sounds_order.add(3);
        sounds_order.add(2);

        sounds_order.add(7);
        sounds_order.add(7);
        sounds_order.add(6);
        sounds_order.add(6);
        sounds_order.add(5);
        sounds_order.add(5);
        sounds_order.add(4);

        sounds_order.add(7);
        sounds_order.add(7);
        sounds_order.add(6);
        sounds_order.add(6);
        sounds_order.add(5);
        sounds_order.add(5);
        sounds_order.add(4);

        sounds_order.add(1);
        sounds_order.add(1);
        sounds_order.add(7);
        sounds_order.add(7);
        sounds_order.add(0);
        sounds_order.add(0);
        sounds_order.add(8);

        sounds_order.add(6);
        sounds_order.add(6);
        sounds_order.add(5);
        sounds_order.add(5);
        sounds_order.add(3);
        sounds_order.add(3);
        sounds_order.add(2);


        //Fill random color orders
        for(int i = 0; i < color_order.size(); i++){
            color_order.add(getRandomNumber(1,4));
        }
    }

    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);

        super.onGameUpdate(message);
        int tileId = AntData.getId(message);
        int event = AntData.getCommand(message);
        //int colour = AntData.getColorFromPress(message);
        int currentTile = 0;
        int currentSound = 0;

        if (event == EVENT_PRESS)
        {
            // Correct tile block
            if (tileId == color_order.get(currentTile)) // To check if the correct tile has been pressed, we check the tile id
            {
                Sounds.playSound(sounds_order.get(currentSound));
                currentTile++;
                currentSound++;

            }
            else // Incorrect tile block
            {
                Sounds.playSound(9);
            }
        }
        else // No attempt block
        {
            incrementPlayerScore(0,1); // No change to the score
            this.getOnGameEventListener().onGameTimerEvent(0); // No change to the timing
        }
    }

    @Override
    public void onGameEnd()
    {
        super.onGameEnd();
        connection.setAllTilesBlink(5, LED_COLOR_INDIGO);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}
