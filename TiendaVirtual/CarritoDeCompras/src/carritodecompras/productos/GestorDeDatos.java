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
    private final String AUTOS    = "Archivos/Productos/Categorias/Autos/Autos.out";
    private final String CALZADO  = "Archivos/Productos/Categorias/Calzado/Calzado.out";
    private final String JOYERIA  = "Archivos/Productos/Categorias/Joyeria/Joyeria.out";
    private final String MODA     = "Archivos/Productos/Categorias/Moda/Moda.out";
    private final String SALUD    = "Archivos/Productos/Categorias/Salud/Salud.out";
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
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto1.jpg",
                "Archivos/Productos/Categorias/Informatica/acer2.jpg",
                "Archivos/Productos/Categorias/Informatica/acer3.jpg",
                "Archivos/Productos/Categorias/Informatica/acer4.jpg"},null,
                "Informatica",new String[]{"Computadoras","Acer"});
        
        Producto pi2 = new ProductoArchivo(2,"Laptop Gamer Asus TUF FX504","i5 8GB RAM 1TB GTX1050 Asus FX504GD-ES51",26590.00,100,
                new String[]{"Negro","Azul"},new String[]{"Plastico","Metal"},
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto2.jpg",
                "Archivos/Productos/Categorias/Informatica/asus2.jpg",
                "Archivos/Productos/Categorias/Informatica/asus3.jpg",
                "Archivos/Productos/Categorias/Informatica/asus4.jpg"},null,
                "Informatica",new String[]{"Computadoras","Asus"});
        
        Producto pi3 = new ProductoArchivo(3,"Laptop HP Stream 14","Celeron N3060 4GB 32GB -Azul HP 14-ax010nr",9289.00,100,
                new String[]{"Negro","Azul"},new String[]{"Plastico","Metal"},
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto3.jpg",
                "Archivos/Productos/Categorias/Informatica/hp2.jpg",
                "Archivos/Productos/Categorias/Informatica/hp3.jpg",
                "Archivos/Productos/Categorias/Informatica/hp4.jpg"},null,
                "Informatica",new String[]{"Computadoras","HP"});
        
        Producto pi4 = new ProductoArchivo(4,"Laptop Lenovo 81FB0045LM","AMD Ryzen 3 2200U 8GB RAM 2TB DD",12499.00,100,
                new String[]{"Negro","Azul"},new String[]{"Plastico","Metal"},
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto4.jpg",
                "Archivos/Productos/Categorias/Informatica/lenovo2.jpg",
                "Archivos/Productos/Categorias/Informatica/lenovo3.jpg",
                "Archivos/Productos/Categorias/Informatica/lenovo4.jpg"},null,
                "Informatica",new String[]{"Computadoras","Lenovo"});
        
        Producto pi5 = new ProductoArchivo(5,"Laptop Hp Stream","4gb Ram 32gb Emmc -morado HP 11-y020nr",7020.00,100,
                new String[]{"Negro","Azul","Morado"},new String[]{"Plastico","Metal"},
                new String[]{"Archivos/Productos/Categorias/Informatica/Producto5.jpg",
                "Archivos/Productos/Categorias/Informatica/stream2.jpg",
                "Archivos/Productos/Categorias/Informatica/stream3.jpg",
                "Archivos/Productos/Categorias/Informatica/stream4.jpg"},null,
                "Informatica",new String[]{"Computadoras","HP"});
        
        //Productos de Libros
        Producto pl1 = new ProductoArchivo(1,"Harry Potter y el Prisionero de Azkaban","Autor: J. K. Rowling ",350.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto1.jpg",
                "Archivos/Productos/Categorias/Libros/harry2.jpg",
                "Archivos/Productos/Categorias/Libros/harry3.jpg",
                "Archivos/Productos/Categorias/Libros/harry4.jpg"},null,
                "Libros",new String[]{"Harry Potter","Libreria Gandhi"});
        
        Producto pl2 = new ProductoArchivo(2,"CATAROS E INQUISICION","Marca Catedra ",431.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto2.jpg",
                "Archivos/Productos/Categorias/Libros/cata2.jpg",
                "Archivos/Productos/Categorias/Libros/cata3.jpg",
                "Archivos/Productos/Categorias/Libros/cata4.jpg"},null,
                "Libros",new String[]{"Inquisicion","Libreria Gandhi"});
        
        Producto pl3 = new ProductoArchivo(3,"QUINTA MUJER","Editores Rustico ",418.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto3.jpg",
                "Archivos/Productos/Categorias/Libros/quin2.jpg",
                "Archivos/Productos/Categorias/Libros/quin3.jpg",
                "Archivos/Productos/Categorias/Libros/quin4.jpg"},null,
                "Libros",new String[]{"Tusquets","Libreria Gandhi"});
        
        Producto pl4 = new ProductoArchivo(4,"LAS CONFESIONES DEL JOVEN NERON","Tapa dura",499.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto4.jpg",
                "Archivos/Productos/Categorias/Libros/neron2.jpg",
                "Archivos/Productos/Categorias/Libros/neron3.jpg",
                "Archivos/Productos/Categorias/Libros/neron4.jpg"},null,
                "Libros",new String[]{"Tapa dura","Libreria Gandhi"});
        
        Producto pl5 = new ProductoArchivo(5,"El enigma","Editores Rustico",388.00,100,
                new String[]{"Negro"},new String[]{"Papel"},
                new String[]{"Archivos/Productos/Categorias/Libros/Producto5.jpg",
                "Archivos/Productos/Categorias/Libros/enigma2.jpg",
                "Archivos/Productos/Categorias/Libros/enigma3.jpg",
                "Archivos/Productos/Categorias/Libros/enigma4.jpg"},null,
                "Libros",new String[]{"Spinoza","Libreria Gandhi"});
        
        //Productos de Telefonia
        Producto pt1 = new ProductoArchivo(1,"Smartphone Samsung Galaxy S8","64GB",12999.00,100,
                new String[]{"Azul"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto1.jpg",
                "Archivos/Productos/Categorias/Telefonia/gala2.jpg",
                "Archivos/Productos/Categorias/Telefonia/gala3.jpg",
                "Archivos/Productos/Categorias/Telefonia/gala4.jpg"},null,
                "Telefonia",new String[]{"Celulares","Samsung"});
        
        Producto pt2 = new ProductoArchivo(2,"XIAOMI Redmi Note 6 Pro","64gb 4gb Dual Sim",4799.00,100,
                new String[]{"Azul"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto2.jpg",
                "Archivos/Productos/Categorias/Telefonia/xia2.jpg",
                "Archivos/Productos/Categorias/Telefonia/xia3.jpg",
                "Archivos/Productos/Categorias/Telefonia/xia4.jpg"},null,
                "Telefonia",new String[]{"Celulares","XIAOMI"});
        
        Producto pt3 = new ProductoArchivo(3,"Smartphone Samsung Galaxy S8","64GB",9999.00,100,
                new String[]{"Dorado"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto3.jpg",
                "Archivos/Productos/Categorias/Telefonia/dorado2.jpg",
                "Archivos/Productos/Categorias/Telefonia/dorado3.jpg",
                "Archivos/Productos/Categorias/Telefonia/dorado4.jpg"},null,
                "Telefonia",new String[]{"Celulares","Samsung"});
        
        Producto pt4 = new ProductoArchivo(4,"Mi A2 Lite Dual","32 GB XIAOMI",4438.00,100,
                new String[]{"Azul"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto4.jpg",
                "Archivos/Productos/Categorias/Telefonia/dual2.jpg",
                "Archivos/Productos/Categorias/Telefonia/dual3.jpg",
                "Archivos/Productos/Categorias/Telefonia/dual4.jpg"},null,
                "Telefonia",new String[]{"Celulares","Xiaomi"});
        
        Producto pt5 = new ProductoArchivo(5,"Smartphone Motorola Moto G6 Play","32GB",4998.00,100,
                new String[]{"Dorado"},new String[]{"Plastico", "Acero"},
                new String[]{"Archivos/Productos/Categorias/Telefonia/Producto5.jpg",
                "Archivos/Productos/Categorias/Telefonia/moto2.jpg",
                "Archivos/Productos/Categorias/Telefonia/moto3.jpg",
                "Archivos/Productos/Categorias/Telefonia/moto4.jpg"},null,
                "Telefonia",new String[]{"Celulares","Motorola"});
        
        //Productos de Televisores
        Producto ptv1 = new ProductoArchivo(1,"TV Samsung","32 Pulgadas 720p HD Smart TV LED",3990.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto1.jpg",
                "Archivos/Productos/Categorias/Televisores/sam2.jpg",
                "Archivos/Productos/Categorias/Televisores/sam3.jpg",
                "Archivos/Productos/Categorias/Televisores/sam4.jpg"},null,
                "Televisores",new String[]{"Samsung","TV"});
        
        Producto ptv2 = new ProductoArchivo(2,"TV Atvio","32 Pulgadas 720p HD LED ATV32",2999.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto2.jpg",
                "Archivos/Productos/Categorias/Televisores/atvio2.jpg",
                "Archivos/Productos/Categorias/Televisores/atvio3.jpg",
                "Archivos/Productos/Categorias/Televisores/atvio4.jpg"},null,
                "Televisores",new String[]{"Atvio","TV"});
        
        Producto ptv3 = new ProductoArchivo(3,"TV Sharp","32 Pulgadas 720p HD Smart TV LED",4499.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto3.jpg",
                "Archivos/Productos/Categorias/Televisores/sharp2.jpg",
                "Archivos/Productos/Categorias/Televisores/sharp3.jpg",
                "Archivos/Productos/Categorias/Televisores/sharp4.jpg"},null,
                "Televisores",new String[]{"Sharp","TV"});
        
        Producto ptv4 = new ProductoArchivo(4,"TV Hisense","50 pulgadasSmart TV Ultra HD Hisense 50H8E",9990.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto4.jpg",
                "Archivos/Productos/Categorias/Televisores/his2.jpg",
                "Archivos/Productos/Categorias/Televisores/his3.jpg",
                "Archivos/Productos/Categorias/Televisores/his4.jpg"},null,
                "Televisores",new String[]{"Hisense","TV"});
        
        Producto ptv5 = new ProductoArchivo(5,"TV Samsung","Smart TV 40 Pulgadas LED FULL HD WiFi USB 60",6999.00,100,
                new String[]{"Negro"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Televisores/Producto5.jpg",
                "Archivos/Productos/Categorias/Televisores/samsung2.jpg",
                "Archivos/Productos/Categorias/Televisores/samsung3.jpg",
                "Archivos/Productos/Categorias/Televisores/samsung4.jpg"},null,
                "Televisores",new String[]{"Samsung","TV"});
        
        //Productos de Videojuegos
        Producto pv1 = new ProductoArchivo(1,"Consola Xbox One S","TB Fortnite y Gears of War 4",6999.00,100,
                new String[]{"Negro"},new String[]{"Acero"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto1.jpg",
                "Archivos/Productos/Categorias/Videojuegos/box2.jpg",
                "Archivos/Productos/Categorias/Videojuegos/box3.jpg",
                "Archivos/Productos/Categorias/Videojuegos/box4.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","Xbox"});
        
        Producto pv2 = new ProductoArchivo(2,"Consola PlayStation 4 Pro","1 TB Stand Alone",9999.00,100,
                new String[]{"Negro"},new String[]{"Acero"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto2.jpg",
                "Archivos/Productos/Categorias/Videojuegos/play2.jpg",
                "Archivos/Productos/Categorias/Videojuegos/play3.jpg",
                "Archivos/Productos/Categorias/Videojuegos/play4.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","PlayStation"});
        
        Producto pv3 = new ProductoArchivo(3,"Consola Nintendo Switch","1 TB",9999.00,100,
                new String[]{"Negro"},new String[]{"Acero"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto3.jpg",
                "Archivos/Productos/Categorias/Videojuegos/nintendo2.jpg",
                "Archivos/Productos/Categorias/Videojuegos/nintendo3.jpg",
                "Archivos/Productos/Categorias/Videojuegos/nintendo4.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","Nintendo"});
        
        Producto pv4 = new ProductoArchivo(4,"Battlefield V PlayStation 4","Marca PlayStation 4",799.00,100,
                new String[]{"Azul"},new String[]{"CD"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto4.jpg",
                "Archivos/Productos/Categorias/Videojuegos/bat2.jpg",
                "Archivos/Productos/Categorias/Videojuegos/bat3.jpg",
                "Archivos/Productos/Categorias/Videojuegos/bat4.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","Battlefield V"});
        
        Producto pv5 = new ProductoArchivo(5,"Madden NFL 19 Xbox One","Xbox",899.00,100,
                new String[]{"Verde"},new String[]{"CD"},
                new String[]{"Archivos/Productos/Categorias/Videojuegos/Producto5.jpg",
                "Archivos/Productos/Categorias/Videojuegos/mad2.jpg",
                "Archivos/Productos/Categorias/Videojuegos/mad3.jpg",
                "Archivos/Productos/Categorias/Videojuegos/mad4.jpg"},null,
                "Videojuegos",new String[]{"Videojuegos","Madden NFL"});
        
        //Productos de autos
        Producto pa1 = new ProductoArchivo(1,"Aceite Sintético","Aceite sintético Gonher Nanotek gold",599.00,100,
                new String[]{"Sintético"},new String[]{"Para motor a gasolina"},
                new String[]{"Archivos/Productos/Categorias/Autos/aceite.png",
                "Archivos/Productos/Categorias/Autos/aceite2.png",
                "Archivos/Productos/Categorias/Autos/aceite3.jpg",
                "Archivos/Productos/Categorias/Autos/aceite4.jpg"},null,
                "Autos",new String[]{"Autos","Aceite"});
        
        Producto pa2 = new ProductoArchivo(2,"Tapetes","4 Tapetes de plastico reforzado",290.00,100,
                new String[]{"Negro","Gris"},new String[]{"Plastico Reforzado"},
                new String[]{"Archivos/Productos/Categorias/Autos/alfombras.png",
                "Archivos/Productos/Categorias/Autos/alfombras2.jpg",
                "Archivos/Productos/Categorias/Autos/alfombras3.jpg",
                "Archivos/Productos/Categorias/Autos/alfombras4.png"},null,
                "Autos",new String[]{"Autos","Accesorios"});
        
        Producto pa3 = new ProductoArchivo(3,"Aromatizante","Aromatizante Air Wick filter & fresh",38.00,100,
                new String[]{"Fresh"},new String[]{"Aroma hasta por un mes"},
                new String[]{"Archivos/Productos/Categorias/Autos/aromatizante.png",
                "Archivos/Productos/Categorias/Autos/aromatizante2.jpg",
                "Archivos/Productos/Categorias/Autos/aromatizante3.jpg",
                "Archivos/Productos/Categorias/Autos/aromatizante4.jpg"},null,
                "Autos",new String[]{"Autos","Accesorios"});
        
        Producto pa4 = new ProductoArchivo(4,"Baston","Baston de seguridad reforzado con proteccion de caucho",339.00,100,
                new String[]{"Rojo","Amarillo"},new String[]{"Estructura reforzada de acero"},
                new String[]{"Archivos/Productos/Categorias/Autos/baston.png",
                "Archivos/Productos/Categorias/Autos/baston2.jpg",
                "Archivos/Productos/Categorias/Autos/baston3.jpg",
                "Archivos/Productos/Categorias/Autos/baston4.jpg"},null,
                "Autos",new String[]{"Autos","Seguridad"});
        
        Producto pa5 = new ProductoArchivo(5,"Forro para volate","Cubre volante universal para auto",200.00,100,
                new String[]{"Azul","Rojo"},new String[]{"Piel sintética con costura"},
                new String[]{"Archivos/Productos/Categorias/Autos/cubrevolante.png",
                "Archivos/Productos/Categorias/Autos/cubrevolante2.png",
                "Archivos/Productos/Categorias/Autos/cubrevolante3.png",
                "Archivos/Productos/Categorias/Autos/cubrevolante4.png"},null,
                "Autos",new String[]{"Autos","Accesorios"});
        
        Producto pa6 = new ProductoArchivo(6,"LLantas","Llantas pirelli 225 50 R17 MODEL CORSA",1200.00,100,
                new String[]{"Negro"},new String[]{"Caucho"},
                new String[]{"Archivos/Productos/Categorias/Autos/llanta.png",
                "Archivos/Productos/Categorias/Autos/llanta2.jpg",
                "Archivos/Productos/Categorias/Autos/llanta3.jpg",
                "Archivos/Productos/Categorias/Autos/llanta4.jpg"},null,
                "Autos",new String[]{"Autos","Neumáticos"});
        
        Producto pa7 = new ProductoArchivo(7,"Porta placa","Porta placa Renovatio Factory",180.00,100,
                new String[]{"Amarillo"},new String[]{"Diseño innovador"},
                new String[]{"Archivos/Productos/Categorias/Autos/portaplaca.png",
                "Archivos/Productos/Categorias/Autos/portaplaca2.jpg",
                "Archivos/Productos/Categorias/Autos/portaplaca3.jpg",
                "Archivos/Productos/Categorias/Autos/portaplaca4.jpg"},null,
                "Autos",new String[]{"Autos","Accesorios"});
        
        Producto pa8 = new ProductoArchivo(8,"Juego de Balatas","Juego de Balatas marca Brembo NKG500",1020.00,100,
                new String[]{"Carboceramicas"},new String[]{"Carboceramicas"},
                new String[]{"Archivos/Productos/Categorias/Autos/balatas.jpg",
                "Archivos/Productos/Categorias/Autos/balatas2.jpg",
                "Archivos/Productos/Categorias/Autos/balatas3.jpg",
                "Archivos/Productos/Categorias/Autos/balatas4.jpg"},null,
                "Autos",new String[]{"Autos","Accesorios"});
        
        Producto pa9 = new ProductoArchivo(8,"Bateria","Bateria LTH de ciclado profundo Lth 367",780.00,100,
                new String[]{"Negra"},new String[]{"Ciclado profundo"},
                new String[]{"Archivos/Productos/Categorias/Autos/bateria.jpg",
                "Archivos/Productos/Categorias/Autos/bateria2.jpg",
                "Archivos/Productos/Categorias/Autos/bateria3.png",
                "Archivos/Productos/Categorias/Autos/bateria4.jpg"},null,
                "Autos",new String[]{"Autos","Accesorios"});
        
        Producto pa10 = new ProductoArchivo(8,"Gato Hidraulico","Gato Hidraulico Duralast 1.5 Ton de capacidad",399.00,100,
                new String[]{"Azul"},new String[]{"Rango de 3.75 a 15.5 pulg"},
                new String[]{"Archivos/Productos/Categorias/Autos/gato.jpg",
                "Archivos/Productos/Categorias/Autos/gato2.jpg",
                "Archivos/Productos/Categorias/Autos/gato3.jpg",
                "Archivos/Productos/Categorias/Autos/gato4.jpg"},null,
                "Autos",new String[]{"Autos","Accesorios"});
        
        
        //Productos de calzado
        Producto pc1 = new ProductoArchivo(1,"Tenis","Calzado adidas Mod. Clasicos blanco",1099.00,100,
                new String[]{"Blanco"},new String[]{"Forro de piel"},
                new String[]{"Archivos/Productos/Categorias/Calzado/adidas.png",
                "Archivos/Productos/Categorias/Calzado/adidas2.jpg",
                "Archivos/Productos/Categorias/Calzado/adidas3.jpg",
                "Archivos/Productos/Categorias/Calzado/adidas4.jpg"},null,
                "Calzado",new String[]{"Calzado","Adidas"});
        
        Producto pc2 = new ProductoArchivo(2,"Botas camel","Timberland 25-27 Mod classic",3200.00,100,
                new String[]{"Camel"},new String[]{"Contra el agua en piel"},
                new String[]{"Archivos/Productos/Categorias/Calzado/botasH.png",
                "Archivos/Productos/Categorias/Calzado/botas2.jpg",
                "Archivos/Productos/Categorias/Calzado/botas3.jpg",
                "Archivos/Productos/Categorias/Calzado/botas4.jpg"},null,
                "Calzado",new String[]{"Calzado","Timberland"});
        
        Producto pc3 = new ProductoArchivo(3,"Tenis","Converse clasicos en tela",899.00,100,
                new String[]{"Negro","Azul"},new String[]{"Modelos del 25-29"},
                new String[]{"Archivos/Productos/Categorias/Calzado/converse.png",
                "Archivos/Productos/Categorias/Calzado/converse2.jpg",
                "Archivos/Productos/Categorias/Calzado/converse3.jpg",
                "Archivos/Productos/Categorias/Calzado/converse4.jpg"},null,
                "Calzado",new String[]{"Calzado","Converse"});
        
        Producto pc4 = new ProductoArchivo(4,"Tenis","Calzado nike Mod. NT3456",1400.00,100,
                new String[]{"Negro"},new String[]{"Forro de tela"},
                new String[]{"Archivos/Productos/Categorias/Calzado/nike.png",
                "Archivos/Productos/Categorias/Calzado/nike2.jpg",
                "Archivos/Productos/Categorias/Calzado/nike3.jpg",
                "Archivos/Productos/Categorias/Calzado/nike4.jpg"},null,
                "Calzado",new String[]{"Calzado","Nike"});
        
        Producto pc5 = new ProductoArchivo(5,"Tenis","Calzado reebok super fly NG50",899.00,100,
                new String[]{"Negro","Azul"},new String[]{"Forro de tela"},
                new String[]{"Archivos/Productos/Categorias/Calzado/reebok.png",
                "Archivos/Productos/Categorias/Calzado/reebok2.jpg",
                "Archivos/Productos/Categorias/Calzado/reebok3.jpg",
                "Archivos/Productos/Categorias/Calzado/reebok4.jpg"},null,
                "Calzado",new String[]{"Calzado","Reebok"});
        
        Producto pc6 = new ProductoArchivo(6,"Tenis","Calzado Gucci clasicos CGC3000",2799.00,100,
                new String[]{"Negro","Blanco"},new String[]{"Forro en piel"},
                new String[]{"Archivos/Productos/Categorias/Calzado/tenis.png",
                "Archivos/Productos/Categorias/Calzado/tenis2.jpg",
                "Archivos/Productos/Categorias/Calzado/tenis3.jpg",
                "Archivos/Productos/Categorias/Calzado/tenis4.jpg"},null,
                "Calzado",new String[]{"Calzado","Gucci"});
        
        Producto pc7 = new ProductoArchivo(7,"Zapatillas","Calzado para dama diferentes marcas",900.00,100,
                new String[]{"Negro","Cafe"},new String[]{"Piel"},
                new String[]{"Archivos/Productos/Categorias/Calzado/zapatilla.png",
                "Archivos/Productos/Categorias/Calzado/zapatilla2.jpg",
                "Archivos/Productos/Categorias/Calzado/zapatilla3.jpg",
                "Archivos/Productos/Categorias/Calzado/zapatilla4.jpg"},null,
                "Calzado",new String[]{"Calzado","Piel"});
        
        Producto pc8 = new ProductoArchivo(8,"Zapato","Calzado formal caballero ",5990.00,100,
                new String[]{"Negro","Cafe"},new String[]{"Forrado en piel"},
                new String[]{"Archivos/Productos/Categorias/Calzado/zapato.png",
                "Archivos/Productos/Categorias/Calzado/zapato2.jpg",
                "Archivos/Productos/Categorias/Calzado/zapato3.jpg",
                "Archivos/Productos/Categorias/Calzado/zapato4.jpg"},null,
                "Calzado",new String[]{"Calzado","piel"});
        
        //Productos de joyeria
        Producto pj1 = new ProductoArchivo(1,"Cadena","Cadena de oro bajo 14k",1099.00,100,
                new String[]{"Oro pulido"},new String[]{"Oro bajo"},
                new String[]{"Archivos/Productos/Categorias/Joyeria/aro.png",
                "Archivos/Productos/Categorias/Joyeria/aro2.jpg",
                "Archivos/Productos/Categorias/Joyeria/aro3.jpg",
                "Archivos/Productos/Categorias/Joyeria/aro4.jpg"},null,
                "Joyeria",new String[]{"Joyeria","14k"});
        
        Producto pj2 = new ProductoArchivo(2,"Dije Corazón","Dije de corazon 14k oro bajo",599.00,100,
                new String[]{"Oro pulido"},new String[]{"Oro bajo"},
                new String[]{"Archivos/Productos/Categorias/Joyeria/corazon.png",
                "Archivos/Productos/Categorias/Joyeria/corazon2.jpg",
                "Archivos/Productos/Categorias/Joyeria/corazon3.jpg",
                "Archivos/Productos/Categorias/Joyeria/corazon4.jpg"},null,
                "Joyeria",new String[]{"Joyeria","14k"});
        
        Producto pj3 = new ProductoArchivo(3,"Dije Corazones","Dije de varios corazones 14k",1350.00,100,
                new String[]{"Oro pulido"},new String[]{"Oro bajo"},
                new String[]{"Archivos/Productos/Categorias/Joyeria/corazones.png",
                "Archivos/Productos/Categorias/Joyeria/corazones2.jpg",
                "Archivos/Productos/Categorias/Joyeria/corazones3.jpg",
                "Archivos/Productos/Categorias/Joyeria/corazones4.jpg"},null,
                "Joyeria",new String[]{"Joyeria","14k"});
        
        Producto pj4 = new ProductoArchivo(4,"Esclava","Exclava con diferentes incrustaciones",4600.00,100,
                new String[]{"Oro Pulido"},new String[]{"Oro bajo"},
                new String[]{"Archivos/Productos/Categorias/Joyeria/esclava.png",
                "Archivos/Productos/Categorias/Joyeria/esclava2.jpg",
                "Archivos/Productos/Categorias/Joyeria/esclava3.jpg",
                "Archivos/Productos/Categorias/Joyeria/esclava4.png"},null,
                "Joyeria",new String[]{"Joyeria","14k"});
        
        Producto pj5 = new ProductoArchivo(5,"Dije mano","Dije con diseño de mano 14k",790.00,100,
                new String[]{"oro Pulido"},new String[]{"Oro Bajo"},
                new String[]{"Archivos/Productos/Categorias/Joyeria/mano.png",
                "Archivos/Productos/Categorias/Joyeria/mano2.jpg",
                "Archivos/Productos/Categorias/Joyeria/mano3.jpg",
                "Archivos/Productos/Categorias/Joyeria/mano4.jpg"},null,
                "Joyeria",new String[]{"Joyeria","14k"});
        
        Producto pj6 = new ProductoArchivo(6,"Pulsera","Hermosa pulsera con diseño innovador",590.00,100,
                new String[]{"Oro Pulido"},new String[]{"Oro bajo"},
                new String[]{"Archivos/Productos/Categorias/Joyeria/pulsera.png",
                "Archivos/Productos/Categorias/Joyeria/pulsera2.jpg",
                "Archivos/Productos/Categorias/Joyeria/pulsera3.jpg",
                "Archivos/Productos/Categorias/Joyeria/pulsera4.jpg"},null,
                "Joyeria",new String[]{"Joyeria","14k"});
        
        //Productos de Moda
        Producto pm1 = new ProductoArchivo(1,"Camisa","Camisa hombre fit con cuello en algodon",499.00,100,
                new String[]{"Negro"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/camisa.png",
                "Archivos/Productos/Categorias/Moda/camisa2.jpg",
                "Archivos/Productos/Categorias/Moda/camisa3.jpg",
                "Archivos/Productos/Categorias/Moda/camisa4.jpg"},null,
                "Moda",new String[]{"Moda","Camisa"});
        
        Producto pm2 = new ProductoArchivo(2,"Sudadera","Sudadera para invierno mod 2342",700.00,100,
                new String[]{"Gris"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/gris.png",
                "Archivos/Productos/Categorias/Moda/gris2.jpg",
                "Archivos/Productos/Categorias/Moda/gris3.jpg",
                "Archivos/Productos/Categorias/Moda/gris4.jpg"},null,
                "Moda",new String[]{"Moda","Adidas"});
        
        Producto pm3 = new ProductoArchivo(3,"Sudadera","Sudadera para invierno mod 534",999.00,100,
                new String[]{"Negro"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/negra.png",
                "Archivos/Productos/Categorias/Moda/negra2.png",
                "Archivos/Productos/Categorias/Moda/negra3.jpg",
                "Archivos/Productos/Categorias/Moda/negra4.jpg"},null,
                "Moda",new String[]{"Moda","Army"});
        
        Producto pm4 = new ProductoArchivo(4,"Playera","Playera completamente de algodon",299.00,100,
                new String[]{"Rojo","Azul"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/playera.png",
                "Archivos/Productos/Categorias/Moda/playera2.jpg",
                "Archivos/Productos/Categorias/Moda/playera3.jpg",
                "Archivos/Productos/Categorias/Moda/playera4.jpg"},null,
                "Moda",new String[]{"Moda","Tipo polo"});
        
        Producto pm5 = new ProductoArchivo(5,"Sudadera","Sudadera para invierno mod casidy",990.00,100,
                new String[]{"Rojo"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/roja.png",
                "Archivos/Productos/Categorias/Moda/roja2.jpg",
                "Archivos/Productos/Categorias/Moda/roja3.jpg",
                "Archivos/Productos/Categorias/Moda/roja4.jpg"},null,
                "Moda",new String[]{"Moda","Adidas performance"});
        
        Producto pm6 = new ProductoArchivo(6,"Sudadera","Sudadera para invierno mod tok",800.00,100,
                new String[]{"Verde"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/verde.png",
                "Archivos/Productos/Categorias/Moda/verde2.jpg",
                "Archivos/Productos/Categorias/Moda/verde4.jpg"},null,
                "Moda",new String[]{"Moda","Under Armour"});
        
        Producto pm7 = new ProductoArchivo(7,"Camisa","Camisa hombre fit con cuello en algodon",499.00,100,
                new String[]{"Blanco"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/blanca.jpg",
                "Archivos/Productos/Categorias/Moda/blanca2.jpg",
                "Archivos/Productos/Categorias/Moda/blanca3.jpg",
                "Archivos/Productos/Categorias/Moda/blanca4.jpg"},null,
                "Moda",new String[]{"Moda","Ropa Caballero"});
        
        Producto pm8 = new ProductoArchivo(8,"Camisa","Camisa hombre fit con cuello en algodon",499.00,100,
                new String[]{"Cuadros"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/cuadros.jpg",
                "Archivos/Productos/Categorias/Moda/cuadros2.jpg",
                "Archivos/Productos/Categorias/Moda/cuadros3.jpg",
                "Archivos/Productos/Categorias/Moda/cuadros4.jpg"},null,
                "Moda",new String[]{"Moda","Ropa caballero"});
        
        Producto pm9 = new ProductoArchivo(9,"Camisa","Camisa hombre fit con cuello en algodon",499.00,100,
                new String[]{"Rayas"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/rayas.jpg",
                "Archivos/Productos/Categorias/Moda/rayas2.jpg",
                "Archivos/Productos/Categorias/Moda/rayas3.jpg",},null,
                "Moda",new String[]{"Moda","Ropa caballero"});
        
        Producto pm10 = new ProductoArchivo(10,"Camisa","Camisa hombre fit con cuello en algodon",499.00,100,
                new String[]{"Gris"},new String[]{"Tela de algodon"},
                new String[]{"Archivos/Productos/Categorias/Moda/griscam.jpg",
                "Archivos/Productos/Categorias/Moda/griscam2.jpg",
                "Archivos/Productos/Categorias/Moda/griscam3.jpg",
                "Archivos/Productos/Categorias/Moda/griscam4.jpg"},null,
                "Moda",new String[]{"Moda","Ropa caballero"});
        
        //Productos de Salud
        Producto ps1 = new ProductoArchivo(1,"Cepillo","Cepillo de dientes Colgate ultracare",49.00,100,
                new String[]{"Negro","Azul"},new String[]{"Tenología duramax"},
                new String[]{"Archivos/Productos/Categorias/Salud/cepillo.png",
                "Archivos/Productos/Categorias/Salud/cepillo2.jpg",
                "Archivos/Productos/Categorias/Salud/cepillo3.png",
                "Archivos/Productos/Categorias/Salud/cepillo4.png"},null,
                "Salud",new String[]{"Salud","Accesorios"});
        
        Producto ps2 = new ProductoArchivo(2,"Desodorante","Desodorante Obao mujer 355 ml",50.00,100,
                new String[]{"Diferentes colores"},new String[]{"Dura todo el dia"},
                new String[]{"Archivos/Productos/Categorias/Salud/desodorante.png",
                "Archivos/Productos/Categorias/Salud/desodorante2.jpg",
                "Archivos/Productos/Categorias/Salud/desodorante3.jpg",
                "Archivos/Productos/Categorias/Salud/desodorante4.jpg"},null,
                "Salud",new String[]{"Salud","Higiene"});
        
        Producto ps3 = new ProductoArchivo(3,"Jabon","Jabon de baño con diferentes aromas",20.00,100,
                new String[]{"Verde","Rosa"},new String[]{"Para una limpieza profunda"},
                new String[]{"Archivos/Productos/Categorias/Salud/jabon.png",
                "Archivos/Productos/Categorias/Salud/jabon2.jpg",
                "Archivos/Productos/Categorias/Salud/jabon3.jpg",
                "Archivos/Productos/Categorias/Salud/jabon4.jpg"},null,
                "Salud",new String[]{"Salud","Higiene"});
        
        Producto ps4 = new ProductoArchivo(4,"Perfume","Agradables aromas que duran todo el dia",990.00,100,
                new String[]{"Hombre","Mujer"},new String[]{"Para un olor inigualable"},
                new String[]{"Archivos/Productos/Categorias/Salud/perfume.png",
                "Archivos/Productos/Categorias/Salud/perfume2.jpg",
                "Archivos/Productos/Categorias/Salud/perfume3.jpg",
                "Archivos/Productos/Categorias/Salud/perfume4.jpg"},null,
                "Salud",new String[]{"Salud","Higiene"});
        
        Producto ps5 = new ProductoArchivo(5,"Rastrillo","Rastrillos Guillette prestobarba",5990.00,100,
                new String[]{"Verde","Azul"},new String[]{"Hoja de acero inoxidable"},
                new String[]{"Archivos/Productos/Categorias/Salud/rastrillo.png",
                "Archivos/Productos/Categorias/Salud/rastrillo2.jpg",
                "Archivos/Productos/Categorias/Salud/rastrillo3.jpg",
                "Archivos/Productos/Categorias/Salud/rastrillo4.jpg"},null,
                "Salud",new String[]{"Salud","Higiene"});
        
        Producto ps6 = new ProductoArchivo(6,"Crema","Crema para afeitar guillette Foamy",60.00,100,
                new String[]{"verde"},new String[]{"Mayor sensibilidad"},
                new String[]{"Archivos/Productos/Categorias/Salud/crema.jpg",
                "Archivos/Productos/Categorias/Salud/crema2.jpg",
                "Archivos/Productos/Categorias/Salud/crema3.jpg",
                "Archivos/Productos/Categorias/Salud/crema4.jpg"},null,
                "Salud",new String[]{"Salud","Higiene"});
        
        Producto ps7 = new ProductoArchivo(7,"Toallas","Toallas femeninas Saba ultra invisible",49.00,100,
                new String[]{"Ultra invisible"},new String[]{"Ultradelgada nocturna"},
                new String[]{"Archivos/Productos/Categorias/Salud/toallas.jpg",
                "Archivos/Productos/Categorias/Salud/toallas3.jpg",
                "Archivos/Productos/Categorias/Salud/toallas4.jpg"},null,
                "Salud",new String[]{"Salud","Higiene"});
        
        Producto ps8 = new ProductoArchivo(8,"Kleenex","Toallas limpiadoras 400 x 600",70.00,100,
                new String[]{"Flores"},new String[]{"Alta resistencia"},
                new String[]{"Archivos/Productos/Categorias/Salud/kleenex.jpg",
                "Archivos/Productos/Categorias/Salud/kleenex2.jpg",
                "Archivos/Productos/Categorias/Salud/kleenex3.jpg",
                "Archivos/Productos/Categorias/Salud/kleenex4.jpg"},null,
                "Salud",new String[]{"Salud","Higiene"});
        
        Producto ps9 = new ProductoArchivo(9,"Pasta Dental","Colgate triple acción con blancura",49.00,100,
                new String[]{"Fresh"},new String[]{"Dura todo el dia"},
                new String[]{"Archivos/Productos/Categorias/Salud/pasta.jpg",
                "Archivos/Productos/Categorias/Salud/pasta2.jpg",
                "Archivos/Productos/Categorias/Salud/pasta3.jpg",
                "Archivos/Productos/Categorias/Salud/pasta4.jpg"},null,
                "Salud",new String[]{"Salud","Higiene"});
        
        Producto ps10 = new ProductoArchivo(10,"Papel","Papel higienico petalo rendimax",180.00,100,
                new String[]{"Rendimax"},new String[]{"Con doble hoja"},
                new String[]{"Archivos/Productos/Categorias/Salud/papel.jpg",
                "Archivos/Productos/Categorias/Salud/papel2.jpg",
                "Archivos/Productos/Categorias/Salud/papel3.jpg",
                "Archivos/Productos/Categorias/Salud/papel4.jpg"},null,
                "Salud",new String[]{"Salud","Higiene"});
        
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
                
            case AUTOS:
                
                lp.add(pa1);
                lp.add(pa2);
                lp.add(pa3);
                lp.add(pa4);
                lp.add(pa5);
                lp.add(pa6);
                lp.add(pa7);
                lp.add(pa8);
                lp.add(pa9);
                lp.add(pa10);
                
                break;
                
            case CALZADO:
                
                lp.add(pc1);
                lp.add(pc2);
                lp.add(pc3);
                lp.add(pc4);
                lp.add(pc5);
                lp.add(pc6);
                lp.add(pc7);
                lp.add(pc8);
                
                break;
                
            case JOYERIA:
                
                lp.add(pj1);
                lp.add(pj2);
                lp.add(pj3);
                lp.add(pj4);
                lp.add(pj5);
                lp.add(pj6);
                
                break;
                
            case MODA:
                
                lp.add(pm1);
                lp.add(pm2);
                lp.add(pm3);
                lp.add(pm4);
                lp.add(pm5);
                lp.add(pm6);
                lp.add(pm7);
                lp.add(pm8);
                lp.add(pm9);
                lp.add(pm10);
                
                break;
                
            case SALUD:
                
                lp.add(ps1);
                lp.add(ps2);
                lp.add(ps3);
                lp.add(ps4);
                lp.add(ps5);
                lp.add(ps6);
                lp.add(ps7);
                lp.add(ps8);
                lp.add(ps9);
                lp.add(ps10);
                
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
                "Archivos/Categorias/Celulares.png",
                "png",
                 "Archivos/Productos/Categorias/Telefonia/Telefonia.out"
        );
        
        Categoria ca2 = new CategoriaArchivo("Informatica",
                "Archivos/Categorias/Informatica.png",
                "png",
                "Archivos/Productos/Categorias/Informatica/Informatica.out"
        );
        
        Categoria ca3 = new CategoriaArchivo("Deportes", "Archivos/Categorias/Deportes.png", "png",
        "Archivos/Productos/Categorias/Deportes/Deportes.out");
        
        Categoria ca4 = new CategoriaArchivo("Libros", "Archivos/Categorias/Libros.png", "png",
        "Archivos/Productos/Categorias/Libros/Libros.out"
        );

        Categoria ca5 = new CategoriaArchivo("Televisiones", "Archivos/Categorias/Televisiones.png", "png",
        "Archivos/Productos/Categorias/Televisores/Televisores.out");
        
        Categoria ca6 = new CategoriaArchivo("Videojuegos", "Archivos/Categorias/Videojuegos.png", "png",
         "Archivos/Productos/Categorias/Videojuegos/Videojuegos.out");
        
        Categoria ca7 = new CategoriaArchivo("Autos", "Archivos/Categorias/Autos.png", "png",
         "Archivos/Productos/Categorias/Autos/Autos.out");
        
        Categoria ca8 = new CategoriaArchivo("Calzado", "Archivos/Categorias/Calzado.png", "png",
         "Archivos/Productos/Categorias/Calzado/Calzado.out");
        
        Categoria ca9 = new CategoriaArchivo("Joyeria", "Archivos/Categorias/Joyeria.png", "png",
         "Archivos/Productos/Categorias/Joyeria/Joyeria.out");
        
        Categoria ca10 = new CategoriaArchivo("Moda", "Archivos/Categorias/Moda.png", "png",
         "Archivos/Productos/Categorias/Moda/Moda.out");
        
        Categoria ca11 = new CategoriaArchivo("Salud", "Archivos/Categorias/Salud.png", "png",
         "Archivos/Productos/Categorias/Salud/Salud.out");
        
        ListaCategoria lc = new ListaCategoria();
        lc.add(ca);
        lc.add(ca2);
        lc.add(ca3);
        lc.add(ca4);
        lc.add(ca5);
        lc.add(ca6);
        lc.add(ca7);
        lc.add(ca8);
        lc.add(ca9);
        lc.add(ca10);
        lc.add(ca11);
        
        
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