package com.example.adul.loginsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by adul on 18/04/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "user.db";
    public static final String TABLE_NAME = "users_admin";
    public static final String COL_ID = "_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final int DB_VERSION = 2;
    private SQLiteDatabase db;
    private static String DB_PATH ="/data/data/"+BuildConfig.APPLICATION_ID+"/databases/";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context ctx;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        ctx = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            try {
                createdatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(dbexist) {
            System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = ctx.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(db != null) {
            db.close();
        }
        super.close();
    }
//    public void CopyDataBaseFromAsset() throws IOException {
//
//        InputStream myInput = ctx.getAssets().open(DB_NAME);
//
//        // Path to the just created empty db
//        String outFileName = getDatabasePath();
//
//        // if the path doesn't exist first, create it
//        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH);
//        if (!f.exists())
//            f.mkdir();
//
//        // Open the empty db as the output stream
//        OutputStream myOutput = new FileOutputStream(outFileName);
//
//        // transfer bytes from the inputfile to the outputfile
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = myInput.read(buffer)) > 0) {
//            myOutput.write(buffer, 0, length);
//        }
//
//        // Close the streams
//        myOutput.flush();
//        myOutput.close();
//        myInput.close();
//
//    }
//    private static String getDatabasePath() {
//        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
//                + DB_NAME;
//    }
//
//    public SQLiteDatabase openDataBase() throws SQLException {
//        File dbFile = ctx.getDatabasePath(DB_NAME);
//
//        if (!dbFile.exists()) {
//            try {
//                CopyDataBaseFromAsset();
//                System.out.println("Copying sucess from Assets folder");
//            } catch (IOException e) {
//                throw new RuntimeException("Error creating source database", e);
//            }
//        }
//
//        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int updateNamaSantri(String id, String nama, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        //prepare content values
        ContentValues values = new ContentValues();

        //put the value
        values.put(COL_USERNAME, nama);
        values.put(COL_PASSWORD, pass);

        // updating row
        return db.update(TABLE_NAME, values, COL_ID + " = ?",
                new String[] { id });
    }

    public Cursor readAllDataSantri(){
        SQLiteDatabase db  = this.getWritableDatabase();

        //query to get the data
        Cursor result = db.rawQuery("select * from "+TABLE_NAME,null);
        return result;
    }

    public int updateEntry(String userNameOld,String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues updatedValues = new ContentValues();
        updatedValues.put("username", userName);
        updatedValues.put("password", password);

        String where = "username = ?";
        return db.update(TABLE_NAME, updatedValues, where, new String[] { userNameOld });
    }

    public String searchPass(String username) {
        db = this.getWritableDatabase();
        String query = "select username, password from "+TABLE_NAME;
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
