package com.example.finalpianotiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1a, btn1b, btn1c, btn1d, btn2a, btn2b, btn2c, btn2d, btn3a, btn3b, btn3c, btn3d, btn4a, btn4b, btn4c, btn4d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initBtn();

        // SET EXAMPLE COLORS
        setColor(btn1a);
        setColor(btn3b);
        setColor(btn4c);
        setColor(btn3d);
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
        getNext();
        Log.d("@@@", "click");
    }
}



