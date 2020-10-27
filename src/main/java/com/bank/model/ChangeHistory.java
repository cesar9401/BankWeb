
package com.bank.model;

import java.sql.Timestamp;

/**
 *
 * @author cesar31
 */
public class ChangeHistory {
    private int changeId;
    private int managerMainId;
    private int clientId;
    private int chashierId;
    private int managerId;
    private String description;
    private java.sql.Timestamp createdAt;
    
    //Datos extras
    private String managerMainName;
    private String clientName;
    private String cashierName;
    private String managerName;

    public int getChangeId() {
        return changeId;
    }

    public void setChangeId(int changeId) {
        this.changeId = changeId;
    }

    public int getManagerMainId() {
        return managerMainId;
    }

    public void setManagerMainId(int managerMainId) {
        this.managerMainId = managerMainId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getChashierId() {
        return chashierId;
    }

    public void setChashierId(int chashierId) {
        this.chashierId = chashierId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getManagerMainName() {
        return managerMainName;
    }

    public void setManagerMainName(String managerMainName) {
        this.managerMainName = managerMainName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @Override
    public String toString() {
        return "ChangeHistory{" + "changeId=" + changeId + ", managerMainId=" + managerMainId + ", description=" + description + ", createdAt=" + createdAt + ", cashierName=" + cashierName + '}';
    }
}
