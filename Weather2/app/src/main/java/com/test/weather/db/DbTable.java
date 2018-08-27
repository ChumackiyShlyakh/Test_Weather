package com.test.weather.db;

public class DbTable {
    public static final String DB_NAME = "weather";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "city_weather";

    public static final String ID = "_id";
    public static final String COUNTRY_NAME = "country_name";
    public static final String CITY_NAME = "city_name";
    public static final String TEMPER = "state_name";
    public static final String DATE = "state_date";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public static final String DB_CREATE =
            String.format("create table %s ("
                            + "%s integer primary key autoincrement,"
                            + "%s text," + "%s text," + "%s text,"
                            + "%s text," + "%s text,"
                            + "%s text" + ");",
                    DB_TABLE, ID, COUNTRY_NAME, CITY_NAME, TEMPER, DATE, LATITUDE, LONGITUDE);
}
