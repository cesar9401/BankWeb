
package com.bank.dao;

import com.bank.model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author cesar31
 */
public class AccountDao {
    
    private final Connection conexion;

    public AccountDao(Connection conexion) {
        this.conexion = conexion;
    }
    
    /**
     * Metodo para insertar una nueva cuenta a la base de datos
     * @param a 
     */
    public void insertAccount(Account a) {
        String queryAccount = "INSERT INTO ACCOUNTS(account_id, created_on, credit) VALUES(?, ?, ?)";
        String queryAccountC = "INSERT INTO CLIENTS_ACCOUNTS(client_id, account_id) VALUES(?, ?)";
        try {
            PreparedStatement ps = this.conexion.prepareStatement(queryAccount);
            ps.setInt(1, a.getAccountId());
            ps.setDate(2, a.getCreatedOn());
            ps.setDouble(3, a.getCredit());
            ps.executeUpdate();
            
            ps = this.conexion.prepareStatement(queryAccountC);
            ps.setInt(1, a.getClientId());
            ps.setInt(2, a.getAccountId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}

