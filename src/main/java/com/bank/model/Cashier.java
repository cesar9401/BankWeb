
package com.bank.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Cashier extends Person{
    private int cashierId;
    private int workDay;
    private String workDayName;
    
    private java.sql.Time startTime;
    private java.sql.Time endTime;

    public Cashier(Element e) {
        super(e);
        this.cashierId = Integer.parseInt(e.getChildText("CODIGO"));
        this.workDayName = e.getChildText("TURNO");
    }

    public Cashier(int cashierId, String workDayName, String name, String dpi, String password) {
        super(name, dpi, password);
        this.cashierId = cashierId;
        this.workDayName = workDayName;
    }
    
    public Cashier(ResultSet rs) throws SQLException {
        super(rs);
        this.cashierId = rs.getInt("cashier_id");
        this.workDay = rs.getInt("workday_id");
        this.workDayName = rs.getString("workday");
        this.startTime = rs.getTime("start_time");
        this.endTime = rs.getTime("end_time");
    }
    
    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
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
        return super.toString() + " Cashier{" + "cashierId=" + cashierId + ", workDay=" + workDay + '}';
    }
}
