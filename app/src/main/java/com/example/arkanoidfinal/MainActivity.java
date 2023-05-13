package com.example.arkanoidfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
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
    int respawn=0;
    ImageView plat;
    int ballstart;int platstart;
    ImageView ball;Handler handler = new Handler();
    //ImageView[][] bricks = new ImageView[9][9];
         ImageView[] bricks1 = new ImageView[3];

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(startchet==-1) {
                int x = (int)plat.getX();
                if(x>200 && respawn==0) {
                    plat.setX(x - 15);
                }
            }
            handler.postDelayed(this, 20);
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
            handler2.postDelayed(this, 20);
        }
    };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for (int i = 0; i < 81; i++) {
        for(int i =0; i< 3;i++){
            int id = getResources().getIdentifier("a" + i, "id", getPackageName());
            bricks1[i] = findViewById(id);
        }
        plat = findViewById(R.id.Platforma);
        plat.setX(plat.getX()+700);
        platstart=(int)plat.getX();
         ball = findViewById(R.id.ballid);
        ball.setX(1180);
        ballstart=(int)ball.getY();
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
                    if(ball.getX() == 190) {
                        ballx = -ballx;
                    }
                    if(ball.getX() == 2230){
                        ballx = -ballx;
                    }
                    if(ball.getY() <= 0) {
                        bally = -bally;
                    }
                    if(ball.getY() + ball.getHeight() >= plat.getY() && ball.getY() <= plat.getY() + plat.getHeight() && ball.getX() + ball.getWidth() >= plat.getX() && ball.getX() <= plat.getX() + plat.getWidth()) {
                        bally = -bally;
                    }
                    if(ball.getY()>=plat.getY())
                    {
                        upal();
                    }
                }
                handler12.postDelayed(this, 20);
            }
        };
        handler12.postDelayed(runnable12, 20);

    }
    public void upal() {
        respawn=1;
        ConstraintLayout cl = findViewById(R.id.rella);
        ball.setX(1210);ball.setY(1200);plat.setX(platstart+300);
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

}
