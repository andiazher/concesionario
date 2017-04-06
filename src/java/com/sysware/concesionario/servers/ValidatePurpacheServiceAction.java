/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servers;

import com.sysware.concesionario.app.App;
import com.sysware.concesionario.core.DispersionValores;
import com.sysware.concesionario.entitie.Entitie;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
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
public class ValidatePurpacheServiceAction extends HttpServlet {

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
                Entitie orden = new Entitie(App.TABLE_OS);
                Entitie dos = new Entitie(App.TABLE_DOS);
                String estado="1";
                String numeroPoliza="0000000000000";
                String observaciones="";
                String valor="";
                String optionsave= "";
                try{
                    dos.getEntitieID(request.getParameter("id"));
                    valor = request.getParameter("valor");
                    orden.getEntitieID(dos.getDataOfLabel("OS"));
                    estado = request.getParameter("var");
                    numeroPoliza = request.getParameter("soat").toUpperCase();
                    observaciones = request.getParameter("obser").toUpperCase();
                    optionsave= request.getParameter("optionsave");
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                try (PrintWriter out = response.getWriter()) {
                    try{
                        if(optionsave.equals("on")){
                            estado="0";
                        }
                    }catch(NullPointerException s){}
                    
                    //start transacction
                    saveDataPerOnly(orden, request, estado);
                    if(estado.equals("2")){
                        String mensaje="Se ha tramitado el servicio";
                        try{
                            if(dos.getDataOfLabel("SERVICIO").equals("1")){
                                valor = request.getParameter("valor");
                                dos.getData().set(dos.getColums().indexOf("VALOR"), valor);
                                //SAVE TO COMISIONES
                                //1. SAVE TO COMISION PLATINO
                                Entitie soat= new Entitie(App.TABLE_SOAT);
                                String tarifa= request.getParameter("tiposoate");
                                soat.getEntitieID(tarifa);
                                String prima = soat.getDataOfLabel("PRIMA");
                                Entitie param = new Entitie(App.TABLE_PARAMETROSFORMS);
                                String pct_soat_platino="0";
                                int c= 0;
                                int s= 0;
                                int valorcomplatino=0;
                                try{
                                    param = param.getEntitieParam("FORM", "SOAT_PLATINO").get(0);
                                    pct_soat_platino = param.getDataOfLabel("VALUE");
                                    for(Entitie entidad : param.getEntitieParam("FORM", "REGIMEN")){
                                        String value=entidad.getDataOfLabel("VALUE");
                                        if (value.equals("C")) {
                                            c= Integer.parseInt(entidad.getDataOfLabel("VALUE2"));
                                        }
                                        if (value.equals("S")) {
                                            s= Integer.parseInt(entidad.getDataOfLabel("VALUE2"));
                                        }
                                    }
                                    int tem1= Integer.parseInt(prima);
                                    int tem2= Integer.parseInt(pct_soat_platino);
                                    valorcomplatino = (tem1*tem2)/100;
                                }
                                catch(IndexOutOfBoundsException error){
                                    
                                };
                                Entitie canal = new Entitie(App.TABLE_CANALES);
                                canal.getEntitieID(orden.getDataOfLabel("ID_CANAL"));
                                int pct_soat_conce= Integer.parseInt(canal.getDataOfLabel("PCT_SOAT"));
                                String regimen = canal.getDataOfLabel("REGIMEN");
                                int valorconcesionario=0;
                                if(regimen.equals("A")){
                                    valorconcesionario = (pct_soat_conce*valorcomplatino)/100;
                                }
                                if(regimen.equals("C")){
                                    valorconcesionario = ((pct_soat_conce*valorcomplatino)/100)*((100-c)/100);
                                }
                                if(regimen.equals("S")){
                                    valorconcesionario = ((pct_soat_conce*valorcomplatino)/100)*((100-s)/100);
                                }
                                dos.getData().set(dos.getColums().indexOf("COM_PLATINO"), valorcomplatino+"");
                                dos.getData().set(dos.getColums().indexOf("COM_CONCE"), valorconcesionario+"");
                                
                                //END 1
                                //OTROS DATOS DE SOAT
                                Entitie aseguradora = new Entitie(App.TABLE_ASEGURADORAS);
                                aseguradora.getEntitieID(request.getParameter("aseguradora"));
                                observaciones+=" NUMERO DE POLIZA: "+numeroPoliza+" DE "+aseguradora.getDataOfLabel("DESCRIPCION");
                                
                                //GUARDAR REGISTRO DE SOAT O POLIZA DE VEHICULO
                                Entitie registro = new Entitie(App.TABLE_REGISTROSOAT);
                                for(String ss: registro.getColums()){
                                    registro.getData().add("");
                                }
                                Calendar fecha = new GregorianCalendar();
                                String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH);
                                Entitie ve = new Entitie(App.TABLE_VEHICULO);
                                ve.getEntitieID(orden.getDataOfLabel("VEHICULO"));
                                Entitie p = new Entitie(App.TABLE_CLIENTE);
                                p.getEntitieID(ve.getDataOfLabel("PROPIETARIO"));
                                
                                
                                registro.getData().set(registro.getColums().indexOf("DOSID"), dos.getId());
                                registro.getData().set(registro.getColums().indexOf("FECHAR"), f);
                                registro.getData().set(registro.getColums().indexOf("POLIZA"), numeroPoliza);
                                registro.getData().set(registro.getColums().indexOf("ASEGURADORA"), aseguradora.getId());
                                registro.getData().set(registro.getColums().indexOf("CLIENTE"), p.getId());
                                registro.getData().set(registro.getColums().indexOf("VEHICULO"), ve.getId());
                                registro.getData().set(registro.getColums().indexOf("VALOR"), valor);
                                registro.getData().set(registro.getColums().indexOf("PRIMA"), prima);
                                registro.getData().set(registro.getColums().indexOf("RUNT"), soat.getDataOfLabel("RUNT"));
                                registro.getData().set(registro.getColums().indexOf("CONTRIBUCION"), soat.getDataOfLabel("CONTRIBUCION"));
                                registro.getData().set(registro.getColums().indexOf("ESTADOP"), "VIGENTE");
                                registro.create();
                                
                                mensaje= "El servicio ha sido tramitado con número de poliza "+numeroPoliza;
                            }
                            if(dos.getDataOfLabel("SERVICIO").equals("2")){
                                valor = request.getParameter("valor");
                                String factura=request.getParameter("factura");
                                Entitie gestoria = new Entitie(App.TABLE_GESTORIASMATR);
                                gestoria.getEntitieID(request.getParameter("gestoria"));
                                System.out.println("Factura: "+factura);
                                System.out.println("ID Gestoria: "+request.getParameter("gestoria"));
                                System.out.println("Gestoria "+gestoria);
                                dos.getData().set(dos.getColums().indexOf("VALOR"), valor);
                                observaciones+="<b>FACTURA No:</b>"+factura+" <b>TRAMITE:</b> "+gestoria.getDataOfLabel("DESCRIPCION");
                                DecimalFormat formateador = new DecimalFormat("###,###.##");
                                mensaje="El servicio has sido tramitado por valor de $ "+formateador.format(Integer.parseInt(valor));
                                Entitie r = new Entitie(App.TABLE_REGISTROMATRICULA);
                                for(String s :r.getColums() ){
                                    r.getData().add("0");
                                }
                                Entitie ve = new Entitie(App.TABLE_VEHICULO);
                                ve.getEntitieID(orden.getDataOfLabel("VEHICULO"));
                                Entitie p = new Entitie(App.TABLE_CLIENTE);
                                p.getEntitieID(ve.getDataOfLabel("PROPIETARIO"));
                                String pro = p.getDataOfLabel("NOMBRE")+" "+p.getDataOfLabel("SNOMBRE")+" "+p.getDataOfLabel("APELLIDO");
                                Entitie clase2 = new Entitie(App.TABLE_CLASEVEHI);
                                clase2.getEntitieID(ve.getDataOfLabel("CLASE"));
                                
                                Entitie concesionario = new Entitie(App.TABLE_CONCESIONARIO);
                                Entitie canal = new Entitie(App.TABLE_CANALES);
                                canal.getEntitieID(orden.getDataOfLabel("ID_CANAL"));
                                concesionario.getEntitieID(canal.getDataOfLabel("ID_CONCESIONARIO"));
                                int saldo = Integer.parseInt(concesionario.getDataOfLabel("SALDO"));
                                int valors = Integer.parseInt(valor);
                                int nuevo = saldo - valors;
                                        
                                r.getData().set(r.getColums().indexOf("FACTURA"), factura);
                                r.getData().set(r.getColums().indexOf("FECHA"), request.getParameter("fecha"));
                                r.getData().set(r.getColums().indexOf("PROPIETARIO"),pro);
                                r.getData().set(r.getColums().indexOf("PLACA"), ve.getDataOfLabel("PLACA"));
                                r.getData().set(r.getColums().indexOf("CLASE"), clase2.getDataOfLabel("DESCRIPCION"));
                                r.getData().set(r.getColums().indexOf("GESTORIA"), gestoria.getDataOfLabel("DESCRIPCION"));
                                r.getData().set(r.getColums().indexOf("MATRICULA"),request.getParameter("matricula"));
                                r.getData().set(r.getColums().indexOf("RUNT"),request.getParameter("runt"));
                                r.getData().set(r.getColums().indexOf("PAPELERIA"),request.getParameter("papelerias"));
                                r.getData().set(r.getColums().indexOf("MENSAJERIA"),request.getParameter("mensajeria"));
                                r.getData().set(r.getColums().indexOf("IMPUESTOS"),request.getParameter("impuesto"));
                                r.getData().set(r.getColums().indexOf("OTROS"),request.getParameter("otros"));
                                r.getData().set(r.getColums().indexOf("RETEFUENTE"),request.getParameter("retefuente"));
                                r.getData().set(r.getColums().indexOf("HONORARIO"),request.getParameter("honorarios"));
                                r.getData().set(r.getColums().indexOf("TOTAL"),valor);
                                r.getData().set(r.getColums().indexOf("SALDO"), nuevo+"");
                                r.create();
                            }
                        }
                        catch(IndexOutOfBoundsException s){
                            System.out.println("Error2 "+s);
                            estado="1";
                            s.printStackTrace();
                        }
                        //UPDATE ESTATE WITH BASIC VALUES
                        dos.getData().set(dos.getColums().indexOf("ESTADO"), estado);
                        //UPDATE REGISTER IN ORDEN DE DETALLE
                        Calendar fecha = new GregorianCalendar();
                        String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)+
                            " "+fecha.get(Calendar.HOUR_OF_DAY)+":"+fecha.get(Calendar.MINUTE)+":"+fecha.get(Calendar.SECOND);
                        dos.getData().set(dos.getColums().indexOf("FECHAT"), f);
                        dos.getData().set(dos.getColums().indexOf("ESTADOL"), "PENDIENTE");
                        //UPDATE ANOTHER VALUES 
                        dos.getData().set(dos.getColums().indexOf("OBSERVACIONES"), observaciones);
                        dos.update();
                        
                        //BOLSA VALOR  Y DISPERSION DE VALORES DE ACUERDO A LOS RUBROS ACORDADOS
                        if(!estado.equals("1")){
                            //ACTUALIZCON DEL VALOR DE LA BOLSA DE BALORES PARA EL CONCESIONARIO, 
                            //SI EL REGISTRO ES POR CONCESIONARIO.
                            Entitie concesionario = new Entitie(App.TABLE_CONCESIONARIO);
                            Entitie canal = new Entitie(App.TABLE_CANALES);
                            
                            canal.getEntitieID(orden.getDataOfLabel("ID_CANAL"));
                            concesionario.getEntitieID(canal.getDataOfLabel("ID_CONCESIONARIO"));
                            int saldo = Integer.parseInt(concesionario.getDataOfLabel("SALDO"));
                            int valors=0;
                            try{
                                valor.trim();
                                valors = Integer.parseInt(valor);
                                valors = valors * -1;
                            }catch(NumberFormatException s){
                                System.out.println("Numero invalido = "+valor);
                                s.printStackTrace();
                            }
                            int nuevo = saldo + valors;
                            concesionario.getData().set(concesionario.getColums().indexOf("SALDO"), nuevo+"");
                            concesionario.update();
                            
                            
                            //CREATE REGISTER MOVIMEINTO DE VOLSA, PROCEDURE INVALIDE. 
                            Entitie reg= new Entitie(App.TABLE_REGMOVBOLSA);
                            for(String s: reg.getColums()){
                                reg.getData().add("");
                            }
                            reg.getData().set(reg.getColums().indexOf("CONCESIONARIO"), concesionario.getId());
                            reg.getData().set(reg.getColums().indexOf("SERVICIO"), dos.getDataOfLabel("SERVICIO"));
                            reg.getData().set(reg.getColums().indexOf("OS"), dos.getDataOfLabel("OS"));
                            reg.getData().set(reg.getColums().indexOf("FECHA"),f);
                            reg.getData().set(reg.getColums().indexOf("TIPOMOV"),"DES");
                            reg.getData().set(reg.getColums().indexOf("VALOR"),(valors)+"");
                            reg.getData().set(reg.getColums().indexOf("SALDO"),nuevo+"");
                            reg.create();
                            
                            /**
                            *CONTROL DE DISPERSION DE VALORES DE ACUERDO A LOS PARAMETROS DE CONTRO DE DISPERSION Y LOS 
                            * RUBROS POR CADA UNO DE LOS SERVICIOS.
                            */
                            new DispersionValores().dispersion(valors*(-1), dos);
                            
                            out.println("<script type=\"text/javascript\">\n" +
                            "    swal(\n" +
                            "        'Guardado!',\n" +
                            "        '"+mensaje+"',\n" +
                            "        'success'\n" +
                            "    )\n" +
                            "</script>");
                            
                        }//END EQUALS IF STATE IS DIF TO 1
                        else{
                            out.println("<script type=\"text/javascript\">\n" +
                            "    swal(\n" +
                            "        'Error!',\n" +
                            "        'Algo ha salido mal',\n" +
                            "        'error'\n" +
                            "    )\n" +
                            "</script>");
                            System.out.println("Error3 :Regresado al estado 1");
                        }
                        
                        
                    }
                    if(estado.equals("3")){
                        dos.getData().set(dos.getColums().indexOf("ESTADO"), estado);
                        dos.getData().set(dos.getColums().indexOf("OBSERVACIONES"), observaciones);
                        dos.update();
                        out.println("<script type=\"text/javascript\">\n" +
                            "    swal(\n" +
                            "        'Guardado',\n" +
                            "        'Servicio no se tramito',\n" +
                            "        'success'\n" +
                            "    )\n" +
                            "</script>");
                    }
                    if(estado.equals("1")){
                        out.println("<script type=\"text/javascript\">\n" +
                            "    swal(\n" +
                            "        'Error!',\n" +
                            "        'Algo ha salido mal',\n" +
                            "        'error'\n" +
                            "    )\n" +
                            "</script>");
                        System.out.println("Error4: Ninguno de los dos estados predeterminados");
                    }
                    if(estado.equals("0")){
                        out.println("<script type=\"text/javascript\">\n" +
                            "    swal(\n" +
                            "        'Guardado',\n" +
                            "        'Los datos han sido guardados, pero no se modifico el estado del servicio',\n" +
                            "        'success'\n" +
                            "    )\n" +
                            "</script>");
                        System.out.println("Error4: Ninguno de los dos estados predeterminados");
                    }
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
            Logger.getLogger(ValidatePurpacheServiceAction.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ValidatePurpacheServiceAction.class.getName()).log(Level.SEVERE, null, ex);
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

