
package com.bank.model;

import java.sql.Time;

/**
 *
 * @author cesar31
 */
public class WorkDay {
    private int workDayId;
    private String workDay;
    private java.sql.Time startTime;
    private java.sql.Time endTime;

    public int getWorkDayId() {
        return workDayId;
    }

    public void setWorkDayId(int workDayId) {
        this.workDayId = workDayId;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "WorkDay{" + "workDayId=" + workDayId + ", workDay=" + workDay + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
}
