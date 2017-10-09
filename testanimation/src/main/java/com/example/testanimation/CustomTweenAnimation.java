package com.example.testanimation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by byc on 2017/10/9.
 * 自定义补间动画（三维旋转动画）
 */

public class CustomTweenAnimation extends Animation {

    private float mCenterX;
    private float mConterY;
    private int mDuration;

    private Camera mCamera = new Camera();

    public CustomTweenAnimation(float x, float y, int duration) {
        mCenterX = x;
        mConterY = y;
        mDuration = duration;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        //设置动画持续时间
        setDuration(mDuration);

        //设置动画结束后效果保留
        setFillAfter(true);

        //设置匀速变换
        setInterpolator(new LinearInterpolator());
    }

    //必须重写此方法
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        mCamera.save();
        //根据interpolatedTime时间来控制x,y,z轴上的偏移
        mCamera.translate(100.0f - 100.0f * interpolatedTime, 150.0f * interpolatedTime - 150.0f, 80.0f - 80.0f * interpolatedTime);
        //设置根据interpolatedTime时间在x轴上旋转不同角度
        mCamera.rotateX(360 * interpolatedTime);
        //设置根据interpolatedTime时间在Y轴上旋转不同角度
        mCamera.rotateY(360 * interpolatedTime);
        //获取Transformation参数的Matrix对象
        Matrix matrix = t.getMatrix();
        //将Camera所做的变换应用到Transformation的Matrix上
        mCamera.getMatrix(matrix);
        matrix.preTranslate(-mCenterX, -mConterY);
        matrix.postTranslate(mCenterX, mConterY);
        mCamera.restore();
    }
}
