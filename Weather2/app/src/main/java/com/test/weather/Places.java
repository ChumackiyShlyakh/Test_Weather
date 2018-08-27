package com.test.weather;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.weather.db.Db;

import java.util.ArrayList;

public class Places extends AppCompatActivity {

    Button btn_add_place, btn_get_weather;
    TextView tv_cityname, tv_countryname;
    String str_cityname, str_countryname;
    RecyclerView list_places;
    private PlacesAdapter placesAdapter;
    Db db;
    Cursor cursor;
    ArrayList<WeatherDataId> weatherDataIds = new ArrayList<>();
    Data data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_layout);
        init();
    }

    private void init() {
        data = new Data();
        tv_cityname = (TextView) findViewById(R.id.tv_cityname);
        tv_countryname = (TextView) findViewById(R.id.tv_countryname);
        list_places = (RecyclerView) findViewById(R.id.list_places);

        btn_add_place = (Button) findViewById(R.id.btn_add_place);
        btn_add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Places.this, MapsActivity.class);
                startActivity(intent);
                Places.this.finish();
            }
        });
        btn_get_weather = (Button) findViewById(R.id.btn_get_weather);
        btn_get_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGetWeather = new Intent(Places.this, Weather.class);
                startActivity(intentGetWeather);
                Places.this.finish();
            }
        });

        str_countryname = data.data_country_name;
        str_cityname = data.data_city_name;

        tv_countryname.setText(str_countryname);
        tv_cityname.setText(str_cityname);

        if (str_countryname != "") {
            btn_get_weather.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        list_places.setLayoutManager(layoutManager);

        list_places.setItemAnimator(new DefaultItemAnimator());

        placesAdapter = new PlacesAdapter(this, weatherDataIds);

        db = new Db(this);
    }

    public void loadWeather() {
        db.open();
        cursor = db.getAllData();
        weatherDataIds.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String countryName = cursor.getString(1);
            String cityName = cursor.getString(2);
            String temper = cursor.getString(3);
            String date = cursor.getString(4);
            String latitude = cursor.getString(5);
            String longitude = cursor.getString(6);
            WeatherDataId weatherData = new WeatherDataId(id, countryName, cityName, temper, date,
                    latitude, longitude);
            weatherDataIds.add(weatherData);
        }
        list_places.setAdapter(placesAdapter);
        cursor.close();
        db.close();
    }

    public void onBackPressed() {
        Places.this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWeather();
    }
}