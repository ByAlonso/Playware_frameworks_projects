package com.example.myapplication;

<<<<<<< HEAD
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

=======
>>>>>>> 7778806ed028f302415c8126ab2c6bf291ad0b25
import androidx.appcompat.app.AppCompatActivity;

import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.GameType;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

<<<<<<< HEAD
public class GameActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection = MotoConnection.getInstance();
    LinearLayout gt_container;
    GameName game_object = new GameName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection.registerListener(this);
        connection.setAllTilesToInit();
        gt_container = findViewById(R.id.game_type_container);
        for (final GameType gt : game_object.getGameTypes()) {
            Button b = new Button(this);
            b.setText(gt.getName());
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
=======
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class GameActivity extends AppCompatActivity implements OnAntEventListener{

    MotoConnection connection = MotoConnection.getInstance();
    LinearLayout gt_container;
    GameName game_object = new GameName(); // Game object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
>>>>>>> 7778806ed028f302415c8126ab2c6bf291ad0b25
                    game_object.selectedGameType = gt;
                    game_object.startGame();
                }
            });
            gt_container.addView(b);
        }
<<<<<<< HEAD
        game_object.setOnGameEventListener(new Game.OnGameEventListener() {
            @Override
            public void onGameTimerEvent(int i) {
            }

            @Override
            public void onGameScoreEvent(int i, int i1) {
            }

            @Override
            public void onGameStopEvent() {
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

    }
=======
        game_object.setOnGameEventListener(new Game.OnGameEventListener()
        {
            @Override
            public void onGameTimerEvent(int i)
            {
            }
            @Override
            public void onGameScoreEvent(int i, int i1)
            {
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
                setContentView(R.layout.activity_game);
    }

>>>>>>> 7778806ed028f302415c8126ab2c6bf291ad0b25

    @Override
    public void onAntServiceConnected() {

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {

    }
<<<<<<< HEAD
        //Stop the game when we exit activity
        @Override
        public void onBackPressed()
        {
            super.onBackPressed();
            game_object.stopGame();
        }
}
=======


    @Override
    public void onMessageReceived(byte[] bytes, long l)
    {
        game_object.addEvent(bytes);
    }

    //Stop the game when we exit activity
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        game_object.stopGame();
    }

}
>>>>>>> 7778806ed028f302415c8126ab2c6bf291ad0b25
