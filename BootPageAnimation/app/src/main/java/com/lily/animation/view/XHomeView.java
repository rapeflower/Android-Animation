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
public class XHomeView extends View{

    private static final String TAG = XHomeView.class.getSimpleName();
    private Paint mBasePaint;
    private float centerX = Float.NaN;
    private float centerY = Float.NaN;

    public XHomeView(Context context) {
        this(context, null);
    }

    public XHomeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XHomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boot_page_img_home);
        float scale = (float) 0.5;
        Bitmap newBitmap = scaleBitmap(bitmap, scale);

        float left = (float) (getWidth() - newBitmap.getWidth()) / 2;
        float top = (float) (getHeight() - newBitmap.getHeight()) / 2;
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
