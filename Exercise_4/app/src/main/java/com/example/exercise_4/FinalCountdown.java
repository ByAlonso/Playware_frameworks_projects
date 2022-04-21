package com.example.exercise_4;

import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.ArrayList;
import java.util.Collections;

public class FinalCountdown extends Game {
    MotoConnection connection = MotoConnection.getInstance();


    FinalCountdown(){
        setName("Final Countdown");

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
        connection.setAllTilesIdle(LED_COLOR_OFF);
        connection.setAllTilesColor(LED_COLOR_BLUE);

        for(int i = 0; i < 4; i++){
            connection.setTileColorCountdown(LED_COLOR_BLUE, i,60);
        }
    }

    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);

        int event = AntData.getCommand(message);
        int tile = AntData.getId(message);

        if (event == AntData.CMD_COUNTDOWN_TIMEUP) {
            stopGame();
        } else if (event == AntData.EVENT_PRESS){
            incrementPlayerScore(1, 0);
            connection.setTileColorCountdown(LED_COLOR_BLUE, tile, 60);
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
