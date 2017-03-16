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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
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
public class FormClientAsitDentalAction extends HttpServlet {

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
                String ti = null;
                String identifica = null;
                String nombre = null;
                String apellido = null;
                String fechaNaci = null;
                String plan = null;
                String direccion = null;
                String telefono = null;
                String celular = null;
                String correo = null;
                try{
                    ti= request.getParameter("ti");
                    identifica= request.getParameter("identification");
                    nombre= request.getParameter("nombres");
                    apellido= request.getParameter("apellidos");
                    fechaNaci= request.getParameter("fecha");
                    plan= request.getParameter("plan");
                    direccion= request.getParameter("direccion");
                    telefono= request.getParameter("telefono");
                    celular= request.getParameter("celular");
                    correo= request.getParameter("correo");
                    
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                
                Entitie cliente = new Entitie(App.TABLE_CLIENTE);
                ArrayList<Entitie> clientes= cliente.getEntitieParam("CEDULA", identifica);
                if(clientes.isEmpty()){
                    cliente = new Entitie(App.TABLE_CLIENTE);
                    for(String s: cliente.getColums()){
                        cliente.getData().add("");
                    }
                    cliente.getData().set(cliente.getColums().indexOf("CEDULA"), identifica);
                    cliente.getData().set(cliente.getColums().indexOf("TIPODOC"), ti);
                    cliente.getData().set(cliente.getColums().indexOf("NOMBRE"), nombre);
                    cliente.getData().set(cliente.getColums().indexOf("APELLIDO"), apellido);
                    cliente.getData().set(cliente.getColums().indexOf("DIRECCION"), direccion);
                    cliente.getData().set(cliente.getColums().indexOf("TELEFONO"), telefono);
                    cliente.getData().set(cliente.getColums().indexOf("CELULAR"), celular);
                    cliente.getData().set(cliente.getColums().indexOf("CORREO"), correo);
                    cliente.getData().set(cliente.getColums().indexOf("FNACIMIENTO"), fechaNaci);
                    cliente.getData().set(cliente.getColums().indexOf("CIUDAD"), "0");
                    cliente.getData().set(cliente.getColums().indexOf("PAIS"), "0");
                    cliente.create();
                }
                clientes= cliente.getEntitieParam("CEDULA", identifica);
                String idCliente="";
                for(Entitie e: clientes){
                    idCliente = e.getId();
                }
                
                Entitie asd = creteEntidadASD();
                ArrayList<Entitie> asds= asd.getEntities();
                String id="";
                for(Entitie e: asds){
                    id = e.getId();
                }
                
                int last = Integer.parseInt(id);
                if(last<10){
                    id="000000"+id;
                }
                if(last>=10 && last<100){
                    id="00000"+id;
                }
                if(last>=100 && last<1000){
                    id="0000"+id;
                }
                if(last>=1000 && last<10000){
                    id="000"+id;
                }
                if(last>=10000 && last<100000){
                    id="00"+id;
                }
                if(last>=100000 && last<1000000){
                    id="0"+id;
                }
                try (PrintWriter out = response.getWriter()) {
                    out.println("ASD"+id);
                }
                asd.getEntitieID(last+"");
                Calendar fecha = new GregorianCalendar();
                String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH);
                asd.getData().set(asd.getColums().indexOf("FECHA"),f );
                asd.getData().set(asd.getColums().indexOf("FECHAEXP"), f);
                asd.getData().set(asd.getColums().indexOf("POLIZA"), "ASD"+id);
                asd.getData().set(asd.getColums().indexOf("PLAN"), plan);
                asd.getData().set(asd.getColums().indexOf("ESTADO"), "2");
                asd.getData().set(asd.getColums().indexOf("CLIENTE"), idCliente);
                asd.update();
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }catch(NullPointerException e){
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
            Logger.getLogger(FormClientAsitDentalAction.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FormClientAsitDentalAction.class.getName()).log(Level.SEVERE, null, ex);
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

    public Entitie creteEntidadASD() throws SQLException {
        Entitie asd = new Entitie(App.TABLE_ASIS_DENTAL);
        for(String s: asd.getColums()){
            asd.getData().add("0");
        }
        asd.getData().set(asd.getColums().indexOf("FECHA"),"0000-00-00");
        asd.getData().set(asd.getColums().indexOf("FECHAEXP"),"0000-00-00");
        asd.create();
        return asd;
    }

}
