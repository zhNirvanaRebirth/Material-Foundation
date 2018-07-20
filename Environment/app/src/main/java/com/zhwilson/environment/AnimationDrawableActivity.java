package com.zhwilson.environment;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AnimationDrawableActivity extends AppCompatActivity {
    private AnimationDrawable animationDrawable;
    private ImageView drawableAnimation;
    private ImageView vectorAnimation;
    private AnimatedVectorDrawable animatedVectorDrawable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_drawable);
        drawableAnimation = findViewById(R.id.drawable_animation);
        animationDrawable = (AnimationDrawable) drawableAnimation.getBackground();
        drawableAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationDrawable.start();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vectorAnimation = findViewById(R.id.vector_animation);
            animatedVectorDrawable = (AnimatedVectorDrawable) vectorAnimation.getBackground();
            vectorAnimation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animatedVectorDrawable.start();
                }
            });
        }
    }
}
