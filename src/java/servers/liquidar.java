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
import java.util.Calendar;
import java.util.Enumeration;
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
public class liquidar extends HttpServlet {

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
                Enumeration<String> a= request.getParameterNames();
                int valor=0;
                Entitie liquidacion = new Entitie(App.TABLE_LIQUIDACION);
                for(String i: liquidacion.getColums()){
                    liquidacion.getData().add("");
                }
                Calendar fecha = new GregorianCalendar();
                String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)+
                                    " "+fecha.get(Calendar.HOUR_OF_DAY)+":"+fecha.get(Calendar.MINUTE)+":"+fecha.get(Calendar.SECOND);
                liquidacion.getData().set(liquidacion.getColums().indexOf("FECHA"), f);
                liquidacion.getData().set(liquidacion.getColums().indexOf("FECHAI"), f);
                liquidacion.getData().set(liquidacion.getColums().indexOf("FECHAF"), f);
                liquidacion.getData().set(liquidacion.getColums().indexOf("CONCESIONARIO"), "0");
                liquidacion.getData().set(liquidacion.getColums().indexOf("VALOR"), "0");
                liquidacion.getData().set(liquidacion.getColums().indexOf("ESTADO"), "PPAGAR"); //ESTADO PPAGAR, PAGADA, ANULADA
                liquidacion.create();
                String id="0";
                for(Entitie o: liquidacion.getEntities()){
                    id= o.getId();
                }
                boolean isFirst = true;
                String fi=f;
                String ff=f;
                String conce="0";
                while(a.hasMoreElements()){
                    String name= a.nextElement();
                    Entitie dos = new Entitie(App.TABLE_OSDETALLE);
                    dos.getEntitieID(name);
                    if(isFirst){
                        fi= dos.getDataOfLabel("FECHAT");
                        isFirst=false;
                    }
                    ff= dos.getDataOfLabel("FECHAT");
                    Entitie os = new Entitie(App.TABLE_ORDENSERVICIO);
                    os.getEntitieID(dos.getDataOfLabel("OS"));
                    Entitie canl= new Entitie(App.TABLE_CANALES);
                    canl.getEntitieID(os.getDataOfLabel("ID_CANAL"));
                    conce= canl.getDataOfLabel("ID_CONCESIONARIO");
                    valor += Integer.parseInt(dos.getDataOfLabel("COM_CONCE"));
                    dos.getData().set(dos.getColums().indexOf("ESTADOL"), "LIQUIDADO");//LIQUIDADO | PENDIENTE
                    dos.getData().set(dos.getColums().indexOf("LIQUIDACION"), id);
                    dos.update();
                }
                liquidacion.getEntitieID(id);
                liquidacion.getData().set(liquidacion.getColums().indexOf("FECHAI"), fi);
                liquidacion.getData().set(liquidacion.getColums().indexOf("FECHAF"), ff);
                liquidacion.getData().set(liquidacion.getColums().indexOf("CONCESIONARIO"), conce);
                liquidacion.getData().set(liquidacion.getColums().indexOf("VALOR"), ""+valor);
                liquidacion.update();
                try (PrintWriter out = response.getWriter()) {
                    out.println(""+valor);
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
            Logger.getLogger(liquidar.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(liquidar.class.getName()).log(Level.SEVERE, null, ex);
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
