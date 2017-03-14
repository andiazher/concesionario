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
public class DataFormServiceView extends HttpServlet {

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
                Entitie orden = new Entitie(App.TABLE_ORDENSERVICIO);
                Entitie detalle = new Entitie(App.TABLE_OSDETALLE);
                Entitie v = new Entitie(App.TABLE_VEHICULO);
                Entitie p = new Entitie(App.TABLE_CLIENTE);
                                
                try{
                    detalle.getEntitieID(request.getParameter("variable"));
                    orden.getEntitieID(detalle.getDataOfLabel("OS"));
                    v.getEntitieID(orden.getDataOfLabel("VEHICULO"));
                    p.getEntitieID(orden.getDataOfLabel("PROPIETARIO"));
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                try (PrintWriter out = response.getWriter()) {
                    Entitie servicio = new Entitie(App.TABLE_SERVICIOS);
                    servicio.getEntitieID(detalle.getDataOfLabel("SERVICIO"));
                    Entitie tipo = new Entitie(App.TABLE_TIPOVEH);
                    tipo.getEntitieID(v.getDataOfLabel("TIPO"));
                    ArrayList<Entitie> tipos = tipo.getEntities();
                    Entitie clase = new Entitie(App.TABLE_CLASEVEHI);
                    clase.getEntitieID(v.getDataOfLabel("CLASE"));
                    ArrayList<Entitie> clases= clase.getEntitieParam("TIPO", tipo.getId());
                    Entitie marca = new Entitie(App.TABLE_MARCA);
                    marca.getEntitieID(v.getDataOfLabel("MARCA"));
                    ArrayList<Entitie> marcas = marca.getEntities();
                    Entitie servi = new Entitie(App.TABLE_SERVICIOVEHI);
                    servi.getEntitieID(v.getDataOfLabel("SERVICIO"));
                    ArrayList<Entitie> servivehs = servi.getEntities();
                    
                    Entitie canal = new Entitie(App.TABLE_CANALES);
                    canal.getEntitieID(orden.getDataOfLabel("ID_CANAL"));
                    Entitie concesionario = new Entitie(App.TABLE_CONCESIONARIO);
                    concesionario.getEntitieID(canal.getDataOfLabel("ID_CONCESIONARIO"));
                    
                    out.println("{");
                    out.println("\"idServicio\": \""+detalle.getDataOfLabel("SERVICIO")+"\",");
                    out.println("\"nombreservicio\": \""+servicio.getDataOfLabel("DESCRIPCION")+"\",");
                    out.println("\"placa\": \""+v.getDataOfLabel("PLACA")+"\",");
                    out.println("\"tipo\": {");
                    out.println("   \"option\": [");
                    
                    for(Entitie t : tipos){
                        if(t.getId().equals(tipo.getId())){
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\",\"selected\":\"selected\" },");
                        }
                        else{
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\"}, ");
                        }
                    }
                    out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                    out.println("       ]");
                    out.println("   },");
                    out.println("\"clase\": {");
                    out.println("   \"option\": [");
                    
                    for(Entitie t : clases){
                        if(t.getId().equals(clase.getId())){
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\",\"selected\":\"selected\" },");
                        }
                        else{
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\"}, ");
                        }
                    }
                    out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                    out.println("       ]");
                    out.println("   },");
                    out.println("\"marca\": {");
                    out.println("   \"option\": [");
                    
                    for(Entitie t : marcas){
                        if(t.getId().equals(marca.getId())){
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\",\"selected\":\"selected\" },");
                        }
                        else{
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\"}, ");
                        }
                    }
                    out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                    out.println("       ]");
                    out.println("   },");
                    out.println("\"modelo\": \""+v.getDataOfLabel("MODELO")+"\",");
                    out.println("\"cilindraje\": \""+v.getDataOfLabel("CILINDRAJE")+"\",");
                    out.println("\"color\": \""+v.getDataOfLabel("COLOR")+"\",");
                    out.println("\"servicio\": {");
                    out.println("   \"option\": [");
                    for(Entitie t: servivehs){
                        if(t.getId().equals(servi.getId())){
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("SERVICIO")+"\",\"selected\":\"selected\" },");
                        }
                        else{
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("SERVICIO")+"\"}, ");
                        }
                    }
                    out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                    out.println("       ]");
                    out.println("   },");
                    out.println("\"motor\": \""+v.getDataOfLabel("NO_MOTOR")+"\",");
                    out.println("\"chasis\": \""+v.getDataOfLabel("NO_CHASIS")+"\",");
                    out.println("\"vin\": \""+v.getDataOfLabel("NO_VIN")+"\",");
                    out.println("\"cedula\": \""+p.getDataOfLabel("CEDULA")+"\",");
                    out.println("\"nombre\": \""+p.getDataOfLabel("NOMBRE")+"\",");
                    out.println("\"apellido\": \""+p.getDataOfLabel("APELLIDO")+"\",");
                    out.println("\"direccion\": \""+p.getDataOfLabel("DIRECCION")+"\",");
                    out.println("\"celular\": \""+p.getDataOfLabel("CELULAR")+"\",");
                    out.println("\"correo\": \""+p.getDataOfLabel("CORREO")+"\",");
                    out.println("\"total\": \""+detalle.getDataOfLabel("VALOR")+"\",");
                    out.println("\"conce\": \""+concesionario.getDataOfLabel("NOMBRE")+"\",");
                    out.println("\"andiazher\": \"andiazher.com\"");
                    out.println("}");
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
            Logger.getLogger(DataFormServiceView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DataFormServiceView.class.getName()).log(Level.SEVERE, null, ex);
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
