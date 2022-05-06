package com.example.playware_final_project;


import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_ORANGE;
import android.util.Log;


import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.ArrayList;

public class PianoTiles extends Game {
    MotoConnection connection = MotoConnection.getInstance();

    //Twinkle twinkle has 42 notes
    ArrayList<Integer> color_order = new ArrayList(42);

    int currentTile = 0;

    public int errors = 0;

    ArrayList<Integer> sounds_order = new ArrayList(42);


    PianoTiles() {
        setName("Piano Tiles");

        GameType gt = new GameType(1, GameType.GAME_TYPE_SCORE, 42, "twinkle twinkle little star", 1);
        addGameType(gt);

    }

    @Override
    public void onGameStart() {

        super.onGameStart();
        Log.d("@@@", "Starting game");

        // Load the song
        Twinkle();

        // Set up the tiles
        connection.setAllTilesIdle(LED_COLOR_OFF);

        // Define the color of the tiles to match the UI
        connection.setTileColor(1, connection.connectedTiles.get(0));
        connection.setTileColor(2, connection.connectedTiles.get(1));
        connection.setTileColor(3, connection.connectedTiles.get(2));
        connection.setTileColor(4, connection.connectedTiles.get(3));


        // Map the song notes to colors
        for (int i = 0; i < 42; i++) {
            if (sounds_order.get(i) == 0 || sounds_order.get(i) == 6 )
                color_order.add(0);
            else if (sounds_order.get(i) == 1 || sounds_order.get(i) == 2)
                color_order.add(1);
            if (sounds_order.get(i) == 3 || sounds_order.get(i) == 4)
                color_order.add(2);
            if (sounds_order.get(i) == 5 || sounds_order.get(i) == 7 || sounds_order.get(i) == 8)
                color_order.add(3);
        }
    }



    @Override
    public void onGameUpdate(byte[] message) {

        super.onGameUpdate(message);
        super.onGameUpdate(message);

        int tileId = AntData.getId(message);
        int event = AntData.getCommand(message);


        if (event == EVENT_PRESS) {
            // End of game
            if(currentTile > 41)
                onGameEnd();


            else { // Correct tile
            int tileNeeded = color_order.get(currentTile);
            if (tileId == tileNeeded + 1) // To check if the correct tile has been pressed, we check the tile id
            {
                Log.d("@@@", "Correct");
                currentTile++;
                incrementPlayerScore(1, 1);
            } else // Incorrect tile
            {
                errors++;
                // This actually doesn't affect the score, it is used only to be able to play the error sound
                super.getOnGameEventListener().onGameScoreEvent(-1,1);
                Log.d("@@@", "Incorrect");
            }}
        } else // No attempt block
        {
            Log.d("@@@", "no attempt");

        }
    }

    @Override
    public void onGameEnd() {
        super.onGameEnd();
        connection.setAllTilesBlink(5, LED_COLOR_ORANGE);
    }


    private void Twinkle(){
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

    }

}







