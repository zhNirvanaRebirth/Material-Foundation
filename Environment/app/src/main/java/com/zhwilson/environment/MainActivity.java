package com.zhwilson.environment;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView enterExit;
    private TextView shapeChange;
    private TextView revealEffect;
    private TextView cardFlip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterExit = findViewById(R.id.enter_and_exit);
        enterExit.setOnClickListener(this);

        shapeChange = findViewById(R.id.shape_change);
        shapeChange.setOnClickListener(this);

        revealEffect = findViewById(R.id.reveal_effect);
        revealEffect.setOnClickListener(this);


        cardFlip = findViewById(R.id.card_flip);
        cardFlip.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.enter_and_exit:
                intent = new Intent(MainActivity.this, EnterAndExitActivity.class);
                break;
            case R.id.shape_change:
                intent = new Intent(MainActivity.this, ShapeChangeActivity.class);
                break;
            case R.id.reveal_effect:
                intent = new Intent(MainActivity.this, RevealEffectActivity.class);
                break;
            case R.id.card_flip:
                intent = new Intent(MainActivity.this, CardFlipActivity.class);
                break;
        }
        startActivity(intent);
    }
}
