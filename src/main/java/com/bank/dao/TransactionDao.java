package com.bank.dao;

import com.bank.conexion.Conexion;
import com.bank.model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cesar31
 */
public class TransactionDao {

    private final Connection conexion;

    public TransactionDao(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Metodo para insertar un trasaccion a la base de datos
     *
     * @param t
     */
    public void insertTransaction(Transaction t) {
        String query = "INSERT INTO TRANSACTIONS(transaction_id, account_id, created_on, created_at, type, amount, cashier_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, t.getTransactionId());
            ps.setInt(2, t.getAccountId());
            ps.setDate(3, t.getCreatedOn());
            ps.setTime(4, t.getCreatedAt());
            ps.setString(5, t.getType());
            ps.setDouble(6, t.getAmount());
            ps.setInt(7, t.getCashierId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Metodo par insertar una nueva transaccion y modificar el credito en la
     * cuenta
     *
     * @param t
     * @return
     * @throws java.sql.SQLException
     */
    public int createTransaction(Transaction t) throws SQLException {
        int transactionId = 0;
        double credit = 0;
        double amount = t.getType().equals("DEBITO") ? (t.getAmount() * -1) : t.getAmount();
        String queryCredit = "SELECT credit FROM ACCOUNTS WHERE account_id = ?";
        String queryUpd = "UPDATE ACCOUNTS SET credit = credit + ? WHERE account_id = ?";
        String queryTransaction = "INSERT INTO TRANSACTIONS(transaction_id, account_id, created_on, created_at, type, amount, cashier_id, balance) VALUES(?, ?, ?, ?, ?, ?, ?, "
                + "(SELECT credit FROM ACCOUNTS WHERE account_id = ?))";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //this.conexion.setAutoCommit(false);
            ps = this.conexion.prepareStatement(queryCredit);
            ps.setInt(1, t.getAccountId());
            rs = ps.executeQuery();
            if (rs.next()) {
                credit = rs.getDouble(1);
            }

            if (credit + amount >= 0) {
                //Actualizar cuenta
                ps = this.conexion.prepareStatement(queryUpd);
                ps.setDouble(1, amount);
                ps.setInt(2, t.getAccountId());
                ps.executeUpdate();

                //Crear transaccion
                ps = this.conexion.prepareStatement(queryTransaction, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, t.getTransactionId());
                ps.setInt(2, t.getAccountId());
                ps.setDate(3, t.getCreatedOn());
                ps.setTime(4, t.getCreatedAt());
                ps.setString(5, t.getType());
                ps.setDouble(6, t.getAmount());
                ps.setInt(7, t.getCashierId());
                ps.setInt(8, t.getAccountId());
                ps.executeUpdate();

                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    transactionId = rs.getInt(1);
                }
            }
            //this.conexion.commit();
            //this.conexion.setAutoCommit(true);
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
        }

        return transactionId;
    }

    /**
     * Crear transaccion atrapando cualquier exception
     *
     * @param t
     * @return
     */
    public int createTransactionEx(Transaction t) {
        int transactionId = 0;

        try {
            this.conexion.setAutoCommit(false);
            transactionId = createTransaction(t);
            this.conexion.commit();
            this.conexion.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            try {
                this.conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
        return transactionId;
    }

    /**
     * Metodo para transaccion web
     *
     * @param t1
     * @param t2
     * @return
     */
    public int[] createTransactionWeb(Transaction t1, Transaction t2) {
        int[] ids = {0, 0};

        try {
            this.conexion.setAutoCommit(false);
            ids[0] = createTransaction(t1);
            if (ids[0] != 0) {
                ids[1] = createTransaction(t2);
            }
            this.conexion.commit();
            this.conexion.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            try {
                this.conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }

        return ids;
    }

    /**
     * Metodo para obtener una transaccion de la base de datos
     *
     * @param transactionId
     * @return
     */
    public Transaction getTransaction(int transactionId) {
        Transaction transaction = null;
        String query = "SELECT t.*, c.name AS client_name, c.client_id, ca.name AS cashier_name FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id "
                + "INNER JOIN CLIENTS c ON a.client_id = c.client_id INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id "
                + "WHERE t.transaction_id = ?";

        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, transactionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    transaction = new Transaction(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return transaction;
    }
}
