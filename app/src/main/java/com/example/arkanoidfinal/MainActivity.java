package com.example.arkanoidfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int startchet=4;
    int ballx=15;int bally=-15;
    int score=0; int level=0;
    int bricks; int lives=2;
    boolean platdvizl=false;
    boolean gamestarted=false;
    boolean platdvizr = false;
    int respawn=0;
    ImageView plat;
    int ballstart;int platstart; int ballstartx;
    ImageView ball;Handler handler = new Handler();
    ImageView[] bricks1 = new ImageView[81];
    int[] brickstatus = new int[81];
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for (int i = 0; i < 81; i++) {
        //for(int i =0; i<81;i++){
           // int id = getResources().getIdentifier("a" + i, "id", getPackageName());
            //bricks1[i] = findViewById(id);
            //bricks1[i].setVisibility(View .INVISIBLE);
          //  brickstatus[i]=0;
        //}
        plat = findViewById(R.id.Platforma);
        ImageView wal = findViewById(R.id.leftwall);
        ImageView walr = findViewById(R.id.rightwall);
        ball = findViewById(R.id.ballid);
        Timer timer = new Timer();
        nextlevel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.start);
                        String newText = Integer.toString(startchet);
                        textView.setText(newText);
                        textView.bringToFront();
                        if (startchet == 0) {
                            timer.cancel();
                            ViewGroup parent = (ViewGroup) textView.getParent();
                            parent.removeView(textView);
                            ImageView wal = findViewById(R.id.leftwall);
                            ImageView walr = findViewById(R.id.rightwall);
                            ballstart=(int)ball.getY();
                            ballstartx = (int)ball.getX();
                            //for (int i = 0; i < 81; i++) {
                             //   if(i!=8) {
                             //       bricks1[i].setVisibility(View.INVISIBLE);
                            //        bricks--;
                            //    }
                            //}
                        }
                        startchet--;
                    }
                });
            }
        }, 1000, 1000);

        ImageButton arrl = findViewById(R.id.leftarrow);
        arrl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(startchet==-1 && respawn==0)
                    platdvizl=true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    platdvizl=false;
                }
                return true;
            }
        });
        ImageButton arrr = findViewById(R.id.rightarrow);
        arrr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(startchet==-1 && respawn==0)
                    platdvizr=true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    platdvizr=false;
                }
                return true;
            }
        });

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(startchet==-1 && respawn==0){
                    if(platdvizl)
                    {
                        if(plat.getX()>findViewById(R.id.leftwall).getRight())
                            plat.setX(plat.getX()-15);
                    }
                    if(platdvizr)
                    {
                        if(plat.getRight()<findViewById(R.id.rightwall).getLeft())
                        {
                            plat.setX(plat.getX()+15);
                        }
                    }
                    ball.setX(ball.getX() + ballx);
                    ball.setY(ball.getY() + bally);
                    if(ball.getX() <= wal.getRight()) {
                        ballx = -ballx;
                    }

                    if(ball.getX() >= walr.getX()-60){
                        ballx = -ballx;
                    }
                    if(ball.getY() <= 0) {
                        bally = -bally;
                    }
                    if(ball.getY()>1250) {
                        if (ball.getY() + ball.getHeight() >= plat.getY() && ball.getY() <= plat.getY() + plat.getHeight() && ball.getX() + ball.getWidth() >= plat.getX() && ball.getX() <= plat.getX() + plat.getWidth()) {
                            bally = -bally;
                            if (ball.getX() + 80 == plat.getX() || ball.getLeft() == plat.getX() + 240) {
                                ballx = -ballx;
                            }
                        }
                    }
                    if(ball.getY()>=plat.getY())
                    {
                        upal(0);
                    }
                    Rect bl = new Rect();
                    ball.getHitRect(bl);
                    for (int i = 0; i < 81; i++) {
                        if(bricks1[i]!=null)
                        {
                            Rect br = new Rect();
                            bricks1[i].getHitRect(br);
                            if(br.intersect(bl))
                            {
                                bricks--;
                                ConstraintLayout cl = findViewById(R.id.rella);
                                cl.removeView(bricks1[i]);
                                bricks1[i]=null;
                                bally=-bally;
                                score+=100;
                            }
                        }
                    }
                    if(bricks==0)
                    {
                        nextlevel();
                    }
                }
                handler.postDelayed(this, 15);
            }
        };
        handler.postDelayed(runnable, 1000);

    }
    public void upal(int a) {
        if(a!=1){
            score-=500;
            lives--;
            TextView ltxt = findViewById(R.id.livesView);
            ltxt.setText("Lives:\n" + Integer.toString(lives));
            TextView txt = findViewById(R.id.scoreView);
            txt.setText("Score:\n" + Integer.toString(score));
        }
        if(lives == 0) {
            death();
        }
        ConstraintLayout cl = findViewById(R.id.rella);
        ball.setX(ballstartx);ball.setY(ballstart);plat.setX(ball.getLeft()-100);
        ballx=15;bally=-15;
        //  startchet=50;
        respawn=1;
        final TextView tmr = new TextView(this);
            tmr.setText("3");
            tmr.setTextSize(100);
            tmr.bringToFront();
            tmr.setId(View.generateViewId());
            if(gamestarted==false){
                tmr.setVisibility(View.GONE);
                gamestarted=true;
            }
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            params.topToTop = cl.getId();
            params.bottomToBottom = cl.getId();
            params.startToStart = cl.getId();
            params.endToEnd = cl.getId();
            tmr.setLayoutParams(params);
            cl.addView(tmr);
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                    tmr.setText(String.valueOf(millisUntilFinished / 1000 + 1));
                }

                public void onFinish() {
                    cl.removeView(tmr);
                    respawn = 0;
                }
            }.start();
        }
    public void nextlevel(){
        for (int i = 0; i < 81; i++) {
            bricks1[i]=null;
            brickstatus[i]=0;
            bricks=0;
        }
        ConstraintLayout cl = findViewById(R.id.rella);
        for (int i = 0; i < cl.getChildCount(); i++) {
            View v = cl.getChildAt(i);
            if(v.getWidth()==200 && v.getHeight()==100)
            {
                cl.removeView(v);
            }

        }
        lives++;
        TextView ltxt = findViewById(R.id.livesView);
        ltxt.setText("Lives:\n" + Integer.toString(lives));
        TextView txt = findViewById(R.id.scoreView);
        txt.setText("Score:\n" + Integer.toString(score));
        upal(1);
        level++;
        TextView lvltxt = findViewById(R.id.levelView);
        lvltxt.setText("Level:\n"+Integer.toString(level));

        switch (level){
            case 1:
                for (int i = 0; i < 81; i++) {
                    if(i<63 && i!=0 && i%9!=0 && i%9!=1 && i%9!=7 && i%9!=8)
                    {
                        brickstatus[i]=1;
                        bricks++;
                    }
                }
                break;
                case 2:
                    for (int i = 0; i < 81; i++) {
                        if( (i>0 && i<9)|| (i>10 && i<18) || (i>20 && i<27) || (i>30 && i<36) || (i>40 && i<45) || (i>50 && i<54)|| (i>60 && i<63) || i==71) {}
                        else
                        {
                            brickstatus[i]=1;
                            bricks++;
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 81; i++) {
                        if(i!=19 && i!=25 && i!=28&& i!=34&& i!=37&& i!=43&& i!=46&& i!=52&& i!=55&& i!=61&& i!=10&& i!=11&& i!=12&& i!=13&& i!=14&& i!=15&& i!=16&& i!=64&& i!=65&& i!=66&& i!=67&& i!=68&& i!=69&& i!=70 && i!=30&& i!=31&& i!=32&& i!=39&& i!=41&& i!=48&& i!=49&& i!=50)
                        {
                            brickstatus[i]=1;
                            bricks++;
                        }
                    }
                    break;
            }for (int i = 0; i < 81; i++) {
            if (brickstatus[i] >= 1) {
                ImageView brick = new ImageView(this);
                brick.setLayoutParams(new ViewGroup.LayoutParams(200, 100));
                brick.setImageResource(R.drawable.brick);
                ConstraintLayout cnlt = findViewById(R.id.rella);
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                );
                brick.setX(210 + (i%9)*240);
                brick.setY(30 + (i/9) * 120);
                cnlt.addView(brick);
                bricks1[i] = brick;
            }
        }
    }
    public void death()
    {
        level=0;
        score=0;
        lives=3;
        nextlevel();
    }
}
