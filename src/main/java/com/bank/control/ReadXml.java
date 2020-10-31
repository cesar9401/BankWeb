package com.bank.control;

import com.bank.model.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Part;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author cesar31
 */
public class ReadXml {

    private final Part filePart;
    private Connection conexion;

    public ReadXml(Part filePart) {
        this.filePart = filePart;
    }

    public void readData() {
        getManagers();
        getCashiers();
        getClients();
        getTransactions();
    }
    
    /**
     * Metodo para leer gerentes
     */
    private void getManagers() {
        List<Manager> managers = new ArrayList<>();
        List<Element> listM = getData("GERENTE");
        for(Element e : listM) {
            managers.add(new Manager(e));
        }
        
        for(Manager m : managers) {
            System.out.println(m.toString());
        }
    }
    
    /**
     * Metodo para leer gerentes
     */
    private void getCashiers() {
        List<Cashier> cashiers = new ArrayList<>();
        List<Element> listC = getData("CAJERO");
        for(Element e : listC) {
            cashiers.add(new Cashier(e));
        }
        
        for(Cashier c : cashiers) {
            System.out.println(c.toString());
        }
    }
    
    /**
     * Metodo para leer clientes
     */
    private void getClients() {
        List<Client> clients = new ArrayList<>();
        List<Element> listC = getData("CLIENTE");
        for(Element e : listC) {
            clients.add(new Client(e));
        }
        
        for(Client c : clients) {
            System.out.println(c.toString());
            for(Account a : c.getAccounts()) {
                System.out.println(a.toString());
            }
        }
    }
    
    /**
     * Metodo para leer transacciones
     */
    private void getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        List<Element> listT = getData("TRANSACCION");
        for(Element e : listT) {
            transactions.add(new Transaction(e));
        }
        
        for(Transaction t : transactions) {
            System.out.println(t.toString());
        }
    }
    
    private List<Element> getData(String node) {
        List<Element> elements = new ArrayList<>();
        try {
            SAXBuilder builder = new SAXBuilder();
            InputStream input = filePart.getInputStream();
            Document document = builder.build(input);
            Element root = document.getRootElement();
            elements = root.getChildren(node);
            
        } catch (JDOMException | IOException ex) {
            ex.printStackTrace(System.out);
        }

        return elements;
    }
    
    public static java.sql.Date getDate(String source) {
        java.sql.Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fecha = format.parse(source);
            date = new java.sql.Date(fecha.getTime());
        } catch (ParseException ex) {
            ex.printStackTrace(System.out);
        }
        return date;
    }

    public static java.sql.Time getTime(String source) {
        java.sql.Time time = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Long ms = format.parse(source).getTime();
            time = new java.sql.Time(ms);
        } catch (ParseException ex) {
            ex.printStackTrace(System.out);
        }
        return time;
    }    

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
