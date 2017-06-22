package com.lily.animation.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.lily.animation.R;

/**
 * Created by lilei on 2017/6/22.
 */
public class AnimView extends View{

    private BitmapDrawable mDrawable;

    private int mViewWidth;
    private int mViewHeight;
    private long mStartX;
    private long mEndX;
    private long mStartY;
    private long mEndY;

    public AnimView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewWidth = w;
        mViewHeight = h;
        init();
    }

    private void init() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boot_page_img_global_purchase);
        mDrawable = new BitmapDrawable(bitmap);
        mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());

        mStartX = mViewWidth / 2;
        // 右移200px
        mEndX = mStartX + 200;
        mStartY = mViewHeight / 2;
        mEndY = mStartY;
    }

    private long mStartTime = -1;
    private long mStartOffset = 1000;
    private long mDuration = 500;

    @Override
    protected void onDraw(Canvas canvas) {
        // 初始化时间值
        if (mStartTime == -1) {
            mStartTime = SystemClock.uptimeMillis();
        }
        long curTime = SystemClock.uptimeMillis();
        boolean done = true;

        // t为一个0到1均匀变化的值
        float t = (curTime - mStartTime - mStartOffset) / (float) mDuration;
        t = Math.max(0, Math.min(t, 1));
        int translateX = (int) lerp(mStartX, mEndX, t);
        int translateY = (int) lerp(mStartY, mEndY, t);
        android.util.Log.w("AnimView", "t = " + t);
        if (t < 1) {
            done = false;
        }
        if (0 < t && t <= 1) {
            done = false;
            // 保存画布，方便下次绘制
            canvas.save();
//            canvas.translate(translateX, translateY);
            canvas.translate(0, translateX);
            mDrawable.draw(canvas);
            canvas.restore();
        }

        android.util.Log.w("AnimView", "done = " + done);
        if (!done) {
            invalidate();
        }
    }

    // 数制差
    float lerp(float start, float end, float t) {
        return start + (end - start) * t;
    }
}
