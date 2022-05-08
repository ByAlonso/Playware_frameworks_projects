package com.example.playware_final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.livelife.motolibrary.Game;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameActivity extends AppCompatActivity implements OnAntEventListener
{
    MotoConnection connection = MotoConnection.getInstance();

    PianoTiles game_object = new PianoTiles();
    LinearLayout gt_container;
    int points_scored = 0;
    //public static boolean ranked = false;

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
        LayoutInflater inflater = this.getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = 800;
        int height = 600;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        dialogBuilder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView popupText = popupView.findViewById(R.id.popup_text);
        final EditText editText = (EditText) popupView.findViewById(R.id.txtSub);
        Button submit_button = (Button) popupView.findViewById(R.id.submit_username);
        Button cancel_button = (Button) popupView.findViewById(R.id.cancel_username);

        popupText.setText("Congratulations! Join our scoreboard! Your score is: " +  String.valueOf(score));

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = editText.getText().toString();
                if(TextUtils.isEmpty(uname)){
                    Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendPost(uname, "Twinkle Twinkle Little Star",score);
                dialogBuilder.dismiss();
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setView(popupView);
        dialogBuilder.show();

    }
    public void sendPost(String username, String song, Integer score) {
        Api api = RetrofitClient.getInstance().getMyApi();
        UserModel user = new UserModel(username,song,score);
        Call<UserModel> call = api.createPost(user);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                // this method is called when we get response from our api.
                Toast.makeText(getApplicationContext(), "Username Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

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
        points_scored = 0;

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
                    playSound(game_object.sounds_order.get(i-1));
                    int final_score = points_scored - (game_object.errors * 2);
                    getNextLine(i);
                    onButtonShowPopupWindowClick(btn1a, final_score);
                    game_object.onGameEnd();
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
                    playSound(game_object.sounds_order.get(i-1));

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
            nextColors.add(game_object.color_order.get(i));
        }
        else if (i>=42 -2){
            nextColors.add(game_object.color_order.get(i));
            nextColors.add(game_object.color_order.get(i+1));
        }
        else if (i>=42 -3){
            nextColors.add(game_object.color_order.get(i));
            nextColors.add(game_object.color_order.get(i+1));
            nextColors.add(game_object.color_order.get(i+2));
        }

        // Rest of casesgame_object.
        else {
            nextColors.add(game_object.color_order.get(i));
            nextColors.add(game_object.color_order.get(i + 1));
            nextColors.add(game_object.color_order.get(i + 2));
            nextColors.add(game_object.color_order.get(i + 3));
        }

        // By default, we always clear all the colors
        btn1a.setAlpha((float) 0.4);
        btn1b.setAlpha((float) 0.4);
        btn1c.setAlpha((float) 0.4);
        btn1d.setAlpha((float) 0.4);
        btn2a.setAlpha((float) 0.4);
        btn2b.setAlpha((float) 0.4);
        btn2c.setAlpha((float) 0.4);
        btn2d.setAlpha((float) 0.4);
        btn3a.setAlpha((float) 0.4);
        btn3b.setAlpha((float) 0.4);
        btn3c.setAlpha((float) 0.4);
        btn3d.setAlpha((float) 0.4);
        btn4a.setAlpha((float) 0.4);
        btn4b.setAlpha((float) 0.4);
        btn4c.setAlpha((float) 0.4);
        btn4d.setAlpha((float) 0.4);


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
            else if(j==1) {
                if(nextColors.get(j) == 0)
                    setColor(btn1b);
                else if (nextColors.get(j) == 1)
                    setColor(btn2b);
                else if (nextColors.get(j) == 2)
                    setColor(btn3b);
                else
                    setColor(btn4b);
            }
            else if(j==2) {
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
        v.setAlpha((float) 1);
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
}