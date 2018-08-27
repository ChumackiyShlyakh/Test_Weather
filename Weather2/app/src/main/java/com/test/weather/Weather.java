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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.weather.api.WeatherAPI;
import com.test.weather.api.WeatherDay;
import com.test.weather.db.Db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Weather extends AppCompatActivity {

    ArrayList<WeatherDataId> weatherDataIds = new ArrayList<>();
    private WeatherAdapter weatherAdapter;
    RecyclerView weatherList;
    Db db;
    Cursor cursor;
    TextView tv_new_city, tv_deagree;
    Button btn_check_weather, btn_back;

    ImageView tvImage;
    WeatherAPI.ApiInterface api;
    String latit = "", lngit = "";
    double lat = 0.0;
    double lng = 0.0;
    private String str_get_cityname, str_get_countryname;
    String date;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        init();

        tvImage = (ImageView) findViewById(R.id.ivImage);

        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
    }

    private void init() {
        tv_new_city = (TextView) findViewById(R.id.tv_new_city);
        tv_deagree = (TextView) findViewById(R.id.tv_deagree);
        btn_check_weather = (Button) findViewById(R.id.btn_check_weather);
        btn_back = (Button) findViewById(R.id.btn_back);
        weatherList = (RecyclerView) findViewById(R.id.list);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  hh.mm");
        date = formatter.format(today);

        data = new Data();

        str_get_countryname = data.data_country_name;
        str_get_cityname = data.data_city_name;
        tv_new_city.setText(data.data_country_name + ", " + data.data_city_name);
        btn_check_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather(v);
                btn_check_weather.setVisibility(View.GONE);
                btn_back.setVisibility(View.VISIBLE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.data_country_name = "";
                data.data_city_name = "";
                Intent intentBack = new Intent(Weather.this, Places.class);
                startActivity(intentBack);
                btn_check_weather.setVisibility(View.GONE);
                Weather.this.finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        weatherList.setLayoutManager(layoutManager);
        weatherList.setItemAnimator(new DefaultItemAnimator());

        weatherAdapter = new WeatherAdapter(this, weatherDataIds);
        db = new Db(this);

        latit = data.data_latitude;
        lngit = data.data_longitude;
        lat = Double.parseDouble(latit);
        lng = Double.parseDouble(lngit);
    }

    public void onBackPressed() {
        Intent intentBack = new Intent(Weather.this, Places.class);
        startActivity(intentBack);
        btn_check_weather.setVisibility(View.GONE);
        Weather.this.finish();
    }

    public void getWeather(View v) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String units = "metric";
                String key = WeatherAPI.KEY;

                // get weather for today
                Call<WeatherDay> callToday = api.getToday(lat, lng, units, key);
                callToday.enqueue(new Callback<WeatherDay>() {
                    @Override
                    public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                        WeatherDay data = response.body();

                        if (response.isSuccessful()) {
                            tv_deagree.setText(" " + data.getTempWithDegree());
                            Glide.with(Weather.this).load(data.getIconUrl()).into(tvImage);

                            db = new Db(Weather.this);
                            db.open();
                            db.addPlace(str_get_countryname, str_get_cityname, data.getTempWithDegree(), date,
                                    latit, lngit);
                            db.close();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherDay> call, Throwable t) {
                    }
                });
            }
        });
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
        weatherList.setAdapter(weatherAdapter);
        cursor.close();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWeather();
    }
}



