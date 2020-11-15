package com.bank.dao;

import com.bank.conexion.Conexion;
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

    /**
     * Obtener listado de cuentas del determinado cliente y cuentas asociadas segun el parametro destino
     *
     * @param clientId
     * @param destino
     * @return
     */
    public List<Account> getAccounts(int clientId, boolean destino) {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT a.*, name, dpi FROM ACCOUNTS a INNER JOIN CLIENTS c ON a.client_id = c.client_id WHERE a.client_id = ?";
        String queryAsso = "SELECT a.*, c1.name, c1.dpi FROM ASSOCIATED_ACCOUNTS aso INNER JOIN CLIENTS c ON aso.client_id = c.client_id INNER JOIN ACCOUNTS a ON aso.account_id = a.account_id INNER JOIN CLIENTS c1 ON a.client_id = c1.client_id "
                + "WHERE aso.client_id = ? AND aso.status = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = this.conexion.prepareStatement(query);
            ps.setInt(1, clientId);
            rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(new Account(rs));
            }

            if (destino) {
                ps = this.conexion.prepareStatement(queryAsso);
                ps.setInt(1, clientId);
                ps.setString(2, "ACEPTADA");
                rs = ps.executeQuery();
                while (rs.next()) {
                    accounts.add(new Account(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
        }
        return accounts;
    }

    /**
     * Obtener cuenta segun id
     *
     * @param accountId
     * @return
     */
    public Account getAccount(int accountId) {
        Account account = null;
        String query = "SELECT a.*, name, dpi FROM ACCOUNTS a INNER JOIN CLIENTS c ON a.client_id = c.client_id WHERE a.account_id = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    account = new Account(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return account;
    }
}
