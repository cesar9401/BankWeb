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
@WebServlet(name = "ClientController", urlPatterns = {"/ClientController"})
public class ClientController extends HttpServlet {

    private final Connection conexion = Conexion.getConnection();
    private final ClientDao clientDao = new ClientDao(conexion);
    private final AccountDao accountDao = new AccountDao(conexion);
    private final AssociatedAccountDao associatedDao = new AssociatedAccountDao(conexion);
    private final TransactionDao transactionDao = new TransactionDao(conexion);

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
            out.println("<title>Servlet ClientController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClientController at " + request.getContextPath() + "</h1>");
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
            case "profile":
                //Perfil del cliente
                setProfileClient(request, response);
                break;

            case "requestAssociation":
                //Iniciar proceso de solicitar asociacion de cuenta
                requestAssociation(request, response);
                break;

            case "respondToRequests":
                //Ver y responder solicitudes
                respondToRequests(request, response);
                break;

            case "answerRequest":
                //Responder solicitud de asociacion
                answerRequest(request, response);
                break;

            case "transferRequest":
                //Formulario para solicitar transferencia
                transferRequest(request, response);
                break;
        }
    }

    /**
     * Perfil del cliente
     *
     * @param request
     * @param response
     */
    private void setProfileClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = (int) request.getSession().getAttribute("code");
        Client client = clientDao.getClient(clientId, "");
        List<Account> accounts = accountDao.getAccounts(clientId, false);
        client.setAccounts(accounts);
        List<AssociatedAccount> requests = associatedDao.getRequestForAssociations(client.getClientId());
        request.setAttribute("client", client);
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("clientView.jsp").forward(request, response);
    }

    /**
     * Iniciar proceso de asociacion de cuenta
     *
     * @param request
     * @param response
     */
    private void requestAssociation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("association.jsp").forward(request, response);
    }

    /**
     * Metodo para ver solicitudes de asociacion
     *
     * @param request
     * @param response
     */
    private void respondToRequests(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = (int) request.getSession().getAttribute("code");
        List<AssociatedAccount> requests = associatedDao.getRequestForAssociations(clientId);
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("requests.jsp").forward(request, response);
    }

    /**
     * Reponder solicitud de asocacion
     *
     * @param request
     * @param response
     */
    private void answerRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String answer = request.getParameter("answer");
        int associatedId = Integer.parseInt(request.getParameter("associatedId"));
        //Respuesta hacia la basse de datos
        associatedDao.insertAnswer(associatedId, answer);
        AssociatedAccount associated = associatedDao.getAssociated(associatedId, null, null);
        if (associated.getStatus().equals("ACEPTADA")) {
            request.setAttribute("associated", associated);
        }
        respondToRequests(request, response);
    }

    /**
     * Formulario para solicitar transferencia
     *
     * @param request
     * @param response
     */
    private void transferRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = (int) request.getSession().getAttribute("code");
        List<Account> accounts = accountDao.getAccounts(clientId, false);
        List<Account> destinations = accountDao.getAccounts(clientId, true);
        request.setAttribute("accounts", accounts);
        request.setAttribute("destinations", destinations);
        request.getRequestDispatcher("transfer.jsp").forward(request, response);
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
            case "search-account":
                //Buscar cuenta para asociacion
                searchAccount(request, response);
                break;

            case "newAssociaction":
                //Asociacion de cuenta
                newAssociaction(request, response);
                break;

            case "newTransaction":
                //Nueva transaccion web
                newTransaction(request, response);
                break;
        }
    }

    /**
     * Metodo para buscar cuenta para asociar
     *
     * @param request
     * @param response
     */
    private void searchAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountId = Integer.parseInt(request.getParameter("account"));
        int clientId = (int) request.getSession().getAttribute("code");

        AssociatedAccount asso = associatedDao.getAssociated(null, clientId, accountId);
        if (asso != null) {
            if (asso.getStatus().equals("ACEPTADA")) {
                //Ya fue aceptada
                request.setAttribute("accepted", asso);
            } else if (asso.getTryNumber() == 3) {
                //Sobrepaso numero de intentos
                request.setAttribute("noAttempts", asso);
            } else {
                //En estado de espera
                request.setAttribute("waiting", asso);
            }
        } else {
            //No existe asociacion
            //Cuenta de la base de datos
            Account account = accountDao.getAccount(accountId);
            if (account != null) {
                if (clientId != account.getClientId()) {
                    Client client = clientDao.getClient(clientId, "");
                    request.setAttribute("client", client);
                    request.setAttribute("account", account);
                } else {
                    //Cuenta propia
                    request.setAttribute("notAllowed", account);
                }
            } else {
                //No existe cuenta
                request.setAttribute("noAccount", accountId);
            }
        }

        requestAssociation(request, response);
    }

    /**
     * Metodo para solicitar asociacion de cuenta
     *
     * @param request
     * @param response
     */
    private void newAssociaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Obtener de formulario
        AssociatedAccount associated = new AssociatedAccount(request);
        int associatedId = associatedDao.insertAssociatedAccount(associated);
        //Obtener de base de datos
        associated = associatedDao.getAssociated(associatedId, null, null);
        System.out.println(associated.toString());
        //Setear atributos
        request.setAttribute("associated", associated);
        requestAssociation(request, response);
    }

    /**
     * Nueva transaccion web
     *
     * @param request
     * @param response
     */
    private void newTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountOrigin = Integer.parseInt(request.getParameter("origin-account"));
        int accountDestination = Integer.parseInt(request.getParameter("destination-account"));
        Double amount = Double.parseDouble(request.getParameter("transfer-amount"));
        java.sql.Date date = ReadXml.getDate(request.getParameter("transfer-date"));
        java.sql.Time time = ReadXml.getTime(request.getParameter("transfer-time"));

        Transaction t1 = new Transaction(0, accountOrigin, date, time, "DEBITO", amount, 101);
        Transaction t2 = new Transaction(0, accountDestination, date, time, "CREDITO", amount, 101);
        //Cuentas distintas
        if (t1.getAccountId() != t2.getAccountId()) {

            int[] ids = transactionDao.createTransactionWeb(t1, t2);
            Transaction r1 = transactionDao.getTransaction(ids[0]);
            Transaction r2 = transactionDao.getTransaction(ids[1]);

            if (r1 != null) {
                //Transacciones exitosas
                List<Transaction> transactions = new ArrayList<>();
                transactions.add(r1);
                transactions.add(r2);
                request.setAttribute("transactions", transactions);

            } else {
                //Transaccion no realizada por falta de dinero
                //Account a = accountDao.getAccount(accountOrigin);
                request.setAttribute("outOfMoney", true);
                request.setAttribute("origin", t1);
                request.setAttribute("destination", accountDestination);
            }
        } else {
            //Mismas cuentas
            request.setAttribute("sameAccounts", true);
            request.setAttribute("origin", t1);
        }
        
        transferRequest(request, response);
    }
}
