package com.example.exercise_5_group_3;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_WHITE;


import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.ArrayList;
import java.util.Collections;

public class AdaptiveGame extends Game {
    MotoConnection connection = MotoConnection.getInstance();
    ArrayList<Integer> colorList = AntData.allColors();
    int correct_tile;
    int correct_presses;

    AdaptiveGame(){
        setName("Adaptive Game");

        GameType gt = new GameType(1, GameType.GAME_TYPE_SCORE, 60, "Start Game",1);
        addGameType(gt);
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
        int tile = AntData.getId(message);
        //int color = AntData.getColorFromPress(message);

        if (correct_presses == 7) {
            stopGame();
        }

        if (event == EVENT_PRESS) {
            if (tile == correct_tile) {
                incrementPlayerScore(10, 1);
                this.getOnGameEventListener().onGameTimerEvent(-500);
                correct_presses++;
            } else {
                incrementPlayerScore(-5, 1);
                this.getOnGameEventListener().onGameTimerEvent(1000);
            }
            updateTiles();
        } else {
            incrementPlayerScore(0, 1);
            this.getOnGameEventListener().onGameTimerEvent(0);
        }
    }

    // Some animation on the tiles once the game is over
    @Override
    public void onGameEnd()
    {
        super.onGameEnd();
        connection.setAllTilesBlink(4,LED_COLOR_RED);

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void updateTiles() {
        connection.setAllTilesIdle(LED_COLOR_OFF);

        Collections.shuffle(colorList);

        for(int tile = 1; tile < 5; tile++) {
            connection.setTileColor(colorList.get(tile), tile);
        }

        correct_tile = getRandomNumber(1, 4);

        connection.setTileColor(colorList.get(6), correct_tile);
    }
}

