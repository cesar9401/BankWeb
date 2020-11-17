package com.bank.model;

import com.bank.control.ReadXml;
import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import javax.servlet.http.HttpServletRequest;
import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Transaction implements Serializable {

    private int transactionId;
    private int accountId;
    private java.sql.Date createdOn;
    private java.sql.Time createdAt;
    private String type;
    private Double amount;
    private int cashierId;
    private Double balance;

    private String cashierName;
    private String clientName;
    private int clientId;

    //Para reporte 3
    private String dpi;
    private Double total;

    private Double credit;
    private java.sql.Date dateAccount;

    private String date;

    public Transaction(Element e) {
        this.transactionId = Integer.parseInt(e.getChildText("CODIGO"));
        this.accountId = Integer.parseInt(e.getChildText("CUENTA-ID"));
        this.createdOn = ReadXml.getDate(e.getChildText("FECHA"));
        this.createdAt = ReadXml.getTime(e.getChildText("HORA"));
        this.type = e.getChildText("TIPO");
        this.amount = Double.parseDouble(e.getChildText("MONTO"));
        this.cashierId = Integer.parseInt(e.getChildText("CAJERO-ID"));
    }

    public Transaction(HttpServletRequest request) {
        this.transactionId = Integer.parseInt(request.getParameter("transactionId"));
        this.accountId = Integer.parseInt(request.getParameter("accountId"));
        this.createdOn = ReadXml.getDate(request.getParameter("created-on"));
        this.createdAt = ReadXml.getTime(request.getParameter("created-at"));
        this.type = request.getParameter("type");
        this.amount = Double.parseDouble(request.getParameter("amount"));
        this.cashierId = Integer.parseInt(request.getParameter("cashierId"));
    }

    public Transaction(ResultSet rs) throws SQLException {
        this.transactionId = rs.getInt("transaction_id");
        this.accountId = rs.getInt("account_id");
        this.createdOn = rs.getDate("created_on");
        this.createdAt = rs.getTime("created_at");
        this.type = rs.getString("type");
        this.amount = rs.getDouble("amount");
        this.cashierId = rs.getInt("cashier_id");
        this.balance = rs.getDouble("balance");
        this.clientName = rs.getString("client_name");
        this.clientId = rs.getInt("client_id");
        this.cashierName = rs.getString("cashier_name");
        this.date = createdOn.toString();
    }

    public Transaction(int transactionId, int accountId, Date createdOn, Time createdAt, String type, Double amount, int cashierId) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.createdOn = createdOn;
        this.createdAt = createdAt;
        this.type = type;
        this.amount = amount;
        this.cashierId = cashierId;
    }

    //Constructor reporte 3
    public Transaction(String type, String clientName, int clientId, String dpi, Double total) {
        this.type = type;
        this.clientName = clientName;
        this.clientId = clientId;
        this.dpi = dpi;
        this.total = total;
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

    public Time getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Time createdAt) {
        this.createdAt = createdAt;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Date getDateAccount() {
        return dateAccount;
    }

    public void setDateAccount(Date dateAccount) {
        this.dateAccount = dateAccount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" + "transactionId=" + transactionId + ", accountId=" + accountId + ", createdOn=" + createdOn + ", createdAt=" + createdAt + ", type=" + type + ", amount=" + amount + ", cashierId=" + cashierId + ", balance=" + balance + '}';
    }
}
