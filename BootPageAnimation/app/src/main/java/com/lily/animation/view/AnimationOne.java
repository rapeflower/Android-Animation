package com.lily.animation.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lily.animation.R;

/**
 * Created by rape flower on 2017/6/18.
 */
public class AnimationOne extends View{

    private static final String TAG = AnimationOne.class.getSimpleName();
    private Paint mBasePaint;
    private float circleRingRadius = Float.NaN;
    private float centerX = Float.NaN;
    private float centerY = Float.NaN;

    public AnimationOne(Context context) {
        this(context, null);
    }

    public AnimationOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBasePaint = new Paint();
        mBasePaint.setColor(Color.WHITE);//设置白色
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        android.util.Log.w(TAG, "getWidth = " + getWidth());
        android.util.Log.w(TAG, "getHeight = " + getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBasePicOne(canvas);
        drawBasePicTwo(canvas);
        drawBasePicThree(canvas);
    }

    /**
     * 画圆环图片
     */
    private void drawBasePicOne(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_6);
        float scale = (float) 0.5;
        Bitmap newBitmap = scaleBitmap(bitmap, scale);

        //
        circleRingRadius = newBitmap.getWidth() / 2;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        float left = (float) (getWidth() - newBitmap.getWidth()) / 2;
        float top = (float) (getHeight() - newBitmap.getHeight()) / 2;
        canvas.drawBitmap(newBitmap, left, top, mBasePaint);
    }

    /**
     * 画中心圆图片
     */
    private void drawBasePicTwo(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_31);
        float scale = (float) 0.5;
        Bitmap newBitmap = scaleBitmap(bitmap, scale);

        float left = (float) (getWidth() - newBitmap.getWidth()) / 2;
        float top = (float) (getHeight() - newBitmap.getHeight()) / 2;
        canvas.drawBitmap(newBitmap, left, top, mBasePaint);
    }

    /**
     * 画线条图片
     */
    private void drawBasePicThree(Canvas canvas) {
        drawTopObliqueLine(canvas);
        drawBottomObliqueLine(canvas);
        drawLeftObliqueLine(canvas);
    }

    /**
     * 上
     * @param canvas
     */
    private void drawTopObliqueLine(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_22);
        int height = bitmap.getHeight();

        float cosX = (float) Math.cos(45) * circleRingRadius;
        float sinY = (float) Math.sin(45) * circleRingRadius;
        float paraX = circleRingRadius / 7;
        float paraY = circleRingRadius / 18;
        android.util.Log.w(TAG, "paraX = " + paraX);
        android.util.Log.w(TAG, "paraY = " + paraY);

        float left = centerX + cosX + paraX;
        float top = centerY - sinY - height - paraY;
        canvas.drawBitmap(bitmap, left, top, mBasePaint);
    }

    /**
     * 下
     * @param canvas
     */
    private void drawBottomObliqueLine(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_22);
        //int width = bitmap.getWidth();
        //int height = bitmap.getHeight();

        float sinX = (float) Math.sin(45) * circleRingRadius;
        //float cosY = (float) Math.cos(45) * circleRingRadius;
        float paraX = circleRingRadius / 22 ;
        float paraY = circleRingRadius + circleRingRadius / 40;
        android.util.Log.w(TAG, "paraX = " + paraX);
        android.util.Log.w(TAG, "paraY = " + paraY);

        float left = centerX - sinX + paraX;
        float top = centerY + paraY;
        canvas.drawBitmap(bitmap, left, top, mBasePaint);
    }

    /**
     * 左
     * @param canvas
     */
    private void drawLeftObliqueLine(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_28);
        int width = bitmap.getWidth();
        //int height = bitmap.getHeight();

        float paraX = circleRingRadius + width + circleRingRadius / 11;
        float paraY = circleRingRadius / 9;
        android.util.Log.w(TAG, "paraX = " + paraX);
        android.util.Log.w(TAG, "paraY = " + paraY);

        float left = centerX - paraX;
        float top = centerY - paraY;
        canvas.drawBitmap(bitmap, left, top, mBasePaint);
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();

        return newBM;
    }

}
