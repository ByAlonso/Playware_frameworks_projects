package com.example.playware_final_project;

import static com.example.playware_final_project.Sounds.context;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
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

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements OnAntEventListener, View.OnClickListener
{
    MotoConnection connection = MotoConnection.getInstance();
    //MotoSound sound = MotoSound.getInstance();
    PianoTiles game_object = new PianoTiles();
    LinearLayout gt_container;
    int points_scored =0;
    ArrayList<Integer> sounds_order = new ArrayList(42);


    MediaPlayer a5_m;
    MediaPlayer c4_m;
    MediaPlayer c4_long_m;
    MediaPlayer d4_m;
    MediaPlayer d4_long_m;
    MediaPlayer e4_m ;
    MediaPlayer f4_m ;
    MediaPlayer g4_m ;
    MediaPlayer g4_long_m ;
    MediaPlayer error_m ;

    //ArrayList<MediaPlayer> mediaPlayers = new ArrayList<MediaPlayer>(10);




        //MediaPlayer.create(this, R.raw.c4), MediaPlayer.create(this, R.raw.c4_long), MediaPlayer.create(this, R.raw.d4), MediaPlayer.create(this, R.raw.d4_long), MediaPlayer.create(this, R.raw.e4), MediaPlayer.create(this, R.raw.f4), MediaPlayer.create(this, R.raw.g4), MediaPlayer.create(this, R.raw.g4_long), MediaPlayer.create(this, R.raw.error)];




    Button btn1a, btn1b, btn1c, btn1d, btn2a, btn2b, btn2c, btn2d, btn3a, btn3b, btn3c, btn3d, btn4a, btn4b, btn4c, btn4d;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        connection.registerListener(this);
        connection.setAllTilesToInit();

        gt_container = findViewById(R.id.game_type_container);

        // Generate the songs
        Twinkle();


        // UI
        initBtn();


        game_object.selectedGameType = game_object.getGameTypes().get(0);
        //sound.playStart();
        game_object.startGame();

//        for (final GameType gt : game_object.getGameTypes())
//        {
//            Button b = new Button(this);
//            b.setText(gt.getName());
//            b.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    game_object.selectedGameType = gt;
//                    //sound.playStart();
//                    game_object.startGame();
//                }
//            });
//            gt_container.addView(b);
//        }

//        final TextView display_colour = findViewById(R.id.colour_name); // The place where the colour is displayed
//        final TextView game_round = findViewById(R.id.round_number); // Shows what round the user is on
        final TextView player_score = findViewById(R.id.score_value); // Where the player's score is displayed


         a5_m = MediaPlayer.create(this, R.raw.a5);
         c4_m = MediaPlayer.create(this, R.raw.c4);
         c4_long_m = MediaPlayer.create(this, R.raw.c4_long);
         d4_m = MediaPlayer.create(this, R.raw.d4);
         d4_long_m = MediaPlayer.create(this, R.raw.d4_long);
         e4_m = MediaPlayer.create(this, R.raw.e4);
         f4_m = MediaPlayer.create(this, R.raw.f4);
         g4_m = MediaPlayer.create(this, R.raw.g4);
         g4_long_m = MediaPlayer.create(this, R.raw.g4_long);
         error_m = MediaPlayer.create(this, R.raw.error);

        game_object.setOnGameEventListener(new Game.OnGameEventListener()
        {
            // Changing the time the user gets to hit the tile
            @Override
            public void onGameTimerEvent(int i)
            {
            }

            @Override
            public void onGameScoreEvent(final int i, int i1)
            {
                // Updating the score
                points_scored = i;
                playSound(sounds_order.get(i));

                // advance in UI
                getNext();
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







    public  void playSound(int sound){
        switch(sound) {
            case 0:
                a5_m.start();
                break;
            case 1:
                c4_m.start();
                break;
            case 2:
                c4_long_m.start();
                break;
            case 3:
                d4_m.start();
                break;
            case 4:
                d4_long_m.start();
                break;
            case 5:
                e4_m.start();
                break;
            case 6:
                f4_m.start();
                break;
            case 7:
                g4_m.start();
                break;
            case 8:
                g4_long_m.start();
                break;
            case 9:
                error_m.start();
                break;
            default:
                break;
        }
    }

    private void initBtn() {
        btn1a = (Button) findViewById(R.id.btn1a);
        btn1a.setOnClickListener(this);
        btn1b = (Button) findViewById(R.id.btn1b);
        btn1b.setOnClickListener(this);
        btn1c = (Button) findViewById(R.id.btn1c);
        btn1c.setOnClickListener(this);
        btn1d = (Button) findViewById(R.id.btn1d);
        btn1d.setOnClickListener(this);
        btn2a = (Button) findViewById(R.id.btn2a);
        btn2a.setOnClickListener(this);
        btn2b = (Button) findViewById(R.id.btn2b);
        btn2b.setOnClickListener(this);
        btn2c = (Button) findViewById(R.id.btn2c);
        btn2c.setOnClickListener(this);
        btn2d = (Button) findViewById(R.id.btn2d);
        btn2d.setOnClickListener(this);
        btn3a = (Button) findViewById(R.id.btn3a);
        btn3a.setOnClickListener(this);
        btn3b = (Button) findViewById(R.id.btn3b);
        btn3b.setOnClickListener(this);
        btn3c = (Button) findViewById(R.id.btn3c);
        btn3c.setOnClickListener(this);
        btn3d = (Button) findViewById(R.id.btn3d);
        btn3d.setOnClickListener(this);
        btn4a = (Button) findViewById(R.id.btn4a);
        btn4a.setOnClickListener(this);
        btn4b = (Button) findViewById(R.id.btn4b);
        btn4b.setOnClickListener(this);
        btn4c = (Button) findViewById(R.id.btn4c);
        btn4c.setOnClickListener(this);
        btn4d = (Button) findViewById(R.id.btn4d);
        btn4d.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        check(view);
        Log.d("@@@", "click");
    }







    private void getNext(){

        btn1a.setBackground(btn1b.getBackground());
        btn1b.setBackground(btn1c.getBackground());
        btn1c.setBackground(btn1d.getBackground());
        // Missing getting new color from list
        btn1d.setBackground(btn1a.getBackground());

        btn2a.setBackground(btn2b.getBackground());
        btn2b.setBackground(btn2c.getBackground());
        btn2c.setBackground(btn2d.getBackground());
        // Missing getting new color from list
        btn2d.setBackground(btn2a.getBackground());

        btn3a.setBackground(btn3b.getBackground());
        btn3b.setBackground(btn3c.getBackground());
        btn3c.setBackground(btn3d.getBackground());
        // Missing getting new color from list
        btn3d.setBackground(btn3a.getBackground());

        btn4a.setBackground(btn4b.getBackground());
        btn4b.setBackground(btn4c.getBackground());
        btn4c.setBackground(btn4d.getBackground());
        // Missing getting new color from list
        btn4d.setBackground(btn4a.getBackground());

    }

    private void setColor(View v){
        switch (v.getId()) {
            case R.id.btn1a:
            case R.id.btn1b:
            case R.id.btn1c:
            case R.id.btn1d:
                v.setBackgroundColor(getResources().getColor(R.color.red));
                break;

            case R.id.btn2a:
            case R.id.btn2b:
            case R.id.btn2c:
            case R.id.btn2d:
                v.setBackgroundColor(getResources().getColor(R.color.blue));
                break;

            case R.id.btn3a:
            case R.id.btn3b:
            case R.id.btn3c:
            case R.id.btn3d:
                v.setBackgroundColor(getResources().getColor(R.color.green));
                break;

            case R.id.btn4a:
            case R.id.btn4b:
            case R.id.btn4c:
            case R.id.btn4d:
                v.setBackgroundColor(getResources().getColor(R.color.purple));
                break;


        }
    }

    private void check(View view){
        // Only bottom row should be check
        int buttonId = view.getId();
        if (buttonId == btn1a.getId() || buttonId == btn2a.getId() || buttonId == btn3a.getId() || buttonId == btn4a.getId()){
            // Check if it is correct
            Log.d("@@@", view.getBackground().getConstantState().toString());
            if ( view.getBackground().equals(getResources().getDrawable(R.color.white).getConstantState())){
                // ERROR CASE
                Log.d("@@@", view.getBackground().toString());
                Log.d("@@@", "error");
            }
            else {
                Log.d("@@@", "correct");
                getNext();
            }
        }
        else Log.d("@@@", "not valid");




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