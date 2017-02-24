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
public class Informes extends HttpServlet {

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
                String entidad="";
                try{
                    menu.getEntitieID(request.getParameter("variable"));
                    entidad=menu.getDataOfLabel("ENTIDAD");
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                if(entidad.equals("dispersion")){
                    Entitie registroR = new Entitie(App.TABLE_REGISTRORECEP);
                    Entitie concesionario = new Entitie(App.TABLE_CONCESIONARIO);
                    Entitie orden = new Entitie(App.TABLE_ORDENSERVICIO);
                    Entitie osdetalle = new Entitie(App.TABLE_OSDETALLE);
                    Entitie servicio = new Entitie(App.TABLE_SERVICIOS);
                    Entitie receptor = new Entitie(App.TABLE_RECEPTORES);
                    ArrayList<Entitie> resgistros = registroR.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: resgistros){
                            out.println("<tr>");
                            concesionario.getEntitieID(i.getDataOfLabel("CONCESIONARIO"));
                            receptor.getEntitieID(i.getDataOfLabel("RECEPTOR"));
                            out.println("<td>"+concesionario.getDataOfLabel("NOMBRE")+"</td>");
                            out.println("<td>"+receptor.getDataOfLabel("DESCRIPCION")+"</td>");
                            osdetalle.getEntitieID(i.getDataOfLabel("SERVICIO"));
                            orden.getEntitieID(osdetalle.getDataOfLabel("OS"));
                            out.println("<td>"+orden.getDataOfLabel("FECHA")+"</td>");
                            servicio.getEntitieID(osdetalle.getDataOfLabel("SERVICIO"));
                            out.println("<td>"+servicio.getDataOfLabel("DESCRIPCION")+"</td>");
                            DecimalFormat formateador = new DecimalFormat("###,###.##");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("VALORBASE")))+"</td>");
                            String ss= "%";
                            if(i.getDataOfLabel("TIPO").equals("1")){
                                ss="("+i.getDataOfLabel("VALORPROG")+"%)";
                            }
                            else{
                                ss="--";
                            }
                            out.println("<td class=\"text-right\">"+ss+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("VALORCALCUL")))+"</td>");
                            out.println("</tr>");
                        }
                    }
                }
                if(entidad.contains("matricula")){
                    Entitie registroR = new Entitie(App.TABLE_REGISTROMATRICULA);
                    ArrayList<Entitie> resgistros = registroR.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: resgistros){
                            out.println("<tr>");
                            out.println("<td>"+i.getId()+"</td>");
                            out.println("<td>"+i.getDataOfLabel("FACTURA")+"</td>");
                            out.println("<td>"+i.getDataOfLabel("FECHA")+"</td>");
                            out.println("<td>"+i.getDataOfLabel("PROPIETARIO")+"</td>");
                            out.println("<td>"+i.getDataOfLabel("PLACA")+"</td>");
                            out.println("<td>"+i.getDataOfLabel("CLASE")+"</td>");
                            out.println("<td>"+i.getDataOfLabel("GESTORIA")+"</td>");
                            
                            DecimalFormat formateador = new DecimalFormat("###,###.##");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("MATRICULA")))+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("RUNT")))+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("PAPELERIA")))+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("MENSAJERIA")))+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("IMPUESTOS")))+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("OTROS")))+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("RETEFUENTE")))+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("HONORARIO")))+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("TOTAL")))+"</td>");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("SALDO")))+"</td>");
                            out.println("</tr>");
                        }
                    }
                }
                
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }
        catch(NullPointerException s){
            try (PrintWriter out = response.getWriter()) {
                out.println("<script type=\"text/javascript\">\n" +
                    "    swal(\n" +
                    "        'Error:',\n" +
                    "        'No se puede cargar el contenido "+s+"',\n" +
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
            Logger.getLogger(Informes.class.getName()).log(Level.SEVERE, null, ex);
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
            throws ServletException, IOException{
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Informes.class.getName()).log(Level.SEVERE, null, ex);
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
