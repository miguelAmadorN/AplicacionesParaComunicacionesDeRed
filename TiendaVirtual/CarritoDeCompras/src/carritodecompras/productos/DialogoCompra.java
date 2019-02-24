/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carritodecompras.productos;

import ImagenesInterfaz.UIFunctions;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author miguel
 */
public class DialogoCompra extends javax.swing.JDialog implements ActionListener {

    /**
     * Creates new form DialogoCompra
     */
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    JRadioButton jrb[];
    ArrayList productosCesta;
    ListaEmpresasDeEnvio le;
    public DialogoCompra(java.awt.Frame parent, boolean modal,  ArrayList productosCesta,
            ObjectInputStream ois, ObjectOutputStream oos ) 
            throws IOException, ClassNotFoundException 
    {
        super(parent, modal);
        this.ois = ois;
        this.oos = oos;
        this.productosCesta = productosCesta;
        initComponents();
        init();
        this.setTitle("Datos de compra"); 
        this.setResizable(false);//no permite que sea redimincionable
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass()
                        .getResource("/ImagenesInterfaz/Icono.png"));
        setIconImage(icon);
        this.setLocationRelativeTo(null);
        
    }

    private void init() throws IOException, ClassNotFoundException
    {
            
        
         placeholder( "Nombres", nombres);
         placeholder("Primer Apellido",primerApellido);
         placeholder("Segundo Apellido",segundoApellido);
         placeholder("E-mail",email);
         placeholder("Telefono",telefono);
         
         placeholder("Nombre y Apellidos",nombreYApellidos);
         placeholder("Dirección",direccion);
         placeholder("País", pais);
         placeholder("Telefono", telefonoe);
         placeholder("E-mail", emaile);
         
         placeholder("Número de Tarjeta",numeroDeTajeta);
         placeholder("Código de seguridad",codigoDeSeguridad);
         placeholder("Vencimiento MM",mm);
         placeholder("Vencimiento YY",yy);
         
         
         
         mostrarPaqueterias();
         
         comprar.addActionListener(this);
        
    }
    
    private void placeholder(String text, JTextField textField)
    {
        TextPrompt placeholder = new TextPrompt(text, textField);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
    }
    
    private void mostrarPaqueterias() throws IOException, ClassNotFoundException
    {
        le = AdministradorDeOperaciones.obtenerEmpresasDeEnvio(oos, ois);
        JPanel panel = new JPanel(new GridLayout(0, 2));
        EmpresaDeEnvio ee;
        int tam = le.getSize();
        jrb = new JRadioButton[tam];
        
        JLabel jl[] = new JLabel[tam];
            for (int i = 0; i < tam; i++) 
            {
                ee = le.get(i);
                jrb[i] = new JRadioButton();
                jrb[i].addActionListener(this);
                jrb[i].setText(UIFunctions.formatoDescripcion(ee.getNombre() +
                                                            ", Tiempo de entrega: " + ee.getTiempoDeEntrega() +
                                                             ", Costo: $" + ee.getCosto() , 20));
                panel.add(jrb[i]);
                jl[i] = new JLabel();
                jl[i].setSize(50, 50);
                panel.add(jl[i]);
            }
            jScrollPane1.setViewportView(panel);   
    }
    
    private EmpresaDeEnvio getEmpresaEnvio()
    {
        for(int i = 0; i < jrb.length; i++)
        {
            if(jrb[i].isSelected())
                return le.get(i);
        }
        return null;
    }
    
    private void comprar() 
    {
        /**
         * Validar todos los datos, y mostrar al usuario
         */
        boolean sinErrores = true;
        String mensaje = "";
        
        Usuario usuario = new Usuario();
        usuario.setNombres(nombres.getText());
        usuario.setId("");
        usuario.setPrimerApellido(primerApellido.getText());
        usuario.setSegundoApellido(segundoApellido.getText());
        usuario.setEmail(email.getText());
        usuario.setTelefono(telefono.getText());
        
        
        
        InformacionDeEnvio ie = new InformacionDeEnvio();
        ie.setDireccion(direccion.getText());
        ie.setEmail(emaile.getText());
        ie.setTelefono(telefono.getText());
        ie.setNombreYApellidos(nombreYApellidos.getText());
        usuario.setInformacionDeEnvio(ie);

        TarjetaDePago tp = new TarjetaDePago();

        tp.setNumeroDeTajeta(numeroDeTajeta.getText());
        /**
         * No estamos ocupando estos campos para hacer el pdf así que mejor no
         * los validamos private String CodigoDeSeguridad; private String
         * NombreTitular; private String ApellidosTitular; private short
         * FechaCaducidadMM; private short FechaCaducidadYY;
         */
        tp.setCodigoDeSeguridad("");
        tp.setNombreTitular("");
        tp.setApellidosTitular("");
        tp.setFechaCaducidadMM((short) 0);
        tp.setFechaCaducidadYY((short) 0);
        usuario.setTarjetaDePago(tp);
        
        EmpresaDeEnvio ev = getEmpresaEnvio();
        if(ev == null)
        {
            mensaje = "Selecciona una empresa de envío";
            sinErrores = false;
        }

        usuario.setEmpresaDeEnvio(ev);
        

        if (sinErrores) 
        {
            int tam = productosCesta.size();
            ProductoCompra pc[] = new ProductoCompra[tam];
            for (int i = 0; i < tam; i++) 
                pc[i] = (ProductoCompra) productosCesta.get(i);
            
            try {
                AdministradorDeOperaciones.comprar(oos, ois, new CarritoCompras(pc, usuario));
            } catch (IOException ex) {
                Logger.getLogger(DialogoCompra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DialogoCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
             UIFunctions.informationMessage(mensaje, "Datos Invalidos");
        }
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
        nombres = new javax.swing.JTextField();
        primerApellido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        segundoApellido = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        telefono = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nombreYApellidos = new javax.swing.JTextField();
        pais = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        telefonoe = new javax.swing.JTextField();
        emaile = new javax.swing.JTextField();
        comprar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        numeroDeTajeta = new javax.swing.JTextField();
        codigoDeSeguridad = new javax.swing.JTextField();
        mm = new javax.swing.JTextField();
        yy = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(254, 254, 254));
        setForeground(new java.awt.Color(254, 254, 254));

        jLabel1.setText("Usuario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombres)
                            .addComponent(primerApellido)
                            .addComponent(segundoApellido)
                            .addComponent(email)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(telefono)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 58, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(47, 47, 47))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(primerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(segundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Información de envío");

        comprar.setText("Confirmar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(emaile, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nombreYApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(pais)
                            .addComponent(direccion)
                            .addComponent(telefonoe))))
                .addGap(171, 171, 171))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(comprar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombreYApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(telefonoe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emaile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comprar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(254, 254, 254));

        jLabel4.setText("Paquetería");

        jLabel3.setText("Tarjeta de pago");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 45, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(23, 23, 23))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numeroDeTajeta)
                    .addComponent(codigoDeSeguridad)
                    .addComponent(mm)
                    .addComponent(yy)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numeroDeTajeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codigoDeSeguridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(60, 60, 60))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codigoDeSeguridad;
    private javax.swing.JButton comprar;
    private javax.swing.JTextField direccion;
    private javax.swing.JTextField email;
    private javax.swing.JTextField emaile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mm;
    private javax.swing.JTextField nombreYApellidos;
    private javax.swing.JTextField nombres;
    private javax.swing.JTextField numeroDeTajeta;
    private javax.swing.JTextField pais;
    private javax.swing.JTextField primerApellido;
    private javax.swing.JTextField segundoApellido;
    private javax.swing.JTextField telefono;
    private javax.swing.JTextField telefonoe;
    private javax.swing.JTextField yy;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == comprar)
        {
            comprar();
        }
        else
        {
            int tam = jrb.length;
            for(int i = 0; i < tam; i++)
            {
                if(ae.getSource() == jrb[i])
                {
                     for(int j = 0; j < tam ; j++ )
                         if(j != i)
                         {
                             jrb[j].setSelected(false);
                         }
                     break;
                }
            }
        }
    }
}
