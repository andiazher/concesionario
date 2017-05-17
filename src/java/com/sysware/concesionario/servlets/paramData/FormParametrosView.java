/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.paramData;

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
public class FormParametrosView extends HttpServlet {

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
                String tipo="";
                Entitie parametros = new Entitie(App.TABLE_PARAMETROSFORMS);
                try{
                    tipo= request.getParameter("tipof");
                }catch(NullPointerException s){
                    s.printStackTrace();
                }
                if(tipo.equals("formularios")){
                    ArrayList<Entitie> params = parametros.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        String param="";
                        String selected= "selected";
                        for(Entitie p: params){
                            if(!p.getDataOfLabel("FORM").equals(param)){
                                param= p.getDataOfLabel("FORM");
                                out.println("<option value=\""+p.getId()+"\" "+selected+">"+param+"</option>");
                                selected="";
                            }
                        }
                    }
                }
                if(tipo.equals("valores")){
                    String formulario= request.getParameter("formulario");
                    if(formulario=="" || formulario.equals("")){
                        formulario="1";
                    }
                    parametros.getEntitieID(formulario);
                    ArrayList<Entitie> params = parametros.getEntitieParam("FORM", parametros.getDataOfLabel("FORM"));
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<h4 class=\"card-title text-center\" id=\"titleContend\">"
                                    + "Valores para el formulario <b>"+parametros.getDataOfLabel("FORM")+"</b></h4>");
                        out.println("<table class=\"table\">");
                        out.println("<thead class=\"\">");
                        out.println("<th>Id</th>\n" +"<th>Valor1</th>\n" +"<th>Valor2</th>");
                        out.println("</thead>");
                        out.println("<tbody>");
                        for(Entitie p: params){
                            out.println("<tr>");
                            out.println("<td>"+p.getId()+"</td>");
                            out.println("<td>"+p.getDataOfLabel("VALUE")+" <a class=\"btn btn-success btn-xs btn-simple\""
                                    + " onclick=\"editarV1("+p.getId()+")\">Editar</a></td>");
                            out.println("<td>"+p.getDataOfLabel("VALUE2")+" <a class=\"btn btn-success btn-xs btn-simple\""
                                    + " onclick=\"editarV2("+p.getId()+")\">Editar</a></td>");
                            out.println("</tr>");
                        }
                        out.println("</tbody>");
                        out.println("</table>");
                    }
                }
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }catch(NullPointerException e){
            try (PrintWriter out = response.getWriter()) {
                out.println("<script type=\"text/javascript\">\n" +
                    "    swal(\n" +
                    "        'Error:',\n" +
                    "        'No se puede cargar el contenido',\n" +
                    "        'error'\n" +
                    "    )\n" +
                    "</script>");
            }
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
            Logger.getLogger(FormParametrosView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FormParametrosView.class.getName()).log(Level.SEVERE, null, ex);
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
