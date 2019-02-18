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
class ListaProductos implements Serializable {
        public final static int SOCKET  = 1;
        public final static int ARCHIVO = 2;
        
                
        private LinkedList lista = new LinkedList();
        int tipo = 0;
	

	public ListaProductos(ProductoSocket productos[])
	{
		for(int i = 0; i < productos.length; i++)
			lista.add(productos[i]);
                tipo = SOCKET;
	}
        
        public ListaProductos(ProductoArchivo productos[])
	{
		for(int i = 0; i < productos.length; i++)
			lista.add(productos[i]);
                tipo = ARCHIVO;
	}

        ListaProductos() {}

	public void mostrar()
	{
		ListIterator li = lista.listIterator();
		Producto p;
		while(li.hasNext())
		{
			p = (Producto) li.next();
			p.mostrar();
		}
	}
        
        public int getSize()
        {
            return lista.size();
        }
    
        public Producto get(int i)
        {
            if(tipo == 1)
                return (ProductoSocket) lista.get(i);
            return (ProductoArchivo) lista.get(i);
        }
        
        public void add(Producto categoria)
        {
            lista.add(categoria);
        }
        
        public void remove(int i)
        {
            lista.remove(i);
        }
        
        
}
