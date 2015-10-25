package com.ideabank.vikky.hisab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private static final String TABLE_EXPENSE = "expenses";
    private static final String TABLE_PLACE_EXPENSE = "place_expenses";

    // Common column names
    private static final String KEY_ID = "id";
//    private static final String KEY_CREATED_AT = "created_at";

    // PLACES Table - column nmaes
    private static final String KEY_PLACE_NAME = "place_name";
    private static final String KEY_DAYSAGO = "daysAgo";
    private static final String KEY_DATE = "date";
    private static final String KEY_NO_OF_PEOPLE_WENT = "noOfPeopleWent";

    // FRIENDS Table - column names
    private static final String KEY_FRIEND_NAME = "friend_name";

    // PLACES_FRIENDS Table - column names
    private static final String KEY_PLACE_ID = "place_id";
    private static final String KEY_FRIEND_ID = "friend_id";

    //EXPENSES Table - column names
    private static final String KEY_WHO_PAID = "who_paid";
    private static final String KEY_FOR_WHOM = "for_whom";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_DESCRIPTION = "description";

    //PLACES_EXPENSES Table - column names
    //KEY_PLACE_ID... already defined
    private static final String KEY_EXPENSE_ID = "expenses_id";

    // Table Create Statements
    // Place table create statement
    private static final String CREATE_TABLE_PLACE = "CREATE TABLE "
            + TABLE_PLACE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PLACE_NAME
            + " TEXT," + KEY_DAYSAGO + " INTEGER," + KEY_DATE
            + " DATETIME," + KEY_NO_OF_PEOPLE_WENT + " INTEGER" + ")";

    // Friend table create statement
    private static final String CREATE_TABLE_FRIEND = "CREATE TABLE " + TABLE_FRIEND
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FRIEND_NAME
            + " TEXT" + ")";

    // place_friend table create statement
    private static final String CREATE_TABLE_PLACE_FRIND = "CREATE TABLE "
            + TABLE_PLACE_FRIEND + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PLACE_ID + " INTEGER," + KEY_FRIEND_ID + " INTEGER" + ")";

    //expenses table create statement
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE "
            + TABLE_EXPENSE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_WHO_PAID + " TEXT," + KEY_FOR_WHOM + " TEXT,"
            + KEY_AMOUNT + " INTEGER," + KEY_DESCRIPTION + " TEXT" + ")";

    //place_expenses table create statement
    private static final String CREATE_TABLE_PLACE_EXPENSES = "CREATE TABLE "
            + TABLE_PLACE_EXPENSE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PLACE_ID + " INTEGER," + KEY_EXPENSE_ID + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_PLACE);
        db.execSQL(CREATE_TABLE_FRIEND);
        db.execSQL(CREATE_TABLE_PLACE_FRIND);
        db.execSQL(CREATE_TABLE_EXPENSE);
        db.execSQL(CREATE_TABLE_PLACE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_FRIEND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_EXPENSE);

        // create new tables
        onCreate(db);
    }

    /*
 * Creating a place
 */
    public long createPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, place.getPlaceName());
        values.put(KEY_DAYSAGO, place.getDaysAgo());
        values.put(KEY_DATE, place.getPlaceDate());
        values.put(KEY_NO_OF_PEOPLE_WENT, place.getNoOfPeopleWent());
//        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long place_id = db.insert(TABLE_PLACE, null, values);

       /* // assigning friends to place
        for (long friend_id : friend_ids) {
            createPlaceFriend(place_id, friend_id);
        }
*/
        return place_id;
    }

    //update place

    public int updatePlace(long place_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Place place = getPlace(place_id);

        ContentValues values = new ContentValues();
        values.put(KEY_NO_OF_PEOPLE_WENT, place.getNoOfPeopleWent());

        // updating row
        return db.update(TABLE_PLACE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(place.getPlaceId())});
    }


    /*
 * get single Place
 */
    public Place getPlace(long place_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PLACE + " WHERE "
                + KEY_ID + " = " + place_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Place place = new Place();
        place.setPlaceId(c.getInt(c.getColumnIndex(KEY_ID)));
        place.setPlaceName((c.getString(c.getColumnIndex(KEY_PLACE_NAME))));
        place.setPlaceDate((c.getString(c.getColumnIndex(KEY_DATE))));
        place.setDaysAgo(Integer.parseInt((c.getString(c.getColumnIndex(KEY_DAYSAGO)))));
        place.setNoOfPeopleWent(Integer.parseInt(c.getString(c.getColumnIndex(KEY_NO_OF_PEOPLE_WENT))));
//        place.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return place;
    }

    /*
 * getting all places
 * */
    public List<Place> getAllPlaces() {
        List<Place> places = new ArrayList<Place>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLACE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Place place = new Place();
                place.setPlaceId(c.getInt((c.getColumnIndex(KEY_ID))));
                place.setPlaceName((c.getString(c.getColumnIndex(KEY_PLACE_NAME))));
                place.setPlaceDate((c.getString(c.getColumnIndex(KEY_DATE))));
                place.setDaysAgo(Integer.parseInt((c.getString(c.getColumnIndex(KEY_DAYSAGO)))));
                place.setNoOfPeopleWent(Integer.parseInt(c.getString(c.getColumnIndex(KEY_NO_OF_PEOPLE_WENT))));
//                place.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to place list
                places.add(place);
            } while (c.moveToNext());
        }

        return places;
    }

    private long createPlaceFriend(long place_id, long friend_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_ID, place_id);
        values.put(KEY_FRIEND_ID, friend_id);
