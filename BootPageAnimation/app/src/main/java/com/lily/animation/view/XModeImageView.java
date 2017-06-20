package com.lily.animation.view;

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

import static android.content.ContentValues.TAG;

/**
 * Created by Author on 17/6/19.
 */
public class XModeImageView extends ImageView{

    private Paint mPaint;
    private int param = 0;
    private boolean isNeedDraw = false;
    Bitmap originBitmap = null;
    Bitmap newBitmap = null;
    float dy = Float.NaN;
    boolean mAttached = false;

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
        //
        dy = getHeight() / 2 - 100;
        android.util.Log.w(TAG, "dy 1 = " + dy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setLayerType(LAYER_TYPE_HARDWARE, null);
        if (isNeedDraw) {

            android.util.Log.w(TAG, "getWidth = " + newBitmap.getWidth());
            android.util.Log.w(TAG, "getHeight = " + newBitmap.getHeight());

            float left = (float) (getWidth() - newBitmap.getWidth()) / 2;
            float top = (float) getHeight() / 2 - newBitmap.getHeight() / 3;
            android.util.Log.w(TAG, "dy 2 = " + dy);
//            canvas.translate(0, dy);
            canvas.drawBitmap(newBitmap, left, top, mPaint);
        }
    }

//    private void startAnim() {
//        ScaleAnimation scaleAnimationFirst = new ScaleAnimation(0.0f, 0.9f, 0.0f, 0.9f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimationFirst.setDuration(1000);//设置动画持续时间
//        scaleAnimationFirst.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        scaleAnimationFirst.setStartOffset(1000);//执行前的等待时间
//    }

    /**
     *
     * @param isNeedDraw
     */
    int base = 100;
    public void startDrawMobilePhonePic(boolean isNeedDraw, int parameter) {
        this.isNeedDraw = isNeedDraw;
        this.param = parameter;

//        if (mAttached) {
//            post(r);
//        } else {
//            Handler handler = new Handler();
//            handler.post(r);
//        }
        invalidate();
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            android.util.Log.w(TAG, "base 1 = " + base);
            if (base > 200) {
                return;
            }
            base = base + 20;
            android.util.Log.w(TAG, "base 2 = " + base);
            dy = getHeight() / 2 - base;
        }
    };

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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAttached = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAttached = false;
    }
}
