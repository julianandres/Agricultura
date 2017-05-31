/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 *
 * @author JULIAN
 */
public class JavaApplication2 {

    public static void main(String ... args) {
    Document document = new Document();
    String input = "d:/captura.png"; // .gif and .jpg are ok too!
    String output = "c:/temp/capture.pdf";
    try {
        JavaApplication2 objeto = new JavaApplication2();
                
        objeto.createPdf(output);
      
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
     
    public void createPdf(String filename){
        
    	// step 1
         float left = 5;
        float right = 5;
        float top = 5;
        float bottom = 5;
        Document document = new Document(PageSize.LEGAL, left, right, top, bottom);
       
        // step 2
        try{
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        }catch(Exception e){
            System.out.println("error");
        }
        // step 3
        document.open();
        // step 4
        
        try{
        
        document.add(createFirstTable());
       
        
        
        }catch(Exception e){
            System.out.println("error");
        }
        // step 5
        document.close();
    }
 
    /**
     * Creates our first table
     * @return our first table
     */
    public static PdfPTable createFirstTable() {
        
        int numColumns=4;
       
    	// a table with three columns
        PdfPTable table = new PdfPTable(numColumns);
        
      table.setWidthPercentage(100);
      
      table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
      table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
      
      
      //region header
      table.addCell("");
      PdfPCell cell;
      Paragraph p = new Paragraph("Resultados Prueba #");
      cell = new PdfPCell(p);
      cell.setBorder(Rectangle.NO_BORDER);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setColspan(2);
      table.addCell(cell);
      table.addCell("");
       //endregion
       //region imagen 1
       try{
      Image imagen= Image.getInstance("http://mvubuntu16.eastus.cloudapp.azure.com/InformacionAppWeb/andres@andres.com/21052017165817filtrorojo054/Tarea%201%20de%20filtrorojo054/FotoRGB/pruebatimbiorojo054100025_stitch.jpg");
      imagen.scaleToFit(250,150);
      imagen.setCompressionLevel(50);
       cell = new PdfPCell(imagen);
       cell.setColspan(2);
       cell.setRowspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(10);
        cell.setPaddingTop(10);
        cell.setBorder(Rectangle.NO_BORDER);
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(cell);
         
         //region imagen 2
        imagen = Image.getInstance("http://mvubuntu16.eastus.cloudapp.azure.com/InformacionAppWeb/saludables.png");
        imagen.scaleAbsolute(120, 30); 
        cell = new PdfPCell(imagen);
        cell.setPaddingBottom(8);
        cell.setPaddingTop(50);
        cell.setPaddingLeft(5);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(cell);
         p = new Paragraph("Plantas sanas y vigorosas");
         cell = new PdfPCell(p);
         cell.setPaddingTop(50);
         cell.setBorder(Rectangle.NO_BORDER);
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(cell);
         imagen = Image.getInstance("http://mvubuntu16.eastus.cloudapp.azure.com/InformacionAppWeb/enfermossuelo.png");
         imagen.scaleAbsolute(120, 30); 
         cell = new PdfPCell(imagen);
         cell.setPaddingBottom(8);
         cell.setPaddingLeft(5);
         cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(cell);
         table.addCell("Plantas Enfermas o Suelo");
         
         imagen = Image.getInstance("http://mvubuntu16.eastus.cloudapp.azure.com/InformacionAppWeb/otros.png");
         imagen.scaleAbsolute(120, 30); 
         cell = new PdfPCell(imagen);
          cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         cell.setPaddingLeft(5);
         table.addCell(cell);
         table.addCell("Agua u otros");
        //endregion 
         //region header2
      table.addCell("");
       p = new Paragraph("NDVI");
      cell = new PdfPCell(p);
      cell.setBorder(Rectangle.NO_BORDER);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setColspan(2);
      table.addCell(cell);
      table.addCell("");
       //endregion
       
        Image imagen2= Image.getInstance("http://mvubuntu16.eastus.cloudapp.azure.com/InformacionAppWeb/andres@andres.com/21052017165817filtrorojo054/Tarea%201%20de%20filtrorojo054/ResultadosNDVI/ResMatPlot/FilterRedAWB1054pruebaMatploTNDVI.jpg");
        imagen2.scaleAbsolute(600, 700);
        imagen2.setCompressionLevel(50);
        
        cell = new PdfPCell(imagen2);
       cell.setColspan(4);
       cell.setPaddingBottom(10);
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
       table.addCell(cell);
      
       }catch(Exception e)
       {
           System.out.println("error");
       }
      
      //imagen.setAbsolutePosition(0, 0);
     
        return table;
    }
    
}
