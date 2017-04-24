/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import models.SubProceso;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JULIAN
 */
public class HandSubProcess {

    Connection con;
    Statement sentencia;

    public HandSubProcess() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://agromobileserver.database.windows.net:1433;database=AgroMobileDataBase;user=julianbolanos@agromobileserver;password=,.940925jabort;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
            sentencia = con.createStatement();

        } catch (Exception e) {
            System.out.println("hubo un error   " + e.getMessage());
        }

    }

    public List<SubProceso> listarSubProcesosbyIdProceso(String idProceso) {
        List<SubProceso> resultado = new ArrayList<>();
        if (sentencia != null) {
            try {
                ResultSet res = sentencia.executeQuery("SELECT * FROM SubProceso WHERE idProceso = '" + idProceso + "' ORDER BY numeroenProceso  ASC ");
                
                while (res.next()) {
                    SubProceso sp = new SubProceso();
                    sp.setId(res.getString("id"));
                    String tm = res.getString("fecha");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                    sp.setFecha(addHours(format.parse(tm), -5));
                    sp.setNombre(res.getString("nombre"));
                    sp.setIdProceso(res.getString("idProceso"));
                    sp.setNumeroenProceso(res.getInt("numeroenProceso"));
                    sp.setFotonoir(res.getInt("fotonoir"));
                    sp.setFotorgb(res.getInt("fotorgb"));
                    sp.setEstado(res.getInt("estado"));
                    
                    GregorianCalendar fechaHoy = new GregorianCalendar();
                    GregorianCalendar config = new GregorianCalendar();
                    fechaHoy.setTime(new Date());
                    Date datePrevia = addDays(sp.getFecha(), -1);
                    Date datePost = addDays(sp.getFecha(), 1);
                    config.setTime(datePrevia);
                    System.out.println(fechaHoy.getTime() + " fechaHoy");
                    System.out.println(config.getTime() + " fecha config");
                    int ts = config.compareTo(fechaHoy);
                    if (ts < 0) {
                        config.setTime(datePost);
                        ts = config.compareTo(fechaHoy);
                        if (ts > 0) {
                            //esta en el rango disponible    
                            if (res.getInt("estado") == 0) {
                                sp.setStateShow("Disponible");
                                sp.setDisponibilidad(1);
                            } else {
                                sp.setStateShow("Procesado");
                                sp.setDisponibilidad(2);
                            }

                        } else {
                            //rango pasado
                            
                            if (res.getInt("estado") == 0) {
                                sp.setStateShow("Perdido");
                                sp.setDisponibilidad(3);
                            } else {
                                sp.setStateShow("Finalizado");
                                sp.setDisponibilidad(4);
                            }
                        }
                    } else {
                        sp.setDisponibilidad(0);
                        sp.setStateShow("No disponible");
                        //aun no se llega la fecha
                    }
                    resultado.add(sp);
                }
                sentencia.close();
                con.close();
            } catch (Exception ex) {
                System.out.println("hubo un error   " + ex.getMessage());
            }
        }

        return resultado;
    }

    public void cerrarConexion() {
        try {
            
            sentencia.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("hubo un error   " + ex.getMessage());
        }

    }

    public Date addDays(Date fecha, int dias) {
        Date dato = fecha;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fecha);
        gc.add(Calendar.DAY_OF_MONTH, dias);
        dato = gc.getTime();
        return dato;
    }

    public Date addHours(Date fecha, int horas) {
        Date dato;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fecha);
        gc.add(Calendar.HOUR_OF_DAY, horas);
        dato = gc.getTime();
        return dato;
    }

    public boolean updateSubProceso(SubProceso subproceso,String campo,String valor) {
        if (sentencia != null) {
            try {
                
                int res = sentencia.executeUpdate("UPDATE SubProceso SET "+campo+" = "+valor+" WHERE id = '" + subproceso.getId() + "' ");
                System.out.println(res);
                sentencia.close();
                con.close();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(HandSubProcess.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }
}
