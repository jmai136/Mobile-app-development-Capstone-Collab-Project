package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //global variables for music
    MediaPlayer mpMusic;

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
        /*TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                kitchen1.setVisibility(View.INVISIBLE);
                kitchen2.setVisibility(View.VISIBLE);
            }
        };
            Timer text = new Timer();
            text.schedule(task, 5000);
*/
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

                button.setVisibility(View.VISIBLE);
            }
        });

        //listener trash can shows toast notification
        btnTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "It's the trash can. It doesn't smell like there is anything good to eat in there.", Toast.LENGTH_LONG).show();
            }
        });

        //listener counter shows toast notification
        btnCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "It's the counter. You don't see any food up there, so you don't bother jumping up.", Toast.LENGTH_LONG).show();
            }
        });

        //listener button takes player to battle.java
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpMusic.stop();
                startActivity(new Intent(MainActivity.this, Battle.class));
            }
        });
    }
}