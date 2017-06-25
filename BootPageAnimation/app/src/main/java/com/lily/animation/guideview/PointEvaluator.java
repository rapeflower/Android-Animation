package com.lily.animation.guideview;

import android.animation.TypeEvaluator;

/**
 * @author rape flower
 * @Date 2017-06-22 14:18
 * @descripe 坐标点估值器
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        android.util.Log.w("PointEvaluator", "fraction = " + fraction);
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX();//startPoint.getX() = endPoint.getX()
        float y = startPoint.getY() - fraction * (startPoint.getY() - endPoint.getY());
        Point point = new Point(x, y);
        return point;
    }
}
