package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    boolean isPairing = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection=MotoConnection.getInstance();
        connection.startMotoConnection(MainActivity.this);
        connection.saveRfFrequency(36); // See the back of your tile for your group’s RF
        connection.setDeviceId(1); //Your group number
        connection.registerListener(MainActivity.this);

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
        connection.startMotoConnection(MainActivity.this);
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