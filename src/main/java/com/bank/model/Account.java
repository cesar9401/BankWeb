package com.bank.model;

import com.bank.control.ReadXml;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Account {

    private int accountId;
    private java.sql.Date createdOn;
    private Double credit;

    //Datos cliente
    private String name;
    private int clientId;
    private String dpi;

    //Datos para cuentas asociadas
    private int associatedId;
    private String status;
    private int tryNumber;

    public Account(int accountId, Date createdOn, Double credit) {
        this.accountId = accountId;
        this.createdOn = createdOn;
        this.credit = credit;
    }

    public Account(HttpServletRequest request) {
        this.accountId = Integer.parseInt(request.getParameter("id-account"));
        this.credit = Double.parseDouble(request.getParameter("credit-account"));
        this.createdOn = ReadXml.getDate(request.getParameter("created-on-account"));
        this.clientId = Integer.parseInt(request.getParameter("client-id-account"));
    }

    public Account(Element i, int clientId) {
        this.accountId = Integer.parseInt(i.getChildText("CODIGO"));
        this.createdOn = ReadXml.getDate(i.getChildText("CREADA"));
        this.credit = Double.parseDouble(i.getChildText("CREDITO"));
        this.clientId = clientId;
    }

    public Account(ResultSet rs) throws SQLException {
        this.accountId = rs.getInt("account_id");
        this.clientId = rs.getInt("client_id");
        this.createdOn = rs.getDate("created_on");
        this.credit = rs.getDouble("credit");
        this.name = rs.getString("name");
        this.dpi = rs.getString("dpi");
    }

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

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }
    
    @Override
    public String toString() {
        return "Account{" + "accountId=" + accountId + ", createdOn=" + createdOn + ", credit=" + credit + '}';
    }
}
