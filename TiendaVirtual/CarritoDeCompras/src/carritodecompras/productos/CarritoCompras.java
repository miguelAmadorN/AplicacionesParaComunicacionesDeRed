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
public class CarritoCompras implements Serializable{

    public ProductoCompra[] getProductoCompra() {
        return productoCompra;
    }

    public void setProductoCompra(ProductoCompra[] productoCompra) {
        this.productoCompra = productoCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private ProductoCompra productoCompra[];
    private Usuario usuario;
    
    
    public CarritoCompras(ProductoCompra[] productoCompra, Usuario usuario) {
        this.productoCompra = productoCompra;
        this.usuario = usuario;
    }

    public CarritoCompras() {
    }
    
    
    
    
    
}
