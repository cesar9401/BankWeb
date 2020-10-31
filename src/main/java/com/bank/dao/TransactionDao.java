package com.bank.dao;

import com.bank.model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
     * @param t 
     */
    public void insertTransaction(Transaction t) {
        String query = "INSERT INTO TRANSACTIONS(transaction_id, account_id, created_on, created_at, type, amount, cashier_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, t.getTransactionId());
            ps.setInt(2, t.getAccountId());
            ps.setDate(3, t.getCreatedOn());
            ps.setTime(4, t.getCreateAt());
            ps.setString(5, t.getType());
            ps.setDouble(6, t.getAmount());
            ps.setInt(7, t.getCashierId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
