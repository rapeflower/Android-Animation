package com.lily.anim.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @Author rape flower
 * @Date 2018-08-28 16:59
 * @Describe 自定义view实现路径绘制动画
 */
public class PathView extends View {

    private Paint mPaint;
    private Path mCirclePath;
    private Path mDstPath;
    private PathMeasure mPathMeasure;
    private float mPathLength;
    private ValueAnimator mValueAnimator;
    private float mAnimatedValue;


    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        //完整的圆的路径
        mCirclePath = new Path();
        //路径绘制每段截取出来的路径
        mDstPath = new Path();

        mCirclePath.addCircle(0, 0, 200, Path.Direction.CW);
        //路径测量类
        mPathMeasure = new PathMeasure();
        //测量路径
        mPathMeasure.setPath(mCirclePath, true);
        //获取被测量路径的长度
        mPathLength = mPathMeasure.getLength();

        //动画
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取从0-1的变化值
                mAnimatedValue = (float) animation.getAnimatedValue();
                //不断刷新绘制，实现路径绘制
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //必须要有lineTo(0,0)才可以实现路径的完整绘制
        mDstPath.reset();
        mDstPath.lineTo(0, 0);
        float startD = 0;
        float stopD = mAnimatedValue * mPathLength;

        startD = (float) (stopD - ((0.5 - Math.abs(mAnimatedValue - 0.5)) * mPathLength));

        //获取当前进度的路径，同时赋值给传入的mDstPath
        mPathMeasure.getSegment(startD, stopD, mDstPath, true);

        canvas.save();
        canvas.translate(300, 300);
        canvas.drawPath(mDstPath, mPaint);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 开始动画
     */
    public void startAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.start();
        }
    }

    /**
     * 暂停动画
     */
    @SuppressLint("NewApi")
    public void pauseAnim() {
        if (mValueAnimator != null && mValueAnimator.isStarted() && mValueAnimator.isRunning()) {
            mValueAnimator.pause();
        }
    }

    /**
     * 取消动画
     */
    public void cancelAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator.removeAllUpdateListeners();
        }
    }
}
