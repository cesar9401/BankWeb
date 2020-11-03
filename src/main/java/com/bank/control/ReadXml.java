package com.bank.control;

import com.bank.dao.AccountDao;
import com.bank.dao.CashierDao;
import com.bank.dao.ClientDao;
import com.bank.dao.ManagerDao;
import com.bank.dao.TransactionDao;
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
        getWorkDays();
        getManagers();
        getCashiers();
        getClients();
        getTransactions();
    }

    /**
     * Metodo para ingresar los horarios de trabajo a la base de datos
     */
    private void getWorkDays() {
        List<WorkDay> days = new ArrayList<>();
        days.add(new WorkDay(1, "MATUTINO", getTime("06:00:00"),getTime("14:30:00")));
        days.add(new WorkDay(2, "VESPERTINO", getTime("13:00:00"), getTime("22:00:00")));
        days.add(new WorkDay(3, "24 HORAS", getTime("00:00:00"), getTime("23:59:59")));
        
        ManagerDao mDao = new ManagerDao(conexion);
        for(WorkDay w : days){
            mDao.insertWorkday(w);
            System.out.println(w.toString());
        }
    }

    /**
     * Metodo para leer gerentes e insertar en la base de datos
     */
    private void getManagers() {
        List<Manager> managers = new ArrayList<>();
        List<Element> listM = getData("GERENTE");
        for (Element e : listM) {
            managers.add(new Manager(e));
        }

        ManagerDao mDao = new ManagerDao(conexion);
        for (Manager m : managers) {
            mDao.insertManager(m);
            System.out.println(m.toString());
        }
    }

    /**
     * Metodo para leer cajeros e insertar en la base de datos
     */
    private void getCashiers() {
        List<Cashier> cashiers = new ArrayList<>();
        List<Element> listC = getData("CAJERO");
        
        //Agregar banca virtual
        Cashier tmp = new Cashier(101, "24 HORAS", "Banca Virtual", "101", "8cX7%%tedj4!yJm4");
        tmp.setAddress("Banco El Billeton");
        tmp.setGender(true);
        cashiers.add(tmp);
        for (Element e : listC) {
            cashiers.add(new Cashier(e));
        }
        
        CashierDao cDao = new CashierDao(conexion);
        for (Cashier c : cashiers) {
            cDao.insertCashier(c);
            System.out.println(c.toString());
        }
    }

    /**
     * Metodo para leer clientes e insertar en la base de datos
     */
    private void getClients() {
        List<Client> clients = new ArrayList<>();
        List<Element> listC = getData("CLIENTE");
        for (Element e : listC) {
            clients.add(new Client(e));
        }

        ClientDao cDao = new ClientDao(conexion);
        AccountDao aDao = new AccountDao(conexion);
        for (Client c : clients) {
            cDao.insertClient(c);
            System.out.println(c.toString());
            for (Account a : c.getAccounts()) {
                aDao.insertAccount(a);
                System.out.println(a.toString());
            }
        }
    }

    /**
     * Metodo para leer transacciones e insertar en la base de datos
     */
    private void getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        List<Element> listT = getData("TRANSACCION");
        for (Element e : listT) {
            transactions.add(new Transaction(e));
        }

        TransactionDao tDao = new TransactionDao(conexion);
        for (Transaction t : transactions) {
            tDao.insertTransaction(t);
            System.out.println(t.toString());
        }
    }

    /**
     * Metodo para leer archivo xml
     * @param node
     * @return 
     */
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

    /**
     * Metodo para obtener fecha de un string
     * @param source
     * @return 
     */
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

    /**
     * Metodo para obtener tiempo de un string
     * @param source
     * @return 
     */
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
