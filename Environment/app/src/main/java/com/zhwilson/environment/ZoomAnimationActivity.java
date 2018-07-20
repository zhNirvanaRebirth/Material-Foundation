package com.zhwilson.environment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ZoomAnimationActivity extends AppCompatActivity {
    private ImageButton zoomSmall;
    private Animator animator;
    private int shortAnimTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_animation);
        zoomSmall = findViewById(R.id.zoom_small);
        zoomSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImage(zoomSmall, R.drawable.image);
            }
        });
        shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    private void zoomImage(final View smallView, int resId) {
        if (animator != null) animator.cancel();

        final ImageView largeView = findViewById(R.id.zoom_large);
        largeView.setImageResource(resId);

        final Rect smallRect = new Rect();
        Rect largeRect = new Rect();
        Point globalOffset = new Point();

        smallView.getGlobalVisibleRect(smallRect);
        findViewById(R.id.container).getGlobalVisibleRect(largeRect, globalOffset);
        smallRect.offset(-globalOffset.x, -globalOffset.y);
        largeRect.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float)largeRect.width()/largeRect.height() > (float) smallRect.width()/smallRect.height()) {
            startScale = (float) smallRect.height()/largeRect.height();
            float startWidth = startScale*largeRect.width();
            float deltaWidth = (startWidth - smallRect.width())/2;
            smallRect.left -= deltaWidth;
            smallRect.right += deltaWidth;
        } else {
            startScale = (float)smallRect.width()/largeRect.width();
            float startHeight = startScale*largeRect.height();
            float deltaHeight = (startHeight - smallRect.height())/2;
            smallRect.top -= deltaHeight;
            smallRect.bottom += deltaHeight;
        }

        smallView.setAlpha(0f);
        largeView.setVisibility(View.VISIBLE);
        largeView.setPivotX(0);
        largeView.setPivotY(0);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(largeView, View.X, smallRect.left, largeRect.left))
                .with(ObjectAnimator.ofFloat(largeView, View.Y, smallRect.top, largeRect.top))
                .with(ObjectAnimator.ofFloat(largeView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(largeView, View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimTime);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                animator = null;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animator = null;
            }
        });
        set.start();
        animator = set;

        final float startScaleFinal = startScale;
        largeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (animator != null) animator.cancel();

                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator.ofFloat(largeView, View.X, smallRect.left))
                        .with(ObjectAnimator.ofFloat(largeView, View.Y, smallRect.top))
                        .with(ObjectAnimator.ofFloat(largeView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(largeView, View.SCALE_Y, startScaleFinal));
                set.setDuration(shortAnimTime);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        smallView.setAlpha(1.0f);
                        largeView.setVisibility(View.INVISIBLE);
                        animator = null;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        smallView.setAlpha(1.0f);
                        largeView.setVisibility(View.INVISIBLE);
                        animator = null;
                    }
                });
                set.start();
                animator = set;
            }
        });
    }
}
