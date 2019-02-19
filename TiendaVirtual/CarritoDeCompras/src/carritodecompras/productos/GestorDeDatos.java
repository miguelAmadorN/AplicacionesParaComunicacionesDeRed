/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import carritodecompras.productos.Categoria;
import carritodecompras.productos.CategoriaSocket;
import static carritodecompras.productos.ControladorPaqueteria.RUTA_PAQUETERIA;
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
    private final String RUTA_CELULARES = "Archivos/Productos/Categorias/Telefonia/Telefonia.out";
    /**
     * Agregar las demás rutas que hagan falta para cada categoria 
     */
    
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
                obtenerProductosDeUnaCategoria(oos, op, op.getParametros());
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
                obntenerEmpresasDeEnvio(oos);
                break;
                
            default:
                break;
        }
    }
    
    private String obtenerRutaDeCategoria(final String CATEGORIA)
    {
        switch(CATEGORIA)
        {
            case TELEFONIA:
                return RUTA_CELULARES;
            default:
                return null;
        }
        
    }
       
    private void obntenerEmpresasDeEnvio(ObjectOutputStream oos) throws IOException, ClassNotFoundException
    {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_PAQUETERIA));
        ListaEmpresasDeEnvio lp = (ListaEmpresasDeEnvio) in.readObject();
        //Enviar al cliente
        oos.writeObject(lp);
        oos.flush();
    }
    
    private void obtenerProductosDeUnaCategoria(ObjectOutputStream oos, Operacion op, final String CATEGORIA)
    throws IOException, ClassNotFoundException 
    {
         System.out.println("Serializando... \n");
        Producto pa = new ProductoArchivo(1,"Xiaomi", "Bonito...", 2.61, 100,
                            new String[]{"Azul", "Verde", "Dorado"}, new String []{"mi 6x", "mi a2"},
                            new String[]{"Archivos/Productos/Categorias/Telefonia/Xiaomi/xiaomi1.jpg",
                                "Archivos/Productos/Categorias/Telefonia/Xiaomi/xiaomi2.jpg"}, 
                                null, "Celulares", new String[]{"Gama media", "Lujo"});
        
        Producto pa2 = new ProductoArchivo(1,"Xiaomi", "Bonito...", 2.61, 100,
                            new String[]{"Azul", "Verde", "Dorado"}, new String []{"mi 6x", "mi a2"},
                            new String[]{"Archivos/Productos/Categorias/Telefonia/Xiaomi/xiaomi1.jpg",
                                "Archivos/Productos/Categorias/Telefonia/Xiaomi/xiaomi2.jpg"}, 
                                null, "Celulares", new String[]{"Gama media", "Lujo"});
        
        ListaProductos lp = new ListaProductos();
        lp.add(pa);
        lp.add(pa2);
        
        
        ObjectOutputStream o = new ObjectOutputStream(
                new FileOutputStream(obtenerRutaDeCategoria(CATEGORIA)));
        o.writeObject(lp);
        o.close();
        /**
         * Lo anterior en este metodo ya debe de estar implementado una carpeta por categoria
         * La carpeta donde está almacenada la informacion es archivos
         */
        System.out.println("\nRecuperando objeto...\n");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(obtenerRutaDeCategoria(CATEGORIA)));
        lp = (ListaProductos) in.readObject();
        //Enviar al cliente
        oos.writeObject(lp);
        oos.flush();
        
        
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
