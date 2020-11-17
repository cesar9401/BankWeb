package com.bank.control;

import com.bank.conexion.Conexion;
import com.bank.dao.*;
import com.bank.model.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "ControllerPdf", urlPatterns = {"/ControllerPdf"})
public class ControllerPdf extends HttpServlet {

    private final Connection conexion = Conexion.getConnection();
    private final ReportDao reportDao = new ReportDao(conexion);
    private final ManagerDao managerDao = new ManagerDao(conexion);
    private final CashierDao cashierDao = new CashierDao(conexion);
    private final String PATH = "/resources/reports/";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControllerPdf</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerPdf at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "manager1":
                getManager1(request, response);
                break;

            case "manager2":
                getManager2(request, response);
                break;

            case "manager3":
                getManager3(request, response);
                break;

            case "manager4":
                getManager4(request, response);
                break;

            case "manager5":
                getManager5(request, response);
                break;

            case "manager6":
                getManager6(request, response);
                break;

            case "manager7":
                getManager7(request, response);
                break;

            case "cashier1":
                getCashier1(request, response);
                break;

            case "cashier2":
                getCashier2(request, response);
                break;
        }
    }

    private void getManager1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String entity = request.getParameter("entity");
        int managerId = (int) request.getSession().getAttribute("code");
        List<ChangeHistory> history = reportDao.getHistory(managerId, entity);
        Map<String, Object> parametros = getManagerData(request, "Historial de Cambios en " + entity);
        generatePdf(request, response, history, parametros, "gerente1.jrxml");
    }

    private void getManager2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Double limit = Double.parseDouble(request.getParameter("limit"));
        Map<String, Object> parametros = getManagerData(request, "Clientes con transacciones monetarias mayores a Q. " + limit);
        List<Transaction> transactions = reportDao.getTopTransactions(limit);
        generatePdf(request, response, transactions, parametros, "gerente2.jrxml");
    }

    private void getManager3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Double limit = Double.parseDouble(request.getParameter("limit"));
        Map<String, Object> parametros = getManagerData(request, "Clientes con transacciones monetarias sumadas mayores a Q. " + limit);
        List<Transaction> summed = reportDao.getTopSummedTransactions(limit);
        generatePdf(request, response, summed, parametros, "gerente3.jrxml");
    }

    private void getManager4(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> parametros = getManagerData(request, "Clientes Top10");
        List<Account> accounts = reportDao.get10ClientsWithMoreMoney();
        generatePdf(request, response, accounts, parametros, "gerente4.jrxml");
    }

    private void getManager5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));
        List<Client> clients = reportDao.getClientsWithOutTransactions(date1, date2);
        Map<String, Object> parametros = getManagerData(request, "Clientes Inactivos entre " + date1 + " y " + date2);
        generatePdf(request, response, clients, parametros, "gerente5.jrxml");
    }

    private void getManager6(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String type = request.getParameter("type");
        String title = "Historial de Transacciones por cliente segun " + type;
        List<Transaction> transactions = new ArrayList<>();
        switch (type) {
            case "Codigo":
                Integer code = Integer.parseInt(request.getParameter("code"));
                title += ": " + code;
                transactions = reportDao.transactionHistoryByClients(code, null, null, null);
                break;
            case "Nombre":
                String name = request.getParameter("name");
                title += ": " + name;
                transactions = reportDao.transactionHistoryByClients(null, name, null, null);
                break;
            case "Limite":
                Double limit1 = Double.parseDouble(request.getParameter("limit1"));
                Double limit2 = Double.parseDouble(request.getParameter("limit2"));
                title += " entre" + limit1 + " y " + limit2;
                transactions = reportDao.transactionHistoryByClients(null, null, limit1, limit2);
                break;
        }
        Map<String, Object> parametros = getManagerData(request, title);
        generatePdf(request, response, transactions, parametros, "gerente6.jrxml");
    }

    private void getManager7(HttpServletRequest request, HttpServletResponse response) throws IOException {
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));
        Cashier cashier = reportDao.casiersWithMoreTransactions(date1, date2);
        List<Cashier> cashiers = new ArrayList<>();
        cashiers.add(cashier);

        Map<String, Object> parametros = getManagerData(request, "Cajero con más transacciones entre entre " + date1 + " y " + date2);
        generatePdf(request, response, cashiers, parametros, "gerente7.jrxml");
    }

    private void getCashier1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int cashierId = (int) request.getSession().getAttribute("code");
        java.sql.Date date = ReadXml.getDate(request.getParameter("date"));
        Map<String, Object> parametros = getCashierData(request, "Listado de Transacciones durante: " + date);
        List<Transaction> turn = reportDao.getTrasactionsInTurn(cashierId, date);
        generatePdf(request, response, turn, parametros, "cashier1.jrxml");
    }

    private void getCashier2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int cashierId = (int) request.getSession().getAttribute("code");
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));
        Map<String, Object> parametros = getCashierData(request, "Listado de Transacciones entre las fechas " + date1 + " y " + date2);
        List<Transaction> transactions = reportDao.getTrasactionsInTurnDuringPeriod(cashierId, date1, date2);
        generatePdf(request, response, transactions, parametros, "cashier2.jrxml");
    }

    /**
     * Metodo para generar pdf
     *
     * @param request
     * @param response
     * @param beanCollection
     * @param parametros
     * @param name
     * @throws IOException
     */
    private void generatePdf(HttpServletRequest request, HttpServletResponse response, Collection<?> beanCollection, Map<String, Object> parametros, String name) throws IOException {
        try {
            response.setContentType("application/pdf");
            File file = new File(request.getServletContext().getRealPath(PATH + name));
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dateSource = new JRBeanCollectionDataSource(beanCollection);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dateSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (JRException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Metodo para obtener los parametros para el pdf del gerente
     *
     * @param request
     * @param titulo
     * @return
     */
    private Map<String, Object> getManagerData(HttpServletRequest request, String titulo) {
        int code = (int) request.getSession().getAttribute("code");
        Manager m = managerDao.getManager(code, "");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", titulo);
        parametros.put("nombre", m.getName());
        parametros.put("cargo", "Gerente");
        return parametros;
    }

    private Map<String, Object> getCashierData(HttpServletRequest request, String titulo) {
        int code = (int) request.getSession().getAttribute("code");
        Cashier cashier = cashierDao.getCashier(code, "");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", titulo);
        parametros.put("nombre", cashier.getName());
        parametros.put("cargo", "Cajero");
        return parametros;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
