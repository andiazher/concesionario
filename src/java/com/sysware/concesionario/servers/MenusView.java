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
 * 
 * Esta servlet es para resporder a la vista de los menus de la parte lateral de la pantalla
 * Esta en mejoramiento, para la siguiente version debe tener mas versatilidad para cuando se miniminiza la barra de menus
 * Los parametros se toman de acuerdo a la tabla de menus y rol_menu de la base de datos,
 * Los nombres en las columnas en la base de datos deben ser como se describen en la presente clase.
 * 
 */

public class MenusView extends HttpServlet {

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
                ArrayList<Entitie> menus = new ArrayList<>(); 
                ArrayList<Entitie> menus2 = new ArrayList<>();
                ArrayList<Entitie> hijos = new ArrayList<>(); 
                try{
                    Entitie menu = new Entitie(App.TABLE_ROL_MENU);
                    String roleString =(String) request.getSession().getAttribute("role");
                    menus = menu.getEntitieParam("ROL", roleString);
                    for(Entitie i: menus){
                        Entitie menu2 = new Entitie(App.TABLE_MENUS);
                        menu2.getEntitieID(i.getDataOfLabel("MENU"));
                        menus2.add(menu2);
                    }
                    for(Entitie i: menus2){
                        String tipe = i.getDataOfLabel("TYPE");
                        if(tipe.equals("3")){
                            hijos.add(i);
                        }
                    }
                }catch(NullPointerException s){
                    System.out.println("Error:"+s);
                }
                
                try (PrintWriter out = response.getWriter()) {
                    
                    for(Entitie i : menus2){
                        String tipe = i.getDataOfLabel("TYPE");
                        if(tipe.equals("1")){
                            String active="";
                            if(i.getId().equals(request.getParameter("variable"))){
                                active= "class=\"active\" ";
                            }
                            else{ active="";}
                            out.println("<li "+active+">");
                            out.println("   <a href=\"#"+i.getDataOfLabel("MENU")+"\" onclick=\"load('"+i.getId()+"')\" >");
                            out.println("       <i class=\"material-icons\">"+i.getDataOfLabel("ICON")+"</i>");
                            out.println("       <p>"+i.getDataOfLabel("MENU")+"</p>");
                            out.println("   </a>");
                            out.println("</li>");
                        }
                        if(tipe.equals("2")){
                            
                            out.println("<li id=\""+i.getId()+"\">");
                            out.println("   <a data-toggle=\"collapse\" href=\"#"+i.getDataOfLabel("MENU")+"\" "
                                    + "id=\""+i.getDataOfLabel("NAME")+i.getId()+"\" >");
                            out.println("       <i class=\"material-icons\">"+i.getDataOfLabel("ICON")+"</i>");
                            out.println("       <p>"+i.getDataOfLabel("MENU")+" <b class=\"caret\"></b> </p>");
                            out.println("   </a>");
                            out.println("   <div class=\"collapse\" id=\""+i.getDataOfLabel("MENU")+"\">");
                            out.println("       <ul class=\"nav\">");
                            for(Entitie j: hijos){
                                String parent = j.getDataOfLabel("PARENT");
                                if(parent.equals(i.getId())){
                                    String active="";
                                    if(j.getId().equals(request.getParameter("variable"))){
                                        active= "class=\"active\" ";
                                        out.println("<script type=\"text/javascript\">" +
                                                    "    $().ready(function() {" +
                                                    "        $('#"+i.getDataOfLabel("MENU")+"').addClass('in');"+
                                                    "        $('#"+i.getId()+"').addClass('active');"+
                                                    "        $('#"+i.getDataOfLabel("MENU")+i.getId()+"').attr(\"aria-expanded\",\"true\");"+
                                                    "        $('#"+i.getDataOfLabel("MENU")+"').attr(\"aria-expanded\",\"true\");"+
                                                    "    });" +
                                                    "</script>");
                                    }
                                    else{ active="";}
                                    out.println("           <li "+active+">");
                                    out.println("               <a href=\"#"+j.getDataOfLabel("MENU")+"\" onclick=\"load('"+j.getId()+"')\" >"
                                            + ""+j.getDataOfLabel("MENU") );
                                    out.println("               </a>");
                                    out.println("           </li>");
                                }
                            }
                            
                            out.println("       </ul>");
                            out.println("   </div>");
                            out.println("</li>");
                        }
                    }

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
            Logger.getLogger(MenusView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MenusView.class.getName()).log(Level.SEVERE, null, ex);
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
