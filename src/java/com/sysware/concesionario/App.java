/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario;

import com.sysware.concesionario.core.Login;
import com.sysware.concesionario.db.ConnectionMysql;
import com.sysware.concesionario.dao.LoginDao;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author andre
 */
public class App {
    
    private static ConnectionMysql connectionMysql = new ConnectionMysql();
    public static String TABLE_USUARIO="usuarios";
    public static String TABLE_CANALES="canales";
    public static String TABLE_ROL_MENU="rol_menu";
    public static String TABLE_MENUS="menus";
    public static String TABLE_ROLES="roles";
    public static String TABLE_CIUDADES="ciudades";
    public static String TABLE_SERVICIOS="servicios";
    public static String TABLE_SOAT="valor_soat";
    public static String TABLE_MATRICULA="valor_matricula";
    public static String TABLE_PROPIETARIO="propietarios";
    public static String TABLE_VEHICULO="vehiculos";
    public static String TABLE_ORDENSERVICIO="orden_servicio";
    public static String TABLE_OSDETALLE="orden_detalle";
    public static String TABLE_ESTADO="estados";
    public static String TABLE_TIPOVEH="tipo_vehiculo";
    public static String TABLE_ASEGURADORAS="aseguradoras";
    public static String TABLE_REGISTRORECEP="registrodisp";
    public static String TABLE_CONCESIONARIO="concesionario";
    public static String TABLE_RECEPTORES="receptor";
    public static String TABLE_SERVICIOVEHI="serviciovehiculo";
    public static String TABLE_CONTROLDIPS="controldispersion";
    public static String TABLE_CLASEVEHI="clasevehiculo";
    public static String TABLE_MARCA="marcas";
    public static String TABLE_SECRETARIAST="secretaria";
    public static String TABLE_GESTORIASMATR="gestoria";
    public static String TABLE_PARAMETROSFORMS="params";
    public static String TABLE_TIPOVEHSOAT="tipo_vehiculosoat";
    public static String TABLE_REGMOVBOLSA="movimientos_bolsa";
    
    public static void setParameter(String host, String port, String db, String user, String pass, String infoschema){
        connectionMysql.setHost(host);
        connectionMysql.setPort(port);
        connectionMysql.setDb(db);
        connectionMysql.setUser(user);
        connectionMysql.setPass(pass);
        connectionMysql.setInformationSchema(infoschema);
    }
    
    public static boolean validateuser(String parameter, String parameter0) throws SQLException {
        connectionMysql.conectar();
        Login login = LoginDao.getLogin(parameter, parameter0);
        Login userpaser= new Login(parameter, parameter0);
        if(login.equals(userpaser)){
            System.out.println("THE USER: \""+login.getUser()+ "\" IS LOGIN AT APLICATION");
            return true;
        }
        return false;
    }
    
    public static ResultSet consultar(String sql) throws SQLException{
        /*
        ConnectionOracle connectionOracle = new ConnectionOracle();
        connectionOracle.conectar();
        //System.out.println("SQL: "+sql);
        return connectionOracle.consultar(sql);
        */
        //connectionMysql = new ConnectionMysql();
        //connectionMysql.conectar();
        
        if(connectionMysql.getConnection()==null){
            connectionMysql.conectar();
        }
        ResultSet resultSet = connectionMysql.consultar(sql);
        System.out.println("SQL: "+sql);
        //connectionMysql.desconectar();
        
        return resultSet;
    }
    public static boolean update(String sql) throws SQLException{
        /*
        ConnectionOracle connectionOracle = new ConnectionOracle();
        connectionOracle.conectar();
        System.out.println("SQL: "+sql);
        return connectionOracle.escribir(sql);
        */
        //connectionMysql =new ConnectionMysql();
        //connectionMysql.conectar();
        if(connectionMysql.getConnection()==null){
            connectionMysql.conectar();
        }
        System.out.println("SQL: "+sql);
        return connectionMysql.escribir(sql);
    }

    public static ConnectionMysql getConnectionMysql() {
        return connectionMysql;
    }

    public static void setConnectionMysql(ConnectionMysql connectionMysql) {
        App.connectionMysql = connectionMysql;
    }
    
    
}
