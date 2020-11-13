package com.bank.control;

import com.bank.conexion.Conexion;
import com.bank.dao.*;
import com.bank.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "ManagerController", urlPatterns = {"/ManagerController"})
@MultipartConfig(maxFileSize = 16177215)
public class ManagerController extends HttpServlet {

    private final Connection conexion = Conexion.getConnection();
    private final ManagerDao managerDao = new ManagerDao(conexion);
    private final ClientDao clientDao = new ClientDao(conexion);
    private final CashierDao cashierDao = new CashierDao(conexion);
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
            out.println("<title>Servlet ManagerController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerController at " + request.getContextPath() + "</h1>");
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
            case "takeChoice":
                //Ir a login o cargar archivos
                List<Manager> managers = managerDao.getManagers();
                if (managers.isEmpty()) {
                    request.getRequestDispatcher("load-data.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                break;

            case "profile":
                //Mostrar el perfil del gerente
                int code = (int) request.getSession().getAttribute("code");
                Manager manager = managerDao.getManager(code, "");
                setProfileManager(request, response, manager.getManagerId());
                break;

            case "signOff":
                //Cerrar sesion
                request.getSession().invalidate();
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;

            case "updateData":
                //Formulario para actualizar datos del gerente
                updateManagerData(request, response);
                break;

            case "searchClients":
                //Para filtrar, editar y agregar clientes
                searchClients(request, response);
                break;

            case "addClient":
                //Formulario para agregar un cliente y crear cuenta
                addClient(request, response);
                break;

            case "getDPI":
                int clientId = Integer.parseInt(request.getParameter("clientId"));
                clientDao.getDPI(response, clientId);
                break;

            case "requestUpdateClient":
                requestUpdateClient(request, response);
                break;
        }
    }

