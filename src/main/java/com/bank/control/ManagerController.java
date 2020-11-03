package com.bank.control;

import com.bank.conexion.Conexion;
import com.bank.dao.*;
import com.bank.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
                setProfileManager(request, response, manager);
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
        request.getRequestDispatcher("updateData.jsp").forward(request, response);
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
                    setProfileManager(request, response, manager);
                } else {
                    setErrorLogin(request, response);
                }
                break;

            case "cashier":
                Cashier cashier = cashierDao.getCashier(code, password);
                if (cashier != null) {
                    request.getSession().setAttribute("code", cashier.getCashierId());
                } else {
                    setErrorLogin(request, response);
                }
                break;

            case "client":
                Client client = clientDao.getClient(code, password);
                if (client != null) {
                    request.getSession().setAttribute("code", client.getClientId());
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

    private void setProfileManager(HttpServletRequest request, HttpServletResponse response, Manager manager) throws ServletException, IOException {
        request.setAttribute("manager", manager);
        request.getRequestDispatcher("managerView.jsp").forward(request, response);
    }
}
