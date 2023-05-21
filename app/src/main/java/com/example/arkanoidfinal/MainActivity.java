package com.example.arkanoidfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.os.Looper;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int startchet=4;
    int ballx=15;int bally=-15;
    int score=0; int level=1;
    int bricks=81;
    int respawn=0;
    ImageView plat;
    int ballstart;int platstart; int ballstartx;
    ImageView ball;Handler handler = new Handler();
    ImageView[] bricks1 = new ImageView[81];

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(startchet==-1) {
                int x = (int)plat.getX();
                if(x>200 && respawn==0) {
                    plat.setX(x - 15);
                }
            }
            handler.postDelayed(this, 10);
        }
    };
    Handler handler2 = new Handler();
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            if (startchet == -1 && respawn==0) {
                int x = (int)plat.getX();
                if(x<1890) {
                    plat.setX(x + 15);
                }
            }
            handler2.postDelayed(this, 10);
        }
    };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for (int i = 0; i < 81; i++) {
        for(int i =0; i<81;i++){
            int id = getResources().getIdentifier("a" + i, "id", getPackageName());
            bricks1[i] = findViewById(id);
        }
        plat = findViewById(R.id.Platforma);
        ImageView wal = findViewById(R.id.leftwall);
        ImageView walr = findViewById(R.id.rightwall);
         ball = findViewById(R.id.ballid);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.start);
                        String newText = Integer.toString(startchet);
                        textView.setText(newText);
                        if (startchet == 0) {
                            timer.cancel();
                            ViewGroup parent = (ViewGroup) textView.getParent();
                            parent.removeView(textView);
                            ImageView wal = findViewById(R.id.leftwall);
                            ImageView walr = findViewById(R.id.rightwall);
                            ball.setX(plat.getRight()-150);
                            ballstart=(int)ball.getY();
                            ballstartx = (int)ball.getX();
                            platstart=(int)plat.getX();
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
                    handler.postDelayed(runnable, 20);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.removeCallbacks(runnable);
                }
                return true;
            }
        });
        ImageButton arrr = findViewById(R.id.rightarrow);
        arrr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    handler2.postDelayed(runnable2, 20);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler2.removeCallbacks(runnable2);
                }
                return true;
            }
        });

        Handler handler12 = new Handler();
        Runnable runnable12 = new Runnable() {
            @Override
            public void run() {
                if(startchet==-1 && respawn==0){
                    ball.setX(ball.getX() + ballx);
                    ball.setY(ball.getY() + bally);
                    for (int i = 0; i < 81; i++) {
                        ImageView img = bricks1[i];
                        Rect rect1 = new Rect();
                        ball.getGlobalVisibleRect(rect1);

                        Rect rect2 = new Rect();
                        bricks1[i].getGlobalVisibleRect(rect2);

                        if (Rect.intersects(rect1, rect2)) {
                            if(img.getVisibility()==View.VISIBLE)
                            {
                                bally=-bally;
                                score+=100;
                                bricks-=1;
                                if(bricks==0)
                                {
                                    nextlevel();
                                }
                                TextView txt = findViewById(R.id.scoreView);
                                txt.setText("Score:\n" + Integer.toString(score));
                            }
                            img.setVisibility(View.GONE);
                        }
                    }
                    if(ball.getX() <= wal.getRight()) {
                        ballx = -ballx;
                    }

                    if(ball.getX() >= walr.getX()-60){
                        ballx = -ballx;
                    }
                    if(ball.getY() <= 0) {
                        bally = -bally;
                    }
                    if(ball.getY() + ball.getHeight() >= plat.getY() && ball.getY() <= plat.getY() + plat.getHeight() && ball.getX() + ball.getWidth() >= plat.getX() && ball.getX() <= plat.getX() + plat.getWidth()) {
                        bally = -bally;
                        if (ball.getX() + 80 == plat.getX() || ball.getLeft() == plat.getX() +240) {
                            ballx=-ballx;
                        }
                    }
                    if(ball.getY()>=plat.getY())
                    {
                        upal();
                    }
                }
                handler12.postDelayed(this, 30);
            }
        };
        handler12.postDelayed(runnable12, 30);

    }
    public void upal() {
        score-=500;
        TextView txt = findViewById(R.id.scoreView);
        txt.setText("Score:\n" + Integer.toString(score));
        respawn=1;
        ConstraintLayout cl = findViewById(R.id.rella);
        ball.setX(ballstartx);ball.setY(ballstart);plat.setX(ball.getLeft()-100);
        ballx=15;bally=-15;
        //  startchet=50;
        final TextView countdownTextView = new TextView(this);
        countdownTextView.setText("3");
        countdownTextView.setTextSize(24);
        countdownTextView.setId(View.generateViewId());

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        params.topToTop = cl.getId();
        params.bottomToBottom = cl.getId();
        params.startToStart = cl.getId();
        params.endToEnd = cl.getId();
        countdownTextView.setLayoutParams(params);

        cl.addView(countdownTextView);

        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownTextView.setText(String.valueOf(millisUntilFinished / 1000 +1));
            }

            public void onFinish() {
                cl.removeView(countdownTextView);
                respawn=0;
            }
        }.start();
    }
    public void nextlevel(){
            upal();
            score+=500;
            TextView txt = findViewById(R.id.scoreView);
            txt.setText("Score:\n" + Integer.toString(score));
            level++;
            switch (level){
                case 2:
                    for (int i = 0; i < 81; i++) {
                        if(i%9==0 || i==0)
                        {
                            ImageView img = bricks1[i];
                            img.setVisibility(View.VISIBLE);
                        }
                        bricks=9;
                    }
                    break;
                case 3:
                    for (int i = 0; i < 81; i++) {
                        if(i%9==1 || i==1)
                        {
                            ImageView img = bricks1[i];
                            img.setVisibility(View.VISIBLE);
                        }
                        bricks=9;
                    }
                    break;
            }
    }
}
