package com.lily.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by lilei on 2017/6/25.
 */

public class TestAnimActivity3 extends Activity{

    ImageView ivAnimLayerTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        initWidget();

//        animValueHolder1();
    }

    private void initWidget() {
        ivAnimLayerTest = (ImageView) findViewById(R.id.iv_anim_layer_test);
    }

    private void animValueHolder1() {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.4f, 0.9f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.4f, 0.9f);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0.25f, 0.5f, 0.75f,1f);
        PropertyValuesHolder pvhTX = PropertyValuesHolder.ofFloat("translationX", 80, 0);
        PropertyValuesHolder pvhTY = PropertyValuesHolder.ofFloat("translationY", 80, 0);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(ivAnimLayerTest, pvhX, pvhY, pvhAlpha);
        animator.setDuration(750);
        animator.start();
        //设置动画监听
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animValueHolder2();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    private void animValueHolder2() {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.9f, 0.7f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.9f, 0.7f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(ivAnimLayerTest, pvhX, pvhY);
        animator.setDuration(300);
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
