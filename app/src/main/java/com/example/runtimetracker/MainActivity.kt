package com.example.runtimetracker

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import com.example.runtimetracker.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import com.example.runtimetracker.AddEditRunActivity
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import com.example.runtimetracker.RunDetails
import android.widget.TextView
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.view.View
import android.widget.ListView
import com.example.runtimetracker.DBHelper
import com.example.runtimetracker.RunCursorAdapter
import android.widget.Toast
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : Activity() {
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onStart() {
        super.onStart()
        runs
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor!!.close()
        db!!.close()
    }

    //------------------------------------
    //       INIT METHOD
    //------------------------------------
    private fun init() {

        //----   SET FAB ONCLICK   ----
        val floatingActionButton = findViewById<View>(R.id.fab_add_run) as FloatingActionButton
        floatingActionButton.setOnClickListener { view -> //----   START ADD/EDIT ACTIVITY   ----
            val intent = Intent(view.context, AddEditRunActivity::class.java)
            intent.putExtra(AddEditRunActivity.TASK_TYPE_STR, AddEditRunActivity.TASK_TYPE.ADD)
            startActivity(intent)
        }

        //----   SET LISTVIEW ON ITEM CLICK   ----
        (findViewById<View>(R.id.runs_list) as ListView).onItemClickListener =
            OnItemClickListener { adapterView, view, index, id ->
                val intent = Intent(this@MainActivity, RunDetails::class.java)
                val cursor = adapterView.getItemAtPosition(index) as SQLiteCursor
                intent.putExtra(AddEditRunActivity.RUN_ID_STR, id)
                intent.putExtra(AddEditRunActivity.RUN_TIME_STR, cursor.getString(2))
                intent.putExtra(AddEditRunActivity.RUN_DIST_STR, cursor.getString(3))
                intent.putExtra(AddEditRunActivity.RUN_DATE_STR, SimpleDateFormat("dd-MM-yyyy").format(Date(cursor.getLong(1))))
                startActivity(intent)
            }
    }

    //------------------------------------
    //       GET RUNS FROM DB
    //------------------------------------
    private val runs: Unit
        private get() {
            val dbHelper: SQLiteOpenHelper = DBHelper(this)
            try {
                db = dbHelper.readableDatabase
                cursor = db!!.rawQuery(
                    "select _id, Run_Date, Run_Time, Run_Distance from Run order by _id desc",
                    null
                )
//              SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
//                    R.layout.run_record,
//                    cursor,
//                    new String[]{"Run_Time", "Run_Distance", "Run_Date"},
//                    new int[]{R.id.txt_run_time, R.id.txt_run_dist, R.id.txt_run_date},
//                    0);
                val listAdapter = RunCursorAdapter(this, cursor, 0)
                (findViewById<View>(R.id.runs_list) as ListView).adapter = listAdapter
            } catch (ex: Exception) {
                Toast.makeText(this, "you're fucking trash: " + ex.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
}