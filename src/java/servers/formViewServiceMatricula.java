/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servers;

import com.sysware.concesionario.App;
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
public class formViewServiceMatricula extends HttpServlet {

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
                String param= request.getParameter("param");
                if(param.equals("list")){
                    try (PrintWriter out = response.getWriter()) {
                        Entitie secretaria = new Entitie(App.TABLE_SECRETARIAST);
                        ArrayList<Entitie> secretarias = secretaria.getEntities();
                        Entitie gestoria = new Entitie(App.TABLE_GESTORIASMATR);
                        ArrayList<Entitie> gestorias =  gestoria.getEntities();
                        Entitie parametro = new Entitie(App.TABLE_PARAMETROSFORMS);
                        ArrayList<Entitie> params = parametro.getEntitieParam("FORM", "MATRICULA");
                        String papeleria = "0";
                        String mensajeria = "0";
                        for(Entitie e: params){
                            if(e.getDataOfLabel("VALUE").equals("PAPELERIA")){
                                papeleria= e.getDataOfLabel("VALUE2");
                            }
                            if(e.getDataOfLabel("VALUE").equals("MENSAJERIA")){
                                mensajeria= e.getDataOfLabel("VALUE2");
                            }
                        }
                        out.println("{");
                        //out.println("\"idServicio\": \""+detalle.getDataOfLabel("SERVICIO")+"\",");
                        //out.println("\"nombreservicio\": \""+servicio.getDataOfLabel("DESCRIPCION")+"\",");
                        //out.println("\"placa\": \""+v.getDataOfLabel("PLACA")+"\",");
                        out.println("\"secretaria\": {");
                        out.println("   \"option\": [");
                        for(Entitie t : secretarias){
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\"}, ");
                        }
                        out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                        out.println("       ]");
                        out.println("   },");

                        out.println("\"gestoria\": {");
                        out.println("   \"option\": [");
                        for(Entitie t : gestorias){
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\"}, ");
                        }
                        out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                        out.println("       ]");
                        out.println("   },");
                        out.println("\"papeleria\": \""+papeleria+"\",");
                        out.println("\"mensajeria\": \""+mensajeria+"\",");
                        out.println("\"andiazher\": \"andiazher.com\"");
                        out.println("}");
                    }
                }
                if(param.contains("load")){
                    try (PrintWriter out = response.getWriter()) {
                        String secretaria = request.getParameter("secretaria");
                        String gestoria = request.getParameter("gestoria");
                        String clase = request.getParameter("clase");
                        Entitie valor = new Entitie(App.TABLE_MATRICULA);
                        ArrayList<String> params= new ArrayList<>();
                        ArrayList<String> params2= new ArrayList<>();
                        params.add("SECRETARIA");
                        params.add("GESTORIA");
                        params.add("CLASEV");
                        params2.add(secretaria);
                        params2.add(gestoria);
                        params2.add(clase);
                        
                        ArrayList<Entitie> valores = valor.getEntitieParams(params, params2);
                        valor = valores.get(0);
                        Entitie secretari = new Entitie(App.TABLE_SECRETARIAST);
                        secretari.getEntitieID(secretaria);
                        Entitie gestori = new Entitie(App.TABLE_GESTORIASMATR);
                        gestori.getEntitieID(gestoria);
                        
                        out.println("{");
                        out.println("\"matricula\": \""+valor.getDataOfLabel("VALORM")+"\",");
                        out.println("\"runt\": \""+valor.getDataOfLabel("VALORRUNT")+"\",");
                        out.println("\"link\": \""+secretari.getDataOfLabel("LINK")+"\",");
                        out.println("\"honorario\": \""+gestori.getDataOfLabel("HONORARIO")+"\",");
                        out.println("\"iva\": \""+gestori.getDataOfLabel("IVA")+"\",");
                        
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
            Logger.getLogger(formViewServiceMatricula.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(formViewServiceMatricula.class.getName()).log(Level.SEVERE, null, ex);
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
