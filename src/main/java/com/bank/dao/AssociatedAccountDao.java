package com.bank.dao;

import com.bank.model.AssociatedAccount;
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
public class AssociatedAccountDao {

    private final Connection conexion;

    public AssociatedAccountDao(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Insertar nueva asociacion a la base de datos
     *
     * @param a
     * @return
     */
    public int insertAssociatedAccount(AssociatedAccount a) {
        int associatedId = 0;
        String query = "INSERT INTO ASSOCIATED_ACCOUNTS(associated_id, client_id, account_id, status, try_number) VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE status = ?, try_number = try_number + 1";
        try (PreparedStatement ps = this.conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getAssociatedId());
            ps.setInt(2, a.getClientId());
            ps.setInt(3, a.getAccountId());
            ps.setString(4, a.getStatus());
            ps.setInt(5, a.getTryNumber());
            ps.setString(6, "EN ESPERA");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    associatedId = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return associatedId;
    }

    /**
     * Obtener asocacion segun id, o cliente y cuetna
     *
     * @param associatedId
     * @param clientId
     * @param accountId
     * @return
     */
    public AssociatedAccount getAssociated(Integer associatedId, Integer clientId, Integer accountId) {
        AssociatedAccount associated = null;
        String query = "SELECT aso.*, c.name AS associated_name, c.dpi AS associated_dpi, a.client_id AS account_client_id, c1.name AS account_name, c1.dpi AS account_dpi FROM ASSOCIATED_ACCOUNTS aso INNER JOIN CLIENTS c ON aso.client_id = c.client_id INNER JOIN ACCOUNTS a ON aso.account_id = a.account_id INNER JOIN CLIENTS c1 ON a.client_id = c1.client_id";
        query += (associatedId != null) ? " WHERE aso.associated_id = ?" : " WHERE aso.client_id = ? AND aso.account_id = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            if (associatedId != null) {
                ps.setInt(1, associatedId);
            } else {
                ps.setInt(1, clientId);
                ps.setInt(2, accountId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    associated = new AssociatedAccount(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return associated;
    }

    /**
     * Obtener listado de asociaciones por responder sugun clientId
     *
     * @param clientId
     * @return
     */
    public List<AssociatedAccount> getRequestForAssociations(int clientId) {
        List<AssociatedAccount> listAsso = new ArrayList<>();
        String query = "SELECT aso.*, c.name AS associated_name, c.dpi AS associated_dpi, a.client_id AS account_client_id, c1.name AS account_name, c1.dpi AS account_dpi FROM ASSOCIATED_ACCOUNTS aso INNER JOIN CLIENTS c ON aso.client_id = c.client_id INNER JOIN ACCOUNTS a ON aso.account_id = a.account_id INNER JOIN CLIENTS c1 ON a.client_id = c1.client_id"
                + " WHERE a.client_id = ? AND aso.status = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, clientId);
            ps.setString(2, "EN ESPERA");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listAsso.add(new AssociatedAccount(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return listAsso;
    }
}
