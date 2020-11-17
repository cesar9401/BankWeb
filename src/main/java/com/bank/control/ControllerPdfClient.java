package com.bank.control;

import com.bank.conexion.Conexion;
import com.bank.dao.ClientDao;
import com.bank.dao.ReportDao;
import com.bank.model.AssociatedAccount;
import com.bank.model.Client;
import com.bank.model.Transaction;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "ControllerPdfClient", urlPatterns = {"/ControllerPdfClient"})
public class ControllerPdfClient extends HttpServlet {

    private final Connection conexion = Conexion.getConnection();
    private final ReportDao reportDao = new ReportDao(conexion);
    private final ClientDao clientDao = new ClientDao(conexion);
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
            out.println("<title>Servlet ControllerPdfClient</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerPdfClient at " + request.getContextPath() + "</h1>");
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
            case "client1":
                getClient1(request, response);
                break;

            case "client2":
                getClient2(request, response);
                break;

            case "client3":
                getClient3(request, response);
                break;

            case "client4":
                getClient4(request, response);
                break;

            case "client5":
                getClient5(request, response);
                break;
        }
    }

    private void getClient1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int account = Integer.parseInt(request.getParameter("account"));
        int year = Integer.parseInt(request.getParameter("year"));
        List<Transaction> top15 = reportDao.top15TransactionsForYear(account, year);
        Map<String, Object> parametros = getDataClient(request, "Top15 transacciones de la cuenta " + account + " en el año " + year);
        generatePdf(request, response, top15, parametros, "client1.jrxml");
    }

    private void getClient2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int code = (int) request.getSession().getAttribute("code");
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));
        List<Transaction> transactions = reportDao.transactionsDuringPeriod(code, date1, date2);
        Map<String, Object> parametros = getDataClient(request, "Transacciones durante las fechas " + date1 + " y " + date2);
        generatePdf(request, response, transactions, parametros, "client2.jrxml");
    }

    private void getClient3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int code = (int) request.getSession().getAttribute("code");
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));
        List<Transaction> top1 = reportDao.getTrasactionsAccountWithMoreMoney(code, date1, date2);
        String str = "";
        if (!top1.isEmpty()) {
            str += top1.get(0).getAccountId();
        }
        Map<String, Object> parametros = getDataClient(request, "Transacciones de la cuenta " + str + " durante " + date1 + " y " + date2);
        generatePdf(request, response, top1, parametros, "client3.jrxml");
    }

    private void getClient4(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int code = (int) request.getSession().getAttribute("code");
        List<AssociatedAccount> received = reportDao.getRequestReceived(code);
        Map<String, Object> parametros = getDataClient(request, "Solicitudes de Asociaciones Recibidas");
        generatePdf(request, response, received, parametros, "client4.jrxml");
    }

    private void getClient5(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int code = (int) request.getSession().getAttribute("code");
        List<AssociatedAccount> sent = reportDao.getRequestSent(code);
        Map<String, Object> parametros = getDataClient(request, "Solicitudes de Asociaciones Enviadas");
        generatePdf(request, response, sent, parametros, "client5.jrxml");
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

    private Map<String, Object> getDataClient(HttpServletRequest request, String titulo) {
        int code = (int) request.getSession().getAttribute("code");
        Client c = clientDao.getClient(code, "");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", titulo);
        parametros.put("nombre", c.getName());
        parametros.put("id", c.getClientId());
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
    }
}
