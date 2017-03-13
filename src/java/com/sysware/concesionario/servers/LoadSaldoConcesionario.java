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
public class LoadSaldoConcesionario extends HttpServlet {

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
                String news="0";
                try{
                    news=request.getParameter("saldo");
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                String usuario =(String) request.getSession().getAttribute("user");
                Entitie user = new Entitie(App.TABLE_USUARIO);
                user=user.getEntitieParam("USUARIO", usuario).get(0);
                Entitie canal = new Entitie(App.TABLE_CANALES);
                canal.getEntitieID(user.getDataOfLabel("ID_CANAL"));
                Entitie concesionario = new Entitie(App.TABLE_CONCESIONARIO);
                concesionario.getEntitieID(canal.getDataOfLabel("ID_CONCESIONARIO"));
                int saldoa= Integer.parseInt(concesionario.getDataOfLabel("SALDO"));
                int ss = Integer.parseInt(news);
                int nuevo = saldoa+ss;
                concesionario.getData().set(concesionario.getColums().indexOf("SALDO"), nuevo+"");
                concesionario.update();
                
                //CREATE REGISTER
                Entitie reg= new Entitie(App.TABLE_REGMOVBOLSA);
                for(String s: reg.getColums()){
                    reg.getData().add("0");
                }
                reg.getData().set(reg.getColums().indexOf("CONCESIONARIO"), concesionario.getId());
                Calendar fecha = new GregorianCalendar();
                String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)+
                    " "+fecha.get(Calendar.HOUR_OF_DAY)+":"+fecha.get(Calendar.MINUTE)+":"+fecha.get(Calendar.SECOND);
                reg.getData().set(reg.getColums().indexOf("FECHA"),f);
                reg.getData().set(reg.getColums().indexOf("TIPOMOV"),"INC");
                reg.getData().set(reg.getColums().indexOf("VALOR"),news);
                reg.getData().set(reg.getColums().indexOf("SALDO"),nuevo+"");
                reg.create();
                
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }catch(NullPointerException s){
            response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales&error:"+s);
        }
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet loadSaldoConcesionario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loadSaldoConcesionario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
            Logger.getLogger(LoadSaldoConcesionario.class.getName()).log(Level.SEVERE, null, ex);
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
