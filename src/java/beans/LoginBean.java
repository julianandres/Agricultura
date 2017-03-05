/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dataEjb.UserEJB;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import javax.servlet.http.HttpSession;
import models.Usuario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author julian
 */
@ManagedBean(name ="loginBean")
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
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error","Credenciales no válidas");
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
        }else{
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
    public void invalidarSession(){
        
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
}
