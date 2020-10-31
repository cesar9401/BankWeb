package com.bank.dao;

import com.bank.model.Manager;
import com.bank.model.WorkDay;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
     * @param w 
     */
    public void insertWorkday(WorkDay w) {
        String query = "INSERT INTO WORKDAYS(workday, start_time, ent_time) VALUES(?, ?, ?)";
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

}
