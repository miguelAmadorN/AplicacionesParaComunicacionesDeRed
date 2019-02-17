/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import java.awt.image.BufferedImage;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * @author miguel
 */
public class ProductoArchivo extends Producto implements Externalizable{

    public ProductoArchivo(int id, String nombre, String descripcion, double precio, 
                           int existencias, String[] colores, String[] materiales, 
                           String[] imagenes, BufferedImage[] imagenesEnBuffer, 
                           String Categoria, String[] PalabrasClave) 
    {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencias = existencias;
        this.colores = colores;
        this.materiales = materiales;
        this.imagenes = imagenes;
        this.Categoria = Categoria;
        this.PalabrasClave = PalabrasClave;
    }

    public ProductoArchivo(){}
    
    @Override
    public void writeExternal(ObjectOutput oo) throws IOException {
        oo.writeObject(id);
        oo.writeObject(nombre);
        oo.writeObject(descripcion);
        oo.writeObject(precio);
        oo.writeObject(existencias);
        oo.writeObject(colores);
        oo.writeObject(materiales);
        oo.writeObject(imagenes);
        oo.writeObject(Categoria);
        oo.writeObject(PalabrasClave);
    }

    @Override
    public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
        setId((int) oi.readObject());
        setNombre((String)oi.readObject());
        setDescripcion((String)oi.readObject()); 
        setPrecio((double) oi.readObject()); 
        setExistencias((int) oi.readObject()); 
        setColores((String[]) oi.readObject()); 
        setMateriales((String[]) oi.readObject());
        setImagenes((String[]) oi.readObject()); 
        setCategoria((String) oi.readObject()); 
        setPalabrasClave((String[]) oi.readObject());
    }
    
}
