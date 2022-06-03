package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class GoodEnding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_ending);

        //timer to start text
        new CountDownTimer(3500, 1000) {
            public void onTick(long millisUntilFinished) {
                ending.setVisibility(View.INVISIBLE);
            }
            public void onFinish() {
                ending.setVisibility(View.VISIBLE);
            }
        }.start();


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(GoodEnding.this, Credits.class));
            }
        };
        Timer opening = new Timer();
        opening.schedule(task, 15000);
    }
}