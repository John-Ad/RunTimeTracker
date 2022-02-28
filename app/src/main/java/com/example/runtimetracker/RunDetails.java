package com.example.runtimetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RunDetails extends Activity {

    private long runID;
    private String runTime;
    private String runDist;
    private String runDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_details);

        Intent intent = getIntent();
        runID = intent.getLongExtra(AddEditRunActivity.RUN_ID_STR, 0);
        runTime = intent.getStringExtra(AddEditRunActivity.RUN_TIME_STR);
        runDist = intent.getStringExtra(AddEditRunActivity.RUN_DIST_STR);
        runDate = intent.getStringExtra(AddEditRunActivity.RUN_DATE_STR);

        setDetails();
        initButtons();
    }

    //------------------------------------
    //       INIT BUTTONS
    //------------------------------------
    private void initButtons() {

        //----   SET UPDATE BUTTON ONCLICK   ----
        Button updateButton = (Button) findViewById(R.id.btn_run_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RunDetails.this,AddEditRunActivity.class);

                intent.putExtra(AddEditRunActivity.RUN_ID_STR, runID);
                intent.putExtra(AddEditRunActivity.RUN_TIME_STR, runTime);
                intent.putExtra(AddEditRunActivity.RUN_DIST_STR, runDist);
                intent.putExtra(AddEditRunActivity.RUN_DATE_STR, runDate);
                intent.putExtra(AddEditRunActivity.TASK_TYPE_STR, AddEditRunActivity.TASK_TYPE.EDIT);

                startActivity(intent);
            }
        });

        //----   SET DELETE BUTTON ONCLICK   ----
        Button deleteButton = (Button) findViewById(R.id.btn_run_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----   TO DO   ----
            }
        });

        //----   SET BACK BUTTON ONCLICK   ----
        Button backButton = (Button) findViewById(R.id.btn_run_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //------------------------------------
    //       INIT BUTTONS
    //------------------------------------
    private void setDetails(){
        ((TextView)findViewById(R.id.txt_run_time)).setText(this.runTime);
        ((TextView)findViewById(R.id.txt_run_dist)).setText(this.runDist);
        ((TextView)findViewById(R.id.txt_run_date)).setText(this.runDate);
    }
}