package com.example.exercise2;

import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

import android.util.Log;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

public class ColourRace extends Game
{
    MotoConnection connection = MotoConnection.getInstance();

    ColourRace()
    {
        setName("Colour Race");

        GameType gt = new GameType(1, GameType.GAME_TYPE_TIME, 30, "1 player 30 sec",1);
        addGameType(gt);

        GameType gt2 = new GameType(2, GameType.GAME_TYPE_TIME, 60, "1 player 1 min",1);
        addGameType(gt2);

        GameType gt3 = new GameType(3, GameType.GAME_TYPE_TIME, 60*2, "1 player 2 min",1);
        addGameType(gt3);
    }

    // Some game setup stuff
    @Override
    public void onGameStart()
    {
        super.onGameStart();

        connection.setAllTilesIdle(LED_COLOR_OFF);

        int randomTile = connection.randomIdleTile();
        Log.i("TAG", "random id " + randomTile);
        connection.setTileColor(LED_COLOR_BLUE, randomTile);
    }

    // Put game logic here
    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);

        int event = AntData.getCommand(message);
        int color= AntData.getColorFromPress(message);
        if (event == AntData.EVENT_PRESS && color!=LED_COLOR_OFF)
        {
            incrementPlayerScore(1,0);

            int randomTile = connection.randomIdleTile();
            connection.setAllTilesIdle(LED_COLOR_OFF);
            connection.setTileColor(LED_COLOR_BLUE, randomTile);
        }
    }

    // Some animation on the tiles once the game is over
    @Override
    public void onGameEnd()
    {
        super.onGameEnd();

        connection.setAllTilesBlink(4,LED_COLOR_RED);
    }
}

