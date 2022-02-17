package com.example.exercise3;

import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.ArrayList;
import java.util.Collections;

public class SpecialOne extends Game {
    MotoConnection connection = MotoConnection.getInstance();
    ArrayList<Integer> colorList = AntData.allColors();


    SpecialOne(){
        setName("Special One");

        GameType gt = new GameType(1, GameType.GAME_TYPE_TIME, 30, "1 player 30 sec",1);
        addGameType(gt);

        GameType gt2 = new GameType(2, GameType.GAME_TYPE_TIME, 60, "1 player 1 min",1);
        addGameType(gt2);

        GameType gt3 = new GameType(3, GameType.GAME_TYPE_TIME, 60*2, "1 player 2 min",1);
        addGameType(gt3);
    }

    @Override
    public void onGameStart()
    {
        super.onGameStart();
        clearPlayersScore();
        updateTiles();
    }

    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);

        int event = AntData.getCommand(message);
        int color = AntData.getColorFromPress(message);

        if (event == AntData.EVENT_PRESS && color==colorList.get(0)){

            incrementPlayerScore(1,0);
        }
        else {
            incrementPlayerScore(-2,0);
        }
        updateTiles();
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
        int randomTile = connection.randomIdleTile();

        Collections.shuffle(colorList);

        connection.setAllTilesColor(colorList.get(1));
        connection.setTileColor( colorList.get(0) , randomTile);
    }
}
