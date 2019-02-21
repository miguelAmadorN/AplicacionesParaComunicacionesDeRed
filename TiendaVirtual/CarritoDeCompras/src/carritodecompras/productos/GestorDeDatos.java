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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.HashMap;
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
        
    private void comprar(ObjectOutputStream oos, ObjectInputStream ois) throws IOException 
    {
        try {
            CarritoCompras cc = (CarritoCompras) ois.readObject();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            String nombre = cc.getUsuario().getNombres() + cc.getUsuario().getPrimerApellido()
                    + c.getTimeInMillis() + ".pdf";

            GeneradorPdf.crearReciboDeCompra(cc, nombre);

            
            oos.writeBoolean(true);
            oos.flush();
            CorreoAdjunto ca = new CorreoAdjunto("CML.Express.ventas@gmail.com", "cmlexpress", RUTA_RECIBOS + nombre,
                    "Recibo.pdf", cc.getUsuario().getEmail(), "Recibo de compra", "Gracias por comprar con nosostros");
            ca.SendMail();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            oos.writeBoolean(false);
            oos.flush();
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
         System.out.println("Serializando... \n");
        //Productos de deportes
        Producto pd1 = new ProductoArchivo(1,"Gimnasio Multifuncional","Athletic works con 44kg de peso",5990.00,100,
                new String[]{"Negro","Azul"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Deportes/Producto1.jpg"},null,
                "Deportes",new String[]{"Ejercicio","Aparatos"});
        
        Producto pd2 = new ProductoArchivo(2,"Remadora magnetica","Con contador fitness",10999.00,100,
                new String[]{"Negro","Azul"},new String[]{"Acero"},
                new String[]{"Archivos/Productos/Categorias/Deportes/Producto2.jpg"},null,"Deportes",
                new String[]{"Ejercicio","Aparatos"});
        
        Producto pd3 = new ProductoArchivo(3,"Eliptica magnetica","Body Fit",2299.00,100,
                new String[]{"Negro","Azul"},new String[]{"Acero"},
                new String[]{"Archivos/Productos/Categorias/Deportes/Producto3.jpg"},null,"Deportes",
                new String[]{"Ejercicio","Aparatos"});
        
        Producto pd4 = new ProductoArchivo(4,"Trampolin X trender","Con red de seguridad",3999.00,100,
                new String[]{"Negro","Azul"},new String[]{"Acero", "Superficie de polietileno"},
                new String[]{"Archivos/Productos/Categorias/Deportes/Producto4.jpg"},null,"Deportes",
                new String[]{"Trampolin","Juegos"});
        
        Producto pd5 = new ProductoArchivo(5,"Eliptica magnetica","Body Fit",2299.00,100,
                new String[]{"Negro","Azul"},new String[]{"Acero"},
                new String[]{"Archivos/Productos/Categorias/Deportes/Producto5.jpg"},null,"Deportes",
                new String[]{"Ejercicio","Aparatos"});
        
        //Productos de Informatica
        Producto pi1 = new ProductoArchivo(1,"Laptop Acer Aspire 3 A315-51-32L5","Intel Core i3 4GB RAM 1TB DD",8919.00,100,
                new String[]{"Negro","Azul"},new String[]{"Plastico","Metal"},
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto1.jpg"},null,
                "Informatica",new String[]{"Computadoras","Acer"});
        
        Producto pi2 = new ProductoArchivo(2,"Laptop Gamer Asus TUF FX504","i5 8GB RAM 1TB GTX1050 Asus FX504GD-ES51",26590.00,100,
                new String[]{"Negro","Azul"},new String[]{"Plastico","Metal"},
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto2.jpg"},null,
                "Informatica",new String[]{"Computadoras","Asus"});
        
        Producto pi3 = new ProductoArchivo(3,"Laptop HP Stream 14","Celeron N3060 4GB 32GB -Azul HP 14-ax010nr",9289.00,100,
                new String[]{"Negro","Azul"},new String[]{"Plastico","Metal"},
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto3.jpg"},null,
                "Informatica",new String[]{"Computadoras","HP"});
        
        Producto pi4 = new ProductoArchivo(4,"Laptop Lenovo 81FB0045LM","AMD Ryzen 3 2200U 8GB RAM 2TB DD",12499.00,100,
                new String[]{"Negro","Azul"},new String[]{"Plastico","Metal"},
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto4.jpg"},null,
                "Informatica",new String[]{"Computadoras","Lenovo"});
        
        Producto pi5 = new ProductoArchivo(5,"Laptop Hp Stream","4gb Ram 32gb Emmc -morado HP 11-y020nr",7020.00,100,
                new String[]{"Negro","Azul","Morado"},new String[]{"Plastico","Metal"},
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto5.jpg"},null,
                "Informatica",new String[]{"Computadoras","HP"});
        
        //Productos de Libros
        Producto pl1 = new ProductoArchivo(1,"Harry Potter y el Prisionero de Azkaban","Autor: J. K. Rowling ",350.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto1.jpg"},null,
                "Libros",new String[]{"Harry Potter","Libreria Gandhi"});
        
        Producto pl2 = new ProductoArchivo(2,"CATAROS E INQUISICION","Marca Catedra ",431.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto2.jpg"},null,
                "Libros",new String[]{"Inquisicion","Libreria Gandhi"});
        
        Producto pl3 = new ProductoArchivo(3,"QUINTA MUJER","Editores Rustico ",418.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto3.jpg"},null,
                "Libros",new String[]{"Tusquets","Libreria Gandhi"});
        
        Producto pl4 = new ProductoArchivo(4,"LAS CONFESIONES DEL JOVEN NERON","Tapa dura",499.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto4.jpg"},null,
                "Libros",new String[]{"Tapa dura","Libreria Gandhi"});
        
        Producto pl5 = new ProductoArchivo(5,"El enigma","Editores Rustico",388.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto5.jpg"},null,
                "Libros",new String[]{"Spinoza","Libreria Gandhi"});
        
        //Productos de Telefonia
        Producto pt1 = new ProductoArchivo(1,"Smartphone Samsung Galaxy S8","64GB",12999.00,100,
                new String[]{"Azul"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto1.jpg"},null,
                "Telefonia",new String[]{"Celulares","Samsung"});
        
        Producto pt2 = new ProductoArchivo(2,"XIAOMI Redmi Note 6 Pro","64gb 4gb Dual Sim",4799.00,100,
                new String[]{"Azul"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto2.jpg"},null,
                "Telefonia",new String[]{"Celulares","XIAOMI"});
        
        Producto pt3 = new ProductoArchivo(3,"Smartphone Samsung Galaxy S8","64GB",9999.00,100,
                new String[]{"Dorado"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto3.jpg"},null,
                "Telefonia",new String[]{"Celulares","Samsung"});
        
        Producto pt4 = new ProductoArchivo(4,"Mi A2 Lite Dual","32 GB XIAOMI",4438.00,100,
                new String[]{"Azul"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto4.jpg"},null,
                "Telefonia",new String[]{"Celulares","Xiaomi"});
        
        Producto pt5 = new ProductoArchivo(5,"Smartphone Motorola Moto G6 Play","32GB",4998.00,100,
                new String[]{"Dorado"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto5.jpg"},null,
                "Telefonia",new String[]{"Celulares","Motorola"});
        
        //Productos de Televisores
        Producto ptv1 = new ProductoArchivo(1,"TV Samsung","32 Pulgadas 720p HD Smart TV LED",3990.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto1.jpg"},null,
                "Televisores",new String[]{"Samsung","TV"});
        
        Producto ptv2 = new ProductoArchivo(2,"TV Atvio","32 Pulgadas 720p HD LED ATV32",2999.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto2.jpg"},null,
                "Televisores",new String[]{"Atvio","TV"});
        
        Producto ptv3 = new ProductoArchivo(3,"TV Sharp","32 Pulgadas 720p HD Smart TV LED",4499.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto3.jpg"},null,
                "Televisores",new String[]{"Sharp","TV"});
        
        Producto ptv4 = new ProductoArchivo(4,"TV Hisense","50 pulgadasSmart TV Ultra HD Hisense 50H8E",9990.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto4.jpg"},null,
                "Televisores",new String[]{"Hisense","TV"});
        
        Producto ptv5 = new ProductoArchivo(5,"TV Samsung","Smart TV 40 Pulgadas LED FULL HD WiFi USB 60",6999.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto5.jpg"},null,
                "Televisores",new String[]{"Samsung","TV"});
        
        //Productos de Videojuegos
        Producto pv1 = new ProductoArchivo(1,"Consola Xbox One S","TB Fortnite y Gears of War 4",6999.00,100,
                new String[]{"Negro"},new String[]{"Acero"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto1.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","Xbox"});
        
        Producto pv2 = new ProductoArchivo(2,"Consola PlayStation 4 Pro","1 TB Stand Alone",9999.00,100,
                new String[]{"Negro"},new String[]{"Acero"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto2.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","PlayStation"});
        
        Producto pv3 = new ProductoArchivo(3,"Consola PlayStation 4 Pro","1 TB Stand Alone",9999.00,100,
                new String[]{"Negro"},new String[]{"Acero"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto3.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","PlayStation"});
        Producto pv4 = new ProductoArchivo(4,"Battlefield V PlayStation 4","Marca PlayStation 4",799.00,100,
                new String[]{"Azul"},new String[]{"CD"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto4.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","Battlefield V"});
        
        Producto pv5 = new ProductoArchivo(5,"Madden NFL 19 Xbox One","Xbox",899.00,100,
                new String[]{"Verde"},new String[]{"CD"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto5.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","Madden NFL"});
        
        ListaProductos lp = new ListaProductos();
        System.out.print(RUTA_CATEGORIA);
        switch(RUTA_CATEGORIA){
            case DEPORTES:
                
                lp.add(pd1);
                lp.add(pd2);
                lp.add(pd3);
                lp.add(pd4);
                lp.add(pd5);
                
                break;
                
            case INFORMATICA:
                
                lp.add(pi1);
                lp.add(pi2);
                lp.add(pi3);
                lp.add(pi4);
                lp.add(pi5);

                break;
                
            case LIBROS:
                
                lp.add(pl1);
                lp.add(pl2);
                lp.add(pl3);
                lp.add(pl4);
                lp.add(pl5);

                break;
                
            case TELEFONIA:
                
                lp.add(pt1);
                lp.add(pt2);
                lp.add(pt3);
                lp.add(pt4);
                lp.add(pt5);

                break;
                
            case TELEVISORES:
                
                lp.add(ptv1);
                lp.add(ptv2);
                lp.add(ptv3);
                lp.add(ptv4);
                lp.add(ptv5);

                break;
                
            case VIDEOJUEGOS:
                
                lp.add(pv1);
                lp.add(pv2);
                lp.add(pv3);
                lp.add(pv4);
                lp.add(pv5);
                break;
                
            default:
                break;
        }
        
        
        ObjectOutputStream o = new ObjectOutputStream(
                new FileOutputStream(RUTA_CATEGORIA));
        o.writeObject(lp);
        o.close();
        /**
         * Lo anterior en este metodo ya debe de estar implementado una carpeta por categoria
         * La carpeta donde está almacenada la informacion es archivos
         */
        System.out.println("\nRecuperando objeto...\n");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_CATEGORIA));
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
                "jpg",
                 "Archivos/Productos/Categorias/Telefonia/Telefonia.out"
        );
        
        Categoria ca2 = new CategoriaArchivo("Informatica",
                "Archivos/Categorias/Informatica.png",
                "png",
                "Archivos/Productos/Categorias/Informatica/Informatica.out"
        );
        
        Categoria ca3 = new CategoriaArchivo("Deportes", "Archivos/Categorias/Deportes.jpg", "jpg",
        "Archivos/Productos/Categorias/Deportes/Deportes.out");
        
        Categoria ca4 = new CategoriaArchivo("Libros", "Archivos/Categorias/Libros.jpg", "jpg",
        "Archivos/Productos/Categorias/Libros/Libros.out"
        );

        Categoria ca5 = new CategoriaArchivo("Televisiones", "Archivos/Categorias/Televisiones.jpg", "jpg",
        "Archivos/Productos/Categorias/Televisores/Televisores.out");
        
        Categoria ca6 = new CategoriaArchivo("Videojuegos", "Archivos/Categorias/Videojuegos.jpg", "jpg",
         "Archivos/Productos/Categorias/Videojuegos/Videojuegos.out");
        
        ListaCategoria lc = new ListaCategoria();
        lc.add(ca);
        lc.add(ca2);
        lc.add(ca3);
        lc.add(ca4);
        lc.add(ca5);
        lc.add(ca6);
        
        
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
