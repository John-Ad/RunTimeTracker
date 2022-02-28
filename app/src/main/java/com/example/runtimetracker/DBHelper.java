package com.example.runtimetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Runs";
    private static final int DB_VERSION = 3;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Run("
                + "_id integer primary key autoincrement,"
                + "Run_Time text not null,"
                + "Run_Distance real not null,"
                + "Run_Date numeric not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (DB_VERSION <= 3) {
            db.execSQL("delete from Run;");
        }
        if (DB_VERSION == 3) {
            insertRun(db, "00:24:07:66", 3);
            insertRun(db, "00:21:15:29", 3);
            insertRun(db, "00:20:33:37", 3);
            insertRun(db, "00:19:36:14", 3);
            insertRun(db, "00:19:40:02", 3);
            insertRun(db, "00:24:07:74", 3);
            insertRun(db, "00:20:32:66", 3);
            insertRun(db, "00:25:48:78", 3);
            insertRun(db, "00:24:28:56", 3);
            insertRun(db, "00:19:13:67", 3);
            insertRun(db, "00:19:13:45", 3);
            insertRun(db, "00:17:19:59", 3);
            insertRun(db, "00:18:07:24", 3);
            insertRun(db, "00:19:29:00", 3);
            insertRun(db, "00:19:29:54", 3);
            insertRun(db, "00:18:43:98", 3);
            insertRun(db, "00:20:55:35", 3);
            insertRun(db, "00:17:26:01", 3);
            insertRun(db, "00:18:49:51", 3);
            insertRun(db, "00:21:11:27", 3);
            insertRun(db, "00:19:13:66", 3);
            insertRun(db, "00:19:18:00", 3);
            insertRun(db, "00:19:33:42", 3);
            insertRun(db, "00:19:12:97", 3);
            insertRun(db, "00:18:09:71", 3);
            insertRun(db, "00:18:30:09", 3);
            insertRun(db, "00:18:30:09", 3);
            insertRun(db, "00:18:34:31", 3);
        }
    }

    //-----------------------------
    //       INSERT RUN
    //-----------------------------

    public static void insertRun(SQLiteDatabase db, String time, float dist) {
        ContentValues runValues = new ContentValues();
        runValues.put("Run_Time", time);
        runValues.put("Run_Distance", dist);
        runValues.put("Run_Date", new Date(System.currentTimeMillis()).getTime());
        db.insert("Run", null, runValues);
    }

    //-----------------------------
    //       UPDATE RUN
    //-----------------------------

    public static void updateRun(SQLiteDatabase db, long runID, String time, float dist) {
        ContentValues runValues = new ContentValues();
        runValues.put("Run_Time", time);
        runValues.put("Run_Distance", dist);
        db.update("Run", runValues, "_id = ?", new String[]{Long.toString(runID)});
    }

    //-----------------------------
    //       UPDATE RUN
    //-----------------------------

    public static void deleteRun(SQLiteDatabase db, String time, float dist) {
        ContentValues runValues = new ContentValues();
        runValues.put("Run_Time", time);
        runValues.put("Run_Distance", dist);
        runValues.put("Run_Date", new Date(System.currentTimeMillis()).getTime());
        db.insert("Run", null, runValues);
    }
}
