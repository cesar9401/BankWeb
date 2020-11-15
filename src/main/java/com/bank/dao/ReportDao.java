package com.bank.dao;

import com.bank.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cesar31
 */
public class ReportDao {

    private final Connection conexion;

    public ReportDao(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Gerente 1 -> Historial de cambios realizados en la informacion de una
     * entidad en especifico
     *
     * @param managerId
     * @param entity
     * @return
     */
    public List<ChangeHistory> getHistory(int managerId, String entity) {
        List<ChangeHistory> history = new ArrayList<>();
        String query = "";
        switch (entity) {
            case "MANAGERS":
                query = "SELECT h.*, m.name AS manager_name FROM CHANGE_HISTORY h INNER JOIN MANAGERS m ON h.manager_manager_id = m.manager_id "
                        + "WHERE h.manager_manager_id = ? AND h.manager_id IS NOT NULL";
                break;
            case "CASHIERS":
                query = "SELECT h.*, m.name AS manager_name, c.name AS cashier_name FROM CHANGE_HISTORY h INNER JOIN MANAGERS m ON h.manager_manager_id = m.manager_id INNER JOIN CASHIERS c ON h.cashier_id = c.cashier_id "
                        + "WHERE h.manager_manager_id = ? AND h.cashier_id IS NOT NULL";
                break;
            case "CLIENTS":
                query = "SELECT h.*, m.name AS manager_name, c.name AS client_name FROM CHANGE_HISTORY h INNER JOIN MANAGERS m ON h.manager_manager_id = m.manager_id INNER JOIN CLIENTS c ON h.client_id = c.client_id "
                        + "WHERE h.manager_manager_id = ? AND h.client_id IS NOT NULL";
                break;
        }
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChangeHistory tmp = new ChangeHistory(rs);
                    switch (entity) {
                        case "MANAGERS":
                            break;
                        case "CASHIERS":
                            tmp.setCashierName(rs.getString("cashier_name"));
                            break;
                        case "CLIENTS":
                            tmp.setClientName(rs.getString("client_name"));
                            break;
                    }
                    history.add(tmp);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return history;
    }

    /**
     * Gerente 2 -> Clientes con transacciones monetarias mayores a un límite
     * establecido
     *
     * @param limit
     * @return
     */
    public List<Transaction> getTopTransactions(int limit) {
        List<Transaction> top = new ArrayList<>();
        String query = "SELECT t.*, c.name AS client_name, c.client_id, ca.name AS cashier_name FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id "
                + "WHERE t.amount > ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    top.add(new Transaction(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return top;
    }

    /**
     * Gerente 3 -> Clientes con transacciones monetarias sumadas mayores a un
     * límite establecido
     *
     * @param limit
     * @return
     */
    public List<Transaction> getTopSummedTransactions(int limit) {
        List<Transaction> top = new ArrayList<>();
        String query = "SELECT t.type, c.name AS client_name, c.client_id, c.dpi, SUM(t.amount) AS total FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id GROUP BY t.type, c.client_id "
                + "HAVING total > ? ORDER BY total DESC";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    top.add(new Transaction(rs.getString("type"), rs.getString("client_name"), rs.getInt("client_id"), rs.getString("dpi"), rs.getDouble("total")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return top;
    }

    /**
     * Gerente 4 -> Los 10 clientes con más dinero en sus cuentas
     *
     * @return
     */
    public List<Account> get10ClientsWithMoreMoney() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT a.*, c.name, c.dpi FROM ACCOUNTS a INNER JOIN CLIENTS c ON a.client_id = c.client_id ORDER BY a.credit DESC LIMIT 10";
        try (PreparedStatement ps = this.conexion.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                accounts.add(new Account(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return accounts;
    }

    /**
     * Gerente -> 5 Clientes que no han realizado transacciones dentro de un
     * intervalo de tiempo
     *
     * @param date1
     * @param date2
     * @return
     */
    public List<Client> getClientsWithOutTransactions(java.sql.Date date1, java.sql.Date date2) {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT c.* FROM (SELECT t.*, a.client_id FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id WHERE t.created_on BETWEEN ? AND ?) AS t1 RIGHT JOIN CLIENTS c ON t1.client_id = c.client_id WHERE t1.client_id IS NULL";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setDate(1, date1);
            ps.setDate(2, date2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clients.add(new Client(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return clients;
    }
}
