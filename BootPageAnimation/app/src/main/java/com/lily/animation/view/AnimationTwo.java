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
public class AnimationTwo extends View{

    private static final String TAG = AnimationTwo.class.getSimpleName();
    private Paint mBasePaint;
    private float circleRingRadius = Float.NaN;
    private float centerX = Float.NaN;
    private float centerY = Float.NaN;

    public AnimationTwo(Context context) {
        this(context, null);
    }

    public AnimationTwo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBasePicLine(canvas);
    }

    /**
     * 画线
     */
    private void drawBasePicLine(Canvas canvas) {
        drawTopObliqueLine(canvas);
        drawBottomObliqueLine(canvas);
    }

    private void drawTopObliqueLine(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_22);
        float scale = (float) 0.4;
        Bitmap newBitmap = scaleBitmap(bitmap, scale);

        float left = (float) getWidth() * 5 / 6;
        float top = (float) getHeight() / 8;
        canvas.drawBitmap(newBitmap, left, top, mBasePaint);
    }

    private void drawBottomObliqueLine(Canvas canvas)  {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_22);
        float scale = (float) 0.4;
        Bitmap newBitmap = scaleBitmap(bitmap, scale);

        float left = (float) getWidth() / 3;
        float top = (float) getHeight() * 3 / 4;
        canvas.drawBitmap(newBitmap, left, top, mBasePaint);
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
