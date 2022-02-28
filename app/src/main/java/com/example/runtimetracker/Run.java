package com.example.runtimetracker;

import java.sql.Date;

public class Run {
    public long id;
    public String runTime;
    public float runDistance;
    public Date runDate;

    public Run(long id, String runTime, float runDistance, Date runDate) {
        this.id = id;
        this.runTime = runTime;
        this.runDistance = runDistance;
        this.runDate = runDate;
    }

    public Run() {
        this.id = 0;
        this.runTime = "";
        this.runDistance = 0;
        this.runDate = null;
    }

    @Override
    public String toString() {
        return "Run{" +
                "runTime='" + runTime + '\'' +
                ", runDistance=" + runDistance +
                ", runDate=" + runDate +
                '}';
    }
}
