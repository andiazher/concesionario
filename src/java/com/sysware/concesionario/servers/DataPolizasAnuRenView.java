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
public class DataPolizasAnuRenView extends HttpServlet {

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
                ArrayList<Entitie> polizas= new ArrayList<>();
                String identificacion="";
                
                try{
                    identificacion= request.getParameter("identificacion");
                    
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                Entitie servicio = new Entitie(App.TABLE_DOS);
                name= "POR FAVOR INGRESAR IDENTIFICACIÓN DEL CLIENTE";
                
                
                
                if(!identificacion.equals("")){
                    name= "RESULTADOS PARA CLIENTE: </b>"+identificacion +"<b>";
                    //CONSULTA DE LAS DIFERENTES TABLAS DE POLIZAS PARA SABER SI EL CLIENTE TIENE UNA POLIZA
                    Entitie cliente = new Entitie(App.TABLE_CLIENTE);
                    
                    //1. CONSULTAR TABLA DE REGISTRO SOAT
                    try{
                        cliente=cliente.getEntitieParam("CEDULA", identificacion).get(0);
                        Entitie rsoat = new Entitie(App.TABLE_REGISTROSOAT);
                        ArrayList<String> params= new ArrayList<>();
                        ArrayList<String> param2= new ArrayList<>();
                        params.add("CLIENTE");
                        params.add("ESTADOP");
                        param2.add(cliente.getId());
                        param2.add("VIGENTE");
                        ArrayList<Entitie> registros = rsoat.getEntitieParams(params, param2);
                        
                        for(Entitie re: registros){
                            Entitie e1 = new Entitie(); //THIS ENTITIE IS TEMPORAL
                            e1.setId(re.getDataOfLabel("POLIZA"));
                            addColumns(e1);
                            //POLIZA, FECHA, CLIENTE, SERVICIO, VALOR
                            e1.getData().add(re.getDataOfLabel("POLIZA"));
                            e1.getData().add(re.getDataOfLabel("FECHAR"));
                            e1.getData().add(cliente.getDataOfLabel("TIPODOC")+identificacion);
                            Entitie serv = new Entitie(App.TABLE_SERVICIOS);
                            servicio.getEntitieID(re.getDataOfLabel("DOSID"));
                            serv.getEntitieID(servicio.getDataOfLabel("SERVICIO"));
                            Entitie ve= new Entitie(App.TABLE_VEHICULO);
                            ve.getEntitieID(re.getDataOfLabel("VEHICULO"));
                            e1.getData().add(serv.getDataOfLabel("DESCRIPCION") +" - "+ve.getDataOfLabel("PLACA"));
                            e1.getData().add(re.getDataOfLabel("VALOR"));
                            e1.getData().add("1"); //TIPO DE POLIZA =1
                            polizas.add(e1);
                        }
                        
                    }catch(IndexOutOfBoundsException s){
                        System.out.println("Error: No hay registros en Registro SOAT: " +s);
                    }
                    //2. CONSULTAR TABLA DE ASISTENCIA DELTAL
                    try{
                        cliente=cliente.getEntitieParam("CEDULA", identificacion).get(0);
                        Entitie asistenciaDental = new Entitie(App.TABLE_ASIS_DENTAL);
                        ArrayList<String> params= new ArrayList<>();
                        ArrayList<String> param2= new ArrayList<>();
                        params.add("CLIENTE");
                        params.add("ESTADOPOL");
                        param2.add(cliente.getId());
                        param2.add("VIGENTE");
                        ArrayList<Entitie> registros = asistenciaDental.getEntitieParams(params,param2);
                        for(Entitie re: registros){
                            Entitie e1 = new Entitie();
                            e1.setId(re.getDataOfLabel("POLIZA"));
                            addColumns(e1);
                            //POLIZA, FECHA, CLIENTE, SERVICIO, VALOR
                            e1.getData().add(re.getDataOfLabel("POLIZA"));
                            e1.getData().add(re.getDataOfLabel("FECHAEXP"));
                            e1.getData().add(cliente.getDataOfLabel("TIPODOC")+identificacion);
                            e1.getData().add("ASISTENCIA DENTAL PROM");
                            //e1.getData().add(asistenciaDental.getDataOfLabel("VALOR"));
                            e1.getData().add("0");
                            e1.getData().add("2");//TIPO DE POLIZA =2
                            polizas.add(e1);
                        }
                        
                    }
                    catch(IndexOutOfBoundsException s){
                        System.out.println("Error: No hay registros en ASISTENCIASDEN: " +s);
                    }
                    
                }
                
                try (PrintWriter out = response.getWriter()) {
                    String idOrder="-1";
                    out.println("<h4 class=\"card-title text-center\" id=\"titleContend\"> <b> "+name+" </b> </h4>");
                    
                    int count=0;
                    
                    if(polizas.isEmpty()){
                        out.println("<h4>No hay resultados para ese cliente</h4>");
                    }
                    else{
                        out.println("<table class=\"table\">");
                        out.println("<thead class=\"\">\n" +
    "                                                <th>Poliza</th>\n" +
    "                                                <th>Fecha</th>\n" +
    "                                                <th>Identificación</th>\n" +
    "                                                <th>Servicio</th>\n" +
    "                                                <th>Valor</th>\n" +
    "                                                <th>A</th>\n" +
    "                                                <th>R</th>\n" +
    "                                            </thead>");
                        out.println("<tbody>");
                        for(Entitie i: polizas){
                            if(!idOrder.equals(i.getId())){
                                out.println("<tr >");
                                //POLIZA, FECHA, CLIENTE, SERVICIO, VALOR
                                out.println("<td>"+i.getDataOfLabel("POLIZA")+"</td>");
                                out.println("<td>"+i.getDataOfLabel("FECHA")+"</td>");
                                out.println("<td>"+i.getDataOfLabel("CLIENTE")+"</td>");
                                out.println("<td>"+i.getDataOfLabel("SERVICIO")+"</td>");
                                
                                DecimalFormat formateador = new DecimalFormat("###,###.##");
                                int valors= Integer.parseInt(i.getDataOfLabel("VALOR"));
                                out.println("<td class=\"text-right\">$"+formateador.format(valors)+"</td>");
                                out.println("<td><a href=\"#AnularPOL="+i.getId()+"&TIPE="+i.getDataOfLabel("TIPO")
                                        +"\" onclick=\"anular('"+i.getId()+"','"
                                        +i.getDataOfLabel("TIPO")+"')\">Anular</a></td>");
                                out.println("<td><a href=\"#renovarPOL="+i.getId()+"&TIPE="+i.getDataOfLabel("TIPO")
                                        +"\" onclick=\"renovar('"+i.getId()+"','"
                                        +i.getDataOfLabel("TIPO")+"')\">Renovar</a></td>");
                                out.println("</tr>");
                            }

                        }
                    }
                    
                    out.println("</tbody>");
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
            Logger.getLogger(DataPolizasAnuRenView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DataPolizasAnuRenView.class.getName()).log(Level.SEVERE, null, ex);
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

    private void addColumns(Entitie e1) {
        e1.getColums().add("POLIZA");
        e1.getColums().add("FECHA");
        e1.getColums().add("CLIENTE");
        e1.getColums().add("SERVICIO");
        e1.getColums().add("VALOR");
        e1.getColums().add("TIPO");
    }

}
