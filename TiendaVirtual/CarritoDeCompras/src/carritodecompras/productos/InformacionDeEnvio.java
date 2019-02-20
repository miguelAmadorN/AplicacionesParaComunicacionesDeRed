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
public class InformacionDeEnvio implements Serializable{

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreYApellidos() {
        return nombreYApellidos;
    }

    public void setNombreYApellidos(String nombreYApellidos) {
        this.nombreYApellidos = nombreYApellidos;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private String email;
    private String nombreYApellidos;
    private String pais;
    private String direccion;
    private String telefono;

    public InformacionDeEnvio(String email, String nombreYApellidos, String pais, String direccion, 
            String telefono) 
    {
        this.email = email;
        this.nombreYApellidos = nombreYApellidos;
        this.pais = pais;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    
    public InformacionDeEnvio() {
    }


    
    
}
