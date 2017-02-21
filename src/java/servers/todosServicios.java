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
public class todosServicios extends HttpServlet {

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
                String name="Sin nombre";
                ArrayList<Entitie> servicios= new ArrayList<>();
                try{
                    menu.getEntitieID(request.getParameter("variable"));
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                Entitie servicio = new Entitie(App.TABLE_ORDENSERVICIO);
                name= "TODAS LAS ORDENES DE SERVICIO";
                servicios = servicio.getEntities();
                
                try (PrintWriter out = response.getWriter()) {
                    String idOrder="-1";
                    for(Entitie i: servicios){
                        if(!idOrder.equals(i.getId())){
                            String a="";
                            a+="onclick=\"openViewOrderService("+i.getId()+")\"";
                            out.println("<tr>");
                            out.println("<td><a href=\"#OS"+i.getId()+"\" class=\"\" "+a+">"+i.getId()+"</a></td>");
                            out.println("<td>"+i.getDataOfLabel("FECHA")+"</td>");
                            try{
                                Entitie propietario = new Entitie(App.TABLE_PROPIETARIO);
                                propietario.getEntitieID(i.getDataOfLabel("PROPIETARIO"));
                                out.println("<td>"+propietario.getDataOfLabel("TIPODOC")
                                        +propietario.getDataOfLabel("CEDULA")+"</td>");
                                Entitie vehiculo = new Entitie(App.TABLE_VEHICULO);
                                vehiculo.getEntitieID(i.getDataOfLabel("VEHICULO"));
                                out.println("<td>"+vehiculo.getDataOfLabel("PLACA")+"</td>");
                                Entitie osdetalle = new Entitie(App.TABLE_OSDETALLE);
                                ArrayList<Entitie> detalle = osdetalle.getEntitieParam("OS", i.getId());
                                int cant=detalle.size();
                                int cant2 = 0;
                                for(Entitie j: detalle){
                                    if(j.getDataOfLabel("ESTADO").equals("2")){
                                        cant2++;
                                    }
                                }
                                out.println("<td>"+cant+" | "+(cant2)+"</td>");
                                Entitie canal = new Entitie(App.TABLE_CANALES);
                                canal.getEntitieID(i.getDataOfLabel("ID_CANAL"));
                                out.println("<td>"+canal.getDataOfLabel("NOMBRE")+"</td>");
                                out.println("</tr>");
                                idOrder = i.getId();
                            }catch(IndexOutOfBoundsException s){
                                System.out.println("Error:"+s);
                            }
                            
                        }
                        
                    }
                    out.println("<script type=\"text/javascript\">\n" +
                        "    $().ready(function(){\n" +
                        "        $(\"#titleContend\").html(\""+name+"\");\n" +
                        "    });\n" +
                        "</script>");
                    out.println(""
                            + ""
                            + "");
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
            Logger.getLogger(todosServicios.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(todosServicios.class.getName()).log(Level.SEVERE, null, ex);
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
