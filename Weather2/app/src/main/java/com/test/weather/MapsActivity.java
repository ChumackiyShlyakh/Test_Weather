package com.test.weather;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    Button btn_map_ok, btn_map_cancel;
    String cityName, stateName, countryName;
    String latitude = "", longitude = "";
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        data = new Data();

        btn_map_ok = (Button) findViewById(R.id.btn_map_ok);
        btn_map_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data.data_country_name = countryName;
                data.data_city_name = cityName;
                data.data_latitude = latitude;
                data.data_longitude = longitude;
                Intent intentMap = new Intent(MapsActivity.this, Places.class);
                startActivity(intentMap);
                MapsActivity.this.finish();
            }
        });

        btn_map_cancel = (Button) findViewById(R.id.btn_map_cancel);
        btn_map_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
            }
        });
    }

    public void onBackPressed() {
        Intent intentMap = new Intent(MapsActivity.this, Places.class);
        startActivity(intentMap);
        MapsActivity.this.finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                LatLng coordinates = new LatLng(latLng.latitude, latLng.longitude);
                mMap.addMarker(new MarkerOptions().position(coordinates));

                latitude = "" + latLng.latitude;
                longitude = "" + latLng.longitude;
                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        cityName = address.getLocality();
                        stateName = address.getAdminArea();
                        countryName = address.getCountryName();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
