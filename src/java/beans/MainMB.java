/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dataEjb.ProcessEJB;
import dataEjb.SubProcessEJB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.Proceso;
import models.Resultado;
import models.SubProceso;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.util.StringUtils;

/**
 *
 * @author JULIAN
 */

@ManagedBean
@SessionScoped
public class MainMB implements Serializable {

    public MainMB() {
        
    }
    
    @ManagedProperty("#{loginBean}")
    private LoginBean logBean;
    
    @EJB
    private ProcessEJB processEjb;
    
    @EJB
    private SubProcessEJB subProcessEjb;
    private List<Proceso> processTable;
    private Proceso processSelect;
    private List<SubProceso> subProcessTable;
    private SubProceso subProcessSelect;
    private boolean subirFotos;
    private boolean selecttypephoto;
    private List<Resultado> resultados;
    
    @PostConstruct
    public void init() {
        processTable = processEjb.findProcesobyIdUsuario(logBean.getUsername());
        System.out.println("hola");
        System.out.println(logBean.getUsername());
        resultados = new ArrayList();
        setSubirFotos(false);
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }
    
    public boolean isSelecttypephoto() {
        return selecttypephoto;
    }

    public void setSelecttypephoto(boolean selecttypephoto) {
        this.selecttypephoto = selecttypephoto;
    }
    
    
     public boolean isSubirFotos() {
        return subirFotos;
    }

    public void setSubirFotos(boolean subirFotos) {
        this.subirFotos = subirFotos;
    }
    public LoginBean getLogBean() {
        return logBean;
    }

    public void setLogBean(LoginBean logBean) {
        this.logBean = logBean;
    }
    
    public List<SubProceso> getSubProcessTable() {
        return subProcessTable;
    }

    public void setSubProcessTable(List<SubProceso> subProcessTable) {
        this.subProcessTable = subProcessTable;
    }

    public SubProceso getSubProcessSelect() {
        return subProcessSelect;
    }

    public void setSubProcessSelect(SubProceso subProcessSelect) {
        this.subProcessSelect = subProcessSelect;
    }
    
    public List<Proceso> getProcessTable() {
        return processTable;
    }

    public void setProcessTable(List<Proceso> processTable) {
        this.processTable = processTable;
    }

    public Proceso getProcessSelect() {
        return processSelect;
    }

    public void setProcessSelect(Proceso processSelect) {
        this.processSelect = processSelect;
    }
    
    public void seleccionarSubProceso() {
        if (subProcessSelect != null) {
            if (subProcessSelect.getDisponibilidad() == 1) {
                setSubirFotos(true);
            } else {
                setSubirFotos(false);
            }
        }
    }
    public void seleccionarTipoFoto() {
        if (subProcessSelect != null) {
            if (subProcessSelect.getDisponibilidad() == 1) {
                setSubirFotos(true);
            } else {
                setSubirFotos(false);
            }
        }
    }
    
    public void verFotoNoir(){
        setSubirFotos(false);
    }
    public void verFotoRGB(){
        setSubirFotos(false);
    }
    public String abrirProceso(){
        if(processSelect!=null){
            
            subProcessTable = subProcessEjb.findSubProcesobyIdProceso(processSelect.getId());
            System.out.println("hola");
            return "processPage.xhtml";
         }
        else {
            
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error", "Seleccionar Un proceso en la columna Nombre");
         FacesContext.getCurrentInstance().addMessage(null, message);
         return "";
        }  
    }
    public String backPage(){
        processTable = processEjb.findProcesobyIdUsuario(logBean.getUsername());
        return "mainPage.xhtml";
    }
    public String backPageUpload(){
        return "processPage.xhtml";
    }
    public String listarResultados(){
        resultados.clear();
        for (SubProceso sp:getSubProcessTable()){
         if(sp.getEstado()==1){
             String base1="/var/www/html/InformacionAppWeb/";
             String base2="http://mvubuntu16.eastus.cloudapp.azure.com//InformacionAppWeb/";
             
        
             String dirNoir = 
                logBean.getUsername() + "/" +
                getProcessSelect().getId() +
                getProcessSelect().getNombre() + "/" +
                sp.getNombre() + "/" + "FotoNoir/";
             String dirRgb = 
                logBean.getUsername() + "/" +
                getProcessSelect().getId() +
                getProcessSelect().getNombre() + "/" +
                sp.getNombre() + "/" + "FotoRGB/";
             String dirNDVI = 
                logBean.getUsername() + "/" +
                getProcessSelect().getId() +
                getProcessSelect().getNombre() + "/" +
                sp.getNombre() + "/" + "ResultadosNDVI/ResMatPlot/";
           Resultado res = new Resultado();
           res.setComentario("Procesado Correctamente");
           res.setFotoNoIR(base2+dirNoir+getNamePhotoFromString(base1+dirNoir));
           res.setFotoRGB(base2+dirRgb+getNamePhotoFromString(base1+dirRgb));
           res.setFotoNDVI(base2+dirNDVI+getNamePhotoFromString(base1+dirNDVI));
           res.setIdSubProceso(sp.getNombre());
           res.setNombreRGB(getNamePhotoFromString(base1+dirRgb));
           System.out.println(res.getFotoNDVI());
             System.out.println(res.getFotoRGB());
           resultados.add(res);
           
         } 
         
        }
        
    	// step 1
         float left = 5;
        float right = 5;
        float top = 5;
        float bottom = 5;
        Document document = new Document(PageSize.LEGAL, left, right, top, bottom);
        String proceso;
        if(resultados.size()>0){
          
        }
        
       String filename = "/home/julian/"+processSelect.getNombre()+"resultado.pdf";
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
        for(Resultado res:resultados){
           document.add(createFirstTable(res));
           document.newPage();
        }
        
        }catch(Exception e){
            System.out.println("error");
        }
        // step 5
        document.close();
        
