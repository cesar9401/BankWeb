package com.bank.control;

import com.bank.conexion.Conexion;
import com.bank.dao.AccountDao;
import com.bank.dao.CashierDao;
import com.bank.dao.TransactionDao;
import com.bank.model.Account;
import com.bank.model.Cashier;
import com.bank.model.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "ManagerCashController", urlPatterns = {"/ManagerCashController"})
@MultipartConfig(maxFileSize = 16177215)
public class ManagerCashController extends HttpServlet {

    private final Connection conexion = Conexion.getConnection();
    private final CashierDao cashierDao = new CashierDao(conexion);
    private final AccountDao accountDao = new AccountDao(conexion);
    private final TransactionDao transactionDao = new TransactionDao(conexion);
    private final HistoryController history = new HistoryController(conexion);

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
            out.println("<title>Servlet ManagerCashController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerCashController at " + request.getContextPath() + "</h1>");
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
            case "searchCashiers":
                //Formulario para buscar cajeros
                searchCashiers(request, response);
                break;

            case "requestAddCashier":
                //Formulario para agragar cajeros
                requestAddCashier(request, response);
                break;

            case "requestUpdateCashier":
                //Formulario para actualizar datos cajero
                requestUpdateCashier(request, response);
                break;

            case "profile":
                //regresar al perfil del cajero
                setProfileCashier(request, response);
                break;

            case "requestDeposit":
                //Formulario para solicitar deposito
                requestDeposit(request, response);
                break;

            case "requestCreditTransaction":
                //Formulario para solicitar retiro
                requestCreditTransaction(request, response);
                break;
        }
    }

    /**
     * Metodo para redirigir a la busqueda de cajeros
     *
     * @param request
     * @param response
     */
    private void searchCashiers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("searchCashiers.jsp").forward(request, response);
    }

    /**
     * Metodo para redirigir al formulario para ingresar un nuevo cajero
     *
     * @param request
     * @param response
     */
    private void requestAddCashier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("addCashier", true);
        request.getRequestDispatcher("dataForManager.jsp").forward(request, response);
    }

    /**
     * Metodo para redirigir la formulario para editar cajero
     *
     * @param request
     * @param response
     */
    private void requestUpdateCashier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cashierId = Integer.parseInt(request.getParameter("cashierId"));
        Cashier cashier = cashierDao.getCashier(cashierId, "");
        request.setAttribute("cashier", cashier);
        request.getRequestDispatcher("dataForManager.jsp").forward(request, response);
    }

    /**
     * Perfil del cajero
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void setProfileCashier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cashierId = (int) request.getSession().getAttribute("code");
        Cashier cashier = cashierDao.getCashier(cashierId, "");
        request.setAttribute("cashier", cashier);
        request.getRequestDispatcher("cashierView.jsp").forward(request, response);
    }

    /**
     * Formulario para solicitar deposito
     *
     * @param request
     * @param response
     */
    private void requestDeposit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("deposit.jsp").forward(request, response);
    }

    /**
     * Formulario para solicitar retiro
     *
     * @param request
     * @param response
     */
    private void requestCreditTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("withdrawal.jsp").forward(request, response);
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
            case "findCashiers":
                //Busqueda de cajeros
                findCashiers(request, response);
                break;

            case "insertCashier":
                //Metodo para insertar cajeros
                insertCashier(request, response);
                break;

            case "updateCashier":
                //Para editar cajeros
                updateCashier(request, response);
                break;

            case "search-account":
                //Buscar cuenta para deposito
                searchAccount(request, response);
                break;

            case "newDeposit":
                //Nuevo deposito
                newDeposit(request, response);
                break;

            case "search-account-credit":
                //Buscar cuenta para retirar
                searchAccountCredit(request, response);
                break;

            case "newWithdrawal":
                //Nuevo retiro
                newWithdrawal(request, response);
                break;
        }
    }

    /**
     * Metodo para buscar cajeros
     *
     * @param request
     * @param response
     */
    private void findCashiers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int type = Integer.parseInt(request.getParameter("type"));
        String search = request.getParameter("search");
        List<Cashier> cashiers = cashierDao.getCashiers(type, search);
        for (Cashier c : cashiers) {
            System.out.println(c.toString());
        }
        //Atributos para la vista
        request.setAttribute("cashiers", cashiers);
        //Vista de busqueda
        searchCashiers(request, response);
    }

    /**
     * Metodo para insertar un nuevo cajero a la base de datos
     *
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     * @throws ServletException
     * @throws IOException
     */
    private void insertCashier(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException, IOException {
        //Obtener de formulario
        Cashier cashier = new Cashier(request);
        System.out.println(cashier.toString());
        //Insertar en la base de datos

        int cashierId = cashierDao.insertCashier(cashier, true);
        cashier = cashierDao.getCashier(cashierId, "");
        System.out.println(cashier.toString());

        request.setAttribute("newCashier", cashier);
        //Redirigir a la vista de busqueda
        searchCashiers(request, response);
    }

    /**
     * Metodo para editar la informacion de un cajero y agregarla a la base de
     * datos
     *
     * @param request
     * @param response
     */
    private void updateCashier(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException, IOException {
        int managerId = (int) request.getSession().getAttribute("code");
        Cashier cashier = new Cashier(request);
        Cashier oldCashier = cashierDao.getCashier(cashier.getCashierId(), "");
        //Actualizar
        cashierDao.updateCashier(cashier);
        //Obtener de la base de datos
        cashier = cashierDao.getCashier(cashier.getCashierId(), "");
        history.historyCashier(managerId, cashier, oldCashier);
        request.setAttribute("updateCashier", cashier);
        //Redirigir a la vista de busqueda
        searchCashiers(request, response);
    }

    /**
     * Metodo para buscar una cuenta para deposito
     *
     * @param request
     * @param response
     */
    private void searchAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountId = Integer.parseInt(request.getParameter("account"));
        //Cuenta de la base de datos
        Account account = accountDao.getAccount(accountId);

        if (account != null) {
            request.setAttribute("account", account);
        } else {
            request.setAttribute("noAccount", accountId);
        }

        requestDeposit(request, response);
    }

    /**
     * Nuevo deposito
     *
     * @param request
     * @param response
     */
    private void newDeposit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = new Transaction(request);
        //Transaccion a la base de datos
        int transactionId = transactionDao.createTransactionEx(transaction);
        transaction = transactionDao.getTransaction(transactionId);
        //Atributos para confirmar deposito
        request.setAttribute("deposit", transaction);
        requestDeposit(request, response);
    }

    /**
     * Buscar cuenta para retirar
     *
     * @param request
     * @param response
     */
    private void searchAccountCredit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountId = Integer.parseInt(request.getParameter("account"));
        //Cuenta de la base de datos
        Account account = accountDao.getAccount(accountId);

        if (account != null) {
            request.setAttribute("accountCredit", account);
        } else {
            request.setAttribute("noAccount", accountId);
        }
        requestCreditTransaction(request, response);
    }

    /**
     * Nuevo retiro
     *
     * @param request
     * @param response
     */
    private void newWithdrawal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = new Transaction(request);
        int accountId = transaction.getAccountId();
        System.out.println(transaction.toString());
        //Transaccion a la base de datos
        int transactionId = transactionDao.createTransactionEx(transaction);
        transaction = transactionDao.getTransaction(transactionId);
        if (transaction != null) {
            //Cuenta no nula
            request.setAttribute("withdrawal", transaction);
        } else {
            //Cuenta nula
            Account account = accountDao.getAccount(accountId);
            request.setAttribute("account", account);
        }
        requestCreditTransaction(request, response);
    }
}
