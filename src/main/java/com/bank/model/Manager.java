
package com.bank.model;

import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Manager extends Person{
    
    private int managerId;
    private int workDay;
    private String workDayName;

    public Manager(Element e) {
        super(e);
        this.managerId = Integer.parseInt(e.getChildText("CODIGO"));
        this.workDayName = e.getChildText("TURNO");
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
    
    @Override
    public String toString() {
        return super.toString() + " Manager{" + "managerId=" + managerId + ", workDay=" + workDay + '}';
    }
}
