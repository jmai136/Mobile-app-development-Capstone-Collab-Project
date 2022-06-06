package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class GoodEnding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_ending);
        // final TextView ending = (TextView) findViewById(R.id.gEnding);
        // Button btnCon = (Button) findViewById(R.id.btnCon);

        //timer to start text and btn
        new CountDownTimer(3500, 1000) {
            public void onTick(long millisUntilFinished) {
                // ending.setVisibility(View.INVISIBLE);
                // btnCon.setVisibility(View.INVISIBLE);
            }

            public void onFinish() {
                // ending.setVisibility(View.VISIBLE);
                // btnCon.setVisibility(View.VISIBLE);

                RelativeLayout relativeLayout = findViewById(R.id.RelativeLayout);
                Snackbar.make(relativeLayout, "You have escaped and are free to start a new life. You cannot stop thinking about the nightmarish ordeal behind you, but at least you survived! You sniff the fresh air and wonder what adventures await you...", Snackbar.LENGTH_INDEFINITE).setAction("Be locked inside the pound forever", view -> startActivity(new Intent(GoodEnding.this, Credits.class))).show();
            }
        }.start();

        //listener button takes player to credits.java
        // btnCon.setOnClickListener(v -> startActivity(new Intent(GoodEnding.this, Credits.class)));
    }
}