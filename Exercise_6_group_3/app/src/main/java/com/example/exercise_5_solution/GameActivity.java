package com.example.exercise_5_solution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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
    ColourRace game_object = new ColourRace();
    LinearLayout gt_container;

    int delay = 4000; // Default time that the user has to press a tile

    int points_scored = 0; // Points scored by the user

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

        final TextView player_score = findViewById(R.id.score_value); // Where the player's score is displayed


        game_object.setOnGameEventListener(new Game.OnGameEventListener()
        {
            // Changing the time the user gets to hit the tile
            @Override
            public void onGameTimerEvent(int i)
            {
                if (i < 0)
                {
                    if (delay >= 1000)          //We need at least 1 sec to press the tile
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
        final Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                game_object.updateTiles();
            }
        };
        Thread my_thread = new Thread()
        {

            String end_message = "Game Over"; // This message is displayed at the end of the last round
            int round = 1; // Stores the current round number
            @Override
            public void run(){
                runOnUiThread(r);
            }

        };
        h.postDelayed(r,delay);
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