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
            */
            
            //Las categorias como CELULARES son constantes que se encuentran en IdOperaciones
            ListaProductos lp = AdministradorDeOperaciones.obtenerProductosDeUnaCategoria(oos, ois, VIDEOJUEGOS);
            lp.mostrar();

            /*
            ListaEmpresasDeEnvio lede = AdministradorDeOperaciones.obtenerEmpresasDeEnvio(oos, ois);
            lede.mostrar();
*/
        }
        catch(Exception e)
        {
            System.err.print(e);
        }
    }
    
    
}
