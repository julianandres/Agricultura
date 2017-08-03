/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataEjb.UserEJB;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import models.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author julian
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private UserEJB userEjb;

    private String username;
    private String password;
    private String idUsuario;
    private Usuario usu;
    private boolean login = false;

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean estaLogeado() {
        return login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String login() {
        uploadFileState=true;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        if (username != null) {
            usu = userEjb.findUserByLogin(username);
            if (usu.getPassword() != null) {
                if (username.equals(usu.getLogin()) && password != null
                        && password.equals(usu.getPassword())) {
                    login = true;
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", usu.getNombre());
                    idUsuario = usu.getId();
                } else {
                    login = false;
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no válidas");
                }
            } else {
                login = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                        "Credenciales no válidas");
            }
        } else {
            login = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    "Credenciales no válidas");

        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("estaLogeado", login);
        if (login) {
            return "mainPage.xhtml";
        } else {
            return "loginPage.xhtml";
        }
    }

    public String logout() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("estaLogeado", login);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        login = false;
        return "loginPage.xhtml";
    }

    public void invalidarSession() {

    }

    public String iniciarSistema() {
        
        String destino;
        if (login) {
            destino = "mainPage.xhtml";
        } else {
            destino = "loginPage.xhtml";
        }
        return destino;
    }
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);

        if (file != null) {

            FileReader fr = null;
            BufferedReader br = null;
            List<String> lineas = new ArrayList<String>();
            String cabecera = "latitude,longitude,altitude(m),heading(deg),curvesize(m),rotationdir,gimbalmode,gimbalpitchangle,actiontype1,actionparam1,actiontype2,actionparam2,actiontype3,actionparam3,actiontype4,actionparam4,actiontype5,actionparam5,actiontype6,actionparam6,actiontype7,actionparam7,actiontype8,actionparam8,actiontype9,actionparam9,actiontype10,actionparam10,actiontype11,actionparam11,actiontype12,actionparam12,actiontype13,actionparam13,actiontype14,actionparam14,actiontype15,actionparam15";
            lineas.add(cabecera);
            try {
                // Apertura del fichero y creacion de BufferedReader para poder
                // hacer una lectura comoda (disponer del metodo readLine()).
                InputStream impt = file.getInputstream();
                BufferedInputStream bufferedinputstream = new BufferedInputStream(impt);
                
                // Lectura del fichero
                int linea;
                int i = 0;
                int content;
                String salida;
                content = 0;
                salida = "kjuliooooo";
                while ((content = bufferedinputstream.read()) != -1) {
                    // convert to char and display it
                    salida += (char) content;
                }
                System.out.println(salida);
                String delimiter2 = "\n";
                String[] temp2;
                temp2 = salida.split(delimiter2);
                System.out.println(temp2.length);
                for (int a = 0; a < temp2.length; a++) {
                    if (a > 2) {
                        String str = temp2[a];
                        String delimiter = "\t";
                        String[] temp;
                        temp = str.split(delimiter);
                        System.out.println(temp[3]);
                        if (temp[3].equals("16")) {
                            String latitud, longitud, altitud;
                            latitud = temp[8];
                            longitud = temp[9];
                            altitud = temp[10];
                            String line = latitud + "," + longitud + "," + altitud + ",0,0.2,0,0,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0,-1,0";
                            lineas.add(line);
                            System.out.println(line);
                        }
                    }
                }
                FileWriter fichero = null;
                PrintWriter pw = null;
                try {
                    String nombreext[]=this.file.getFileName().split("\\.");
                    fichero = new FileWriter("/home/julian/"+nombreext[0]+".csv");
                    pw = new PrintWriter(fichero);

                    for (int f = 0; f < lineas.size(); f++) {
                        pw.println(lineas.get(f));
                    }
                    uploadFileState=false;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // Nuevamente aprovechamos el finally para 
                        // asegurarnos que se cierra el fichero.
                        if (null != fichero) {
                            fichero.close();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                try {

                } catch (Exception e) {
                    System.out.println("hubo un erro " + e.getCause());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // En el finally cerramos el fichero, para asegurarnos
                // que se cierra tanto si todo va bien como si salta 
                // una excepcion.
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

        } else {
            System.out.println("es nulooo");
        }
    }
    

    public StreamedContent prepDownload() throws Exception {
        StreamedContent download = new DefaultStreamedContent();
        String nombreext[]=this.file.getFileName().split("\\.");
        
        File file = new File("/home/julian/"+nombreext[0]+".csv");
        InputStream input = new FileInputStream(file);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        download = new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName());
        System.out.println("PREP = " + download.getName());
        return download;
    }
    private boolean uploadFileState;

    public boolean isUploadFileState() {
        return uploadFileState;
    }

    public void setUploadFileState(boolean uploadFileState) {
        this.uploadFileState = uploadFileState;
    }
    
    
}
