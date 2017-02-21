/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.dao;

import com.sysware.concesionario.App;
import com.sysware.concesionario.core.Login;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author andre
 */
public class LoginDao {

    public static Login getLogin(String usuario, String pass) throws SQLException {
        ResultSet resultado=App.consultar("Select USUARIO, CLAVE from USUARIOS where USUARIO='"+usuario+"'");
        Login login = new Login();
        while(resultado.next()){
            login.setUser(resultado.getString("USUARIO"));
            login.setPass(resultado.getString("CLAVE"));
        }
        resultado.close();
        return login;
    }

}
