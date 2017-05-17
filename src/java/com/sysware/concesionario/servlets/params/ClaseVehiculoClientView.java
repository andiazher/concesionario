/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.params;


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
 * Este servlet responde al llamado del cliente para solicitar los rangos de cilindrajes que se debe escojer 
 * seleccionar el tipo de vehiculo,
 * 
 * Esta en modificacion. 
 */
public class ClaseVehiculoClientView extends HttpServlet {

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
                
                String var = request.getParameter("variable");
                
                /**
                *RETORNA LOS TIPOS DE VEHICULO GUARDADAS EN TIPOVEHICULO
                */
                if(var.equals("none")){
                    Entitie tipo = new Entitie(App.TABLE_TIPOVEH);
                    ArrayList<Entitie> tipos = tipo.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: tipos){
                            //DecimalFormat formateador = new DecimalFormat("###,###.##");
                            //formateador.format(Integer.parseInt(i.getDataOfLabel("CILINDRAJE"))) 
                            String s="";
                            if(i.getDataOfLabel("DESCRIPCION").contains("MOTO")){
                                s="selected";
                            }
                            out.println("<option value=\""+i.getId()+"\" "+s+">" 
                                    + i.getDataOfLabel("DESCRIPCION")+"</option>");
                        }
                    }
                }
                /**
                 * RETORNA LAS CLASES DE VEHICULO
                 */
                if(var.equals("clase")){
                    Entitie clase = new Entitie(App.TABLE_CLASEVEHI);
                    String tipo = request.getParameter("tipo");
                    ArrayList<Entitie> clases = clase.getEntitieParam("TIPO", tipo);
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: clases){
                            String s="";
                            if(i.getDataOfLabel("DESCRIPCION").equals("MOTOCICLETA")){
                                s="selected";
                            }
                            out.println("<option value=\""+i.getId()+"\" "+s+">" 
                                    + i.getDataOfLabel("DESCRIPCION")+"</option>");
                        }
                    }
                    
                }
                /**
                 * RETORNA LOS TIPOS DE VEHUCULO PERO PARA CALCULAR EL SOAT
                 */
                if(var.equals("tipo")){
                    Entitie clase = new Entitie(App.TABLE_TIPOVEHSOAT);
                    ArrayList<Entitie> clases = clase.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: clases){
                            out.println("<option value=\""+i.getId()+"\">" 
                                    + i.getDataOfLabel("DESCRIPCION")+"</option>");
                        }
                    }
                }
                /**
                 * RETORNA LA LISTA DE CONCESIONARIOS
                 */
                if(var.equals("concesionarios")){
                    Entitie clase = new Entitie(App.TABLE_CONCESIONARIO);
                    ArrayList<Entitie> clases = clase.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: clases){
                            out.println("<option value=\""+i.getId()+"\">" 
                                    + i.getDataOfLabel("NOMBRE")+"</option>");
                        }
                    }
                    
                }
                /**
                 * RETORNA LAS TARIFAS DEL SOAT DISPONIBLES DE ACUERDO AL TIPO DE VEHICULO QUE SE HAYA SELECCIONADO
                 */
                if(var.equals("especifico")){
                    Entitie soat = new Entitie(App.TABLE_SOAT);
                    String clase = request.getParameter("clase");
                    ArrayList<Entitie> valores= soat.getEntitieParam("TIPOVEH",clase );
                    try (PrintWriter out = response.getWriter()) {
                        out.println("{");
                        out.println("\"clases\": {");
                        out.println("   \"option\": [");
                        for(Entitie t : valores){
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\"}, ");
                        }
                        out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                        out.println("       ]");
                        out.println("   },");
                        String valor="0";
                        if(!valores.isEmpty()){
                            valor= valores.get(0).getDataOfLabel("VALOR");
                        }
                        out.println("\"valor\": \""+valor+"\",");
                        out.println("\"andiazher\": \"andiazher.com\"");
                        out.println("}");
                        
                    }
                }
                /**
                 * RETORNA SOLO EL VALOR DE LA BOLSA DE VALOR DEL CONCESIONARIO.
                 * DE ACUERDO AL USUARIO QUE ESTA HACIENDO LA CONSULTA.
                 * TAMBIEN RETORNA EL MINIMO Y EL NOMBRE EL CONCESIONARIO
                 */
                if(var.equals("concesionario")){
                    String usuario =(String) request.getSession().getAttribute("user");
                    Entitie user = new Entitie(App.TABLE_USUARIO);
                    user=user.getEntitieParam("USUARIO", usuario).get(0);
                    Entitie canal = new Entitie(App.TABLE_CANALES);
                    canal.getEntitieID(user.getDataOfLabel("ID_CANAL"));
                    Entitie concesionario = new Entitie(App.TABLE_CONCESIONARIO);
                    concesionario.getEntitieID(canal.getDataOfLabel("ID_CONCESIONARIO"));
                    
                    try (PrintWriter out = response.getWriter()) {
                        out.println("{");
                        out.println("\"valor\": \""+concesionario.getDataOfLabel("SALDO")+"\",");
                        out.println("\"minimo\": \""+concesionario.getDataOfLabel("MINIMO")+"\",");
                        out.println("\"nombre\": \""+concesionario.getDataOfLabel("NOMBRE")+"\",");
                        out.println("\"andiazher\": \"andiazher.com\"");
                        out.println("}");
                        
                    }
                }
                /**
                 * @return el valor del soat de acuerdo al tipo de carro seleccionado
                 */
                if(var.equals("valor")){
                    Entitie soat = new Entitie(App.TABLE_SOAT);
                    String tarifa = request.getParameter("clase");
                    soat.getEntitieID(tarifa);
                    try (PrintWriter out = response.getWriter()) {
                        out.println(soat.getDataOfLabel("VALOR"));
                    }
                }
                /**
                 * 
                 */
                if(var.equals("servicios")){
                    Entitie servi = new Entitie(App.TABLE_SERVICIOVEHI);
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: servi.getEntities()){
                            out.println("<option value=\""+i.getId()+"\">" 
                                    + i.getDataOfLabel("SERVICIO")+"</option>");
                        }
                        
                    }
                }
                /**
                 * @return la lista de marcas registradas en la tabla de marcas
                 */
                if(var.equals("marca")){
                    Entitie marca = new Entitie(App.TABLE_MARCA);
                    String tipo = request.getParameter("marca");
                    ArrayList<Entitie> marcas = marca.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: marcas){
                            String s="";
                            if(i.getDataOfLabel("DESCRIPCION").equals("AUTECO")){
                                s="selected";
                            }
                            out.println("<option value=\""+i.getId()+"\" "+s+">" 
                                    + i.getDataOfLabel("DESCRIPCION")+"</option>");
                        }
                    }
                    
                }
                /**
                 * Retona la lista de aseguradoras registradas en la tabla de aseguradoras
                 */
                
                if(var.equals("aseguradora")){
                    Entitie aseguradora = new Entitie(App.TABLE_ASEGURADORAS);
                    ArrayList<Entitie> aseguradoras = new ArrayList<>();
                    aseguradoras = aseguradora.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: aseguradoras){
                            out.println("<option value=\""+i.getId()+"\">" 
                                    + i.getDataOfLabel("DESCRIPCION")+"</option>");
                        }
                    }
                }
                /**
                 * RETORNA UNA LISTA DE CANALES DE ACUERDO A UN CONCESIONARIO
                 * COMO PARAMETRO
                 */
                if(var.equals("canales")){
                    Entitie canal = new Entitie(App.TABLE_CANALES);
                    ArrayList<Entitie> canales = new ArrayList<>();
                    String concesionario = request.getParameter("concesionario");
                    canales = canal.getEntitieParam("ID_CONCESIONARIO", concesionario);
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<option selected=\"\" value=\"\">--SELECCIONAR--</option>");
                        for(Entitie i: canales){
                            out.println("<option value=\""+i.getId()+"\">" 
                                    + i.getDataOfLabel("NOMBRE")+"</option>");
                        }
                    }
                }
                
                /**
                 * RETORNA UNA LISTA DE CANALES DE ACUERDO A UN CONCESIONARIO
                 * COMO PARAMETRO
                 */
                if(var.equals("canalesAll")){
                    Entitie canal = new Entitie(App.TABLE_CANALES);
                    ArrayList<Entitie> canales = canal.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<option selected=\"\" value=\"\">--TODOS--</option>");
                        for(Entitie i: canales){
                            out.println("<option value=\""+i.getId()+"\">" 
                                    + i.getDataOfLabel("NOMBRE")+"</option>");
                        }
                    }
                }
                
                /**
                 * RETORNA UNA LISTADO DE LOS SERVICIOS REGITRADOS
                 * COMO PARAMETRO
                 */
                if(var.equals("serviciosr")){
                    Entitie servicio = new Entitie(App.TABLE_SERVICIOS);
                    ArrayList<Entitie> servicios = servicio.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        String selected= "selected";
                        for(Entitie i: servicios){
                            out.println("<option value=\""+i.getId()+"\" "+selected+">" 
                                    + i.getDataOfLabel("DESCRIPCION")+"</option>");
                            selected="";
                        }
                    }
                }
                /**
                 * RETORNA LISTADOS DE RECEPTORES REGISTRADOS
                 */
                if(var.equals("AllReceptores")){
                    Entitie canal = new Entitie(App.TABLE_RECEPTORES);
                    ArrayList<Entitie> canales = canal.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        String selected="selected";
                        for(Entitie i: canales){
                            out.println("<option value=\""+i.getId()+"\" "+selected+" >" 
                                    + i.getDataOfLabel("DESCRIPCION")+"</option>");
                            selected="";
                        }
                    }
                }
                
            }
            else{
                /**
                 * En caso de ser invalida la session se envia a la pagina de login
                 */
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        } catch(NullPointerException a){
            try (PrintWriter out = response.getWriter()) {
                out.println("<script type=\"text/javascript\">\n" +
                    "    swal(\n" +
                    "        'Error:',\n" +
                    "        'No se puede cargar el contenido',\n" +
                    "        'error'\n" +
                    "    )\n" +
                    "</script>");
            }
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
            Logger.getLogger(ClaseVehiculoClientView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ClaseVehiculoClientView.class.getName()).log(Level.SEVERE, null, ex);
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
