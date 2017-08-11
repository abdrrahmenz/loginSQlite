package com.example.adul.loginsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adul on 19/04/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "admin.db";
    public static final String TABLE_NAME = "admin";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_PASS = "pass";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table admin (id INTEGER PRIMARY KEY AUTOINCREMENT , " +
            "user text, pass text)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, pass TEXT)");
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insertUser(NameAdmin admin) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from admin";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_USER, admin.getUsername());
        values.put(COLUMN_PASS , admin.getPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //get All santri
    public Cursor readAllDataSantri(){
        SQLiteDatabase db  = this.getWritableDatabase();

        //query to get the data
        Cursor result = db.rawQuery("select * from "+TABLE_NAME,null);
        return result;
    }

    // Updating single santriModel
    public int updateNamaSantri(String id, String nama, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        //prepare content values
        ContentValues values = new ContentValues();

        //put the value
        values.put(COLUMN_USER, nama);
        values.put(COLUMN_PASS, pass);

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[] { id });
    }

    public String searchPass(String username) {
        db = this.getWritableDatabase();
        String query = "select user, pass from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String a, b;
        b = "not founds";
        if (cursor.moveToFirst()){
            do {
                a = cursor.getString(0);
                if (a.equals(username)){

                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return b;
    }
}
