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
public class TodosServiciosInform extends HttpServlet {

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
                //String placa="";
                //String cliente="";
                String concesionario="";
                String os="";
                try{
                    fi= request.getParameter("fi");
                    ff= request.getParameter("ff");
                    //placa= request.getParameter("placa");
                    //cliente= request.getParameter("cliente");
                    concesionario= request.getParameter("concesionario");
                    os= request.getParameter("os");
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                Entitie servicio = new Entitie(App.TABLE_OS);
                name= "RESULTADOS PARA";
                boolean nada=false;
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
                    param2.add(ff);
                    operation.add("<=");
                    nada=true;
                }
                /**
                if(!placa.equals("")){
                    if(nada){
                        name+=",";
                    }
                    name+=" PLACA: </b>"+placa+"<b> ";
                    Entitie vehi = new Entitie(App.TABLE_VEHICULO);
                    try{
                        vehi= vehi.getEntitieParam("PLACA", placa).get(0);
                    }
                    catch(IndexOutOfBoundsException s){}
                    param1.add("VEHICULO");
                    param2.add(vehi.getId());
                    operation.add("=");
                    nada=true;
                }
                if(!cliente.equals("")){
                    if(nada){
                        name+=",";
                    }
                    name+=" CLIENTE: </b>"+cliente+"<b> ";
                    Entitie client = new Entitie(App.TABLE_PROPIETARIO);
                    try{
                        client= client.getEntitieParam("CEDULA", cliente).get(0);
                    }
                    catch(IndexOutOfBoundsException s){}
                    param1.add("PROPIETARIO");
                    param2.add(client.getId());
                    operation.add("=");
                    nada=true;
                }
                */
                if(!os.equals("")){
                    if(nada){
                        name+=",";
                    }
                    param1.add("ID");
                    param2.add(os);
                    operation.add("=");
                    name+=" OS: </b>"+os+"<b> ";
                    nada=true;
                }
                String qry="";
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
                    Entitie canal= new Entitie(App.TABLE_CANALES);
                    ArrayList<Entitie> canales =canal.getEntitieParam("ID_CONCESIONARIO", concesionario);
                    if(!canales.isEmpty()){
                        if(nada){
                            qry=" and ";
                        }
                    }
                    else{
                        if(nada){
                            qry=" and ID=0";
                        }
                        else{
                            qry=" ID=0";
                        }
                        
                    }
                    
                    for(int i=0; i<canales.size(); i++){
                        if(canales.size()==1){
                            qry+=" ID_CANAL='"+canales.get(i).getId()+"' ";
                        }
                        else{
                            if(i==0){
                                qry+="( ID_CANAL='"+canales.get(i).getId()+"' ";
                            }
                            if(i==canales.size()-1){
                                qry+=" OR ID_CANAL='"+canales.get(i).getId()+"' )";
                            }
                            else{
                                qry+=" OR ID_CANAL='"+canales.get(i).getId()+"' ";
                            }
                        }   
                    }
                    nada=true;
                }
                
                if(param1.isEmpty() && param2.isEmpty() && operation.isEmpty() && nada==false){
                    servicios = servicio.getEntities();
                    name="TODAS LAS ORDENES DE SERVICIO";
                }
                else{
                    servicios = servicio.getEntitieParams(param1, param2, operation, qry, "");
                }
                
                try (PrintWriter out = response.getWriter()) {
                    String idOrder="-1";
                    out.println("<h4 class=\"card-title text-center\" id=\"titleContend\"> <b> "+name+" </b> </h4>");
                    out.println("<table class=\"table\">");
                    out.println("<thead class=\"\">\n" +
"                                                <th>No</th>\n" +
"                                                <th>Fecha</th>\n" +
"                                                <th>Documento</th>\n" +
"                                                <th>Placa</th>\n" +
"                                                <th>Servicios</th>\n" +
"                                                <th>Canal</th>\n" +
"                                            </thead>");
                    out.println("<tbody>");
                    for(Entitie i: servicios){
                        if(!idOrder.equals(i.getId())){
                            String a="";
                            a+="onclick=\"openViewOrderService("+i.getId()+")\"";
                            out.println("<tr>");
                            out.println("<td><a href=\"#OS"+i.getId()+"\" class=\"\" "+a+">"+i.getId()+"</a></td>");
                            out.println("<td>"+i.getDataOfLabel("FECHA")+"</td>");
                            try{
                                Entitie propietario = new Entitie(App.TABLE_CLIENTE);
                                propietario.getEntitieID(i.getDataOfLabel("PROPIETARIO"));
                                out.println("<td><a href=\"#clientOpen\" onclick=\"window.open('formEditableEntidad?variable=8&entidad="+propietario.getId()+"','ventana'"
                                        + ",'width=640,height=480,scrollbars=NO,menubar=NO,resizable=NO"
                                        + ",titlebar=NO,status=NO');\">"
                                        + ""+propietario.getDataOfLabel("TIPODOC")
                                        +propietario.getDataOfLabel("CEDULA")+"</a></td>");
                                Entitie vehiculo = new Entitie(App.TABLE_VEHICULO);
                                vehiculo.getEntitieID(i.getDataOfLabel("VEHICULO"));
                                out.println("<td><a href=\"#vehiculoOpen\" onclick=\"window.open('formEditableEntidad?variable=9&entidad="+vehiculo.getId()+"','ventana'"
                                        + ",'width=640,height=480,scrollbars=NO,menubar=NO,resizable=NO"
                                        + ",titlebar=NO,status=NO');\">"
                                        + ""+vehiculo.getDataOfLabel("PLACA")+"</a></td>");
                                Entitie osdetalle = new Entitie(App.TABLE_DOS);
                                ArrayList<Entitie> detalle = osdetalle.getEntitieParam("OS", i.getId());
                                int cant=detalle.size();
                                int cant2 = 0;
                                for(Entitie j: detalle){
                                    if(j.getDataOfLabel("ESTADO").equals("2")){
                                        cant2++;
                                    }
                                }
                                out.println("<td>"+cant+" | "+(cant2)+"</td>");
                                Entitie canal = new Entitie(App.TABLE_CANALES);
                                canal.getEntitieID(i.getDataOfLabel("ID_CANAL"));
                                out.println("<td>"+canal.getDataOfLabel("NOMBRE")+"</td>");
                                out.println("</tr>");
                                idOrder = i.getId();
                            }catch(IndexOutOfBoundsException s){
                                System.out.println("Error:"+s);
                            }
                            
                        }
                        
                    }
                    out.println("</tbody>");
                    out.println("</table>");
                }
            }
            else{
                response.sendRedirect("app.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }
        catch(NullPointerException e){
            response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales Error: "+e.getMessage());
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
            Logger.getLogger(TodosServiciosInform.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TodosServiciosInform.class.getName()).log(Level.SEVERE, null, ex);
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
