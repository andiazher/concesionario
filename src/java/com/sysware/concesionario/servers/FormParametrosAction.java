/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servers;

import com.sysware.concesionario.app.App;
import com.sysware.concesionario.entitie.Entitie;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andre
 */
public class FormParametrosAction extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            if(request.getSession().getAttribute("session").equals("true")){
                try{
                    String id= request.getParameter("idp");
                    String tipo= request.getParameter("parametro");
                    String valor= request.getParameter("valor");
                    Entitie parametros = new Entitie(App.TABLE_PARAMETROSFORMS);
                    parametros.getEntitieID(id);
                    if(tipo.equals("1")){
                        parametros.getData().set(parametros.getColums().indexOf("VALUE"), valor);
                    }
                    if(tipo.equals("2")){
                        parametros.getData().set(parametros.getColums().indexOf("VALUE2"), valor);
                    }
                    parametros.update();
                    try (PrintWriter out = response.getWriter()) {
                        String form = parametros.getDataOfLabel("FORM");
                        out.println("<script type=\"text/javascript\">\n"
                            + "swal(\n" +
                            "'Valor cambiado!',\n" +
                            "'Nuevo valor= "+valor+" en el parametro valor"+tipo+" del formulario "+form+" se ha actualizado',\n" +
                            "'success'\n" +
                            ")\n"
                            + "</script>");
                    }
                }catch(NullPointerException s){
                    s.printStackTrace();
                }
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }
        catch(NullPointerException s){
            s.printStackTrace();
            response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FormParametrosAction.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FormParametrosAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
