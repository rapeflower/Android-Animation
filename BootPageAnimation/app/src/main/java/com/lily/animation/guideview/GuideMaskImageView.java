package com.lily.animation.guideview;

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
import com.lily.animation.model.Point;


/**
 * @author rape flower
 * @Date 2017-06-19 14:21
 * @descripe 带遮罩效果同时有偏移（translationY）动画的ImageView
 */
public class GuideMaskImageView extends ImageView{

    private Paint mPaint;
    private Point currentPoint;
    private boolean isNeedDraw = false;
    private long duration = 0;
    Bitmap originBitmap = null;
    Bitmap newBitmap = null;
    float startX = Float.NaN;//动画开始点x坐标
    float startY = Float.NaN;//动画开始点y坐标
    float endX = Float.NaN;//动画结束点x坐标
    float endY = Float.NaN;//动画结束点y坐标

    public GuideMaskImageView(Context context) {
        this(context, null);
    }

    public GuideMaskImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideMaskImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        //计算绘制的起始点坐标
        startX = (float) (getWidth() - newBitmap.getWidth()) / 2;
        startY = (float) (getHeight() / 2 + newBitmap.getHeight() / 2);
        //计算绘制的结束点坐标
        endX = startX;
        endY = (float) (getHeight() / 2 - newBitmap.getHeight() / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setLayerType(LAYER_TYPE_HARDWARE, null);
        if (isNeedDraw) {
//            android.util.Log.w(TAG, "getWidth = " + newBitmap.getWidth());
//            android.util.Log.w(TAG, "getHeight = " + newBitmap.getHeight());

            if (currentPoint == null) {
                currentPoint = new Point(startX, startY);
                drawBitmap(canvas);
                startAnimation();
            } else {
                drawBitmap(canvas);
            }
        }
    }

    /**
     * 在Canvas上绘制图片
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
//        float left = (float) (getWidth() - newBitmap.getWidth()) / 2;
//        float top = (float) getHeight() / 2 - newBitmap.getHeight() / 3;
        float left = currentPoint.getX();
        float top = currentPoint.getY();
        canvas.drawBitmap(newBitmap, left, top, mPaint);
    }

    /**
     * 开始偏移（translationY）动画
     * 在Y轴方向向上移动
     */
    private void startAnimation() {
        Point startPoint = new Point(startX, startY);
        Point endPoint = new Point(endX, endY);

        //ObjectAnimator animator = ObjectAnimator.ofObject(TypeEvaluator evaluator, Object... values)
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(duration);
        anim.start();
    }

    /**
     * 开始绘制手机图片，带动画效果
     * @param isNeedDraw
     */
    public void startDrawMobilePhonePic(boolean isNeedDraw, long duration) {
        this.isNeedDraw = isNeedDraw;
        this.duration = duration;
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
