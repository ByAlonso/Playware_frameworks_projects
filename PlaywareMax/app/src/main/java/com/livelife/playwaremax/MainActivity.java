package com.livelife.playwaremax;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.livelife.motolibrary.AntData;
import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.MotoSound;
import com.livelife.motolibrary.OnAntEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import static com.livelife.motolibrary.AntData.EVENT_PRESS;
import static com.livelife.motolibrary.AntData.LED_COLOR_OFF;
import static com.livelife.motolibrary.AntData.LED_COLOR_RED;

public class MainActivity extends AppCompatActivity implements OnAntEventListener {

    MotoConnection connection;
    MotoSound sound;

    Button pairingButton;
    Button startGameButton;
    Button simulateGetGameSessions,simulatePostGameSession, getGameChallenge, postGameChallenge;
    TextView connectedTextView;
    TextView scoreText;

    TextView challengeStatusText;



    Boolean isPairing = false;
    Boolean isPlaying = false;

    ListView challengeList;
    ArrayAdapter<String> adapter;
    ArrayList<String> serializedItems = new ArrayList<>(Arrays.asList("demo challenge","demo challenge 2"));

    TextView apiOutput;
    String endpoint = "https://centerforplayware.com/api/index.php";

    int selectedTile;
    SharedPreferences sharedPref ;

    ArrayList<String> statusText = new ArrayList<>(Arrays.asList("created (waiting to be accepted)" , "accepted" ,"waiting for the challenged" , "waiting for the challenge" , "challenge completed"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = this.getApplicationContext().getSharedPreferences("PLAYWARE_COURSE", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = MotoConnection.getInstance();
        sound = MotoSound.getInstance();

        connection.startMotoConnection(this);
        connection.saveRfFrequency(36);
        connection.setDeviceId(3);
        connection.registerListener(this);


        apiOutput = findViewById(R.id.apiOutput);
        connectedTextView = findViewById(R.id.connectedTextView);

        scoreText = findViewById(R.id.scoreText);
        challengeStatusText = findViewById(R.id.challengeStatusText);

        pairingButton = findViewById(R.id.pairingButton);
        pairingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPlaying) {
                    return;
                }

                Log.i("ButtonStuff","You clicked the button!");
                if(isPairing) {
                    connection.pairTilesStop();
                    pairingButton.setText("START PAIRING");
                } else {
                    connection.pairTilesStart();
                    pairingButton.setText("STOP PAIRING");
                }
                isPairing = !isPairing;
            }
        });

        startGameButton = findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPlaying) {
                    startGameButton.setText("STOP GAME");
                    isPlaying = true;
                    connection.setAllTilesIdle(LED_COLOR_OFF);
                    selectedTile = connection.randomIdleTile();
                    connection.setTileColor(LED_COLOR_RED,selectedTile);
                } else {
                    startGameButton.setText("START GAME");
                    isPlaying = false;
                    connection.setAllTilesToInit();
                }
            }
        });

        simulateGetGameSessions = findViewById(R.id.simulateGetGameSessions);
        simulateGetGameSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGameSessions();
            }
        });

        simulatePostGameSession = findViewById(R.id.simulatePostGameSession);
        simulatePostGameSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                postGameSession();


            }
        });

        getGameChallenge = findViewById(R.id.getGameChallenge);
        getGameChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGameChallenge();
            }
        });

        postGameChallenge = findViewById(R.id.postGameChallenge);
        postGameChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                postGameChallenge();


            }
        });

        challengeList = findViewById(R.id.challengeList);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, serializedItems);
        challengeList.setAdapter(adapter);

        challengeList.setOnItemClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Accept this challenge?")
                    .setPositiveButton("Accept", (dialog, which) -> dialog.cancel())
