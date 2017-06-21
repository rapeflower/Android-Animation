package com.lily.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * Created by rape flower on 17/6/19.
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
    ScaleAnimation scaleAnimationFirst = null;
    ScaleAnimation scaleAnimationSecond = null;

    /**
     * Message What
     */
    private static final int MSG_GLOBAL_PURCHASE = 1;
    private static final int MSG_MUTUAL_AID_CIRCLE = 2;
    private static final int MSG_SIGN = 3;
    private static final int MSG_FRACTURE = 4;
    /**
     * '全球购'、'互助圈'、'签到'、'折'的晃动动偏移量
     * 单位像素(px)
     */
    private static final int OFFSET_GLOBAL_PURCHASE = 20;
    private static final int OFFSET_MUTUAL_AID_CIRCLE = 18;
    private static final int OFFSET_SIGN = 15;
    private static final int OFFSET_FRACTURE = 10;
    /**
     * '全球购'、'互助圈'、'签到'、'折'的晃动动画时间
     * 单位毫秒(ms)
     */
    private static final long DURATION_GLOBAL_PURCHASE = 800;
    private static final long DURATION_MUTUAL_AID_CIRCLE = 700;
    private static final long DURATION_SIGN = 600;
    private static final long DURATION_FRACTURE = 1000;

    /**
     * Handler
     */
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GLOBAL_PURCHASE:
                    playShakeAnim(ivAnimLayerGlobalPurchase, MSG_GLOBAL_PURCHASE, OFFSET_GLOBAL_PURCHASE, DURATION_GLOBAL_PURCHASE);
                    break;
                case MSG_MUTUAL_AID_CIRCLE:
                    playShakeAnim(ivAnimLayerMutualAidCircle, MSG_MUTUAL_AID_CIRCLE, OFFSET_MUTUAL_AID_CIRCLE, DURATION_MUTUAL_AID_CIRCLE);
                    break;
                case MSG_SIGN:
                    playShakeAnim(ivAnimLayerSign, MSG_SIGN, OFFSET_SIGN, DURATION_SIGN);
                    break;
                case MSG_FRACTURE:
                    playShakeAnim(ivAnimLayerFracture, MSG_FRACTURE, OFFSET_FRACTURE, DURATION_FRACTURE);
                    break;
            }
            return false;
        }
    });

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
        ivAnimLayerGlobalPurchase.clearAnimation();

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
        globalPurchaseSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playShakeAnim(ivAnimLayerGlobalPurchase, MSG_GLOBAL_PURCHASE, OFFSET_GLOBAL_PURCHASE, DURATION_GLOBAL_PURCHASE);
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
     * 播放'互助圈'图片动画
     */
    private void playMutualAidCircleAnim() {
        //互助圈的图片动画
        if (ivAnimLayerMutualAidCircle.getVisibility() != View.VISIBLE) {
            ivAnimLayerMutualAidCircle.setVisibility(View.VISIBLE);
        }
        ivAnimLayerMutualAidCircle.clearAnimation();

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
        mutualAidCircleSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playShakeAnim(ivAnimLayerMutualAidCircle, MSG_MUTUAL_AID_CIRCLE, OFFSET_MUTUAL_AID_CIRCLE, DURATION_MUTUAL_AID_CIRCLE);
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
     * 播放'签到'图片动画
     */
    private void playSignAnim() {
        //签到的图片动画
        if (ivAnimLayerSign.getVisibility() != View.VISIBLE) {
            ivAnimLayerSign.setVisibility(View.VISIBLE);
        }
        ivAnimLayerSign.clearAnimation();

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
        signSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playShakeAnim(ivAnimLayerSign, MSG_SIGN, OFFSET_SIGN, DURATION_SIGN);
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
     * 播放'折'图片动画
     */
    private void playFractureAnim() {
        //折扣的图片动画
        if (ivAnimLayerFracture.getVisibility() != View.VISIBLE) {
            ivAnimLayerFracture.setVisibility(View.VISIBLE);
        }
        ivAnimLayerFracture.clearAnimation();

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
                playShakeAnim(ivAnimLayerFracture, MSG_FRACTURE, OFFSET_FRACTURE, DURATION_FRACTURE);
                playTextOneAnimFirst();
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
     * 播放"晃动"动画
     */
    private void playShakeAnim(View target, final int what, int offset, long duration) {
        if (target != null) {
            target.clearAnimation();
        }

        ObjectAnimator anim1 = getAnimTranslationX(target, 0, offset);
        ObjectAnimator anim2 = getAnimTranslationY(target, 0, -offset);

        ObjectAnimator anim3 = getAnimTranslationX(target, offset, 0);
        ObjectAnimator anim4 = getAnimTranslationY(target, -offset, 0);

        AnimatorSet shakeSet = new AnimatorSet();
        shakeSet.play(anim1).with(anim2).before(anim3);
        shakeSet.play(anim3).before(anim4);
        shakeSet.play(anim4);

        shakeSet.setDuration(duration).start();
        shakeSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (handler != null) {
                    Message message = new Message();
                    message.what = what;
                    handler.sendMessage(message);
                }
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
     * 播放"全新首页"第一段Alpha渐变动画
     */
    private void playTextOneAnimFirst() {
        //全新首页的图片动画
        if (ivAnimLayerTextOne.getVisibility() != View.VISIBLE) {
            ivAnimLayerTextOne.setVisibility(View.VISIBLE);
        }

        ObjectAnimator animScaleXOne = getAnimScaleX(ivAnimLayerTextOne, 0.6f, 0.6f);
        ObjectAnimator animScaleYOne = getAnimScaleY(ivAnimLayerTextOne, 0.6f, 0.6f);
        ObjectAnimator animAlpha = getAnimAlpha(ivAnimLayerTextOne, 0.0f, 0.25f ,0.5f, 0.75f,1f);

        AnimatorSet animatorSetFirst = new AnimatorSet();
        animatorSetFirst.play(animScaleXOne).with(animScaleYOne).with(animAlpha);
        //动画周期为500ms
        animatorSetFirst.setDuration(200).start();
        animatorSetFirst.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playTextOneAnimSecond();
                playTextTwoAnim();
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
     * 播放"全新首页"第二段TranslationY动画
     */
    private void playTextOneAnimSecond() {
        //全新首页的图片动画
        if (ivAnimLayerTextOne.getVisibility() != View.VISIBLE) {
            ivAnimLayerTextOne.setVisibility(View.VISIBLE);
        }

        ObjectAnimator animTranslationXOne = getAnimTranslationX(ivAnimLayerTextOne, 0, 0);
        ObjectAnimator animTranslationYOne = getAnimTranslationY(ivAnimLayerTextOne, 0, -80);

        AnimatorSet animatorSetSecond = new AnimatorSet();
        animatorSetSecond.play(animTranslationXOne).with(animTranslationYOne);
        //动画周期为500ms
        animatorSetSecond.setDuration(500).start();
    }

    /**
     * 播放'全新首页'图片动画
     * @descripe 以下动画实现方式被采用属性动画：playTextOneAnimFirst() 和 playTextOneAnimSecond()替换
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //清除View对应的动画对象
        ivAnimLayerOne.clearAnimation();
        ivAnimLayerTwo.clearAnimation();
        ivAnimLayerThree.clearAnimation();
        ivAnimLayerFour.clearAnimation();
        ivAnimLayerGlobalPurchase.clearAnimation();
        ivAnimLayerMutualAidCircle.clearAnimation();
        ivAnimLayerSign.clearAnimation();
        ivAnimLayerFracture.clearAnimation();
        ivAnimLayerTextOne.clearAnimation();
        ivAnimLayerTextTwo.clearAnimation();

        scaleAnimationFirst.cancel();
        scaleAnimationSecond.cancel();

        handler = null;
    }

    /**
     * 获取X轴方向缩放动画
     *
     * @param target
     * @param values
     */
    private ObjectAnimator getAnimScaleX(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "scaleX", values);
    }

    /**
     * 获取Y轴方向缩放动画
     *
     * @param target
     * @param values
     * @return
     */
    private ObjectAnimator getAnimScaleY(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "scaleY", values);
    }

    /**
     * 获取Alpha动画
     *
     * @param target
     * @param values
     * @return
     */
    private ObjectAnimator getAnimAlpha(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "alpha", values);
    }

    /**
     * 获取X轴方向平移动画
     *
     * @param target
     * @param values
     * @return
     */
    private ObjectAnimator getAnimTranslationX(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "translationX", values);
    }

    /**
     * 获取Y轴方向平移动画
     *
     * @param target
     * @param values
     * @return
     */
    private ObjectAnimator getAnimTranslationY(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "translationY", values);
    }

    /**
     * 获取旋转动画
     *
     * @param target
     * @param values
     * @return
     */
    private ObjectAnimator getAnimRotation(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "rotation", values);
    }
}
