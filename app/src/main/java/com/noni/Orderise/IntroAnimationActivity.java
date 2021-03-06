package com.noni.Orderise;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class IntroAnimationActivity extends AppCompatActivity {


    private ImageView shortblackSteam, shortblackCircle, shortblackCup, shortblackHeart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar ab = this.getSupportActionBar();
            if (ab != null) {
                ab.hide();
            }
        }
        setContentView(R.layout.activity_intro_animation);
        shortblackSteam = (ImageView) findViewById(R.id.shortblack_intro_steam);
        shortblackHeart = (ImageView) findViewById(R.id.shortblack_intro_heart);
        shortblackCup = (ImageView) findViewById(R.id.shortblack_intro_cup);
        shortblackCircle = (ImageView) findViewById(R.id.shortblack_intro_circle);
        heartBeat(shortblackHeart, this);
    }

    private void heartBeat(View v, final Context c) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.heart_beat);
        v.startAnimation(animation);
        v.postOnAnimationDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(c.getApplicationContext(), Orderise.class);
                startActivity(i);
                finish();
            }
        }, 3000l);
    }
}
