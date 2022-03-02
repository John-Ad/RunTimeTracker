package com.example.runtimetracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.Date

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table Run("
                + "_id integer primary key autoincrement,"
                + "Run_Time text not null,"
                + "Run_Distance real not null,"
                + "Run_Date numeric not null);")
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        if (DB_VERSION <= 3) {
            db.execSQL("delete from Run;")
        }
        if (DB_VERSION == 3) {
            insertRun(db, "00:24:07:66", 3f)
            insertRun(db, "00:21:15:29", 3f)
            insertRun(db, "00:20:33:37", 3f)
            insertRun(db, "00:19:36:14", 3f)
            insertRun(db, "00:19:40:02", 3f)
            insertRun(db, "00:24:07:74", 3f)
            insertRun(db, "00:20:32:66", 3f)
            insertRun(db, "00:25:48:78", 3f)
            insertRun(db, "00:24:28:56", 3f)
            insertRun(db, "00:19:13:67", 3f)
            insertRun(db, "00:19:13:45", 3f)
            insertRun(db, "00:17:19:59", 3f)
            insertRun(db, "00:18:07:24", 3f)
            insertRun(db, "00:19:29:00", 3f)
            insertRun(db, "00:19:29:54", 3f)
            insertRun(db, "00:18:43:98", 3f)
            insertRun(db, "00:20:55:35", 3f)
            insertRun(db, "00:17:26:01", 3f)
            insertRun(db, "00:18:49:51", 3f)
            insertRun(db, "00:21:11:27", 3f)
            insertRun(db, "00:19:13:66", 3f)
            insertRun(db, "00:19:18:00", 3f)
            insertRun(db, "00:19:33:42", 3f)
            insertRun(db, "00:19:12:97", 3f)
            insertRun(db, "00:18:09:71", 3f)
            insertRun(db, "00:18:30:09", 3f)
            insertRun(db, "00:18:30:09", 3f)
            insertRun(db, "00:18:34:31", 3f)
        }
    }

    companion object {
        private const val DB_NAME = "Runs"
        private const val DB_VERSION = 4

        //-----------------------------
        //       INSERT RUN
        //-----------------------------
        fun insertRun(db: SQLiteDatabase, time: String?, dist: Float) {
            val runValues = ContentValues()
            runValues.put("Run_Time", time)
            runValues.put("Run_Distance", dist)
            runValues.put("Run_Date", Date(System.currentTimeMillis()).time)
            db.insert("Run", null, runValues)
        }

        //-----------------------------
        //       UPDATE RUN
        //-----------------------------
        fun updateRun(db: SQLiteDatabase, runID: Long, time: String?, dist: Float) {
            val runValues = ContentValues()
            runValues.put("Run_Time", time)
            runValues.put("Run_Distance", dist)
            db.update("Run", runValues, "_id = ?", arrayOf(runID.toString()))
        }

        //-----------------------------
        //       DELETE RUN
        //-----------------------------
        fun deleteRun(db: SQLiteDatabase, runID:Long) {
            db.delete("Run","_id = ?", arrayOf(runID.toString()))
        }
    }
}