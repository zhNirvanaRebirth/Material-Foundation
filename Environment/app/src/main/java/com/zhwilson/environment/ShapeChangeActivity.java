package com.zhwilson.environment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.zhwilson.environment.view.ShapeChangeView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShapeChangeActivity extends AppCompatActivity {
    private ShapeChangeView shapeChangeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_change);
        shapeChangeView = findViewById(R.id.shape_change);
//        shapeChangeView.setClipToOutline(true);
        float quadLength = (float) (ShapeChangeView.REDIUS * Math.tan(Math.PI / 6));
        float octagonInc = quadLength;
        float rectInc = ShapeChangeView.REDIUS - quadLength;

        ObjectAnimator originAnimator = ObjectAnimator.ofFloat(shapeChangeView, "rectInc", 0, ShapeChangeView.REDIUS);
        originAnimator.setInterpolator(new DecelerateInterpolator());
        originAnimator.setDuration(300);
        originAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                shapeChangeView.setShape(ShapeChangeView.Shape.RECT);
            }
        });

        ObjectAnimator circleAnimator = ObjectAnimator.ofFloat(shapeChangeView, "quadLength", 0, quadLength);
        circleAnimator.setInterpolator(new DecelerateInterpolator());
        circleAnimator.setDuration(300);
        circleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                shapeChangeView.setShape(ShapeChangeView.Shape.CIRCLE);
            }
        });

        ObjectAnimator octagonAnimator = ObjectAnimator.ofFloat(shapeChangeView, "octagonInc", 0, octagonInc);
        octagonAnimator.setInterpolator(new DecelerateInterpolator());
        octagonAnimator.setDuration(200);
        octagonAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                shapeChangeView.setShape(ShapeChangeView.Shape.OCTAGON);
            }
        });

        ObjectAnimator rectAnimator = ObjectAnimator.ofFloat(shapeChangeView, "rectInc", 0, rectInc);
        rectAnimator.setInterpolator(new DecelerateInterpolator());
        rectAnimator.setDuration(200);
        rectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                shapeChangeView.setShape(ShapeChangeView.Shape.BIGRECT);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                shapeChangeView.setShape(ShapeChangeView.Shape.RECT);
            }
        });

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(originAnimator);
        animatorSet.play(circleAnimator).after(1000).after(originAnimator);
        animatorSet.play(octagonAnimator).after(2000).after(circleAnimator);
        animatorSet.play(rectAnimator).after(3000).after(octagonAnimator);
        animatorSet.setStartDelay(2000);
//        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }
        });

        //TODO 问题：这里这样写，动画执行过程中，ShapeChangeView的onDraw并没有重新执行，至少没有打印信息，这是为什么？
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(shapeChangeView, "scaleX", 1, 0.1f);
        scaleAnimator.setInterpolator(new DecelerateInterpolator());
        scaleAnimator.setDuration(3000);
        scaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(shapeChangeView.getLayoutParams());
                layoutParams.width = 300;
                layoutParams.height = 300;
                shapeChangeView.setLayoutParams(layoutParams);
            }
        });
//        scaleAnimator.start();

    }
}
