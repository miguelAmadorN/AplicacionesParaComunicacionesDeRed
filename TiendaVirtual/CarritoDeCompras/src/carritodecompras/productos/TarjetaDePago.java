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
public class TarjetaDePago implements Serializable{

    public String getNumeroDeTajeta() {
        return NumeroDeTajeta;
    }

    public void setNumeroDeTajeta(String NumeroDeTajeta) {
        this.NumeroDeTajeta = NumeroDeTajeta;
    }

    public String getCodigoDeSeguridad() {
        return CodigoDeSeguridad;
    }

    public void setCodigoDeSeguridad(String CodigoDeSeguridad) {
        this.CodigoDeSeguridad = CodigoDeSeguridad;
    }

    public String getNombreTitular() {
        return NombreTitular;
    }

    public void setNombreTitular(String NombreTitular) {
        this.NombreTitular = NombreTitular;
    }

    public String getApellidosTitular() {
        return ApellidosTitular;
    }

    public void setApellidosTitular(String ApellidosTitular) {
        this.ApellidosTitular = ApellidosTitular;
    }

    public short getFechaCaducidadMM() {
        return FechaCaducidadMM;
    }

    public void setFechaCaducidadMM(short FechaCaducidadMM) {
        this.FechaCaducidadMM = FechaCaducidadMM;
    }

    public short getFechaCaducidadYY() {
        return FechaCaducidadYY;
    }

    public void setFechaCaducidadYY(short FechaCaducidadYY) {
        this.FechaCaducidadYY = FechaCaducidadYY;
    }
    private String NumeroDeTajeta;
    private String CodigoDeSeguridad;
    private String NombreTitular;
    private String ApellidosTitular;
    private short FechaCaducidadMM;
    private short FechaCaducidadYY;

    public TarjetaDePago(String NumeroDeTajeta, String CodigoDeSeguridad, String NombreTitular, 
            String ApellidosTitular, short FechaCaducidadMM, short FechaCaducidadYY) 
    {
        this.NumeroDeTajeta = NumeroDeTajeta;
        this.CodigoDeSeguridad = CodigoDeSeguridad;
        this.NombreTitular = NombreTitular;
        this.ApellidosTitular = ApellidosTitular;
        this.FechaCaducidadMM = FechaCaducidadMM;
        this.FechaCaducidadYY = FechaCaducidadYY;
    }

    public TarjetaDePago() {
    }
    
    
    
}
