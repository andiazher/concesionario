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
public class DataPolizasAnuRenView extends HttpServlet {

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
                String name="Sin nombre";
                ArrayList<Entitie> polizas= new ArrayList<>();
                String identificacion="";
                
                try{
                    identificacion= request.getParameter("identificacion");
                    
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                Entitie servicio = new Entitie(App.TABLE_OSDETALLE);
                name= "POR FAVOR INGRESAR IDENTIFICACIÓN DEL CLIENTE";
                
                if(!identificacion.equals("")){
                    name= "RESULTADOS PARA CLIENTE: </b>"+identificacion +"<b>";
                    //CONSULTA DE LAS DIFERENTES TABLAS DE POLIZAS PARA SABER SI EL CLIENTE TIENE UNA POLIZA
                }
                
                try (PrintWriter out = response.getWriter()) {
                    String idOrder="-1";
                    out.println("<h4 class=\"card-title text-center\" id=\"titleContend\"> <b> "+name+" </b> </h4>");
                    
                    int count=0;
                    
                    if(polizas.isEmpty()){
                        out.println("<h4>No hay resultados para ese cliente</h4>");
                    }
                    else{
                        out.println("<table class=\"table\">");
                        out.println("<thead class=\"\">\n" +
    "                                                <th>Poliza</th>\n" +
    "                                                <th>Fecha</th>\n" +
    "                                                <th>Identificación</th>\n" +
    "                                                <th>Servicio</th>\n" +
    "                                                <th>Valor</th>\n" +
    "                                                <th>A</th>\n" +
    "                                                <th>R</th>\n" +
    "                                            </thead>");
                        out.println("<tbody>");
                        for(Entitie i: polizas){
                            if(!idOrder.equals(i.getId())){
                                String danger = "text-danger";
                                danger = "";
                                out.println("<tr class=\""+danger+"\" >");
                                out.println("<td>"+i.getDataOfLabel("FECHAT")+"</td>");
                                Entitie os = new Entitie(App.TABLE_ORDENSERVICIO);
                                Entitie canals = new Entitie(App.TABLE_CANALES);
                                Entitie conce = new Entitie(App.TABLE_CONCESIONARIO);
                                os.getEntitieID(i.getDataOfLabel("OS"));
                                canals.getEntitieID(os.getDataOfLabel("ID_CANAL"));
                                conce.getEntitieID(canals.getDataOfLabel("ID_CONCESIONARIO"));
                                out.println("<td>"+conce.getDataOfLabel("NOMBRE")+"</td>");
                                out.println("<td>"+i.getDataOfLabel("OS")+"</td>");
                                Entitie servicion = new Entitie(App.TABLE_SERVICIOS);
                                servicion.getEntitieID(i.getDataOfLabel("SERVICIO"));
                                out.println("<td>"+servicion.getDataOfLabel("DESCRIPCION")+"</td>");
                                DecimalFormat formateador = new DecimalFormat("###,###.##");
                                int valors= Integer.parseInt(i.getDataOfLabel("COM_CONCE"));
                                count+=valors;
                                out.println("<td class=\"text-right\">$"+formateador.format(valors)+"</td>");
                                out.println("<td><input type=\"checkbox\" name=\""+i.getId()+"\" checked "
                                        + "onchange=\"reloadvalue("+i.getId()+")\"></td>");
                                out.println("<td><a href=\"#rechazarDOS="+i.getId()+"\" onclick=\"rechazar("+i.getId()+")\">Rechazar</a></td>");
                                out.println("</tr>");
                            }

                        }
                    }
                    
                    out.println("</tbody>");
                    out.println("</table>");
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
            Logger.getLogger(DataPolizasAnuRenView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DataPolizasAnuRenView.class.getName()).log(Level.SEVERE, null, ex);
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
