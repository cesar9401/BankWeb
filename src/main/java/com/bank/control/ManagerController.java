package com.bank.control;

import com.bank.conexion.Conexion;
import com.bank.dao.ManagerDao;
import com.bank.model.Manager;
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
                List<Manager> managers = managerDao.getManagers();
                if (managers.isEmpty()) {
                    request.getRequestDispatcher("load-data.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                break;
        }
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
                System.out.println(action);
                Part filePart = request.getPart("data");
                ReadXml read = new ReadXml(filePart);
                read.setConexion(conexion);
                read.readData();
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;

            case "signIn":
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
    private void logIn(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        
        switch(type) {
            case "manager":
                break;
                
            case "cashier":
                break;
                
            case "client":
                break;
        }
    }

}
