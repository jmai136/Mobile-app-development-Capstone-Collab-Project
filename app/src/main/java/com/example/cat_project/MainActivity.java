package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //global variables for music
    MediaPlayer mpMusic;

    private final TextView
            kitchen1 = (TextView) findViewById(R.id.txtKitchen1),
            kitchen2 = (TextView) findViewById(R.id.txtKitchen2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.imgKitchenPixel);

        Button btnPantry = (Button) findViewById(R.id.btnPantry);

        RelativeLayout relativeLayout = findViewById(R.id.RelativeLayout);

        //music
        mpMusic = new MediaPlayer();
        mpMusic = MediaPlayer.create(this, R.raw.music);
        mpMusic.setLooping(true);
        mpMusic.start();

        // if possible, replace with handler later
        kitchen1.setVisibility(View.VISIBLE);
        kitchen2.setVisibility(View.INVISIBLE);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                kitchen1.setVisibility(View.INVISIBLE);
                kitchen2.setVisibility(View.INVISIBLE);
            }
        }, 3500);

        //listener pantry button toast shows img and txt
        btnPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView i = new ImageView(getApplicationContext());
                i.setImageResource(R.drawable.pantrydoorpixel);

                Toast toast = new Toast(getApplicationContext());
                toast.setView(i);
                toast.show();
                Toast.makeText(MainActivity.this, "You open the pantry to find your food. Oh, no! The bag of cat food is completely empty. Better search for your owner...", Toast.LENGTH_LONG).show();

                button.setVisibility(View.INVISIBLE);
                new Handler(getMainLooper()).postDelayed(() -> button.setVisibility(View.VISIBLE), 3500);
                }
        });

        //listener trash can shows toast notification
        findViewById(R.id.btnTrash).setOnClickListener(view -> Snackbar.make(relativeLayout, "It's the trash can. It doesn't smell like there is anything good to eat in there.", Snackbar.LENGTH_INDEFINITE).setAction("Close", v-> Toast.makeText(MainActivity.this, "Refocusing..", Toast.LENGTH_LONG).show()).show());

        //listener counter shows toast notification
        findViewById(R.id.btnCounter).setOnClickListener(view -> Snackbar.make(relativeLayout, "It's the counter. You don't see any food up there, so you don't bother jumping up.", Snackbar.LENGTH_INDEFINITE).setAction("Close", v-> Toast.makeText(MainActivity.this, "Refocusing..", Toast.LENGTH_LONG).show()).show());

        //listener button takes player to battle.java
        findViewById(R.id.btnLivingRoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpMusic.stop();
                Snackbar.make(relativeLayout, "You sense something wrong, it's imperative to go here.", Snackbar.LENGTH_INDEFINITE).setAction("Close", view ->  startActivity(new Intent(MainActivity.this, Battle.class))).show();
            }
        });
    }
}