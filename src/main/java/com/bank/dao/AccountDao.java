package com.bank.dao;

import com.bank.model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     *
     * @param a
     * @return
     * @throws java.sql.SQLException
     */
    public int insertAccount(Account a) throws SQLException {
        int accountId = 0;
        String queryAccount = "INSERT INTO ACCOUNTS(account_id, client_id, created_on, credit) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(queryAccount, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getAccountId());
            ps.setInt(2, a.getClientId());
            ps.setDate(3, a.getCreatedOn());
            ps.setDouble(4, a.getCredit());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                accountId = rs.getInt(1);
            }
        }
        return accountId;
    }

    /**
     * Metodo para insertar nueva cuenta caputurando las excepciones
     *
     * @param a
     * @return
     */
    public int insertNewAccount(Account a) {
        int accountId = 0;
        try {
            accountId = insertAccount(a);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return accountId;
    }

    public List<Account> getAccounts(int clientId) {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM ACCOUNTS WHERE client_id = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accounts.add(new Account(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return accounts;
    }

    public Account getAccount(int accountId) {
        Account account = null;
        String query = "SELECT * FROM ACCOUNTS WHERE account_id = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    account = new Account(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return account;
    }
}
