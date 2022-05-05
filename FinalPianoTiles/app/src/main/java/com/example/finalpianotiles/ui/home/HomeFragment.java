package com.example.finalpianotiles.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalpianotiles.GameActivity;
import com.example.finalpianotiles.MainActivity;
import com.example.finalpianotiles.R;
import com.example.finalpianotiles.databinding.FragmentHomeBinding;



import com.livelife.motolibrary.MotoConnection;
import com.livelife.motolibrary.OnAntEventListener;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment implements View.OnClickListener, OnAntEventListener {

    private FragmentHomeBinding binding;
    Button startGame, pairingButton;
    TextView statusTextView;
    boolean is_pairing = false;
    public static MediaPlayer mediaPlayer;
    MotoConnection connection;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;

        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize your view here for use view.findViewById("your view id")
        Button startGame = (Button) getView().findViewById(R.id.startGame);
        startGame.setOnClickListener(this);


        Button pairingButton =  (Button) getView().findViewById(R.id.pairingButton);
        TextView statusTextView = (TextView) getView().findViewById(R.id.statusTextView);

        connection=MotoConnection.getInstance();
        connection.startMotoConnection(getActivity());
        connection.saveRfFrequency(36);         // Check the back of your tiles for the RF
        connection.setDeviceId(3);              // Your group number
        connection.registerListener(getActivity().this);
        // To display the number of tiles connected

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        connection.stopMotoConnection();
        connection.unregisterListener(getActivity().this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startGame:
                Intent i = new Intent(getActivity(), GameActivity.class);
                startActivity(i);
                break;
            case R.id.pairingButton:
                if(!is_pairing)
                {
                    connection.pairTilesStart();
                    pairingButton.setText("Stop Paring");
                }
                else
                {
                    connection.pairTilesStop();
                    pairingButton.setText("Start Paring");
                }
                is_pairing = !is_pairing;
                break;

        }
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
    public void onNumbersOfTilesConnected(final int i)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                statusTextView.setText(i + " connected tiles");

            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        connection.registerListener(getActivity().this);
    }

}





