/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.informes;

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
public class InformesDispersionByReceptor extends HttpServlet {

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
                String menu="";
                try{
                    menu= request.getParameter("menu");
                }catch(NullPointerException s){
                    s.printStackTrace();
                }
                if(menu.equals("getData")){
                    String name;
                    String fi="";
                    String ff="";   
                    ArrayList<Entitie> registros = new ArrayList<>();
                    String receptor="";
                    try{
                        fi= request.getParameter("fi");
                        ff= request.getParameter("ff");
                        receptor= request.getParameter("canal");
                    }catch(NullPointerException s){
                        System.out.println("Error: "+s);
                        System.out.println("Receptor vacio");
                    }
                    Entitie regDispersion = new Entitie(App.TABLE_REGISTRORECEP); //REGISTROS DISPERSION
                    name= "REGISTROS";
                    boolean nada = false;
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
                        param2.add(ff +" 23:59:59");
                        operation.add("<=");
                        nada=true;
                    }
                    if(!receptor.equals("")){
                        Entitie can = new Entitie(App.TABLE_RECEPTORES);
                        can.getEntitieID(receptor);
                        if(nada){
                            name+=",";
                        }
                        name+="  RECEPTOR: </b>"+can.getDataOfLabel("DESCRIPCION")+"<b>";
                        param1.add("RECEPTOR");
                        param2.add(receptor);
                        operation.add("=");
                        nada=true;
                    }

                    if(param1.isEmpty() && param2.isEmpty() && operation.isEmpty() && nada==false){
                        registros = regDispersion.getEntitieParams(param1, param2, operation, " ORDER BY RECEPTOR","");
                        name="TODOS LOS REGISTROS DE DISPERSION";
                    }
                    else{
                        registros = regDispersion.getEntitieParams(param1, param2, operation, " ORDER BY RECEPTOR","");
                    }
                    Entitie conce = new Entitie(App.TABLE_CONCESIONARIO);
                    Entitie recep = new Entitie(App.TABLE_RECEPTORES);
                    Entitie dos = new Entitie(App.TABLE_DOS);
                    Entitie servicio = new Entitie(App.TABLE_SERVICIOS);
                    try (PrintWriter out = response.getWriter()) {
                        out.println("{");
                        out.println("\"title\": \""+name+"\",");
                        out.println("\"info\": {");
                        out.println("   \"row\": [");
                        for(Entitie i: registros){
                            out.println("       {");
                            out.println("       \"id\": \""+i.getId()+"\",");
                            conce.getEntitieID(i.getDataOfLabel("CONCESIONARIO"));
                            out.println("       \"conce\": \""+conce.getDataOfLabel("NOMBRE")+"\",");
                            recep.getEntitieID(i.getDataOfLabel("RECEPTOR"));
                            out.println("       \"receptor\": \""+recep.getDataOfLabel("DESCRIPCION")+"\",");
                            out.println("       \"receptorId\": \""+i.getDataOfLabel("RECEPTOR")+"\",");
                            out.println("       \"fecha\": \""+i.getDataOfLabel("FECHA")+"\",");
                            dos.getEntitieID(i.getDataOfLabel("DOS"));
                            servicio.getEntitieID(dos.getDataOfLabel("SERVICIO"));
                            out.println("       \"servicio\": \""+servicio.getDataOfLabel("DESCRIPCION")+"\",");
                            out.println("       \"rubro\": \""+i.getDataOfLabel("RUBRO")+"\",");
                            out.println("       \"base\": \""+i.getDataOfLabel("VALORBASE")+"\",");
                            out.println("       \"tipo\": \""+i.getDataOfLabel("TIPO")+"\",");
                            out.println("       \"valorprog\": \""+i.getDataOfLabel("VALORPROG")+"\",");
                            out.println("       \"valorpag\": \""+i.getDataOfLabel("VALORCALCUL")+"\",");
                            out.println("       \"pret\": \""+i.getDataOfLabel("PRENT")+"\",");
                            out.println("       \"vrent\": \""+i.getDataOfLabel("VRENT")+"\",");
                            out.println("       \"pimp\": \""+i.getDataOfLabel("PIMP")+"\",");
                            out.println("       \"vimp\": \""+i.getDataOfLabel("VIMP")+"\"");
                            out.println("       }, ");
                        }
                            out.println("       {");
                            out.println("       \"id\": \"0\",");
                            out.println("       \"receptorId\": \"0\"");
                            out.println("       } ");
                        out.println("       ]");
                        out.println("   },");
                        out.println("\"andiazher\": \"andiazher.com\"");
                        out.println("}");
                        
                    }
                    //END
                    //END
                    //END
                    //END
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
            Logger.getLogger(InformesDispersionByReceptor.class.getName()).log(Level.SEVERE, null, ex);
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
