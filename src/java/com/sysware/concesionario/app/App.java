/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.app;

import com.sysware.concesionario.core.LoginEntitie;
import com.sysware.concesionario.core.Mail;
import com.sysware.concesionario.db.ConnectionMysql;
import com.sysware.concesionario.dao.LoginDao;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author andre
 */
public class App {
    
    public final static String versionCompilation="V1.052";
    
    private static ConnectionMysql connectionMysql = new ConnectionMysql();
    private static Mail mailStaticParams= new Mail();
    public static String TABLE_USUARIO="usuarios";
    public static String TABLE_CANALES="canales";
    public static String TABLE_ROL_MENU="rol_menu";
    public static String TABLE_MENUS="menus";
    public static String TABLE_ROLES="roles";
    public static String TABLE_CIUDADES="ciudades";
    public static String TABLE_SERVICIOS="servicios";
    public static String TABLE_SOAT="valor_soat";
    public static String TABLE_MATRICULA="valor_matricula";
    public static String TABLE_CLIENTE="cliente";
    public static String TABLE_VEHICULO="vehiculos";
    public static String TABLE_OS="orden_servicio";
    public static String TABLE_DOS="orden_detalle";
    public static String TABLE_ESTADO="estados";
    public static String TABLE_TIPOVEH="tipo_vehiculo";
    public static String TABLE_ASEGURADORAS="aseguradoras";
    public static String TABLE_REGISTRORECEP="registrodisp";
    public static String TABLE_REGISTROMATRICULA="registromatri";
    public static String TABLE_REGISTROSOAT="registrosoat";
    public static String TABLE_CONCESIONARIO="conce";
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
    public static String TABLE_LIQUIDACION="liquidacion";
    public static String TABLE_ASIS_DENTAL="asis_dental";
    public static String TABLE_REGISTROPAGO="registropago";
    public static String TABLE_RUBRODIPS="rubrosdips";
    
    public static void setParameter(
            String host, 
            String port, 
            String db, 
            String user, 
            String pass, 
            String infoschema,
            String mail,
            String mailpass){
        connectionMysql.setHost(host);
        connectionMysql.setPort(port);
        connectionMysql.setDb(db);
        connectionMysql.setUser(user);
        connectionMysql.setPass(pass);
        connectionMysql.setInformationSchema(infoschema);
        mailStaticParams.setMAIL(mail);
        mailStaticParams.setPASS(mailpass);
    }
    
    public static boolean validateuser(String parameter, String parameter0) throws SQLException {
        connectionMysql.conectar();
        LoginEntitie login = LoginDao.getLogin(parameter, parameter0);
        LoginEntitie userpaser= new LoginEntitie(parameter, parameter0);
        if(login.equals(userpaser)){
            System.out.println("THE USER: \""+login.getUser()+ "\" IS LOGIN AT APLICATION");
            return true;
        }
        return false;
    }
    
    public static ResultSet consult(String sql) throws SQLException{
        if(connectionMysql.getConnection()==null){
            connectionMysql.conectar();
        }
        sql+=" LIMIT 100";
        ResultSet resultSet = connectionMysql.consultar(sql);
        //System.out.println("SQL: "+sql);
        //connectionMysql.desconectar();
        return resultSet;
    }
    public static boolean update(String sql) throws SQLException{
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

    public static Mail getMailStaticParams() {
        return mailStaticParams;
    }
    
    public static String getVersionApp(){
        return versionCompilation;
    }
    
}
