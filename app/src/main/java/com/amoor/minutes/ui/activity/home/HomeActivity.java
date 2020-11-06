package com.amoor.minutes.ui.activity.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amoor.minutes.R;
import com.amoor.minutes.data.model.profile.Profile;
import com.amoor.minutes.data.rest.ApiServices;
import com.amoor.minutes.ui.activity.user.LoginActivity;
import com.amoor.minutes.ui.fragment.homeCycle.HomeCycleFragment;
import com.amoor.minutes.ui.fragment.homeCycle.nav.AboutUsFragment;
import com.amoor.minutes.ui.fragment.homeCycle.nav.FeedbackFragment;
import com.amoor.minutes.ui.fragment.homeCycle.nav.ProfileFragment;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.amoor.minutes.data.rest.RetrofitClient.getClient;
import static com.amoor.minutes.helper.HelperMethod.replace;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.Home_Activity_Tv_No_Buses)
    TextView HomeActivityTvNoBuses;
    private SharedPreferences preferences;
    private ApiServices apiServices;
    private ProgressDialog progressDialog;
    private TextView studentName;
    private CircleImageView studentImage;
    private static final String PHONE_CALL = Manifest.permission.CALL_PHONE;
    private boolean aPhoneCallPermissionGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        giveCallPhonePermission();
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);

        apiServices = getClient().create(ApiServices.class);
        progressDialog = new ProgressDialog(this);

        preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String access_token = preferences.getString("access_token", "");
        getUserData(access_token);

        HomeCycleFragment homeCycleFragment = new HomeCycleFragment();
        replace(homeCycleFragment, getSupportFragmentManager(), R.id.Home_Cycle_Fragment_Container);


        View headerView = navView.getHeaderView(0);
        studentName = headerView.findViewById(R.id.nav_student_name);
        studentImage = headerView.findViewById(R.id.nav_student_image);


    }

    private void getUserData(String access_token)
    {
        progressDialog.setMessage("انتظر قليلا يتم تحميل بياناتك ... ");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        apiServices.getProfileData(access_token).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Profile profileData = response.body();
                    Glide.with(getApplicationContext()).load("http://gtex.ddns.net/busbooking-system/data/include/uploads/" + profileData.getImage()).into(studentImage);
                    studentName.setText(profileData.getName());
                    String line = profileData.getLine();
                    String unviersity = profileData.getUniversity();
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("line", line);
                    edit.putString("university", unviersity);
                    edit.apply();

                } else {
                    progressDialog.dismiss();
                    HomeActivityTvNoBuses.setVisibility(View.VISIBLE);
                    HomeActivityTvNoBuses.setText("النت ضعيف");
                }


            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t)
            {
                progressDialog.dismiss();
                HomeActivityTvNoBuses.setVisibility(View.VISIBLE);
                HomeActivityTvNoBuses.setText("النت فاصل او السيرفر مش شغال");
            }
        });
    }

    private void giveCallPhonePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(PHONE_CALL) != PackageManager.PERMISSION_GRANTED) {
                if (!aPhoneCallPermissionGranted) {
                    requestPermissions(new String[]{PHONE_CALL}, 10);
                }

            }


        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            replace(profileFragment, getSupportFragmentManager(), R.id.Home_Cycle_Fragment_Container);

            toolbar.setTitle(R.string.profile);
        } else if (id == R.id.nav_about_us) {
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            replace(aboutUsFragment, getSupportFragmentManager(), R.id.Home_Cycle_Fragment_Container);
            toolbar.setTitle(R.string.about_us);

        } else if (id == R.id.nav_feedback) {
            FeedbackFragment feedbackFragment = new FeedbackFragment();
            replace(feedbackFragment, getSupportFragmentManager(), R.id.Home_Cycle_Fragment_Container);
            toolbar.setTitle(R.string.feedback);

        } else if (id == R.id.nav_home) {

//            HomeCycleFragment homeCycleFragment = new HomeCycleFragment();
//            replace(homeCycleFragment, getSupportFragmentManager(), R.id.Home_Cycle_Fragment_Container);
//            toolbar.setTitle(R.string.app_name);
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        } else if (id == R.id.nav_exit) {
            preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("status", false);
            edit.putString("access_token", "");
            edit.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults != null && grantResults.length > 1) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{PHONE_CALL}, 10);
                }

            }
        }
    }


}



