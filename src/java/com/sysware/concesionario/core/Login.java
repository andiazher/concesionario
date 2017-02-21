/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.core;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author andre
 */
public class Login {
    
    private String user;
    private String pass;
    
    public static String USER = "USUARIO";
    public static String CLAVE = "CLAVE";
    public static String CANAL = "ID_CANAL";
    public static String TABLA = "USARIOS";

    public Login() {
    }

    public Login(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }
    
    

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.user);
        hash = 73 * hash + Objects.hashCode(this.pass);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Login other = (Login) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.pass, other.pass)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Login{" + "user=" + user + ", pass=" + pass + '}';
    }

    
    
    
}
