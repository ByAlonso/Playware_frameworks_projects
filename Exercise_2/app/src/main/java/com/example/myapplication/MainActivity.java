package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

public class MainActivity extends AppCompatActivity implements OnAntEventListener
{

    MotoConnection connection;
    Button pairingButton;
    Button startButton;
    TextView statusTextView;
    boolean isPairing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection=MotoConnection.getInstance();
        connection.startMotoConnection(MainActivity.this);
        connection.saveRfFrequency(36); // See the back of your tile for your group’s RF
        connection.setDeviceId(3); //Your group number
        connection.registerListener(MainActivity.this);

        statusTextView = findViewById(R.id.statusTextView);
        pairingButton = findViewById(R.id.pairingButton);
        pairingButton.setText("Pairing Button");
        pairingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPairing){
                    connection.pairTilesStart();
                    pairingButton.setText("Stop Pairing");
                } else {
                    connection.pairTilesStop();
                    pairingButton.setText("Start Pairing");
                }
                isPairing = !isPairing;
            }
        });
        startButton = findViewById(R.id.startGameButton);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                connection.unregisterListener(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMessageReceived(byte[] bytes, long l)
    {

    }

    @Override
    public void onAntServiceConnected()
    {
        connection.setAllTilesToInit();
    }

    @Override
    public void onNumbersOfTilesConnected(int i)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                statusTextView.setText(i +" connected tiles");
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        connection.stopMotoConnection();
        connection.unregisterListener(MainActivity.this);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        connection.registerListener(MainActivity.this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        connection.stopMotoConnection();
        connection.unregisterListener(MainActivity.this);
    }
}