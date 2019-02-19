/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author miguel
 */
public class ControladorPaqueteria {
    public static String RUTA_PAQUETERIA = "Archivos/EmpresasEnvio/Paqueterias.out";
    
    public ControladorPaqueteria() throws IOException
    {
        File archivo = new File(RUTA_PAQUETERIA);
        if(!archivo.exists())
        {
           ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(RUTA_PAQUETERIA));
           EmpresaDeEnvio e[] = new EmpresaDeEnvio[1];
           e[0] = new EmpresaDeEnvio(1, "FedEx", "12 días", 300, true);
           ListaEmpresasDeEnvio le = new ListaEmpresasDeEnvio(e);
           o.writeObject(le);
           o.close();
        }
        
    }
    
    public boolean agregarPaqueteria(EmpresaDeEnvio paqueteria) 
    {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_PAQUETERIA));
            ListaEmpresasDeEnvio le = (ListaEmpresasDeEnvio) in.readObject();
            le.add(paqueteria);

            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(RUTA_PAQUETERIA));
            o.writeObject(le);
            o.close();
        }catch(IOException e)
        {
            return false;
        } catch (ClassNotFoundException ex) {
            return false;
        }finally{
            
            
        }
        return true;
    }
    
    
    public  boolean eliminarPaqueterias(ListaEmpresasDeEnvio le)
    {
        try {
            
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(RUTA_PAQUETERIA));
            o.writeObject(le);
            o.close();
        }catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }finally{
            
        }
        return true;  
    }
    
    
    
}
