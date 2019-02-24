/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import ImagenesInterfaz.UIFunctions;
import carritodecompras.productos.ControladorPaqueteria;
import static carritodecompras.productos.ControladorPaqueteria.RUTA_PAQUETERIA;
import carritodecompras.productos.EmpresaDeEnvio;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

/**
 *
 * @author miguel
 */
public class DialogoPaqueteria extends javax.swing.JDialog {

    /**
     * Creates new form DialogoPaqueteria
     */
    ControladorPaqueteria cp;
    JRadioButton radio[];
    ListaEmpresasDeEnvio lede;
    public DialogoPaqueteria(java.awt.Frame parent, boolean modal) 
    throws IOException, FileNotFoundException, ClassNotFoundException
    {
        super(parent, modal);
        initComponents(); 
        init();
    }
    
    private void init() throws IOException, FileNotFoundException, ClassNotFoundException
    {
        //this.setResizable(false);//no permite que sea redimincionable
        this.setLocationRelativeTo(this);//la ventana aparece al centro de la pantalla
        cp = new ControladorPaqueteria();
        jBAgregar.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                System.out.print("Hola desde Agregar\n");
                EmpresaDeEnvio paqueteria = new EmpresaDeEnvio();
                String info = null;
                boolean valido = true;
                String error = "";
               
                info = jTFid.getText();
                if(info.length() < 3)
                {
                    error += " Ingresa Id valido ";
                    valido = false;
                }
                else
                {
                    paqueteria.setId(Integer.parseInt(jTFid.getText()));
                }
                info = jTFnombre.getText();
                if(info.length() < 3)
                {
                    error += " Ingresa Nombre valido";
                    valido = false;
                }
                else
                {
                    paqueteria.setNombre(jTFnombre.getText());
                }
                
                info = jTFtiempo.getText();
                if(info.length() < 1)
                {
                    error += " Ingresa Tiempo ";
                    valido = false;
                }
                else
                {
                    paqueteria.setTiempoDeEntrega(jTFtiempo.getText());
                }
                
                info = jTFcosto.getText();
                if(info.length() < 1)
                {
                    error += " Ingresa costo ";
                    valido = false;
                }
                else
                {
                    paqueteria.setCosto(Double.parseDouble(jTFcosto.getText()));
                }
                paqueteria.setDisponibilidad(jCBdisponibilidad.isSelected());
                if(valido)
                {
                    cp.agregarPaqueteria(paqueteria);
                    lede = mostrarPaqueterias();
                }
                else
                    UIFunctions.warningMessage(error, "Datos invalidos");
                   // System.out.print(error);
            }
        });
        
        jBEliminar.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                for(int i = 0; i < radio.length; i++)
                {
                    if(radio[i].isSelected())
                    {
                        lede.remove(i);
                    }
                }
                cp.eliminarPaqueterias(lede);
                lede = mostrarPaqueterias();
            }
        });
        
        lede = mostrarPaqueterias();
       
    }
    
    public ListaEmpresasDeEnvio mostrarPaqueterias() 
    {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_PAQUETERIA));
            ListaEmpresasDeEnvio le = (ListaEmpresasDeEnvio) in.readObject();
            JPanel panel = new JPanel(new GridLayout(0, 1));
            radio = new JRadioButton[le.getSize()];
            for (int i = 0; i < le.getSize(); i++) {
                radio[i] = new JRadioButton();
                radio[i].setText(le.get(i).getNombre() + ", " + le.get(i).getTiempoDeEntrega() + ", " +
                              "$" + le.get(i).getCosto() );
                panel.add(radio[i]);
            }
            jScrollPane1.setViewportView(panel);
           // this.add(jScrollPane1);
            return le;
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

        jPanel1 = new javax.swing.JPanel();
        id = new javax.swing.JLabel();
        jTFid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTFnombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFtiempo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTFcosto = new javax.swing.JTextField();
        jCBdisponibilidad = new javax.swing.JCheckBox();
        jBAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Agregar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jBEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        id.setText("ID");

        jLabel2.setText("Nombre");

        jLabel3.setText("Tiempo");

        jLabel4.setText("Costo");

        jCBdisponibilidad.setText("Disponible");

        jBAgregar.setText("Agregar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(id)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFtiempo)
                            .addComponent(jTFnombre)
                            .addComponent(jTFcosto)
                            .addComponent(jTFid)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCBdisponibilidad)
                        .addGap(0, 90, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBAgregar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id)
                    .addComponent(jTFid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFtiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFcosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jCBdisponibilidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBAgregar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Agregar.setText("Agregar Paquetería");

        jLabel1.setText("Eliminar Paqueterías");

        jBEliminar.setText("Eliminar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jBEliminar)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(48, 48, 48))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Agregar)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBEliminar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Agregar;
    private javax.swing.JLabel id;
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JCheckBox jCBdisponibilidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFcosto;
    private javax.swing.JTextField jTFid;
    private javax.swing.JTextField jTFnombre;
    private javax.swing.JTextField jTFtiempo;
    // End of variables declaration//GEN-END:variables
}
