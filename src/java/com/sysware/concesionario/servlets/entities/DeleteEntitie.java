/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.entities;

import com.sysware.concesionario.app.App;
import com.sysware.concesionario.entitie.Entitie;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class DeleteEntitie extends HttpServlet {

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
                Entitie menu = new Entitie(App.TABLE_MENUS);
                String id="0";
                Entitie entitie = new Entitie();
                try{
                    id = request.getParameter("id");
                    menu.getEntitieID(request.getParameter("variable"));
                    entitie = new Entitie(menu.getDataOfLabel("ENTIDAD"));
                    entitie.getEntitieID(id);
                    try (PrintWriter out = response.getWriter()) {
                        if(entitie.delete()){
                            out.println("<script type=\"text/javascript\">\n"
                                + "swal(\n" +
                            "'Borrado!',\n" +
                            "'El registro con ID="+id+" ha sido borrado',\n" +
                            "'success'\n" +
                            ")\n"
                            + "</script>");
                        }else{
                            out.println("<script type=\"text/javascript\">\n"
                                +" swal(\n" +
                            "'Error!',\n" +
                            "'El registro con ID="+id+" NO ha sido borrado',\n" +
                            "'error'\n" +
                            ")\n"
                            + "</script>");
                        }
                        
                    }
                }catch(NullPointerException s){s.printStackTrace();}
                catch(IndexOutOfBoundsException s){s.printStackTrace();}
                
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }
        catch(NullPointerException e){
            response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
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
            Logger.getLogger(DeleteEntitie.class.getName()).log(Level.SEVERE, null, ex);
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
