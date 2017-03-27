/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.services;

import com.sysware.concesionario.entitie.Entitie;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author andre
 */
public class WebServiceAsistenciaDen {
    public static final String EXCLUIR="Excluir/ExcluirReg";
    public static final String REGISTRO="Registro/PacienteReg";
    public static final String URL_API="http://www.e-dentalsys.com/AppWS/api/";
    protected static final String ENCODING = "UTF-8"; 

    public WebServiceAsistenciaDen() {
    }
    
    public String registro(Entitie e, Entitie client) throws UnsupportedEncodingException, IOException{
        String messagge="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(URL_API+REGISTRO);
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("llave_poliza", e.getDataOfLabel("POLIZA")));
            nvps.add(new BasicNameValuePair("nombre", client.getDataOfLabel("NOMBRE")));
            nvps.add(new BasicNameValuePair("segundo_nombre", client.getDataOfLabel("SNOMBRE")));
            nvps.add(new BasicNameValuePair("primer_apellido", client.getDataOfLabel("APELLIDO")));
            nvps.add(new BasicNameValuePair("segundo_apellido", client.getDataOfLabel("SAPELLIDO")));
            nvps.add(new BasicNameValuePair("sexo", "M"));
            nvps.add(new BasicNameValuePair("identificacion", client.getDataOfLabel("CEDULA")));
            nvps.add(new BasicNameValuePair("dirección", client.getDataOfLabel("DIRECCION")));
            nvps.add(new BasicNameValuePair("telefono", client.getDataOfLabel("TELEFONO")));
            nvps.add(new BasicNameValuePair("telefono_celular", client.getDataOfLabel("CELULAR")));
            nvps.add(new BasicNameValuePair("email", client.getDataOfLabel("CORREO  ")));
            nvps.add(new BasicNameValuePair("fecha_nacimiento", client.getDataOfLabel("FNACIMIENTO")));
            nvps.add(new BasicNameValuePair("fecha_vigencia", e.getDataOfLabel("FECHAEXP")));
            
            nvps.add(new BasicNameValuePair("serial_suc_ase_id" , "675420DD-34ED-4B14-BFB3-CC1E682358D4"));
            nvps.add(new BasicNameValuePair("serial_suc_cli_id" , "92713D20-ACF9-4AFC-B068-209704A2C4D6"));
            nvps.add(new BasicNameValuePair("serial_prm_id"     , "72FA83DA-F2C8-4DB9-956E-9F41DF6E157F"));
            nvps.add(new BasicNameValuePair("serial_contrato_id", "18B97997-A96D-40A6-987B-E77B2641F6CE"));
            nvps.add(new BasicNameValuePair("serial_emp_id"     , "66117962-8939-4BC0-841A-36A9030FFC8B"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
               
                HttpEntity entity2 = response2.getEntity();
                // do something useful with the response body
                
                String responseBody = responseAsString(response2); 
                
                Response response=bodyResponse(responseBody);

                System.out.println("MErrorLS: "+response.getValue_exitoso());
                System.out.println("ValueCSE: "+response.getMsn_error());
                
                if("1".equals(response.getMsn_error())){
                    messagge= "1";
                }
                else{
                    messagge= response.getValue_exitoso();
                }
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
        return messagge;
    }
    
    public String excluir(String poliza) throws UnsupportedEncodingException, IOException{
        String messagge="";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(URL_API+EXCLUIR);
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("llave_poliza", poliza));
            nvps.add(new BasicNameValuePair("serial_suc_cli_id", "92713D20-ACF9-4AFC-B068-209704A2C4D6"));
            nvps.add(new BasicNameValuePair("serial_prm_id", "72FA83DA-F2C8-4DB9-956E-9F41DF6E157F"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
               
                HttpEntity entity2 = response2.getEntity();
                // do something useful with the response body
                
                String responseBody = responseAsString(response2); 
                
                Response response=bodyResponse(responseBody);

                System.out.println(response.getValue_exitoso());
                System.out.println(response.getMsn_error());
                if("1".equals(response.getMsn_error())){
                    messagge= "1";
                }
                else{
                    messagge= response.getValue_exitoso();
                }
                
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
        return messagge;
    }
    
    protected static String responseAsString(CloseableHttpResponse response) throws IOException { 
        return streamAsString(response.getEntity().getContent()); 
    } 
 
    protected static String streamAsString(InputStream inputStream) throws IOException { 
        StringWriter writer = new StringWriter(); 
        IOUtils.copy(inputStream, writer, ENCODING); 
        return  writer.toString(); 
    } 
    

    private static Response bodyResponse(String responseBody) {
        responseBody=responseBody.replace("\"", "");
        String msn_value="";
        String msn_error="";
                String[] split = responseBody.split(":|,|}|]|\\{|\\[");
                Response response= new Response("","");
                for (int i = 0; i < split.length; i++) {
                    if (split[i].toString().equals("exitoso")) {
                        msn_value = split[i+1].toString();
                         if (!msn_value.equals(1)) {
                            msn_error = split[i-1].toString();  
                        }
                    }
                }
        return new Response(msn_value, msn_error);
    }
    
}
