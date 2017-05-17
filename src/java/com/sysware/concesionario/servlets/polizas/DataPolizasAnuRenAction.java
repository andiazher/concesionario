/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.polizas;

import com.sysware.concesionario.app.App;
import com.sysware.concesionario.core.Mail;
import com.sysware.concesionario.entitie.Entitie;
import com.sysware.concesionario.services.WebServiceAsistenciaDen;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
public class DataPolizasAnuRenAction extends HttpServlet {

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
                String menu="0";
                String servicio="";
                String poliza= "";
                try{
                    menu=request.getParameter("menu");
                    servicio= request.getParameter("servicio");
                    poliza= request.getParameter("poliza");
                    System.out.println("Poliza En Proceso: "+poliza);
                }catch(IndexOutOfBoundsException s){
                    s.printStackTrace();
                }
                ///PROCESO DE ANULZACIÓN DE LAS POLIZAS
                if(menu.equals("anular")){
                   if(servicio.equals("1")){
                       Entitie rsoat = new Entitie(App.TABLE_REGISTROSOAT);
                       try{
                           rsoat = rsoat.getEntitieParam("POLIZA", poliza).get(0);
                           rsoat.getData().set(rsoat.getColums().indexOf("ESTADOP"),"ANULADA");
                           rsoat.update();
                       }catch(IndexOutOfBoundsException s){
                           s.printStackTrace();
                       }
                   }
                   if(servicio.equals("2")){
                       Entitie ad = new Entitie(App.TABLE_ASIS_DENTAL);
                       try{
                           ad = ad.getEntitieParam("POLIZA", poliza).get(0);
                           ad.getData().set(ad.getColums().indexOf("ESTADO"), "3");
                           ad.getData().set(ad.getColums().indexOf("ESTADOPOL"), "ANULADA");
                           ad.update();
                           //CONSUMO DEL WEB SERVICE
                           WebServiceAsistenciaDen wsad = new WebServiceAsistenciaDen();
                            //CANCELAR POLIZA ANTERIOR ASOCIADA AL CLIENTE
                           String message = wsad.excluir(poliza);
                           System.out.println("Respuesta del Servicio Cancelar: "+message);
                       }catch(IndexOutOfBoundsException s){
                           s.printStackTrace();
                       }
                   }
                }
                
