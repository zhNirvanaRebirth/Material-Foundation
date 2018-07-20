package com.zhwilson.environment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 揭示效果
 */
public class RevealEffectActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout effectOne;
    private TextView crossfade;
    private ProgressBar progressBar;
    private TextView circular;
    private int shorAnimTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setEnterTransition(new Fade());
        setContentView(R.layout.activity_reveal_effect);

        effectOne = findViewById(R.id.effect_one);
        effectOne.setOnClickListener(this);

        crossfade = findViewById(R.id.crossfade);
        crossfade.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progress_bar);

        circular = findViewById(R.id.circular);

        shorAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);
        crossfade();
    }

    private void crossfade(){
        crossfade.setAlpha(0);
        crossfade.setVisibility(View.VISIBLE);

        crossfade.animate().alpha(1).setDuration(3000).start();

        progressBar.animate().alpha(0).setDuration(3000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBar.setVisibility(View.GONE);
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.effect_one:
                circularReveal();
                break;
        }
    }

    private void circularReveal(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = circular.getWidth()/2;
            int cy = circular.getHeight()/2;

            float finalRedius = (float) Math.hypot(cx, cy);
            Animator animator = null;
            if (circular.getVisibility() == View.INVISIBLE) {
                animator = ViewAnimationUtils.createCircularReveal(circular, cx, cy, 0, finalRedius);
                circular.setVisibility(View.VISIBLE);
            } else {
                animator = ViewAnimationUtils.createCircularReveal(circular, cx, cy, finalRedius, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        circular.setVisibility(View.INVISIBLE);
                    }
                });
            }
            animator.start();
        }
    }
}
