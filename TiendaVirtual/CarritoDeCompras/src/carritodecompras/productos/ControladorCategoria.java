/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

//import static carritodecompras.productos.ControladorCategoria.RUTA_CATEGORIA;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author rodrigo
 */
public class ControladorCategoria {
    public static String RUTA_CATEGORIA = "Archivos/Categorias/categorias.out";
    
    public ControladorCategoria() throws IOException{
        File archivo = new File(RUTA_CATEGORIA);
        if(!archivo.exists()){
           ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(RUTA_CATEGORIA));
           CategoriaArchivo c[] = new CategoriaArchivo[1];
           c[0] = new CategoriaArchivo("Deportes", "Archivos/Categorias/Deportes.jpg", "jpg", "Archivos/Productos/Categorias/Deportes/Deportes.out");
           ListaCategoria lc = new ListaCategoria(c);
           o.writeObject(lc);
           o.close();
        }   
    }
    
    public boolean agregarCategoria(CategoriaArchivo categoria, String rutaArchivo, String rutaCarpeta, String cat) {
        try {
            //Para agregar la categoría al archivo categorias.out
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_CATEGORIA));
            ListaCategoria lc = (ListaCategoria) in.readObject();
            lc.add(categoria);
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(RUTA_CATEGORIA));
            o.writeObject(lc);
            o.close();

            //Para crear el archivo categoriaNueva.out en su directorio
            File carpeta = new File(rutaCarpeta);
            carpeta.mkdirs();
            
            File archivoCategoria = new File(rutaArchivo);
            if(!archivoCategoria.exists()){
                ObjectOutputStream op = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
                ProductoArchivo pa[] = new ProductoArchivo[1];
                pa[0] = new ProductoArchivo(1,"CualquierCosa","Veamos",10,100,new String[]{"Azul"},new String[]{"Metal"},
                        new String[]{"Archivos/Productos/Categorias/Deportes/Producto1.jpg"},null,cat,new String[]{"lo que sea","lo que sea"});
                ListaProductos lp = new ListaProductos(pa);
                op.writeObject(lp);
                op.close();
            }
            ControladorProducto cp = new ControladorProducto();
            ObjectInputStream inCat = new ObjectInputStream(new FileInputStream(rutaArchivo));
            ListaProductos lc1 = (ListaProductos)inCat.readObject();
            lc1.remove(0);
            cp.eliminarProducto(lc1, cat);
            inCat.close();
            
        }catch(IOException e){
            return false;
        } catch (ClassNotFoundException ex) {
            return false;
        }finally{           
        }
        return true;
    }
    
    public  boolean eliminarCategoria(ListaCategoria lc){
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(RUTA_CATEGORIA));
            o.writeObject(lc);
            o.close();
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }finally{
        }
        return true;  
    }
}