//                    {
//                        if(isStatusCreated(items.get(position)) &&
//                                challengeManager.postGameChallengeAccept(getDeviceToken(),
//                                        String.valueOf(items.get(position).getGcid()),"testName")){
//                            new AlertDialog.Builder(view.getContext())
//                                    .setTitle("Accepted Challenge!")
//                                    // A null listener allows the button to dismiss the dialog and take no further action.
//                                    .setNegativeButton(android.R.string.no, null)
//                                    .setIcon(android.R.drawable.ic_dialog_alert)
//                                    .show();
//                        }else{
//                            new AlertDialog.Builder(view.getContext())
//                                    .setTitle("Failed to accept challenge! Has it already been accepted?")
//                                    // A null listener allows the button to dismiss the dialog and take no further action.
//                                    .setNegativeButton(android.R.string.no, null)
//                                    .setIcon(android.R.drawable.ic_dialog_alert)
//                                    .show();
//
//                        }
//                        dialog.cancel();

                    .setNegativeButton("Decline", (dialog, which) ->  dialog.cancel())
                    .show();
        });

    }


    private String getDeviceToken() {
        // Get unique device_token from shared preferences
        // Remember that what is saved in sharedPref exists until you delete the app!
        String device_token = sharedPref.getString("device_token",null);

        if(device_token == null) { // If device_token was never saved and null create one
            device_token =  UUID.randomUUID().toString(); // Get a new device_token
            sharedPref.edit().putString("device_token",device_token).apply(); // save it to shared preferences so next time will be used
        }

        return device_token;
    }


    private void postGameSession() {
        RemoteHttpRequest requestPackage = new RemoteHttpRequest();
        requestPackage.setMethod("POST");
        requestPackage.setUrl(endpoint);
        requestPackage.setParam("method","postGameSession"); // The method name
        requestPackage.setParam("group_id","3"); // Your group ID
        requestPackage.setParam("game_id","1"); // The game ID (From the Game class > setGameId() function
        requestPackage.setParam("game_type_id","1"); // The game type ID (From the GameType class creation > first parameter)
        requestPackage.setParam("game_score","30"); // The game score
        requestPackage.setParam("game_time","60"); // The game elapsed time in seconds
        requestPackage.setParam("num_tiles","4");
        requestPackage.setParam("device_token",getDeviceToken()); // Your device token

        Downloader downloader = new Downloader(); //Instantiation of the Async task
        //that’s defined below

        downloader.execute(requestPackage);
    }
    private void getGameSessions() {
        RemoteHttpRequest requestPackage = new RemoteHttpRequest();
        requestPackage.setMethod("GET");
        requestPackage.setUrl(endpoint);
        requestPackage.setParam("method","getGameSessions");
        requestPackage.setParam("group_id","3");

        Downloader downloader = new Downloader(); //Instantiation of the Async task
        //that’s defined below

        downloader.execute(requestPackage);
    }



    private void postGameChallenge() {


        RemoteHttpRequest requestPackage = new RemoteHttpRequest();
        requestPackage.setMethod("POST");
        requestPackage.setUrl(endpoint);
        requestPackage.setParam("method","postGameChallenge"); // The method name
        requestPackage.setParam("device_token",getDeviceToken()); // Your device token
        requestPackage.setParam("game_id","1"); // The game ID (From the Game class > setGameId() function
        requestPackage.setParam("game_type_id","1"); // The game type ID (From the GameType class creation > first parameter)
        requestPackage.setParam("challenger_name","Max"); // The challenger name


        Downloader downloader = new Downloader(); //Instantiation of the Async task
        //that’s defined below

        downloader.execute(requestPackage);
    }

    private void getGameChallenge() {
        RemoteHttpRequest requestPackage = new RemoteHttpRequest();
        requestPackage.setMethod("GET");
        requestPackage.setUrl(endpoint);
        requestPackage.setParam("method","getGameChallenge"); // The method name
        requestPackage.setParam("device_token",getDeviceToken()); // Your device token

        Downloader downloader = new Downloader(); //Instantiation of the Async task
        //that’s defined below

        downloader.execute(requestPackage);
    }


    private void postGameChallengeAccept() {
        RemoteHttpRequest requestPackage = new RemoteHttpRequest();
        requestPackage.setMethod("POST");
        requestPackage.setUrl(endpoint);
        requestPackage.setParam("method","postGameChallengeAccept"); // The method name
        requestPackage.setParam("device_token",getDeviceToken()); // Your device token
        requestPackage.setParam("challenged_name","1"); // The name of the person accepting the challenge
        requestPackage.setParam("gcid","1"); // The game challenge id you want to accept


        Downloader downloader = new Downloader(); //Instantiation of the Async task
        //that’s defined below

        downloader.execute(requestPackage);
    }

    private class Downloader extends AsyncTask<RemoteHttpRequest, String, String> {
        @Override
        protected String doInBackground(RemoteHttpRequest... params) {
            return HttpManager.getData(params[0]);
        }

        //The String that is returned in the doInBackground() method is sent to the
        // onPostExecute() method below. The String should contain JSON data.
        @Override
        protected void onPostExecute(String result) {
            try {
                //We need to convert the string in result to a JSONObject
                JSONObject jsonObject = new JSONObject(result);

                String message = jsonObject.getString("message");
                Log.i("sessions",message);

                // Log the entire response if needed to check the data structure
                Log.i("sessions",jsonObject.toString());

                // Log response
                Log.i("sessions","response: "+jsonObject.getBoolean("response"));
                // Update UI
                apiOutput.setText(message);



                if(jsonObject.getString("method") == "getGameSessions") {

                    JSONArray sessions = jsonObject.getJSONArray("results");
                    for(int i = 0; i < sessions.length();i++) {
                        JSONObject session = sessions.getJSONObject(i);
                        Log.i("sessions",session.toString());

                        // get score example:
                        // String score = session.getString("game_score");

                    }

                }
                else if(jsonObject.getString("method") == "postGameSession") {

                    Log.i("sessions",message);

                    // Update UI


                }
                else if(jsonObject.getString("method") == "postGameChallenge") {

                    Log.i("challenge",message);

                    // Update UI


                }
                else if(jsonObject.getString("method") == "getGameChallenge") {
                    int status =0;
                    JSONArray challenges = jsonObject.getJSONArray("results");
                    for(int i = 0; i < challenges.length();i++) {
                        JSONObject challenge = challenges.getJSONObject(i);
                        Log.i("challenge",challenge.toString());
                        status  = challenge.getInt("c_status");
                        if(status == 4) {
                            Log.i("challenge",challenge.getJSONArray("summary").toString());
                        }
                    }
                    challengeStatusText.setText(statusText.get(status));

                    // Update UI
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessageReceived(byte[] bytes, long l) {

        int command = AntData.getCommand(bytes);
        int tileId = AntData.getId(bytes);
        int color = AntData.getColorFromPress(bytes);

        if(command == EVENT_PRESS) {
            if(tileId == selectedTile) {
                sound.playMatched();
                int randTile = connection.randomIdleTile();
                connection.setAllTilesIdle(LED_COLOR_OFF);
                connection.setTileColor(LED_COLOR_RED,randTile);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update UI
                    }
                });

            }
        }

    }

    @Override
    public void onAntServiceConnected() {
        connection.setAllTilesToInit();

    }

    @Override
    public void onNumbersOfTilesConnected(int i) {
        connectedTextView.setText("Tiles connected: "+i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        connection.stopMotoConnection();
        connection.unregisterListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connection.startMotoConnection(this);
        connection.registerListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.stopMotoConnection();
        connection.unregisterListener(this);
    }
}
