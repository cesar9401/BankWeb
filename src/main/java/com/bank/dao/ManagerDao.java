package com.bank.dao;

import com.bank.model.Manager;
import com.bank.model.WorkDay;
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
public class ManagerDao {

    private final Connection conexion;

    public ManagerDao(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Metodo para insertar las jornadas de trabajo a la base de datos.
     *
     * @param w
     */
    public void insertWorkday(WorkDay w) {
        String query = "INSERT INTO WORKDAYS(workday, start_time, end_time) VALUES(?, ?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setString(1, w.getWorkDay());
            ps.setTime(2, w.getStartTime());
            ps.setTime(3, w.getEndTime());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Metodo para insertar nuevo gerente a la base de datos
     *
     * @param m
     */
    public void insertManager(Manager m) {
        String query = "INSERT INTO MANAGERS(manager_id, name, workday_id, dpi, address, gender, password) VALUES(?, ?, (SELECT workday_id FROM WORKDAYS WHERE workday = ? LIMIT 1), ?, ?, ?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, m.getManagerId());
            ps.setString(2, m.getName());
            ps.setString(3, m.getWorkDayName());
            ps.setString(4, m.getDpi());
            ps.setString(5, m.getAddress());
            ps.setBoolean(6, m.isGender());
            ps.setString(7, m.getPassword());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Metodo para obtener listado de gerentes
     * @return 
     */
    public List<Manager> getManagers() {
        String query = "SELECT m.*, w.workday, w.start_time, w.end_time FROM MANAGERS m INNER JOIN WORKDAYS w ON m.workday_id = w.workday_id";
        List<Manager> managers = new ArrayList<>();
        try (PreparedStatement ps = this.conexion.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                managers.add(new Manager(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return managers;
    }
    
    /**
     * Metodo para obtener a un gerente segun su usuario y password
     * @param user
     * @param password
     * @return 
     */
    public Manager getManager(int user, String password) {
        String query = "SELECT m.*, w.workday, w.start_time, w.end_time FROM MANAGERS m INNER JOIN WORKDAYS w ON m.workday_id = w.workday_id WHERE m.manager_id = ? AND m.password = ?";
        Manager m = null;
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, user);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    m = new Manager(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return m;
    }

}
