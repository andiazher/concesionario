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
public class FormPagoLiquidacion extends HttpServlet {

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
                ArrayList<Entitie> servicios= new ArrayList<>();
                String fi="";
                String ff="";
                String concesionario="";
                String canal="";
                try{
                    fi= request.getParameter("fi");
                    ff= request.getParameter("ff");
                    concesionario= request.getParameter("concesionario");
                    canal= request.getParameter("canal");
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                Entitie servicio = new Entitie(App.TABLE_OSDETALLE);
                name= "RESULTADOS PARA ORDENES PENDIENTES";
                boolean nada=false;
                ArrayList<String> param1=new ArrayList<>();
                ArrayList<String> param2=new ArrayList<>();
                ArrayList<String> operation=new ArrayList<>();
                param1.add("ESTADOL");
                param2.add("PENDIENTE");
                operation.add("=");
                nada=true;
                if(!fi.equals("")){
                    name+=" DESPUES DE: </b>"+fi+"<b>";
                    param1.add("FECHAT");
                    param2.add(fi);
                    operation.add(">=");
                    nada=true;
                }
                if(!ff.equals("")){
                    if(nada){
                        name+=",";
                    }
                    name+=" ANTES DE: </b>"+ff+"<b> ";
                    param1.add("FECHAT");
                    param2.add(ff +" 23:59:59");
                    operation.add("<=");
                    nada=true;
                }
                String qry="";
                String tables ="";
                if(!canal.equals("")){
                    if(nada){
                        name+=",";
                        qry +=" and";
                    }
                    Entitie conce = new Entitie(App.TABLE_CONCESIONARIO);
                    try{
                        conce.getEntitieID(concesionario);
                    }
                    catch(IndexOutOfBoundsException s){}
                    name+=" CONCESIONARIO: </b>"+conce.getDataOfLabel("NOMBRE")+"<b>, ";
                    Entitie canalEntitie = new Entitie(App.TABLE_CANALES);
                    canalEntitie.getEntitieID(canal);
                    name+=" CANAL: </b>"+canalEntitie.getDataOfLabel("NOMBRE")+"<b> ";
                    tables =App.TABLE_ORDENSERVICIO+","+App.TABLE_CANALES;
                    qry += " orden_servicio.ID = orden_detalle.OS and canales.ID = orden_servicio.ID_CANAL "
                            + "and canales.ID ="+canal;
                    nada=true;
                }else{
                    if(!concesionario.equals("")){
                        if(nada){
                            name+=" Y";
                            qry +=" and";
                        }
                        Entitie conce = new Entitie(App.TABLE_CONCESIONARIO);
                        try{
                            conce.getEntitieID(concesionario);
                        }
                        catch(IndexOutOfBoundsException s){}
                        name+=" CONCESIONARIO: </b>"+conce.getDataOfLabel("NOMBRE")+"<b> ";
                        tables =App.TABLE_ORDENSERVICIO+","+App.TABLE_CANALES+","+App.TABLE_CONCESIONARIO;

                        qry += " orden_servicio.ID = orden_detalle.OS and canales.ID =  orden_servicio.ID_CANAL "
                                + "and conce.ID = canales.ID_CONCESIONARIO and conce.ID = "+concesionario;
                        nada=true;
                    }
                }
                if(param1.isEmpty() && param2.isEmpty() && operation.isEmpty() && nada==false){
                    servicios = servicio.getEntities();
                    name="TODAS LAS ORDENES DE SERVICIO";
                }
                else{
                    servicios = servicio.getEntitieParams(param1, param2, operation, qry, tables);
                }
                
                try (PrintWriter out = response.getWriter()) {
                    String idOrder="-1";
                    out.println("<h4 class=\"card-title text-center\" id=\"titleContend\"> <b> "+name+" </b> </h4>");
                    out.println("<table class=\"table\">");
                    out.println("<thead class=\"\">\n" +
"                                                <th>Fecha</th>\n" +
"                                                <th>Concesionario</th>\n" +
"                                                <th>OS</th>\n" +
"                                                <th>Servicio</th>\n" +
"                                                <th>Comisión</th>\n" +
"                                                <th>L</th>\n" +
"                                                <th>R</th>\n" +
"                                            </thead>");
                    out.println("<tbody>");
                    int count=0;
                    for(Entitie i: servicios){
                        if(!idOrder.equals(i.getId())){
                            String a="";
                            a+="onclick=\"openViewOrderService("+i.getId()+")\"";
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
                            out.println("<td><input type=\"checkbox\" name=\""+i.getId()+"\" checked></td>");
                            out.println("<td><a href=\"#rechazarDOS"+i.getId()+"\" onclick=\"rechazar("+i.getId()+")\">Rechazar</a></td>");
                            out.println("</tr>");
                        }
                        
                    }
                    out.println("</tbody>");
                    out.println("<tr class=\"\" >");
                        out.println("<td class=\"text-center\" colspan=\"3\" >TOTAL</td>");
                        DecimalFormat formateador = new DecimalFormat("###,###.##");
                        out.println("<td class=\"text-right\" colspan=\"2\" >$"+formateador.format(count)+"</td>");
                        out.println("<td class=\"text-center\" colspan=\"2\" >"
                                + "<button type=\"submit\" class=\"btn btn-success btn-fill\" id=\"buttonsubmit\">\n" +
"                                                    Liquidar\n" +
"                                                </button>"
                                + "</td>");
                    out.println("</tr>");
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
            Logger.getLogger(FormPagoLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FormPagoLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
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
