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
import java.sql.Date;
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
public class FormClientAction extends HttpServlet {

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
                Enumeration<String> a= request.getParameterNames();
                //1. TIPO DE INDETIFICACION
                String ti = a.nextElement();
                ti= request.getParameter(ti);
                //2. NUMERO DE IDENTIFICACION
                String identificaction = a.nextElement();
                identificaction = request.getParameter(identificaction);
                
                Entitie propietario = new Entitie(App.TABLE_CLIENTE);
                ArrayList<Entitie> res= new ArrayList<>();
                try{
                    res=propietario.getEntitieParam("CEDULA", identificaction );
                    if(!res.isEmpty()){
                        propietario= res.get(0);
                    }
                    else{
                        for(int i=0; i<propietario.getColums().size(); i++){
                            propietario.getData().add("");
                        }
                        int s= propietario.getColums().indexOf("CEDULA");
                        propietario.getData().set(s, identificaction);
                        propietario.getData().set(propietario.getColums().indexOf("TIPODOC"), ti);
                        propietario.getData().set(propietario.getColums().indexOf("FNACIMIENTO"), "0000-00-00");
                        propietario.getData().set(propietario.getColums().indexOf("CIUDAD"), "0");
                        propietario.getData().set(propietario.getColums().indexOf("PAIS"), "0);
                        propietario.create();
                        propietario = propietario.getEntitieParam("CEDULA", identificaction).get(0);
                    }
                    
                }catch(IndexOutOfBoundsException w){
                    System.out.println("Error: Propietario "+w);
                }
                //3. PLACA DE VEHICULO
                String placa= a.nextElement();
                placa= request.getParameter(placa);
                placa = placa.toUpperCase();
                //4. MODELO DE VEHICULO
                String modelo= a.nextElement();
                modelo = request.getParameter(modelo);
                //5.CILINDRAJE DE VEHICULO
                String cilindraje = a.nextElement();
                cilindraje = request.getParameter(cilindraje);
                //6. TIPO DE VEHICULO
                String tipo = a.nextElement();
                tipo = request.getParameter(tipo);
                //7.CLASE DE VEHICULO
                String clase = a.nextElement();
                clase = request.getParameter(clase);
                //8.MARCA DEL VEHICULO
                String marca = a.nextElement();
                marca = request.getParameter(marca);
                
                String usuario =(String) request.getSession().getAttribute("user");
                Entitie user = new Entitie(App.TABLE_USUARIO);
                user=user.getEntitieParam("USUARIO", usuario).get(0);
                Entitie canal = new Entitie(App.TABLE_CANALES);
                canal.getEntitieID(user.getDataOfLabel("ID_CANAL"));
                
                Entitie vehiculo = new Entitie(App.TABLE_VEHICULO);
                ArrayList<Entitie> resp;
                
                try{
                    resp = vehiculo.getEntitieParam("PLACA", placa);
                    if(!resp.isEmpty()){
                        vehiculo = resp.get(0);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("PROPIETARIO"), propietario.getId());
                        vehiculo.getData().set(vehiculo.getColums().indexOf("TIPO"), tipo);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("CLASE"), clase);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("MARCA"), marca);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("CILINDRAJE"), cilindraje);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("MODELO"), modelo);
                        vehiculo.update();
                    }
                    else{
                        for(String i: vehiculo.getColums()){
                            vehiculo.getData().add("");
                        }
                        vehiculo.getData().set(vehiculo.getColums().indexOf("PLACA"), placa);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("PROPIETARIO"), propietario.getId());
                        vehiculo.getData().set(vehiculo.getColums().indexOf("TIPO"), tipo);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("MARCA"), marca);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("CLASE"), clase);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("SERVICIO"), "1");
                        vehiculo.getData().set(vehiculo.getColums().indexOf("CILINDRAJE"), cilindraje);
                        vehiculo.getData().set(vehiculo.getColums().indexOf("MODELO"), modelo);
                        vehiculo.create();
                        vehiculo= vehiculo.getEntitieParam("PLACA", placa).get(0);
                    }
                }catch(IndexOutOfBoundsException w){
                    System.out.println("Error: Vehicle "+w);
                }
                Entitie ordenServicio = new Entitie(App.TABLE_ORDENSERVICIO);
                for(String i: ordenServicio.getColums()){
                    ordenServicio.getData().add("");
                }
                System.out.println(""+ordenServicio);
                ordenServicio.getData().set(ordenServicio.getColums().indexOf("PROPIETARIO"), propietario.getId());
                ordenServicio.getData().set(ordenServicio.getColums().indexOf("VEHICULO"), vehiculo.getId());
                ordenServicio.getData().set(ordenServicio.getColums().indexOf("ID_CANAL"), canal.getId());
                Calendar fecha = new GregorianCalendar();
                String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)+
                                    " "+fecha.get(Calendar.HOUR_OF_DAY)+":"+fecha.get(Calendar.MINUTE)+":"+fecha.get(Calendar.SECOND);
                ordenServicio.getData().set(ordenServicio.getColums().indexOf("FECHA"),f );
                
                ordenServicio.create();
                System.out.println(""+ordenServicio);
                
                ArrayList<Entitie> ordenes = ordenServicio.getEntities();
                String ultimo="";
                for(Entitie o: ordenes){
                    ultimo= o.getId();
                }
                
                while(a.hasMoreElements()){
                        String name= a.nextElement();
                        String valor="0";
                        Entitie servcio = new Entitie(App.TABLE_SERVICIOS);
                        servcio.getEntitieID(name);
                        valor = servcio.getDataOfLabel("VALOR");
                        Entitie osdetalle = new Entitie(App.TABLE_OSDETALLE);
                        for(String s: osdetalle.getColums()){
                            osdetalle.getData().add("");
                        }
                        osdetalle.getData().set(osdetalle.getColums().indexOf("OS"), ultimo);
                        osdetalle.getData().set(osdetalle.getColums().indexOf("SERVICIO"), name);
                        osdetalle.getData().set(osdetalle.getColums().indexOf("VALOR"), valor);
                        osdetalle.getData().set(osdetalle.getColums().indexOf("ESTADO"), "1");
                        osdetalle.getData().set(osdetalle.getColums().indexOf("COM_PLATINO"), "0");
                        osdetalle.getData().set(osdetalle.getColums().indexOf("COM_CONCE"), "0");
                        osdetalle.getData().set(osdetalle.getColums().indexOf("LIQUIDACION"), "0");
                        osdetalle.getData().set(osdetalle.getColums().indexOf("FECHAT"), f);
                        osdetalle.create();
                        System.out.println(name +" : "+ request.getParameter(name));
                }
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet formClientAction</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    
                    out.println("</body>");
                    out.println("</html>");
                }
                
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
            Logger.getLogger(FormClientAction.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FormClientAction.class.getName()).log(Level.SEVERE, null, ex);
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
