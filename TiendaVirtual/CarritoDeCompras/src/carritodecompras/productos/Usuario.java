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
public class Usuario implements Serializable{

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InformacionDeEnvio getInformacionDeEnvio() {
        return informacionDeEnvio;
    }

    public void setInformacionDeEnvio(InformacionDeEnvio informacionDeEnvio) {
        this.informacionDeEnvio = informacionDeEnvio;
    }

    public EmpresaDeEnvio getEmpresaDeEnvio() {
        return empresaDeEnvio;
    }

    public void setEmpresaDeEnvio(EmpresaDeEnvio empresaDeEnvio) {
        this.empresaDeEnvio = empresaDeEnvio;
    }

    public TarjetaDePago getTarjetaDePago() {
        return tarjetaDePago;
    }

    public void setTarjetaDePago(TarjetaDePago tarjetaDePago) {
        this.tarjetaDePago = tarjetaDePago;
    }
    
    private String id;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String email;
    private String telefono;
    private String password;
    private InformacionDeEnvio informacionDeEnvio;
    private EmpresaDeEnvio empresaDeEnvio;
    private TarjetaDePago tarjetaDePago;

    public Usuario(String id, String nombres, String primerApellido, String segundoApellido, 
            String email, String telefono, String password, InformacionDeEnvio informacionDeEnvio, 
            EmpresaDeEnvio empresaDeEnvio, TarjetaDePago tarjetaDePago) 
    {
        this.id = id;
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.informacionDeEnvio = informacionDeEnvio;
        this.empresaDeEnvio = empresaDeEnvio;
        this.tarjetaDePago = tarjetaDePago;
    }
    
    public Usuario() {
    }
    
    
}
