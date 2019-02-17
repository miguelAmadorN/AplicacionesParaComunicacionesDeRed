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
class ListaCategoria implements Serializable {
        public final static int SOCKET  = 1;
        public final static int ARCHIVO = 2;
        
                
        private LinkedList lista = new LinkedList();
        int tipo = 0;
	

	public ListaCategoria(CategoriaSocket categorias[])
	{
		for(int i = 0; i < categorias.length; i++)
			lista.add(categorias[i]);
                tipo = SOCKET;
	}
        
        public ListaCategoria(CategoriaArchivo categorias[])
	{
		for(int i = 0; i < categorias.length; i++)
			lista.add(categorias[i]);
                tipo = ARCHIVO;
	}

        ListaCategoria() {}

	public void mostrar()
	{
		ListIterator li = lista.listIterator();
		Categoria c;
		while(li.hasNext())
		{
			c = (Categoria) li.next();
			c.mostrar();
		}
	}
        
        public int getSize()
        {
            return lista.size();
        }
    
        public Categoria get(int i)
        {
            if(tipo == 1)
                return (CategoriaSocket) lista.get(i);
            return (CategoriaArchivo) lista.get(i);
        }
        
        public void add(Categoria categoria)
        {
            lista.add(categoria);
        }
        
        public void remove(int i)
        {
            lista.remove(i);
        }
        
        
}
