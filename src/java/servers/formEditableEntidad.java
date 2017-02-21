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
public class formEditableEntidad extends HttpServlet {

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
                                    out.println("<input type=\"text\" class=\"form-control\" value=\""+get+"\" "
                                            + "placeholder=\""+entitie.getColums().get(i)+"\" "
                                            + "name=\""+entitie.getColums().get(i)+"\">");
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
            Logger.getLogger(formEditableEntidad.class.getName()).log(Level.SEVERE, null, ex);
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
