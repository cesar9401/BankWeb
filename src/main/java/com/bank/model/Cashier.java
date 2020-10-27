
package com.bank.model;

import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Cashier extends Person{
    private int cashierId;
    private int workDay;
    private String workDayName;

    public Cashier(Element e) {
        super(e);
        this.cashierId = Integer.parseInt(e.getChildText("CODIGO"));
        this.workDayName = e.getChildText("TURNO");
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
