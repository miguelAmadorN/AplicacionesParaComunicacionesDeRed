/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import Correo.CorreoAdjunto;
import carritodecompras.productos.Categoria;
import carritodecompras.productos.CategoriaSocket;
import static carritodecompras.productos.ControladorPaqueteria.RUTA_PAQUETERIA;
import static carritodecompras.servidor.IdOperaciones.*;
import carritodecompras.productos.Operacion;
import carritodecompras.productos.Producto;
import carritodecompras.productos.Usuario;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import pdf.GeneradorPdf;
import static pdf.GeneradorPdf.RUTA_RECIBOS;

/**
 *
 * @author miguel
 */
public class GestorDeDatos {
    
    private final String TELEFONIA = "Archivos/Productos/Categorias/Telefonia/Telefonia.out";
    private final String DEPORTES = "Archivos/Productos/Categorias/Deportes/Deportes.out";
    private final String INFORMATICA = "Archivos/Productos/Categorias/Informatica/Informatica.out";
    private final String TELEVISORES = "Archivos/Productos/Categorias/Televisores/Televisores.out";
    private final String VIDEOJUEGOS = "Archivos/Productos/Categorias/Videojuegos/Videojuegos.out";
    private final String LIBROS = "Archivos/Productos/Categorias/Libros/Libros.out";
    private final String AUTOS    = "Archivos/Productos/Categorias/Autos/Autos.out";
    private final String CALZADO  = "Archivos/Productos/Categorias/Calzado/Calzado.out";
    private final String JOYERIA  = "Archivos/Productos/Categorias/Joyeria/Joyeria.out";
    private final String MODA     = "Archivos/Productos/Categorias/Moda/Moda.out";
    private final String SALUD    = "Archivos/Productos/Categorias/Salud/Salud.out";
    
    
    
    
    private final String RUTA_CATEGORIAS = "Archivos/Categorias/categorias.out";
    /**
     * Agregar las demás rutas que hagan falta para cada categoria 
     */
    
   
    
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
    public boolean ejecutarOperacion(ObjectOutputStream oos, Operacion op, ObjectInputStream ois) 
            throws IOException, ClassNotFoundException
    {
        switch(op.getOperacion())
        {
            case OBTENER_CATEGORIAS:
                obtenerCategorias(oos, op);
                return true;
            case OBTENER_PRODUCTOS_CATEGORIA:
                obtenerProductosDeUnaCategoria(oos, op.getParametros());
                return true;
            case REGISTAR_USUARIO:
                return true;
            case IDENTIFICAR_USUARIO:
                return true;
            case COMPRAR:
                comprar(oos, ois);
                return true;
            case BUSCAR_PRODUCTO:
                return true;
            case OBTENER_EMPRESAS_DE_ENVIO:
                obtenerEmpresasDeEnvio(oos);
                return true;
            case TERMINAR_CONEXION:
                return false;
                
            default:
                return true;
        }
    }
        
    private void comprar(ObjectOutputStream oos, ObjectInputStream ois) throws IOException, FileNotFoundException, ClassNotFoundException 
    {
         CarritoCompras cc = null;
        try {
            cc = (CarritoCompras) ois.readObject();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            String nombre = cc.getUsuario().getNombres() + cc.getUsuario().getPrimerApellido()
                    + c.getTimeInMillis() + ".pdf";

            GeneradorPdf.crearReciboDeCompra(cc, nombre);     
            
            CorreoAdjunto ca = new CorreoAdjunto("CML.Express.ventas@gmail.com", "cmlexpress", RUTA_RECIBOS + nombre,
                    "Recibo.pdf", cc.getUsuario().getEmail(), "Recibo de compra", "Gracias por comprar con nosostros");
            ca.SendMail();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        oos.writeBoolean(true);
        oos.flush();
        
        if(cc != null)
        {
            int cantidad = cc.getProductoCompra().length;
            for(int i = 0; i < cantidad; i++ )
            {
                actualizarInventario(cc.getProductoCompra()[i]);
            }
        }
    }
    
    
    private void obtenerEmpresasDeEnvio(ObjectOutputStream oos) throws IOException, ClassNotFoundException
    {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_PAQUETERIA));
        ListaEmpresasDeEnvio lp = (ListaEmpresasDeEnvio) in.readObject();
        //Enviar al cliente
        oos.writeObject(lp);
        oos.flush();
    }
    
    private void obtenerProductosDeUnaCategoria(ObjectOutputStream oos, final String RUTA_CATEGORIA)
    throws IOException, ClassNotFoundException 
    {
        System.out.println("\nRecuperando objeto...\n");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_CATEGORIA));
        ListaProductos lp = (ListaProductos) in.readObject();
        //Enviar al cliente
        oos.writeObject(lp);
        oos.flush();
    }
    
    private void obtenerCategorias(ObjectOutputStream oos, Operacion op) 
            throws IOException, ClassNotFoundException 
    {
        
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_CATEGORIAS));
        ListaCategoria lc = (ListaCategoria) in.readObject();
        //Enviar al cliente
        oos.writeObject(lc);
        oos.flush();
    }
    
    /*
    Modificar actualizarInventario para hacerlo eficiente 
    */
    private void actualizarInventario( ProductoCompra pc) 
    {
        try {
            int i = 0;
            String ruta = null;
            ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(RUTA_CATEGORIAS));
            ListaCategoria lc = (ListaCategoria) in1.readObject();
            for(; i < lc.getSize(); i++ )
            {
                if(lc.get(i).getNombre().equals(pc.getProducto().getCategoria()))
                {
                    ruta = lc.get(i).getRuta();
                    break;
                }
            }
            
            if (ruta != null) {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta));
                ListaProductos lp = (ListaProductos) in.readObject();
                for (i = 0; i < lp.getSize(); i++) {
                    if (lp.get(i).getId() == pc.getProducto().getId()) {
                        lp.get(i).setExistencias(lp.get(i).getExistencias() - pc.getCantidad());
                        break;
                    }
                }

                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(ruta));
                o.writeObject(lp);
                o.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestorDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
}