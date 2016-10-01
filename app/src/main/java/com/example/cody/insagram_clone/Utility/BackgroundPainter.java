package com.example.cody.insagram_clone.Utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Random;

/**
 * Created by Cody on 2016/10/1.
 */

public class BackgroundPainter {

    private static final int MIN = 3000;
    private static final int MAX = 5000;

    private final Random random;

    public BackgroundPainter() {
        random = new Random();
    }

    public void animate(@NonNull final View target, @ColorInt final int color1,
                        @ColorInt final int color2) {

        final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), color1, color2);

        valueAnimator.setDuration(randInt(MIN, MAX));

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                target.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                //reverse animation
                animate(target, color2, color1);
            }
        });

        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }

    private int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
