package com.lily.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.lily.animation.view.XModeImageView;

import static android.content.ContentValues.TAG;

/**
 * Created by Author on 17/6/19.
 */

public class BootPageAnimActivity extends Activity {

    ImageView ivAnimLayerOne;
    XModeImageView ivAnimLayerTwo;
    ImageView ivAnimLayerFour;
    ScaleAnimation scaleAnimationFirst;
    ScaleAnimation scaleAnimationSecond;
    AnimationDrawable frameAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_anim);
        initWidget();
        initAnimation();

        playScaleAnimationFirst();
    }

    /**
     * 初始化
     */
    private void initWidget() {
        ivAnimLayerOne = (ImageView) findViewById(R.id.iv_anim_layer_one);
        ivAnimLayerTwo = (XModeImageView) findViewById(R.id.iv_anim_layer_two);
        ivAnimLayerFour = (ImageView) findViewById(R.id.iv_anim_layer_four);
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
        scaleAnimationFirst =new ScaleAnimation(0.4f, 0.9f, 0.4f, 0.9f,
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
        scaleAnimationSecond =new ScaleAnimation(0.9f, 0.7f, 0.9f, 0.7f,
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
                ivAnimLayerTwo.startDrawMobilePhonePic(true, param);
                playHandAnim();
//                playFrameAnimation();//// TODO: 17/6/19 rape flower
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
//        ivAnimLayerFour.setImageDrawable(frameAnimation);// TODO: 17/6/19
    }

    /**
     * 开始播放缩放动画(第一段)
     */
    private void playScaleAnimationFirst() {
        ivAnimLayerOne.clearAnimation();
        ivAnimLayerOne.startAnimation(scaleAnimationFirst);

        ivAnimLayerTwo.clearAnimation();
        ivAnimLayerTwo.startAnimation(scaleAnimationFirst);
    }

    /**
     * 开始播放缩放动画(第二段)
     */
    private void playScaleAnimationSecond() {
        ivAnimLayerOne.clearAnimation();
        ivAnimLayerOne.startAnimation(scaleAnimationSecond);

        ivAnimLayerTwo.clearAnimation();
        ivAnimLayerTwo.startAnimation(scaleAnimationSecond);
    }

    /**
     * 开始播放贞动画
     */
    private void playFrameAnimation() {
        ivAnimLayerFour.setVisibility(View.VISIBLE);
        if (!frameAnimation.isRunning()) {
            frameAnimation.start();
        }
    }

    /**
     * 重置贞动画
     */
    private void resetFrameAnimation() {
        frameAnimation.stop();
        ivAnimLayerFour.setVisibility(View.GONE);
    }

    private void playHandAnim() {
        if (ivAnimLayerFour.getVisibility() != View.VISIBLE) {
            ivAnimLayerFour.setVisibility(View.VISIBLE);
        }

        int translationX = 80;
        int translationY = 80;

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(ivAnimLayerFour, "scaleX", 0.6f, 0.6f),
                ObjectAnimator.ofFloat(ivAnimLayerFour, "scaleY", 0.6f, 0.6f),
                ObjectAnimator.ofFloat(ivAnimLayerFour, "alpha", 0.25f, 0.5f, 0.75f,1f),
                ObjectAnimator.ofFloat(ivAnimLayerFour, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(ivAnimLayerFour, "translationY", translationY, 0)
        );
        //动画周期为500ms
        set.setDuration(500).start();
        //为动画加上事件监听，当动画结束的时候，开始另一个动画
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 播放动画
     */
    int param = 0;
    public void startAnim(View view) {
        playScaleAnimationFirst();
        resetFrameAnimation();
    }
}
