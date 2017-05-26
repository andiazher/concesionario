/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.core;

import com.sysware.concesionario.app.App;
import com.sysware.concesionario.entitie.Entitie;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author andre
 */
public class Mail {
    
    private static String MAIL="andres.diaz@sysware-ingenieria.com";
    private static String PASS="sysware2017";
    
    private String subject;
    private String contend;
    private String recipient;

    public Mail() {
        
    }
    
    
    
    public boolean send(HttpServletRequest request , Entitie asd){
        try{
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable","true");
            p.setProperty("mail.smtp.port","587");
            p.setProperty("mail.smtp.auth","true");
            
            Session session = Session.getInstance(p, null);
            BodyPart texto=new MimeBodyPart();
            texto.setText(contend);
            
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(
                    request.getSession().getServletContext().getRealPath("/attach")+"/POL "+asd.getDataOfLabel("POLIZA")+".pdf"
            )));
            adjunto.setFileName("POLIZA "+asd.getDataOfLabel("POLIZA")+".pdf");

            MimeMultipart mimeMultipart= new MimeMultipart();

            mimeMultipart.addBodyPart(texto); //TEXTO DE MENSAGE
            mimeMultipart.addBodyPart(adjunto);
            
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom();
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            //msg.setText(contend);
            msg.setContent(mimeMultipart, "text/html");
            
            Transport t =  session.getTransport("smtp");
            //System.out.println("user: "+App.getMailStaticParams().getMAIL()+" pass: "
            //        +App.getMailStaticParams().getPASS());
            t.connect(App.getMailStaticParams().getMAIL(),App.getMailStaticParams().getPASS());
            t.sendMessage(msg,msg.getAllRecipients());
            t.close();
            return true;
            
        }catch(MessagingException e){
            e.printStackTrace();
            return false;
        }
        
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContend() {
        return contend;
    }

    public void setContend(String contend) {
        this.contend = contend;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMAIL() {
        return MAIL;
    }

    public void setMAIL(String MAIL) {
        this.MAIL = MAIL;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }
    
    
}
