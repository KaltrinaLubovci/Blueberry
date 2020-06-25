package com.kl.blueberry.ui.splash_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import com.kl.blueberry.R;
import com.kl.blueberry.data.AppPreferences;
import com.kl.blueberry.databinding.SplashScreenActivityBinding;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.ui.MainActivity;
import com.kl.blueberry.ui.sign_up.SignUpActivity;
import com.kl.blueberry.ui.signin.SignInActivity;
import com.kl.blueberry.utils.ParentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import static com.kl.blueberry.utils.Usage.showToast;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SplashScreenActivity extends ParentActivity {

    SplashScreenActivityBinding binding;
    @Inject
    AppPreferences appPreferences;
    ProgressBar pbSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen_activity);
        binding.setViewModel(new SplashScreenViewModel());
        pbSplashScreen = binding.pbSplashScreen;
        String username = appPreferences.getEmail();
        if (username == null){
            animateObjects();
        } else {
            LoadAsyncTask loadTask = new LoadAsyncTask(this);
            loadTask.execute(10);
        }

        onClicks();
    }


    private void onClicks(){

        binding.llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenActivityEvent(new SignInActivity()));
            }
        });

        binding.llSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenActivityEvent(new SignUpActivity()));
            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OpenActivityEvent openActivityEvent){
        Intent intent = new Intent(this, openActivityEvent.getActivity().getClass());
        startActivity(intent);
    }


    private static class LoadAsyncTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<SplashScreenActivity> activityWeakReference;
        LoadAsyncTask(SplashScreenActivity activity) {
            activityWeakReference = new WeakReference<SplashScreenActivity>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SplashScreenActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.pbSplashScreen.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) / integers[0]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            SplashScreenActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.pbSplashScreen.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SplashScreenActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.pbSplashScreen.setProgress(0);
            activity.pbSplashScreen.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
