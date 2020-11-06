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
        String queryAccount = "INSERT INTO ACCOUNTS(account_id, created_on, credit) VALUES(?, ?, ?)";
        String queryAccountC = "INSERT INTO CLIENTS_ACCOUNTS(client_id, account_id) VALUES(?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(queryAccount, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getAccountId());
            ps.setDate(2, a.getCreatedOn());
            ps.setDouble(3, a.getCredit());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                accountId = rs.getInt(1);
            }

            try (PreparedStatement ps1 = this.conexion.prepareStatement(queryAccountC)) {
                ps1.setInt(1, a.getClientId());
                ps1.setInt(2, accountId);
                ps1.executeUpdate();
            }
        }

        return accountId;
    }

    /**
     * Metodo para insertar nueva cuenta caputurando las excepciones
     *
     * @param a
     */
    public void insertNewAccount(Account a) {
        try {
            insertAccount(a);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public List<Account> getAccounts(int clientId) {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT a.* FROM ACCOUNTS a INNER JOIN CLIENTS_ACCOUNTS ca ON a.account_id = ca.account_id WHERE ca.client_id = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    accounts.add(new Account(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return accounts;
    }
}
