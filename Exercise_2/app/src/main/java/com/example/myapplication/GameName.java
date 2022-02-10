package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;

public class GameName extends Game {
    public GameType selectedGameType;
    GameName(){
        setName("Colour Race");
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
        super.onGameStart();
    }
    // Put game logic here
    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);
    }
    // Some animation on the tiles to signal that the game is over
    @Override
    public void onGameEnd()
    {
        super.onGameEnd();
    }
}
