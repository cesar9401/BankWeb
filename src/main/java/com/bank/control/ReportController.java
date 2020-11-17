package com.bank.control;

import com.bank.conexion.Conexion;
import com.bank.dao.*;
import com.bank.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "ReportController", urlPatterns = {"/ReportController"})
public class ReportController extends HttpServlet {

    private final Connection conexion = Conexion.getConnection();
    private final ReportDao reportDao = new ReportDao(conexion);
    private final AccountDao accountDao = new AccountDao(conexion);

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
            out.println("<title>Servlet ReportController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportController at " + request.getContextPath() + "</h1>");
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
                request.setAttribute("manager1", true);
                setReportManager(request, response);
                break;

            case "manager2":
                request.setAttribute("manager2", true);
                setReportManager(request, response);
                break;

            case "manager3":
                request.setAttribute("manager3", true);
                setReportManager(request, response);
                break;

            case "manager4":
                getManager4(request, response);
                break;

            case "manager5":
                request.setAttribute("manager5", true);
                setReportManager(request, response);
                break;

            case "manager6":
                request.setAttribute("manager6", true);
                setReportManager(request, response);
                break;

            case "manager7":
                request.setAttribute("manager7", true);
                setReportManager(request, response);
                break;

            case "cashier1":
                request.setAttribute("cashier1", true);
                setReportCashier(request, response);
                break;

            case "cashier2":
                request.setAttribute("cashier2", true);
                setReportCashier(request, response);
                break;

            case "client1":
                int code = (int) request.getSession().getAttribute("code");
                List<Account> accounts = accountDao.getAccounts(code, false);
                request.setAttribute("accounts", accounts);
                request.setAttribute("client1", true);
                setReportClient(request, response);
                break;

            case "client2":
                request.setAttribute("client2", true);
                setReportClient(request, response);
                break;

            case "client3":
                request.setAttribute("client3", true);
                setReportClient(request, response);
                break;

            case "client4":
                getClient4(request, response);
                break;

