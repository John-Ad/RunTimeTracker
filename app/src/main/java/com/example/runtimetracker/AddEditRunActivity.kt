package com.example.runtimetracker

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class AddEditRunActivity : Activity(), AddEditRunTask.AddEditRunTaskListener {
    enum class TASK_TYPE {
        ADD, EDIT, DELETE
    }

    //----   INSTANCE VARIABLES   ----
    private var currentTaskType: TASK_TYPE? = null
    private var runID: Long = 0

    //--------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_run)
        initButtons()
        initTaskType()
    }

    //------------------------------------
    //       INIT BUTTONS
    //------------------------------------
    private fun initButtons() {

        //----   SET SAVE BUTTON ONCLICK   ----
        val saveButton = findViewById<View>(R.id.btn_save_run) as Button
        saveButton.setOnClickListener {
            if (validateInput()) {
                val runTime = (findViewById<View>(R.id.edt_run_time) as EditText).text.toString()
                val runDist = (findViewById<View>(R.id.edt_run_distance) as EditText).text.toString()
                val taskRun = TaskRun(runID, runTime, runDist.toFloat(), null, currentTaskType!!)
                AddEditRunTask(this).execute(taskRun)
            }
        }

        //----   SET BACK BUTTON ONCLICK   ----
        val backButton = findViewById<View>(R.id.btn_add_run_back) as Button
        backButton.setOnClickListener { finish() }
    }

    //------------------------------------
    //       INIT TASK TYPE
    //------------------------------------
    private fun initTaskType() {
        val intent = intent
        currentTaskType = intent.getSerializableExtra(TASK_TYPE_STR) as TASK_TYPE?
        runID = intent.getLongExtra(RUN_ID_STR, 0)

        //----   GET RUN VALUES TO EDIT   ----
        if (currentTaskType == TASK_TYPE.EDIT) {
            (findViewById<View>(R.id.edt_run_time) as EditText).setText(intent.getStringExtra(RUN_TIME_STR))
            (findViewById<View>(R.id.edt_run_distance) as EditText).setText(intent.getStringExtra(RUN_DIST_STR))
            (findViewById<View>(R.id.txt_add_edit_run_title) as TextView).text = "Edit Run"
            (findViewById<View>(R.id.btn_save_run) as Button).text = "Update"
        }
    }

    //------------------------------------
    //       VALIDATE INPUT
    //------------------------------------
    private fun validateInput(): Boolean {
        val runTime = (findViewById<View>(R.id.edt_run_time) as EditText).text.toString()
        val runDist = (findViewById<View>(R.id.edt_run_distance) as EditText).text.toString()
        Log.i("validate input", runTime)
        Log.i("validate input", runDist)

        //if (runTime.length()==0) {
        if (runTime.isEmpty()) {
            Toast.makeText(this, "Enter a run time!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (runDist.isEmpty()) {
            Toast.makeText(this, "Enter a distance!", Toast.LENGTH_SHORT).show()
            return false
        }
        try {
            runDist.toFloat()
        } catch (ex: Exception) {
            Toast.makeText(this, "Enter only numeric values for distance!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    //-------------------------------------------------
    //       ADD/EDIT LISTENER INTERFACE OVERRIDES
    //-------------------------------------------------
    override fun clearInput() {
        (findViewById<View>(R.id.edt_run_time) as EditText).setText("")
        (findViewById<View>(R.id.edt_run_distance) as EditText).setText("")
    }

    override fun getCallerContext(): Context? {
        return applicationContext
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    //-------------------------------------------------
    //-------------------------------------------------


    companion object {
        const val TASK_TYPE_STR = "TASK_TYPE"
        const val RUN_TIME_STR = "RUN_TIME"
        const val RUN_DIST_STR = "RUN_DIST"
        const val RUN_DATE_STR = "RUN_DATE"
        const val RUN_ID_STR = "RUN_ID"
    }
}