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
public class tableEntitieView extends HttpServlet {

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
                
                ArrayList<Entitie> entidades;
                try{
                    menu.getEntitieID(request.getParameter("variable"));
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                Entitie entitie = new Entitie(menu.getDataOfLabel("ENTIDAD"));
                entidades = entitie.getEntities();
                
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<h4 class=\"card-title\" id=\"NameTable\">Tabla de "+entitie.getName().toUpperCase()+"</h4>");
                    out.println("<div class=\"toolbar text-right\">");
                        out.println("<button type=\"button\"  class=\"btn btn-success btn-round\" onclick=\"addForm(-1)\">");
                        out.println("<i class=\"material-icons\">add_circle</i></button>");
                    out.println("</div>");
                    out.println("<script src=\"js/jquery.datatables.js\"></script>");
                    out.println("<div class=\"material-datatables\">");
                    out.println("<table id=\"datatables\" class=\"table table-striped table-no-bordered table-hover\" cellspacing=\"0\" width=\"100%\" style=\"width:100%\">");
                    out.println("<thead><tr>");
                    out.println("<th class>ID</th>");
                    for(String i: entitie.getColums()){
                        out.println("<th class>"+i+"</th>");
                    }
                    out.println("<th class=\"disabled-sorting text-right\">Actions</th>");
                    out.println("</tr></thead>");
                    out.println("<tbody>");
                    for(Entitie i : entidades){
                        out.println("<tr class>");
                        out.println("<td>"+i.getId()+"</td>");
                        for(String j: i.getData()){
                            out.println("<td>"+j+"</td>");
                        }
                        out.println("<td class=\"td-actions text-right\">");
                        out.println("<button type=\"button\" rel=\"tooltip\" class=\"btn btn-success btn-round\" "
                                + " onclick=\"addForm('"+i.getId()+"')\">" +
"                                                            <i class=\"material-icons\">edit</i>\n" +
"                                                        </button>");
                        out.println("<button type=\"button\" rel=\"tooltip\" class=\"btn btn-danger btn-round remove\">\n" +
"                                                            <i class=\"material-icons\">close</i>\n" +
"                                                        </button>");
                        out.println("</td>");
                        out.println("</tr>");
                    }
                    out.println("</tbody>");
                    out.println("</table>");
                    out.println("</div>");
                    
                }
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }
        catch(NullPointerException e){
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
            Logger.getLogger(tableEntitieView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(tableEntitieView.class.getName()).log(Level.SEVERE, null, ex);
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
