package com.bank.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author cesar31
 */
public class ChangeHistory {

    private int changeId;
    private int managerMainId;
    private Integer clientId;
    private Integer cashierId;
    private Integer managerId;
    private String description;
    private java.sql.Timestamp createdAt;

    //Datos extras
    private String managerMainName;
    private String clientName;
    private String cashierName;
    private String managerName;

    public ChangeHistory(ResultSet rs) throws SQLException {
        this.changeId = rs.getInt("change_id");
        this.managerMainId = rs.getInt("manager_manager_id");
        this.clientId = rs.getInt("client_id");
        this.cashierId = rs.getInt("cashier_id");
        this.managerId = rs.getInt("manager_id");
        this.description = rs.getString("description");
        this.createdAt = rs.getTimestamp("created_at");
        this.managerMainName = rs.getString("manager_name");
    }

    public ChangeHistory(int changeId, int managerMainId, Integer clientId, Integer cashierId, Integer managerId, String description) {
        this.changeId = changeId;
        this.managerMainId = managerMainId;
        this.clientId = clientId;
        this.cashierId = cashierId;
        this.managerId = managerId;
        this.description = description;
    }

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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
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
