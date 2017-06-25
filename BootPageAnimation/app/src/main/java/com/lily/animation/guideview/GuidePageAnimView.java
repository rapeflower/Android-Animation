package com.lily.animation.guideview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lily.animation.R;

/**
 * @Author rape flower
 * @Date 2017-06-21 17:25
 * @Describe 3.8.5引导页动画View
 */
public class GuidePageAnimView extends FrameLayout{

    ImageView ivAnimLayerOne;//默认显示的第一张动画播放图片
    ImageView ivAnimLayerLineOne;//右上角显示的线条图片
    ImageView ivAnimLayerLineTwo;//左下角显示的线条图片
    GuideMaskImageView ivAnimLayerTwo;//中间手机遮罩图片
    ImageView ivAnimLayerThree;//中间表示主界面的图片
    ImageView ivAnimLayerFour;//'手'图片
    ImageView ivAnimLayerLightEffect;//光效图片
    ImageView ivAnimLayerGlobalPurchase;//'全球购'
    ImageView ivAnimLayerMutualAidCircle;//'互助圈'
    ImageView ivAnimLayerSign;//'签到'
    ImageView ivAnimLayerFracture;//'折'
    ImageView ivAnimLayerTextOne;//'全新首页'
    ImageView ivAnimLayerTextTwo;//'让你体验不一样的电商'
    ScaleAnimation scaleAnimationFirst = null;
    ScaleAnimation scaleAnimationSecond = null;

    public static final String TAG = GuidePageAnimView.class.getSimpleName();
    /**
     * 动画时间(其它图片的)
     * 单位毫秒(ms)
     */
    private static final long DURATION_FIRST_SCALE_ANIM = 1400;//第一段缩放动画时间
    private static final long DURATION_SECOND_SCALE_ANIM = 600;//第二段缩放动画时间
    private static final long DURATION_MOBILE_PHONE = 300;//'手机'图片动画播放时间
    private static final long DURATION_HAND = 500;//'手'图片动画播放时间
    private static final long DURATION_HOME = 300;//'首页'图片动画播放时间
    private static final long DURATION_GP_MAC_S_F = 500;//'全球购'、'互助圈'、'签到'、'折'图片的缩放、渐变、位置动画时间
    private static final long DURATION_LIGHT_EFFECT = 700;//'光效'图片的播放时间
    private static final long DURATION_FIRST_NHP = 200;//'全新首页'渐变(Alpha)动画时间
    private static final long DURATION_SECOND_NHP = 500;//'全新首页'位移动画时间、播放'让你体验不一样的电商'图片动画时间

    /**
     * '全球购'、'互助圈'、'签到'、'折'的晃动动画时间
     * 单位毫秒(ms)
     */
    private static final long DURATION_SHAKE_GLOBAL_PURCHASE = 800;
    private static final long DURATION_SHAKE_MUTUAL_AID_CIRCLE = 700;
    private static final long DURATION_SHAKE_SIGN = 600;
    private static final long DURATION_SHAKE_FRACTURE = 1000;
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

    private Context mContext;
    private LayoutInflater mInflater;//布局加载器

    public GuidePageAnimView(@NonNull Context context) {
        this(context, null);
    }

