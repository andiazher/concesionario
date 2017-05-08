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
import java.text.DecimalFormat;
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
public class DetailsOrdenServiceView extends HttpServlet {

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
                Entitie orden = new Entitie(App.TABLE_OS);
                Entitie vehiculo = new Entitie(App.TABLE_VEHICULO);
                Entitie propietario = new Entitie(App.TABLE_CLIENTE);
                Entitie ordendetalle = new Entitie(App.TABLE_DOS);
                String name="Sin nombre";
                String menu ="0";
                String get ="";
                ArrayList<Entitie> servicios = new ArrayList<>();
                try{
                    orden.getEntitieID(request.getParameter("variable"));                    
                    menu = request.getParameter("variablem");
                    servicios = ordendetalle.getEntitieParam("OS", orden.getId());
                    
                    vehiculo.getEntitieID(orden.getDataOfLabel("VEHICULO"));
                    if(vehiculo.getId().equals("0")){
                        for(String s: vehiculo.getColums()){
                            vehiculo.getData().add("--");
                        }
                    }
                    propietario.getEntitieID(orden.getDataOfLabel("PROPIETARIO"));
                    
                }catch(NullPointerException s){
                    s.printStackTrace();
                }catch(IndexOutOfBoundsException s){
                    s.printStackTrace();
                }
                try (PrintWriter out = response.getWriter()) {
                    
                    out.println("<h4 class=\"card-title text-center\" id=\"titleContend\"> Servicios para la orden No. <b>"+orden.getId()+"</b> Placa:"
                            + " <b>"+vehiculo.getDataOfLabel("PLACA")+"</b> Cliente: <b>"
                            +propietario.getDataOfLabel("TIPODOC")+propietario.getDataOfLabel("CEDULA")+"</b></h4>");
                    
                    out.println("<div class=\"table-responsive\">");
                    out.println("<table class=\"table\">");
                    out.println("<thead class=\"\">");
                    
                    out.println("<th>No</th>");
                    out.println("<th>OS</th>");
                    out.println("<th>Servicio</th>");
                    out.println("<th>Valor</th>");
                    out.println("<th>Estado</th>");
                    out.println("<th>Observaciones</th>");
                    
                    out.println("</thead>");
                    out.println("<tbody>");
                    for(Entitie i : servicios){
                        out.println("<tr>");
                        String a="";
                        
                        out.println("<td>"+i.getId()+"</td>");
                        //out.println("<td><a href=\"#SERVICE"+i.getId()+"\" class=\"\" "+a+">"+i.getId()+"</a></td>");
                        out.println("<td>"+orden.getId()+"</td>");
                        Entitie servicio = new Entitie(App.TABLE_SERVICIOS);
                        servicio.getEntitieID(i.getDataOfLabel("SERVICIO"));
                        out.println("<td>"+servicio.getDataOfLabel("DESCRIPCION")+"</td>");
                        DecimalFormat formateador = new DecimalFormat("###,###.##");
                        out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("VALOR")))+"</td>");
                        Entitie estado = new Entitie(App.TABLE_ESTADO);
                        estado.getEntitieID(i.getDataOfLabel("ESTADO"));
                        if(estado.getId().equals("3")){
                            out.println("<td>"+estado.getDataOfLabel("DESCRIPCION")+"<a href=\"#revert\" class=\"btn btn-warning btn-xs\" onclick=\"revertService('"+i.getId()+"')\" > REVERTIR <a/></td>");
                        }
                        else{
                            out.println("<td>"+estado.getDataOfLabel("DESCRIPCION")+"</td>");
                        }
                        out.println("<td>"+i.getDataOfLabel("OBSERVACIONES")+"</td>");
                        out.println("</tr>");
                    }
                    
                    out.println("</tbody>");
                    out.println("</table>");
                    out.println("</div>");
                }
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }
        catch(NullPointerException e){
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
            Logger.getLogger(DetailsOrdenServiceView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DetailsOrdenServiceView.class.getName()).log(Level.SEVERE, null, ex);
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
