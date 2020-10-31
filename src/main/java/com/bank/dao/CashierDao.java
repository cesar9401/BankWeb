package com.bank.dao;

import com.bank.model.Cashier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author cesar31
 */
public class CashierDao {

    private final Connection conexion;

    public CashierDao(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Metodo para insertar nuevo cajero a la base de datos
     * @param c 
     */
    public void insertChashier(Cashier c) {
        String query = "INSERT INTO CASHIERS(cashier_id, name, workday_id, dpi, address, gender, password) VALUES(?, ?, (SELECT workday_id FROM WORKDAYS WHERE workday = ? LIMIT 1), ?, ?, ?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, c.getCashierId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getWorkDayName());
            ps.setString(4, c.getDpi());
            ps.setString(5, c.getAddress());
            ps.setBoolean(6, c.isGender());
            ps.setString(7, c.getPassword());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
