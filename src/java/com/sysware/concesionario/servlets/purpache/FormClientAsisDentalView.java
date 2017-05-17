/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.purpache;

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
public class FormClientAsisDentalView extends HttpServlet {

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
                String param = "";
                try{
                    param = request.getParameter("param");
                }catch (NullPointerException s){
                    System.out.println("Eror in param, "+s);
                }
                if(param.equals("asistenciasclass")){
                    Entitie asistencia = new Entitie(App.TABLE_PARAMETROSFORMS);
                    ArrayList<Entitie> asistencias = asistencia.getEntitieParam("FORM", "ASISDENTAL");
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie e : asistencias){
                            out.println("<option value=\""+e.getId()+"\">"+e.getDataOfLabel("VALUE")+"</option>");
                        }
                    }
                }
                if(param.equals("idservice")){
                    String id= "";
                    Entitie dos = new Entitie(App.TABLE_DOS);
                    Entitie os = new Entitie(App.TABLE_OS);
                    Entitie client = new Entitie(App.TABLE_CLIENTE);
                    try{
                        id= request.getParameter("id");
                        dos.getEntitieID(id);
                        os.getEntitieID(dos.getDataOfLabel("OS"));
                        client.getEntitieID(os.getDataOfLabel("PROPIETARIO"));
                        try (PrintWriter out = response.getWriter()) {
                            //RETURN THE CEDULA O IDENTIFICATION NUMBER OF CLIENT
                            out.println(client.getDataOfLabel("CEDULA"));
                        }
                        
                    }catch(NullPointerException s){}
                }
                if(param.equals("client")){
                    Entitie p = new Entitie(App.TABLE_CLIENTE);
                    for(String s: p.getColums()){
                        p.getData().add("");
                    }
                    try{
                        p = p.getEntitieParam("CEDULA", request.getParameter("cliente")).get(0);
                    }catch(NullPointerException s){
                        System.out.println("Error: "+s);
                    }catch(IndexOutOfBoundsException s){
                        //System.out.println("No ha sido posible cargar el Cliente "+s);
                    }

                    try (PrintWriter out = response.getWriter()) {

                        out.println("{");

                        out.println("\"cedula\": \""+p.getDataOfLabel("CEDULA")+"\",");
                        out.println("\"nombre\": \""+p.getDataOfLabel("NOMBRE")+"\",");
                        out.println("\"snombre\": \""+p.getDataOfLabel("SNOMBRE")+"\",");
                        out.println("\"apellido\": \""+p.getDataOfLabel("APELLIDO")+"\",");
                        out.println("\"sapellido\": \""+p.getDataOfLabel("SAPELLIDO")+"\",");
                        out.println("\"fnacimiento\": \""+p.getDataOfLabel("FNACIMIENTO")+"\",");
                        out.println("\"direccion\": \""+p.getDataOfLabel("DIRECCION")+"\",");
                        out.println("\"telefono\": \""+p.getDataOfLabel("TELEFONO")+"\",");
                        out.println("\"celular\": \""+p.getDataOfLabel("CELULAR")+"\",");
                        out.println("\"correo\": \""+p.getDataOfLabel("CORREO")+"\",");
                        out.println("\"andiazher\": \"andiazher.com\"");
                        out.println("}");
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
            Logger.getLogger(FormClientAsisDentalView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FormClientAsisDentalView.class.getName()).log(Level.SEVERE, null, ex);
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
