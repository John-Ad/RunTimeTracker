package com.example.runtimetracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.runtimetracker.AddEditRunActivity

class RunDetails : Activity(), AddEditRunTask.AddEditRunTaskListener {
    private var runID: Long = 0
    private var runTime: String? = null
    private var runDist: String? = null
    private var runDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_details)
        val intent = intent
        runID = intent.getLongExtra(AddEditRunActivity.RUN_ID_STR, 0)
        runTime = intent.getStringExtra(AddEditRunActivity.RUN_TIME_STR)
        runDist = intent.getStringExtra(AddEditRunActivity.RUN_DIST_STR)
        runDate = intent.getStringExtra(AddEditRunActivity.RUN_DATE_STR)
        setDetails()
        initButtons()
    }

    //------------------------------------
    //       INIT BUTTONS
    //------------------------------------
    private fun initButtons() {

        //----   SET UPDATE BUTTON ONCLICK   ----
        val updateButton = findViewById<View>(R.id.btn_run_update) as Button
        updateButton.setOnClickListener {
            val intent = Intent(this@RunDetails, AddEditRunActivity::class.java)
            intent.putExtra(AddEditRunActivity.RUN_ID_STR, runID)
            intent.putExtra(AddEditRunActivity.RUN_TIME_STR, runTime)
            intent.putExtra(AddEditRunActivity.RUN_DIST_STR, runDist)
            intent.putExtra(AddEditRunActivity.RUN_DATE_STR, runDate)
            intent.putExtra(AddEditRunActivity.TASK_TYPE_STR, AddEditRunActivity.TASK_TYPE.EDIT)
            startActivity(intent)
        }

        //----   SET DELETE BUTTON ONCLICK   ----
        val deleteButton = findViewById<View>(R.id.btn_run_delete) as Button
        deleteButton.setOnClickListener {
            val taskRun = TaskRun(runID, "", 0f, null, AddEditRunActivity.TASK_TYPE.DELETE!!)
            AddEditRunTask(this).execute(taskRun)
        }

        //----   SET BACK BUTTON ONCLICK   ----
        val backButton = findViewById<View>(R.id.btn_run_back) as Button
        backButton.setOnClickListener { finish() }
    }

    //------------------------------------
    //       INIT BUTTONS
    //------------------------------------
    private fun setDetails() {
        (findViewById<View>(R.id.txt_run_time) as TextView).text = runTime
        (findViewById<View>(R.id.txt_run_dist) as TextView).text = runDist + " Km"
        (findViewById<View>(R.id.txt_run_date) as TextView).text = runDate
    }

    //-------------------------------------------------
    //       ADD/EDIT LISTENER INTERFACE OVERRIDES
    //-------------------------------------------------
    override fun returnToPrevScreen() {
        finish()
    }

    override fun clearInput() {
        this.clearInput()
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun getCallerContext(): Context? {
        return applicationContext
    }
    //-------------------------------------------------
    //-------------------------------------------------
}