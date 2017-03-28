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
public class ServiciosPendientes extends HttpServlet {

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
                Entitie sdetalle = new Entitie(App.TABLE_DOS);
                String name="";
                ArrayList<Entitie> sds =new ArrayList<>();
                try{
                    menu.getEntitieID(request.getParameter("variable"));
                }catch(NullPointerException s){
                    
                }
                name+= "SERVICIOS PENDIENTES POR APROBACIÓN";
                sds = sdetalle.getEntitieParam("ESTADO", "1");

                try (PrintWriter out = response.getWriter()) {
                    String idOrder="-1";
                    for(Entitie i: sds){
                        if(!idOrder.equals(i.getId())){
                            Entitie servicio = new Entitie(App.TABLE_SERVICIOS);
                            servicio.getEntitieID(i.getDataOfLabel("SERVICIO"));
                            String a="";
                            String href="#OS"+i.getId();
                            Entitie orden = new Entitie(App.TABLE_OS);
                            orden.getEntitieID(i.getDataOfLabel("OS"));
                            Entitie client = new Entitie(App.TABLE_CLIENTE);
                            client.getEntitieID(orden.getDataOfLabel("PROPIETARIO"));
                            if(servicio.getDataOfLabel("ISASISTENCIA").equals("1")){
                                //THE MENU LOAD IS 32 - ASISTENCIA DENTAL
                                a="onclick=\"openViewServiceSpecial("+i.getId()+")\"";
                            }
                            else{
                                a="onclick=\"openViewService("+i.getId()+")\"";
                            }
                            
                            out.println("<tr>");
                            out.println("<td><a href=\""+href+"\" class=\"\" "+a+">"+i.getId()+"</a></td>");
                            out.println("<td>"+orden.getId()+"</td>");
                            Entitie vehiculo = new Entitie(App.TABLE_VEHICULO);
                            vehiculo.getEntitieID(orden.getDataOfLabel("VEHICULO"));
                            out.println("<td>"+vehiculo.getDataOfLabel("PLACA")+"</td>");
                            out.println("<td>"+servicio.getDataOfLabel("DESCRIPCION")+"</td>");
                            DecimalFormat formateador = new DecimalFormat("###,###.##");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("VALOR")))+"</td>");
                            Entitie canal = new Entitie(App.TABLE_CANALES);
                            canal.getEntitieID(orden.getDataOfLabel("ID_CANAL"));
                            out.println("<td>"+canal.getDataOfLabel("NOMBRE")+"</td>");
                            out.println("<td>"+orden.getDataOfLabel("FECHA")+"</td>");
                            out.println("<td>"+i.getDataOfLabel("OBSERVACIONES")+"</td>");
                            out.println("</tr>");
                            idOrder = i.getId();
                        }
                        
                    }
                    out.println("<script type=\"text/javascript\">\n" +
                        "    $().ready(function(){\n" +
                        "        $(\"#titleContend\").html(\""+name+"\");\n" +
                        "    });\n" +
                        "</script>");
                    out.println(""
                            + ""
                            + "");
                }
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }
        catch(NullPointerException e){
            //response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            System.out.println("Error: No se cargaron datos - "+e);
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
            Logger.getLogger(ServiciosPendientes.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServiciosPendientes.class.getName()).log(Level.SEVERE, null, ex);
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
