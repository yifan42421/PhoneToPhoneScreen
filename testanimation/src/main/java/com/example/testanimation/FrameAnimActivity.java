package com.example.testanimation;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

public class FrameAnimActivity extends AppCompatActivity {

    private Button btn_start;
    private Button btn_stop;
    private TextView frameView;
    private TextView frameView2;
    private TextView alphaanimView;
    private TextView alphaanimView2;
    private TextView scaleanim;
    private TextView scaleanim2;
    private TextView translateanim;
    private TextView translateanim2;
    private TextView rotateanim;
    private TextView rotateanim2;
    private TextView customanim;
    private TextView propertyanim;
    private TextView tv_transition;
    private Button bt_transition;
    private AnimationDrawable animDrawable;
    private AnimationDrawable animDrawable2;
    private Animation alphaAnim;
    private AlphaAnimation alphaAnim2;
    private Animation scaleAnimation;
    private ScaleAnimation scaleAnimation2;
    private Animation translateAnimation;
    private TranslateAnimation translateAnimation2;
    private Animation rotateAnimation;
    private RotateAnimation rotateAnimation2;
    private CustomTweenAnimation customAnimation;
    private ObjectAnimator pAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        //xml形式
        frameView = (TextView) findViewById(R.id.frameanim);
        Drawable draw[] = frameView.getCompoundDrawables();
        animDrawable = (AnimationDrawable) draw[3];

