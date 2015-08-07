package com.example.vikky.hisab;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    // Table Names
    private static final String TABLE_PLACE = "places";
    private static final String TABLE_FRIEND = "friends";
    private static final String TABLE_PLACE_FRIEND = "place_friends";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // PLACES Table - column nmaes
    private static final String KEY_PLACE_NAME = "place_name";
    private static final String KEY_DAYSAGO = "daysAgo";
    private static final String KEY_DATE = "date";

    // FRIENDS Table - column names
    private static final String KEY_FRIEND_NAME = "friend_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_PLACE_ID = "place_id";
    private static final String KEY_FRIEND_ID = "friend_id";

    // Table Create Statements
    // Place table create statement
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_PLACE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PLACE_NAME
            + " TEXT," + KEY_DAYSAGO + " INTEGER," + KEY_DATE
            + " DATETIME" + KEY_CREATED_AT + "DATETIME" + ")";

    // Friend table create statement
    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_FRIEND
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FRIEND_NAME
            + " TEXT" + KEY_CREATED_AT + "DATETIME" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_PLACE_FRIND = "CREATE TABLE "
            + TABLE_PLACE_FRIEND + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PLACE_ID + " INTEGER," + KEY_FRIEND_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_PLACE_FRIND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_FRIEND);

        // create new tables
        onCreate(db);
    }

    /*
 * Creating a place
 */
    public long createPlace(Place place, long[] friend_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, place.getPlaceName());
        values.put(KEY_DAYSAGO, place.getDaysAgo());
        values.put(KEY_DATE, place.getPlaceDate());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long place_id = db.insert(TABLE_PLACE, null, values);

        // assigning friends to place
        for (long friend_id : friend_ids) {
            createPlaceFriend(place_id, friend_id);
        }

        return place_id;
    }

    
    private long createPlaceFriend(long place_id, long friend_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_ID, place_id);
        values.put(KEY_FRIEND_ID, friend_id);
        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_PLACE_FRIEND, null, values);

        return id;
    }

    /**
     * get datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
