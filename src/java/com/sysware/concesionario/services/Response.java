/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.services;

/**
 *
 * @author user
 */
public class Response {
    private String msn_error;
    private String value_exitoso;

    public Response(String msn_error, String value_exitoso) {
        this.msn_error = msn_error;
        this.value_exitoso = value_exitoso;
    }

    public String getMsn_error() {
        return msn_error;
    }

    public void setMsn_error(String msn_error) {
        this.msn_error = msn_error;
    }

    public String getValue_exitoso() {
        return value_exitoso;
    }

    public void setValue_exitoso(String value_exitoso) {
        this.value_exitoso = value_exitoso;
    }
    
    
    
}
