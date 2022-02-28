package com.example.runtimetracker;

import java.sql.Date;

public class TaskRun extends Run {
    public AddEditRunActivity.TASK_TYPE taskType;

    public TaskRun(long id, String runTime, float runDistance, Date runDate, AddEditRunActivity.TASK_TYPE taskType) {
        super(id, runTime, runDistance, runDate);
        this.taskType = taskType;
    }

    public TaskRun(AddEditRunActivity.TASK_TYPE taskType) {
        this.taskType = taskType;
    }
}
