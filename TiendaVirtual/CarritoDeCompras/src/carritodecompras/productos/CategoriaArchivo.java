/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.imageio.ImageIO;

/**
 *
 * @author miguel
 */
public class CategoriaArchivo extends Categoria implements Externalizable{
    
    public CategoriaArchivo(String nombre, String imagen, String extension) throws IOException {
        super();
        this.nombre = nombre;
        this.imagen = imagen;
        this.extension = extension;
    }

    public CategoriaArchivo(){}
    
    @Override
    public void writeExternal(ObjectOutput oo) throws IOException {
        oo.writeObject(getNombre());
        oo.writeObject(getImagen());
        oo.writeObject(getExtension());
    }

    @Override
    public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
        setNombre((String) oi.readObject());
        setImagen((String) oi.readObject());
        setExtension((String) oi.readObject());
        setImagenEnBuffer(ImageIO.read(new File(getImagen())));
    }
}