        if(resultados.size()>0)
        return "resultPage.xhtml";
        else
           return "";
        
    }
    public void exportPDF() {
    
  }
    
    public PdfPTable createFirstTable(Resultado res) {
        
        int numColumns=4;
       
    	// a table with three columns
        PdfPTable table = new PdfPTable(numColumns);
        
      table.setWidthPercentage(100);
      
      table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
      table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
      
      
      //region header
      table.addCell("");
      PdfPCell cell;
      Paragraph p = new Paragraph("Resultados Prueba "+res.getIdSubProceso());
      cell = new PdfPCell(p);
      cell.setBorder(Rectangle.NO_BORDER);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setColspan(2);
      table.addCell(cell);
      table.addCell("");
       //endregion
       //region imagen 1
       try{
           String fotorgb=res.getFotoRGB();
      Image imagen= Image.getInstance(reemplazarEspacios(fotorgb));
      imagen.scaleToFit(250,150);
      imagen.setCompressionLevel(120);
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
       
        Image imagen2= Image.getInstance(reemplazarEspacios(res.getFotoNDVI()));
        imagen2.scaleAbsolute(600, 700);
        imagen2.setCompressionLevel(10);
        
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
    public String reemplazarEspacios(String originalString){
        
         String myString = StringUtils.replace(originalString, Matcher.quoteReplacement(" "), Matcher.quoteReplacement("%20"));
         return myString;
    }
    public StreamedContent prepDownload() throws Exception {
        StreamedContent download = new DefaultStreamedContent();
        File file = new File("/home/julian/"+processSelect.getNombre()+"resultado.pdf");
        InputStream input = new FileInputStream(file);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        download = new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName());
        System.out.println("PREP = " + download.getName());
        return download;
    }
    
    public String getNamePhotoFromString(String pathDir){
    File folder = new File(pathDir);
    for (File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            
        } else {
            System.out.println(fileEntry.getName());
            return fileEntry.getName();
        }
    }
    return "no encontrado";
    }
    public StreamedContent getImagen(String path){
    StreamedContent imageen = null;
    
   File directorio = new File(path);
   File[] ficheros = directorio.listFiles();
    for (File fichero : ficheros) {
            try {
                if (fichero.isDirectory()) {
                    borrarDirectorio(fichero);
                }
                
                imageen = new DefaultStreamedContent(new FileInputStream(fichero));
                System.out.println("prueba");
            } //destinationImage = "/var/www/html/InformacionAppWeb/" + logBean.getUsername() + "/" + mainmb.getProcessSelect().getId() + mainmb.getProcessSelect().getNombre() + "/" + mainmb.getSubProcessSelect().getNombre() + "/" + "FotoNoir/";
            catch (FileNotFoundException ex) {
                Logger.getLogger(ArchivesMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    return imageen;
    }
    public void borrarDirectorio(File directorio) {
        File[] ficheros = directorio.listFiles();
        for (File fichero : ficheros) {
            if (fichero.isDirectory()) {
                borrarDirectorio(fichero);
            }
            fichero.delete();
        }
    }
}
