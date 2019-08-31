package com.amoor.minutes.ui.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amoor.minutes.R;
import com.amoor.minutes.helper.directionhelpers.FetchURL;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    private Button getDirection;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDirection = findViewById(R.id.direction);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(MainActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });

        place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.navigation_map);
//        mapFragment.getMapAsync(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);

//        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
//        LatLng place1 = new LatLng(30.742977, 31.2620049);
//        LatLng place2 = new LatLng(30.7456789, 31.2618976);
//
//
//        mMap.addMarker(new MarkerOptions().title("amr").position(place1).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo)));
//        mMap.addMarker(new MarkerOptions().title("ahmed").position(place2));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1, 18f));
//        mMap.addPolyline(new PolylineOptions().add(place1,place2));
//        mMap.getUiSettings().setZoomControlsEnabled(true);



    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_API_key);
        return url;
    }


//    @Override
//    public  void onTaskDone(Object... values) {
//        if (currentPolyline != null)
//            currentPolyline.remove();
//        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
//    }
}
