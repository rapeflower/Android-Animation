package com.lily.animation;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.lily.animation.view.AnimationOne;
import com.lily.animation.view.AnimationTwo;

public class CustomAnimViewActivity extends Activity {

    private static final String TAG = CustomAnimViewActivity.class.getSimpleName();
    AnimationOne animationOne;
    AnimationTwo animationTwo;
    ImageView imgHand;
    ScaleAnimation scaleAnimationFirst;
    ScaleAnimation scaleAnimationSecond;
    AnimationDrawable frameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_anim_view);

        animationOne = (AnimationOne) findViewById(R.id.anim_one_view);
        animationTwo = (AnimationTwo) findViewById(R.id.anim_two_view);
        imgHand = (ImageView) findViewById(R.id.img_hand);

        initAnimation();
        playScaleAnimationFirst();
    }

    /**
     * 初始化需要执行的动画
     */
    private void initAnimation() {
        scaleAnimFirst();
        scaleAnimSecond();
        frameAnimation();
    }

    private void scaleAnimFirst() {
        /** 设置缩放动画 */
        scaleAnimationFirst =new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationFirst.setDuration(1000);//设置动画持续时间
        scaleAnimationFirst.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        scaleAnimationFirst.setStartOffset(1000);//执行前的等待时间
        scaleAnimationFirst.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                android.util.Log.w(TAG, "--------------> First onAnimationEnd");
                playScaleAnimationSecond();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void scaleAnimSecond() {
        /** 设置缩放动画 */
        scaleAnimationSecond =new ScaleAnimation(2.0f, 1.8f, 2.0f, 1.8f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationSecond.setDuration(1000);//设置动画持续时间
        scaleAnimationSecond.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        //scaleAnimationSecond.setStartOffset(1000);//执行前的等待时间
        scaleAnimationSecond.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                android.util.Log.w(TAG, "--------------> Second onAnimationEnd");
                playFrameAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void frameAnimation() {
        frameAnimation = new AnimationDrawable();
        try {
            for (int i = 2; i >= 0; i--) {
                int id = getResources().getIdentifier("img_" + i, "drawable", getPackageName());//-hdpi
                Drawable drawable = getResources().getDrawable(id);
                frameAnimation.addFrame(drawable, 200);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        frameAnimation.setOneShot(true);
        imgHand.setImageDrawable(frameAnimation);
    }

    /**
     * 开始播放缩放动画(第一段)
     */
    private void playScaleAnimationFirst() {
        animationOne.clearAnimation();
        animationOne.startAnimation(scaleAnimationFirst);
    }

    /**
     * 开始播放缩放动画(第二段)
     */
    private void playScaleAnimationSecond() {
        animationOne.clearAnimation();
        animationOne.startAnimation(scaleAnimationSecond);
    }

    /**
     * 开始播放贞动画
     */
    private void playFrameAnimation() {
        imgHand.setVisibility(View.VISIBLE);
        if (!frameAnimation.isRunning()) {
            frameAnimation.start();
        }
    }

    /**
     * 重置贞动画
     */
    private void resetFrameAnimation() {
        frameAnimation.stop();
        imgHand.setVisibility(View.GONE);
    }

    /**
     * 播放动画
     */
    public void startAnim(View view) {
        playScaleAnimationFirst();
        resetFrameAnimation();
    }
}
