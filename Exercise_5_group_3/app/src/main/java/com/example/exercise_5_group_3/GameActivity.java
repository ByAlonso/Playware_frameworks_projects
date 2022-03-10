package com.example.exercise_5_group_3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity implements OnAntEventListener
{
    MotoConnection connection = MotoConnection.getInstance();
    AdaptiveGame game_object = new AdaptiveGame(); // Game object
    LinearLayout gt_container;
    Button playButton;
    LinearLayout colorBox;
    Map<Integer, Integer> colorDict = new HashMap<Integer, Integer>();
    int delay = 4000;



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
        playButton = findViewById(R.id.playButton);
        colorBox = findViewById(R.id.target_color_box);

        this.colorDict.put(1,android.R.color.holo_red_dark);
        this.colorDict.put(2,android.R.color.holo_blue_dark);
        this.colorDict.put(3,android.R.color.holo_green_dark);
        this.colorDict.put(4, android.R.color.holo_red_light);
        this.colorDict.put(5,android.R.color.holo_orange_dark);
        this.colorDict.put(6,android.R.color.white);
        this.colorDict.put(7,android.R.color.holo_purple);


        connection.registerListener(this);
        connection.setAllTilesToInit();



        gt_container = findViewById(R.id.game_type_container);
        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                game_object.selectedGameType = game_object.getGameTypes().get(0);
                game_object.startGame();
                colorBox.setBackgroundColor(getResources().getColor(colorDict.get(game_object.getTargetColor())));
                Log.i("TAG0_COL",Integer.toString(game_object.getTargetColor()));

            }
        });

        game_object.setOnGameEventListener(new Game.OnGameEventListener()
        {

            TextView scoreValue = findViewById(R.id.scoreValue);
            TextView timeValue = findViewById(R.id.timeValue);

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
            public void onGameScoreEvent(int i, int i1)
            {
                game_object.updateTiles();
                colorBox.setBackgroundColor(getResources().getColor(colorDict.get(game_object.getTargetColor())));
                Log.i("TAG0_COL",Integer.toString(game_object.getTargetColor()));
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
