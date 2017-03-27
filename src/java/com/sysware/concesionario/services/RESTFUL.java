/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
 * @author luis
 */
public class RESTFUL {
    public static final String microservice_uri="Excluir/ExcluirReg";
    public static final String URL_API="http://www.e-dentalsys.com/AppWS/api/"+microservice_uri;
    

    /**
     * @param args the command line arguments
     */
    protected static final String ENCODING = "UTF-8"; 
     public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
           
            HttpPost httpPost = new HttpPost(URL_API);
            
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("llave_poliza", "8"));
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
               
                
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
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
