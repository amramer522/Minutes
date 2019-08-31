package com.amoor.minutes.ui.fragment.homeCycle.tab;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.amoor.minutes.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class NavigationFragment extends Fragment implements OnMapReadyCallback{


    private static final String TAG = "MapFragment";
    private GoogleMap mMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;


    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        givePermission();
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);




//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.navigation_map);
//        mapFragment.getMapAsync(this);
//        getCurrentLocation();


        return root;
    }

    private void getCurrentLocation()
    {

        Log.i(TAG, "getCurrentLocation Method: ok ");
        LocationManager mngr = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        LocationProvider provider = mngr.getProvider(LocationManager.GPS_PROVIDER);
        String providerName = provider.getName();
        if (ActivityCompat.checkSelfPermission(getContext(), FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;


        }



//        mMap.setMyLocationEnabled(true);
        Log.i(TAG, "getCurrentLocation: "+mngr);
        mngr.requestLocationUpdates(providerName, 10, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                Log.i(TAG, "onLocationChanged: lat : "+lat);
                double lng = location.getLongitude();
                Log.i(TAG, "onLocationChanged: lng : "+lng);
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

    private void givePermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            Log.i(TAG, "givePermission: > version 22");
            if (getContext().checkSelfPermission(FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{FINE_LOCATION}, 10);

            }
        }
        else
        {
            Log.i(TAG, "givePermission: <= version 22 ");
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i(TAG, "onMapReady: ok");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length > 0)
        {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{FINE_LOCATION}, 10);
            }
        }
    }

    private void setMarkerOnMap(LatLng latLng)
    {
        Log.i(TAG, "setMarkerOnMap: ok");
        // دول لو عايز تعمل aniation وهوا رايح للمكان
        //        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mMap.clear(); //clear old markers
//
//        CameraPosition googlePlex = CameraPosition.builder()
//                // المفروض هنا بقى هديله الcurrent location بتاعه عشان يعمل زوم عليه
//                .target(new LatLng(30.7461214, 31.2486796))
//                .zoom(16)
//                .bearing(0)
//                .tilt(45)
//                .build();
//
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

        Toast.makeText(getContext(), "setMarkerOnMap ok", Toast.LENGTH_SHORT).show();
        // مكان ماركر على الخريطه
        mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(30.7461214, 31.2486796))
                        .title("Amoor")
                        .snippet("I am a programmer")
//                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
        );
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f);
        mMap.moveCamera(cameraUpdate);


    }
}
