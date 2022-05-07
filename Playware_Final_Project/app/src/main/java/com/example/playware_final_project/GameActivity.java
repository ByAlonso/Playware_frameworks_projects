package com.example.playware_final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements OnAntEventListener
{
    MotoConnection connection = MotoConnection.getInstance();

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






    Button btn1a, btn1b, btn1c, btn1d, btn2a, btn2b, btn2c, btn2d, btn3a, btn3b, btn3c, btn3d, btn4a, btn4b, btn4c, btn4d;



    public void onButtonShowPopupWindowClick(View view, int score) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = 800;
        int height = 600;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView popupText = popupView.findViewById(R.id.popup_text);
        popupText.setText("GAME OVER! Your score is: \n\n" +  String.valueOf(score));



        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(GameActivity.this,
                        MainActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
                return true;
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        connection.registerListener(this);
        connection.setAllTilesToInit();

        gt_container = findViewById(R.id.game_type_container);

        // Generate the song
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

        // Initialize UI
        initBtn();


        game_object.selectedGameType = game_object.getGameTypes().get(0);

        game_object.startGame();

        final TextView player_score = findViewById(R.id.score_value); // Where the player's score is displayed
        final TextView player_error = findViewById(R.id.error_value); // Where the player's error is displayed


        // Initialize media for each sound
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


        // Initialize the beginning of the song
        getNextLine(0);

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

                if(points_scored > 41){

                    playSound(sounds_order.get(i-1));

                    int final_score = points_scored - (game_object.errors * 2);
                    getNextLine(i);


                    onButtonShowPopupWindowClick(btn1a, final_score);


                }

                else if (points_scored == -1) {
                    playSound(9);
                    GameActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            player_error.setText("Errors: " + String.valueOf(game_object.errors));
                        }
                    });

                }

                else {
                    playSound(sounds_order.get(i-1));

                    // advance in UI
                    getNextLine(i);
                    GameActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            player_score.setText("Tiles: " + String.valueOf(points_scored));
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


    // Play the selected note
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


    private void getNextLine(int i){
        ArrayList<Integer> nextColors = new ArrayList<>(4);

        // Edge cases: Last notes (make it nice)

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

        // Rest of cases
        else {
            nextColors.add(color_order.get(i));
            nextColors.add(color_order.get(i + 1));
            nextColors.add(color_order.get(i + 2));
            nextColors.add(color_order.get(i + 3));
        }

        // By default, we always clear all the colors
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


        // For each of the next notes, we color the button corresponding with the note
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
                v.setBackgroundColor(getResources().getColor(R.color.indigo_pink));
                break;
        }
    }

    // To initialize UI
    private void initBtn() {
        btn1a = (Button) findViewById(R.id.btn1a);
        btn1b = (Button) findViewById(R.id.btn1b);
        btn1c = (Button) findViewById(R.id.btn1c);
        btn1d = (Button) findViewById(R.id.btn1d);
        btn2a = (Button) findViewById(R.id.btn2a);
        btn2b = (Button) findViewById(R.id.btn2b);
        btn2c = (Button) findViewById(R.id.btn2c);
        btn2d = (Button) findViewById(R.id.btn2d);
        btn3a = (Button) findViewById(R.id.btn3a);
        btn3b = (Button) findViewById(R.id.btn3b);
        btn3c = (Button) findViewById(R.id.btn3c);
        btn3d = (Button) findViewById(R.id.btn3d);
        btn4a = (Button) findViewById(R.id.btn4a);
        btn4b = (Button) findViewById(R.id.btn4b);
        btn4c = (Button) findViewById(R.id.btn4c);
        btn4d = (Button) findViewById(R.id.btn4d);
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