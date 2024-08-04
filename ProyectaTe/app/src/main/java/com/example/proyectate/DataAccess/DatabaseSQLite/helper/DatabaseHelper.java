package com.example.proyectate.DataAccess.DatabaseSQLite.helper;

import static com.example.proyectate.Utils.Constants.CREATE_TABLE_PROJECTS;
import static com.example.proyectate.Utils.Constants.CREATE_TABLE_USERS;
import static com.example.proyectate.Utils.Constants.TABLE_PROJECTS;
import static com.example.proyectate.Utils.Constants.TABLE_USERS;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "proyectate.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROJECTS);
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROJECTS);
        onCreate(db);
    }
}
