/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author miguel
 */
public class main {

    /**
     * 
     * PARA HACER PRUEBAS  
     */
   public static void main(String[] args)throws IOException, ClassNotFoundException {
       
   /**Ejemplo para categorias FUNCIONANDO
        System.out.println("Creando el objeto... \n");
        Categoria ca = new CategoriaSocket("Celulares",
                                            "Archivos/Categorias/Celulares.jpg",
                                            "jpg");
        
        ObjectOutputStream o = new ObjectOutputStream(
                               new FileOutputStream("Archivos/Categorias/categorias.out"));
        o.writeObject(ca);
        o.close();
        System.out.println("\nRecuperando objeto...\n");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Archivos/Categorias/categorias.out"));
        ca = (CategoriaSocket)in.readObject();
        ca.mostrar();
**/
   
       /**Ejemplo para  productos FUNCIONANDO
       System.out.println("Creando el objeto... \n");
       
        Producto pa = new ProductoSocket(1,"Xiaomi", "Bonito...", 2.61, 100,
                            new String[]{"Azul", "Verde", "Dorado"}, new String []{"mi 6x", "mi a2"},
                            new String[]{"Archivos/Productos/Categorias/Telefonia/Xiaomi/xiaomi1.jpg",
                                "Archivos/Productos/Categorias/Telefonia/Xiaomi/xiaomi2.jpg"}, 
                                null, "Celulares", new String[]{"Gama media", "Lujo"});
        
        ObjectOutputStream o = new ObjectOutputStream(
                               new FileOutputStream("Archivos/Productos/Categorias/Telefonia/Telefonia.out"));
        o.writeObject(pa);
        o.close();
        System.out.println("\nRecuperando objeto...\n");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Archivos/Productos/Categorias/Telefonia/Telefonia.out"));
        pa = (Producto)in.readObject();
        pa.mostrar();
    
     **/
       
   }
}
