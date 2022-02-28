package com.example.runtimetracker;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getRuns();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    //------------------------------------
    //       INIT METHOD
    //------------------------------------
    private void init() {

        //----   SET FAB ONCLICK   ----
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_run);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----   START ADD/EDIT ACTIVITY   ----
                Intent intent = new Intent(view.getContext(), AddEditRunActivity.class);
                intent.putExtra(AddEditRunActivity.TASK_TYPE_STR, AddEditRunActivity.TASK_TYPE.ADD);

                startActivity(intent);
            }
        });

        //----   SET LISTVIEW ON ITEM CLICK   ----
        ((ListView) findViewById(R.id.runs_list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Intent intent = new Intent(MainActivity.this, RunDetails.class);

                intent.putExtra(AddEditRunActivity.RUN_ID_STR, id);
                intent.putExtra(AddEditRunActivity.RUN_TIME_STR, ((TextView) view.findViewById(R.id.txt_run_time)).getText());
                intent.putExtra(AddEditRunActivity.RUN_DIST_STR, ((TextView) view.findViewById(R.id.txt_run_dist)).getText());
                intent.putExtra(AddEditRunActivity.RUN_DATE_STR, ((TextView) view.findViewById(R.id.txt_run_date)).getText());

                startActivity(intent);
            }
        });
    }

    //------------------------------------
    //       GET RUNS FROM DB
    //------------------------------------
    private void getRuns() {
        SQLiteOpenHelper dbHelper = new DBHelper(this);
        try {
            this.db = dbHelper.getReadableDatabase();
            this.cursor = db.rawQuery("select _id, Run_Date, Run_Time, Run_Distance from Run order by _id desc", null);
//            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
//                    R.layout.run_record,
//                    cursor,
//                    new String[]{"Run_Time", "Run_Distance", "Run_Date"},
//                    new int[]{R.id.txt_run_time, R.id.txt_run_dist, R.id.txt_run_date},
//                    0);
            RunCursorAdapter listAdapter = new RunCursorAdapter(this, cursor, 0);
            ((ListView) findViewById(R.id.runs_list)).setAdapter(listAdapter);
        } catch (Exception ex) {
            Toast.makeText(this, "you're fucking trash: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}