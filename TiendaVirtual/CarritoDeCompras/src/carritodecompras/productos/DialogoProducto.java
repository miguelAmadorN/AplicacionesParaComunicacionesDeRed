/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import ImagenesInterfaz.UIFunctions;
import static carritodecompras.productos.ControladorCategoria.RUTA_CATEGORIA;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author rodrigo
 */
public class DialogoProducto extends javax.swing.JDialog {
    public static String RUTA = "Archivos/Productos/Categorias/";
    String categoria, rutaImagen1 = "", rutaImagen2 = "", rutaImagen3 = "", rutaImagen4 = "", rutaCategoria = "";
    String datos;
    ControladorProducto cp;
    JRadioButton radio[];
    ListaProductos lp;
    /**
     * Creates new form DialogoUsuario
     */
    public DialogoProducto(java.awt.Frame parent, boolean modal) {
        super(parent,modal);
        initComponents();
        init();
    }

    private void init() {

        this.setLocationRelativeTo(this);//la ventana aparece al centro de la pantalla
        cp = new ControladorProducto();
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_CATEGORIA));
            ListaCategoria lc = (ListaCategoria)in.readObject();
            
            for(int i = 0; i < lc.getSize(); i++){
                jCategorias.addItem(lc.get(i).getNombre());
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        jBAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.print("Hola desde Agregar\n");
                ProductoArchivo producto = new ProductoArchivo();
                String info = null;
                String[] info2 = new String[4];
                boolean valido = true;
                String error = "";
                info = jCategorias.getSelectedItem().toString();
                producto.setCategoria(info);
                
                info = jId.getText();
                if(Integer.parseInt(info) < -1){
                    error += " Ingresa ID valido ";
                    valido = false;
                }
                else{
                    producto.setId(Integer.parseInt(info));
                }
                
                info = jNombre.getText();
                if(info.length() < 3){
                    error += " Ingresa Nombre valido ";
                    valido = false;
                }
                else{
                    producto.setNombre(info);
                }
                
                info = jDescripcion.getText();
                if(info.length() < 3){
                    error += " Ingresa Descripción valida ";
                    valido = false;
                }
                else{
                    producto.setDescripcion(info);
                }
                
                info = jPrecio.getText();
                if(Double.parseDouble(info) < -1){
                    error += " Ingresa Precio valido ";
                    valido = false;
                }
                else{
                    producto.setPrecio(Double.parseDouble(info));
                }
                
                info = jExistencias.getText();
                if(Integer.parseInt(info) < -1){
                    error += " Ingresa Existencia valida ";
                    valido = false;
                }
                else{
                    producto.setExistencias(Integer.parseInt(info));
                }
                
                info2[0] = jColores.getText();
                producto.setColores(info2);
                
                info2[0] = jMaterial.getText();
                producto.setMateriales(info2);
                
                info2[0] = jPalabra1.getText();
                info2[1] = jPalabra2.getText();
                
                producto.setPalabrasClave(info2);
                
                info2[0] = rutaImagen1;
                info2[1] = rutaImagen2;
                info2[2] = rutaImagen3;
                info2[3] = rutaImagen4;
                
                producto.setImagenes(info2);
                
                if(valido){
                    cp.agregarProducto(producto,categoria);
                    lp = mostrarProductos();
                    
                }
                else
                    UIFunctions.warningMessage(error, "Datos invalidos");
                   // System.out.print(error);
            }
        });
        lp = mostrarProductos();
        
        
        /*jBEliminar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < radio.length; i++){
                    if(radio[i].isSelected()){
                        lp.remove(i);
                    }
                }
                cp.eliminarProducto(lp,categoria);
                lp = mostrarProductos();
            }

        });*/

    }
    
    public ListaProductos mostrarProductos() {
        String aux = "";
        aux = jCategorias.getSelectedItem().toString(); //Tomar este ejemplo
        rutaCategoria = RUTA + aux + "/" + aux + ".out";
        System.out.println(rutaCategoria);
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaCategoria));
            ListaProductos lp = (ListaProductos)in.readObject();
            JPanel panel = new JPanel(new GridLayout(0, 1));
            radio = new JRadioButton[lp.getSize()];
            
            for (int i = 0; i < lp.getSize(); i++) {
                radio[i] = new JRadioButton();
                radio[i].setText(lp.get(i).getNombre());
                panel.add(radio[i]);
            }
            jScrollPane1.setViewportView(panel);
           // this.add(jScrollPane1);
            return lp;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        rutaCategoria = "";
        return null;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        id = new javax.swing.JLabel();
        jId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jNombre = new javax.swing.JTextField();
        jBAgregar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jDescripcion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        id1 = new javax.swing.JLabel();
        jPrecio = new javax.swing.JTextField();
        id2 = new javax.swing.JLabel();
        jExistencias = new javax.swing.JTextField();
        jColores = new javax.swing.JTextField();
        id3 = new javax.swing.JLabel();
        jMaterial = new javax.swing.JTextField();
        id4 = new javax.swing.JLabel();
        id5 = new javax.swing.JLabel();
        jImagen1 = new javax.swing.JButton();
        id6 = new javax.swing.JLabel();
        jCategorias = new javax.swing.JComboBox<>();
        id7 = new javax.swing.JLabel();
        jPalabra1 = new javax.swing.JTextField();
        jPalabra2 = new javax.swing.JTextField();
        id8 = new javax.swing.JLabel();
        id9 = new javax.swing.JLabel();
        jImagen2 = new javax.swing.JButton();
        id10 = new javax.swing.JLabel();
        jImagen3 = new javax.swing.JButton();
        id11 = new javax.swing.JLabel();
        jImagen4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Agregar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jBEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        id.setText("ID");

        jLabel3.setText("Nombre");

        jBAgregar.setText("Agregar");

        jDescripcion.setColumns(20);
        jDescripcion.setRows(5);
        jScrollPane2.setViewportView(jDescripcion);

        jLabel4.setText("Descripción");

        id1.setText("Precio");

        id2.setText("Existencias");

        id3.setText("Colores");

        id4.setText("Material");

        id5.setText("Imagen 1");

        jImagen1.setText("Seleccione una imagen");
        jImagen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jImagen1ActionPerformed(evt);
            }
        });

        id6.setText("Categoría");

        jCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCategoriasActionPerformed(evt);
            }
        });

        id7.setText("Palabra Clave 1");

        id8.setText("Palabra Clave 2");

        id9.setText("Imagen 2");

        jImagen2.setText("Seleccione una imagen");
        jImagen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jImagen2ActionPerformed(evt);
            }
        });

        id10.setText("Imagen 3");

        jImagen3.setText("Seleccione una imagen");
        jImagen3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jImagen3ActionPerformed(evt);
            }
        });

        id11.setText("Imagen 4");

        jImagen4.setText("Seleccione una imagen");
        jImagen4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jImagen4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(id6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(id1)
                            .addComponent(id2)
                            .addComponent(id3)
                            .addComponent(id4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jMaterial)
                            .addComponent(jColores)
                            .addComponent(jExistencias)
                            .addComponent(jPrecio)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(jNombre)
                            .addComponent(jId)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBAgregar)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id5)
                            .addComponent(id9)
                            .addComponent(id10)
                            .addComponent(id11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jImagen4)
                            .addComponent(jImagen3)
                            .addComponent(jImagen2)
                            .addComponent(jImagen1))
                        .addGap(38, 38, 38))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id7)
                            .addComponent(id8))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPalabra1)
                            .addComponent(jPalabra2))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id6)
                    .addComponent(jCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id)
                    .addComponent(jId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id1)
                    .addComponent(jPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id2)
                    .addComponent(jExistencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id3)
                    .addComponent(jColores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id4)
                    .addComponent(jMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id5)
                    .addComponent(jImagen1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id9)
                    .addComponent(jImagen2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jImagen3)
                    .addComponent(id10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id11)
                    .addComponent(jImagen4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id7)
                    .addComponent(jPalabra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id8)
                    .addComponent(jPalabra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jBAgregar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Agregar.setText("Agregar Producto");

        jLabel1.setText("Eliminar Producto");

        jBEliminar.setText("Eliminar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBEliminar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Agregar)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jBEliminar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jImagen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jImagen1ActionPerformed
        JFileChooser jf = new JFileChooser();
        int r = jf.showOpenDialog(null);
        
        if(r == JFileChooser.APPROVE_OPTION){
            File imagen = jf.getSelectedFile();
            String nombre = imagen.getName();
            String path = imagen.getAbsolutePath();
            long tam = imagen.length();
            byte[] b = new byte[1024];
            int creados = 0, n = 0;
            rutaImagen1 = RUTA + datos + "/" + nombre;
            System.out.println(rutaImagen1);
            try{
                DataInputStream dis = new DataInputStream(new FileInputStream(path));
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaImagen1));
                
                while(creados < tam){
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    creados = creados + n;
                }
                System.out.println("\nArchivo creado\n");
                dos.close();
                dis.close();
            }catch(Exception e){}
        }
    }//GEN-LAST:event_jImagen1ActionPerformed

    private void jImagen4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jImagen4ActionPerformed
        JFileChooser jf = new JFileChooser();
        int r = jf.showOpenDialog(null);

        if(r == JFileChooser.APPROVE_OPTION){
            File imagen = jf.getSelectedFile();
            String nombre = imagen.getName();
            String path = imagen.getAbsolutePath();
            long tam = imagen.length();
            byte[] b = new byte[1024];
            int creados = 0, n = 0;
            rutaImagen4 = RUTA + datos + "/" + nombre;
            try{
                DataInputStream dis = new DataInputStream(new FileInputStream(path));
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaImagen4));

                while(creados < tam){
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    creados = creados + n;
                }
                System.out.println("\nArchivo creado\n");
                dos.close();
                dis.close();
            }catch(Exception e){}
        }
    }//GEN-LAST:event_jImagen4ActionPerformed

    private void jImagen3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jImagen3ActionPerformed
        JFileChooser jf = new JFileChooser();
        int r = jf.showOpenDialog(null);

        if(r == JFileChooser.APPROVE_OPTION){
            File imagen = jf.getSelectedFile();
            String nombre = imagen.getName();
            String path = imagen.getAbsolutePath();
            long tam = imagen.length();
            byte[] b = new byte[1024];
            int creados = 0, n = 0;
            rutaImagen3 = RUTA + datos + "/" + nombre;
            try{
                DataInputStream dis = new DataInputStream(new FileInputStream(path));
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaImagen3));

                while(creados < tam){
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    creados = creados + n;
                }
                System.out.println("\nArchivo creado\n");
                dos.close();
                dis.close();
            }catch(Exception e){}
        }
    }//GEN-LAST:event_jImagen3ActionPerformed

    private void jImagen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jImagen2ActionPerformed
        JFileChooser jf = new JFileChooser();
        int r = jf.showOpenDialog(null);

        if(r == JFileChooser.APPROVE_OPTION){
            File imagen = jf.getSelectedFile();
            String nombre = imagen.getName();
            String path = imagen.getAbsolutePath();
            long tam = imagen.length();
            byte[] b = new byte[1024];
            int creados = 0, n = 0;
            rutaImagen2 = RUTA + datos + "/" + nombre;
            try{
                DataInputStream dis = new DataInputStream(new FileInputStream(path));
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaImagen2));

                while(creados < tam){
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    creados = creados + n;
                }
                System.out.println("\nArchivo creado\n");
                dos.close();
                dis.close();
            }catch(Exception e){}
        }
    }//GEN-LAST:event_jImagen2ActionPerformed

    private void jCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCategoriasActionPerformed
        datos = jCategorias.getSelectedItem().toString(); //Tomar este ejemplo
        categoria = jCategorias.getSelectedItem().toString();
        System.out.println("La categoria es: " + datos);
        lp = mostrarProductos();
    }//GEN-LAST:event_jCategoriasActionPerformed
/**/
    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new DialogoProducto().setVisible(true);
        }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Agregar;
    private javax.swing.JLabel id;
    private javax.swing.JLabel id1;
    private javax.swing.JLabel id10;
    private javax.swing.JLabel id11;
    private javax.swing.JLabel id2;
    private javax.swing.JLabel id3;
    private javax.swing.JLabel id4;
    private javax.swing.JLabel id5;
    private javax.swing.JLabel id6;
    private javax.swing.JLabel id7;
    private javax.swing.JLabel id8;
    private javax.swing.JLabel id9;
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JComboBox<String> jCategorias;
    private javax.swing.JTextField jColores;
    private javax.swing.JTextArea jDescripcion;
    private javax.swing.JTextField jExistencias;
    private javax.swing.JTextField jId;
    private javax.swing.JButton jImagen1;
    private javax.swing.JButton jImagen2;
    private javax.swing.JButton jImagen3;
    private javax.swing.JButton jImagen4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jMaterial;
    private javax.swing.JTextField jNombre;
    private javax.swing.JTextField jPalabra1;
    private javax.swing.JTextField jPalabra2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jPrecio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
