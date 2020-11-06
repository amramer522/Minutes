package com.amoor.minutes.ui.activity.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.data.model.locationSend.LocationSend;
import com.amoor.minutes.data.rest.ApiServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.amoor.minutes.data.rest.RetrofitClient.getClient;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;
    private LatLng currentLatlang;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;
    private ApiServices apiServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        givePermission();
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
        apiServices = getClient().create(ApiServices.class);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_map);
        mapFragment.getMapAsync(this);
        getCurrentLocation();

    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getCurrentLocation()
    {

        LocationManager mngr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationProvider provider = mngr.getProvider(LocationManager.GPS_PROVIDER);
        String providerName = provider.getName();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mngr.requestLocationUpdates(providerName, 10, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                LatLng latLng = new LatLng(lat, lng);
                setMarkerOnMap(latLng);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

    }

    private void setMarkerOnMap(LatLng latLng) {
        mMap.addMarker(new MarkerOptions()
                        .position(latLng)
//                        .title("Amoor")
//                        .snippet("I am a programmer")
//                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
        );
        currentLatlang = latLng;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f);
        mMap.moveCamera(cameraUpdate);
    }

    private void givePermission() {
        if (isMapsEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{FINE_LOCATION, COURSE_LOCATION}, 10);
                }
            }
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults != null && grantResults.length > 1) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{FINE_LOCATION, COURSE_LOCATION}, 10);
                }

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                setMarkerOnMap(latLng);
                currentLatlang = latLng;

            }
        });
    }


    @OnClick(R.id.dialog_map_save_your_position)
    public void onViewClicked()
    {

        SharedPreferences.Editor editor = preferences.edit();
        if (currentLatlang!=null)
        {
            editor.putString("current_location_lat", currentLatlang.latitude+"");
            editor.putString("current_location_long", currentLatlang.longitude+"");
            editor.apply();

            String access_token = preferences.getString("access_token", "");
            String driver_id = preferences.getString("driver_id", "");
            String driver_time = preferences.getString("driver_time", "");
            String note = preferences.getString("note", "");


//            // get county name
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addresses = null;
//            try {
//                addresses = geocoder.getFromLocation(currentLatlang.latitude, currentLatlang.longitude, 1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String cityName = addresses.get(0).getAddressLine(0);
////            String stateName = addresses.get(0).getAddressLine(1);
////            String countryName = addresses.get(0).getAddressLine(2);
//
//            Toast.makeText(this, "City Name" + cityName, Toast.LENGTH_SHORT).show();
//

            sendStudentLocation(access_token,driver_id,driver_time,note,currentLatlang.latitude,currentLatlang.longitude);
        }
        else
        {
            Toast.makeText(this, "please select your position", Toast.LENGTH_SHORT).show();
        }




    }

    private void sendStudentLocation(String access_token, String driver_id, String driver_time, String note, double lat, double longt)
    {
        apiServices.sendStudentLocation(access_token,driver_id,driver_time,note,lat,longt).enqueue(new Callback<LocationSend>() {
            @Override
            public void onResponse(Call<LocationSend> call, Response<LocationSend> response)
            {
                if (response.isSuccessful())
                {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                    Toast.makeText(MapActivity.this, "Request sent", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(MapActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LocationSend> call, Throwable t)
            {
                Toast.makeText(MapActivity.this, "onFailure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        Toast.makeText(this, "Request Canceled", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();
    }
}