//        values.put(KEY_CREATED_AT, getDateTime());

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

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * getting place count
     */
    public int getPlaceCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLACE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getFriendsCount(long place_id) {
        return 0;
    }

    /*
* Creating a friend
*/
    public long createFriend(Friend friend, long place_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FRIEND_NAME, friend.getName());
//        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long friend_id = db.insert(TABLE_FRIEND, null, values);

        // assigning friends to place
//        for (long place_id : friend_ids) {
        createPlaceFriend(place_id, friend_id);
//        }
        return friend_id;
    }

    public long getFriendId(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_FRIEND + " WHERE "
                + KEY_FRIEND_NAME + " LIKE " + "'" + name + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        return c.getLong(c.getColumnIndex(KEY_ID));
    }

    /*
     * getting all friends under place
     * */
    public List<Friend> getAllFriendsByPlace(String place_id) {
        List<Friend> friends = new ArrayList<Friend>();

        String selectQuery = "SELECT  * FROM " + TABLE_FRIEND + " friend, "
                + TABLE_PLACE + " place, " + TABLE_PLACE_FRIEND + " place_friend WHERE place."
                + KEY_ID + " = '" + place_id + "'" + " AND place." + KEY_ID
                + " = " + "place_friend." + KEY_PLACE_ID + " AND friend." + KEY_ID + " = "
                + "place_friend." + KEY_FRIEND_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Friend friend = new Friend();
                friend.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                friend.setName((c.getString(c.getColumnIndex(KEY_FRIEND_NAME))));
//                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to friend list
                friends.add(friend);
            } while (c.moveToNext());
        }

        return friends;
    }

    /*
     * get friend
     */

    public Friend getFriend(long friend_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FRIEND + " WHERE "
                + KEY_ID + " = " + friend_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Friend friend = new Friend();
        friend.setId(c.getColumnIndex(KEY_FRIEND_ID));
        friend.setName((c.getString(c.getColumnIndex(KEY_FRIEND_NAME))));
//        place.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return friend;
    }

    /*
 * Deleting a place
 */
    public void deletePlace(long place_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACE, KEY_ID + " = ?",
                new String[]{String.valueOf(place_id)});
        db.delete(TABLE_PLACE_FRIEND, KEY_PLACE_ID + " = ?",
                new String[]{String.valueOf(place_id)});
    }

   /* //create expenses
    public long createExpenses(long place_id,long friend_who_paid_id, long for_whom_id, int amount, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WHO_PAID, friend_who_paid_id);
        values.put(KEY_FOR_WHOM, for_whom_id);
        values.put(KEY_AMOUNT, amount);
        values.put(KEY_DESCRIPTION, desc);


        long expense_id = db.insert(TABLE_EXPENSE, null, values);

        createPlaceExpenses(place_id, expense_id);


        return expense_id;
    }
*/

    /*  * getting all expenses under place
      **/
    public List<TransactionDetails> getAllExpensesByPlace(String place_id) {
        List<TransactionDetails> transactionDetail = new ArrayList<TransactionDetails>();

        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE + " expense, "
                + TABLE_PLACE + " place, " + TABLE_PLACE_EXPENSE + " place_expense WHERE place."
                + KEY_ID + " = '" + place_id + "'" + " AND place." + KEY_ID
                + " = " + "place_expense." + KEY_PLACE_ID + " AND expense." + KEY_ID + " = "
                + "place_expense." + KEY_EXPENSE_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                TransactionDetails transactionDetails = new TransactionDetails();
                transactionDetails.setPlaceId(c.getInt((c.getColumnIndex(KEY_PLACE_ID))));
                transactionDetails.setAmount((c.getInt(c.getColumnIndex(KEY_AMOUNT))));
                transactionDetails.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                transactionDetails.setWhoPaid(c.getString(c.getColumnIndex(KEY_WHO_PAID)));

                // adding to friend list
                transactionDetail.add(transactionDetails);
            } while (c.moveToNext());
        }

        return transactionDetail;
    }


    //creating place_expenses
    private long createPlaceExpenses(long place_id, long expenses_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_ID, place_id);
        values.put(KEY_EXPENSE_ID, expenses_id);
//        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_PLACE_EXPENSE, null, values);

        return id;
    }
}
