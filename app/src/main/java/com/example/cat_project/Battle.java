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

    final private Cat cat = new Cat(rng.nextInt(100 - 20) + 20);

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

        final Mouse mouse = new Mouse(rng.nextInt(150 - 100) + 100);

        new CountDownTimer(duration, 1000) {
             public void onTick(long millisUntilFinished) {
                 // countdown on screen
                 // convert milliseconds to seconds
                 // String sDur = String.format(Locale.ENGLISH, "%02d", TimeUnit.MILLISECOND(1));

                 // set converted string onto textview
                 // txtTimer.setText(sDur);

                 // allows for battle options which should be radio groups
                 playerBattleOption(radioGroup);

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
                 txtTimer.setText(playerAttackText(radioGroup) + "\n" + mouse.mAtkTxt);

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
                playerBattleOption(radioGroup);

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
                playerBattleOption(radioGroup);

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

    // battle option
    private int playerBattleOption(RadioGroup radioGroup) {
        Random rng = new Random();

        int rdId = radioGroup.getCheckedRadioButtonId(), dmg;

        // need to figure out what are the ids of the radio buttons
        switch (rdId) {
            case 0:
                dmg = rng.nextInt(15);
                break;
            case 1:
                dmg = rng.nextInt(10);
                break;
            case 2:
                dmg = rng.nextInt(12);
                break;
            case 3:
                dmg = rng.nextInt(20);
                break;
            default:
                dmg = 0;
                break;
        }

        return dmg;
    }

    private boolean isDead(int HP) {
        return (HP <= 0);
    }

    private String playerAttackText(RadioGroup radioGroup) {
        int att = radioGroup.getCheckedRadioButtonId();
        String attTxt;

        switch (att) {
            case 0:
                attTxt = "You hiss at the mouse to let him know who is boss";
                break;
            case 1:
                attTxt = "You pounce towards the mouse, ready to bite if you get the chance.";
                break;
            case 2:
                attTxt = "You raise your mighty claws into the air and swipe down.";
                break;
            case 3:
                attTxt = "You concentrate and hit them all in one quick swipe!";
                break;
            default:
                attTxt = "You missed.";
                break;
        }

        return attTxt;
    }

    // superclass
    private static class Character {
        Random rng = new Random();

        protected int HP;

        protected Pair<Integer, String> DmgAndTxtValues;

        protected void getBattleOptionResults(
                int choice, int dmgMax1, int dmgMax2, int dmgMax3, int dmgMax4,
                String atkTxt1, String atkTxt2, String atkTxt3, String atkTxt4) {

            DmgAndTxtValues = setBattleOption(choice, dmgMax1, dmgMax2, dmgMax3, dmgMax4,
            atkTxt1, atkTxt2, atkTxt3, atkTxt4);
        }

        protected Pair<Integer, String> setBattleOption(
                int choice, int dmgMax1, int dmgMax2, int dmgMax3, int dmgMax4,
                String atkTxt1, String atkTxt2, String atkTxt3, String atkTxt4) {

            int dmg;
            String atkTxt;

            switch (choice) {
                case 0:
                    dmg = rng.nextInt(dmgMax1);
                    atkTxt = atkTxt1;
                    break;
                case 1:
                    dmg = rng.nextInt(dmgMax2);
                    atkTxt = atkTxt2;
                    break;
                case 2:
                    dmg = rng.nextInt(dmgMax3);
                    atkTxt = atkTxt3;
                    break;
                case 3:
                    dmg = rng.nextInt(dmgMax4);
                    atkTxt = atkTxt4;
                    break;
                default:
                    dmg = 0;
                    atkTxt = "MISSED.";
                    break;
            }

            return new Pair<Integer, String>(dmg, atkTxt);
        }

        protected int setApplyDmg(int dmg) {
            return HP -= dmg;
        }

        protected boolean isDead(int HP) {
            return (HP <= 0);
        }
    }


    // inner classes
    public static class Cat extends Character {

        public Cat(int HP) {
            this.HP = HP;
        }

        public boolean getIsDead() {
            return isDead(setApplyDmg(1));
        }

        public void getDeathScreen() {
            deathScreen();
        }

        public int getDamage() {
            return Dmg;
        }
        private void deathScreen() {
            // switch to bad ending screen
        }
    }

    public static class Mouse extends Character {
        private int HP;
        String mAtkTxt;

        Random rng = new Random();

        public Mouse(int HP) {
            this.HP = HP;
            this.mAtkTxt = "";
        }

        public int mouseBattleOption(int choice) {
            int dmg;

            switch (choice) {
                case 0:
                    dmg = rng.nextInt(18);
                    mAtkTxt = "The mouse finds things in the pantry to throw at you. It's really annoying, but it won't stop you.";
                    break;
                case 1:
                    dmg = rng.nextInt(12);
                    mAtkTxt = "The mouse discovered a bunch of toothpicks. He is looking around.";
                    break;
                case 2:
                    dmg = rng.nextInt(14);
                    mAtkTxt = "The mouse calls to all his mouse friends. They gang up on you. Mice everywhere!!";
                    break;
                case 3:
                    dmg = rng.nextInt(15);
                    mAtkTxt = "They have you surrounded. Toothpicks everywhere. Is this how it ends?";
                    break;
                default:
                    dmg = 0;
                    mAtkTxt = "The mice missed.";
                    break;
            }

            return dmg;
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
