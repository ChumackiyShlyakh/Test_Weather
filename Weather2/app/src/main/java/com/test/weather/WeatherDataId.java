package com.test.weather;

public class WeatherDataId {

    private int id;
    private String countryName;
    private String cityName;
    private String temper;
    private String date;
    private String latitude;
    private String longitude;

    public WeatherDataId(int id, String countryName, String cityName, String temper, String date,
                         String latitude, String longitude) {
        this.id = id;
        this.countryName = countryName;
        this.cityName = cityName;
        this.temper = temper;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
