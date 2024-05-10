package com.example.registor_login;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userData.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        String CREATE_TABLE_MY_DATA = "CREATE TABLE userInfo (" +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL" + ")";
        db.execSQL(CREATE_TABLE_MY_DATA);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userInfo");
        onCreate(db);
    }
}
