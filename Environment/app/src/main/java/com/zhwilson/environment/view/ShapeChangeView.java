package com.zhwilson.environment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ShapeChangeView extends View {
    public static final float REDIUS = 200;
    private Paint paint;
    private int width, height;
    private Path path = new Path();
    private Shape shape = Shape.RECT;
    private float quadLength = 0;
    private float octagonInc = 0;
    public ShapeChangeView(Context context) {
        this(context, null);
    }

    public ShapeChangeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeChangeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width*1.0f/2, height*1.0f/2);
        canvas.scale(1, -1);
        drawShape(canvas);
    }

    private void drawShape(Canvas canvas) {
        path.reset();
        switch (shape) {
            case RECT:
                path.moveTo(0, REDIUS);
                path.lineTo(REDIUS, 0);
                path.lineTo(0, -REDIUS);
                path.lineTo(-REDIUS, 0);
                path.close();
                break;
            case CIRCLE:
                path.moveTo(0, REDIUS);
                path.cubicTo(quadLength, REDIUS, REDIUS, quadLength, REDIUS, 0);
                path.cubicTo(REDIUS, -quadLength, quadLength, -REDIUS, 0, -REDIUS);
                path.cubicTo(-quadLength, -REDIUS, -REDIUS, -quadLength, -REDIUS, 0);
                path.cubicTo(-REDIUS, quadLength, -quadLength, REDIUS, 0, REDIUS);
                break;
            case OCTAGON:
                float shortAxis = (float) ((REDIUS + octagonInc)*Math.sin(Math.PI/6));
                float longAxis = (float) ((REDIUS + octagonInc)*Math.cos(Math.PI/6));
                path.lineTo(-shortAxis, longAxis);
                path.quadTo(0, REDIUS, shortAxis, longAxis);
                path.lineTo(longAxis, shortAxis);
                path.quadTo(REDIUS, 0, longAxis, -shortAxis);
                path.lineTo(shortAxis, -longAxis);
                path.quadTo(0, -REDIUS, -shortAxis, -longAxis);
                path.lineTo(-longAxis, -shortAxis);
                path.quadTo(-REDIUS, 0, -longAxis, shortAxis);
                path.lineTo(-shortAxis, longAxis);
                break;
        }
        canvas.drawPath(path, paint);
    }

    public enum Shape {
        RECT, CIRCLE, OCTAGON, BIGRECT
    }

    public float getQuadLength() {
        return quadLength;
    }

    public void setQuadLength(float quadLength) {
        this.quadLength = quadLength;
        invalidate();
    }

    public float getOctagonInc() {
        return octagonInc;
    }

    public void setOctagonInc(float octagonInc) {
        this.octagonInc = octagonInc;
        invalidate();
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
