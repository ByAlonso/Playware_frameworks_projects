package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

public class GameActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection = MotoConnection.getInstance();
    GameName game_object = new GameName();
    LinearLayout gt_container;

    //Stop the game when we exit activity
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        game_object.stopGame();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        connection.registerListener(this);
        connection.setAllTilesToInit();
        gt_container = findViewById(R.id.game_type_container);

        for (final GameType gt : game_object.getGameTypes()) {
            Button b = new Button(this);
            b.setText(gt.getName());
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    game_object.selectedGameType = gt;
                    game_object.startGame();
                }
            });
            gt_container.addView(b);
        }
        game_object.setOnGameEventListener(new Game.OnGameEventListener() {

            final TextView scoreValue = findViewById(R.id.scoreValue);
            final TextView timeValue = findViewById(R.id.timeValue);

            @Override
            public void onGameTimerEvent(int i) {
                timeValue.setText("Time: " + String.valueOf(i));
            }

            @Override
            public void onGameScoreEvent(int i, int i1) {
                scoreValue.setText(String.valueOf(i));
            }

            @Override
            public void onGameStopEvent() {
                //this.scoreValue.setText("Stop");
            }

            @Override
            public void onSetupMessage(String s) {
            }

            @Override
            public void onGameMessage(String s) {

            }

            @Override
            public void onSetupEnd() {
            }

        });
    }
    @Override
    public void onMessageReceived(byte[] bytes, long l) {
        game_object.addEvent(bytes);
    }

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {

    }
}
