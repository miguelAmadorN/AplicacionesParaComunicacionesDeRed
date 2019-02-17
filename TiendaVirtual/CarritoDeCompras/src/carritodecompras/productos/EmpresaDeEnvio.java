/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import java.io.Serializable;

/**
 *
 * @author miguel
 */
public class EmpresaDeEnvio implements Serializable{

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempoDeEntrega() {
        return tiempoDeEntrega;
    }

    public void setTiempoDeEntrega(String tiempoDeEntrega) {
        this.tiempoDeEntrega = tiempoDeEntrega;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
    private String nombre;
    private String tiempoDeEntrega;
    private double costo;
    private  boolean disponibilidad;

    public EmpresaDeEnvio(String nombre, String tiempoDeEntrega, double costo, boolean disponibilidad) {
        this.nombre = nombre;
        this.tiempoDeEntrega = tiempoDeEntrega;
        this.costo = costo;
        this.disponibilidad = disponibilidad;
    }

    public EmpresaDeEnvio() {
    }
        
    
}
