package com.example.runtimetracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RunCursorAdapter extends CursorAdapter {
    private final LayoutInflater layoutInflater;

    public RunCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.run_record, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        setDate(view, cursor);
        setTime(view, cursor);
        setDistance(view, cursor);
    }

    private void setDate(View view, Cursor cursor) {
        @SuppressLint("Range") long milli = cursor.getLong(cursor.getColumnIndex("Run_Date"));
        Date date = new Date(milli);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ((TextView) view.findViewById(R.id.txt_run_date)).setText(formatter.format(date));
    }

    private void setTime(View view, Cursor cursor) {
        @SuppressLint("Range") String runTime = cursor.getString(cursor.getColumnIndex("Run_Time"));
        ((TextView) view.findViewById(R.id.txt_run_time)).setText(runTime);
    }

    private void setDistance(View view, Cursor cursor) {
        @SuppressLint("Range") String runDist = cursor.getString(cursor.getColumnIndex("Run_Distance"));
        ((TextView) view.findViewById(R.id.txt_run_dist)).setText(runDist+" Km");
    }
}
