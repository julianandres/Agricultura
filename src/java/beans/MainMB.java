/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dataEjb.ProcessEJB;
import dataEjb.SubProcessEJB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import models.Proceso;
import models.Resultado;
import models.SubProceso;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
             String base2="http://mvubuntu14.eastus.cloudapp.azure.com//InformacionAppWeb/";
             
        
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
        if(resultados.size()>0)
        return "resultPage.xhtml";
        else
           return "";
        
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
