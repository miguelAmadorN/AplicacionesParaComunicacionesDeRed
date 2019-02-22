/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import ImagenesInterfaz.PanelFondo;
import java.awt.Image;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author miguel
 */
public class VistaCliente extends javax.swing.JFrame implements ActionListener{

    /**
     * Creates new form VistaCliente
     */
    private JButton categorias[];
    private JButton productos[];
    private JLabel nombreProductos[];
    private static final int NUMCATEGORIAS = 5;
    private static final int NUMPRODUCTOS = 8;
    private Producto productoPrincipal;
    private int cantidadDeProductos;
    private Producto producto[];
    private Categoria categoria[];
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    ListaProductos lp;
    ListaCategoria lc;
    ArrayList productosCesta;
            
    
    
    public VistaCliente(ObjectOutputStream oos, ObjectInputStream  ois) 
            throws IOException, ClassNotFoundException 
    {
       
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
 
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                try {
                    close(oos, ois);
                } catch (IOException ex) {
                    Logger.getLogger(VistaCliente.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
            }
        });
        
        this.oos = oos;
        this.ois = ois;
        initComponents();
        init();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(false);//no permite que sea redimincionable
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass()
                        .getResource("/ImagenesInterfaz/Icono.png"));
        setIconImage(icon);
        this.setTitle("CML EXPRESS"); 
      
    }
    
    private void close(ObjectOutputStream oos, ObjectInputStream  ois) throws IOException{
        if (JOptionPane.showConfirmDialog(rootPane, "¿Deseas salir del sistema?",
                "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            AdministradorDeOperaciones.finalizarConexion(oos, ois);
            System.exit(0);
        }else
        {
            
        }
    } 
    
    public void init() 
            throws IOException, ClassNotFoundException
    {
        ImageIcon iconoOriginal = new ImageIcon("src/pdf/LogoCML.png");
        ImageIcon iconoEscala = new ImageIcon(iconoOriginal.getImage().getScaledInstance(JLCML.getWidth(), JLCML.getHeight(), java.awt.Image.SCALE_DEFAULT));
        JLCML.setIcon(iconoEscala);
        lc = AdministradorDeOperaciones.obtenerTodasLasCategorias(oos, ois);
        
        categorias = new JButton[NUMCATEGORIAS];
        categorias[0] = Categoria1;
        categorias[1] = Categoria2;
        categorias[2] = Categoria3;
        categorias[3] = Categoria4;
        categorias[4] = Categoria5;
        
        productos = new JButton[NUMPRODUCTOS];
        productos[0] = Producto1;
        productos[1] = Producto2;
        productos[2] = Producto3;
        productos[3] = Producto4;
        productos[4] = Producto5;
        productos[5] = Producto6;
        productos[6] = Producto7;
        productos[7] = Producto8;
        
        nombreProductos = new JLabel[NUMPRODUCTOS];
        nombreProductos[0] = DescripcionP1;
        nombreProductos[1] = DescripcionP2;
        nombreProductos[2] = DescripcionP3;
        nombreProductos[3] = DescripcionP4;
        nombreProductos[4] = DescripcionP5;
        nombreProductos[5] = DescripcionP6;
        nombreProductos[6] = DescripcionP7;
        nombreProductos[7] = DescripcionP8;
        
        int i;
        for( i = 0 ; i < NUMCATEGORIAS; i++)
            categorias[i].addActionListener(this);
        for( i = 0 ; i < NUMPRODUCTOS; i++)
            productos[i].addActionListener(this);
        
        Imagen1.addActionListener(this);
        Imagen2.addActionListener(this);
        Imagen3.addActionListener(this);
        Imagen4.addActionListener(this);
        
        Agregar.addActionListener(this);
        Quitar.addActionListener(this);
        
        AgregarAlaCesta.addActionListener(this);
        
        lp = AdministradorDeOperaciones.obtenerProductosDeUnaCategoria(oos, ois,  lc.get(0).getRuta());
        
        setCategorias(lc, 0, NUMCATEGORIAS < lc.getSize() ? NUMCATEGORIAS : lc.getSize());
        setProductos(lp, 0, NUMPRODUCTOS < lp.getSize() ? NUMPRODUCTOS : lp.getSize());
        if(lp.getSize() > 0)
            setProductoPrincipal(lp.get(0));
        
        productosCesta = new ArrayList();
     
    }
    private void setCategorias(ListaCategoria lc, int botonInicial, int botonFinal)
    {
        if (lc != null) {
            categoria = new Categoria[botonFinal - botonInicial + 1];
            ImageIcon img; int i;
            for (i = botonInicial; i < botonFinal; i++) 
            {
                img = new ImageIcon(lc.get(i).getImagenEnBuffer());
                categorias[i].setIcon(new ImageIcon(img.getImage().getScaledInstance(categorias[i].getWidth(),
                                        categorias[i].getHeight(), java.awt.Image.SCALE_DEFAULT)));
                categoria[i] = lc.get(i);
            }
            
            for (; i < NUMCATEGORIAS; i++) {
                categorias[i].setVisible(false);
            }

        }
    }
    
    private void setProductos(ListaProductos lp, int botonInicial, int botonFinal)
    {
       if(lp != null)
        {
            producto = new Producto[botonFinal - botonInicial + 1];
            NombreCategoria.setText(lp.get(0).getCategoria().toUpperCase());
            ImageIcon img;
            int i = 0;
            for (i = botonInicial; i < botonFinal; i++) {
                img = new ImageIcon(lp.get(i).getImagenesEnBuffer()[0]);
                productos[i].setIcon(new ImageIcon(img.getImage().getScaledInstance(productos[i].getWidth(),
                        productos[i].getHeight(), java.awt.Image.SCALE_DEFAULT)));
                nombreProductos[i].setText(recortarCadena(lp.get(i).getNombre()));
                producto[i] = lp.get(i);
            }
            for (; i < NUMPRODUCTOS; i++) {
                productos[i].setVisible(false);
                nombreProductos[i].setVisible(false);
            }
        }
            
    }
    
    private void setProductoPrincipal(Producto producto)
    {
        if(producto != null)
        {
            
            ImageIcon img;
            if(producto.getImagenesEnBuffer().length > 0)
            {
                Imagen1.setVisible(true);
                img = new ImageIcon(producto.getImagenesEnBuffer()[0]);
                Imagen1.setIcon(new ImageIcon(img.getImage()
                                    .getScaledInstance(Imagen1.getWidth(), 
                                                       Imagen1.getHeight(), 
                                                       java.awt.Image.SCALE_DEFAULT)));
                ImagenProductoPrincipal.setIcon(new ImageIcon(img.getImage()
                                    .getScaledInstance(ImagenProductoPrincipal.getWidth(), 
                                                       ImagenProductoPrincipal.getHeight(), 
                                                       java.awt.Image.SCALE_DEFAULT)));
            }else
            {
               Imagen1.setVisible(false);
            }
            
            if(producto.getImagenesEnBuffer().length > 1)
            {
                Imagen2.setVisible(true);
                img = new ImageIcon(producto.getImagenesEnBuffer()[0]);
                Imagen2.setIcon(new ImageIcon(img.getImage()
                                    .getScaledInstance(Imagen2.getWidth(), 
                                                       Imagen2.getHeight(), 
                                                       java.awt.Image.SCALE_DEFAULT)));
            }
            else
            {
               Imagen2.setVisible(false);
            }
            
            
            if(producto.getImagenesEnBuffer().length > 2)
            {
                Imagen3.setVisible(true);
                img = new ImageIcon(producto.getImagenesEnBuffer()[0]);
                Imagen3.setIcon(new ImageIcon(img.getImage()
                                    .getScaledInstance(Imagen3.getWidth(), 
                                                       Imagen3.getHeight(), 
                                                       java.awt.Image.SCALE_DEFAULT)));
            }
            else
            {
               Imagen3.setVisible(false);
            }
            
            
            if(producto.getImagenesEnBuffer().length > 3)
            {
                Imagen4.setVisible(true);
                img = new ImageIcon(producto.getImagenesEnBuffer()[0]);
                Imagen4.setIcon(new ImageIcon(img.getImage()
                                    .getScaledInstance(Imagen4.getWidth(), 
                                                       Imagen4.getHeight(), 
                                                       java.awt.Image.SCALE_DEFAULT)));
            }else
            {
               Imagen4.setVisible(false);
            }
            
            Nombre.setText(producto.getNombre());
            Descripcion.setText("Descripción: " + producto.getDescripcion());
            Precio.setText("Precio: $" + producto.getPrecio());
            Existencias.setText("Existencias: " + producto.getExistencias());
            productoPrincipal = producto;
            cantidadDeProductos = 1;
        }
    }

    private String recortarCadena(String cadena)
    {
        if(cadena.length() > 15)
        {
            return cadena.substring(0, 15) + "...";
        }
        return cadena;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        ComprarTodo = new javax.swing.JButton();
        NombreCategoria = new javax.swing.JLabel();
        ImagenProductoPrincipal = new javax.swing.JLabel();
        AgregarAlaCesta = new javax.swing.JButton();
        Precio = new javax.swing.JLabel();
        Descripcion = new javax.swing.JLabel();
        Nombre = new javax.swing.JLabel();
        Existencias = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        JLCML = new javax.swing.JLabel();
        Categoria1 = new javax.swing.JButton();
        Categoria2 = new javax.swing.JButton();
        Categoria3 = new javax.swing.JButton();
        Categoria4 = new javax.swing.JButton();
        SiguientesCategorias = new javax.swing.JButton();
        Categoria5 = new javax.swing.JButton();
        AnterioresCategorias = new javax.swing.JButton();
        Cantidad = new javax.swing.JLabel();
        Quitar = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        Producto1 = new javax.swing.JButton();
        Producto2 = new javax.swing.JButton();
        SiguientesProductos = new javax.swing.JButton();
        AnterioresProductos = new javax.swing.JButton();
        DescripcionP1 = new javax.swing.JLabel();
        DescripcionP2 = new javax.swing.JLabel();
        Producto3 = new javax.swing.JButton();
        Producto4 = new javax.swing.JButton();
        DescripcionP3 = new javax.swing.JLabel();
        DescripcionP4 = new javax.swing.JLabel();
        Producto5 = new javax.swing.JButton();
        Producto6 = new javax.swing.JButton();
        DescripcionP5 = new javax.swing.JLabel();
        DescripcionP6 = new javax.swing.JLabel();
        Producto7 = new javax.swing.JButton();
        Producto8 = new javax.swing.JButton();
        DescripcionP7 = new javax.swing.JLabel();
        DescripcionP8 = new javax.swing.JLabel();
        Imagen2 = new javax.swing.JButton();
        Imagen4 = new javax.swing.JButton();
        Imagen1 = new javax.swing.JButton();
        EliminarCesta = new javax.swing.JButton();
        Imagen3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 629, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel3);

        ComprarTodo.setText("Comprar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(NombreCategoria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ComprarTodo)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComprarTodo)
                    .addComponent(NombreCategoria)))
        );

        AgregarAlaCesta.setText("Añadir a la cesta");

        Precio.setText("Precio: ");

        Descripcion.setText("Descripción:");

        Nombre.setText("Nombre:");

        Existencias.setText("Existencias:");

        SiguientesCategorias.setText(">>");

        AnterioresCategorias.setText("<<");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLCML, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Categoria4, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(Categoria3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Categoria2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Categoria1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(Categoria5, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(AnterioresCategorias)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SiguientesCategorias)
                .addGap(53, 53, 53))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(JLCML, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Categoria1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Categoria2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Categoria3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Categoria4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Categoria5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SiguientesCategorias)
                    .addComponent(AnterioresCategorias))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Cantidad.setText("1");

        Quitar.setText("-");

        Agregar.setText("+");

        Producto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Producto1ActionPerformed(evt);
            }
        });

        SiguientesProductos.setText(">>");

        AnterioresProductos.setText("<<");

        DescripcionP1.setText("_______________");

        DescripcionP2.setText("______________");

        Producto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Producto3ActionPerformed(evt);
            }
        });

        DescripcionP3.setText("______________");

        DescripcionP4.setText("______________");

        Producto5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Producto5ActionPerformed(evt);
            }
        });

        DescripcionP5.setText("______________");

        DescripcionP6.setText("______________");

        Producto7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Producto7ActionPerformed(evt);
            }
        });

        DescripcionP7.setText("______________");

        DescripcionP8.setText("______________");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Producto3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DescripcionP3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Producto4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DescripcionP4)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Producto1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DescripcionP1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DescripcionP2)
                            .addComponent(Producto2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Producto5, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DescripcionP5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DescripcionP6)
                            .addComponent(Producto6, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(Producto7, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(AnterioresProductos)
                                    .addGap(25, 25, 25)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(DescripcionP7)
                                .addGap(95, 95, 95)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DescripcionP8)
                            .addComponent(SiguientesProductos)
                            .addComponent(Producto8, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Producto1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Producto2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DescripcionP1)
                    .addComponent(DescripcionP2))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Producto3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Producto4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DescripcionP3)
                    .addComponent(DescripcionP4))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Producto5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Producto6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DescripcionP5)
                    .addComponent(DescripcionP6))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Producto7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Producto8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DescripcionP7)
                    .addComponent(DescripcionP8))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AnterioresProductos)
                    .addComponent(SiguientesProductos))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        EliminarCesta.setText("Eliminar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ImagenProductoPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Nombre)
                            .addComponent(Precio)
                            .addComponent(Existencias)
                            .addComponent(Descripcion)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Quitar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Cantidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Agregar))
                            .addComponent(AgregarAlaCesta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Imagen2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Imagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Imagen3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Imagen4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EliminarCesta)
                        .addGap(122, 122, 122))))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ImagenProductoPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(Imagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Imagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(AgregarAlaCesta)
                                .addGap(18, 18, 18)
                                .addComponent(Nombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Precio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Existencias)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Quitar)
                                    .addComponent(Cantidad)
                                    .addComponent(Agregar))
                                .addGap(24, 24, 24)
                                .addComponent(Descripcion))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Imagen3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Imagen4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EliminarCesta)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Producto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Producto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Producto1ActionPerformed

    private void Producto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Producto3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Producto3ActionPerformed

    private void Producto5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Producto5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Producto5ActionPerformed

    private void Producto7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Producto7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Producto7ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
        */
        
        
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
            
            new VistaCliente( oos,  ois ).setVisible(true);
            //ListaCategoria lc = AdministradorDeOperaciones.obtenerTodasLasCategorias(oos, ois);
       
            
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.print(e);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JButton AgregarAlaCesta;
    private javax.swing.JButton AnterioresCategorias;
    private javax.swing.JButton AnterioresProductos;
    private javax.swing.JLabel Cantidad;
    private javax.swing.JButton Categoria1;
    private javax.swing.JButton Categoria2;
    private javax.swing.JButton Categoria3;
    private javax.swing.JButton Categoria4;
    private javax.swing.JButton Categoria5;
    private javax.swing.JButton ComprarTodo;
    private javax.swing.JLabel Descripcion;
    private javax.swing.JLabel DescripcionP1;
    private javax.swing.JLabel DescripcionP2;
    private javax.swing.JLabel DescripcionP3;
    private javax.swing.JLabel DescripcionP4;
    private javax.swing.JLabel DescripcionP5;
    private javax.swing.JLabel DescripcionP6;
    private javax.swing.JLabel DescripcionP7;
    private javax.swing.JLabel DescripcionP8;
    private javax.swing.JButton EliminarCesta;
    private javax.swing.JLabel Existencias;
    private javax.swing.JButton Imagen1;
    private javax.swing.JButton Imagen2;
    private javax.swing.JButton Imagen3;
    private javax.swing.JButton Imagen4;
    private javax.swing.JLabel ImagenProductoPrincipal;
    private javax.swing.JLabel JLCML;
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel NombreCategoria;
    private javax.swing.JLabel Precio;
    private javax.swing.JButton Producto1;
    private javax.swing.JButton Producto2;
    private javax.swing.JButton Producto3;
    private javax.swing.JButton Producto4;
    private javax.swing.JButton Producto5;
    private javax.swing.JButton Producto6;
    private javax.swing.JButton Producto7;
    private javax.swing.JButton Producto8;
    private javax.swing.JButton Quitar;
    private javax.swing.JButton SiguientesCategorias;
    private javax.swing.JButton SiguientesProductos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource().equals(Imagen1)) 
        {
             ImagenProductoPrincipal.setIcon(new ImageIcon(productoPrincipal.getImagenesEnBuffer()[0]
                                    .getScaledInstance(ImagenProductoPrincipal.getWidth(), 
                                                       ImagenProductoPrincipal.getHeight(), 
                                                       java.awt.Image.SCALE_DEFAULT)));
        }
        else if (ae.getSource().equals(Imagen2)) 
        {
             ImagenProductoPrincipal.setIcon(new ImageIcon(productoPrincipal.getImagenesEnBuffer()[1]
                                    .getScaledInstance(ImagenProductoPrincipal.getWidth(), 
                                                       ImagenProductoPrincipal.getHeight(), 
                                                       java.awt.Image.SCALE_DEFAULT)));
        }
        else if (ae.getSource().equals(Imagen3)) 
        {
            ImagenProductoPrincipal.setIcon(new ImageIcon(productoPrincipal.getImagenesEnBuffer()[2]
                                    .getScaledInstance(ImagenProductoPrincipal.getWidth(), 
                                                       ImagenProductoPrincipal.getHeight(), 
                                                       java.awt.Image.SCALE_DEFAULT)));
        }
        else if (ae.getSource().equals(Imagen4)) 
        {
            ImagenProductoPrincipal.setIcon(new ImageIcon(productoPrincipal.getImagenesEnBuffer()[3]
                                    .getScaledInstance(ImagenProductoPrincipal.getWidth(), 
                                                       ImagenProductoPrincipal.getHeight(), 
                                                       java.awt.Image.SCALE_DEFAULT)));
        }
        
        if (ae.getSource().equals(Agregar)) 
        {
            if(cantidadDeProductos < productoPrincipal.getExistencias())
            {
                cantidadDeProductos++;
                Cantidad.setText(String.valueOf(cantidadDeProductos));
            }
        }
        
        if (ae.getSource().equals(Quitar)) 
        {
            if(cantidadDeProductos > 1)
            {
                cantidadDeProductos--;
                Cantidad.setText(String.valueOf(cantidadDeProductos));
            }
        }
        
        int i = 0;
        for(i = 0; i < NUMPRODUCTOS; i++)
        {
            if(productos[i] != null)
            {
                if(ae.getSource().equals(productos[i]))
                {
                    this.setProductoPrincipal(producto[i]);
                }
            }
        }
        
        for(i = 0; i < NUMCATEGORIAS; i++)
        {
            if(categorias[i] != null)
            {
                if(ae.getSource().equals(categorias[i]))
                {
                    try {
                        lp = AdministradorDeOperaciones.obtenerProductosDeUnaCategoria(oos, ois, categoria[i].getRuta());
                        setProductos(lp, 0, NUMPRODUCTOS < lp.getSize() ? NUMPRODUCTOS : lp.getSize());
                        if(lp.getSize() > 0)
                            setProductoPrincipal(lp.get(0));
                    } catch (IOException ex) {
                        Logger.getLogger(VistaCliente.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(VistaCliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }
    }
}