            case "client5":
                getClient5(request, response);
                break;

        }
    }

    /**
     * Redirigir a reportes del gerente
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void setReportManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("manager-reports.jsp").forward(request, response);
    }

    /**
     * Redirigir a reportes del cajero
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void setReportCashier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("cashier-reports.jsp").forward(request, response);
    }

    /**
     * Redigir a reportes del cliente
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void setReportClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("client-reports.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        switch (action) {
            case "getManager1":
                getManager1(request, response);
                break;
            case "getManager2":
                getManager2(request, response);
                break;
            case "getManager3":
                getManager3(request, response);
                break;
            case "getManager5":
                getManager5(request, response);
                break;
            case "getManager6":
                getManager6(request, response);
                break;
            case "getManager7":
                getManager7(request, response);
                break;
            case "getCashier1":
                getCashier1(request, response);
                break;
            case "getCashier2":
                getCashier2(request, response);
                break;
            case "getClient1":
                getClient1(request, response);
                break;
            case "getClient2":
                getClient2(request, response);
                break;
            case "getClient3":
                getClient3(request, response);
                break;
        }
    }

    private void getManager1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int managerId = (int) request.getSession().getAttribute("code");
        String entity = request.getParameter("entity");
        List<ChangeHistory> history = reportDao.getHistory(managerId, entity);
        request.setAttribute("history", history);
        request.setAttribute("entity", entity);

        request.setAttribute("manager1", true);
        setReportManager(request, response);
    }

    private void getManager2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Double limit = Double.parseDouble(request.getParameter("limit"));
        List<Transaction> transactions = reportDao.getTopTransactions(limit);
        request.setAttribute("transactions", transactions);
        request.setAttribute("limit", limit);

        request.setAttribute("manager2", true);
        setReportManager(request, response);
    }

    private void getManager3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Double limit = Double.parseDouble(request.getParameter("limit"));
        List<Transaction> summed = reportDao.getTopSummedTransactions(limit);
        request.setAttribute("summed", summed);
        request.setAttribute("limit", limit);

        request.setAttribute("manager3", true);
        setReportManager(request, response);
    }

    private void getManager4(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Account> accounts = reportDao.get10ClientsWithMoreMoney();
        request.setAttribute("accounts", accounts);

        request.setAttribute("manager4", true);
        setReportManager(request, response);
    }

    private void getManager5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));
        List<Client> clients = reportDao.getClientsWithOutTransactions(date1, date2);
        request.setAttribute("clients", clients);
        request.setAttribute("date1", date1);
        request.setAttribute("date2", date2);

        request.setAttribute("manager5", true);
        setReportManager(request, response);
    }

    private void getManager6(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type6");
        List<Transaction> transactions = new ArrayList<>();
        switch (type) {
            case "Codigo":
                Integer code = Integer.parseInt(request.getParameter("client-code"));
                request.setAttribute("code", code);
                transactions = reportDao.transactionHistoryByClients(code, null, null, null);
                break;
            case "Nombre":
                String name = request.getParameter("client-name");
                request.setAttribute("name", name);
                transactions = reportDao.transactionHistoryByClients(null, name, null, null);
                break;
            case "Limite":
                Double limit1 = Double.parseDouble(request.getParameter("limit1"));
                Double limit2 = Double.parseDouble(request.getParameter("limit2"));
                request.setAttribute("limit1", limit1);
                request.setAttribute("limit2", limit2);
                transactions = reportDao.transactionHistoryByClients(null, null, limit1, limit2);
                break;
        }
        request.setAttribute("type", type);
        request.getSession().setAttribute("transactions", transactions);
        request.setAttribute("manager6", true);
        setReportManager(request, response);
    }

    private void getManager7(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));
        Cashier cashier = reportDao.casiersWithMoreTransactions(date1, date2);

        request.setAttribute("cashier", cashier);
        request.setAttribute("date1", date1);
        request.setAttribute("date2", date2);

        request.setAttribute("manager7", true);
        setReportManager(request, response);
    }

    private void getCashier1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cashierId = (int) request.getSession().getAttribute("code");
        java.sql.Date date = ReadXml.getDate(request.getParameter("date1"));
        List<Transaction> turn = reportDao.getTrasactionsInTurn(cashierId, date);

        request.setAttribute("turn", turn);
        request.setAttribute("date", date);
        request.setAttribute("cashier1", true);
        setReportCashier(request, response);
    }

    private void getCashier2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cashierId = (int) request.getSession().getAttribute("code");
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));

        List<Transaction> transactions = reportDao.getTrasactionsInTurnDuringPeriod(cashierId, date1, date2);
        request.setAttribute("transactions", transactions);
        request.setAttribute("date1", date1);
        request.setAttribute("date2", date2);
        request.setAttribute("cashier2", true);
        setReportCashier(request, response);
    }

    private void getClient1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int code = (int) request.getSession().getAttribute("code");
        int account = Integer.parseInt(request.getParameter("client-account"));
        int year = Integer.parseInt(request.getParameter("client-year"));
        List<Transaction> top15 = reportDao.top15TransactionsForYear(account, year);

        request.setAttribute("top15", top15);
        request.setAttribute("account", account);
        request.setAttribute("year", year);

        List<Account> accounts = accountDao.getAccounts(code, false);
        request.setAttribute("accounts", accounts);
        request.setAttribute("client1", true);
        setReportClient(request, response);
    }

    private void getClient2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int code = (int) request.getSession().getAttribute("code");
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));
        List<Transaction> transactions = reportDao.transactionsDuringPeriod(code, date1, date2);

        request.setAttribute("transactions", transactions);
        request.setAttribute("date1", date1);
        request.setAttribute("date2", date2);

        request.setAttribute("client2", true);
        setReportClient(request, response);
    }

    private void getClient3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int code = (int) request.getSession().getAttribute("code");
        java.sql.Date date1 = ReadXml.getDate(request.getParameter("date1"));
        java.sql.Date date2 = ReadXml.getDate(request.getParameter("date2"));
        List<Transaction> top1 = reportDao.getTrasactionsAccountWithMoreMoney(code, date1, date2);

        request.setAttribute("top1", top1);
        request.setAttribute("date1", date1);
        request.setAttribute("date2", date2);

        request.setAttribute("client3", true);
        setReportClient(request, response);
    }

    private void getClient4(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int code = (int) request.getSession().getAttribute("code");
        List<AssociatedAccount> received = reportDao.getRequestReceived(code);
        request.setAttribute("received", received);
        request.setAttribute("client4", true);
        setReportClient(request, response);
    }

    private void getClient5(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int code = (int) request.getSession().getAttribute("code");
        List<AssociatedAccount> sent = reportDao.getRequestSent(code);
        request.setAttribute("sent", sent);
        request.setAttribute("client5", true);
        setReportClient(request, response);
    }
}
