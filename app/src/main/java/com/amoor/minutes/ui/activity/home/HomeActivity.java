package com.amoor.minutes.ui.activity.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.ui.activity.user.LoginActivity;
import com.amoor.minutes.ui.fragment.homeCycle.nav.AboutUsFragment;
import com.amoor.minutes.ui.fragment.homeCycle.nav.FeedbackFragment;
import com.amoor.minutes.ui.fragment.homeCycle.nav.ProfileFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);





    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            ProfileFragment profileFragment = new ProfileFragment();
//            replace(profileFragment, getSupportFragmentManager(), R.id.Home_Cycle_Fragment_Container);

            toolbar.setTitle(R.string.profile);
        } else if (id == R.id.nav_about_us) {
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
//            replace(aboutUsFragment, getSupportFragmentManager(), R.id.Home_Cycle_Fragment_Container);
            toolbar.setTitle(R.string.about_us);

        } else if (id == R.id.nav_feedback) {

            FeedbackFragment feedbackFragment = new FeedbackFragment();
//            replace(feedbackFragment, getSupportFragmentManager(), R.id.Home_Cycle_Fragment_Container);
            toolbar.setTitle(R.string.feedback);

        } else if (id == R.id.nav_home) {

//            HomeCycleFragment homeCycleFragment = new HomeCycleFragment();
//            replace(homeCycleFragment, getSupportFragmentManager(), R.id.Home_Cycle_Fragment_Container);
//            toolbar.setTitle(R.string.app_name);
//            int count = getSupportFragmentManager().getBackStackEntryCount();
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//            finish();
        } else if (id == R.id.nav_exit) {
            preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("status", false);
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






}



