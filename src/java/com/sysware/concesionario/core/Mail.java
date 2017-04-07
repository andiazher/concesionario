/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.core;

import com.sysware.concesionario.app.App;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author andre
 */
public class Mail {
    
    private static String MAIL;
    private static String PASS;
    
    private String subject;
    private String contend;
    private String recipient;

    public Mail() {
        
    }
    
    
    
    public boolean send(){
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


            MimeMultipart mimeMultipart= new MimeMultipart();

            mimeMultipart.addBodyPart(texto);
            
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom();
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            //msg.setText(contend);
            msg.setContent(contend, "text/html");
            
            Transport t =  session.getTransport("smtp");
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
