package com.amoor.minutes.ui.activity.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.amoor.minutes.R;
import com.amoor.minutes.ui.activity.home.HomeActivity;
import com.amoor.minutes.ui.activity.user.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_spinner)
    ImageView splashSpinner;
    private SharedPreferences preferences;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        final boolean status = preferences.getBoolean("status", false);


        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        animation.setDuration(2000);
        splashSpinner.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (status) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

            }
        }, 2000);

    }
}
