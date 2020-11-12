package com.bank.model;

import com.bank.control.ReadXml;
import java.sql.Date;
import java.sql.Time;
import javax.servlet.http.HttpServletRequest;
import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Transaction {

    private int transactionId;
    private int accountId;
    private java.sql.Date createdOn;
    private java.sql.Time createAt;
    private String type;
    private Double amount;
    private int cashierId;
    private Double balance;

    private String cashierName;
    private String clientName;

    public Transaction(Element e) {
        this.transactionId = Integer.parseInt(e.getChildText("CODIGO"));
        this.accountId = Integer.parseInt(e.getChildText("CUENTA"));
        this.createdOn = ReadXml.getDate(e.getChildText("FECHA"));
        this.createAt = ReadXml.getTime(e.getChildText("HORA"));
        this.type = e.getChildText("TIPO");
        this.amount = Double.parseDouble(e.getChildText("MONTO"));
        this.cashierId = Integer.parseInt(e.getChildText("CAJERO"));
    }

    public Transaction(HttpServletRequest request) {
        this.transactionId = Integer.parseInt(request.getParameter("transactionId"));
        this.accountId = Integer.parseInt(request.getParameter("accountId"));
        this.createdOn = ReadXml.getDate(request.getParameter("created-on"));
        this.createAt = ReadXml.getTime(request.getParameter("created-at"));
        this.type = request.getParameter("type");
        this.amount = Double.parseDouble(request.getParameter("amount"));
        this.cashierId = Integer.parseInt(request.getParameter("cashierId"));
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public Time getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Time createAt) {
        this.createAt = createAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Transaction{" + "transactionId=" + transactionId + ", accountId=" + accountId + ", createdOn=" + createdOn + ", createAt=" + createAt + ", type=" + type + ", amount=" + amount + ", cashierId=" + cashierId + ", balance=" + balance + '}';
    }
}
