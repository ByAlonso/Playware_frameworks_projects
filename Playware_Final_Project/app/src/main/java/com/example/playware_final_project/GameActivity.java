package com.example.playware_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements OnAntEventListener
{
    MotoConnection connection = MotoConnection.getInstance();
    //MotoSound sound = MotoSound.getInstance();
    PianoTiles game_object = new PianoTiles();
    LinearLayout gt_container;

    int delay = 4000; // Default time that the user has to press a tile
    int trials = 7; // Number of times the user plays
    int points_scored = 0; // Points scored by the user
    String colour_names[] = new String[trials]; // To store the names of the randomly generated colours

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        connection.registerListener(this);
        connection.setAllTilesToInit();

        gt_container = findViewById(R.id.game_type_container);

        for (final GameType gt : game_object.getGameTypes())
        {
            Button b = new Button(this);
            b.setText(gt.getName());
            b.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    game_object.selectedGameType = gt;
                    //sound.playStart();
                    game_object.startGame();
                }
            });
            gt_container.addView(b);
        }

        final TextView display_colour = findViewById(R.id.colour_name); // The place where the colour is displayed
        final TextView game_round = findViewById(R.id.round_number); // Shows what round the user is on
        final TextView player_score = findViewById(R.id.score_value); // Where the player's score is displayed

        // Storing the randomly generated list of 7 colours for 7 rounds
        for (int i = 0; i < trials; i++)
        {
            colour_names[i] = game_object.random_string_generator();
        }

        game_object.setOnGameEventListener(new Game.OnGameEventListener()
        {
            // Changing the time the user gets to hit the tile
            @Override
            public void onGameTimerEvent(int i)
            {
                if (i < 0)
                {
                    if (delay >= 2000)
                    {
                        delay += i;
                    }
                }
                else
                {
                    delay += i;
                }
            }

            @Override
            public void onGameScoreEvent(final int i, int i1)
            {
                // Updating the score
                points_scored = i;
                GameActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        player_score.setText(String.valueOf(points_scored));
                    }
                });
            }

            @Override
            public void onGameStopEvent()
            {

            }

            @Override
            public void onSetupMessage(String s)
            {

            }

            @Override
            public void onGameMessage(String s)
            {

            }

            @Override
            public void onSetupEnd()
            {

            }
        });

        // Displaying each colour for a certain period of time (default - 3000 ms)
        Thread my_thread = new Thread()
        {
            int i = trials - 1; // Loop variable
            String end_message = "Game Over"; // This message is displayed at the end of the last round
            int round = 1; // Stores the current round number

            @Override
            public void run()
            {
                try
                {
                    while (i >= 0)
                    {
                        Thread.sleep(delay);
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (i >= 0)
                                {
                                    connection.setAllTilesIdle(AntData.LED_COLOR_OFF);
                                    display_colour.setText(colour_names[i]); // Updating and displaying a new colour
                                    game_object.set_random_tile_colour(colour_names[i]); // Updating the colour of the Moto tiles
                                    game_round.setText(toString().valueOf(round)); // Updating and displaying a new colour
                                    round++;
                                }
                                else
                                {
                                    Timer timer_object  = new Timer(); // Object of the Timer Class

                                    // Displaying the end game message
                                    timer_object.schedule(new TimerTask()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            finish();
                                        }
                                    }, 5000);

                                    display_colour.setText(end_message);
                                    game_object.onGameEnd();
                                }
                                i--;
                            }
                        });
                    }
                }
                catch (InterruptedException e)
                {

                }
            }
        };

        my_thread.start();
    }

    @Override
    public void onMessageReceived(byte[] bytes, long l)
    {
        game_object.addEvent(bytes);
    }

    @Override
    public void onAntServiceConnected()
    {

    }

    @Override
    public void onNumbersOfTilesConnected(int i)
    {

    }
}