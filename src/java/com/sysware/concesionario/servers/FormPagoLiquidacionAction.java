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
public class FormPagoLiquidacionAction extends HttpServlet {

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
                String method= "";
                try{
                   method= request.getParameter("action"); 
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                if(method.equals("true")){
                    String id="0";
                    String fecha="";
                    String formaPago="";
                    String observacion="";
                    try{
                        id= request.getParameter("id");
                        fecha= request.getParameter("fecha3");
                        formaPago= request.getParameter("fpago");
                        observacion= request.getParameter("observacion");
                    }catch(NullPointerException s){
                        System.out.println("Error: "+s);
                    }
                    Entitie rpago = new Entitie(App.TABLE_REGISTROPAGO);
                    for(String s: rpago.getColums()){
                        rpago.getData().add("");
                    }
                    rpago.getData().set(rpago.getColums().indexOf("FECHA"), fecha);
                    rpago.getData().set(rpago.getColums().indexOf("LIQUIDACION"), id);
                    rpago.getData().set(rpago.getColums().indexOf("FOMAP"), formaPago);
                    Entitie liquidacion = new Entitie(App.TABLE_LIQUIDACION);
                    liquidacion.getEntitieID(id);
                    String valor = liquidacion.getDataOfLabel("VALOR");
                    rpago.getData().set(rpago.getColums().indexOf("VALOR"), valor);
                    rpago.getData().set(rpago.getColums().indexOf("OBSERVACIONES"), observacion);
                    if(rpago.create()){
                        liquidacion.getData().set(
                                liquidacion.getColums().indexOf("ESTADO"), 
                                "PAGADA"
                        );
                        liquidacion.getData().set(
                                liquidacion.getColums().indexOf("OBSERVACIONES"), 
                                observacion
                        );
                        liquidacion.update();
                        try (PrintWriter out = response.getWriter()) {
                            out.println(id);
                        }
                    }
                    else{
                        try (PrintWriter out = response.getWriter()) {
                            out.println("ERROR: [NO PAGADO]");
                        }
                    }
                }
                if(method.equals("loadData")){
                    String id="";
                    try{
                        id= request.getParameter("entitie");
                    }catch(NullPointerException s){
                        System.out.println("Error: "+s);
                    }
                    Entitie liquidacion = new Entitie(App.TABLE_LIQUIDACION);
                    liquidacion.getEntitieID(id);
                    String valor = liquidacion.getDataOfLabel("VALOR");
                    try (PrintWriter out = response.getWriter()) {
                        out.println(valor);
                    }
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
            Logger.getLogger(FormPagoLiquidacionAction.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FormPagoLiquidacionAction.class.getName()).log(Level.SEVERE, null, ex);
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
