package com.test.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Db {

    public static String orderBy = null;
    private final Context mCtx;
    private DbHelper mDBHelper;
    private SQLiteDatabase mDB;

    public Db(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        mDBHelper = new DbHelper(mCtx, DbTable.DB_NAME, null, DbTable.DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query(DbTable.DB_TABLE, null, null, null, null, null, orderBy);
    }

    public void addPlace(String countryName, String cityName, String temper, String date, String latitude, String longitude) {
        ContentValues cv = new ContentValues();
        cv.put(DbTable.COUNTRY_NAME, countryName);
        cv.put(DbTable.CITY_NAME, cityName);
        cv.put(DbTable.TEMPER, temper);
        cv.put(DbTable.DATE, date);
        cv.put(DbTable.LATITUDE, latitude);
        cv.put(DbTable.LONGITUDE, longitude);

        long rowID = mDB.insert(DbTable.DB_TABLE, null, cv);
    }

    public void delCity(long id) {
        mDB.delete(DbTable.DB_TABLE, DbTable.ID + " = " + id, null);
    }
}
