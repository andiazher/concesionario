/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.informes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andre
 */
public class DownloadInformeMatriculas extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String fileName = request.getSession().getServletContext().getRealPath("/attach")+"/matriculas.xls";
        String fileType = "xls";
        response.setContentType(fileType);
        Calendar fecha = new GregorianCalendar();
        String mes = (fecha.get(Calendar.MONTH)+1) < 10 ? "0"+(fecha.get(Calendar.MONTH)+1): ""+(fecha.get(Calendar.MONTH)+1);
        String f= fecha.get(Calendar.YEAR)+""+ mes+""+fecha.get(Calendar.DAY_OF_MONTH)+""
               +fecha.get(Calendar.HOUR_OF_DAY)+""+fecha.get(Calendar.MINUTE)+""+fecha.get(Calendar.SECOND);
        response.setHeader("Content-disposition","attachment; filename=INFORME DE MATRICULAS "+f+".xls");
        File my_file = new File(fileName);

         // This should send the file to browser
         OutputStream out = response.getOutputStream();
         FileInputStream in = new FileInputStream(my_file);
         byte[] buffer = new byte[4096];
         int length;
         while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
         }
         in.close();
         out.flush();
        
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
        processRequest(request, response);
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
        processRequest(request, response);
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
