/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author andre
 */
public class XLSFile {
    private FileOutputStream fileOut;
    private HSSFWorkbook workbook; 
    private int indexrowActive =0;
    private int indexColActive =0;
    private HSSFSheet worksheet;
    private HSSFRow rowActive;
    private HSSFCell cellActive;
    private HSSFCellStyle cellStyle;
    
    public XLSFile(String nameFile) throws FileNotFoundException {
        fileOut = new FileOutputStream(nameFile+".xls");
        workbook = new HSSFWorkbook();
        cellStyle=workbook.createCellStyle();
    }
    public void createWorksheet(String name){
        worksheet=workbook.createSheet(name);
        indexrowActive=0;
    }
    public void createRow(){
        rowActive=worksheet.createRow((short) indexrowActive);
        indexrowActive++;
        indexColActive = 0;
    }
    public void createCell(){
        cellActive = rowActive.createCell((short) indexColActive);
        indexColActive++;
    }
    public HSSFCell getCell(){
        return cellActive;
    }
    public HSSFCell getCell(int row, int col){
        rowActive=worksheet.getRow(row);
        cellActive = rowActive.getCell(col);
        indexrowActive= row;
        indexColActive = col;
        return  cellActive;
    }
    
    public HSSFCellStyle getCellStyle(){
        return cellStyle;
    }
    
    public void addCellStyleTo(int row, int col, HSSFCellStyle cellStyle){
        rowActive=worksheet.getRow(row);
        cellActive = rowActive.getCell(col);
        indexrowActive= row;
        indexColActive = col;
        cellActive.setCellStyle(cellStyle);
    }
    
    public void addCellStyleTo( HSSFCellStyle cellStyle){
        cellActive.setCellStyle(cellStyle);
    }
    public void addCellStyleTo(){
        cellActive.setCellStyle(cellStyle);
    }
    public void restartStyle(){
        cellStyle=workbook.createCellStyle();
    }
    
    public void generateInformXLS(ArrayList<String> nameCols, String nameReport){
        createRow();
        createCell();
        cellActive.setCellValue(nameReport);
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        addCellStyleTo();
        worksheet.addMergedRegion(new CellRangeAddress(0,0,0,16));
        createRow();
    }
    public void closeReport() throws IOException{
        workbook.write(fileOut);
        fileOut.flush();
        fileOut.close();
        System.out.println("Archivo creado");
    }
}
