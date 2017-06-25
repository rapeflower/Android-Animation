package com.lily.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.lily.animation.view.XModeImageView;

/**
 * Created by Author on 17/6/19.
 */

public class TestAnimActivity2 extends Activity {

    ImageView ivAnimLayerOne;
    XModeImageView ivAnimLayerTwo;
    ImageView ivAnimLayerFour;
    AnimatorSet animatorSetFirst;
    AnimatorSet animatorSetSecond;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_anim);
        initWidget();
        initAnimation();

        playBaseAnimFirst();
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
    }

    private void scaleAnimFirst() {
        animatorSetFirst = new AnimatorSet();
        //包含第一段放大动画
        animatorSetFirst.playTogether(
                ObjectAnimator.ofFloat(ivAnimLayerOne, "scaleX", 0.4f, 0.9f),
                ObjectAnimator.ofFloat(ivAnimLayerOne, "scaleY", 0.4f, 0.9f)
        );
        //动画周期为500ms
        animatorSetFirst.setDuration(750).start();
        animatorSetFirst.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playBaseAnimSecond();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void scaleAnimSecond() {
        animatorSetSecond = new AnimatorSet();
        //第二段缩小动画度动画
        animatorSetSecond.playTogether(
                ObjectAnimator.ofFloat(ivAnimLayerOne, "scaleX", 0.9f, 0.7f),
                ObjectAnimator.ofFloat(ivAnimLayerOne, "scaleY", 0.9f, 0.7f)
        );
        //动画周期为500ms
        animatorSetSecond.setDuration(750);
        animatorSetSecond.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playHandAnim();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void playBaseAnimFirst() {
        if (animatorSetFirst.isRunning()) {
            animatorSetFirst.cancel();
        }
        animatorSetFirst.start();
    }

    private void playBaseAnimSecond() {
        if (animatorSetSecond.isRunning()) {
            animatorSetSecond.cancel();
        }
        animatorSetSecond.start();
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

        set.cancel();
    }

    /**
     * 播放动画
     */

    public void startAnim(View view) {
        playBaseAnimFirst();
    }

    /**
     * Android属性动画优化（更高效的使用属性动画）
     * <p>
     *    http://blog.csdn.net/rentee/article/details/52251829
     * </p>
     * 使用PropertyValuesHolder
     */
    private void animValueHolder() {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.6f, 0.6f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.6f, 0.6f);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0.25f, 0.5f, 0.75f,1f);
        PropertyValuesHolder pvhTX = PropertyValuesHolder.ofFloat("translationX", 80, 0);
        PropertyValuesHolder pvhTY = PropertyValuesHolder.ofFloat("translationY", 80, 0);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(ivAnimLayerOne, pvhX, pvhY, pvhAlpha, pvhTX, pvhTY);
        animator.start();
        //设置动画监听
        animator.addListener(new Animator.AnimatorListener() {

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
}
