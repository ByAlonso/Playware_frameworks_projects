package com.example.playware_final_project;

import static com.example.playware_final_project.Sounds.context;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
    ArrayList<Integer> color_order = new ArrayList(42);



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


        // Initialise the beginning of the song
        getNextLine(0);




//        for(int i=0; i < 4; i++) {
//            int color = color_order.get(i);
//            if (color == 1) {
//                if (i ==0)
//                    setColor(btn1a);
//                else if (i == 1)
//                setColor(btn1b);
//                else if (i == 2)
//                setColor(btn1c);
//                else
//                setColor(btn1d);
//            }
//
//           else  if (color == 2) {
//                if (i == 0)
//                    setColor(btn2a);
//               else if (i == 1)
//                    setColor(btn2b);
//                else if (i == 2)
//                    setColor(btn2c);
//                else
//                    setColor(btn2d);
//            }
//            else if(color == 3) {
//                if (i == 0)
//                    setColor(btn3a);
//               else if (i == 1)
//                    setColor(btn3b);
//               else if (i == 2)
//                    setColor(btn3c);
//               else
//                    setColor(btn3d);
//            }
//           else if(color == 4) {
//                if (i == 0)
//                    setColor(btn4a);
//                else if (i == 1)
//                    setColor(btn4b);
//                else if (i == 2)
//                    setColor(btn4c);
//                else
//                    setColor(btn4d);
//            }
//        }

        game_object.setOnGameEventListener(new Game.OnGameEventListener()
        {
            // Changing the time the user gets to hit the tile
            @Override
            public void onGameTimerEvent(int i)
            {
            }

            @Override
            public void onGameScoreEvent(final int i, int i1) {
                // Updating the score
                points_scored = i;
                if (points_scored == -1) {
                    playSound(9);
                } else {
                    playSound(sounds_order.get(i-1));

                    // advance in UI
                    getNextLine(i);
                    GameActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            player_score.setText(String.valueOf(points_scored));
                        }
                    });
                }
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


    private void getNextLine(int i){
        ArrayList<Integer> nextColors = new ArrayList<>(4);

        // Edge case: Last notes (make it nice)

        if (i>=42){
            // Everything goes blank
        }

        else if (i>=42 -1){
            nextColors.add(color_order.get(i));
        }

        else if (i>=42 -2){
            nextColors.add(color_order.get(i));
            nextColors.add(color_order.get(i+1));
        }
        else if (i>=42 -3){
            nextColors.add(color_order.get(i));
            nextColors.add(color_order.get(i+1));
            nextColors.add(color_order.get(i+2));
        }

        // Rest of notes
        else {
            nextColors.add(color_order.get(i));
            nextColors.add(color_order.get(i + 1));
            nextColors.add(color_order.get(i + 2));
            nextColors.add(color_order.get(i + 3));
        }

        // By default, we clear all the colors

        btn1a.setBackgroundColor(getResources().getColor(R.color.white));
        btn1b.setBackgroundColor(getResources().getColor(R.color.white));
        btn1c.setBackgroundColor(getResources().getColor(R.color.white));
        btn1d.setBackgroundColor(getResources().getColor(R.color.white));
        btn2a.setBackgroundColor(getResources().getColor(R.color.white));
        btn2b.setBackgroundColor(getResources().getColor(R.color.white));
        btn2c.setBackgroundColor(getResources().getColor(R.color.white));
        btn2d.setBackgroundColor(getResources().getColor(R.color.white));
        btn3a.setBackgroundColor(getResources().getColor(R.color.white));
        btn3b.setBackgroundColor(getResources().getColor(R.color.white));
        btn3c.setBackgroundColor(getResources().getColor(R.color.white));
        btn3d.setBackgroundColor(getResources().getColor(R.color.white));
        btn4a.setBackgroundColor(getResources().getColor(R.color.white));
        btn4b.setBackgroundColor(getResources().getColor(R.color.white));
        btn4c.setBackgroundColor(getResources().getColor(R.color.white));
        btn4d.setBackgroundColor(getResources().getColor(R.color.white));


        // For each of the next four notes, we color the  button corresponding with the note
        for(int j=0; j< nextColors.size(); j++){
            if(j==0){
                if(nextColors.get(j) == 0)
                    setColor(btn1a);
                else if (nextColors.get(j) == 1)
                    setColor(btn2a);
                else if (nextColors.get(j) == 2)
                    setColor(btn3a);
                else
                    setColor(btn4a);

            }
            else  if(j==1) {
                if(nextColors.get(j) == 0)
                    setColor(btn1b);
                else if (nextColors.get(j) == 1)
                    setColor(btn2b);
                else if (nextColors.get(j) == 2)
                    setColor(btn3b);
                else
                    setColor(btn4b);
            }
            else  if(j==2) {
                if(nextColors.get(j) == 0)
                    setColor(btn1c);
                else if (nextColors.get(j) == 1)
                    setColor(btn2c);
                else if (nextColors.get(j) == 2)
                    setColor(btn3c);
                else
                    setColor(btn4c);
            }
            else {
                if(nextColors.get(j) == 0)
                    setColor(btn1d);
                else if (nextColors.get(j) == 1)
                    setColor(btn2d);
                else if (nextColors.get(j) == 2)
                    setColor(btn3d);
                else
                    setColor(btn4d);
            }

        }

    }


    private void getNext(int i){

        int next_color = color_order.get(i);

        if (i > 1) {
            if (next_color == 1)
                setColor(btn1d);

            else if (next_color == 2)
                setColor(btn2d);
           else if (next_color == 3)
                setColor(btn3d);
            else if (next_color == 4)
                setColor(btn4d);
        }


        btn1a.setBackgroundColor(((ColorDrawable) btn1b.getBackground()).getColor());
        btn1b.setBackgroundColor(((ColorDrawable) btn1c.getBackground()).getColor());
        btn1c.setBackgroundColor(((ColorDrawable) btn1d.getBackground()).getColor());

//        btn1b.setBackground(btn1c.getBackground());
//        btn1c.setBackground(btn1d.getBackground());
        // Missing getting new color from list
        //btn1d.setBackground(btn1a.getBackground());

        btn2a.setBackground(btn2b.getBackground());
        btn2b.setBackground(btn2c.getBackground());
        btn2c.setBackground(btn2d.getBackground());
        // Missing getting new color from list
        //btn2d.setBackground(btn2a.getBackground());

        btn3a.setBackground(btn3b.getBackground());
        btn3b.setBackground(btn3c.getBackground());
        btn3c.setBackground(btn3d.getBackground());
        // Missing getting new color from list
        //btn3d.setBackground(btn3a.getBackground());

        btn4a.setBackground(btn4b.getBackground());
        btn4b.setBackground(btn4c.getBackground());
        btn4c.setBackground(btn4d.getBackground());
        // Missing getting new color from list
        //btn4d.setBackground(btn4a.getBackground());

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

            }
        }
        else Log.d("@@@", "not valid");




    }


    private void Twinkle(){
        //Adding the order of the sounds to the array
       // sounds_order.add(0);
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