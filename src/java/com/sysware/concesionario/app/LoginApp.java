/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.app;

import com.sysware.concesionario.entitie.Entitie;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andre
 */
public class LoginApp extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param config
     * @throws javax.servlet.ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        App.setParameter(
                getInitParameter("host"),
                getInitParameter("port"),
                getInitParameter("db"),
                getInitParameter("user"),
                getInitParameter("pass"),
                getInitParameter("infoschema")
        );
    }
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            if(request.getParameter("user").isEmpty() || request.getParameter("pass").isEmpty()){
                request.getSession().setAttribute("session", "false");
                    response.sendRedirect("login.jsp");
            }
            else{
                if(App.validateuser(request.getParameter("user"), request.getParameter("pass"))){
                    
                    request.getSession().setAttribute("session", "true");
                    
                    Entitie usuario = new Entitie(App.TABLE_USUARIO);
                    ArrayList<Entitie> usuarios= usuario.getEntitieParam("USUARIO", request.getParameter("user"));
                    usuario = usuarios.get(0);
                    Entitie role = new Entitie(App.TABLE_ROLES);
                    role.getEntitieID(usuario.getDataOfLabel("ID_ROL"));
                    request.getSession().setAttribute("userName", usuario.getDataOfLabel("NOMBRE"));
                    request.getSession().setAttribute("role", usuario.getDataOfLabel("ID_ROL"));
                    request.getSession().setAttribute("user", usuario.getDataOfLabel("USUARIO"));
                    Entitie menusOfRol = new Entitie(App.TABLE_ROL_MENU);
                    ArrayList<Entitie> menus = menusOfRol.getEntitieParam("ROL", role.getId());
                    String menuDef="SIN MENU";
                    for(Entitie menu: menus){
                        if(menu.getDataOfLabel("MENU").equals(role.getDataOfLabel("MENUDEF"))){
                            menuDef = menu.getDataOfLabel("MENU");
                        }
                    }
                    request.getSession().setAttribute("menu", menuDef);
                    response.sendRedirect("app.jsp");
                }
                else{
                    request.getSession().setAttribute("session", "false");
                    response.sendRedirect("login.jsp?validate=Credenciales+Invalidas");
                }
                
            }
        }
        catch (NullPointerException ex) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<meta http-equiv=\"Refresh\" content=\"0;url=login.jsp?validate=Error: "+ex+"\">");
            }
        }
        catch(SQLException s){
            try (PrintWriter out = response.getWriter()) {
                out.println("<meta http-equiv=\"Refresh\" content=\"0;url=login.jsp?validate=Ha+ocurrido+un+error,+intentar+nuevamente+en+unos+segundos. SQL=NULL and Error="+s+"\">");
            }
        }
        try (PrintWriter out = response.getWriter()) {
                out.println("Error");
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
    /**
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
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
            Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
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
