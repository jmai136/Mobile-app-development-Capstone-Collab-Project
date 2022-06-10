package com.example.cat_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;
import java.util.Random;

public class Battle extends AppCompatActivity {
    private MediaPlayer mpMusic;

    private enum Phases {
        PHASE_ONE,
        PHASE_TWO,
        PHASE_THREE,
        GOOD_ENDING {
            @Override
            public Phases next() {
                return null;
            }
        };

        // https://stackoverflow.com/questions/17664445/is-there-an-increment-operator-for-java-enum#:~:text=You%20can't%20%22increment%22,ordinal()%20%2B%201%5D%3B
        public Phases next() {
            return values()[ordinal() + 1];
        }
    }

    final private Cat cat = new Cat();
    private Phases phases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        RelativeLayout relativeLayout = findViewById(R.id.RelativeLayout);

        ImageView body = (ImageView) findViewById(R.id.body);

       new Handler(getMainLooper()).postDelayed(() -> {
                Snackbar s = Snackbar
                        .make(relativeLayout, "What?? Your owner has been murdered!! A scruffy looking mouse heads towards you. Is this the fiend who killed your owner?",Snackbar.LENGTH_INDEFINITE)
                        .setAction("Battle", view -> {
                                body.setVisibility(View.GONE);

                                mpMusic = new MediaPlayer();
                                mpMusic = MediaPlayer.create(this, R.raw.music);
                                mpMusic.setLooping(true);
                                mpMusic.start();

                                phases = Phases.PHASE_ONE;
                                setPhases();
                            }
                        );

           TextView snackTextView = (TextView) s .getView().findViewById(com.google.android.material.R.id.snackbar_text);
           snackTextView.setMaxLines(99);
           s.show();

       }, 2000);
    }

    private void setPhases()
    {
        switch (phases) {
            case PHASE_ONE:
                battle(20000, new Mouse());
                break;
            case PHASE_TWO:
                battle(15000, new Mice());
                break;
            case PHASE_THREE:
                battle(10000, new Killer());
                break;
            default:
                mpMusic.stop();
                Snackbar.make(findViewById(R.id.RelativeLayout), "You won, and although you will never be free from your scars, you can always start on a new beginning.", Snackbar.LENGTH_INDEFINITE).setAction("Roam free as a stray.", view -> startActivity(new Intent(Battle.this, GoodEnding.class))).show();
                break;
        }
    }

    private void battle(long countdownTimerDuration, Character Subclass) {
        RelativeLayout relativeLayout = findViewById(R.id.RelativeLayout);
        TextView txtTimer = findViewById(R.id.txtTimer);
        Button btnLockIn = findViewById(R.id.btnLockIn);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        new CountDownTimer(countdownTimerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                radioGroup.setVisibility(View.VISIBLE);

                txtTimer.setVisibility(View.VISIBLE);
                txtTimer.setText(String.format(Locale.getDefault(), "%02d", (int) millisUntilFinished / 1000 % 60));

                btnLockIn.setVisibility(View.VISIBLE);
                btnLockIn.setOnClickListener(view -> onFinish());
            }
            @Override
            public void onFinish() {
                cancel();
                txtTimer.setText("");
                btnLockIn.setVisibility(View.GONE);

                cat.setApplyDmg(Subclass.getBattleOptionResults(getChoice()));
                Subclass.setApplyDmg(cat.getBattleOptionResults(getRadioID(radioGroup)));

                radioGroup.setVisibility(View.GONE);

                Snackbar attacks = Snackbar.make(relativeLayout, "Cat damages at: " + cat.getDamageVal() + ", " + cat.getDamageText() + "\n\nEnemy damages at: " + Subclass.getDamageVal() + " , " + Subclass.getDamageText() + "\n\nHP -" + cat.getName() + ": " + cat.getHP() + " " + Subclass.getName() + ": " + Subclass.getHP(),  Snackbar.LENGTH_INDEFINITE).setAction("Close", view -> {
                    {
                        if (cat.getIsDead())
                            Snackbar.make(relativeLayout, "Ultimately, you failed, you couldn't avenge your owner, you couldn't do anything.", Snackbar.LENGTH_INDEFINITE).setAction("Be locked inside the pound forever", v ->startActivity(new Intent(Battle.this, BadEnding.class))).show();
                        else if (Subclass.getIsDead()) {
                            Subclass.clearImage();

                            phases = phases.next();
                            setPhases();
                        }
                        else
                            start();
                    }
                });
                TextView snackTextView = (TextView) attacks .getView().findViewById(com.google.android.material.R.id.snackbar_text);
                snackTextView.setMaxLines(99);
                attacks.show();
            }
        }.start();
    }

    // grab the radio id the player clicked on
    private int getRadioID(RadioGroup radioGroup) {
        int choice = 4;
        switch(radioGroup.getCheckedRadioButtonId()) {
            case R.id.one:
                choice = 0;
                break;
            case R.id.two:
                choice = 1;
                break;
            case R.id.three:
                choice = 2;
                break;
            case R.id.four:
                choice = 3;
                break;
        }
        return choice;
    }

    // randomised choice of attack reserved for mouse and killer, it's 0-4 so the enemies can miss
    private int getChoice() {
        return new Random().nextInt(4);
    }

    // superclass
    private static class Character {

        private int[] dmgMins, dmgMaxs;
        private String[] atkTxt;
        private int HP, MaxHP;
        private ImageView enemy;
        private Pair<Integer, String> DmgAndTxtValues;

        protected Pair<Integer, String> getBattleOptionResults(int choice) {
            return DmgAndTxtValues = setBattleOption(choice);
        }

        public String getName() {
            return atkTxt[0];
        }

        public int getHP() {
            return HP;
        }

        protected int setHP(int minHP, int maxHP) {
            MaxHP = (int) Math.floor(Math.random()*(maxHP - minHP + 1) + minHP);
            return HP  = MaxHP;
        }

        private int setDamage(int minDmg, int maxDmg) {
            return (int) Math.floor(Math.random()*(maxDmg - minDmg +1) + minDmg);
        }

        protected int[] setDmgMin(int dmgMin1, int dmgMin2, int dmgMin3, int dmgMin4) {
            return dmgMins = new int[]{dmgMin1, dmgMin2, dmgMin3, dmgMin4};
        }

        protected int[] setDmgMax(int dmgMax1, int dmgMax2, int dmgMax3, int dmgMax4) {
            return dmgMaxs = new int[]{dmgMax1, dmgMax2, dmgMax3, dmgMax4};
        }

        protected String[] setAtkTxt(String name, String atkTxt1, String atkTxt2, String atkTxt3, String atkTxt4) {
            return atkTxt = new String[]{name, atkTxt1, atkTxt2, atkTxt3, atkTxt4, name + " missed."};
        }

        protected int getDamageVal() {
            return DmgAndTxtValues.first;
        }

        protected String getDamageText() {
            return DmgAndTxtValues.second;
        }

        protected Pair<Integer, String> setBattleOption(int choice) {
            int finDmg = 0;
            String finalAtkTxt;

            // https://www.educative.io/edpresso/how-to-generate-random-numbers-in-java
            switch (choice) {
                case 0:
                    finDmg = setDamage(dmgMins[0], dmgMaxs[0]);
                    finalAtkTxt = atkTxt[1];
                    break;
                case 1:
                    finDmg = setDamage(dmgMins[1], dmgMaxs[1]);
                    finalAtkTxt = atkTxt[2];
                    break;
                case 2:
                    finDmg = setDamage(dmgMins[2], dmgMaxs[2]);
                    finalAtkTxt = atkTxt[3];
                    break;
                case 3:
                    finDmg = setDamage(dmgMins[3], dmgMaxs[3]);
                    finalAtkTxt = atkTxt[4];
                    break;
                default:
                    finalAtkTxt = atkTxt[5];
                    break;
            }

            return new Pair<>(finDmg, finalAtkTxt);
        }

        protected int setApplyDmg(@NonNull Pair<Integer, String> Dmg) { return HP = clamp(0, HP -= Dmg.first, MaxHP);}

        protected boolean getIsDead() { return (HP <= 0);}

        protected void setImage(ImageView source) {
            enemy = source;
            enemy.setVisibility(View.VISIBLE);
        }
        protected void clearImage() { enemy.setImageResource(0);}

        // utility method: http://www.java2s.com/example/java-utility-method/integer-clamp/clamp-final-int-min-final-int-x-final-int-max-41c25.html
        private static int clamp(final int min, final int x, final int max) {
            if (max < min)
                throw new IllegalArgumentException("Max is less than min");

            return Math.max(min, Math.min(max, x));
        }
    }

    // inner classes
    public static class Cat extends Character {
        public Cat() {
            this.setHP(300, 500);
            this.setDmgMin(9, 8, 9, 2);
            this.setDmgMax(15, 10, 12, 20);
            this.setAtkTxt(
                    "You",
                    "You raise your mighty claws into the air and swipe down.",
                    "You pounce towards the mouse, ready to bite if you get the chance.",
                    "You concentrate and hit them all in one quick swipe!",
                    "You hiss at the mouse to let him know who is boss");
        }
    }

    public class Mouse extends Character {
        public Mouse() {
            this.setHP(10, 15);
            this.setDmgMin(2, 4, 7, 7);
            this.setDmgMax(18, 12, 14, 15);
            this.setAtkTxt(
                    "Mouse",
                    "The mouse finds things in the pantry to throw at you. It's really annoying, but it won't stop you.",
                    "The mouse discovered a bunch of toothpicks. He is looking around.",
                    "The mouse calls to all his mouse friends. They gang up on you. Mice everywhere!!",
                    "They have you surrounded. Toothpicks everywhere. Is this how it ends?");

            this.setImage(findViewById(R.id.ratAlone));
        }
    }

    public class Mice extends Character {
        public Mice() {
            this.setHP(30, 60);
            this.setDmgMin(4, 8, 10, 12);
            this.setDmgMax(36, 24, 28, 30);
            this.setAtkTxt(
                    "Mice",
                    "The mice all gang up on you, you feel your skin crawling.",
                    "The mice all decide to claw at your eyes, it hurts to some degree.",
                    "The mice are merciless, deciding to kick you swiftly while surrounding.",
                    "They truly are a pair, backing you into a corner.");

            this.setImage(findViewById(R.id.ratGroup));
        }
    }

    public class Killer extends Character {
        public Killer() {
            this.setHP(80, 100);
            this.setDmgMin(12, 14, 7, 6);
            this.setDmgMax(40, 50, 40, 70);
            this.setAtkTxt(
                    "Killer",
                    "The killer plunges their knife into your soul. You feel a part of it leaving you.",
                    "You feel a drain, your movement feels more sluggish, you could fall asleep here and now.",
                    "The killer slashes, and you can imagine what your owner must have felt.",
                    "The killer strikes from above, and unfortunately you can't hide your terror.");

            this.setImage(findViewById(R.id.killer));
        }
    }
}
