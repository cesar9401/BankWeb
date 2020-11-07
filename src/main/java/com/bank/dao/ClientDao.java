package com.bank.dao;

import com.bank.model.Account;
import com.bank.model.Client;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author cesar31
 */
public class ClientDao {

    private final Connection conexion;
    private final Base64 base64;

    public ClientDao(Connection conexion) {
        this.conexion = conexion;
        this.base64 = new Base64();
    }

    /**
     * Metodo para insertar nuevo cliente a la base de datos
     *
     * @param c
     * @return
     * @throws java.sql.SQLException
     */
    public int insertClient(Client c) throws SQLException {
        int client_id = 0;
        String query = "INSERT INTO CLIENTS(client_id, name, dpi, birth, address, gender, password, pdf_dpi) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getClientId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getDpi());
            ps.setDate(4, c.getBirth());
            ps.setString(5, c.getAddress());
            ps.setBoolean(6, c.isGender());
            ps.setString(7, new String(base64.encode(c.getPassword().getBytes())));
            ps.setBlob(8, c.getPdfDpi());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                client_id = rs.getInt(1);
            }
        }

        return client_id;
    }

    /**
     * Metodo para insertar cliente capturando las excepciones
     *
     * @param c
     */
    public void insertClientData(Client c) {
        try {
            insertClient(c);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Metodo para insertar nuevos clientes a la base de datos desde vista y
     * crear cuenta
     *
     * @param c
     * @return
     */
    public int createClientAndAccount(Client c) {
        int clientId = 0;
        try {
            this.conexion.setAutoCommit(false);
            clientId = insertClient(c);
            Account a = new Account(0, c.getCreatedOn(), 0D);
            a.setClientId(clientId);
            AccountDao aDao = new AccountDao(conexion);
            aDao.insertAccount(a);
            this.conexion.commit();

        } catch (SQLException ex) {
            try {
                this.conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
            ex.printStackTrace(System.out);
        }

        return clientId;
    }

    /**
     * Metodo para obtener a un cliente segun codigo y password
     *
     * @param code
     * @param password
     * @return
     */
    public Client getClient(int code, String password) {
        String query = "SELECT * FROM CLIENTS WHERE client_id = ?";
        if (!password.equals("")) {
            query += " AND password = ?";
        }
        Client c = null;
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, code);
            if (!password.equals("")) {
                ps.setString(2, new String(base64.encode(password.getBytes())));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Client(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return c;
    }

    public List<Client> getClients(int type, String search) {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM CLIENTS";
        switch (type) {
            case 0:
                query += " WHERE client_id = ?";
                break;
            case 1:
                query += " WHERE name LIKE ?";
                break;
            case 2:
                query += " WHERE dpi = ?";
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
     * Metodo para actulizar informacion del cliente
     *
     * @param c
     */
    public void updateClient(Client c) {
        String query = "UPDATE CLIENTS SET name = ?, dpi = ?, birth = ?, address = ?, gender = ?, password = ? WHERE client_id = ?";
        try {
            PreparedStatement ps = this.conexion.prepareStatement(query);
            ps.setString(1, c.getName());
            ps.setString(2, c.getDpi());
            ps.setDate(3, c.getBirth());
            ps.setString(4, c.getAddress());
            ps.setBoolean(5, c.isGender());
            ps.setString(6, new String(base64.encode(c.getPassword().getBytes())));
            ps.setInt(7, c.getClientId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void getDPI(HttpServletResponse response, int clientId) {
        String query = "SELECT pdf_dpi FROM CLIENTS WHERE client_id = ?";
        InputStream input = null;
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    input = new ByteArrayInputStream(rs.getBytes("pdf_dpi"));
                }
            }
            byte[] data = new byte[input.available()];
            input.read(data, 0, input.available());
            response.getOutputStream().write(data);
            input.close();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