    /**
     * Metodo para redireccionar hacia formulario para actualizar datos del
     * gerente
     *
     * @param request
     * @param response
     */
    private void updateManagerData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int code = (int) request.getSession().getAttribute("code");
        Manager manager = managerDao.getManager(code, "");
        request.setAttribute("manager", manager);
        request.getRequestDispatcher("dataForManager.jsp").forward(request, response);
    }

    /**
     * Metodo para dirigir al formulario para buscar, agregar o editar clientes
     *
     * @param request
     * @param response
     */
    private void searchClients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("searchClients.jsp").forward(request, response);
    }

    /**
     * Metodo para redirigir al fomulario para agregar cliente y cuenta
     *
     * @param request
     * @param response
     */
    private void addClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("addClient", true);
        request.getRequestDispatcher("dataForManager.jsp").forward(request, response);
    }

    /**
     * Metodo para llevar la informacion del cliente al formulario para
     * actualizar datos
     *
     * @param request
     * @param response
     */
    private void requestUpdateClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        Client client = clientDao.getClient(clientId, "");
        request.setAttribute("client", client);
        request.getRequestDispatcher("dataForManager.jsp").forward(request, response);
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
            case "data":
                //Cargar datos a la base de datos
                System.out.println(action);
                Part filePart = request.getPart("data");
                ReadXml read = new ReadXml(filePart);
                read.setConexion(conexion);
                read.readData();
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;

            case "signIn":
                //Iniciar sesion
                logIn(request, response);
                break;

            case "updateManager":
                //Actualizar informacion manager
                updateDataManager(request, response);
                break;

            case "findClients":
                //Buscar clientes
                findClients(request, response);
                break;

            case "insertClient":
                //Insertar nuevo cliente en la base de datos
                insertClient(request, response);
                break;

            case "updateClient":
                updateClient(request, response);
                break;

            case "insertAccount":
                insertAccount(request, response);
                break;
        }
    }

    /**
     * Metodo para iniciar sesion
     *
     * @param request
     * @param response
     */
    private void logIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int code = Integer.parseInt(request.getParameter("code"));
        String password = request.getParameter("password");
        String type = request.getParameter("type");

        switch (type) {
            case "manager":
                Manager manager = managerDao.getManager(code, password);
                if (manager != null) {
                    request.getSession().setAttribute("code", manager.getManagerId());
                    setProfileManager(request, response, manager.getManagerId());
                } else {
                    setErrorLogin(request, response);
                }
                break;

            case "cashier":
                Cashier cashier = cashierDao.getCashier(code, password);
                if (cashier != null) {
                    request.getSession().setAttribute("code", cashier.getCashierId());
                    request.setAttribute("cashier", cashier);
                    request.getRequestDispatcher("cashierView.jsp").forward(request, response);
                } else {
                    setErrorLogin(request, response);
                }
                break;

            case "client":
                Client client = clientDao.getClient(code, password);
                if (client != null) {
                    request.getSession().setAttribute("code", client.getClientId());
                    request.setAttribute("client", client);
                    request.getRequestDispatcher("clientView.jsp").forward(request, response);
                } else {
                    setErrorLogin(request, response);
                }
                break;
        }
    }

    /**
     * Metodo para indicar que los datos ingresados para inicio de sesion son
     * incorrectos
     */
    private void setErrorLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", true);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Metodod para mostrar perfil del gerente
     *
     * @param request
     * @param response
     * @param managerId
     * @throws ServletException
     * @throws IOException
     */
    private void setProfileManager(HttpServletRequest request, HttpServletResponse response, int managerId) throws ServletException, IOException {
        Manager manager = managerDao.getManager(managerId, "");
        request.setAttribute("manager", manager);
        request.getRequestDispatcher("managerView.jsp").forward(request, response);
    }

    /**
     * Metodo para actualizar informacion del gerente
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void updateDataManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Crear manager
        Manager manager = new Manager(request);
        System.out.println(manager.toString());
        //Actualizar en base de datos
        managerDao.updateManager(manager);
        request.setAttribute("update", true);
        //Perfil del gerente
        setProfileManager(request, response, manager.getManagerId());
    }

    /**
     * Metodo para buscar clientes y devolverlos a la vista de busqueda
     *
     * @param request
     * @param response
     */
    private void findClients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int type = Integer.parseInt(request.getParameter("type"));
        String search = request.getParameter("search");
        //Obtener clientes de la base de datos
        List<Client> clients = clientDao.getClients(type, search);

        //Atributos para vista
        request.setAttribute("type", type);
        request.setAttribute("search", search);
        request.setAttribute("clients", clients);

        searchClients(request, response);
    }

    /**
     * Metodo para insertar nuevo cliente en la base de datos
     *
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    private void insertClient(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException, IOException {
        Client client = new Client(request, true);
        System.out.println(client.toString());
        //Insertar en la base de datos y obtener id y numero de cuentas del ciente
        int clientId = clientDao.createClientAndAccount(client);

        //Obtener cliente y cuentas de DB
        client = clientDao.getClient(clientId, "");
        System.out.println(client.toString());
        client.setAccounts(accountDao.getAccounts(clientId));

        //setear atributo
        request.setAttribute("newClient", client);

        System.out.println("Cliente agregado");
        System.out.println(client.toString());
        System.out.println(client.getAccounts().get(0).toString());

        //Redirigir a buscar clientes
        searchClients(request, response);
    }

    /**
     * Metodo para actualizar informacion de cliente en la base de datos
     *
     * @param request
     * @param response
     */
    private void updateClient(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException, IOException {
        Client client = new Client(request, false);
        System.out.println(client.toString());
        //Actualizar en la base de datos
        clientDao.updateClient(client);
        //Obtener de DB
        client = clientDao.getClient(client.getClientId(), "");
        //Enviar atributo
        request.setAttribute("updateClient", client);
        //Redigir a la vista de buscar clientes
        searchClients(request, response);
    }

    /**
     * Metodo para insertar una cuenta nueva a un cliente que ya esta en el
     * sistema
     *
     * @param request
     * @param response
     */
    private void insertAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Obtener informacion de cuenta en formulario
        Account account = new Account(request);
        System.out.println(account.toString());
        System.out.println(account.getClientId());

        //Insertar en la base de datos
        int accountId = accountDao.insertNewAccount(account);

        //Obtener cliente de base de datos
        Client client = clientDao.getClient(account.getClientId(), "");
        client.setAccounts(new ArrayList<Account>());
        client.getAccounts().add(accountDao.getAccount(accountId));
        //Enviar atributo
        request.setAttribute("newClient", client);
        request.setAttribute("newAccount", true);

        System.out.println("Cuenta agregada");
        System.out.println(client.toString());
        System.out.println(client.getAccounts().get(0).toString());

        //Redirigir a buscar clientes
        searchClients(request, response);
    }
}
