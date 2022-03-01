package com.example.runtimetracker

import android.app.Activity
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.runtimetracker.AddEditRunActivity

class AddEditRunActivity : Activity() {
    enum class TASK_TYPE {
        ADD, EDIT
    }

    private inner class AddEditRunTask : AsyncTask<TaskRun?, Void?, Boolean>() {
        private var run: TaskRun? = null
        override fun onPreExecute() {
            super.onPreExecute()
            (findViewById<View>(R.id.prg_add_edit) as ProgressBar).visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: TaskRun?): Boolean {
            run = p0[0]
            val dbHelper: SQLiteOpenHelper = DBHelper(this@AddEditRunActivity)
            return try {
                val db = dbHelper.writableDatabase
                when (run!!.taskType) {
                    TASK_TYPE.ADD -> DBHelper.insertRun(db, run!!.runTime, run!!.runDistance)
                    TASK_TYPE.EDIT -> DBHelper.updateRun(db, run!!.id, run!!.runTime, run!!.runDistance)
                }
                true
            } catch (ex: Exception) {
                false
            }
        }

        override fun onPostExecute(success: Boolean) {
            super.onPostExecute(success)
            (findViewById<View>(R.id.prg_add_edit) as ProgressBar).visibility = View.INVISIBLE
            if (success) {
                Toast.makeText(this@AddEditRunActivity, "Run saved", Toast.LENGTH_SHORT).show()
                clearInput()
            } else {
                Toast.makeText(this@AddEditRunActivity, "Failed to save run", Toast.LENGTH_SHORT).show()
            }
        }

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
                AddEditRunTask().execute(taskRun)
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

    //------------------------------------
    //       CLEAR INPUT
    //------------------------------------
    private fun clearInput() {
        (findViewById<View>(R.id.edt_run_time) as EditText).setText("")
        (findViewById<View>(R.id.edt_run_distance) as EditText).setText("")
    }

    companion object {
        const val TASK_TYPE_STR = "TASK_TYPE"
        const val RUN_TIME_STR = "RUN_TIME"
        const val RUN_DIST_STR = "RUN_DIST"
        const val RUN_DATE_STR = "RUN_DATE"
        const val RUN_ID_STR = "RUN_ID"
    }
}