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
public class EditEntitieFormView extends HttpServlet {

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
                Entitie menu = new Entitie(App.TABLE_MENUS);
                ArrayList<String> datos = new ArrayList<>();
                String idEntidad = "-1";
                String name="Entidad de tipo ";
                
                try{
                    menu.getEntitieID(request.getParameter("variable"));
                    idEntidad= request.getParameter("entidad");
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                Entitie entitie = new Entitie(menu.getDataOfLabel("ENTIDAD"));
                entitie.getEntitieID(idEntidad);
                if("-1".equals(idEntidad)){
                    name = "Nueva Entidad de tipo "+entitie.getName().toUpperCase();
                }
                else{
                    name="Edición de Entidad '"+entitie.getName().toUpperCase()+ "' ID="+entitie.getId();
                    datos = entitie.getData();
                }
                
                
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    
                    out.println("<h4 class=\"card-title\">"+name+"</h4>");
                    out.println("<div class=\"material-datatables\">");
                    if(datos.size()!=entitie.getColums().size() || idEntidad.contains("-1") ){
                        for(String i: entitie.getColums()){
                            datos.add("");
                        }
                    }
                    for(int i=0; i<entitie.getColums().size(); i++){
                        out.println("<div class=\"row\">");
                        out.println("<label class=\"col-sm-2 label-on-left\">"+entitie.getColums().get(i)+"</label>");
                            out.println("<div class=\"col-sm-10\">");
                                out.println("<div class=\"form-group label-floating is-empty\">");
                                    out.println("<label class=\"control-label\"></label>");
                                    String get = (datos.get(i) == null) ? "" : datos.get(i);
                                    Entitie object;
                                    ArrayList<Entitie> objects;
                                    switch(entitie.getName()){
                                        case "vehiculos":
                                            switch(entitie.getColums().get(i)){
                                                case "PROPIETARIO":
                                                    object = new Entitie(App.TABLE_PROPIETARIO);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +ob.getDataOfLabel("NOMBRE")+" "+ob.getDataOfLabel("APELLIDO")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                case "TIPO":
                                                    object = new Entitie(App.TABLE_TIPOVEH);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +ob.getDataOfLabel("DESCRIPCION")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                case "CLASE":
                                                    object = new Entitie(App.TABLE_CLASEVEHI);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +ob.getDataOfLabel("DESCRIPCION")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                case "MARCA":
                                                    object = new Entitie(App.TABLE_MARCA);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +ob.getDataOfLabel("DESCRIPCION")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                case "SERVICIO":
                                                    object = new Entitie(App.TABLE_SERVICIOVEHI);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +ob.getDataOfLabel("SERVICIO")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                default:
                                                    out.println("<input type=\"text\" class=\"form-control\" value=\""+get+"\" "
                                                        + "placeholder=\""+entitie.getColums().get(i)+"\" "
                                                        + "name=\""+entitie.getColums().get(i)+"\">");
                                                    break;
                                            }
                                            break;
                                        case "rol_menu":
                                            switch(entitie.getColums().get(i)){
                                                case "ROL":
                                                    object = new Entitie(App.TABLE_ROLES);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +"["+ob.getId()+"] "+ob.getDataOfLabel("ROL")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                case "MENU":
                                                    object = new Entitie(App.TABLE_MENUS);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +"["+ob.getId()+"] "+ob.getDataOfLabel("MENU")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                default:
                                                    out.println("<input type=\"text\" class=\"form-control\" value=\""+get+"\" "
                                                        + "placeholder=\""+entitie.getColums().get(i)+"\" "
                                                        + "name=\""+entitie.getColums().get(i)+"\">");
                                                    break;
                                            }
                                            break;
                                        case "usuarios":
                                            switch(entitie.getColums().get(i)){
                                                case "ID_ROL":
                                                    object = new Entitie(App.TABLE_ROLES);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +"["+ob.getId()+"] "+ob.getDataOfLabel("ROL")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                case "ID_CANAL":
                                                    object = new Entitie(App.TABLE_CANALES);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +"["+ob.getId()+"] "+ob.getDataOfLabel("NOMBRE")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                default:
                                                    out.println("<input type=\"text\" class=\"form-control\" value=\""+get+"\" "
                                                        + "placeholder=\""+entitie.getColums().get(i)+"\" "
                                                        + "name=\""+entitie.getColums().get(i)+"\">");
                                                    break;
                                            }
                                            break;
                                        case "roles":
                                            switch(entitie.getColums().get(i)){
                                                case "MENUDEF":
                                                    object = new Entitie(App.TABLE_MENUS);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +"["+ob.getId()+"] "+ob.getDataOfLabel("MENU")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                default:
                                                    out.println("<input type=\"text\" class=\"form-control\" value=\""+get+"\" "
                                                        + "placeholder=\""+entitie.getColums().get(i)+"\" "
                                                        + "name=\""+entitie.getColums().get(i)+"\">");
                                                    break;
                                            }
                                            break;
                                        case "concesionario":
                                            switch(entitie.getColums().get(i)){
                                                case "ID_MARCA":
                                                    object = new Entitie(App.TABLE_MARCA);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +ob.getDataOfLabel("DESCRIPCION")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                case "ID_CIUDAD":
                                                    object = new Entitie(App.TABLE_CIUDADES);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +ob.getDataOfLabel("DEPARTAMENTO")+" - "+ob.getDataOfLabel("CIUDAD")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                case "SECRETARIA":
                                                    object = new Entitie(App.TABLE_SECRETARIAST);
                                                    objects = object.getEntities();
                                                    out.println("<select class=\"select-with-transition\" data-style=\"btn btn-default\" name=\""+entitie.getColums().get(i)+"\">");
                                                    out.println("<option selected=\"\" value=\"0\">--SELECCIONAR--</option>");
                                                    for(Entitie ob: objects){
                                                        String selected= get.equals(ob.getId()) ? "selected":"";
                                                        out.println("<option value=\""+ob.getId()+"\" "+selected+" >"
                                                                +ob.getDataOfLabel("DESCRIPCION")
                                                                + "</option>");
                                                    }
                                                    out.println("</select>");
                                                    break;
                                                default:
                                                    out.println("<input type=\"text\" class=\"form-control\" value=\""+get+"\" "
                                                        + "placeholder=\""+entitie.getColums().get(i)+"\" "
                                                        + "name=\""+entitie.getColums().get(i)+"\">");
                                                    break;
                                            }
                                            break;    
                                            
                                        default: 
                                            out.println("<input type=\"text\" class=\"form-control\" value=\""+get+"\" "
                                            + "placeholder=\""+entitie.getColums().get(i)+"\" "
                                            + "name=\""+entitie.getColums().get(i)+"\">");
                                            break;
                                    }
                                    
                                out.println("</div>");
                            out.println("</div>");
                        out.println("</div>");
                    }
                    out.println("</div>");
                    out.println("</div>");
                    
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
            Logger.getLogger(EditEntitieFormView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //PARA BORRAR
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EditEntitieFormView.class.getName()).log(Level.SEVERE, null, ex);
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
