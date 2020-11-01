package com.bank.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Manager extends Person {

    private int managerId;
    private int workDay;
    private String workDayName;
    
    private java.sql.Time startTime;
    private java.sql.Time endTime;

    public Manager(Element e) {
        super(e);
        this.managerId = Integer.parseInt(e.getChildText("CODIGO"));
        this.workDayName = e.getChildText("TURNO");
    }

    public Manager(ResultSet rs) throws SQLException {
        super(rs);
        this.managerId = rs.getInt("manager_id");
        this.workDay = rs.getInt("workday_id");
        this.workDayName = rs.getString("workday");
        this.startTime = rs.getTime("start_time");
        this.endTime = rs.getTime("end_time");
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getWorkDay() {
        return workDay;
    }

    public void setWorkDay(int workDay) {
        this.workDay = workDay;
    }

    public String getWorkDayName() {
        return workDayName;
    }

    public void setWorkDayName(String workDayName) {
        this.workDayName = workDayName;
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
        return super.toString() + " Manager{" + "managerId=" + managerId + ", workDay=" + workDay + '}';
    }
}
