/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author rodrigo
 */
public class ControladorProducto {
    public static String RUTA_CATEGORIA = "Archivos/Productos/Categorias/";
    String ruta = "", rutaE = "";
    String aux = ".out";
    public ControladorProducto(){}
    
    public boolean agregarProducto(ProductoArchivo producto, String cat) {
        
        ruta = RUTA_CATEGORIA + cat + "/" + cat + aux;
        System.out.println("\nHola desde agregar: " + ruta + "\n");
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta));
            ListaProductos lp = (ListaProductos) in.readObject();
            lp.add(producto);
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(ruta));
            o.writeObject(lp);
            o.close();
        }catch(IOException e){
            return false;
        } catch (ClassNotFoundException ex) {
            return false;
        }finally{           
        }
        ruta = "";
        return true;
    }
    
    public  boolean eliminarProducto(ListaProductos lp, String cat){
        rutaE = RUTA_CATEGORIA + cat + "/" + cat + aux;
        System.out.println("\nHola desde borrar: " + rutaE + "\n");
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(rutaE));
            o.writeObject(lp);
            o.close();
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }finally{
        }
        rutaE = "";
        return true;  
    }
}
