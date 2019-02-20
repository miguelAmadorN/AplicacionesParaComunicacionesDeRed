/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import static carritodecompras.servidor.IdOperaciones.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente 
{
    public static void main(String args[])
    {
        ObjectOutputStream oos = null;
	ObjectInputStream  ois = null;
        String host            = "127.0.0.1";
        int port               = 9999;
        
        try
        {
            Socket cl = new Socket(host, port);
            System.out.print("Conexion establecida...\n");
            oos = new ObjectOutputStream(cl.getOutputStream());
            ois = new ObjectInputStream(cl.getInputStream());
           
            
            /**
             * Agregar operaciones de la clase AdministradordeOpereracion (no 
             * necsitas instanciarla sus metodods son estaticos)  
             * e implemnetar la logica para la interfaz gráfica del usuario
             */
            
            /*
            ListaCategoria lc = AdministradorDeOperaciones.obtenerTodasLasCategorias(oos, ois);
            lc.mostrar();
            
            
            //Las categorias como CELULARES son constantes que se encuentran en IdOperaciones
           
            ListaProductos lp = AdministradorDeOperaciones.obtenerProductosDeUnaCategoria(oos, ois, VIDEOJUEGOS);
            lp.mostrar();
            

            
            ListaEmpresasDeEnvio lede = AdministradorDeOperaciones.obtenerEmpresasDeEnvio(oos, ois);
            lede.mostrar();
            */
            Usuario u = new Usuario("u1", "Miguel Angel", "Amador", "Nava", 
                                    "miguel.amador.n@gmail.com","5555555555","12345",
                                    new InformacionDeEnvio("miguel_amador_n@hotmail.com", "Miguel Amador",
                                        "Mexico","Av Zumpango no 56 , Tlanepantla de Baz", "56789032"), 
                                    new EmpresaDeEnvio(1,"FedEx","5 días", (double)140.34, true),
                                    new TarjetaDePago("123654987", "","", "",(short)0,(short)0)
                                    );
           
            /*
            Los copie y pegue como los ercribio rodrigo
            */
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
            
            ProductoCompra pc[] = new ProductoCompra[3];
            pc[0] = new ProductoCompra(pd1,2);
            pc[1] = new ProductoCompra(pd2,3);
            pc[2] = new ProductoCompra(pd3,1);
            
            CarritoCompras cc = new CarritoCompras(pc,u);
            
            boolean compra = AdministradorDeOperaciones.comprar(oos, ois, cc);
            System.out.print(compra);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.print(e);
        }
    }
    
    
}
