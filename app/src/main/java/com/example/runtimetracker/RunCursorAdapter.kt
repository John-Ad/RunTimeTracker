package com.example.runtimetracker

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class RunCursorAdapter(context: Context?, c: Cursor?, flags: Int) : CursorAdapter(context, c, flags) {
    private val layoutInflater: LayoutInflater

    override fun newView(context: Context, cursor: Cursor, viewGroup: ViewGroup): View {
        return layoutInflater.inflate(R.layout.run_record, viewGroup, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        setDate(view, cursor)
        setTime(view, cursor)
        setDistance(view, cursor)
    }

    private fun setDate(view: View, cursor: Cursor) {
        @SuppressLint("Range") val milli = cursor.getLong(cursor.getColumnIndex("Run_Date"))
        val date = Date(milli)
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        (view.findViewById<View>(R.id.txt_run_date) as TextView).text = formatter.format(date)
    }

    private fun setTime(view: View, cursor: Cursor) {
        @SuppressLint("Range") val runTime = cursor.getString(cursor.getColumnIndex("Run_Time"))
        (view.findViewById<View>(R.id.txt_run_time) as TextView).text = runTime
    }

    private fun setDistance(view: View, cursor: Cursor) {
        @SuppressLint("Range") val runDist = cursor.getString(cursor.getColumnIndex("Run_Distance"))
        (view.findViewById<View>(R.id.txt_run_dist) as TextView).text = "$runDist Km"
    }

    init {
        layoutInflater = LayoutInflater.from(context)
    }
}