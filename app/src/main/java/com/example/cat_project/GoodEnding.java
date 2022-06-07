package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class GoodEnding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_ending);

        // new Handler(getMainLooper()).postDelayed(() -> Snackbar.make(findViewById(R.id.RelativeLayout), "You have escaped and are free to start a new life. You cannot stop thinking about the nightmarish ordeal behind you, but at least you survived! You sniff the fresh air and wonder what adventures await you...", Snackbar.LENGTH_INDEFINITE).setAction("Credits", view -> startActivity(new Intent(GoodEnding.this, Credits.class))).show(),3500);

        /*final TextView ending = (TextView) findViewById(R.id.gEnding);
        Button btnCon = (Button) findViewById(R.id.btnCon);

        ending.setVisibility(View.INVISIBLE);
        btnCon.setVisibility(View.INVISIBLE);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ending.setVisibility(View.VISIBLE);
                btnCon.setVisibility(View.VISIBLE);
            }
        }, 3500);

        //listener button takes player to credits.java
        btnCon.setOnClickListener(view -> startActivity(new Intent(GoodEnding.this, Credits.class)));*/
    }
}