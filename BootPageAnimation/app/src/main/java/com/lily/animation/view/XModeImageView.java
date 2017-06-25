package com.lily.animation.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lily.animation.R;

/**
 * Created by Author on 17/6/19.
 */
public class XModeImageView extends ImageView{

    private Paint mPaint;
    private boolean isNeedDraw = false;
    Bitmap originBitmap = null;
    Bitmap newBitmap = null;

    private Point currentPoint;

    public XModeImageView(Context context) {
        this(context, null);
    }

    public XModeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XModeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //设置是否抖动，如果不设置感觉就会有一些僵硬的线条，如果设置图像就会看的更柔和一些，
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boot_page_img_mobile_phone);
        float scale = (float) 0.7;
        newBitmap = scaleBitmap(originBitmap, scale);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setLayerType(LAYER_TYPE_HARDWARE, null);
        if (isNeedDraw) {
//            android.util.Log.w(TAG, "getWidth = " + newBitmap.getWidth());
//            android.util.Log.w(TAG, "getHeight = " + newBitmap.getHeight());

            if (currentPoint == null) {
                float x = (float) (getWidth() - newBitmap.getWidth()) / 2;
                float y = (float) getHeight() / 2 + newBitmap.getHeight() / 2;
                currentPoint = new Point(x, y);
                drawBitmap(canvas);
                startAnimation();
            } else {
                drawBitmap(canvas);
            }
        }
    }

    private void drawBitmap(Canvas canvas) {
//        float left = (float) (getWidth() - newBitmap.getWidth()) / 2;
//        float top = (float) getHeight() / 2 - newBitmap.getHeight() / 3;

        float left = currentPoint.getX();
        float top = currentPoint.getY();

        canvas.drawBitmap(newBitmap, left, top, mPaint);
    }

    private void startAnimation() {
        float x = (float) (getWidth() - newBitmap.getWidth()) / 2;
        Point startPoint = new Point(x, (float) (getHeight() / 2 + newBitmap.getHeight() / 2));

        float endPointY = (float) getHeight() / 2 - newBitmap.getHeight() / 3;
        Point endPoint = new Point(x, endPointY);
        //ObjectAnimator animator = ObjectAnimator.ofObject(TypeEvaluator evaluator, Object... values)
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(300);
        anim.start();
    }

    public class PointEvaluator implements TypeEvaluator {

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

    /**
     *
     * @param isNeedDraw
     */
    public void startDrawMobilePhonePic(boolean isNeedDraw, int parameter) {
        this.isNeedDraw = isNeedDraw;
        invalidate();
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
