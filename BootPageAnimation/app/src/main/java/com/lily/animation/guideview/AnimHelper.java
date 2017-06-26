package com.lily.animation.guideview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * @author rape flower
 * @Date 2017-06-19 17:25
 * @descripe 动画工具类
 */
public class AnimHelper {

    /**
     * 获取X轴方向缩放动画
     * <p>属性动画：ObjectAnimator </p>
     *
     * @param target
     * @param values
     */
    public static ObjectAnimator getAnimScaleX(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "scaleX", values);
    }

    /**
     * 获取Y轴方向缩放动画
     * <p>属性动画：ObjectAnimator </p>
     *
     * @param target
     * @param values
     * @return
     */
    public static ObjectAnimator getAnimScaleY(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "scaleY", values);
    }

    /**
     * 获取Alpha动画
     * <p>属性动画：ObjectAnimator </p>
     *
     * @param target
     * @param values
     * @return
     */
    public static ObjectAnimator getAnimAlpha(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "alpha", values);
    }

    /**
     * 获取X轴方向平移动画
     * <p>属性动画：ObjectAnimator </p>
     *
     * @param target
     * @param values
     * @return
     */
    public static ObjectAnimator getAnimTranslationX(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "translationX", values);
    }

    /**
     * 获取Y轴方向平移动画
     * <p>属性动画：ObjectAnimator </p>
     *
     * @param target
     * @param values
     * @return
     */
    public static ObjectAnimator getAnimTranslationY(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "translationY", values);
    }

    /**
     * 获取旋转动画
     * <p>属性动画：ObjectAnimator </p>
     *
     * @param target
     * @param values
     * @return
     */
    public static ObjectAnimator getAnimRotation(View target, float... values) {
        return ObjectAnimator.ofFloat(target, "rotation", values);
    }

    /**
     * 获取X轴方向缩放动画
     * <p>属性动画：PropertyValuesHolder </p>
     *
     * @param values
     */
    public static PropertyValuesHolder getPVHAnimScaleX(float... values) {
        return PropertyValuesHolder.ofFloat("scaleX", values);
    }

    /**
     * 获取Y轴方向缩放动画
     * <p>属性动画：PropertyValuesHolder </p>
     *
     * @param values
     * @return
     */
    public static PropertyValuesHolder getPVHAnimScaleY(float... values) {
        return PropertyValuesHolder.ofFloat("scaleY", values);
    }

    /**
     * 获取Alpha动画
     * <p>属性动画：PropertyValuesHolder </p>
     *
     * @param values
     * @return
     */
    public static PropertyValuesHolder getPVHAnimAlpha(float... values) {
        return PropertyValuesHolder.ofFloat("alpha", values);
    }

    /**
     * 获取X轴方向平移动画
     * <p>属性动画：</p>
     *
     * @param values
     * @return
     */
    public static PropertyValuesHolder getPVHAnimTranslationX(float... values) {
        return PropertyValuesHolder.ofFloat("translationX", values);
    }

    /**
     * 获取Y轴方向平移动画
     * <p>属性动画：PropertyValuesHolder </p>
     *
     * @param values
     * @return
     */
    public static PropertyValuesHolder getPVHAnimTranslationY(float... values) {
        return PropertyValuesHolder.ofFloat("translationY", values);
    }

    /**
     * 获取旋转动画
     * <p>属性动画：PropertyValuesHolder </p>
     *
     * @param values
     * @return
     */
    public static PropertyValuesHolder getPVHAnimRotation(float... values) {
        return PropertyValuesHolder.ofFloat("rotation", values);
    }

    /**
     * 根据传入PropertyValuesHolder构建ObjectAnimator
     *
     * @param target 执行动画的对象
     * @param values 动画值
     * @return
     */
    public static ObjectAnimator createObjectAnimator(View target, PropertyValuesHolder... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, values);
    }
}
