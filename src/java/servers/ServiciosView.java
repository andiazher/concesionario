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
 * Este servlet responde al llamado del cliente con la informacion de los servicios
 * que estan registrados en la tabla de servicios, se responde con los servicios activos o con estodo = 1,
 * si estan con otro estado no los mostrara, se debe tener en cuenta bien la parametrizacion de los estados
 * para esta caso el Activo es 1 e inactivo cualquier otro.
 */
public class ServiciosView extends HttpServlet {

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
                Entitie servicio = new Entitie(App.TABLE_SERVICIOS);
                ArrayList<Entitie> servicios= servicio.getEntitieParam("ESTADO", "1");
                try (PrintWriter out = response.getWriter()) {
                    for(Entitie s: servicios){
                        String checked= "";
                        if(s.getId().equals("1")){
                            checked="checked";
                        }
                        out.println("<div class=\"col-sm-3 col-sm-offset-1 checkbox-radios\">");
                        out.println("   <div class=\"checkbox\">");
                        out.println("       <label class=\"text-success\" >");            
                        out.println("           <input type=\"checkbox\" name=\""+s.getId()+"\" "+checked+">");
                        out.println("           <span class=\"checkbox-material\"><span class=\"check\"></span></span>");
                        out.println("           ");
                        out.println("       </label>"+s.getDataOfLabel("DESCRIPCION"));            
                        out.println("   </div>");
                        out.println("</div>");
                    }
                }
            }
            else{
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
            Logger.getLogger(ServiciosView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServiciosView.class.getName()).log(Level.SEVERE, null, ex);
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
