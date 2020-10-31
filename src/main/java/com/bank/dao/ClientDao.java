
package com.bank.dao;

import com.bank.model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author cesar31
 */
public class ClientDao {
    
    private final Connection conexion;

    public ClientDao(Connection conexion) {
        this.conexion = conexion;
    }
    
    /**
     * Metodo para insertar nuevo cliente a la base de datos
     * @param c 
     */
    public void insertClient(Client c) {
        String query = "INSERT INTO CLIENTS(client_id, name, dpi, birth, address, gender, password, pdf_dpi) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = this.conexion.prepareStatement(query);
            ps.setInt(1, c.getClientId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getDpi());
            ps.setDate(4, c.getBirth());
            ps.setString(5, c.getAddress());
            ps.setBoolean(6, c.isGender());
            ps.setString(7, c.getPassword());
            ps.setBlob(8, c.getPdfDpi());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
