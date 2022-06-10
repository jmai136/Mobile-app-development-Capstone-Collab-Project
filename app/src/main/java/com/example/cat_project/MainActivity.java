package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    //global variables for music
    MediaPlayer mpMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.imgKitchenPixel);

        RelativeLayout relativeLayout = findViewById(R.id.RelativeLayout);

        //music
        mpMusic = new MediaPlayer();
        mpMusic = MediaPlayer.create(this, R.raw.music);
        mpMusic.setLooping(true);
        mpMusic.start();

        final TextView
                kitchen1 = (TextView) findViewById(R.id.txtKitchen1),
                kitchen2 = (TextView) findViewById(R.id.txtKitchen2);

        //show first text message; hide second message
        new Handler().post(() -> {
                kitchen1.setVisibility(View.VISIBLE);
                kitchen2.setVisibility(View.INVISIBLE);
            };

        //show second text message on delay; hide first message
        new Handler(getMainLooper()).postDelayed(() -> {
            kitchen1.setVisibility(View.INVISIBLE);
            kitchen2.setVisibility(View.VISIBLE);
        }, 3500);

        Button btnLivingRoom = (Button) findViewById(R.id.btnLivingRoom);
        btnLivingRoom.setVisibility(View.GONE);

        //listener pantry button toast shows img and txt
        // fix icon, it's a bit too big
        findViewById(R.id.btnPantry).setOnClickListener(v -> {
            Snackbar pantry = Snackbar.make(relativeLayout, "You open the pantry to find your food. Oh, no! The bag of cat food is completely empty. Better search for your owner...",  Snackbar.LENGTH_INDEFINITE).setAction("Close", view -> btnLivingRoom.setVisibility(View.VISIBLE));

            TextView snackTextView = (TextView) pantry.getView().findViewById(com.google.android.material.R.id.snackbar_text);
            snackTextView.setMaxLines(99);

            pantry.show();
            });

        //listener trash can shows toast notification
        findViewById(R.id.btnTrash).setOnClickListener(view -> Snackbar.make(relativeLayout, "It's the trash can. It doesn't smell like there is anything good to eat in there.", Snackbar.LENGTH_INDEFINITE).setAction("Close", v-> Toast.makeText(MainActivity.this, "Refocusing..", Toast.LENGTH_LONG).show()).show());

        //listener counter shows toast notification
        findViewById(R.id.btnCounter).setOnClickListener(view -> Snackbar.make(relativeLayout, "It's the counter. You don't see any food up there, so you don't bother jumping up.", Snackbar.LENGTH_INDEFINITE).setAction("Close", v-> Toast.makeText(MainActivity.this, "Refocusing..", Toast.LENGTH_LONG).show()).show());

        //listener button takes player to battle.java
        btnLivingRoom.setOnClickListener(v -> {
            mpMusic.stop();
            Snackbar.make(relativeLayout, "You sense something wrong, it's imperative to go here.", Snackbar.LENGTH_INDEFINITE).setAction("Close", view ->  startActivity(new Intent(MainActivity.this, Battle.class))).show();
        });
    }
}