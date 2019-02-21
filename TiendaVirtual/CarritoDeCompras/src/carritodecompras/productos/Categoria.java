/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author miguel
 */
public class Categoria{

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public BufferedImage getImagenEnBuffer() {
        return imagenEnBuffer;
    }

    public void setImagenEnBuffer(BufferedImage imagenEnBuffer) {
        this.imagenEnBuffer = imagenEnBuffer;
    }
    
    String nombre;//Nombre de la categoria
    String imagen;//Nombre de la imagen
    String extension;
    String ruta;
    BufferedImage imagenEnBuffer;

    
    
    public void mostrar()
    {
        System.out.print(imagen +"\n");
        System.out.print(nombre +"\n");
        System.out.print(extension +"\n");
        if(imagenEnBuffer != null)
        {
            JFrame frame = new JFrame();
            frame.getContentPane().setLayout(new FlowLayout());
            frame.getContentPane().add(new JLabel(new ImageIcon(imagenEnBuffer)));
            frame.pack();
            frame.setVisible(true);
        }
    }

    
   
}