    private boolean isviable(ArrayList<Entitie> cdis, String valor) {
        int valor2=0;
        int valor1 = Integer.parseInt(valor);
        int P99 =(int) (valor1*(0.999));
        int P101 = (int) (valor1*(0.001));
        for(Entitie i: cdis){
            if(i.getDataOfLabel("TIPO").equals("1")){
                valor2+=(Integer.parseInt(i.getDataOfLabel("VALOR"))/100)*valor1;
            }
            if(i.getDataOfLabel("TIPO").equals("1")){
                valor2+=Integer.parseInt(i.getDataOfLabel("VALOR"));
            }
        }
        if(valor2>=P99  && valor2 <=P101 ){
            return true;
        }
        return false;
    }

    private void saveDataPerOnly(Entitie orden, HttpServletRequest request, String estado) throws SQLException {
        try{
            Entitie ve = new Entitie(App.TABLE_VEHICULO);
            ve.getEntitieID(orden.getDataOfLabel("VEHICULO"));
            ve.getData().set(ve.getColums().indexOf("TIPO"), request.getParameter("tipo"));
            ve.getData().set(ve.getColums().indexOf("CLASE"), request.getParameter("clase"));
            ve.getData().set(ve.getColums().indexOf("MARCA"), request.getParameter("marca"));
            ve.getData().set(ve.getColums().indexOf("MODELO"), request.getParameter("modelo"));
            ve.getData().set(ve.getColums().indexOf("COLOR"), request.getParameter("color").toUpperCase());
            ve.getData().set(ve.getColums().indexOf("CILINDRAJE"), request.getParameter("cilindraje"));
            ve.getData().set(ve.getColums().indexOf("SERVICIO"), request.getParameter("servicio"));
            ve.getData().set(ve.getColums().indexOf("NO_MOTOR"), request.getParameter("motor").toUpperCase());
            ve.getData().set(ve.getColums().indexOf("NO_CHASIS"), request.getParameter("chasis").toUpperCase());
            ve.getData().set(ve.getColums().indexOf("NO_VIN"), request.getParameter("vin").toUpperCase());
            Entitie p = new Entitie(App.TABLE_CLIENTE);
            p.getEntitieID(ve.getDataOfLabel("PROPIETARIO"));
            p.getData().set(p.getColums().indexOf("CEDULA"),request.getParameter("cedula").toUpperCase());
            p.getData().set(p.getColums().indexOf("NOMBRE"),request.getParameter("nombre").toUpperCase());
            p.getData().set(p.getColums().indexOf("SNOMBRE"),request.getParameter("snombre").toUpperCase());
            p.getData().set(p.getColums().indexOf("APELLIDO"),request.getParameter("apellido").toUpperCase());
            p.getData().set(p.getColums().indexOf("SAPELLIDO"),request.getParameter("sapellido").toUpperCase());
            p.getData().set(p.getColums().indexOf("DIRECCION"),request.getParameter("direccion").toUpperCase());
            p.getData().set(p.getColums().indexOf("CELULAR"),request.getParameter("celular"));
            p.getData().set(p.getColums().indexOf("CORREO"),request.getParameter("correo").toLowerCase());
            p.getData().set(p.getColums().indexOf("TELEFONO"),request.getParameter("telefono"));
            ve.getData().set(ve.getColums().indexOf("PROPIETARIO"), p.getId());
            System.out.println(p);
            //UPDATE DATA OF PERSON AND VEHICLE
            p.update();
            ve.update();
        }catch(IndexOutOfBoundsException s){
            System.out.println("Error1: "+s);
            estado="1";
        }
    }

}
