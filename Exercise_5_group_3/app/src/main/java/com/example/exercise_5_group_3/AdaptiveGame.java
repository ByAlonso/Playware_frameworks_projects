package com.example.exercise_5_group_3;

import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.ArrayList;
import java.util.Collections;

public class AdaptiveGame extends Game {
    MotoConnection connection = MotoConnection.getInstance();
    int tileSpeed = 60;

    AdaptiveGame(){
        setName("Final Countdown");

        GameType gt = new GameType(1, GameType.GAME_TYPE_SPEED, 60, "Slow",1);
        addGameType(gt);

        GameType gt2 = new GameType(2, GameType.GAME_TYPE_SPEED, 40, "Medium",1);
        addGameType(gt2);

        GameType gt3 = new GameType(3, GameType.GAME_TYPE_SPEED, 20, "Fast",1);
        addGameType(gt3);

        GameType gt4 = new GameType(4, GameType.GAME_TYPE_SPEED, 10, "Ultra-instinct",1);
        addGameType(gt4);
    }

    @Override
    public void onGameStart()
    {
        super.onGameStart();
        clearPlayersScore();
        //connection.setAllTilesIdle(LED_COLOR_OFF);
        connection.setAllTilesColor(LED_COLOR_BLUE);

        for(int i = 1; i <= 4; i++){
            connection.setTileColorCountdown(LED_COLOR_BLUE, i, this.tileSpeed);
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
            connection.setTileColorCountdown(LED_COLOR_BLUE, tile, this.tileSpeed);
        }
    }

    // Some animation on the tiles once the game is over
    @Override
    public void onGameEnd()
    {
        super.onGameEnd();
        connection.setAllTilesBlink(4,LED_COLOR_RED);

    }

    public void setTileSpeed(int speed){
        this.tileSpeed = speed;
    }
}

