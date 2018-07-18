package com.zhwilson.environment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.RequiresApi;

public class ShapeChangeView extends View {
    public static final float REDIUS = 200;
    private static float QUAD = (float) (ShapeChangeView.REDIUS * Math.tan(Math.PI / 6));
    private Paint paint;
    private int width, height;
    private Path path = new Path();
    private Path opPath = new Path();
    private Path octagonOpPath = new Path();
    private Path octagonPath = new Path();
    private Shape shape = Shape.RECT;
    private float quadLength = 0;
    private float octagonInc = 0;
    private float rectInc = 0;
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
        Log.d("zhwilson", "onMeasure");
        setMeasuredDimension(getMeasureDimension(widthMeasureSpec), getMeasureDimension(heightMeasureSpec));
    }

    private int getMeasureDimension(int measureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        if (mode == MeasureSpec.EXACTLY) result = size;
        else result = (int) (REDIUS*2);
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("zhwilson", "onSizeChanged");
        width = w;
        height = h;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) setOutlineProvider(new ShadowOutline(w, h));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class ShadowOutline extends ViewOutlineProvider {
        int width, height;
        public ShadowOutline(int width, int height) {
            this.width = width;
            this.height = height;
        }
        @Override
        public void getOutline(View view, Outline outline) {
//            outline.setRect(0, 0, width, height);
            outline.setRoundRect(0, 0, width, height, 20);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("zhwilson", "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("zhwilson", "onDraw");
//        canvas.drawARGB(255, 255, 0, 0);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.translate(width*1.0f/2, height*1.0f/2);
        canvas.scale(1, -1);
        drawShape(canvas);
    }

    private void drawShape(Canvas canvas) {
        path.reset();
        switch (shape) {
            case RECT:
                path.moveTo(REDIUS - rectInc, REDIUS);
                path.lineTo(REDIUS, REDIUS - rectInc);
                path.lineTo(REDIUS, -(REDIUS - rectInc));
                path.lineTo(REDIUS - rectInc, -REDIUS);
                path.lineTo(-(REDIUS - rectInc), -REDIUS);
                path.lineTo(-REDIUS, -(REDIUS - rectInc));
                path.lineTo(-REDIUS, REDIUS - rectInc);
                path.lineTo(-(REDIUS - rectInc), REDIUS);
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
                if (Build.VERSION.SDK_INT >= 19) {
                    opPath.reset();
                    opPath.moveTo(0, REDIUS);
                    opPath.cubicTo(QUAD, REDIUS, REDIUS, QUAD + octagonInc*(1 + octagonInc*0.1f/QUAD), REDIUS, 0);
                    opPath.lineTo(0, 0);
                    opPath.close();
                    octagonOpPath.reset();
                    octagonOpPath.addRect(0, REDIUS, QUAD, 0, Path.Direction.CW);
                    opPath.op(octagonOpPath, Path.Op.INTERSECT);
                    path.addPath(opPath);

                    opPath.reset();
                    opPath.moveTo(0, REDIUS);
                    opPath.cubicTo(QUAD + octagonInc*(1 + octagonInc*0.1f/QUAD), REDIUS, REDIUS, QUAD, REDIUS, 0);
                    opPath.lineTo(0, 0);
                    opPath.close();
                    octagonOpPath.reset();
                    octagonOpPath.addRect(0, QUAD, REDIUS, 0, Path.Direction.CW);
                    opPath.op(octagonOpPath, Path.Op.INTERSECT);
                    path.op(opPath, Path.Op.UNION);

                    opPath.reset();
                    float dataInc = (QUAD*0.05f)*(octagonInc/QUAD) + octagonInc;
                    dataInc = dataInc > QUAD ? QUAD : dataInc;
                    opPath.moveTo(dataInc, REDIUS);
                    opPath.cubicTo(QUAD, REDIUS, REDIUS, QUAD, REDIUS, dataInc);
                    opPath.lineTo(0, 0);
                    opPath.close();
                    octagonOpPath.reset();
                    octagonOpPath.moveTo(0, 0);
                    octagonOpPath.lineTo(QUAD, REDIUS);
                    octagonOpPath.lineTo(REDIUS, QUAD);
                    octagonOpPath.close();
                    opPath.op(octagonOpPath, Path.Op.INTERSECT);
                    path.op(opPath, Path.Op.UNION);

                    Matrix matrix = new Matrix();
                    for (int i = 0; i < 3; i++) {
                        matrix.preRotate(90);
                        path.transform(matrix, octagonPath);
                        path.op(octagonPath, Path.Op.UNION);
                    }
                }
                break;
            case BIGRECT:
                path.moveTo(QUAD + rectInc, REDIUS);
                path.lineTo(REDIUS, QUAD + rectInc);
                path.lineTo(REDIUS, -(QUAD + rectInc));
                path.lineTo(QUAD + rectInc, -REDIUS);
                path.lineTo(-(QUAD + rectInc), -REDIUS);
                path.lineTo(-REDIUS, -(QUAD + rectInc));
                path.lineTo(-REDIUS, QUAD + rectInc);
                path.lineTo(-(QUAD + rectInc), REDIUS);
                path.close();
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

    public float getRectInc() {
        return rectInc;
    }

    public void setRectInc(float rectInc) {
        this.rectInc = rectInc;
        invalidate();
    }
}
