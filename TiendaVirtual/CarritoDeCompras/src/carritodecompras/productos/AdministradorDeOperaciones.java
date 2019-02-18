/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import static carritodecompras.servidor.IdOperaciones.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author miguel
 */
public class AdministradorDeOperaciones {
   /**
    * 
    * @param oos
    * @param ois
    * @return Todas las categorias de productos la tienda
    * @throws IOException
    * @throws ClassNotFoundException 
    */
    public static ListaCategoria obtenerTodasLasCategorias(ObjectOutputStream oos, ObjectInputStream ois) 
            throws IOException, ClassNotFoundException
    {
        oos.writeObject(new Operacion(OBTENER_CATEGORIAS, null));
        oos.flush();
        return (ListaCategoria) ois.readObject();
    }
    
    /**
     * 
     * @param oos
     * @param ois
     * @param usuario un objeto usuario con su nick name y password nadamás 
     * @return null si el usuario no extiste, un objeto usuario con todos los campos llenos si
     * ya estaba registrado
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Usuario identificarUsuario(ObjectOutputStream oos, ObjectInputStream ois, Usuario usuario) 
            throws IOException, ClassNotFoundException
    {
        oos.writeObject(new Operacion(IDENTIFICAR_USUARIO,null));
        oos.flush();
        oos.writeObject(usuario);
        oos.flush();
        return (Usuario) ois.readObject();
    }
    
    /**
     * 
     * @param oos
     * @param ois
     * @param CATEGORIA Rodrigo las debe definir en IdOperaciones como el ejemplo de celulares
     * @return un objeto con la lista de productos de la categoria solucitada
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static ListaProductos obtenerProductosDeUnaCategoria(ObjectOutputStream oos, 
            ObjectInputStream ois, final String CATEGORIA) throws IOException, ClassNotFoundException
    {
        oos.writeObject(new Operacion(OBTENER_PRODUCTOS_CATEGORIA, CATEGORIA));
        oos.flush();
        return (ListaProductos) ois.readObject();
    }
    
    /**
     * 
     * @param oos
     * @param ois
     * @return un objeto ListaEmpresasDeEnvio con objetos de EmpresaDeEnvio para las paqueterias
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static ListaEmpresasDeEnvio obtenerEmpresasDeEnvio(ObjectOutputStream oos, ObjectInputStream ois) 
            throws IOException, ClassNotFoundException
    {
        oos.writeObject(new Operacion(OBTENER_EMPRESAS_DE_ENVIO, null));
        oos.flush();
        return (ListaEmpresasDeEnvio) ois.readObject();
        
    }
    
    /**
     * 
     * @param oos
     * @param ois
     * @param usuario objeto con toda la informacion para hacer su registro
     * @return true si se hizo con exito; false si hubo algún error
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static boolean registrarUsuario(ObjectOutputStream oos, ObjectInputStream ois, Usuario usuario) 
            throws IOException, ClassNotFoundException
    {
        oos.writeObject(new Operacion(REGISTAR_USUARIO, null));
        oos.flush();
        oos.writeObject(usuario);
        oos.flush();
        return (boolean) ois.readObject();  
    }
    
    /**
     * 
     * @param oos
     * @param ois
     * @param carrito objeto que lleva la informacion para hacer la compra y que el servidor genere 
     * el pedf y envie el correo, debe llevar el objeto usuario  o por lo menos el id
     * @return true si la compra se hizo con exito; false si hubo algún error
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static boolean comprar(ObjectOutputStream oos, ObjectInputStream ois, CarritoCompras carrito) 
            throws IOException, ClassNotFoundException
    {
        oos.writeObject(new Operacion(COMPRAR, null));
        oos.flush();
        oos.writeObject(carrito);
        oos.flush();
        return (boolean) ois.readObject(); 
    }
    
    /**
     * 
     * @param oos
     * @param ois
     * @param busqueda objeto que encapsula la información para realzar la busqueda en el servidor
     * @return un objeto con la lista de productos que se encontrarron con las especificaciones
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static ListaProductos buscarProducto(ObjectOutputStream oos, ObjectInputStream ois, 
            BusquedaProductos busqueda) throws IOException, ClassNotFoundException
    {
        oos.writeObject(new Operacion(BUSCAR_PRODUCTO, null));
        oos.flush();
        oos.writeObject(busqueda);
        oos.flush();
        return (ListaProductos) ois.readObject(); 
    }
    
    
    
    
    
}
