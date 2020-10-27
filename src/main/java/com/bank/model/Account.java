
package com.bank.model;

import java.sql.Date;

/**
 *
 * @author cesar31
 */
public class Account {
    private int accountId;
    private java.sql.Date createdOn;
    private Double credit;

    public Account(int accountId, Date createdOn, Double credit) {
        this.accountId = accountId;
        this.createdOn = createdOn;
        this.credit = credit;
    }
    
    //Datos cliente
    private String name;
    private int clientId;
    
    //Datos para cuentas asociadas
    private int associatedId;
    private String status;
    private int tryNumber;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(int associatedId) {
        this.associatedId = associatedId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTryNumber() {
        return tryNumber;
    }

    public void setTryNumber(int tryNumber) {
        this.tryNumber = tryNumber;
    }
    
    @Override
    public String toString() {
        return "Account{" + "accountId=" + accountId + ", createdOn=" + createdOn + ", credit=" + credit + '}';
    }
}
