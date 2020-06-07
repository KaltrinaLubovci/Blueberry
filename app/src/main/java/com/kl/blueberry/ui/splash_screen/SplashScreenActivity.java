package com.kl.blueberry.ui.splash_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.kl.blueberry.R;
import com.kl.blueberry.databinding.SplashScreenActivityBinding;

public class SplashScreenActivity extends AppCompatActivity {

    SplashScreenActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen_activity);
        binding.setViewModel(new SplashScreenViewModel());

        animateObjects();
    }


    void animateObjects() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        long DURATION = 1000L;

        ObjectAnimator iconAnimator = ObjectAnimator.ofFloat(binding.ivAppIcon, "translationY"
                , (float) (height / 2), 0f);
        iconAnimator.setDuration(DURATION);

        ObjectAnimator loginView = ObjectAnimator.ofFloat(binding.llLogin, "translationY",
                (float) (height / 2), 0f);
        loginView.setDuration(DURATION);

        ObjectAnimator signUpView = ObjectAnimator.ofFloat(binding.llSignUp, "translationY",
                (float) (height / 2), 0f);
        signUpView.setDuration(DURATION);

        AnimatorSet iconAnimatorSet = new AnimatorSet();
        iconAnimatorSet.play(iconAnimator);
        iconAnimatorSet.start();

        AnimatorSet viewsAnimator = new AnimatorSet();
        viewsAnimator.play(loginView).with(signUpView);

        iconAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                binding.rlHolder.setVisibility(View.VISIBLE);
                viewsAnimator.start();
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
