
package carritodecompras.productos;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Producto {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public String[] getColores() {
        return colores;
    }

    public void setColores(String[] colores) {
        this.colores = colores;
    }

    public String[] getMateriales() {
        return materiales;
    }

    public void setMateriales(String[] materiales) {
        this.materiales = materiales;
    }

    public String[] getImagenes() {
        return imagenes;
    }

    public void setImagenes(String[] imagenes) {
        this.imagenes = imagenes;
    }

    public BufferedImage[] getImagenesEnBuffer() {
        return imagenesEnBuffer;
    }

    public void setImagenesEnBuffer(BufferedImage[] imagenesEnBuffer) {
        this.imagenesEnBuffer = imagenesEnBuffer;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public String[] getPalabrasClave() {
        return PalabrasClave;
    }

    public void setPalabrasClave(String[] PalabrasClave) {
        this.PalabrasClave = PalabrasClave;
    }
    
    protected int id;//No lo ocupa Carlos para la vista, lo ocupo en el servidor
    protected String nombre;
    protected String descripcion;
    protected double precio;
    protected int existencias;
    protected String colores[];
    protected String materiales[]; 
    protected String imagenes[];//Ruta de las imagenes
    protected BufferedImage imagenesEnBuffer[];
    
    protected String Categoria;// No las ocupa Carlos para la vista 
    protected String PalabrasClave[]; // No las ocupa Carlos para la vista

    public void mostrar()
    {
        int i;
        System.out.print(id +"\n");
        System.out.print(nombre +"\n");
        System.out.print(descripcion +"\n");
        System.out.print(precio +"\n");
        System.out.print(existencias +"\n");
        for(i = 0; i < colores.length; i++)
            System.out.print(colores[i] +"\n");
        for(i = 0; i < materiales.length; i++)
            System.out.print(materiales[i] +"\n");
        for(i = 0; i < imagenes.length; i++)
            System.out.print(imagenes[i] + "\n");
        if(imagenesEnBuffer != null)
        {   
            for(i = 0; i < imagenesEnBuffer.length; i++)
            {
                JFrame frame = new JFrame();
                frame.getContentPane().setLayout(new FlowLayout());
                frame.getContentPane().add(new JLabel(new ImageIcon(imagenesEnBuffer[i])));
                frame.pack();
                frame.setVisible(true); 
            }
              
 
        }
        System.out.print(Categoria + "\n");
        for(i = 0; i < PalabrasClave.length; i++)
            System.out.print(PalabrasClave[i] + "\n");
    }
    
  
    /**
     * Categoria: no aparece en Aliexpress explicitamente pero debe de existir
     * para tener un mejor control.
     * 
     * PalabrasClave: Para tener un control por si se implementa la busqueta por palabras
     * 
     * Agrega los campos que necesites.
     **/
}
