package com.example.exercise_5_group_3;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

import android.util.Log;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class AdaptiveGame extends Game {
    MotoConnection connection = MotoConnection.getInstance();
    int timeMs = 7;
    int round = 0;
    int targetColor = 1;
    ArrayList<Integer> colorList = AntData.allColors();

    AdaptiveGame(){
        setName("Adaptive Game");

        GameType gt = new GameType(1, GameType.GAME_TYPE_TIME, timeMs, "Score",1);
        addGameType(gt);

    }

    @Override
    public void onGameStart()
    {
        super.onGameStart();
        clearPlayersScore();
        updateTiles();
        Log.i("TAG",Integer.toString(targetColor));
        Log.i("TAG0",Integer.toString(colorList.get(0)));
        Log.i("TAG1",Integer.toString(colorList.get(1)));
        Log.i("TAG2",Integer.toString(colorList.get(2)));
        Log.i("TAG3",Integer.toString(colorList.get(3)));
    }

    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);

        int event = AntData.getCommand(message);
        int tile = AntData.getId(message);
        int color = AntData.getColorFromPress(message);
        if (round == 7){
            this.onGameEnd();
        }
        if (event == EVENT_PRESS)
        {
            // Correct tile block
            if (color == targetColor) // To check if the correct tile has been pressed, we check the tile id
            {
                timeMs -= 1;
                incrementPlayerScore(1, 1); // Adding 10 points if the player presses a correct tile
                this.getOnGameEventListener().onGameTimerEvent(timeMs); // Player gets 500 ms less to hit the tile in the next round
            }
            else // Incorrect tile block
            {
                timeMs += 1;
                incrementPlayerScore(-1,1); // Subtracting 10 points if the player presses a correct tile
                //connection.setAllTilesIdle(AntData.LED_COLOR_OFF);
                this.getOnGameEventListener().onGameTimerEvent(timeMs); // Player gets 1000 ms more to hit the tile in the next round
            }
        }
        else // No attempt block
        {
            incrementPlayerScore(0,1); // No change to the score
            this.getOnGameEventListener().onGameTimerEvent(timeMs); // No change to the timing
        }
        round += 1;
        //updateTiles();
    }

    // Some animation on the tiles once the game is over
    @Override
    public void onGameEnd()
    {
        super.onGameEnd();
        connection.setAllTilesBlink(4,LED_COLOR_RED);

    }

    public void updateTiles(){
        connection.setAllTilesIdle(LED_COLOR_OFF);
        Random rand = new Random();


        Collections.shuffle(colorList);

        connection.setTileColor( colorList.get(0) , 1);
        connection.setTileColor( colorList.get(1) , 2);
        connection.setTileColor( colorList.get(2) , 3);
        //connection.setTileColor( colorList.get(3) , 4);
        targetColor = colorList.get(rand.nextInt(3));

    }

    int getTargetColor(){
        return this.targetColor;
    }
}

