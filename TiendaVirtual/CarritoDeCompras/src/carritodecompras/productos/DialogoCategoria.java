/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import ImagenesInterfaz.UIFunctions;
import carritodecompras.productos.CategoriaArchivo;
import carritodecompras.productos.ControladorCategoria;
import carritodecompras.productos.ListaCategoria;
import static carritodecompras.productos.ControladorCategoria.RUTA_CATEGORIA;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
public class DialogoCategoria extends javax.swing.JDialog {
    String ruta = "Archivos/Categorias/";
    String rutaArchivo = "Archivos/Productos/Categorias/";
    String rutaCarpeta = "Archivos/Productos/Categorias/";
    String aux = ".out";
    /**
     * Creates new form DialogoCategoria
     */
    ControladorCategoria cc;
    JRadioButton radio[];
    ListaCategoria lc;
    
    public DialogoCategoria(java.awt.Frame parent, boolean modal) 
    throws IOException, FileNotFoundException, ClassNotFoundException{
        super(parent,modal);
        initComponents();
        init();
    }
    private void init() throws IOException, FileNotFoundException, ClassNotFoundException{
        //this.setResizable(false);//no permite que sea redimincionable
        this.setLocationRelativeTo(this);//la ventana aparece al centro de la pantalla
        cc = new ControladorCategoria();
        jBAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.print("Hola desde Agregar\n");
                CategoriaArchivo categoria = new CategoriaArchivo();
                String info = null;
                boolean valido = true;
                String error = "";
               
                info = jNombre.getText();
                if(info.length() < 3){
                    error += " Ingresa Nombre valido ";
                    valido = false;
                }
                else{
                    categoria.setNombre(info);
                }
                
                rutaArchivo += info;
                String aux2 = "/" + info + aux;
                rutaArchivo += aux2;
                
                categoria.setRuta(rutaArchivo);
                
                rutaCarpeta += info;
                info = jExtension.getText();
                if(info.length() < 3){
                    error += " Ingresa Extension valida";
                    valido = false;
                }
                else{
                    categoria.setExtension(info);
                }
                
                
                if(valido){
                    categoria.setImagen(ruta);
                    cc.agregarCategoria(categoria,rutaArchivo,rutaCarpeta,jNombre.getText());
                    lc = mostrarCategorias();
                }
                else
                    UIFunctions.warningMessage(error, "Datos invalidos");
                   // System.out.print(error);
            }
        });
        
        jBEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < radio.length; i++){
                    if(radio[i].isSelected()){
                        lc.remove(i);
                    }
                }
                cc.eliminarCategoria(lc);
                lc = mostrarCategorias();
            }
        });
        
        lc = mostrarCategorias();
       
    }
    
    public ListaCategoria mostrarCategorias() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_CATEGORIA));
            ListaCategoria lc = (ListaCategoria)in.readObject();
            JPanel panel = new JPanel(new GridLayout(0, 1));
            radio = new JRadioButton[lc.getSize()];
            
            for (int i = 0; i < lc.getSize(); i++) {
                radio[i] = new JRadioButton();
                radio[i].setText(lc.get(i).getNombre());
                panel.add(radio[i]);
            }
            jScrollPane1.setViewportView(panel);
           // this.add(jScrollPane1);
            return lc;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
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

        jOptionPane1 = new javax.swing.JOptionPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        id = new javax.swing.JLabel();
        jNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jExtension = new javax.swing.JTextField();
        jBAgregar = new javax.swing.JButton();
        jImagen = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Agregar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jBEliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar Categoría");

        id.setText("Nombre");

        jLabel3.setText("Extension");

        jBAgregar.setText("Agregar");

        jImagen.setText("Imagen");
        jImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jImagenActionPerformed(evt);
            }
        });

        jLabel2.setText("Escoja una imagen");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jExtension, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(jNombre)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBAgregar)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jImagen))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id)
                    .addComponent(jNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jExtension, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jImagen)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBAgregar)
                .addContainerGap())
        );

        Agregar.setText("Agregar Categoría");

        jBEliminar.setText("Eliminar");

        jLabel1.setText("Eliminar Categoría");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jBEliminar)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBEliminar)
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jImagenActionPerformed
        JFileChooser jf = new JFileChooser();
        int r = jf.showOpenDialog(null);
        
        if(r == JFileChooser.APPROVE_OPTION){
            File imagen = jf.getSelectedFile();
            String nombre = imagen.getName();
            String path = imagen.getAbsolutePath();
            long tam = imagen.length();
            byte[] b = new byte[1024];
            int creados = 0, n = 0;
            //String ruta = "imagenes/";
            ruta += nombre;
            try{
                DataInputStream dis = new DataInputStream(new FileInputStream(path));
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta));
                
                while(creados < tam){
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    creados = creados + n;
                }
                System.out.println("\nArchivo creado\n");
                jLabel5.setText("Imagen seleccionada");
                dos.close();
                dis.close();
            }catch(Exception e){}
        }
    }//GEN-LAST:event_jImagenActionPerformed

    /**

    public static void main(String args[]) {

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DialogoCategoria().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Agregar;
    private javax.swing.JLabel id;
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JTextField jExtension;
    private javax.swing.JButton jImagen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jNombre;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