        //代码形式
        frameView2 = (TextView) findViewById(R.id.frameanim2);
        animDrawable2 = new AnimationDrawable();
        for (int i = 7; i > 0; i--) {
            int id = getResources().getIdentifier("shiye_" + i, "drawable", getPackageName());
            Drawable drawable = getResources().getDrawable(id);
            //drawable.setBounds(0,0,132,44);
            //drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());

            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            animDrawable2.addFrame(drawable, 120);
        }
        //frameView2.setCompoundDrawables(null,null,null,animDrawable2);//不能用，不知道什么原因
        frameView2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, animDrawable2);
        animDrawable2.setOneShot(false);


        //alpha动画 调用xml
        alphaanimView = (TextView) findViewById(R.id.alphaanim);
        alphaAnim = AnimationUtils.loadAnimation(this, R.anim.tweenanim_alpha);


        //alpha动画 纯代码
        alphaanimView2 = (TextView) findViewById(R.id.alphaanim2);
        alphaAnim2 = new AlphaAnimation(0.1f, 1.0f);
        alphaAnim2.setDuration(5000);
        alphaAnim2.setFillAfter(true);


        //scale动画 调用xml
        scaleanim = (TextView) findViewById(R.id.scaleanim);
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.tweenanim_scale);
        scaleAnimation.setFillAfter(true);


        //scale动画 纯代码
        scaleanim2 = (TextView) findViewById(R.id.scaleanim2);
        scaleAnimation2 = new ScaleAnimation(0.2f, 1.0f, 0.2f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setDuration(2000);
        scaleAnimation2.setFillAfter(true);


        //translate动画 调用xml
        translateanim = (TextView) findViewById(R.id.translateanim);
        translateAnimation = AnimationUtils.loadAnimation(this, R.anim.tweenanim_translate);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setRepeatCount(1);
        translateAnimation.setFillAfter(true);


        //translate动画 纯代码
        translateanim2 = (TextView) findViewById(R.id.translateanim2);
        translateAnimation2 = new TranslateAnimation(0, 100, 0, 0);
        translateAnimation2.setDuration(5000);
        translateAnimation2.setRepeatMode(Animation.REVERSE);
        translateAnimation2.setRepeatCount(1);
        translateAnimation2.setFillAfter(true);


        //rotate动画 调用xml
        rotateanim = (TextView) findViewById(R.id.rotateanim);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.tweenanim_rotate);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setRepeatCount(1);


        //rotate动画 纯代码
        rotateanim2 = (TextView) findViewById(R.id.rotateanim2);
        rotateAnimation2 = new RotateAnimation(0, -720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation2.setDuration(2000);
        rotateAnimation2.setRepeatMode(Animation.REVERSE);
        rotateAnimation2.setRepeatCount(1);

        //自定义动画
        customanim = (TextView) findViewById(R.id.customanim);
        customAnimation = new CustomTweenAnimation(50, 50, 5000);

        //属性动画
        propertyanim = (TextView) findViewById(R.id.propertyanim);
        //把TextView放大1.5倍
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.5f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.5f);
        Keyframe keyframe0 = Keyframe.ofFloat(0f, 0);
        Keyframe keyframe1 = Keyframe.ofFloat(.3f, 100);
        Keyframe keyframe2 = Keyframe.ofFloat(.4f, 200);
        Keyframe keyframe3 = Keyframe.ofFloat(1f, 200);
        PropertyValuesHolder pvhM = PropertyValuesHolder.ofKeyframe("translationX", keyframe0, keyframe1, keyframe2, keyframe3);
        pAnim = ObjectAnimator.ofPropertyValuesHolder(propertyanim, pvhX, pvhY, pvhM);
        pAnim.setDuration(5000);
        pAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                propertyanim.setTranslationX((float) pAnim.getAnimatedValue());
            }
        });

        //可以在动画执行的某阶段做其它事情
        pAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        //过渡动画
        tv_transition = (TextView) findViewById(R.id.tv_transition);
        bt_transition = (Button) findViewById(R.id.goButton);
        bt_transition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrameAnimActivity.this, TransitionSceneAfterActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FrameAnimActivity.this, Pair.create((View) tv_transition, "transitionanim"), Pair.create((View) bt_transition, "bt_transition")).toBundle());
            }
        });
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimations();
            }
        });

        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAnimations();
            }
        });
    }

    private void startAnimations() {
        if (animDrawable != null) {
            animDrawable.start();
        }

        if (animDrawable2 != null) {
            animDrawable2.start();
        }

        //以下所有Textview如果都用setAnimation，则不反应，有一个startAnimation就都可以动画
        //SetAnimation这个可以控制动画启动时间，并且需要其父view在动画快开始时调用invalidate.  所以其需要一定的条件限制。
        //StartAnimation立即开始动画效果

        if (alphaAnim != null) {
            alphaanimView.setAnimation(alphaAnim);
            alphaAnim.startNow();
        }

        if (alphaAnim2 != null) {
            // ((View)alphaanimView2.getParent()).invalidate();//都用setAnimation时，需要刷新父view
            // alphaanimView2.startAnimation(alphaAnim2);
            alphaanimView2.setAnimation(alphaAnim2);
            alphaAnim2.startNow();
        }

        if (scaleAnimation != null) {
            scaleanim.setAnimation(scaleAnimation);
            scaleAnimation.startNow();
        }

        if (scaleAnimation2 != null) {
            scaleanim2.setAnimation(scaleAnimation2);
            scaleAnimation2.startNow();
        }
        if (translateAnimation != null) {
            translateanim.setAnimation(translateAnimation);
            translateAnimation.startNow();
        }

        if (translateAnimation2 != null) {
            translateanim2.setAnimation(translateAnimation2);
            translateAnimation2.startNow();
        }
        if (rotateAnimation != null) {
            rotateanim.setAnimation(rotateAnimation);
            rotateAnimation.startNow();
        }

        if (rotateAnimation2 != null) {
            // rotateanim2.setAnimation(rotateAnimation2);
            //rotateAnimation2.startNow();
            rotateanim2.startAnimation(rotateAnimation2);
        }

        if (customAnimation != null) {
            customanim.setAnimation(customAnimation);
            customAnimation.startNow();
        }
        pAnim.start();
    }

    private void stopAnimations() {
        if (animDrawable != null) {
            animDrawable.stop();
        }

        if (animDrawable2 != null) {
            animDrawable2.stop();
        }

        if (alphaAnim != null) {
            alphaAnim.cancel();
        }

        if (alphaAnim2 != null) {
            alphaAnim2.cancel();
        }

        if (scaleAnimation != null) {
            scaleAnimation.cancel();
        }

        if (scaleAnimation2 != null) {
            scaleAnimation2.cancel();
        }

        if (translateAnimation != null) {
            translateAnimation.cancel();
        }

        if (translateAnimation2 != null) {
            translateAnimation2.cancel();
        }
        if (rotateAnimation != null) {
            rotateAnimation.cancel();
        }

        if (rotateAnimation2 != null) {
            rotateAnimation2.cancel();
        }
    }

    @Override
    protected void onPause() {
        stopAnimations();
        super.onPause();
    }
}
