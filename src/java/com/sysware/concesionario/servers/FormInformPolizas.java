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
public class FormInformPolizas extends HttpServlet {

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
                String name;
                ArrayList<Entitie> polizas = new ArrayList<>();
                String fi="";
                String ff="";
                String canal="";
                String pago="";
                try{
                    fi= request.getParameter("fi");
                    ff= request.getParameter("ff");
                    canal= request.getParameter("canal");
                    pago= request.getParameter("pago");
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                Entitie polizaAS = new Entitie(App.TABLE_ASIS_DENTAL); //POLIZAS DE ASISTENCIA DENTAL
                
                name= "POLIZAS";
                boolean nada = false;
                ArrayList<String> param1=new ArrayList<>();
                ArrayList<String> param2=new ArrayList<>();
                ArrayList<String> operation=new ArrayList<>();
                if(!fi.equals("")){
                    name+=" DESPUES DE: </b>"+fi+"<b>";
                    param1.add("FECHA");
                    param2.add(fi);
                    operation.add(">=");
                    nada=true;
                }
                if(!ff.equals("")){
                    if(nada){
                        name+=",";
                    }
                    name+=" ANTES DE: </b>"+ff+"<b> ";
                    param1.add("FECHA");
                    param2.add(ff +" 23:59:59");
                    operation.add("<=");
                    nada=true;
                }
                if(!canal.equals("")){
                    param1.add("CANAL");
                    param2.add(canal);
                    operation.add("=");
                    nada=true;
                }
                if(!pago.equals("")){
                    if(pago.equals("1")){
                        pago="PAGADO";
                        param1.add("ESTADOPAGO");
                        param2.add(pago);
                        operation.add("=");
                        nada=true;
                    }
                    if(pago.equals("2")){
                        pago="PORPAGAR";
                        param1.add("ESTADOPAGO");
                        param2.add(pago);
                        operation.add("=");
                        nada=true;
                    }
                    param1.add("ESTADOPOL");
                    param2.add("VIGENTE");
                    operation.add("=");
                }
                if(param1.isEmpty() && param2.isEmpty() && operation.isEmpty() && nada==false){
                    polizas = polizaAS.getEntities();
                    name="LISTADO DE TODAS LAS POLIZAS";
                }
                else{
                    polizas = polizaAS.getEntitieParams(param1, param2, operation);
                }
                
                try (PrintWriter out = response.getWriter()) {
                    String idOrder="-1";
                    out.println("<h4 class=\"card-title text-center\" id=\"titleContend\"> <b> "+name+" </b> </h4>");
                    out.println("<table class=\"table\">");
                    out.println("<thead class=\"\">\n" +
                        "<th>N. Poliza</th>\n" +
                        "<th>Fecha Exp</th>\n" +
                        "<th>Canal</th>\n" +
                        "<th>Cliente</th>\n" +
                        "<th>V.Prima</th>\n" +
                        "<th>V.Consingnar</th>\n" +
                        "<th>Dias</th>"+
                        "</thead>");
                    out.println("<tbody>");
                    int count=0;
                    for(Entitie i: polizas){
                        if(!idOrder.equals(i.getId())){
                            String a="";
                            a+="onclick=\"openViewOrderService("+i.getId()+")\"";
                            String danger = "text-danger";
                            danger = "";
                            out.println("<tr class=\""+danger+"\" >");
                            out.println("<td>"+i.getDataOfLabel("POLIZA")+"</td>");
                            out.println("<td>"+i.getDataOfLabel("FECHA")+"</td>");
                            Entitie conce = new Entitie(App.TABLE_CANALES);
                            conce.getEntitieID(i.getDataOfLabel("CANAL"));
                            out.println("<td>"+conce.getDataOfLabel("NOMBRE")+"</td>");
                            Entitie cliente = new Entitie(App.TABLE_CLIENTE);
                            cliente.getEntitieID(i.getDataOfLabel("CLIENTE"));
                            out.println("<td>"+cliente.getDataOfLabel("NOMBRE")+" "+cliente.getDataOfLabel("APELLIDO")+"</td>");
                            DecimalFormat formateador = new DecimalFormat("###,###.##");
                            int valors= Integer.parseInt(i.getDataOfLabel("VALORPRIMA"));
                            int valors2= Integer.parseInt(i.getDataOfLabel("PAGOCANAL"));
                            out.println("<td class=\"text-right\">$"+formateador.format(valors)+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(valors2)+"</td>");
                            out.println("<td class=\"text-center\">"+formateador.format(0)+"</td>");
                            out.println("</tr>");
                        }
                        
                    }
                    out.println("</tbody>");
                    /**
                    out.println("<tr class=\"\" >");
                        out.println("<td class=\"text-center\" colspan=\"2\" >TOTAL</td>");
                        DecimalFormat formateador = new DecimalFormat("###,###.##");
                        out.println("<td class=\"text-right\" colspan=\"2\" >$"+formateador.format(count)+"</td>");
                        out.println("<td class=\"text-center\" colspan=\"2\" >"
                                + "</td>");
                    out.println("</tr>");
                    */
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
            Logger.getLogger(FormInformPolizas.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FormInformPolizas.class.getName()).log(Level.SEVERE, null, ex);
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
