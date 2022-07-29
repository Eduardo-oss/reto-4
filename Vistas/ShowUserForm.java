package Vistas;
//import com.sun.jdi.connect.spi.Connection;
import Controlador.CbSucursal;
import java.sql.Statement;
import java.sql.Connection;//
import java.sql.ResultSet;
import Modelo.Conexion;//
import Modelo.Sucursal;
import java.sql.SQLException;//
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;//

public class ShowUserForm extends javax.swing.JDialog {

  //creacion de 4 variables para conectar con base de datos 
  Conexion conexion = new Conexion();
  Connection connection;//clase de MySQL
  Statement st;
  ResultSet rs;

  public ShowUserForm(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    this.setLocationRelativeTo(parent);
  }
  public void recibirDatosDeUserMenu(int idEmp, String nombresucursal, String nombres, String apellidos, String tipoDocumento, String documento, String correo ){
    txtIdEmp.setText("" + idEmp);
    txtSucursal.setText(nombresucursal);
    txtNombre.setText(nombres);
    txtApellidos.setText(apellidos);
    txtTipoDocumento.setText(tipoDocumento);
    txtDocumento.setText(documento);
    txtCorreo.setText(correo);
  }
// metodo que reciba los datos que le va a pasar addUser
  public void actualizarEmpleado() {
    int idEmp = Integer.parseInt(txtIdEmp.getText());// idEmp es un campo numerico y convertirlo de texto a numero  OR int num = Integer,parseInt("2");
    String nombreEmp = txtNombre.getText();
    String apellidos = txtApellidos.getText();
    String email = txtCorreo.getText();
    if (nombreEmp.isEmpty()) {
      JOptionPane.showMessageDialog(this, "El nombre del empleado es requerido", "", JOptionPane.WARNING_MESSAGE); //warninig_message simbolo de advertencia
    } else if (apellidos.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Los apellidos de empleados son requeridos", "", JOptionPane.WARNING_MESSAGE);
    } else if (email.isEmpty()) {
      JOptionPane.showMessageDialog(this, "El correo electronico del empleado es  requerido", "", JOptionPane.WARNING_MESSAGE);
    } else {
      String query = "UPDATE `empleado` SET `nombreEmp`='" + nombreEmp + "',`apellidos`='" + apellidos + "',`correo`='" + email + "'WHERE idEmp =" + idEmp + ";";
      System.out.println(query);
      try {
        connection = conexion.getConnection();// devuelve si logre hacer conexion o no
        st = connection.createStatement();
        st.executeUpdate(query);
        JOptionPane.showMessageDialog(this, "El usuario ha sido actualizado");
      } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "El usuario no ha sido actualizado", "", JOptionPane.ERROR_MESSAGE);
      }
      this.dispose();
    }
  }
  public void eliminarEmpleado() {
    //consultamos el id del empleado
    int idEmp = Integer.parseInt(txtIdEmp.getText()); //capturar ID
    //validar si el usuario no ha seleccionado un empleado
    String query = "DELETE FROM `empleado` WHERE idEmp = " + idEmp + ";";
    //try catch para establecer conexion
    System.out.println("query");
    try {
      //intente hacer eso
      connection = conexion.getConnection();
      st = connection.createStatement();
      st.executeUpdate(query);
      JOptionPane.showMessageDialog(this, "El usuario ha sido eliminado");
    } catch (SQLException e) { //de lo contrario arroje una excepcion
      JOptionPane.showMessageDialog(this, "No se pudo eliminar el usuario", "", JOptionPane.ERROR_MESSAGE);
    }
    this.dispose();
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    txtIdEmp = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    txtNombre = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    txtApellidos = new javax.swing.JTextField();
    txtTipoDocumento = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    txtDocumento = new javax.swing.JTextField();
    jLabel7 = new javax.swing.JLabel();
    txtCorreo = new javax.swing.JTextField();
    btnEditar = new javax.swing.JButton();
    btnEliminar = new javax.swing.JButton();
    btnRegresar = new javax.swing.JButton();
    jLabel8 = new javax.swing.JLabel();
    txtSucursal = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jPanel1.setBackground(new java.awt.Color(204, 204, 204));

    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logo.png"))); // NOI18N

    jLabel2.setText("idEmp");

    txtIdEmp.setEditable(false);

    jLabel3.setText("Nombre(s)");

    jLabel4.setText("Apellido(s)");

    txtTipoDocumento.setEditable(false);

    jLabel5.setText("Tipo documento");

    jLabel6.setText("N° documento");

    txtDocumento.setEditable(false);

    jLabel7.setText("Correo");

    btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/editUser.png"))); // NOI18N
    btnEditar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnEditarActionPerformed(evt);
      }
    });

    btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/deleteUser.png"))); // NOI18N
    btnEliminar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnEliminarActionPerformed(evt);
      }
    });

    btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/backIcon.png"))); // NOI18N
    btnRegresar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnRegresarActionPerformed(evt);
      }
    });

    jLabel8.setText("Sucursal");

    txtSucursal.setEditable(false);

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addComponent(jLabel1)
        .addGap(0, 0, Short.MAX_VALUE))
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGap(122, 122, 122)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(0, 86, Short.MAX_VALUE)
            .addComponent(btnEditar)
            .addGap(87, 87, 87)
            .addComponent(btnEliminar)
            .addGap(68, 68, 68)
            .addComponent(btnRegresar)
            .addGap(94, 94, 94))
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel2))
              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel7)
                .addComponent(jLabel4)
                .addComponent(jLabel3)
                .addComponent(jLabel5)
                .addComponent(jLabel6)
                .addComponent(jLabel8)))
            .addGap(29, 29, 29)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(txtNombre)
              .addComponent(txtApellidos)
              .addComponent(txtTipoDocumento)
              .addComponent(txtDocumento)
              .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
              .addComponent(txtIdEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtSucursal))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(btnRegresar))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(jLabel3)
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE))
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8))
                  .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                      .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIdEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                      .addComponent(jLabel1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                    .addComponent(txtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(btnEditar)
              .addComponent(btnEliminar))))
        .addContainerGap(353, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
    // TODO add your handling code here:
    this.actualizarEmpleado();//this porque es un metodo propio de esta clase


  }//GEN-LAST:event_btnEditarActionPerformed

  private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    // TODO add your handling code here:
    this.eliminarEmpleado();//this apuntador indicado por buena practica de programacion
  }//GEN-LAST:event_btnEliminarActionPerformed

  private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
    // TODO add your handling code here:
    this.dispose();
  }//GEN-LAST:event_btnRegresarActionPerformed

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
      java.util.logging.Logger.getLogger(ShowUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(ShowUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(ShowUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(ShowUserForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the dialog */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        ShowUserForm dialog = new ShowUserForm(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
          @Override
          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnEditar;
  private javax.swing.JButton btnEliminar;
  private javax.swing.JButton btnRegresar;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JTextField txtApellidos;
  private javax.swing.JTextField txtCorreo;
  private javax.swing.JTextField txtDocumento;
  private javax.swing.JTextField txtIdEmp;
  private javax.swing.JTextField txtNombre;
  private javax.swing.JTextField txtSucursal;
  private javax.swing.JTextField txtTipoDocumento;
  // End of variables declaration//GEN-END:variables
}
