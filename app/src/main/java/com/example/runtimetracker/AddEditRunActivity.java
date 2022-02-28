package com.example.runtimetracker;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditRunActivity extends Activity {

    public static final String TASK_TYPE_STR = "TASK_TYPE";
    public static final String RUN_TIME_STR = "RUN_TIME";
    public static final String RUN_DIST_STR = "RUN_DIST";
    public static final String RUN_DATE_STR = "RUN_DATE";
    public static final String RUN_ID_STR = "RUN_ID";

    public enum TASK_TYPE {
        ADD,
        EDIT
    }

    private class AddEditRunTask extends AsyncTask<TaskRun, Void, Boolean> {

        private TaskRun run;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((ProgressBar) findViewById(R.id.prg_add_edit)).setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(TaskRun... runs) {
            run = runs[0];

            SQLiteOpenHelper dbHelper = new DBHelper(AddEditRunActivity.this);
            try {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                switch (run.taskType) {
                    case ADD:
                        DBHelper.insertRun(db, run.runTime, run.runDistance);
                        break;
                    case EDIT:
                        DBHelper.updateRun(db, run.id, run.runTime, run.runDistance);
                        break;
                }
                return true;
            } catch (Exception ex) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            ((ProgressBar) findViewById(R.id.prg_add_edit)).setVisibility(View.INVISIBLE);

            if (success) {
                Toast.makeText(AddEditRunActivity.this, "Run saved", Toast.LENGTH_SHORT).show();
                clearInput();
            } else {
                Toast.makeText(AddEditRunActivity.this, "Failed to save run", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    //----   INSTANCE VARIABLES   ----

    private TASK_TYPE currentTaskType;
    private long runID;

    //--------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_run);

        initButtons();
        initTaskType();
    }

    //------------------------------------
    //       INIT BUTTONS
    //------------------------------------
    private void initButtons() {

        //----   SET SAVE BUTTON ONCLICK   ----
        Button saveButton = (Button) findViewById(R.id.btn_save_run);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    String runTime = ((EditText) findViewById(R.id.edt_run_time)).getText().toString();
                    String runDist = ((EditText) findViewById(R.id.edt_run_distance)).getText().toString();
                    TaskRun taskRun = new TaskRun(AddEditRunActivity.this.runID, runTime, Float.parseFloat(runDist), null, AddEditRunActivity.this.currentTaskType);
                    new AddEditRunTask().execute(taskRun);
                }
            }
        });

        //----   SET BACK BUTTON ONCLICK   ----
        Button backButton = (Button) findViewById(R.id.btn_add_run_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //------------------------------------
    //       INIT TASK TYPE
    //------------------------------------

    private void initTaskType() {
        Intent intent = getIntent();

        currentTaskType = (TASK_TYPE) intent.getSerializableExtra(TASK_TYPE_STR);
        this.runID=intent.getLongExtra(RUN_ID_STR,0);

        //----   GET RUN VALUES TO EDIT   ----
        if (currentTaskType == TASK_TYPE.EDIT) {
            ((EditText) findViewById(R.id.edt_run_time)).setText(intent.getStringExtra(RUN_TIME_STR));
            ((EditText) findViewById(R.id.edt_run_distance)).setText(intent.getStringExtra(RUN_DIST_STR));
            ((TextView)findViewById(R.id.txt_add_edit_run_title)).setText("Edit Run");
            ((Button)findViewById(R.id.btn_save_run)).setText("Update");
        }
    }

    //------------------------------------
    //       VALIDATE INPUT
    //------------------------------------
    private boolean validateInput() {
        String runTime = ((EditText) findViewById(R.id.edt_run_time)).getText().toString();
        String runDist = ((EditText) findViewById(R.id.edt_run_distance)).getText().toString();

        Log.i("validate input", runTime);
        Log.i("validate input", runDist);

        //if (runTime.length()==0) {
        if (runTime.isEmpty()) {
            Toast.makeText(this, "Enter a run time!", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (runDist.isEmpty()) {
            Toast.makeText(this, "Enter a distance!", Toast.LENGTH_SHORT).show();

            return false;
        }
        try {
            Float.parseFloat(runDist);
        } catch (Exception ex) {
            Toast.makeText(this, "Enter only numeric values for distance!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    //------------------------------------
    //       CLEAR INPUT
    //------------------------------------
    private void clearInput() {
        ((EditText) findViewById(R.id.edt_run_time)).setText("");
        ((EditText) findViewById(R.id.edt_run_distance)).setText("");
    }

}