package com.bank.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author cesar31
 */
public class AssociatedAccount {

    private int associatedId;
    private int clientId;
    private int accountId;
    private String status;
    private int tryNumber;
    
    //Datos solicitante
    private String associatedName;
    private String associatedDpi;
    
    //Datos propietario de la cuenta
    private int accountClientId;
    private String accountName;
    private String accountDpi;

    public AssociatedAccount(HttpServletRequest request) {
        this.associatedId = Integer.parseInt(request.getParameter("associatedId"));
        this.clientId = Integer.parseInt(request.getParameter("client-client-id"));
        this.accountId = Integer.parseInt(request.getParameter("accountId"));
        this.status = request.getParameter("status");
        this.tryNumber = Integer.parseInt(request.getParameter("try-number"));
    }
    
    public AssociatedAccount(ResultSet rs) throws SQLException {
        this.associatedId = rs.getInt("associated_id");
        this.clientId = rs.getInt("client_id");
        this.accountId = rs.getInt("account_id");
        this.status = rs.getString("status");
        this.tryNumber = rs.getInt("try_number");
        this.associatedName = rs.getString("associated_name");
        this.associatedDpi = rs.getString("associated_dpi");
        this.accountClientId = rs.getInt("account_client_id");
        this.accountName = rs.getString("account_name");
        this.accountDpi = rs.getString("account_dpi");
    }

    public int getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(int associatedId) {
        this.associatedId = associatedId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public String getAssociatedName() {
        return associatedName;
    }

    public void setAssociatedName(String associatedName) {
        this.associatedName = associatedName;
    }

    public String getAssociatedDpi() {
        return associatedDpi;
    }

    public void setAssociatedDpi(String associatedDpi) {
        this.associatedDpi = associatedDpi;
    }

    public int getAccountClientId() {
        return accountClientId;
    }

    public void setAccountClientId(int accountClientId) {
        this.accountClientId = accountClientId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountDpi() {
        return accountDpi;
    }

    public void setAccountDpi(String accountDpi) {
        this.accountDpi = accountDpi;
    }
    
    @Override
    public String toString() {
        return "AssociatedAccount{" + "associatedId=" + associatedId + ", clientId=" + clientId + ", accountId=" + accountId + ", status=" + status + ", tryNumber=" + tryNumber + '}';
    }
}
