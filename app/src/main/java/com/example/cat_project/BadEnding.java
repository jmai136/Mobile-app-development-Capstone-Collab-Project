package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class BadEnding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_ending);
        final TextView ending = (TextView) findViewById(R.id.bEnding);
        Button btnCon = (Button) findViewById(R.id.btnCon);

        //timer to start text and btn
        new CountDownTimer(3500, 1000) {
            public void onTick(long millisUntilFinished) {
                ending.setVisibility(View.INVISIBLE);
                btnCon.setVisibility(View.INVISIBLE);
            }

            public void onFinish() {
                ending.setVisibility(View.VISIBLE);
                btnCon.setVisibility(View.VISIBLE);
            }
        }.start();

        //listener button takes player to credits.java
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BadEnding.this, Credits.class));
            }
        });
    }
}