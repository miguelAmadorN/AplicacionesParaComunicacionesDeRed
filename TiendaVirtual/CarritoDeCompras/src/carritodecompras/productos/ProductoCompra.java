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
public class ProductoCompra implements Serializable {

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    protected Producto producto;
    protected int cantidad;

    public ProductoCompra(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ProductoCompra() {
    }
    
    
    
}
