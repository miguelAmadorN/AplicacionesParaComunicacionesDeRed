/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

/**
 *
 * @author miguel
 */
public class Pais {

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EmpresaDeEnvio[] getPaqueterias() {
        return paqueterias;
    }

    public void setPaqueterias(EmpresaDeEnvio[] paqueterias) {
        this.paqueterias = paqueterias;
    }
    private String nombre;
    private EmpresaDeEnvio paqueterias[];

    public Pais(String nombre, EmpresaDeEnvio[] paqueterias) {
        this.nombre = nombre;
        this.paqueterias = paqueterias;
    }

    public Pais() {
    }
    
    
}