                //PROCESO DE RENOVACION DE LAS POLIZAS
                if(menu.equals("renovar")){
                   if(servicio.equals("1")){
                       Entitie registro = new Entitie(App.TABLE_REGISTROSOAT);
                       try{
                           registro = registro.getEntitieParam("POLIZA", poliza).get(0);
                           registro.getData().set(registro.getColums().indexOf("ESTADOP"),"ANULADA");
                           registro.update();
                           String npoliza = request.getParameter("npoliza").toUpperCase();
                           Calendar fecha = new GregorianCalendar();
                           String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH);
                           registro.getData().set(registro.getColums().indexOf("FECHAR"), f);
                           registro.getData().set(registro.getColums().indexOf("POLIZA"), npoliza);
                           registro.getData().set(registro.getColums().indexOf("ESTADOP"), "VIGENTE");
                           registro.create();
                           
                           try (PrintWriter out = response.getWriter()) {
                               out.println("<script type=\"text/javascript\">\n"
                                            + "swal(\n" +
                                        "'Poliza Renovada!',\n" +
                                        "'Nuevo número de Poliza "+npoliza+" registrado',\n" +
                                        "'success'\n" +
                                        ")\n"
                                        + "</script>");
                           }
                       }catch(IndexOutOfBoundsException s){
                           s.printStackTrace();
                       }catch(NullPointerException s){
                           s.printStackTrace();
                       }
                   }
                   if(servicio.equals("2")){
                       Entitie ad = new Entitie(App.TABLE_ASIS_DENTAL);
                       try{
                            ad = ad.getEntitieParam("POLIZA", poliza).get(0);
                            //ad.getData().set(ad.getColums().indexOf("ESTADO"), "3");
                            //ad.getData().set(ad.getColums().indexOf("ESTADOPOL"), "ANULADA");
                            //ad.update(); //ACTUALIZAR LA POLZIA CANCELADA
                            int ram=(int) (Math.random()*100000);
                            ad.getData().set(ad.getColums().indexOf("POLIZA"), "*NV*"+ram);
                            ad.create(); //CREAR LA NUEVA POLIZA
                            ArrayList<Entitie> asds= ad.getEntitieParam("CLIENTE", ad.getDataOfLabel("CLIENTE"));
                            String idPoliza="";
                            for(Entitie e: asds){
                                idPoliza = e.getId();
                                ad = e;
                            }
                            int last = Integer.parseInt(idPoliza);
                            if(last<10){
                                idPoliza="000000"+idPoliza;
                            }
                            if(last>=10 && last<100){
                                idPoliza="00000"+idPoliza;
                            }
                            if(last>=100 && last<1000){
                                idPoliza="0000"+idPoliza;
                            }
                            if(last>=1000 && last<10000){
                                idPoliza="000"+idPoliza;
                            }
                            if(last>=10000 && last<100000){
                                idPoliza="00"+idPoliza;
                            }
                            if(last>=100000 && last<1000000){
                                idPoliza="0"+idPoliza;
                            }
                            Calendar fecha = new GregorianCalendar();
                            String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH);
                            ad.getData().set(ad.getColums().indexOf("FECHA"),f );
                            ad.getData().set(ad.getColums().indexOf("FECHAEXP"), f);
                            ad.getData().set(ad.getColums().indexOf("POLIZA"), "AD"+idPoliza);
                            
                            WebServiceAsistenciaDen wsad = new WebServiceAsistenciaDen();
                            //CANCELAR POLIZA ANTERIOR ASOCIADA AL CLIENTE
                            String message = wsad.excluir(poliza);
                            System.out.println("Respuesta del Servicio Cancelar: "+message);

                            //REGISTRO DE NUEVA POLIZA CON EL LOS DATOS DEL CLIENTE
                            Entitie cliente = new Entitie(App.TABLE_CLIENTE);
                            cliente.getEntitieID(ad.getDataOfLabel("CLIENTE"));
                            String messagge = wsad.registro(ad, cliente);
                            System.out.println("Respuesta del Servicio Crear: "+messagge);
                            
                            if(messagge.equals("1")){
                                
                                ad.getData().set(ad.getColums().indexOf("ESTADO"), "2");
                                ad.getData().set(ad.getColums().indexOf("ESTADOPOL"), "VIGENTE");
                                ad.update();
                                //SEND MAIL 
                                try{
                                    ad = ad.getEntitieParam("POLIZA", poliza).get(0);
                                    ad.getData().set(ad.getColums().indexOf("ESTADO"), "3");
                                    ad.getData().set(ad.getColums().indexOf("ESTADOPOL"), "ANULADA");
                                    ad.update(); //ACTUALIZAR LA POLIZA CANCELADA
                                }catch(IndexOutOfBoundsException s){
                                    s.printStackTrace();
                                }
                                Mail mail = new Mail();
                                String mc= cliente.getDataOfLabel("CORREO");
                                mc = mc.toLowerCase();
                                mc = mc.trim();
                                boolean enviado = false;
                                if(!mc.isEmpty()  && !mc.equals("") && !mc.equals(" ") && mc.contains("@") && mc.contains(".") && mc!= null){
                                    mail.setRecipient(mc);
                                    mail.setSubject("NUEVA POLIZA ASISTENCIA DENTAL AD"+idPoliza);
                                    String cadena= cliente.getDataOfLabel("NOMBRE");
                                    String nameClient = cadena.substring(0,1).toUpperCase() + cadena.substring(1).toLowerCase();
                                    mail.setContend("<p>Hola "+nameClient+",</p><br>"
                                            +"<p>Su número de poliza actual <b>"+poliza+"</b> ha sido reemplazado por"
                                                    + " la poliza de asistencia dental <b>AD"+idPoliza+"</b></p>"
                                            +"<p>Muchas Gracias por utilizar nuestros servicios</p> <br>"
                                            +"<p><b>Platinos Seguros</b></p>"
                                            + "<a href=\"sysware-ingenieria.com\">www.platinoseguros.com.co</a>");
                                    enviado = mail.send();
                                    System.out.println("Enviar a: "+mc + " Confirm:"+enviado);
                                }
                                else{
                                    System.out.println("No enviado");
                                }
                                try (PrintWriter out = response.getWriter()) {
                                    String msg="";
                                    if(enviado){
                                        msg= "Se le ha enviado una copia de la poliza al correo registrado";
                                    }
                                    else{
                                        msg= "Por favor Guardar este numero de poliza.";
                                    }

                                    out.println("<script type=\"text/javascript\">\n"
                                            + "swal(\n" +
                                        "'Poliza Renovada!',\n" +
                                        "'Nuevo número de Poliza AD"+idPoliza+"; "+msg+"',\n" +
                                        "'success'\n" +
                                        ")\n"
                                        + "</script>");
                                }
                                
                            }
                            else{
                                try (PrintWriter out = response.getWriter()) {
                                    out.println("<script type=\"text/javascript\">\n"
                                            +" swal(\n" +
                                        "'Error al renovar!',\n" +
                                        "'La Poliza "+poliza+" no ha se ha renovado. Razón: "+messagge+" ',\n" +
                                        "'error'\n" +
                                        ")\n"
                                        + "</script>");
                                }
                                //PASO DEL SERVICIO A NO TRAMITADO.
                                ad.getData().set(ad.getColums().indexOf("ESTADO"), "3");
                                ad.getData().set(ad.getColums().indexOf("ESTADOPOL"), "ANULADA");
                                ad.update();
                            }
                            
                            
                       }catch(IndexOutOfBoundsException s){
                           s.printStackTrace();
                       }
                   }
                }
                
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }catch(NullPointerException e){
            response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            e.printStackTrace();
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
            Logger.getLogger(DataPolizasAnuRenAction.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DataPolizasAnuRenAction.class.getName()).log(Level.SEVERE, null, ex);
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
