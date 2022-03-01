package com.example.runtimetracker

import java.sql.Date

open class Run {
    var id: Long
    var runTime: String
    var runDistance: Float
    var runDate: Date?

    constructor(id: Long, runTime: String, runDistance: Float, runDate: Date?) {
        this.id = id
        this.runTime = runTime
        this.runDistance = runDistance
        this.runDate = runDate
    }

    constructor() {
        id = 0
        runTime = ""
        runDistance = 0f
        runDate = null
    }

    override fun toString(): String {
        return "Run{" +
                "runTime='" + runTime + '\'' +
                ", runDistance=" + runDistance +
                ", runDate=" + runDate +
                '}'
    }
}