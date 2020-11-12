package com.bank.dao;

import com.bank.model.Cashier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @param isNew
     * @return
     */
    public int insertCashier(Cashier c, boolean isNew) {
        int cashierId = 0;
        String query = "INSERT INTO CASHIERS(cashier_id, name, workday_id, dpi, address, gender, password) ";
        query += isNew ? "VALUES(?, ?, ?, ?, ?, ?, ?)" : "VALUES(?, ?, (SELECT workday_id FROM WORKDAYS WHERE workday = ? LIMIT 1), ?, ?, ?, ?)";

        try (PreparedStatement ps = this.conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getCashierId());
            ps.setString(2, c.getName());
            if (isNew) {
                ps.setInt(3, c.getWorkDay());
            } else {
                ps.setString(3, c.getWorkDayName());
            }
            ps.setString(4, c.getDpi());
            ps.setString(5, c.getAddress());
            ps.setBoolean(6, c.isGender());
            ps.setString(7, new String(base64.encode(c.getPassword().getBytes())));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cashierId = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return cashierId;
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
     * Metodo para obtener cajeros segun ciertos parametros
     *
     * @param type
     * @param search
     * @return
     */
    public List<Cashier> getCashiers(int type, String search) {
        List<Cashier> cashiers = new ArrayList<>();
        String query = "SELECT c.*, w.workday, w.start_time, w.end_time FROM CASHIERS c INNER JOIN WORKDAYS w ON c.workday_id = w.workday_id";
        switch (type) {
            case 0:
                query += " WHERE c.cashier_id = ?";
                break;
            case 1:
                query += " WHERE c.name LIKE ?";
                break;
            case 2:
                query += " WHERE c.dpi = ?";
                break;
        }
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            switch (type) {
                case 0:
                    ps.setInt(1, Integer.parseInt(search));
                    break;
                case 1:
                    search = "%" + search + "%";
                    ps.setString(1, search);
                    break;
                case 2:
                    ps.setString(1, search);
                    break;
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    cashiers.add(new Cashier(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return cashiers;
    }

    /**
     * Metodo para actualizar informacion de un cajero
     *
     * @param c
     */
    public void updateCashier(Cashier c) {
        String query = "UPDATE CASHIERS SET name = ?, workday_id = ?, dpi = ?, address = ?, gender = ?, password = ? WHERE cashier_id = ?";
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
