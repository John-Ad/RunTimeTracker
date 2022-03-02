package com.example.runtimetracker

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

class AddEditRunTask constructor(listener: AddEditRunTaskListener) : AsyncTask<TaskRun?, Void?, Boolean>() {
    private var run: TaskRun? = null
    private val taskListener = listener

    public interface AddEditRunTaskListener {
        fun getCallerContext():Context?{return null}
        fun toggleProgressVisibility(isVisible:Boolean){}
        fun makeToast(message: String){}
        fun clearInput() {}
        fun returnToPrevScreen() {}
    }

    override fun onPreExecute() {
        super.onPreExecute()
        taskListener.toggleProgressVisibility(true)
//        (context!!.findViewById<View>(R.id.prg_add_edit) as ProgressBar).visibility = View.VISIBLE
    }

    override fun doInBackground(vararg p0: TaskRun?): Boolean {
        run = p0[0]
        val dbHelper: SQLiteOpenHelper = DBHelper(taskListener.getCallerContext())
        return try {
            val db = dbHelper.writableDatabase
            when (run!!.taskType) {
                AddEditRunActivity.TASK_TYPE.ADD -> DBHelper.insertRun(db, run!!.runTime, run!!.runDistance)
                AddEditRunActivity.TASK_TYPE.EDIT -> DBHelper.updateRun(db, run!!.id, run!!.runTime, run!!.runDistance)
                AddEditRunActivity.TASK_TYPE.DELETE -> DBHelper.deleteRun(db, run!!.id)
            }
            true
        } catch (ex: Exception) {
            Log.d("FUCKING BROKEN: ",ex.message!!)
            false
        }
    }

    override fun onPostExecute(success: Boolean) {
        super.onPostExecute(success)
        taskListener.toggleProgressVisibility(false)
//        (context!!.findViewById<View>(R.id.prg_add_edit) as ProgressBar).visibility = View.INVISIBLE
        if (success) {
            taskListener.makeToast(if (run!!.taskType == AddEditRunActivity.TASK_TYPE.DELETE) "Run deleted" else "Run saved")

            when (run!!.taskType) {
                AddEditRunActivity.TASK_TYPE.ADD -> taskListener.clearInput()
                AddEditRunActivity.TASK_TYPE.EDIT -> taskListener.clearInput()
                AddEditRunActivity.TASK_TYPE.DELETE -> taskListener.returnToPrevScreen()
            }
        } else {
            taskListener.makeToast(if (run!!.taskType == AddEditRunActivity.TASK_TYPE.DELETE) "Failed to delete run" else "Failed to save run")
        }
    }
}