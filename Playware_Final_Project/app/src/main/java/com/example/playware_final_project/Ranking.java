package com.example.playware_final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.net.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ranking extends AppCompatActivity {

    ListView superListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        superListView = findViewById(R.id.superListView);
        getUserList();
    }

    private void getUserList() {
        Call<List<UserModel>> call = RetrofitClient.getInstance().getMyApi().getUser();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> userList = response.body();
                String[] userName = new String[userList.size()];
                Integer[] score = new Integer[userList.size()];
                Integer[] index = new Integer[userList.size()];

                for (int i = 0; i < userList.size(); i++) {
                    userName[i] = userList.get(i).getName();
                    index[i] = i + 1;
                    score[i] = userList.get(i).getHighscore();
                }

                MyListView adapter = new MyListView(getApplicationContext(), userName, score, index);
                superListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }

}