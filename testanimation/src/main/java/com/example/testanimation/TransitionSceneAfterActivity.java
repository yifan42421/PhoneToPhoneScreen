package com.example.testanimation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;

/**
 * Created by byc on 2017/10/9.
 */

public class TransitionSceneAfterActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setEnterTransition(new Slide());
        getWindow().setEnterTransition(new Fade());
        setContentView(R.layout.transition_scene_after);
    }
}
