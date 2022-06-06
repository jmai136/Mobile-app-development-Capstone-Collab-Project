package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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

    private RelativeLayout relativeLayout = findViewById(R.id.RelativeLayout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.imgKitchenPixel);
        final TextView kitchen1 = (TextView) findViewById(R.id.txtKitchen1);
        final TextView kitchen2 = (TextView) findViewById(R.id.txtKitchen2);
        Button button = (Button) findViewById(R.id.btnLivingRoom);
        Button btnPantry = (Button) findViewById(R.id.btnPantry);
        Button btnTrash = (Button) findViewById(R.id.btnTrash);
        Button btnCounter = (Button) findViewById(R.id.btnCounter);

        //music
        mpMusic = new MediaPlayer();
        mpMusic = MediaPlayer.create(this, R.raw.music);
        mpMusic.setLooping(true);
        mpMusic.start();

        //timer to set first txt invisible, second txt vis
        new CountDownTimer(3500, 1000) {
            public void onTick(long millisUntilFinished) {
                kitchen1.setVisibility(View.VISIBLE);
                kitchen2.setVisibility(View.INVISIBLE);
            }
            public void onFinish() {
                kitchen1.setVisibility(View.INVISIBLE);
                kitchen2.setVisibility(View.VISIBLE);
            }
        }.start();

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

                //delay button visibility
                new CountDownTimer(3500, 1000) {
                    public void onTick(long millisUntilFinished) {
                        button.setVisibility(View.INVISIBLE);
                    }
                    public void onFinish() {
                        button.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });

        //listener trash can shows toast notification
        btnTrash.setOnClickListener(view -> Snackbar.make(relativeLayout, "Ultimately, you failed, you couldn't avenge your owner, you couldn't do anything.", Snackbar.LENGTH_INDEFINITE).setAction("Be locked inside the pound forever", v-> Toast.makeText(MainActivity.this, "Refocusing..", Toast.LENGTH_LONG).show()).show());

        //listener counter shows toast notification
        btnCounter.setOnClickListener(view -> Snackbar.make(relativeLayout, "It's the counter. You don't see any food up there, so you don't bother jumping up.", Snackbar.LENGTH_INDEFINITE).setAction("Close", v-> Toast.makeText(MainActivity.this, "Refocusing..", Toast.LENGTH_LONG).show()).show());

        //listener button takes player to battle.java
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpMusic.stop();
                Snackbar.make(relativeLayout, "You sense something wrong, it's imperative to go here.", Snackbar.LENGTH_INDEFINITE).setAction("Close", new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, Battle.class));
                    }
                }).show();
            }
        });
    }
}