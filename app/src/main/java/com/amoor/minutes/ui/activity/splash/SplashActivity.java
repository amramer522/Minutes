package com.amoor.minutes.ui.activity.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.helper.HelperMethod;
import com.amoor.minutes.ui.activity.noInternet.NoInternetActivity;
import com.amoor.minutes.ui.activity.home.HomeActivity;
import com.amoor.minutes.ui.activity.user.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_spinner)
    ImageView splashSpinner;
    @BindView(R.id.splash_activity_iv_logo)
    ImageView splashActivityIvLogo;
    private SharedPreferences preferences;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        final boolean status = preferences.getBoolean("status", false);

//        Glide.with(this).load("https://tenor.com/view/loading-gif-11276352").into(splashActivityIvLogo);

//        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
//        animation.setDuration(2000);
//        splashSpinner.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                {
//                    //we are connected to a network
////                    connected = true;
//                    Toast.makeText(SplashActivity.this, "فيه نت ياغالى ", Toast.LENGTH_SHORT).show();
                    if (status) {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                } else {
//                    connected = false;
                    Toast.makeText(SplashActivity.this, "مفيش نت ياغالى ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), NoInternetActivity.class));
                    finish();
//
                }




            }
        }, 2000);

    }
}
