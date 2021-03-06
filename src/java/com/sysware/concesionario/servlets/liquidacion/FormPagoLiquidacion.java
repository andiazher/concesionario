/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.liquidacion;

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
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            if(request.getSession().getAttribute("session").equals("true")){
                String name;
                ArrayList<Entitie> liquidaciones;
                String fi="";
                String ff="";
                String concesionario="";
                try{
                    fi= request.getParameter("fi");
                    ff= request.getParameter("ff");
                    concesionario= request.getParameter("concesionario");
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                Entitie liquidacion = new Entitie(App.TABLE_LIQUIDACION);
                name= "RESULTADOS PARA LIQUIDACIONES POR PAGAR";
                boolean nada;
                ArrayList<String> param1=new ArrayList<>();
                ArrayList<String> param2=new ArrayList<>();
                ArrayList<String> operation=new ArrayList<>();
                param1.add("ESTADO");
                param2.add("PPAGAR");
                operation.add("=");
                nada=true;
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
                String qry="";
                String tables ="";
                if(!concesionario.equals("")){
                    if(nada){
                        name+=" Y";
                    }
                    Entitie conce = new Entitie(App.TABLE_CONCESIONARIO);
                    try{
                        conce.getEntitieID(concesionario);
                    }
                    catch(IndexOutOfBoundsException s){}
                    name+=" CONCESIONARIO: </b>"+conce.getDataOfLabel("NOMBRE")+"<b> ";
                    param1.add("CONCESIONARIO");
                    param2.add(concesionario );
                    operation.add("=");                    
                    nada=true;
                }
                if(param1.isEmpty() && param2.isEmpty() && operation.isEmpty() && nada==false){
                    liquidaciones = liquidacion.getEntities();
                    name="TODAS LAS ORDENES DE SERVICIO";
                }
                else{
                    liquidaciones = liquidacion.getEntitieParams(param1, param2, operation, qry, tables);
                }
                
                try (PrintWriter out = response.getWriter()) {
                    String idOrder="-1";
                    out.println("<h4 class=\"card-title text-center\" id=\"titleContend\"> <b> "+name+" </b> </h4>");
                    out.println("<table class=\"table\">");
                    out.println("<thead class=\"\">\n" +
"                                                <th>Liq.</th>\n" +
"                                                <th>Fecha</th>\n" +
"                                                <th>Concesionario</th>\n" +
"                                                <th>Valor</th>\n" +
"                                                <th>P</th>\n" +
"                                                <th>A</th>\n" +
"                                            </thead>");
                    out.println("<tbody>");
                    int count=0;
                    for(Entitie i: liquidaciones){
                        if(!idOrder.equals(i.getId())){
                            String a="";
                            a+="onclick=\"openViewOrderService("+i.getId()+")\"";
                            String danger = "text-danger";
                            danger = "";
                            out.println("<tr class=\""+danger+"\" >");
                            out.println("<td>"+i.getId()+"</td>");
                            out.println("<td>"+i.getDataOfLabel("FECHA")+"</td>");
                            Entitie conce = new Entitie(App.TABLE_CONCESIONARIO);
                            conce.getEntitieID(i.getDataOfLabel("CONCESIONARIO"));
                            out.println("<td>"+conce.getDataOfLabel("NOMBRE")+"</td>");
                            DecimalFormat formateador = new DecimalFormat("###,###.##");
                            int valors= Integer.parseInt(i.getDataOfLabel("VALOR"));
                            count+=valors;
                            out.println("<td class=\"text-right\">$"+formateador.format(valors)+"</td>");
                            out.println("<td><a href=\"#pagar="+i.getId()+"\" onclick=\"pagar("+i.getId()+")\">Pagar</a></td>");
                            out.println("<td><a href=\"#anular="+i.getId()+"\" onclick=\"anular("+i.getId()+")\">Anular</a></td>");
                            out.println("</tr>");
                        }
                        
                    }
                    out.println("</tbody>");
                    out.println("<tr class=\"\" >");
                        out.println("<td class=\"text-center\" colspan=\"2\" >TOTAL</td>");
                        DecimalFormat formateador = new DecimalFormat("###,###.##");
                        out.println("<td class=\"text-right\" colspan=\"2\" >$"+formateador.format(count)+"</td>");
                        out.println("<td class=\"text-center\" colspan=\"2\" >"
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
