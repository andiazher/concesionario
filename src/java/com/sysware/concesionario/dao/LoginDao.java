/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.dao;

import com.sysware.concesionario.app.App;
import com.sysware.concesionario.core.LoginEntitie;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author andre
 */
public class LoginDao {

    public static LoginEntitie getLogin(String usuario, String pass) throws SQLException {
        ResultSet resultado=App.consultar("Select USUARIO, CLAVE from USUARIOS where USUARIO='"+usuario+"'");
        LoginEntitie login = new LoginEntitie();
        while(resultado.next()){
            login.setUser(resultado.getString("USUARIO"));
            login.setPass(resultado.getString("CLAVE"));
        }
        resultado.close();
        return login;
    }

}
