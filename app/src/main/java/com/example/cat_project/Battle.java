package com.example.cat_project;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class Battle extends AppCompatActivity {
    final Random rng = new Random();

    private RadioGroup radioGroup;
    private TextView txtTimer, txtPlayerHP, txtEnemyHP;

    final private Cat cat = new Cat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        radioGroup = findViewById(R.id.radioGroup);
        txtTimer = findViewById(R.id.txtTimer);
        txtEnemyHP = findViewById(R.id.txtEnemyHP);
        txtPlayerHP = findViewById(R.id.txtPlayerHP);

        // start the battle
        firstBattle();

        secondBattle();

        finalBattle();
    }

    // if possible, make a universal method
    private void battle(long countdownTimerDuration) {

    }

    private void firstBattle() {
        // countdown timer, 20 seconds is equal to 20000 milliseconds
        long duration = 20000;

        final Mouse mouse = new Mouse(100, 150);

        new CountDownTimer(duration, 1000) {
             public void onTick(long millisUntilFinished) {
                 // countdown on screen
                 // convert milliseconds to seconds
                 // String sDur = String.format(Locale.ENGLISH, "%02d", TimeUnit.MILLISECOND(1));

                 // set converted string onto textview
                 // txtTimer.setText(sDur);

                 // allows for battle options which should be radio groups
                 cat.getBattleOptionResults(getRadioID());

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
                 // apply damage to both characters
                 int catDmg = cat.applyDmg(mouse.mouseBattleOption(rng.nextInt(4))), mouseDmg = mouse.applyDmg(playerBattleOption(radioGroup));

                 // check if player's dead first
                 if (isDead(catDmg))
                    cat.deathScreen();

                 // then mouse
                 if (isDead(mouseDmg))
                     return;

                 // set text timer to show what has happened
                 txtTimer.setText(cat.getDamageText() + "\n" + mouse.mAtkTxt);

                 // update healths
                 txtEnemyHP.setText(mouseDmg);
                 txtPlayerHP.setText(catDmg);

                 // if neither deaths is true, run the timer again
                 onTick(duration);
             }
         }.start();
    }

    private void secondBattle() {
        // countdown timer, 20 seconds is equal to 20000 milliseconds
        long duration = 10000;

        final Mouse mousePhaseTwo[] =
                {
                        new Mouse(rng.nextInt(150 - 100) + 100),
                        new Mouse(rng.nextInt(180 - 100) + 100),
                        new Mouse(rng.nextInt(270 - 100) + 100),
                        new Mouse(rng.nextInt(220 - 100) + 100),
                        new Mouse(rng.nextInt(230 - 100) + 100),
                };

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
    }

    private void finalBattle() {
        // countdown timer, 20 seconds is equal to 20000 milliseconds
        long duration = 5000;

        final Killer killer = new Killer(rng.nextInt(500 - 250) + 250);

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
                // int catDmg = cat.applyDmg(killer.mouseBattleOption(rng.nextInt(4))), killerDmg = killer.applyDmg(playerBattleOption(radioGroup));

                // check if player's dead first
                // if (isDead(catDmg))
                    // cat.deathScreen();

                // apply damage to both characters
                // if (mousePhaseTwo.length <= 0)
                    // return;

                // set text timer to show what has happened
                txtTimer.setText(playerAttackText(radioGroup));

                // if neither deaths is true, run the timer again
                onTick(duration);
            }
        }.start();
    }

    private int getRadioID() {
        return radioGroup.getCheckedRadioButtonId();
    }

    private boolean isDead(int HP) {
        return (HP <= 0);
    }


    // superclass
    private static class Character {
        Random rng = new Random();

        protected int HP, Choice, DmgMax1, DmgMax2, DmgMax3, DmgMax4;
        protected String AtkTxt1, AtkTxt2, AtkTxt3, AtkTxt4, Missed;
        protected Pair<Integer, String> DmgAndTxtValues;

        protected void getBattleOptionResults(int choice) {
            DmgAndTxtValues = setBattleOption(choice);
        }

        protected int getDamage() {
            return DmgAndTxtValues.first;
        }

        protected String getDamageText() {
            return DmgAndTxtValues.second;
        }

        protected Pair<Integer, String> setBattleOption(
                int choice) {

            int dmg;
            String atkTxt;

            switch (choice) {
                case 0:
                    dmg = rng.nextInt(DmgMax1);
                    atkTxt = AtkTxt1;
                    break;
                case 1:
                    dmg = rng.nextInt(DmgMax2);
                    atkTxt = AtkTxt2;
                    break;
                case 2:
                    dmg = rng.nextInt(DmgMax3);
                    atkTxt = AtkTxt3;
                    break;
                case 3:
                    dmg = rng.nextInt(DmgMax4);
                    atkTxt = AtkTxt4;
                    break;
                default:
                    dmg = 0;
                    atkTxt = Missed;
                    break;
            }

            return new Pair<Integer, String>(dmg, atkTxt);
        }

        protected int setApplyDmg() {
            return HP -= DmgAndTxtValues.first;
        }

        protected boolean isDead() {
            return (HP <= 0);
        }
    }


    // inner classes
    public static class Cat extends Character {
        public Cat() {
            this.HP = rng.nextInt(100 - 20) + 20;
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

        public boolean getIsDead() {
            return isDead(setApplyDmg(1));
        }

        public void getDeathScreen() {
            deathScreen();
        }

        private void deathScreen() {
            // switch to bad ending screen
        }
    }

    public static class Mouse extends Character {
        final Mouse mousePhaseTwo[] =
                {
                        new Mouse(100, 150),
                        new Mouse(100, 180),
                        new Mouse(100, 270),
                        new Mouse(100, 220),
                        new Mouse(100, 230)
                };

        public Mouse(int minHP, int maxHP) {
            this.HP = rng.nextInt((maxHP - minHP) + minHP);
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

        public int applyDmg(int dmg) {
            return HP -= dmg;
        }

        public void mouseDeath() {
            // mouse dies,
            // cutscene for mouse reformation
            // spawns killer
        }
    }

    public static class Killer {
        private int HP;

        public Killer(int HP) {
            this.HP = HP;
        }

        public int applyDmg(int dmg) { return HP -= dmg; }
    }
}
