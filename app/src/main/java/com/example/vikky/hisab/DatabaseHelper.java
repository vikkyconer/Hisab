package com.example.vikky.hisab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vikky on 8/3/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "hisab";
    private static final String table_PLACES = "books";
    private static final String place_ID = "id";
    private static final String place_NAME = "Place Name";
    private static final String place_DAYS_AGO = "Days Ago";
    private static final String place_DATE = "date";

    private static final String[] COLUMNS = {place_ID, place_NAME, place_DAYS_AGO, place_DATE};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACE_TABLE = "CREATE TABLE places ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "Place Name TEXT, " + "Days Ago TEXT, " + "date TEXT" + ")";
        db.execSQL(CREATE_PLACE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS places");
        this.onCreate(db);
    }

    public void createPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(place_NAME, place.getPlaceName());
        values.put(place_DAYS_AGO, place.getDaysAgo());
        values.put(place_DATE, place.getPlaceDate());

        db.insert(table_PLACES, null, values);

        db.close();
    }

    public Place readPlace(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table_PLACES, COLUMNS, "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Place place = new Place();
        place.setPlaceId(Integer.parseInt(cursor.getString(0)));
        place.setPlaceName(cursor.getString(1));
        place.setDaysAgo(cursor.getString(2));
        place.setPlaceDate(cursor.getString(3));

        return place;
    }

    public List getAllPlaces() {
        List places = new LinkedList<>();

        String query = "SELECT * FROM " + table_PLACES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Place place = null;
        if (cursor.moveToFirst()) {
            do {
                place = new Place();
                place.setPlaceId(Integer.parseInt(cursor.getString(0)));
                place.setPlaceName(cursor.getString(1));
                place.setDaysAgo(cursor.getString(2));
                place.setPlaceDate(cursor.getString(3));
                places.add(place);
            } while (cursor.moveToNext());
        }
        return places;
    }

    public int updatePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Place Name", place.getPlaceName());
        values.put("Days Ago", place.getDaysAgo());
        values.put("date", place.getPlaceDate());
        int i = db.update(table_PLACES, values, place_ID + " = ?", new String[]{String.valueOf(place.getPlaceId())});

        db.close();
        return i;
    }

    public void deletePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table_PLACES, place_ID + " = ?", new String[]{String.valueOf(place.getPlaceId())});
        db.close();
    }
}
