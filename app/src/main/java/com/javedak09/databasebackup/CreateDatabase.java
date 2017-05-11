package com.javedak09.databasebackup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by javed.khan on 5/11/2017.
 */

public class CreateDatabase extends SQLiteOpenHelper {

    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS info ("
            + " id INTEGER,"
            + " nme TEXT);";

    public static final String SQL_DELETE_TABLE = "DELETE TABLE IF EXISTS info ";

    private static final String TAG = "Sec1";
    public static final String DATABASE_NAME = "mapps.db";
    private static final int DATABASE_VERSION = 3;

    public CreateDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}