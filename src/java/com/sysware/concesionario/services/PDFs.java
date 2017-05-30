/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysware.concesionario.services;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.sysware.concesionario.entitie.Entitie;
import java.io.FileNotFoundException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author andre
 */
public class PDFs {

    

    public void createPDFPoliza(HttpServletRequest request, Entitie cliente, Entitie asd) throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        System.out.println("CREADO PDF");
        System.out.println("DIR REAL: "+request.getSession().getServletContext().getRealPath("/attach"));
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(
                request.getSession().getServletContext().getRealPath("/attach")+"/POL "+asd.getDataOfLabel("POLIZA")+".pdf"
                ));
        
        document.open();
        
        Anchor anchorTarget = new Anchor("POLIZA N. "+asd.getDataOfLabel("POLIZA"));
        anchorTarget.setName("BackToTop");
        Paragraph paragraph1 = new Paragraph();
        paragraph1.setSpacingBefore(50);

        paragraph1.add(anchorTarget);
        document.add(paragraph1);
        String cadena= cliente.getDataOfLabel("NOMBRE");
        String nameClient = cadena.substring(0,1).toUpperCase() + cadena.substring(1).toLowerCase();
        document.add(new Paragraph("Señor(a) "+nameClient+", su numero de poliza es "+asd.getDataOfLabel("POLIZA"), 
        FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(255, 255, 255, 0))));
        document.close();
        System.out.println("CERRANDO PDF");
    }

}
