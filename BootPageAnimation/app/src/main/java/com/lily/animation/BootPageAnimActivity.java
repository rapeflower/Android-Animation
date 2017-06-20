package com.lily.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.lily.animation.view.XModeImageView;

import static android.R.attr.translationX;
import static android.R.attr.translationY;
import static android.content.ContentValues.TAG;

/**
 * Created by Author on 17/6/19.
 */

public class BootPageAnimActivity extends Activity {

    ImageView ivAnimLayerOne;
    XModeImageView ivAnimLayerTwo;
    ImageView ivAnimLayerThree;
    ImageView ivAnimLayerFour;
    ImageView ivAnimLayerGlobalPurchase;//'全球购'
    ImageView ivAnimLayerMutualAidCircle;//'互助圈'
    ImageView ivAnimLayerSign;//'签到'
    ImageView ivAnimLayerFracture;//'折'
    ImageView ivAnimLayerTextOne;//'全新首页'
    ImageView ivAnimLayerTextTwo;//'让你体验不一样的电商'
    ScaleAnimation scaleAnimationFirst;
    ScaleAnimation scaleAnimationSecond;

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
        ivAnimLayerThree = (ImageView) findViewById(R.id.iv_anim_layer_three);
        ivAnimLayerFour = (ImageView) findViewById(R.id.iv_anim_layer_four);

