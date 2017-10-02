package com.plasto.dealerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.utils.Constants;
import com.plasto.dealerapp.utils.SharedPrefUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgLogo = (ImageView) findViewById(R.id.logo);
        //showAmin();
        SharedPrefUtils utils = new SharedPrefUtils();

        String userDetails = utils.getFromPrefs(SplashActivity.this, Constants.SP_KEY_USER_DETAILS_JSON, null);

        if (userDetails == null) {
            Intent intent = new Intent(this, LoginActivity.class);//LoginActivity
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(this, MainDashboardActivity.class));
            finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void showAmin() {
        ViewCompat.animate(imgLogo)
                .translationY(-250)
                .setStartDelay(100)
                .setDuration(3000).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();
/*
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(findViewById(R.id.icon))
                    .scaleX(1.0F)
                    .scaleY(1.0F)
                    .alpha(1.0F)
                    .setInterpolator(new DecelerateInterpolator(1.2f))
                    .withLayer()
                    .setListener(null)
                    .start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(button.getContext(),
                    android.support.design.R.anim.fab_in);
            anim.setDuration(200L);
            anim.setInterpolator(INTERPOLATOR);
            button.startAnimation(anim);
        }*/
    }

    private void animate() {
        ImageView logoImageView = (ImageView) findViewById(R.id.logo);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        ViewCompat.animate(logoImageView)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(v instanceof Button)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }
}
