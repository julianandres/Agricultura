/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author julianbolanos
 */
public class Resultado {
    
    String idSubProceso;
    String fotoRGB;
    String fotoNoIR;
    String fotoNDVI;
    String comentario;
    String nombreRGB;

    public String getNombreRGB() {
        return nombreRGB;
    }

    public void setNombreRGB(String nombreRGB) {
        this.nombreRGB = nombreRGB;
    }
    public String getIdSubProceso() {
        return idSubProceso;
    }

    public void setIdSubProceso(String idSubProceso) {
        this.idSubProceso = idSubProceso;
    }

    public String getFotoRGB() {
        return fotoRGB;
    }

    public void setFotoRGB(String fotoRGB) {
        this.fotoRGB = fotoRGB;
    }

    public String getFotoNoIR() {
        return fotoNoIR;
    }

    public void setFotoNoIR(String fotoNoIR) {
        this.fotoNoIR = fotoNoIR;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFotoNDVI() {
        return fotoNDVI;
    }

    public void setFotoNDVI(String fotoNDVI) {
        this.fotoNDVI = fotoNDVI;
    }
    
    
    
    
}