        ivAnimLayerGlobalPurchase = (ImageView) findViewById(R.id.iv_anim_layer_global_purchase);
        ivAnimLayerMutualAidCircle = (ImageView) findViewById(R.id.iv_anim_layer_mutual_aid_circle);
        ivAnimLayerSign = (ImageView) findViewById(R.id.iv_anim_layer_sign);
        ivAnimLayerFracture = (ImageView) findViewById(R.id.iv_anim_layer_fracture);
        ivAnimLayerTextOne = (ImageView) findViewById(R.id.iv_anim_layer_text_one);
        ivAnimLayerTextTwo = (ImageView) findViewById(R.id.iv_anim_layer_text_two);
    }

    /**
     * 初始化需要执行的动画
     */
    private void initAnimation() {
        scaleAnimFirst();
        scaleAnimSecond();
    }

    /**
     * 初始化第一段缩放动画
     */
    private void scaleAnimFirst() {
        /** 设置缩放动画 */
        scaleAnimationFirst =new ScaleAnimation(0.4f, 0.9f, 0.4f, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationFirst.setDuration(1400);//设置动画持续时间
        scaleAnimationFirst.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        //scaleAnimationFirst.setStartOffset(500);//执行前的等待时间
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

    /**
     * 初始化第二段缩放动画
     */
    private void scaleAnimSecond() {
        /** 设置缩放动画 */
        scaleAnimationSecond =new ScaleAnimation(0.9f, 0.7f, 0.9f, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationSecond.setDuration(600);//设置动画持续时间
        scaleAnimationSecond.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        scaleAnimationSecond.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                android.util.Log.w(TAG, "--------------> Second onAnimationEnd");
                ivAnimLayerTwo.startDrawMobilePhonePic(true, 0);
                playHandAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
     * 播放手指的动画
     */
    private void playHandAnim() {
        if (ivAnimLayerFour.getVisibility() != View.VISIBLE) {
            ivAnimLayerFour.setVisibility(View.VISIBLE);
        }

        int translationX = 60;
        int translationY = 60;

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
                playHomeAnim();
                playGlobalPurchaseAnim();
                playMutualAidCircleAnim();
                playSignAnim();
                playFractureAnim();
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
     * 播放'首页'图片动画
     */
    private void playHomeAnim() {
        if (ivAnimLayerThree.getVisibility() != View.VISIBLE) {
            ivAnimLayerThree.setVisibility(View.VISIBLE);
        }

        AnimatorSet homeSet = new AnimatorSet();
        //包含缩放和透明度动画
        homeSet.playTogether(
                ObjectAnimator.ofFloat(ivAnimLayerThree, "scaleX", 0.5f, 0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerThree, "scaleY", 0.5f, 0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerThree, "alpha", 0.0f, 0.25f ,0.5f, 0.75f,1f),
                ObjectAnimator.ofFloat(ivAnimLayerThree, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(ivAnimLayerThree, "translationY", translationY, 0)
        );
        //动画周期为500ms
        homeSet.setDuration(300).start();
    }

    private int[] getLocation(View view) {
        int[] xy = new int[2];
        view.getLocationInWindow(xy);
        return xy;
    }

    /**
     * 播放'全球购'图片动画
     */
    private void playGlobalPurchaseAnim() {
        //全球购的图片动画
        if (ivAnimLayerGlobalPurchase.getVisibility() != View.VISIBLE) {
            ivAnimLayerGlobalPurchase.setVisibility(View.VISIBLE);
        }

        AnimatorSet globalPurchaseSet = new AnimatorSet();
        //包含平移、缩放和透明度动画
        globalPurchaseSet.playTogether(
                ObjectAnimator.ofFloat(ivAnimLayerGlobalPurchase, "scaleX", 0.1f, 0.2f, 0.3f, 0.4f,0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerGlobalPurchase, "scaleY", 0.1f, 0.2f, 0.3f, 0.4f,0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerGlobalPurchase, "alpha", 0.0f, 0.25f ,0.5f, 0.75f,1f),
                ObjectAnimator.ofFloat(ivAnimLayerGlobalPurchase, "translationX", -100, 0),
                ObjectAnimator.ofFloat(ivAnimLayerGlobalPurchase, "translationY", 560, 0)
        );
        //动画周期为500ms
        globalPurchaseSet.setDuration(500).start();
    }

    /**
     * 播放'互助圈'图片动画
     */
    private void playMutualAidCircleAnim() {
        //互助圈的图片动画
        if (ivAnimLayerMutualAidCircle.getVisibility() != View.VISIBLE) {
            ivAnimLayerMutualAidCircle.setVisibility(View.VISIBLE);
        }

        AnimatorSet mutualAidCircleSet = new AnimatorSet();
        //包含平移、缩放和透明度动画
        mutualAidCircleSet.playTogether(
                ObjectAnimator.ofFloat(ivAnimLayerMutualAidCircle, "scaleX", 0.1f, 0.2f, 0.3f, 0.4f,0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerMutualAidCircle, "scaleY", 0.1f, 0.2f, 0.3f, 0.4f,0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerMutualAidCircle, "alpha", 0.0f, 0.25f ,0.5f, 0.75f,1f),
                ObjectAnimator.ofFloat(ivAnimLayerMutualAidCircle, "translationX", 280, 0),
                ObjectAnimator.ofFloat(ivAnimLayerMutualAidCircle, "translationY", 330, 0)
        );
        //动画周期为500ms
        mutualAidCircleSet.setDuration(500).start();
    }

    /**
     * 播放'签到'图片动画
     */
    private void playSignAnim() {
        //签到的图片动画
        if (ivAnimLayerSign.getVisibility() != View.VISIBLE) {
            ivAnimLayerSign.setVisibility(View.VISIBLE);
        }

        AnimatorSet signSet = new AnimatorSet();
        //包含平移、缩放和透明度动画
        signSet.playTogether(
                ObjectAnimator.ofFloat(ivAnimLayerSign, "scaleX", 0.1f, 0.2f, 0.3f, 0.4f,0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerSign, "scaleY", 0.1f, 0.2f, 0.3f, 0.4f,0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerSign, "alpha", 0.0f, 0.25f ,0.5f, 0.75f,1f),
                ObjectAnimator.ofFloat(ivAnimLayerSign, "translationX", 240, 0),
                ObjectAnimator.ofFloat(ivAnimLayerSign, "translationY", -100, 0)
        );
        //动画周期为500ms
        signSet.setDuration(500).start();
    }

    /**
     * 播放'折'图片动画
     */
    private void playFractureAnim() {
        //折扣的图片动画
        if (ivAnimLayerFracture.getVisibility() != View.VISIBLE) {
            ivAnimLayerFracture.setVisibility(View.VISIBLE);
        }

        AnimatorSet fractureSet = new AnimatorSet();
        //包含平移、缩放和透明度动画
        fractureSet.playTogether(
                ObjectAnimator.ofFloat(ivAnimLayerFracture, "scaleX", 0.1f, 0.2f, 0.3f, 0.4f,0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerFracture, "scaleY", 0.1f, 0.2f, 0.3f, 0.4f,0.5f),
                ObjectAnimator.ofFloat(ivAnimLayerFracture, "alpha", 0.0f, 0.25f ,0.5f, 0.75f,1f),
                ObjectAnimator.ofFloat(ivAnimLayerFracture, "translationX", -320, 0),
                ObjectAnimator.ofFloat(ivAnimLayerFracture, "translationY", 260, 0)
        );
        //动画周期为500ms
        fractureSet.setDuration(500).start();
        fractureSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playTextOneAnim();
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
     * 播放'全新首页'图片动画
     */
    private void playTextOneAnim() {
        //全新首页的图片动画
        if (ivAnimLayerTextOne.getVisibility() != View.VISIBLE) {
            ivAnimLayerTextOne.setVisibility(View.VISIBLE);
            ivAnimLayerTextOne.setScaleX(0.6f);
            ivAnimLayerTextOne.setScaleY(0.6f);
        }

        /**
         * 第一步Alpha值渐变动画
         */
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        //设置动画持续时长
        alphaAnimation.setDuration(200);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        alphaAnimation.setFillAfter(true);
        ivAnimLayerTextOne.clearAnimation();
        ivAnimLayerTextOne.startAnimation(alphaAnimation);

        /**
         * 第二步Y轴方向上平移
         */
        final TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -80);
        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);//设置为true，动画转化结束后被应用

        //设置Alpha值渐变动画监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Y轴方向上平移动画
                ivAnimLayerTextOne.clearAnimation();
                ivAnimLayerTextOne.startAnimation(translateAnimation);

                //开始播放'让你体验不一样的电商'图片动画
                playTextTwoAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 播放'让你体验不一样的电商'图片动画
     */
    private void playTextTwoAnim() {
        if (ivAnimLayerTextTwo.getVisibility() != View.VISIBLE) {
            ivAnimLayerTextTwo.setVisibility(View.VISIBLE);
        }

        final AnimatorSet textTwoSet = new AnimatorSet();
        //包含平移、缩放和透明度动画
        textTwoSet.playTogether(
                ObjectAnimator.ofFloat(ivAnimLayerTextTwo, "scaleX", 0.8f, 0.8f),
                ObjectAnimator.ofFloat(ivAnimLayerTextTwo, "scaleY", 0.8f, 0.8f),
                ObjectAnimator.ofFloat(ivAnimLayerTextTwo, "alpha", 0.0f, 0.25f ,0.5f, 0.75f,1f),
                ObjectAnimator.ofFloat(ivAnimLayerTextTwo, "translationX", 0, 0),
                ObjectAnimator.ofFloat(ivAnimLayerTextTwo, "translationY", 0, -80)
        );
        //动画周期为300ms
        textTwoSet.setDuration(500).start();

        textTwoSet.addListener(new Animator.AnimatorListener() {
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
    public void startAnim(View view) {
        ivAnimLayerFour.setVisibility(View.GONE);
        ivAnimLayerSign.setVisibility(View.GONE);
        ivAnimLayerGlobalPurchase.setVisibility(View.GONE);
        playScaleAnimationFirst();

        int [] xy = getLocation(ivAnimLayerThree);
        android.util.Log.w(TAG, "--------------> x = " + xy[0]);
        android.util.Log.w(TAG, "--------------> y = " + xy[1]);
    }
}
