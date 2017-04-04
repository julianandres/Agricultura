/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataEjb;

import handlers.HandProcess;
import handlers.HandSubProcess;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import models.Proceso;
import models.SubProceso;

/**
 *
 * @author JULIAN
 */

@Stateless
@LocalBean
public class ProcessEJB {
     public List<Proceso> findProcesobyIdUsuario(String idUsuario){
        List<Proceso> procesos = new ArrayList<Proceso>();
        HandProcess hp= new HandProcess();
        procesos = hp.listarProcesosByIdUsuario(idUsuario);
        hp.cerrarConexion();
        return procesos;
    }
      public boolean updateProceso(Proceso proceso,String campo,String valor){
        boolean resultado = false;
        HandProcess  hp= new HandProcess();
        resultado = hp.updateProceso(proceso,campo,valor);
        return resultado;
    }
}
