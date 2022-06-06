package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.TimeUnit;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;
import java.util.Random;

public class Battle extends AppCompatActivity {
    private RadioGroup radioGroup;
    private TextView txtTimer, txtHPAll;
    private MediaPlayer mpMusic;

     ImageView body = (ImageView) findViewById(R.id.body);
    final ImageView rat1 = (ImageView) findViewById(R.id.rat1);
    final ImageView rat2 = (ImageView) findViewById(R.id.rat2);
    final ImageView ratAlone = (ImageView) findViewById(R.id.ratAlone);
    final ImageView killer = (ImageView) findViewById(R.id.killer);

    public RelativeLayout relativeLayout = findViewById(R.id.RelativeLayout);

    private enum Phases {
        PHASE_ONE,
        PHASE_TWO,
        PHASE_THREE {
            @Override
            public Phases next() {
                return null;
            }
        };

        public Phases next() {
            // No bounds checking required here, because the last instance overrides
            return values()[ordinal() + 1];
        }
    }

    final private Cat cat = new Cat();
    private Phases phases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        //music
        mpMusic = new MediaPlayer();
        mpMusic = MediaPlayer.create(this, R.raw.music);
        mpMusic.setLooping(true);
        mpMusic.start();

        radioGroup = findViewById(R.id.radioGroup);
        txtTimer = findViewById(R.id.txtTimer);

        phases = Phases.PHASE_ONE;

