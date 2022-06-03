package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.TimeUnit;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Battle extends AppCompatActivity {
    private RadioGroup radioGroup;
    private TextView txtTimer, txtHPAll;
    private MediaPlayer mpMusic;
    private enum Phases {
        PHASE_ONE,
        PHASE_TWO,
        PHASE_THREE
                {
                    @Override
                    public Phases next() {
                        return null;
                    };
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
        txtHPAll = findViewById(R.id.txtHPAll);

        phases = Phases.PHASE_ONE;
        // final Mouse mouse = new Mouse(100, 150);

        switch (phases) {
            case PHASE_ONE:
                // insert battle with argument
                battle(20000, new Mouse(100, 150));
                break;
            case PHASE_TWO:
                // hm, how to do array of mice here
                Mouse mouseGrp[] = {new Mouse(100, 150), new Mouse(100, 180), new Mouse(100, 270), new Mouse(100, 220), new Mouse(100, 230)};
                battle(10000, mouseGrp);
                break;
            case PHASE_THREE:
                battle(5000, new Killer());
                break;
            default:
                // ending screen
                mpMusic.stop();
                break;
        }
    }

    private void goodEnding() {

    }

    @Override
    private void battle() {
        return;
    };

    // if possible, make a universal method
    private void battle(long countdownTimerDuration, Character Subclass) {
        new CountDownTimer(countdownTimerDuration, 1000) {
            public void onTick(long millisUntilFinished) {
                // countdown on screen
                // convert milliseconds to seconds
                String timerDurationTxt = String.format(Locale.getDefault(),  "%02d",  (int)(millisUntilFinished/1000)%60);;

                // set converted string onto textview
                txtTimer.setText(timerDurationTxt);

                // button to skip to fight
                Button btnLockIn = findViewById(R.id.btnLockIn);

                btnLockIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // finish the timer automatically
                        onFinish();
                    }
                });
            }

            public void onFinish() {
                // allows for battle options which should be radio groups

                // apply damage to both characters
                cat.setApplyDmg(Subclass.getBattleOptionResults(getChoice()));
                Subclass.setApplyDmg(cat.getBattleOptionResults(getRadioID()));

                // check if player's dead first
                if (cat.getIsDead())
                    cat.deathScreen();

                // then enemy
                if (Subclass.getIsDead()) {
                    phases.next();
                    return;
                }

                // set text timer to show what has happened
                txtTimer.setText(cat.getDamage() + ": " +  cat.getDamageText() + "\n" +  + Subclass.getDamage() + ": " + Subclass.getDamageText());

                // update healths, maybe make this just one text view, save space
                txtHPAll.setText(cat.HP + "\n" + Subclass.HP);

                // if neither deaths is true, run the timer again
                onTick(countdownTimerDuration);
            }
        }.start();

        return;
    }

    // for the second battle
    private void battle(long countdownTimerDuration, Mouse[] mouse) {
        return;
    }

    private int getChoice ()  { return new Random().nextInt(4); }

    /*private void secondBattle() {
        // countdown timer, 20 seconds is equal to 20000 milliseconds
        long duration = 10000;
        // final Mouse mouse.mousePhaseTwo[];

        new CountDownTimer(duration, 1000) {
            public void onTick(long millisUntilFinished) {
                // countdown on screen
                // convert milliseconds to seconds
                // String sDur = String.format(Locale.ENGLISH, "%02d", TimeUnit.MILLISECOND(1));

                // set converted string onto textview
                // txtTimer.setText(sDur);

                // allows for battle options which should be radio groups
                // playerBattleOption(radioGroup);

                Button btnLockIn = findViewById(R.id.btnLockIn);
                btnLockIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // finish the timer automatically
                        onFinish();
                    }
                });
            }

            public void onFinish() {
                int catDmg = 0;

                for (int mouse = 0; mouse < mousePhaseTwo.length ; mouse++)
                    catDmg += cat.applyDmg(mousePhaseTwo[mouse].mouseBattleOption(rng.nextInt(4)));

                // check if player's dead first
                if (isDead(catDmg))
                    cat.deathScreen();

                // apply damage to both characters
                if (mousePhaseTwo.length <= 0)
                    return;

                // set text timer to show what has happened
                txtTimer.setText(playerAttackText(radioGroup));

                // if neither deaths is true, run the timer again
                onTick(duration);
            }
        }.start();
    }*/


    private int getRadioID() { return radioGroup.getCheckedRadioButtonId(); }

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

        protected int getDamage() {
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

            return new Pair<Integer, String>(dmg, atkTxt);
        }

        protected int setApplyDmg(Pair<Integer, String> Dmg) {
            return HP -= Dmg.first;
        }

        protected boolean getIsDead() {
            return (HP <= 0);
        }

        @Override
        protected void deathScreen() {
        };
    };

    // inner classes
    public class Cat extends Character {
        public Cat() {
            this.HP = rng.nextInt(100 - 20) + 20;
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

        public void deathScreen() {
            Toast.makeText(Battle.this, "Cat is dead", Toast.LENGTH_LONG).show();
        }
    }

    public class Mouse extends Character {
        final protected Mouse mousePhaseTwo[] =
                {
                        new Mouse(100, 150),
                        new Mouse(100, 180),
                        new Mouse(100, 270),
                        new Mouse(100, 220),
                        new Mouse(100, 230)
                };

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

        public void deathScreen() {
            Toast.makeText(Battle.this, "Killer is dead", Toast.LENGTH_LONG).show();
        }
    }
