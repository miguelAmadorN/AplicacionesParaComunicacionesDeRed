/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author miguel
 */
class ListaEmpresasDeEnvio implements Serializable{
             
        private LinkedList lista = new LinkedList();
	
	public ListaEmpresasDeEnvio(EmpresaDeEnvio paqueterias[])
	{
		for(int i = 0; i < paqueterias.length; i++)
			lista.add(paqueterias[i]);
	}

        ListaEmpresasDeEnvio() {}

	public void mostrar()
	{
		ListIterator li = lista.listIterator();
		EmpresaDeEnvio e;
		while(li.hasNext())
		{
			e = (EmpresaDeEnvio) li.next();
			e.mostrar();
		}
	}
        
        public int getSize()
        {
            return lista.size();
        }
    
        public EmpresaDeEnvio get(int i)
        {
            return (EmpresaDeEnvio) lista.get(i);
        }
        
        public void add(EmpresaDeEnvio paqueteria)
        {
            lista.add(paqueteria);
        }
        
        public void remove(int i)
        {
            lista.remove(i);
        }
        
        
        public void remove(EmpresaDeEnvio paqueterias[])
        {
            for(int i = 0; i < paqueterias.length; i++)
                lista.remove(paqueterias[i]);
        }
        
}
