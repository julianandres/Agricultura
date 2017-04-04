/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataEjb;

import handlers.HandSubProcess;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import models.SubProceso;

/**
 *
 * @author JULIAN
 */

@Stateless
@LocalBean
public class SubProcessEJB implements Serializable {
    public List<SubProceso> findSubProcesobyIdProceso(String idProceso){
        List<SubProceso> subProcesos = new ArrayList<SubProceso>();
        HandSubProcess hsp= new HandSubProcess();
        subProcesos = hsp.listarSubProcesosbyIdProceso(idProceso);
        hsp.cerrarConexion();
        return subProcesos;
    }
    public boolean updateSubProceso(SubProceso subproceso,String campo,String valor){
        boolean resultado = false;
        HandSubProcess  hsp= new HandSubProcess();
        resultado = hsp.updateSubProceso(subproceso,campo,valor);
        return resultado;
    }
}
