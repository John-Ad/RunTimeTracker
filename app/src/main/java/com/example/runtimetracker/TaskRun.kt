package com.example.runtimetracker

import com.example.runtimetracker.AddEditRunActivity.TASK_TYPE
import java.sql.Date

class TaskRun : Run {
    var taskType: TASK_TYPE

    constructor(id: Long, runTime: String, runDistance: Float, runDate: Date?, taskType: TASK_TYPE) : super(id, runTime, runDistance, runDate) {
        this.taskType = taskType
    }

    constructor(taskType: TASK_TYPE) {
        this.taskType = taskType
    }
}