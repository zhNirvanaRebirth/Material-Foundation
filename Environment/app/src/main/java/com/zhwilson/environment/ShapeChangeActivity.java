package com.zhwilson.environment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;

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
        shapeChangeView.setShape(ShapeChangeView.Shape.CIRCLE);
        float quadLength = (float) (ShapeChangeView.REDIUS * Math.tan(Math.PI / 6));
        float octagonInc = (float) (ShapeChangeView.REDIUS / Math.cos(Math.PI / 6) - ShapeChangeView.REDIUS);
        Log.e("zhwilson", quadLength + "_" + octagonInc);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(shapeChangeView, "quadLength", 0, quadLength);
        objectAnimator.setDuration(2000);

//        final ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(shapeChangeView, "octagonInc", 0, octagonInc);
        octagonInc = 200;
        final ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(shapeChangeView, "quadLength", quadLength, octagonInc);
        objectAnimator2.setDuration(2000);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                shapeChangeView.setShape(ShapeChangeView.Shape.OCTAGON);
//                shapeChangeView.setOctagonInc(0);
                objectAnimator2.setStartDelay(1000);
                objectAnimator2.start();
            }
        });

        objectAnimator.setStartDelay(2000);
        objectAnimator.start();
    }
}