    public GuidePageAnimView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuidePageAnimView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs);
    }

    /**
     * Handler
     */
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GLOBAL_PURCHASE:
                    playShakeAnim(ivAnimLayerGlobalPurchase, MSG_GLOBAL_PURCHASE, OFFSET_GLOBAL_PURCHASE, DURATION_SHAKE_GLOBAL_PURCHASE);
                    break;
                case MSG_MUTUAL_AID_CIRCLE:
                    playShakeAnim(ivAnimLayerMutualAidCircle, MSG_MUTUAL_AID_CIRCLE, OFFSET_MUTUAL_AID_CIRCLE, DURATION_SHAKE_MUTUAL_AID_CIRCLE);
                    break;
                case MSG_SIGN:
                    playShakeAnim(ivAnimLayerSign, MSG_SIGN, OFFSET_SIGN, DURATION_SHAKE_SIGN);
                    break;
                case MSG_FRACTURE:
                    playShakeAnim(ivAnimLayerFracture, MSG_FRACTURE, OFFSET_FRACTURE, DURATION_SHAKE_FRACTURE);
                    break;
            }
            return false;
        }
    });

    /**
     * 初始化布局
     * @param context
     */
    private void initLayout(Context context, AttributeSet attrs) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_guide_page_anim, this, true);

        initWidget();
        initAnimation();

        playScaleAnimationFirst();
    }

    /**
     * 初始化
     */
    private void initWidget() {
        ivAnimLayerOne = (ImageView) findViewById(R.id.iv_anim_layer_one);
        ivAnimLayerLineOne = (ImageView) findViewById(R.id.iv_anim_layer_line_one);
        ivAnimLayerLineTwo = (ImageView) findViewById(R.id.iv_anim_layer_line_two);
        ivAnimLayerTwo = (GuideMaskImageView) findViewById(R.id.iv_anim_layer_two);
        ivAnimLayerThree = (ImageView) findViewById(R.id.iv_anim_layer_three);
        ivAnimLayerFour = (ImageView) findViewById(R.id.iv_anim_layer_four);
        ivAnimLayerLightEffect = (ImageView) findViewById(R.id.iv_anim_layer_light_effect);
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
        scaleAnimationFirst =new ScaleAnimation(0.4f, 0.8f, 0.4f, 0.8f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationFirst.setDuration(DURATION_FIRST_SCALE_ANIM);//设置动画持续时间
        scaleAnimationFirst.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        //scaleAnimationFirst.setStartOffset(500);//执行前的等待时间
        scaleAnimationFirst.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                android.util.Log.w(TAG, "First scaleAnim onAnimationEnd");
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
        scaleAnimationSecond =new ScaleAnimation(0.8f, 0.7f, 0.8f, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationSecond.setDuration(DURATION_SECOND_SCALE_ANIM);//设置动画持续时间
        scaleAnimationSecond.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        scaleAnimationSecond.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                android.util.Log.w(TAG, "Second scaleAnim onAnimationEnd");
                ivAnimLayerTwo.startDrawMobilePhonePic(true, DURATION_MOBILE_PHONE);
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

        playLineAnimFirst();
    }

    /**
     * 开始播放缩放动画(第二段)
     */
    private void playScaleAnimationSecond() {
        ivAnimLayerOne.clearAnimation();
        ivAnimLayerOne.startAnimation(scaleAnimationSecond);

        ivAnimLayerTwo.clearAnimation();
        ivAnimLayerTwo.startAnimation(scaleAnimationSecond);

        playLineAnimSecond();
    }

    /**
     * 线条的缩放动画第一段
     */
    private void playLineAnimFirst() {
        constructLineAnim(ivAnimLayerLineOne, DURATION_FIRST_SCALE_ANIM, 0.3f, 0.8f);
        constructLineAnim(ivAnimLayerLineTwo, DURATION_FIRST_SCALE_ANIM, 0.3f, 0.8f);
    }

    /**
     * 线条的缩放动画第二段
     */
    private void playLineAnimSecond() {
        constructLineAnim(ivAnimLayerLineOne, DURATION_SECOND_SCALE_ANIM, 0.8f, 0.6f);
        constructLineAnim(ivAnimLayerLineTwo, DURATION_SECOND_SCALE_ANIM, 0.8f, 0.6f);
    }

    /**
     * 创建线条缩放动画
     */
    private void constructLineAnim(View target, long duration, float... values) {
        if (target != null) {
            target.clearAnimation();
        }

        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(values);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(values);
        ObjectAnimator animator = AnimHelper.createPropertyValuesHolder(target, pvhX, pvhY);
        animator.setDuration(duration);
        animator.start();
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

        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(0.6f, 0.6f);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(0.6f, 0.6f);
        PropertyValuesHolder pvhAlpha = AnimHelper.getPVHAnimAlpha(0.25f, 0.5f, 0.75f, 1f);
        PropertyValuesHolder pvhTX = AnimHelper.getPVHAnimTranslationX(translationX, 0);
        PropertyValuesHolder pvhTY = AnimHelper.getPVHAnimTranslationY(translationY, 0);

        ObjectAnimator animator = AnimHelper.createPropertyValuesHolder(ivAnimLayerFour, pvhX, pvhY, pvhAlpha, pvhTX, pvhTY);
        animator.setDuration(DURATION_HAND);
        animator.start();
        //为动画加上事件监听，当动画结束的时候，开始另一个动画
        animator.addListener(new Animator.AnimatorListener() {
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

        //包含缩放和透明度动画
        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(0.5f, 0.5f);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(0.5f, 0.5f);
        PropertyValuesHolder pvhAlpha = AnimHelper.getPVHAnimAlpha(0.25f, 0.5f, 0.75f, 1f);

        ObjectAnimator homeAnimator = AnimHelper.createPropertyValuesHolder(ivAnimLayerThree, pvhX, pvhY, pvhAlpha);
        homeAnimator.setDuration(DURATION_HOME);
        homeAnimator.start();
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

        //包含平移、缩放和透明度动画
        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(0.1f, 0.2f, 0.3f, 0.4f, 0.5f);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(0.1f, 0.2f, 0.3f, 0.4f, 0.5f);
        PropertyValuesHolder pvhAlpha = AnimHelper.getPVHAnimAlpha(0.0f, 0.25f ,0.5f, 0.75f, 1f);
        PropertyValuesHolder pvhTX = AnimHelper.getPVHAnimTranslationX(-100, 0);
        PropertyValuesHolder pvhTY = AnimHelper.getPVHAnimTranslationY(560, 0);

        ObjectAnimator globalPurchaseAnimator = AnimHelper.createPropertyValuesHolder(ivAnimLayerGlobalPurchase, pvhX, pvhY, pvhAlpha, pvhTX, pvhTY);
        globalPurchaseAnimator.setDuration(DURATION_GP_MAC_S_F);
        globalPurchaseAnimator.start();
        globalPurchaseAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playShakeAnim(ivAnimLayerGlobalPurchase, MSG_GLOBAL_PURCHASE, OFFSET_GLOBAL_PURCHASE, DURATION_SHAKE_GLOBAL_PURCHASE);
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

        //包含平移、缩放和透明度动画
        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(0.1f, 0.2f, 0.3f, 0.4f, 0.5f);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(0.1f, 0.2f, 0.3f, 0.4f, 0.5f);
        PropertyValuesHolder pvhAlpha = AnimHelper.getPVHAnimAlpha(0.0f, 0.25f ,0.5f, 0.75f, 1f);
        PropertyValuesHolder pvhTX = AnimHelper.getPVHAnimTranslationX(280, 0);
        PropertyValuesHolder pvhTY = AnimHelper.getPVHAnimTranslationY(330, 0);

        ObjectAnimator mutualAidCircleAnimator = AnimHelper.createPropertyValuesHolder(ivAnimLayerMutualAidCircle, pvhX, pvhY, pvhAlpha, pvhTX, pvhTY);
        mutualAidCircleAnimator.setDuration(DURATION_GP_MAC_S_F);
        mutualAidCircleAnimator.start();
        mutualAidCircleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playShakeAnim(ivAnimLayerMutualAidCircle, MSG_MUTUAL_AID_CIRCLE, OFFSET_MUTUAL_AID_CIRCLE, DURATION_SHAKE_MUTUAL_AID_CIRCLE);
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

        //包含平移、缩放和透明度动画
        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(0.1f, 0.2f, 0.3f, 0.4f, 0.5f);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(0.1f, 0.2f, 0.3f, 0.4f, 0.5f);
        PropertyValuesHolder pvhAlpha = AnimHelper.getPVHAnimAlpha(0.0f, 0.25f ,0.5f, 0.75f, 1f);
        PropertyValuesHolder pvhTX = AnimHelper.getPVHAnimTranslationX(240, 0);
        PropertyValuesHolder pvhTY = AnimHelper.getPVHAnimTranslationY(-100, 0);

        ObjectAnimator signAnimator = AnimHelper.createPropertyValuesHolder(ivAnimLayerSign, pvhX, pvhY, pvhAlpha, pvhTX, pvhTY);
        signAnimator.setDuration(DURATION_GP_MAC_S_F);
        signAnimator.start();
        signAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playShakeAnim(ivAnimLayerSign, MSG_SIGN, OFFSET_SIGN, DURATION_SHAKE_SIGN);
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

        //包含平移、缩放和透明度动画
        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(0.1f, 0.2f, 0.3f, 0.4f, 0.5f);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(0.1f, 0.2f, 0.3f, 0.4f, 0.5f);
        PropertyValuesHolder pvhAlpha = AnimHelper.getPVHAnimAlpha(0.0f, 0.25f ,0.5f, 0.75f, 1f);
        PropertyValuesHolder pvhTX = AnimHelper.getPVHAnimTranslationX(-320, 0);
        PropertyValuesHolder pvhTY = AnimHelper.getPVHAnimTranslationY(260, 0);

        ObjectAnimator fractureAnimator = AnimHelper.createPropertyValuesHolder(ivAnimLayerFracture, pvhX, pvhY, pvhAlpha, pvhTX, pvhTY);
        fractureAnimator.setDuration(DURATION_GP_MAC_S_F);
        fractureAnimator.start();
        fractureAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playShakeAnim(ivAnimLayerFracture, MSG_FRACTURE, OFFSET_FRACTURE, DURATION_SHAKE_FRACTURE);
                playLightEffect();
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

        ObjectAnimator anim1 = AnimHelper.getAnimTranslationX(target, 0, offset);
        ObjectAnimator anim2 = AnimHelper.getAnimTranslationY(target, 0, -offset);

        ObjectAnimator anim3 = AnimHelper.getAnimTranslationX(target, offset, 0);
        ObjectAnimator anim4 = AnimHelper.getAnimTranslationY(target, -offset, 0);

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
     * 播放"光效"动画
     */
    private void playLightEffect() {
        if (ivAnimLayerLightEffect.getVisibility() != View.VISIBLE) {
            ivAnimLayerLightEffect.setVisibility(View.VISIBLE);
        }

        //包含平移、缩放和透明度动画
        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(0.8f, 0.8f);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(0.8f, 0.8f);
        PropertyValuesHolder pvhAlpha = AnimHelper.getPVHAnimAlpha(0.0f, 0.25f ,0.5f, 0.75f, 1f);
        PropertyValuesHolder pvhTX = AnimHelper.getPVHAnimTranslationX(-600, 0);
        PropertyValuesHolder pvhTY = AnimHelper.getPVHAnimTranslationY(600, 0);

        ObjectAnimator lightEffectAnimator = AnimHelper.createPropertyValuesHolder(ivAnimLayerLightEffect, pvhX, pvhY, pvhAlpha, pvhTX, pvhTY);
        lightEffectAnimator.setDuration(DURATION_LIGHT_EFFECT);
        lightEffectAnimator.start();
    }

    /**
     * 播放"全新首页"第一段Alpha渐变动画
     */
    private void playTextOneAnimFirst() {
        //全新首页的图片动画
        if (ivAnimLayerTextOne.getVisibility() != View.VISIBLE) {
            ivAnimLayerTextOne.setVisibility(View.VISIBLE);
        }

        //包含缩放和透明度动画
        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(0.6f, 0.6f);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(0.6f, 0.6f);
        PropertyValuesHolder pvhAlpha = AnimHelper.getPVHAnimAlpha(0.0f, 0.25f ,0.5f, 0.75f, 1f);

        ObjectAnimator animatorFirst = AnimHelper.createPropertyValuesHolder(ivAnimLayerTextOne, pvhX, pvhY, pvhAlpha);
        animatorFirst.setDuration(DURATION_FIRST_NHP);
        animatorFirst.start();
        animatorFirst.addListener(new Animator.AnimatorListener() {
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

        //平移动画
        PropertyValuesHolder pvhTX = AnimHelper.getPVHAnimTranslationX(0, 0);
        PropertyValuesHolder pvhTY = AnimHelper.getPVHAnimTranslationY(0, -80);

        ObjectAnimator animatorSecond = AnimHelper.createPropertyValuesHolder(ivAnimLayerTextOne, pvhTX, pvhTY);
        animatorSecond.setDuration(DURATION_SECOND_NHP);
        animatorSecond.start();
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

        //包含平移、缩放和透明度动画
        PropertyValuesHolder pvhX = AnimHelper.getPVHAnimScaleX(0.8f, 0.8f);
        PropertyValuesHolder pvhY = AnimHelper.getPVHAnimScaleY(0.8f, 0.8f);
        PropertyValuesHolder pvhAlpha = AnimHelper.getPVHAnimAlpha(0.0f, 0.25f ,0.5f, 0.75f, 1f);
        PropertyValuesHolder pvhTX = AnimHelper.getPVHAnimTranslationX(0, 0);
        PropertyValuesHolder pvhTY = AnimHelper.getPVHAnimTranslationY(0, -80);

        ObjectAnimator textTwoAnimator = AnimHelper.createPropertyValuesHolder(ivAnimLayerTextTwo, pvhX, pvhY, pvhAlpha, pvhTX, pvhTY);
        textTwoAnimator.setDuration(DURATION_SECOND_NHP);
        textTwoAnimator.start();
    }

    /**
     * 释放资源
     */
    public void release() {
        //清除View对应的动画对象
        ivAnimLayerOne.clearAnimation();
        ivAnimLayerLineOne.clearAnimation();
        ivAnimLayerLineTwo.clearAnimation();
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
    }
}
