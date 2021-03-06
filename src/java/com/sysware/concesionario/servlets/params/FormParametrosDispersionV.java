/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.params;

import com.sysware.concesionario.app.App;
import com.sysware.concesionario.entitie.Entitie;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
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
public class FormParametrosDispersionV extends HttpServlet {

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
                
                String var = request.getParameter("variable");
                
                /**
                 *
                 */
                if(var.equals("rubrosPorServicio")){
                    Entitie rubro=new Entitie(App.TABLE_RUBRODIPS);
                    ArrayList<Entitie> rubros = new ArrayList<>();
                    try{
                        String servicio = request.getParameter("servicio");
                        rubros= rubro.getEntitieParam("SERVICIO", servicio);
                    }catch(NullPointerException s){s.printStackTrace();}
                    catch(IndexOutOfBoundsException s){s.printStackTrace();}
                    try (PrintWriter out = response.getWriter()) {
                        out.println("{");
                        out.println("\"rubros\": {");
                        out.println("   \"option\": [");
                        for(Entitie t : rubros){
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("NOMRUBRO")+"\"}, ");
                        }
                        out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                        out.println("       ]");
                        out.println("   },");
                        out.println("\"andiazher\": \"andiazher.com\"");
                        out.println("}");
                    }
                }
                /**
                 * 
                 */
                if(var.equals("controldisper")){
                    Entitie conDisp= new Entitie(App.TABLE_CONTROLDIPS);
                    Entitie rubroE=new Entitie(App.TABLE_RUBRODIPS);
                    Entitie receptor = new Entitie(App.TABLE_RECEPTORES);
                    ArrayList<Entitie> rubros = new ArrayList<>();
                    String name="Resultados";
                    String error="1";
                    String servicio ="";
                    String concesionario="";
                    String rubro="";
                    String form="";
                    ArrayList<Entitie> parametros = new ArrayList<>();
                    try{
                        servicio = request.getParameter("servicio");
                        concesionario = request.getParameter("concesionario");
                        rubro = request.getParameter("rubro");
                        form = request.getParameter("form");
                        rubroE.getEntitieID(rubro);
                        name+=" para el rubro <b>"+rubroE.getDataOfLabel("NOMRUBRO")+"</b>";
                        ArrayList<String> param1 = new ArrayList<>();
                        ArrayList<String> param2 = new ArrayList<>();
                        param1.add("CONCESIONARIO");
                        param1.add("RUBRO");
                        param2.add(concesionario);
                        param2.add(rubro);
                        parametros = conDisp.getEntitieParams(param1, param2);
                        error="0";
                    }catch(NullPointerException s){s.printStackTrace();}
                    catch(IndexOutOfBoundsException s){s.printStackTrace();}
                    
                    try (PrintWriter out = response.getWriter()) {
                        out.println("{");
                        out.println("\"noerror\": \""+error+"\",");
                        out.println("\"conce\": \""+concesionario+"\",");
                        out.println("\"rubro\": \""+rubro+"\",");
                        out.println("\"title\": \""+name+"\",");
                        out.println("\"info\": {");
                        out.println("   \"row\": [");
                        for(Entitie i: parametros){
                            out.println("       {");
                            out.println("       \"id\": \""+i.getId()+"\",");
                            receptor.getEntitieID(i.getDataOfLabel("RECEPTOR"));
                            out.println("       \"receptor\": \""+receptor.getDataOfLabel("DESCRIPCION")+"\",");
                            out.println("       \"tipo\": \""+i.getDataOfLabel("TIPO")+"\",");
                            int valor= Integer.parseInt(i.getDataOfLabel("VALOR"));
                            out.println("       \"valor\": \""+valor+"\",");
                            out.println("       \"retencion\": \""+i.getDataOfLabel("RETENCION")+"\",");
                            out.println("       \"impdeclara\": \""+i.getDataOfLabel("IMPDECLARA")+"\"");
                            out.println("       }, ");
                        }
                            out.println("       {");
                            out.println("       \"id\": \"0\"");
                            out.println("       } ");
                        out.println("       ]");
                        out.println("   },");
                        out.println("\"andiazher\": \"andiazher.com\"");
                        out.println("}");
                    }
                }
                /**
                 * 
                 */
                if(var.equals("rowdispersion")){
                    Entitie conDisp= new Entitie(App.TABLE_CONTROLDIPS);
                    Entitie receptor = new Entitie(App.TABLE_RECEPTORES);
                    ArrayList<Entitie> receptores = receptor.getEntities();
                    try{
                        String id = request.getParameter("idp");
                        conDisp.getEntitieID(id);
                    }catch(NullPointerException s){s.printStackTrace();}
                    catch(IndexOutOfBoundsException s){s.printStackTrace();}
                    try (PrintWriter out = response.getWriter()) {
                        out.println("{");
                        out.println("\"receptor\": {");
                        out.println("   \"option\": [");
                        String idReceptor = conDisp.getDataOfLabel("RECEPTOR");
                        for(Entitie t : receptores){
                            if(idReceptor.equals(t.getId())){
                                out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\", \"selected\":\"selected\"},");
                            }
                            else{
                                out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\"}, ");
                            }
                        }
                        out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                        out.println("       ]");
                        out.println("   },");
                        out.println("\"tipo\": \""+conDisp.getDataOfLabel("TIPO")+"\",");
                        out.println("\"valor\": \""+conDisp.getDataOfLabel("VALOR")+"\",");
                        out.println("\"vr\": \""+conDisp.getDataOfLabel("RETENCION")+"\",");
                        out.println("\"vi\": \""+conDisp.getDataOfLabel("IMPDECLARA")+"\",");
                        out.println("\"andiazher\": \"andiazher.com\"");
                        out.println("}");
                    }
                }
                /**
                 * 
                 */
                if(var.equals("save")){
                    Enumeration<String> a= request.getParameterNames();
                    a.nextElement();
                    String receptor = a.nextElement();
                    receptor= request.getParameter(receptor);
                    String id = a.nextElement();
                    id= request.getParameter(id);
                    String valor = a.nextElement();
                    valor= request.getParameter(valor);
                    String tipo = a.nextElement();
                    tipo= request.getParameter(tipo);
                    String valorR = a.nextElement();
                    valorR= request.getParameter(valorR);
                    String valorIm = a.nextElement();
                    valorIm= request.getParameter(valorIm);
                    
                    Entitie re= new Entitie(App.TABLE_CONTROLDIPS);
                    re.getEntitieID(id);
                    if(id.equals("0")){
                        for(String i: re.getColums()){
                            re.getData().add("0");
                        }
                        String conce = a.nextElement();
                        conce= request.getParameter(conce);
                        String rubro = a.nextElement();
                        rubro= request.getParameter(rubro);
                        re.getData().set(re.getColums().indexOf("CONCESIONARIO"), conce);
                        re.getData().set(re.getColums().indexOf("RUBRO"), rubro);
                    }
                    re.getData().set(re.getColums().indexOf("RECEPTOR"), receptor);
                    re.getData().set(re.getColums().indexOf("TIPO"), tipo);
                    re.getData().set(re.getColums().indexOf("VALOR"), valor);
                    re.getData().set(re.getColums().indexOf("RETENCION"), valorR);
                    re.getData().set(re.getColums().indexOf("IMPDECLARA"), valorIm);
                    if(id.equals("0")){
                        re.create();
                    }else{
                        re.update();
                    }
                }
                /**
                 * 
                 */
                if(var.equals("receptores")){
                    Entitie receptor = new Entitie(App.TABLE_RECEPTORES);
                    ArrayList<Entitie> receptores = receptor.getEntities();
                    try (PrintWriter out = response.getWriter()) {
                        out.println("{");
                        out.println("\"receptor\": {");
                        out.println("   \"option\": [");
                        for(Entitie t : receptores){
                            out.println("       {\"value\": \""+t.getId()+"\",\"name\": \""+t.getDataOfLabel("DESCRIPCION")+"\"}, ");
                        }
                        out.println("       {\"value\": \"0\",\"name\": \"--\"}");
                        out.println("       ]");
                        out.println("   },");
                        out.println("\"andiazher\": \"andiazher.com\"");
                        out.println("}");
                    }
                }
                /**
                 * Delete the Parametro de dispersion
                 * @param id
                 * @return true if is delete and false if not is delete
                 */
                if(var.equals("delete")){
                    Entitie conDisp= new Entitie(App.TABLE_CONTROLDIPS);
                    String id="0";
                    try{
                        id = request.getParameter("id");
                        conDisp.getEntitieID(id);
                    }catch(NullPointerException s){s.printStackTrace();}
                    catch(IndexOutOfBoundsException s){s.printStackTrace();}
                    try (PrintWriter out = response.getWriter()) {
                        if(conDisp.delete()){
                            out.println("<script type=\"text/javascript\">\n"
                                + "swal(\n" +
                            "'Borrado!',\n" +
                            "'El registro con ID="+id+" ha sido borrado',\n" +
                            "'success'\n" +
                            ")\n"
                            + "</script>");
                        }else{
                            out.println("<script type=\"text/javascript\">\n"
                                +" swal(\n" +
                            "'Error!',\n" +
                            "'El registro con ID="+id+" NO ha sido borrado',\n" +
                            "'error'\n" +
                            ")\n"
                            + "</script>");
                        }
                        
                    }
                }
            }
            
            else{
                /**
                 * En caso de ser invalida la session se envia a la pagina de login
                 */
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        } catch(NullPointerException a){
            a.printStackTrace();
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
            Logger.getLogger(FormParametrosDispersionV.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FormParametrosDispersionV.class.getName()).log(Level.SEVERE, null, ex);
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
