/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import java.awt.image.BufferedImage;
import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author miguel
 */
public class ProductoSocket  extends Producto implements Externalizable{

    public ProductoSocket(int id, String nombre, String descripcion, double precio, 
                           int existencias, String[] colores, String[] materiales, 
                           String[] imagenes, BufferedImage[] imagenesEnBuffer, 
                           String Categoria, String[] PalabrasClave) throws IOException 
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
        this.imagenesEnBuffer = new BufferedImage[imagenes.length];
        for(int i = 0; i < imagenes.length; i++)
             this.imagenesEnBuffer[i] = ImageIO.read(new File(imagenes[i]));
    }
    
    public ProductoSocket(){}
    
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
        oo.writeObject((int)imagenesEnBuffer.length);
        for (int i = 0; i < imagenes.length ; i++) 
        {
            ImageIO.write(imagenesEnBuffer[i], "jpg", (OutputStream) oo);
            oo.writeObject((int)1);
        }
        // png is lossless
        
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
        final int numImgs = (int)oi.readObject();
        imagenesEnBuffer = new BufferedImage[numImgs];
        int a;
        for (int i = 0; i < numImgs; i++) 
        {
            imagenesEnBuffer[i] = (BufferedImage)(ImageIO.read((InputStream)oi));
            a = (int) oi.readObject();
        }
        
       
    }
}
