package com.example.exercise_5_group_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

public class GameActivity extends AppCompatActivity implements OnAntEventListener
{
    MotoConnection connection = MotoConnection.getInstance();
    //ColourRace game_object = new ColourRace();
    AdaptiveGame game_object = new AdaptiveGame(); // Game object
    LinearLayout gt_container;
    Button playButton = findViewById(R.id.playButton);


    //Stop the game when we exit activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        game_object.stopGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        connection.registerListener(this);
        connection.setAllTilesToInit();

        gt_container = findViewById(R.id.game_type_container);
        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
<<<<<<< Updated upstream
                game_object.selectedGameType = game_object.getGameTypes().get(0);
                game_object.startGame();
            }
        });
=======
                @Override
                public void onClick(View v)
                {
                    game_object.selectedGameType = gt;
                    game_object.startGame();
                }
            });
            gt_container.addView(b);
        }
>>>>>>> Stashed changes

        game_object.setOnGameEventListener(new Game.OnGameEventListener()
        {

            TextView scoreValue = findViewById(R.id.scoreValue);
            TextView timeValue = findViewById(R.id.timeValue);
            @Override

            public void onGameTimerEvent(int i)
            {
                timeValue.setText("Time: " + String.valueOf(i));
            }

            @Override
            public void onGameScoreEvent(int i, int i1)
            {
                scoreValue.setText(String.valueOf(i));
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
    public void onNumbersOfTilesConnected(final int i)
    {

    }
}
