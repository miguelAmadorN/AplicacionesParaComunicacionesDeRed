/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.servidor;

import carritodecompras.productos.GestorDeDatos;
import carritodecompras.productos.Categoria;
import carritodecompras.productos.CategoriaArchivo;
import carritodecompras.productos.CategoriaSocket;
import carritodecompras.productos.Operacion;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author miguel
 */
public class Servidor {
    
    
    public static void main(String args[]) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            ServerSocket s = new ServerSocket(9999);
            System.out.print("Servidor iniciado...\n");
            GestorDeDatos gdd = GestorDeDatos.getInstance();
            for (;;) {
                Socket cl = s.accept();
                System.out.print("Cliente conectado desde "
                        + cl.getInetAddress() + ":" + cl.getPort() + "\n");
                oos = new ObjectOutputStream(cl.getOutputStream());
                ois = new ObjectInputStream(cl.getInputStream());
               
                Operacion op = (Operacion) ois.readObject();
                gdd.ejecutarOperacion(oos, op);
                
                /**
                 * El GestorDeDatos es el encargado de recibir la operacion
                 * ejecutarla y devolver lo solicitado
                 */
                

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
