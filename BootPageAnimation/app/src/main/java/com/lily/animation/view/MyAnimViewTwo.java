package com.lily.animation.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lily.animation.model.Point;

import static android.content.ContentValues.TAG;

/**
 * Created by lilei on 2017/6/22.
 */

public class MyAnimViewTwo extends View{


    public static final float RADIUS = 50f;

    private Point currentPoint;

    private Paint mPaint;

    public MyAnimViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation() {
        Point startPoint = new Point(getWidth() / 2, getHeight() / 2);
        Point endPoint = new Point(getWidth() / 2, getHeight() / 3);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(2000);
        anim.start();
    }

    public class PointEvaluator implements TypeEvaluator{

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            android.util.Log.w("PointEvaluator", "fraction = " + fraction);
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            float x = startPoint.getX();// startPoint.getX() = endPoint.getX()
            float y = startPoint.getY() - fraction * (startPoint.getY() - endPoint.getY());
            Point point = new Point(x, y);
            return point;
        }

    }
}
