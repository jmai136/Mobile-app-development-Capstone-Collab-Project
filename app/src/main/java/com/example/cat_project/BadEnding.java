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

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class BadEnding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_ending);
        final TextView ending = (TextView) findViewById(R.id.bEnding);
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

        btnCon.setOnClickListener(view -> startActivity(new Intent(BadEnding.this, Credits.class)));
    }
}