package com.bank.dao;

import com.bank.model.Cashier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author cesar31
 */
public class CashierDao {

    private final Connection conexion;
    private final Base64 base64;

    public CashierDao(Connection conexion) {
        this.conexion = conexion;
        this.base64 = new Base64();
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
            ps.setString(7, new String(base64.encode(c.getPassword().getBytes())));
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
        String query = "SELECT c.*, w.workday, w.start_time, w.end_time FROM CASHIERS c INNER JOIN WORKDAYS w ON c.workday_id = w.workday_id WHERE c.cashier_id = ?";
        if (!password.equals("")) {
            query += " AND c.password = ?";
        }
        Cashier c = null;
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, user);
            if (!password.equals("")) {
                ps.setString(2, new String(base64.encode(password.getBytes())));
            }
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

    /**
     * Metodo para actualizar informacion de un cajero
     *
     * @param c
     */
    public void updateCashier(Cashier c) {
        String query = "UDPATE CASHIERS SET name = ?, workday_id = ?, dpi = ?, address = ?, gender = ?, password = ? WHERE cashier_id = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setString(1, c.getName());
            ps.setInt(2, c.getWorkDay());
            ps.setString(3, c.getDpi());
            ps.setString(4, c.getAddress());
            ps.setBoolean(5, c.isGender());
            ps.setString(6, new String(base64.encode(c.getPassword().getBytes())));
            ps.setInt(7, c.getCashierId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
