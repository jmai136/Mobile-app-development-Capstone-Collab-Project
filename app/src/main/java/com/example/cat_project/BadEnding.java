package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

        RelativeLayout relativeLayout = findViewById(R.id.RelativeLayout);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(relativeLayout, "Not only were you humiliated by a bunch of mice, but the shadowy figure that killed your owner has defeated you as well. What will become of you now?", Snackbar.LENGTH_INDEFINITE).setAction("Credits", view -> startActivity(new Intent(BadEnding.this, Credits.class))).show();
            }
        }, 3500);
    }
}