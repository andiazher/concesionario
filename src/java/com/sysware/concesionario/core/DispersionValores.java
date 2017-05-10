/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.core;

import com.sysware.concesionario.app.App;
import com.sysware.concesionario.entitie.Entitie;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author andre
 */
public class DispersionValores {
    
    //TABLE OF PAREMETERS
    Entitie disp = new Entitie(App.TABLE_CONTROLDIPS);
    Entitie rubro = new Entitie(App.TABLE_RUBRODIPS);
    //TABLE OF REGISTER
    Entitie rdisper = new Entitie(App.TABLE_REGISTRORECEP);
    

    public DispersionValores() {
        for(String i: rdisper.getColums()){
            rdisper.getData().add("");
        }
    }
    public void dispersion(int valor, Entitie dos) throws SQLException{
        ArrayList<Entitie> rbsxServicio = rubro.getEntitieParam("SERVICIO", dos.getDataOfLabel("SERVICIO"));
        for(Entitie rb: rbsxServicio){
            dispersion(valor, rb, dos);
        }
    }
    public void dispersion(int valor, Entitie rb, Entitie dos) throws SQLException{
        try{
            ArrayList<Entitie> rbrosd = disp.getEntitieParam("RUBRO", rb.getId());
            int total=0;
            for(Entitie dis: rbrosd){
                    disp = dis;
                    //CALCULAR EL VALOR DEL PORCENTAGE
                    int valorDispersar=0;
                    if(disp.getDataOfLabel("TIPO").equals("1")){
                        int v =Integer.parseInt(disp.getDataOfLabel("VALOR"));
                        if(v==0){
                            valorDispersar= valor -total;
                        }
                        else{
                            valorDispersar = (v*valor)/100;
                        }
                    }
                    else{
                        valorDispersar = (Integer.parseInt(disp.getDataOfLabel("VALOR")));
                    }
                    if(total+valorDispersar <= valor+1){
                        total+=valorDispersar;
                    }
                    else{
                        valorDispersar=0;
                    }
                    Calendar fecha = new GregorianCalendar();
                    String f= fecha.get(Calendar.YEAR) +"-"+(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.DAY_OF_MONTH)+
                            " "+fecha.get(Calendar.HOUR_OF_DAY)+":"+fecha.get(Calendar.MINUTE)+":"+fecha.get(Calendar.SECOND);
                    rdisper.getData().set(rdisper.getColums().indexOf("FECHA"), f);
                    rdisper.getData().set(rdisper.getColums().indexOf("CONCESIONARIO"), disp.getDataOfLabel("CONCESIONARIO"));
                    rdisper.getData().set(rdisper.getColums().indexOf("DOS"), dos.getId());
                    rdisper.getData().set(rdisper.getColums().indexOf("RUBRO"), rb.getDataOfLabel("NOMRUBRO"));
                    rdisper.getData().set(rdisper.getColums().indexOf("RECEPTOR"), disp.getDataOfLabel("RECEPTOR"));
                    rdisper.getData().set(rdisper.getColums().indexOf("TIPO"), disp.getDataOfLabel("TIPO"));
                    rdisper.getData().set(rdisper.getColums().indexOf("VALORPROG"), disp.getDataOfLabel("VALOR"));
                    rdisper.getData().set(rdisper.getColums().indexOf("VALORCALCUL"), valorDispersar+"");
                    rdisper.getData().set(rdisper.getColums().indexOf("VALORBASE"), valor+"");
                    rdisper.create();
            }
        }catch( IndexOutOfBoundsException s){
            s.printStackTrace();
        }catch( NullPointerException s){
            s.printStackTrace();
        }
    }
    
    public void dispersion(int valor, String idrb, String iddos) throws SQLException{
        try{
            Entitie dos = new Entitie(App.TABLE_DOS);
            dos.getEntitieID(iddos);
            rubro.getEntitieID(idrb);
            dispersion(valor, rubro, dos);
        }catch( IndexOutOfBoundsException s){
            s.printStackTrace();
        }catch( NullPointerException s){
            s.printStackTrace();
        }
    }
    public void dispersion(int valor, String idrb, Entitie dos) throws SQLException{
        try{
            rubro.getEntitieID(idrb);
            dispersion(valor, rubro, dos);
        }catch( IndexOutOfBoundsException s){
            s.printStackTrace();
        }catch( NullPointerException s){
            s.printStackTrace();
        }
    }
}
