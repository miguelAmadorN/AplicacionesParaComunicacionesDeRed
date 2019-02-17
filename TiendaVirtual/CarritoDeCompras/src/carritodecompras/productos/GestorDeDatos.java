/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import carritodecompras.productos.Categoria;
import carritodecompras.productos.CategoriaSocket;
import static carritodecompras.servidor.IdOperaciones.*;
import carritodecompras.productos.Operacion;
import carritodecompras.productos.Producto;
import carritodecompras.productos.Usuario;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 *
 * @author miguel
 */
public class GestorDeDatos {

    
    private HashMap <String, Usuario> usuarios = new HashMap<String, Usuario>();
    private HashMap <String, Producto> productos = new HashMap<String, Producto>();
 
    
    private GestorDeDatos() {
    }
    
    public static GestorDeDatos getInstance() {
        return GestorDeDatosHolder.INSTANCE;
    }
    
    private static class GestorDeDatosHolder {

        private static final GestorDeDatos INSTANCE = new GestorDeDatos();
    }
    
    /**
     * 
     * @param oos
     * @param op un objeto que encupsula un id para la oprecion solicitada
     * las connstantes se encuentran en la clase IdOperaciones
     */
    public void ejecutarOperacion(ObjectOutputStream oos, Operacion op) throws IOException, ClassNotFoundException
    {
        switch(op.getOperacion())
        {
            case OBTENER_CATEGORIAS:
                obtenerCategorias(oos, op);
                break;
            case OBTENER_PRODUCTOS_CATEGORIA:
                break;
            case REGISTAR_USUARIO:
                break;
            case IDENTIFICAR_USUARIO:
                break;
            case COMPRAR:
                break;
            case BUSCAR_PRODUCTO:
                break;
            case OBTENER_EMPRESAS_DE_ENVIO:
                break;
                
            default:
                break;
        }
    }
    
    
    private void obtenerCategorias(ObjectOutputStream oos, Operacion op) 
            throws IOException, ClassNotFoundException 
    {
        System.out.println("Serializando... \n");
        Categoria ca = new CategoriaArchivo("Celulares",
                "Archivos/Categorias/Celulares.jpg",
                "jpg");
        
        Categoria ca2 = new CategoriaArchivo("Informatica",
                "Archivos/Categorias/Informatica.png",
                "png");
        
        ListaCategoria lc = new ListaCategoria();
        lc.add(ca);
        lc.add(ca2);
        
        
        ObjectOutputStream o = new ObjectOutputStream(
                new FileOutputStream("Archivos/Categorias/categorias.out"));
        o.writeObject(lc);
        o.close();
        /**
         * Lo anterior en este metodo ya debe de estar implementado una carpeta por categoria
         * La carpeta donde está almacenada la informacion es archivos
         */
        System.out.println("\nRecuperando objeto...\n");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Archivos/Categorias/categorias.out"));
        lc = (ListaCategoria) in.readObject();
        //Enviar al cliente
        oos.writeObject(lc);
        oos.flush();
    }
}
