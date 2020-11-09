package com.bank.control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "ManagerCashController", urlPatterns = {"/ManagerCashController"})
public class ManagerCashController extends HttpServlet {

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
                requestAddCashier(request, response);
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
