package com.example.playware_final_project;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_INDIGO;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;

import android.util.Log;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;

import java.util.Random;

public class PianoTiles extends Game
{
    MotoConnection connection = MotoConnection.getInstance();
    int random_tile_id;

    PianoTiles()
    {
        setName("Game Name");

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
        connection.setAllTilesIdle(LED_COLOR_OFF);
    }

    @Override
    public void onGameUpdate(byte[] message)
    {
        super.onGameUpdate(message);

        super.onGameUpdate(message);
        int tileId = AntData.getId(message);
        int event = AntData.getCommand(message);
        //int colour = AntData.getColorFromPress(message);

        if (event == EVENT_PRESS)
        {
            // Correct tile block
            if (tileId == random_tile_id) // To check if the correct tile has been pressed, we check the tile id
            {
                Log.d("myTag", "This is my message");
                incrementPlayerScore(10, 1); // Adding 10 points if the player presses a correct tile
                this.getOnGameEventListener().onGameTimerEvent(-500); // Player gets 500 ms less to hit the tile in the next round
            }
            else // Incorrect tile block
            {
                Log.d("myTag", "xyzzyspoon");
                incrementPlayerScore(-5,1); // Subtracting 10 points if the player presses a correct tile
                //connection.setAllTilesIdle(AntData.LED_COLOR_OFF);
                this.getOnGameEventListener().onGameTimerEvent(1000); // Player gets 1000 ms more to hit the tile in the next round
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

    // Generates random strings. Each string represents a colour
    public String random_string_generator()
    {
        String random_string = "\0"; // To store the colour name

        Random random_object = new Random(); // Creating an object of the random class
        int bound = 6; // Upper bound for the number of characters we want to generate
        int random_number; // To store the random number
        random_number = (random_object.nextInt(bound) + 1); // Generating a random number between 1-7 (both inclusive)

        // Assigning the strings to the corresponding numbers
        if (random_number == 1)
        {
            random_string = "Red";
        }
        else if (random_number == 2)
        {
            random_string = "Blue";
        }
        else if (random_number == 3)
        {
            random_string = "Green";
        }
        else if (random_number == 4)
        {
            random_string = "Pink";
        }
        else if (random_number == 5)
        {
            random_string = "Yellow";
        }
        else if (random_number == 6)
        {
            random_string = "White";
        }
        return random_string;
    }

    // Sets the colours of the tiles
    public void set_random_tile_colour(String colour_name)
    {
        random_tile_id = connection.randomIdleTile();
        //Log.d("ADebugTag", "Value: " + Float.toString(random_tile_id));
        for(int t:connection.connectedTiles)
        {
            if(t==random_tile_id)
            {
                if (colour_name == "Red")
                {
                    connection.setTileColor(AntData.LED_COLOR_RED, random_tile_id);
                }
                else if (colour_name == "Blue")
                {
                    connection.setTileColor(AntData.LED_COLOR_BLUE, random_tile_id);
                }
                else if (colour_name == "Green")
                {
                    connection.setTileColor(AntData.LED_COLOR_GREEN, random_tile_id);
                }
                else if (colour_name == "Pink")
                {
                    connection.setTileColor(LED_COLOR_INDIGO, random_tile_id);
                }
                else if (colour_name == "Yellow")
                {
                    connection.setTileColor(AntData.LED_COLOR_ORANGE, random_tile_id);
                }
                else if (colour_name == "White")
                {
                    connection.setTileColor(AntData.LED_COLOR_WHITE, random_tile_id);
                }
            }
            else
            {
                if (colour_name == "Red")
                {
                    if (t % 2 == 0)
                    {
                        connection.setTileColor(AntData.LED_COLOR_WHITE, t);
                    }
                    else
                    {
                        connection.setTileColor(AntData.LED_COLOR_BLUE, t);
                    }
                }
                else if (colour_name == "Blue")
                {
                    if (t % 2 == 0)
                    {
                        connection.setTileColor(AntData.LED_COLOR_WHITE, t);
                    }
                    else
                    {
                        connection.setTileColor(AntData.LED_COLOR_RED, t);
                    }
                }
                else if (colour_name == "Green")
                {
                    if (t % 2 == 0)
                    {
                        connection.setTileColor(AntData.LED_COLOR_VIOLET, t);
                    }
                    else
                    {
                        connection.setTileColor(AntData.LED_COLOR_ORANGE, t);
                    }
                }
                else if (colour_name == "Pink")
                {
                    if (t % 2 == 0)
                    {
                        connection.setTileColor(AntData.LED_COLOR_WHITE, t);
                    }
                    else
                    {
                        connection.setTileColor(AntData.LED_COLOR_GREEN, t);
                    }
                }
                else if (colour_name == "Yellow")
                {
                    if (t % 2 == 0)
                    {
                        connection.setTileColor(AntData.LED_COLOR_BLUE, t);
                    }
                    else
                    {
                        connection.setTileColor(AntData.LED_COLOR_VIOLET, t);
                    }
                }
                else if (colour_name == "White")
                {
                    if (t % 2 == 0)
                    {
                        connection.setTileColor(AntData.LED_COLOR_RED, t);
                    }
                    else
                    {
                        connection.setTileColor(AntData.LED_COLOR_GREEN, t);
                    }
                }
            }
        }
    }
}
