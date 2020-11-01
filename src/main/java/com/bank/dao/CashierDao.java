package com.bank.dao;

import com.bank.model.Cashier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
     *
     * @param c
     */
    public void insertCashier(Cashier c) {
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

    /**
     * Metodo para obtener a un cajero segun codigo y password
     *
     * @param user
     * @param password
     * @return
     */
    public Cashier getCashier(int user, String password) {
        String query = "SELECT c.*, w.workday, w.start_time, w.end_time FROM CASHIERS c INNER JOIN WORKDAYS w ON c.workday_id = w.workday_id WHERE c.cashier_id = ? AND c.password = ?";
        Cashier c = null;
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, user);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Cashier(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return c;
    }
}