/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

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
public class CategoriaSocket extends Categoria implements Externalizable {

    public CategoriaSocket(String nombre, String imagen, String extension) throws IOException {
        super();
        this.nombre = nombre;
        this.imagen = imagen;
        this.extension = extension;
        this.imagenEnBuffer = ImageIO.read(new File(imagen));
    }

    public CategoriaSocket(){ }
    
    @Override
    public void writeExternal(ObjectOutput oo) throws IOException {
        oo.writeObject(getNombre());
        oo.writeObject(getExtension());
        oo.writeObject(getRuta());
        ImageIO.write(this.getImagenEnBuffer(), this.getExtension(), (OutputStream) oo);
    }

    @Override
    public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
        setNombre((String) oi.readObject());
        setExtension((String) oi.readObject());
        setRuta((String)oi.readObject());
        setImagenEnBuffer(ImageIO.read((InputStream) oi));
    }
    
}
