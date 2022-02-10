package com.example.myapplication;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_BLUE;
import static com.livelife.motolibrary.AntData.LED_COLOR_GREEN;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_ORANGE;
import static com.livelife.motolibrary.AntData.getCommand;

import android.service.quicksettings.Tile;

import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

public class GameName extends Game {
    MotoConnection connection;
    String name;


    public GameName() {
        setName("Color Race");
        GameType gt = new GameType(1, GameType.GAME_TYPE_TIME, 30, "1 player 30 sec",1);
        addGameType(gt);
        GameType gt2 = new GameType(2, GameType.GAME_TYPE_TIME, 60, "1 player 1 min",1);
        addGameType(gt2);
        GameType gt3 = new GameType(3, GameType.GAME_TYPE_TIME, 60*2, "1 player 2 min",1);
        addGameType(gt3);
    }

    // Game setup code
    @Override
    public void onGameStart()
    {
       // set all tiles to an idle mode
        connection.setAllTilesIdle(LED_COLOR_BLUE);
        //turn off the LED's
        connection.setAllTilesColor(LED_COLOR_OFF);
        //generate random tile
        int tileId = connection.randomIdleTile();

        connection.setTileColor(LED_COLOR_GREEN, tileId);

        super.onGameStart();


    }

    // Put game logic here
    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);
        int command = getCommand(message);

        if (command == EVENT_PRESS){
            // create a new random tile
            int tileId = connection.randomIdleTile();
            connection.setTileColor(LED_COLOR_GREEN, tileId);
        };

    }
    // Some animation on the tiles to signal that the game is over
    @Override
    public void onGameEnd()
    {
        connection.setAllTilesBlink(5, LED_COLOR_ORANGE);
        super.onGameEnd();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public GameType selectedGameType;



    public void startGame() {
        
    }

    public void setOnGameEventListener(Game.OnGameEventListener onGameEventListener) {
    }

    public void addEvent(byte[] bytes) {
    }

    public void stopGame() {
    }
}