        // make switch actually work, phases can only be PHASE_ONE
        switch (phases) {
            case PHASE_ONE:
                // insert battle with argument
                battle(20000, new Mouse(100, 150));
                break;
            case PHASE_TWO:
                battle(15000, new Mice(150, 200));
                break;
            case PHASE_THREE:
                battle(10000, new Killer());
                break;
            default:
                // ending screen
                mpMusic.stop();

                Snackbar.make(relativeLayout, "You won, and although you will never be free from your scars, you can always start on a new beginning.", Snackbar.LENGTH_INDEFINITE).setAction("Roam free as a stray.", view -> startActivity(new Intent(Battle.this, GoodEnding.class))).show();
                break;
        }
    }

    // if possible, make a universal method
    private void battle(long countdownTimerDuration, Character Subclass) {
        // maybe make a CountDownTimer modifiable variable that the methods can be 'overridden'
        new CountDownTimer(countdownTimerDuration, 1000) {
            public void onTick(long millisUntilFinished) {
                // countdown on screen
                // convert milliseconds to seconds
                String timerDurationTxt = String.format(Locale.getDefault(), "%02d", (int) ((millisUntilFinished / 1000) % 60));
                ;

                // set converted string onto textview
                txtTimer.setText(timerDurationTxt);

                // button to skip to fight
                Button btnLockIn = findViewById(R.id.btnLockIn);
                btnLockIn.setOnClickListener(view -> onFinish());
            }

            public void onFinish() {
                txtTimer.setText("");

                // apply damage to both characters
                cat.setApplyDmg(Subclass.getBattleOptionResults(getChoice()));
                Subclass.setApplyDmg(cat.getBattleOptionResults(getRadioID()));

                Snackbar.make(
                        relativeLayout,
                        "Cat damages at: " + cat.getDamageVal() + ", " + cat.getDamageText() + "\n Enemy damages at: " + Subclass.getDamageVal() + " , " + Subclass.getDamageText(),
                        Snackbar.LENGTH_INDEFINITE).setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Battle.this, "You: " + cat.HP + "\n Enemy: " + Subclass.HP, Toast.LENGTH_LONG).show();
                    }
                }).show();

                // check if player's dead first
                if (cat.getIsDead())
                    cat.deathScreen();

                // then enemy
                if (Subclass.getIsDead()) {
                    phases.next();
                    return;
                }

                // if neither deaths is true, run the timer again
                onTick(countdownTimerDuration);
            }
        }.start();
    }

    // grab the radio id the player clicked on
    private int getRadioID() {
        return radioGroup.getCheckedRadioButtonId();
    }

    // randomised choice of attack reserved for mouse and killer, it's 0-4 so the enemies can miss
    private int getChoice() {
        return new Random().nextInt(4);
    }

    // superclass
    private static class Character {
        protected Random rng = new Random();

        protected int HP,
                DmgMin1, DmgMin2, DmgMin3, DmgMin4,
                DmgMax1, DmgMax2, DmgMax3, DmgMax4;
        protected String AtkTxt1, AtkTxt2, AtkTxt3, AtkTxt4, Missed;
        protected Pair<Integer, String> DmgAndTxtValues;

        protected Pair<Integer, String> getBattleOptionResults(int choice) {
            return DmgAndTxtValues = setBattleOption(choice);
        }

        protected int getDamageVal() {
            return DmgAndTxtValues.first;
        }

        protected String getDamageText() {
            return DmgAndTxtValues.second;
        }

        protected Pair<Integer, String> setBattleOption(int choice) {
            int dmg;
            String atkTxt;

            switch (choice) {
                case 0:
                    dmg = rng.nextInt((DmgMax1 - DmgMin1) + DmgMin1);
                    atkTxt = AtkTxt1;
                    break;
                case 1:
                    dmg = rng.nextInt((DmgMax2 - DmgMin2) + DmgMin2);
                    atkTxt = AtkTxt2;
                    break;
                case 2:
                    dmg = rng.nextInt((DmgMax3 - DmgMin3) + DmgMin3);
                    atkTxt = AtkTxt3;
                    break;
                case 3:
                    dmg = rng.nextInt((DmgMax4 - DmgMin4) + DmgMin4);
                    atkTxt = AtkTxt4;
                    break;
                default:
                    dmg = 0;
                    atkTxt = Missed;
                    break;
            }

            return new Pair<>(dmg, atkTxt);
        }

        protected int setApplyDmg(Pair<Integer, String> Dmg) {
            return HP -= Dmg.first;
        }

        protected boolean getIsDead() {
            return (HP <= 0);
        }

        protected void deathScreen() {
        }

        ;
    }

    ;

    // inner classes
    public class Cat extends Character {
        public Cat() {
            this.HP = rng.nextInt((100 - 20) + 20);
            this.DmgMin1 = 9;
            this.DmgMin2 = 8;
            this.DmgMin3 = 9;
            this.DmgMin4 = 2;

            this.DmgMax1 = 15;
            this.DmgMax2 = 10;
            this.DmgMax3 = 12;
            this.DmgMax4 = 20;

            this.AtkTxt1 = "You hiss at the mouse to let him know who is boss";
            this.AtkTxt2 = "You pounce towards the mouse, ready to bite if you get the chance.";
            this.AtkTxt3 = "You raise your mighty claws into the air and swipe down.";
            this.AtkTxt4 = "You concentrate and hit them all in one quick swipe!";
            this.Missed = "You missed";
        }

        @Override
        public void deathScreen() {
           Snackbar.make(relativeLayout, "Ultimately, you failed, you couldn't avenge your owner, you couldn't do anything.", Snackbar.LENGTH_INDEFINITE).setAction("Be locked inside the pound forever", view -> startActivity(new Intent(Battle.this, BadEnding.class))).show();
        }
    }

    public class Mouse extends Character {
        public Mouse(int minHP, int maxHP) {
            this.HP = rng.nextInt((maxHP - minHP) + minHP);
            this.DmgMin1 = 2;
            this.DmgMin2 = 4;
            this.DmgMin3 = 7;
            this.DmgMin4 = 7;

            this.DmgMax1 = 18;
            this.DmgMax2 = 12;
            this.DmgMax3 = 14;
            this.DmgMax4 = 15;

            this.AtkTxt1 = "The mouse finds things in the pantry to throw at you. It's really annoying, but it won't stop you.";
            this.AtkTxt2 = "The mouse discovered a bunch of toothpicks. He is looking around.";
            this.AtkTxt3 = "The mouse calls to all his mouse friends. They gang up on you. Mice everywhere!!";
            this.AtkTxt4 = "They have you surrounded. Toothpicks everywhere. Is this how it ends?";
            this.Missed = "The mice missed.";
        }

        @Override
        public void deathScreen() {
            // mouse dies,
            // cutscene for mouse reformation
            // spawns killer
        }
    }

    public class Mice extends Character {
        public Mice(int minHP, int maxHP) {
            this.HP = rng.nextInt((maxHP - minHP) + minHP);
            this.DmgMin1 = 4;
            this.DmgMin2 = 8;
            this.DmgMin3 = 14;
            this.DmgMin4 = 14;

            this.DmgMax1 = 36;
            this.DmgMax2 = 24;
            this.DmgMax3 = 28;
            this.DmgMax4 = 30;

            this.AtkTxt1 = "The mouse finds things in the pantry to throw at you. It's really annoying, but it won't stop you.";
            this.AtkTxt2 = "The mouse discovered a bunch of toothpicks. He is looking around.";
            this.AtkTxt3 = "The mouse calls to all his mouse friends. They gang up on you. Mice everywhere!!";
            this.AtkTxt4 = "They have you surrounded. Toothpicks everywhere. Is this how it ends?";
            this.Missed = "The mice missed.";
        }

        @Override
        public void deathScreen() {
            // mouse dies,
            // cutscene for mouse reformation
            // spawns killer
        }
    }

    public class Killer extends Character {
        public Killer() {
            this.HP = rng.nextInt(500 - 250) + 250;
            this.DmgMin1 = 12;
            this.DmgMin2 = 14;
            this.DmgMin3 = 7;
            this.DmgMin4 = 6;

            this.DmgMax1 = 40;
            this.DmgMax2 = 50;
            this.DmgMax3 = 40;
            this.DmgMax4 = 70;

            this.AtkTxt1 = "Insert text here.";
            this.AtkTxt2 = "Insert text here.";
            this.AtkTxt3 = "Insert text here.";
            this.AtkTxt4 = "Insert text here.";
            this.Missed = "The killer missed.";
        }

        @Override
        public void deathScreen() {
            Toast.makeText(Battle.this, "Killer is dead", Toast.LENGTH_LONG).show();
        }
    }
}
