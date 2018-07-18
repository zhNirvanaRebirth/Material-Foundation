package com.zhwilson.environment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EnterAndExitActivity extends AppCompatActivity {
    private RelativeLayout material;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_and_exit);
        material = findViewById(R.id.material);
        ObjectAnimator alphaEnter = ObjectAnimator.ofFloat(material, "alpha", 0.0f, 1.0f);
        alphaEnter.setDuration(1000);
        ObjectAnimator alphaExit = ObjectAnimator.ofFloat(material, "alpha", 1.0f, 0.0f);
        alphaExit.setDuration(1000);
        int height = getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator transEnter = ObjectAnimator.ofFloat(material, "translationY", height*1.0f/2 + dp2px(200), 0);
        transEnter.setInterpolator(new AccelerateInterpolator());
        transEnter.setDuration(1000);
        ObjectAnimator transExit = ObjectAnimator.ofFloat(material, "translationY", 0, height*1.0f/2 + dp2px(200));
        transExit.setInterpolator(new DecelerateInterpolator());
        transExit.setDuration(1000);
        AnimatorSet alphaAnimatorSet = new AnimatorSet();
        alphaAnimatorSet.play(alphaExit).after(1000).after(alphaEnter);
        alphaAnimatorSet.setStartDelay(2000);

        final AnimatorSet transAnimatorSet = new AnimatorSet();
        transAnimatorSet.play(transExit).after(2000).after(transEnter);
        alphaAnimatorSet.setStartDelay(2000);

        alphaAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                material.setAlpha(1.0f);
                transAnimatorSet.start();
            }
        });
        alphaAnimatorSet.start();
    }

    private float dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return dp*density + 0.5f;
    }
}
