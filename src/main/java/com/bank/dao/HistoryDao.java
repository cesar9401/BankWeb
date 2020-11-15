package com.bank.dao;

import com.bank.model.ChangeHistory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author cesar31
 */
public class HistoryDao {

    private final Connection conexion;

    public HistoryDao(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Metodo para insertar historial de actualizacion de entidades
     *
     * @param h
     */
    public void insertChangeHistory(ChangeHistory h) {
        String query = "INSERT INTO CHANGE_HISTORY(change_id, manager_manager_id, ";
        if (h.getClientId() != null) {
            query += "client_id";
        } else if (h.getCashierId() != null) {
            query += "cashier_id";
        } else {
            query += "manager_id";
        }
        query += ", description) VALUES(?, ?, ?, ?)";

        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, h.getChangeId());
            ps.setInt(2, h.getManagerMainId());
            if (h.getClientId() != null) {
                ps.setInt(3, h.getClientId());
            } else if (h.getCashierId()!= null) {
                ps.setInt(3, h.getCashierId());
            } else {
                ps.setInt(3, h.getManagerId());
            }
            ps.setString(4, h.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
