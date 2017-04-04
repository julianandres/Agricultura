/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataEjb;

import handlers.HandUser;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import models.Usuario;

/**
 *
 * @author JULIAN
 */
@Stateless
@LocalBean
public class UserEJB implements Serializable {

   public Usuario findUserByID(String id) {
        HandUser hus = new HandUser();
        Usuario resultado = new Usuario();
        List<Usuario> allUser = hus.listarUsuarios();
        for (Usuario us : allUser) {
            if (us.getId().equals(id)) {
                resultado = us;
            }
        }
        hus.cerrarConexion();
        return resultado;
    }
   public Usuario findUserByLogin(String login){
        HandUser hus = new HandUser();
        Usuario resultado = new Usuario();
        List<Usuario> allUser = hus.listarUsuarios();
        for (Usuario us : allUser) {
            if (us.getLogin().equals(login)) {
                resultado = us;
            }
        }
        hus.cerrarConexion();
        return resultado;
    }
}
