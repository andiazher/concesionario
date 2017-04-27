/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.entitie;

import com.sysware.concesionario.app.App;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class Entitie {
    
    private String name;
    private String id;
    private ArrayList<String> colums;
    private ArrayList<String> data;

    public Entitie() {
        colums= new ArrayList<>();
        data = new ArrayList<>();
        name= "PROTOTIPE";
        id="0";
    }
    
    
    public Entitie(String name)  {
        setColums(new ArrayList<>());
        setName(name);
        setId("0");
        setData(new ArrayList<>());
        String sql = "";
        sql+="SELECT COLUMN_NAME FROM "+App.getConnectionMysql().getInformationSchema()+".columns "
                + "where table_schema ='"+App.getConnectionMysql().getDb()+"' and table_name='"+name+"'";
        try{
            ResultSet resultSet = App.consult(sql);
            while(resultSet.next()){
                String request = resultSet.getString("COLUMN_NAME");
                if(!request.equals("ID")){
                    colums.add(request);
                }
            }
        }
        catch(SQLException s){
            System.out.println(s);
        }
        
    }

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getColums() {
        return colums;
    }

    public void setColums(ArrayList<String> colums) {
        this.colums = colums;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
    /**
     * 
     * @return verdadero si la operacion es un exito.
     * @throws SQLException 
     */
    public boolean create() throws SQLException{
        String sql = "";
        String columnas="",datos="";
        for(int i=0; i<colums.size();i++){
            if(i!=colums.size()-1){
                columnas+=colums.get(i)+", ";
                datos+="'"+data.get(i)+"', ";
            }
            else{
                columnas+=colums.get(i)+"";
                datos+="'"+data.get(i)+"'";
            }
        }
        sql+="insert into "+App.getConnectionMysql().getDb()+"."+name+" ("+columnas+")  values ("+datos+");";
        return App.update(sql);
    }
    /**
     * 
     * @return verdadero si la operacion es un exito
     * @throws SQLException 
     */
    public boolean update() throws SQLException{
        String sql = "";
        String datos="";
        for(int i=0; i<colums.size();i++){
            if(i!=colums.size()-1){
                datos+=colums.get(i)+"='"+data.get(i)+"', ";
            }
            else{
                datos+=colums.get(i)+"='"+data.get(i)+"'";
            }
        }
        sql+="update "+App.getConnectionMysql().getDb()+"."+name+" set "+datos+" where ID = "+id;
        return App.update(sql);
    }
    /**
     * 
     * @return verdadero si la operacion es un exito
     * @throws SQLException 
     */
    public boolean delete() throws SQLException{
        String sql = "";
        sql+="delete from "+App.getConnectionMysql().getDb()+"."+name+ " where ID = "+id;
        return App.update(sql);
    }
    
    /**
     * 
     * @param id
     * @throws SQLException 
     */
    public void getEntitieID(String id) throws SQLException{
        String sql = "";
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name +" where ID="+id ;
        ResultSet query= App.consult(sql);
        setId(id);
        setData(new ArrayList<>());
        while(query.next()){
            for(int i=0; i<colums.size();i++){
                this.getData().add(query.getString(colums.get(i)));
            }
        }
    }
    
    /**
     * 
     * @param param
     * @param param2
     * @return
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntitieParams(ArrayList<String> param, ArrayList<String> param2) throws SQLException{
        String sql = "";
        String params = "";
        for(int i=0; i<param.size();i++){
            if(i!=param.size()-1){
                params+=param.get(i)+"='"+param2.get(i)+"' and ";
            }
            else{
                params+=param.get(i)+"='"+param2.get(i)+"'";
            }
        }
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name +" where "+params+"";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString(name+".ID"));
            for(int i=0; i<colums.size();i++){
                entitie.getData().add(query.getString(colums.get(i)));
            }
            entities.add(entitie);
        }
        return entities;
    }
    /**
     * 
     * @param param
     * @param param2
     * @param opera
     * @return
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntitieParams(ArrayList<String> param, ArrayList<String> param2, ArrayList<String> opera ) throws SQLException{
        String sql = "";
        String params = "";
        for(int i=0; i<param.size();i++){
            if(i!=param.size()-1){
                params+=param.get(i)+ " "+opera.get(i)+"'"+param2.get(i)+"' and ";
            }
            else{
                params+=param.get(i)+ " "+opera.get(i)+"'"+param2.get(i)+"'";
            }
        }
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name +" where "+params+"";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString(name+".ID"));
            for(int i=0; i<colums.size();i++){
                entitie.getData().add(query.getString(colums.get(i)));
            }
            entities.add(entitie);
        }
        return entities;
    }
    /**
     * 
     * @param param
     * @param param2
     * @param opera
     * @param sqlq
     * @return
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntitieParams(ArrayList<String> param, ArrayList<String> param2, ArrayList<String> opera, String sqlq, String Otables ) throws SQLException{
        String sql = "SELECT "+name+".ID, ";
        for(int i=0; i<colums.size();i++){
            if(i==colums.size()-1){
                sql+= name+"."+colums.get(i);
            }
            else{
                sql+= name+"."+colums.get(i)+", ";
            }
        }
        String params = "";
        for(int i=0; i<param.size();i++){
            if(i!=param.size()-1){
                params+=name+"."+param.get(i)+ " "+opera.get(i)+"'"+param2.get(i)+"' and ";
            }
            else{
                params+=name+"."+param.get(i)+ " "+opera.get(i)+"'"+param2.get(i)+"'";
            }
        }
        params+=sqlq;
        if(!Otables.equals("")){
            Otables = ", "+Otables;
        }
        sql+=" FROM "+App.getConnectionMysql().getDb()+"."+ name +Otables+"  where "+params+"";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString(name+".ID"));
            for(int i=0; i<colums.size();i++){
                entitie.getData().add(query.getString(colums.get(i)));
            }
            entities.add(entitie);
        }
        return entities;
    }
    
    /**
     * 
     * @param param
     * @param param2
     * @return
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntitieParam(String param, String param2) throws SQLException{
        String sql = "";
        String params = "";
        params+=param+"='"+param2+"'";
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name +" where "+params+"";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString("ID"));
            for(int i=0; i<colums.size();i++){
                entitie.getData().add(query.getString(colums.get(i)));
            }
            entities.add(entitie);
        }
        return entities;
    }
    
    /**
     * 
     * @return TODAS LAS ENTIDADES
     * @throws SQLException 
     */
    public ArrayList<Entitie> getEntities() throws SQLException{
        String sql = "";
        sql+="SELECT * FROM "+App.getConnectionMysql().getDb()+"."+ name + " order by ID";
        ResultSet query= App.consult(sql);
        ArrayList<Entitie> entities= new ArrayList<>();
        while(query.next()){
            Entitie entitie = new Entitie(name);
            entitie.setId(query.getString("ID"));
            for(int i=0; i<colums.size();i++){
                entitie.getData().add(query.getString(colums.get(i)));
            }
            entities.add(entitie);
        }
        return entities;
    }
    
    /**
     * 
     * @return TOSTRING ENTITIE
     */
    
    @Override
    public String toString() {
        return "Entitie{" + "name=" + name + ", id=" + id + ", colums=" + colums + ", data=" + data + '}';
    }
    
    /**
     * 
     * @param param
     * @return DATO DE ACUERDO A LA COLUMANA INGRESADA EN @param
     */
    public String getDataOfLabel(String param){
        for(int i=0; i<colums.size(); i++){
            if(colums.get(i).equals(param)){
                return data.get(i);
            }
        }
        return null;
    }

}
