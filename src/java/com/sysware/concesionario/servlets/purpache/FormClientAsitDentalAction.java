/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.servlets.purpache;

import com.itextpdf.text.DocumentException;
import com.sysware.concesionario.app.App;
import com.sysware.concesionario.core.DispersionValores;
import com.sysware.concesionario.core.Mail;
import com.sysware.concesionario.entitie.Entitie;
import com.sysware.concesionario.services.PDFs;
import com.sysware.concesionario.services.WebServiceAsistenciaDen;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
public class FormClientAsitDentalAction extends HttpServlet {

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
            throws ServletException, IOException, SQLException, FileNotFoundException, DocumentException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            if(request.getSession().getAttribute("session").equals("true")){
                String id="0";
                String ti = null;
                String identifica = null;
                String nombre = null;
                String nombre2 = null;
                String apellido = null;
                String apellido2 = null;
                String fechaNaci = null;
                String plan = null;
                String direccion = null;
                String telefono = null;
                String celular = null;
                String correo = null;
                try{
                    id= request.getParameter("idservicio");
                    ti= request.getParameter("ti").toUpperCase();
                    identifica= request.getParameter("identification").toUpperCase();
                    nombre= request.getParameter("nombre").toUpperCase();
                    nombre2= request.getParameter("nombres2").toUpperCase();
                    apellido= request.getParameter("apellidos").toUpperCase();
                    apellido2= request.getParameter("apellidos2").toUpperCase();
                    fechaNaci= request.getParameter("fecha");
                    plan= request.getParameter("plan");
                    direccion= request.getParameter("direccion").toUpperCase();
                    telefono= request.getParameter("telefono");
                    celular= request.getParameter("celular");
                    correo= request.getParameter("correo").toLowerCase();
                    
                }catch(NullPointerException s){
                    System.out.println("Error: "+s);
                }
                
                Entitie cliente = new Entitie(App.TABLE_CLIENTE);
                ArrayList<Entitie> clientes= cliente.getEntitieParam("CEDULA", identifica);
                if(clientes.isEmpty()){
                    cliente = new Entitie(App.TABLE_CLIENTE);
                    for(String s: cliente.getColums()){
                        cliente.getData().add("");
                    }
                    cliente.getData().set(cliente.getColums().indexOf("CEDULA"), identifica);
                    cliente.getData().set(cliente.getColums().indexOf("TIPODOC"), ti);
                    cliente.getData().set(cliente.getColums().indexOf("NOMBRE"), nombre);
                    cliente.getData().set(cliente.getColums().indexOf("SNOMBRE"), nombre2);
                    cliente.getData().set(cliente.getColums().indexOf("APELLIDO"), apellido);
                    cliente.getData().set(cliente.getColums().indexOf("SAPELLIDO"), apellido2);
                    cliente.getData().set(cliente.getColums().indexOf("DIRECCION"), direccion);
                    cliente.getData().set(cliente.getColums().indexOf("TELEFONO"), telefono);
                    cliente.getData().set(cliente.getColums().indexOf("CELULAR"), celular);
                    cliente.getData().set(cliente.getColums().indexOf("CORREO"), correo);
                    cliente.getData().set(cliente.getColums().indexOf("FNACIMIENTO"), fechaNaci);
                    cliente.getData().set(cliente.getColums().indexOf("CIUDAD"), "0");
                    cliente.getData().set(cliente.getColums().indexOf("PAIS"), "0");
                    cliente.create();
                }
                String idCliente="";
                try{
                    cliente= cliente.getEntitieParam("CEDULA", identifica).get(0);
                    idCliente = cliente.getId();
                    //ACTUALIZCION DE DATOS
                    //cliente.getData().set(cliente.getColums().indexOf("CEDULA"), identifica);
                    //cliente.getData().set(cliente.getColums().indexOf("TIPODOC"), ti);
                    cliente.getData().set(cliente.getColums().indexOf("NOMBRE"), nombre);
                    cliente.getData().set(cliente.getColums().indexOf("SNOMBRE"), nombre2);
                    cliente.getData().set(cliente.getColums().indexOf("APELLIDO"), apellido);
                    cliente.getData().set(cliente.getColums().indexOf("SAPELLIDO"), apellido2);
                    cliente.getData().set(cliente.getColums().indexOf("DIRECCION"), direccion);
                    cliente.getData().set(cliente.getColums().indexOf("TELEFONO"), telefono);
                    cliente.getData().set(cliente.getColums().indexOf("CELULAR"), celular);
                    cliente.getData().set(cliente.getColums().indexOf("CORREO"), correo);
                    cliente.getData().set(cliente.getColums().indexOf("FNACIMIENTO"), fechaNaci);
                    cliente.update();
                    
                }catch(IndexOutOfBoundsException s){
                    System.out.println("Error: "+s);
                }
                //Creacion de la entidad en la base de datos para Asignacion del numero de poliza de forma consecutiva. 
                Entitie asd = creteEntidadASD();
                ArrayList<Entitie> asds= asd.getEntities();
                String idPoliza="";
                for(Entitie e: asds){
                    idPoliza = e.getId();
                }
                
                int last = Integer.parseInt(idPoliza);
                if(last<10){
                    idPoliza="000000"+idPoliza;
                }
                if(last>=10 && last<100){
                    idPoliza="00000"+idPoliza;
                }
                if(last>=100 && last<1000){
                    idPoliza="0000"+idPoliza;
                }
                if(last>=1000 && last<10000){
                    idPoliza="000"+idPoliza;
                }
                if(last>=10000 && last<100000){
                    idPoliza="00"+idPoliza;
                }
                if(last>=100000 && last<1000000){
                    idPoliza="0"+idPoliza;
                }
                asd.getEntitieID(last+"");
                Calendar fecha = new GregorianCalendar();
                String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH);
                Entitie dos = new Entitie(App.TABLE_DOS);
                if(id != null && !"0".equals(id)){
                    dos.getEntitieID(id);
                }
                asd.getData().set(asd.getColums().indexOf("FECHA"),f );
                asd.getData().set(asd.getColums().indexOf("FECHAEXP"), f);
                asd.getData().set(asd.getColums().indexOf("FECHAPAGO"), "1900-01-01");
                asd.getData().set(asd.getColums().indexOf("POLIZA"), "AD"+idPoliza);
                asd.getData().set(asd.getColums().indexOf("PLAN"), plan);
                asd.getData().set(asd.getColums().indexOf("CLIENTE"), idCliente);
                
