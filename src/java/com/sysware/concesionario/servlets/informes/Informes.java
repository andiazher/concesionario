/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.informes;

import com.sysware.concesionario.app.App;
import com.sysware.concesionario.entitie.Entitie;
import com.sysware.concesionario.services.XLSFile;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;


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
                    Entitie orden = new Entitie(App.TABLE_OS);
                    Entitie osdetalle = new Entitie(App.TABLE_DOS);
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
                            osdetalle.getEntitieID(i.getDataOfLabel("DOS"));
                            out.println("<td>"+i.getDataOfLabel("FECHA")+"</td>");
                            servicio.getEntitieID(osdetalle.getDataOfLabel("SERVICIO"));
                            out.println("<td>"+servicio.getDataOfLabel("DESCRIPCION")+"/"+i.getDataOfLabel("RUBRO")+"</td>");
                            DecimalFormat formateador = new DecimalFormat("###,###.##");
                            out.println("<td class=\"text-right\">$"+formateador.format(Integer.parseInt(i.getDataOfLabel("VALORBASE")))+"</td>");
                            String ss= "";
                            if(i.getDataOfLabel("TIPO").equals("1")){
                                String valor = i.getDataOfLabel("VALORPROG");
                                if(valor.equals("0")){
                                    int vbase = Integer.parseInt(i.getDataOfLabel("VALORBASE"));
                                    int vcal = Integer.parseInt(i.getDataOfLabel("VALORCALCUL"));
                                    int vpor = (vcal*100)/vbase;
                                    valor= vpor+"";
                                }
                                ss="("+valor+"%)";
                            }
                            //out.println("<td class=\"text-right\">"+ss+"</td>");
                            out.println("<td class=\"text-right\">"+ss+"<b>$"+formateador.format(Integer.parseInt(i.getDataOfLabel("VALORCALCUL")))+"</b></td>");
                            if(i.getDataOfLabel("PRENT").equals("0")){
                                out.println("<td class=\"text-center\">--</td>");
                            }
                            else{
                                out.println("<td class=\"text-right\">("+
                                    i.getDataOfLabel("PRENT")+"%)$"+formateador.format(Integer.parseInt(i.getDataOfLabel("VRENT")))+"</td>");
                            }
                            
                            if(i.getDataOfLabel("PIMP").equals("0")){
                                out.println("<td class=\"text-center\">--</td>");
                            }else{
                                out.println("<td class=\"text-right\">("+
                                    i.getDataOfLabel("PIMP")+"%)$"+formateador.format(Integer.parseInt(i.getDataOfLabel("VIMP")))+"</td>");
                            }
                            out.println("</tr>");
                        }
                    }
                }
                if(entidad.contains("matricula")){
                    Entitie registroR = new Entitie(App.TABLE_REGISTROMATRICULA);
                    ArrayList<Entitie> resgistros = registroR.getEntities();
                    
                    //FILE
                    ArrayList<String> names=new ArrayList<>();
                    names.add("Id");
                    names.add("Factura");
                    names.add("Fecha");
                    names.add("Propietario");
                    names.add("Placa");
                    names.add("Clase");
                    names.add("Gestoria");
                    names.add("Matricula");
                    names.add("Runt");
                    names.add("Papelera");
                    names.add("Mensajeria");
                    names.add("Impuestos");
                    names.add("Otros");
                    names.add("Retefuente");
                    names.add("Honorarios");
                    names.add("Total");
                    names.add("Saldo");
                    
                    XLSFile file = new XLSFile(request.getSession().getServletContext().getRealPath("/attach")+"/matriculas");
                    file.createWorksheet("MATRICULAS");
                    file.generateInformXLS( names , "INFORME DE MATRICULAS");
                    
                    try (PrintWriter out = response.getWriter()) {
                        int matricula = 0;
                        int runt = 0;
                        int papeleria = 0;
                        int mensajeria = 0;
                        int impuestos = 0;
                        int otros = 0;
                        int retefuente = 0;
                        int honorario = 0;
                        int total = 0;
                        for(Entitie i: resgistros){
                            out.println("<tr>");
                            file.createRow();
                            
                            out.println("<td>"+i.getId()+"</td>");
                            file.createCell();
                            file.getCell().setCellValue(Double.parseDouble(i.getId()));
                            
                            out.println("<td>"+i.getDataOfLabel("FACTURA")+"</td>");
                            file.createCell();
                            file.getCell().setCellValue(i.getDataOfLabel("FACTURA"));
                            
                            out.println("<td>"+i.getDataOfLabel("FECHA")+"</td>");
                            file.createCell();
                            file.getCell().setCellValue(i.getDataOfLabel("FECHA"));
                            
                            out.println("<td>"+i.getDataOfLabel("PROPIETARIO")+"</td>");
                            file.createCell();
                            file.getCell().setCellValue(i.getDataOfLabel("PROPIETARIO"));
                            
                            out.println("<td>"+i.getDataOfLabel("PLACA")+"</td>");
                            file.createCell();
                            file.getCell().setCellValue(i.getDataOfLabel("PLACA"));
                            
                            out.println("<td>"+i.getDataOfLabel("CLASE")+"</td>");
                            file.createCell();
                            file.getCell().setCellValue(i.getDataOfLabel("CLASE"));
                            
                            out.println("<td>"+i.getDataOfLabel("GESTORIA")+"</td>");
                            file.createCell();
                            file.getCell().setCellValue(i.getDataOfLabel("GESTORIA"));
                            
                            matricula+=Integer.parseInt(i.getDataOfLabel("MATRICULA"));
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("MATRICULA")));
                            
                            runt+=Integer.parseInt(i.getDataOfLabel("RUNT"));
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("RUNT")));
                            
                            papeleria+=Integer.parseInt(i.getDataOfLabel("PAPELERIA"));
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("PAPELERIA")));
                            
                            mensajeria+=Integer.parseInt(i.getDataOfLabel("MENSAJERIA"));
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("MENSAJERIA")));
                            
                            impuestos+=Integer.parseInt(i.getDataOfLabel("IMPUESTOS"));
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("IMPUESTOS")));
                            
                            otros+=Integer.parseInt(i.getDataOfLabel("OTROS"));
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("OTROS")));
                            
                            retefuente+=Integer.parseInt(i.getDataOfLabel("RETEFUENTE"));
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("RETEFUENTE")));
                            
                            honorario+=Integer.parseInt(i.getDataOfLabel("HONORARIO"));
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("HONORARIO")));
                            
                            total+=Integer.parseInt(i.getDataOfLabel("TOTAL"));
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("TOTAL")));
                            
                            file.createCell();
                            file.getCell().setCellValue(Integer.parseInt(i.getDataOfLabel("SALDO")));
                            
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
                        // TOTAL
                        out.println("<tr>");
                        file.createRow();
                        int ri= file.getCell().getRowIndex()+1;
                        file.createCell();
                        file.getCell().setCellValue("TOTAL");
                        file.addCellStyleTo();
                        file.getCell().getRow().getSheet().addMergedRegion(new CellRangeAddress(ri,ri,0,6));
                        file.createCell();file.createCell();file.createCell();file.createCell();file.createCell();
                        file.createCell();
                        file.restartStyle();
                        file.getCellStyle().setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
                        file.getCellStyle().setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        
                        out.println("<th colspan=\"7\" class=\"text-center \">TOTAL</th>");
                            
                        DecimalFormat formateador = new DecimalFormat("###,###.##");
                        out.println("<th class=\"text-right\">$"+formateador.format(matricula)+"</th>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue(matricula);
                        
                        out.println("<th class=\"text-right\">$"+formateador.format(runt)+"</th>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue(runt);
                        
                        out.println("<th class=\"text-right\">$"+formateador.format(papeleria)+"</th>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue(papeleria);
                        
                        out.println("<th class=\"text-right\">$"+formateador.format(mensajeria)+"</th>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue(mensajeria);
                        
                        out.println("<th class=\"text-right\">$"+formateador.format(impuestos)+"</th>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue(impuestos);
                        
                        out.println("<th class=\"text-right\">$"+formateador.format(otros)+"</th>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue(otros);
                        
                        out.println("<th class=\"text-right\">$"+formateador.format(retefuente)+"</th>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue(retefuente);
                        
                        out.println("<th class=\"text-right\">$"+formateador.format(honorario)+"</th>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue(honorario);
                        
                        out.println("<th class=\"text-right\">$"+formateador.format(total)+"</th>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue(total);
                        
                        out.println("<th class=\"text-right\">--</td>");
                        file.createCell(); file.addCellStyleTo();
                        file.getCell().setCellValue("--");
                        
                        out.println("</tr>");
                        
                    }
                    file.closeReport();
                    /**
                    try (FileOutputStream fileOut = new FileOutputStream( request.getSession().getServletContext().getRealPath("/attach")+"/matriculas.xls")) {
                        HSSFWorkbook workbook = new HSSFWorkbook();
                        HSSFSheet worksheet = workbook.createSheet("MATRICULAS");
                        
                        //TITULO
                        
                        HSSFRow row1 = worksheet.createRow((short) 0);
                        HSSFCell cellA1 = row1.createCell((short) 0);
                        cellA1.setCellValue("Hello");
                        HSSFCellStyle cellStyle = workbook.createCellStyle();
                        cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
                        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        cellA1.setCellStyle(cellStyle);
                        
                        HSSFCell cellB1 = row1.createCell((short) 1);
                        cellB1.setCellValue("Goodbye");
                        cellStyle = workbook.createCellStyle();
                        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
                        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        cellB1.setCellStyle(cellStyle);
                        
                        HSSFCell cellC1 = row1.createCell((short) 2);
                        cellC1.setCellValue(true);
                        
                        HSSFCell cellD1 = row1.createCell((short) 3);
                        cellD1.setCellValue(new Date());
                        cellStyle = workbook.createCellStyle();
                        cellStyle.setDataFormat(HSSFDataFormat
                                .getBuiltinFormat("m/d/yy h:mm"));
                        cellD1.setCellStyle(cellStyle);
                        workbook.write(fileOut);
                        fileOut.flush();
                        System.out.println("Archivo creado");
                    }
                    */
                }
                if(entidad.contains("movimientos")){
                    Entitie registroR = new Entitie(App.TABLE_REGMOVBOLSA);
                    String concesionario="";
                    ArrayList<Entitie> resgistros = new ArrayList<>();
                    try{
                        concesionario= request.getParameter("concesionario");
                    }catch(NullPointerException s){
                        System.out.println("Error: "+s);
                    }
                    if(!concesionario.equals("")){
                        resgistros = registroR.getEntitieParam("CONCESIONARIO", concesionario);
                    }
                    else{
                        //resgistros = registroR.getEntities();
                    }
                    try (PrintWriter out = response.getWriter()) {
                        for(Entitie i: resgistros){
                            out.println("<tr>");
                            out.println("<td>"+i.getDataOfLabel("FECHA")+"</td>");
                            if(i.getDataOfLabel("OS").equals("0")){
                                out.println("<td>--</td>");
                            }
                            else{
                                out.println("<td>"+i.getDataOfLabel("OS")+"</td>");
                            }
                            
                            if(i.getDataOfLabel("SERVICIO").equals("0")){
                                out.println("<td>--</td>");
                            }
                            else{
                                Entitie servicio = new Entitie(App.TABLE_SERVICIOS);
                                servicio.getEntitieID(i.getDataOfLabel("SERVICIO"));
                                out.println("<td>"+servicio.getDataOfLabel("DESCRIPCION")+"</td>");
                            }
                            DecimalFormat formateador = new DecimalFormat("###,###.##");
                            int valor = Integer.parseInt(i.getDataOfLabel("VALOR"));
                            String ss="";
                            if(valor < 0){
                                ss="text-danger";
                            }
                            out.println("<td class=\"text-right "+ss+" \">$"+formateador.format(valor)+"</td>");
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
