package com.bank.dao;

import com.bank.model.*;
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
                            tmp.setChangedName(tmp.getManagerMainName());
                            tmp.setChangedId(tmp.getManagerMainId());
                            break;
                        case "CASHIERS":
                            tmp.setCashierName(rs.getString("cashier_name"));
                            tmp.setChangedName(tmp.getCashierName());
                            tmp.setChangedId(tmp.getCashierId());
                            break;
                        case "CLIENTS":
                            tmp.setClientName(rs.getString("client_name"));
                            tmp.setChangedName(tmp.getClientName());
                            tmp.setChangedId(tmp.getClientId());
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
    public List<Transaction> getTopTransactions(double limit) {
        List<Transaction> top = new ArrayList<>();
        String query = "SELECT t.*, c.name AS client_name, c.client_id, ca.name AS cashier_name FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id "
                + "WHERE t.amount > ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setDouble(1, limit);
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
    public List<Transaction> getTopSummedTransactions(double limit) {
        List<Transaction> top = new ArrayList<>();
        String query = "SELECT t.type, c.name AS client_name, c.client_id, c.dpi, SUM(t.amount) AS total FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id GROUP BY t.type, c.client_id "
                + "HAVING total > ? ORDER BY total DESC";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setDouble(1, limit);
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

    /**
     * Gerente 6 -> Historial de transacciones por cliente, se puede realizar la
     * búsqueda basados en nombre, y dentro de límites de dinero en la cuenta
     *
     * @param clientId
     * @param name
     * @param lim1
     * @param lim2
     * @return
     */
    public List<Transaction> transactionHistoryByClients(Integer clientId, String name, Double lim1, Double lim2) {
        List<Transaction> history = new ArrayList<>();
        String query = "SELECT t.*, c.name AS client_name, c.client_id, ca.name AS cashier_name, a.credit FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id ";
        if (clientId != null) {
            query += "WHERE c.client_id = ?";
        } else if (name != null) {
            query += "WHERE c.name LIKE ?";
        } else {
            query += "WHERE a.credit BETWEEN ? AND ?";
        }
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            if (clientId != null) {
                ps.setInt(1, clientId);
            } else if (name != null) {
                name = "%" + name + "%";
                ps.setString(1, name);
            } else {
                ps.setDouble(1, lim1);
                ps.setDouble(2, lim2);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction tmp = new Transaction(rs);
                    tmp.setCredit(rs.getDouble("credit"));
                    history.add(tmp);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return history;
    }

    /**
     * Manager 7 -> Cajero que más transacciones ha realizado en un intervalo de
     * tiempo
     *
     * @param date1
     * @param date2
     * @return
     */
    public Cashier casiersWithMoreTransactions(java.sql.Date date1, java.sql.Date date2) {
        Cashier top = null;
        String query = "SELECT ca.*, w.workday, w.start_time, w.end_time, COUNT(t.transaction_id) AS quantity FROM TRANSACTIONS t INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id INNER JOIN WORKDAYS w ON w.workday_id = ca.workday_id "
                + "WHERE t.created_on BETWEEN ? AND ? GROUP BY ca.cashier_id ORDER BY quantity DESC LIMIT 1";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setDate(1, date1);
            ps.setDate(2, date2);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                top = new Cashier(rs);
                top.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return top;
    }

    /**
     * Cajero 1 -> Listado de depósitos y retiros realizados durante su turno de
     * manera ordenada, mostrando el total existente en caja (depósitos -
     * retiros)
     *
     * @param cashierId
     * @param date
     * @return
     */
    public List<Transaction> getTrasactionsInTurn(int cashierId, java.sql.Date date) {
        List<Transaction> turn = new ArrayList<>();
        String query = "SELECT t.*, c.name AS client_name, c.client_id, ca.name AS cashier_name FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id "
                + "WHERE t.cashier_id = ? AND t.created_on = ?";
        try {
            PreparedStatement ps = this.conexion.prepareStatement(query);
            ps.setInt(1, cashierId);
            ps.setDate(2, date);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    turn.add(new Transaction(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return turn;
    }

    /**
     * Cajero 2 -> Listado de las transacciones realizadas por día en un
     * intervalo de tiempo, mostrando el balance final
     *
     * @param cashierId
     * @param date1
     * @param date2
     * @return
     */
    public List<Transaction> getTrasactionsInTurnDuringPeriod(int cashierId, java.sql.Date date1, java.sql.Date date2) {
        List<Transaction> turn = new ArrayList<>();
        String query = "SELECT t.*, c.name AS client_name, c.client_id, ca.name AS cashier_name FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id "
                + "WHERE t.cashier_id = ? AND t.created_on BETWEEN ? AND ?";
        try {
            PreparedStatement ps = this.conexion.prepareStatement(query);
            ps.setInt(1, cashierId);
            ps.setDate(2, date1);
            ps.setDate(3, date2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    turn.add(new Transaction(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return turn;
    }

    /**
     * Cliente 1 -> Las últimas 15 transacciones más grandes realizadas en el
     * último año, por cuenta
     *
     * @param accountId
     * @param year
     * @return
     */
    public List<Transaction> top15TransactionsForYear(int accountId, int year) {
        List<Transaction> top = new ArrayList<>();
        String query = "SELECT t.*, c.name AS client_name, c.client_id, ca.name AS cashier_name FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id "
                + "WHERE t.account_id = ? AND YEAR(t.created_on) = ? ORDER BY t.amount DESC LIMIT 15";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, accountId);
            ps.setInt(2, year);
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
     * Cliente 2 -> Listado de todas las transacciones realizadas dentro de un
     * intervalo de tiempo mostrando el cambio del dinero de la cuenta por cada
     * transacción
     *
     * @param clientId
     * @param date1
     * @param date2
     * @return
     */
    public List<Transaction> transactionsDuringPeriod(int clientId, java.sql.Date date1, java.sql.Date date2) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT t.*, c.name AS client_name, c.client_id, ca.name AS cashier_name FROM TRANSACTIONS t INNER JOIN ACCOUNTS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id "
                + "WHERE c.client_id = ? AND t.created_on BETWEEN ? AND ? ORDER BY t.transaction_id";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, clientId);
            ps.setDate(2, date1);
            ps.setDate(3, date2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new Transaction(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

        return transactions;
    }

    /**
     * Cliente 3 -> La cuenta con más dinero y sus transacciones con una fecha
     * inició a ingresar y la fecha en curso como límite superior
     *
     * @param clientId
     * @param date1
     * @param date2
     * @return
     */
    public List<Transaction> getTrasactionsAccountWithMoreMoney(int clientId, java.sql.Date date1, java.sql.Date date2) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT t.*, c.name AS client_name, c.client_id, ca.name AS cashier_name, a.created_on AS account_created, a.credit FROM TRANSACTIONS t INNER JOIN (SELECT * FROM ACCOUNTS WHERE client_id = ? ORDER BY credit DESC LIMIT 1) AS a ON t.account_id = a.account_id INNER JOIN CLIENTS c ON a.client_id = c.client_id INNER JOIN CASHIERS ca ON t.cashier_id = ca.cashier_id "
                + "WHERE t.created_on BETWEEN ? AND ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, clientId);
            ps.setDate(2, date1);
            ps.setDate(3, date2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new Transaction(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return transactions;
    }

    /**
     * Cliente 4 -> Historial con el listado de solicitud de asociación de
     * cuenta recibidas con su estado
     *
     * @param clientId
     * @return
     */
    public List<AssociatedAccount> getRequestReceived(int clientId) {
        List<AssociatedAccount> received = new ArrayList<>();
        String query = "SELECT aso.*, c.name AS associated_name, c.dpi AS associated_dpi, a.client_id AS account_client_id, c1.name AS account_name, c1.dpi AS account_dpi FROM ASSOCIATED_ACCOUNTS aso INNER JOIN CLIENTS c ON aso.client_id = c.client_id INNER JOIN ACCOUNTS a ON aso.account_id = a.account_id INNER JOIN CLIENTS c1 ON a.client_id = c1.client_id "
                + "WHERE a.client_id = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    received.add(new AssociatedAccount(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return received;
    }

    /**
     * Cliente 5 -> Historial con el listado de solicitud de asociación de
     * cuenta realizadas con su estado
     *
     * @param clientId
     * @return
     */
    public List<AssociatedAccount> getRequestSent(int clientId) {
        List<AssociatedAccount> sent = new ArrayList<>();
        String query = "SELECT aso.*, c.name AS associated_name, c.dpi AS associated_dpi, a.client_id AS account_client_id, c1.name AS account_name, c1.dpi AS account_dpi FROM ASSOCIATED_ACCOUNTS aso INNER JOIN CLIENTS c ON aso.client_id = c.client_id INNER JOIN ACCOUNTS a ON aso.account_id = a.account_id INNER JOIN CLIENTS c1 ON a.client_id = c1.client_id "
                + "WHERE aso.client_id = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sent.add(new AssociatedAccount(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return sent;
    }
}