                String usuario =(String) request.getSession().getAttribute("user");
                Entitie user = new Entitie(App.TABLE_USUARIO);
                user=user.getEntitieParam("USUARIO", usuario).get(0);
                Entitie canal = new Entitie(App.TABLE_CANALES);
                canal.getEntitieID(user.getDataOfLabel("ID_CANAL"));
                
                asd.getData().set(asd.getColums().indexOf("CANAL"), canal.getId());
                
                //asd.update();
                
                //CONSUMO DE WEB SERVICE EN UN AGENTE EXTERIOR
                /**
                 * Dependiendo del estado del consumo del web service 
                 * se podra hacer el registro completo.
                 * Si la transaccion probiene de una orden de servicio por el modulo
                 * de concesionarios, este se debe tramitar el servicio si la transaccion es exitosa. 
                 * Si la transaccion falla, se debe pasar a orden no tramitada. 
                 */
                WebServiceAsistenciaDen wsad = new WebServiceAsistenciaDen();                
                String messagge = wsad.registro(asd, cliente);
                System.out.println("Respuesta del Servicio: "+messagge);
                
                if(messagge.equals("1")){
                    //PASO DEL SERVICIO A TRAMITADO
                    asd.getData().set(asd.getColums().indexOf("ESTADO"), "2");
                    asd.getData().set(asd.getColums().indexOf("ESTADOPOL"), "VIGENTE");
                    
                    //CREACION DE ORDEN DE SERVICIO JUNTO A UNA DETALLE DE ORDEN DE SERVICIO
                    if(dos.getId().equals("0")){
                       Entitie os = new Entitie(App.TABLE_OS);
                       for(String i: os.getColums()){
                           os.getData().add("");
                       }
                       System.out.println("Client2"+os);
                       os.getData().set(os.getColums().indexOf("PROPIETARIO"), cliente.getId());
                       os.getData().set(os.getColums().indexOf("VEHICULO"), "0");
                       os.getData().set(os.getColums().indexOf("FECHA"), f);
                       os.getData().set(os.getColums().indexOf("ID_CANAL"), canal.getId());
                       os.create();
                       ArrayList<Entitie> ordenes = os.getEntitieParam("PROPIETARIO", cliente.getId());
                       String ultimo="";
                       for(Entitie o: ordenes){
                            ultimo= o.getId();
                       }
                       os.getEntitieID(ultimo);
                       for(String i: dos.getColums()){
                           dos.getData().add("");
                       }
                       dos.getData().set(dos.getColums().indexOf("OS"), ultimo);
                       dos.getData().set(dos.getColums().indexOf("SERVICIO"), "3");
                       Entitie param = new Entitie(App.TABLE_PARAMETROSFORMS);
                       param.getEntitieID(asd.getDataOfLabel("PLAN"));
                       dos.getData().set(dos.getColums().indexOf("VALOR"), param.getDataOfLabel("VALUE2"));
                       dos.getData().set(dos.getColums().indexOf("ESTADO"), "2");
                       dos.getData().set(dos.getColums().indexOf("COM_PLATINO"), "0");
                       dos.getData().set(dos.getColums().indexOf("COM_CONCE"), "0");
                       dos.getData().set(dos.getColums().indexOf("LIQUIDACION"), "0");
                       dos.getData().set(dos.getColums().indexOf("ESTADOL"), "0");
                       dos.getData().set(dos.getColums().indexOf("FECHAT"), f);
                       dos.create();
                       String iddos="0";
                       ArrayList<Entitie> doss = dos.getEntitieParam("OS", ultimo);
                       for(Entitie o: doss){
                           iddos=o.getId();
                       }
                       dos.getEntitieID(iddos);
                    }
                    
                    //Configuracion del Rubro;
                    try{    
                        DispersionValores disp= new DispersionValores();
                        Entitie param = new Entitie(App.TABLE_PARAMETROSFORMS);
                        Entitie pd= new Entitie(App.TABLE_CONTROLDIPS);
                        param.getEntitieID(asd.getDataOfLabel("PLAN"));
                        int valor = Integer.parseInt(param.getDataOfLabel("VALUE2"));
                        asd.getData().set(asd.getColums().indexOf("VALORPRIMA"), valor+"");
                        
                        /**
                         * @TITLE = CALCULO DE VALORES BASE.
                         * 
                         * PARAMETROS PARA SABER CUAL DE LOS DOS PLANE SE DEBE ESCOJER
                         * IDS DE DISPERSION DE VALORES (CONTROLDISPERSION) ES FIJO, NO MODIFICAR EN LA BASE DE DATOS
                         * @RESERVADOS LAS FILAS CON 
                         * @ID= 3 HASTA 10
                         *
                         * @PARAM SOAT IS SOAT WITH
                         * @PARAM FONDO IS FONDO
                         * 
                         */
                        String paramPlan=param.getDataOfLabel("VALUE");
                        if(paramPlan.contains("SOAT")){
                            //DISPERSAR AL RUBRO ID=1
                            disp.dispersion(valor,"1", dos);
                            /**
                             * ID'S RESEVARDOS
                             * @ID=3 COSTO DE PLATAFORMA | % | % | - |
                             * @ID=5 COSTO DE PRODUCTO   | % | % | - |
                             * @ID=7 CANAL DE DISTRIBUCI | % | - | - |
                             * @ID=9 ADMIN PLATINOS SEGU | % | - | % |
                             */
                            //1. PLATAFORMA
                            //2. PRODUCTO
                            //3. CANAL
                            pd.getEntitieID("7");
                            int valorCanal = Integer.parseInt( pd.getDataOfLabel("VALOR"));
                            valorCanal = valor-valorCanal;
                            asd.getData().set(asd.getColums().indexOf("PAGOCANAL"), valorCanal+"");
                            //4. ADMIN PLATINOS SEGUROS
                        }
                        if(paramPlan.contains("FONDO")){
                            //DISPERSAR AL RUBRO ID=2
                            disp.dispersion(valor,"2", dos);
                            /**
                             * ID'S RESEVARDOS
                             * @ID=4  COSTO DE PLATAFORMA | % | % | - |
                             * @ID=6  COSTO DE PRODUCTO   | % | % | - |
                             * @ID=8  CANAL DE DISTRIBUCI | % | - | - |
                             * @ID=10 ADMIN PLATINOS SEGU | % | - | % |
                             */
                            //1. PLATAFORMA
                            //2. PRODUCTO
                            //3. CANAL
                            pd.getEntitieID("8");
                            int valorCanal = Integer.parseInt( pd.getDataOfLabel("VALOR"));
                            valorCanal = valor-valorCanal;
                            asd.getData().set(asd.getColums().indexOf("PAGOCANAL"), valorCanal+"");
                            //4. ADMIN PLATINOS SEGUROS
                        }
                        
                    }catch(Exception s){
                        s.printStackTrace();
                    }
                    //END CONFIG RUBROS AND DISPERSION
                    
                    //
                    if(id != null && !"0".equals(id)){
                        try{
                            dos.getData().set(dos.getColums().indexOf("ESTADO"), "2");
                            dos.update();
                        }   
                        catch(IndexOutOfBoundsException s){
                            //System.out.println("Error al actualizar dos: "+s);
                        }
                    }
                    
                    
                    
                    //SEN MAIL TO CLIENT NEW 
                    
                    System.out.println("Sender Mail");
                    Mail mail = new Mail();
                    System.out.println("Create Mail Class End ");
                    String mc= cliente.getDataOfLabel("CORREO");
                    mc = mc.toLowerCase();
                    mc = mc.trim();
                    boolean enviado = false;
                    System.out.println("Correo Electronico: "+mc);
                    if(!mc.isEmpty()  && !mc.equals("") && !mc.equals(" ") && mc.contains("@") && mc.contains(".") && mc!= null){
                        
                        mail.setRecipient(mc);
                        mail.setSubject("POLIZA ASISTENCIA DENTAL AD"+idPoliza);
                        String cadena= cliente.getDataOfLabel("NOMBRE");
                        String nameClient = cadena.substring(0,1).toUpperCase() + cadena.substring(1).toLowerCase();
                        mail.setContend("Hola "+nameClient+". "+
                                 "Su número de poliza de asistencia dental es AD" + idPoliza + ".");
                        //CREATE PDF
                        PDFs ss = new PDFs();
                        ss.createPDFPoliza(request, cliente, asd);
                        //send
                        enviado = mail.send(request, asd);
                        System.out.println("Enviado a: "+mc + " Confirm:"+enviado);
                    }
                    else{
                        System.out.println("No enviado");
                    }
                    try (PrintWriter out = response.getWriter()) {
                        String msg="";
                        if(enviado){
                            msg= "Se le ha enviado una copia de la poliza al correo registrado";
                        }
                        else{
                            msg= "Por favor Guardar este numero de poliza.";
                        }
                        
                        out.println("<script type=\"text/javascript\">\n"
                                + "swal(\n" +
                            "'Guardado!',\n" +
                            "'Numero de Poliza AD"+idPoliza+"; "+msg+"',\n" +
                            "'success'\n" +
                            ")\n"
                            + "</script>");
                    }
                }
                else{
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<script type=\"text/javascript\">\n"
                                +" swal(\n" +
                            "'Error!',\n" +
                            "'Poliza AD"+idPoliza+"; "+messagge+" ',\n" +
                            "'error'\n" +
                            ")\n"
                            + "</script>");
                    }
                    //PASO DEL SERVICIO A NO TRAMITADO.
                    asd.getData().set(asd.getColums().indexOf("ESTADO"), "3");
                    asd.getData().set(asd.getColums().indexOf("ESTADOPOL"), "ANULADA");
                    
                    if(id != null && !"0".equals(id)){
                        try{
                            dos.getData().set(dos.getColums().indexOf("ESTADO"), "3");
                            dos.update();
                        }   
                        catch(IndexOutOfBoundsException s){
                            System.out.println("Error al actualizar dos: "+s);
                        }
                    }
                }
                asd.update();
                
            }
            else{
                response.sendRedirect("login.jsp?validate=Por+favor+ingresar+credenciales");
            }
        }catch(NullPointerException e){
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
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FormClientAsitDentalAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(FormClientAsitDentalAction.class.getName()).log(Level.SEVERE, null, ex);
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

    public Entitie creteEntidadASD() throws SQLException {
        Entitie asd = new Entitie(App.TABLE_ASIS_DENTAL);
        for(String s: asd.getColums()){
            asd.getData().add("0");
        }
        asd.getData().set(asd.getColums().indexOf("FECHA"),"1900-01-01");
        asd.getData().set(asd.getColums().indexOf("FECHAEXP"),"1900-01-01");
        asd.getData().set(asd.getColums().indexOf("FECHAPAGO"),"1900-01-01");
        asd.getData().set(asd.getColums().indexOf("ESTADOPAGO"),"PORPAGAR");
        
        asd.getData().set(asd.getColums().indexOf("POLIZA"),""+(int) (Math.random()*1000000000));
        asd.create();
        return asd;
    }

}
