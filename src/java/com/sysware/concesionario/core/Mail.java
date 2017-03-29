/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.core;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author andre
 */
public class Mail {
    
    private static String MAIL = "andiazher.sysware@gmail.com";
    private static String PASS = "sysware2017";
    
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
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MAIL, PASS);
                }
            };
            Session session = Session.getDefaultInstance(p, authenticator);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(MAIL);
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setText(contend);
            Transport.send(msg);
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
    
    
}
