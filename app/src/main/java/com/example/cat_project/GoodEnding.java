package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GoodEnding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_ending);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(GoodEnding.this, Credits.class));
            }
        };
        Timer opening = new Timer();
        opening.schedule(task, 10000);
    }
}