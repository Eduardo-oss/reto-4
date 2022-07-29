package Vistas;

import Controlador.*;
import java.sql.Statement;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import Modelo.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class GestionarSucursalesForm extends javax.swing.JDialog {

  ComboBoxModel enumDepartamentos, enumTipoCalles, enumZonas;
  Conexion conexion = new Conexion();
  Connection connection;
  Statement st; //ejecucion de los query
  ResultSet rs;

  public GestionarSucursalesForm(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    enumDepartamentos = new DefaultComboBoxModel(EnumDepartamento.values());
    enumTipoCalles = new DefaultComboBoxModel(EnumTipoCalle.values());
    enumZonas = new DefaultComboBoxModel(EnumZona.values());
    initComponents();
    this.setLocationRelativeTo(parent);
  }

  public void recibeDatosDireccion(int idDireccion, String departamento, String sucursal, String zona, String tipoCalle, String numero1, String numero2, String numero3) {
    System.out.println("recibiendo información " + "idDir: " + idDireccion + ", idSucursal: " + sucursal + " " + departamento + " " + zona + " "
                + tipoCalle + " " + numero1 + " " + numero2 + " " + numero3);
    enumDepartamentos.setSelectedItem(departamento);
    txtSucursal.setText(sucursal);
    enumTipoCalles.setSelectedItem(tipoCalle);
    enumZonas.setSelectedItem(zona);
    txtNumero1.setText(numero1);
    txtNumero2.setText(numero2);
    txtNumero3.setText(numero3);
  }

  public void actualizarSucursalDireccion() {
            String nombreSucursal = txtSucursal.getText();
        String queryIdSucursal = "SELECT idSucursal, idDireccion FROM `sucursal` INNER JOIN `direccion` WHERE FK_idDireccion = idDireccion AND nombreSucursal = '" + nombreSucursal + "';";
        try {
            connection = conexion.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(queryIdSucursal);
            while (rs.next()) {
                int idSucursal = rs.getInt("idSucursal");
                int idDireccion = rs.getInt("idDireccion");
                String sucursal = txtNuevaSucursal.getText();
                if (sucursal.isEmpty()) {
                    sucursal = txtSucursal.getText();
                } else {
                    
                    String queryActualizarSucursal = "UPDATE `sucursal` SET `nombreSucursal`='" + sucursal + "' WHERE idSucursal = " + idSucursal + ";";
                    try {
                        st.executeUpdate(queryActualizarSucursal);
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                }
                System.out.println(sucursal);
                String numero1 = txtNumero1.getText();
                String numero2 = txtNumero2.getText();
                String numero3 = txtNumero3.getText();
                String departamento = cbDepartamento.getSelectedItem().toString();
                String zona = cbZona.getSelectedItem().toString();
                String tipoCalle = cbTipoCalle.getSelectedItem().toString();
                String queryActualizar = "UPDATE `direccion` SET `zona`='" + zona + "',`tipoCalle`='" + tipoCalle + "',`numero1`='" + numero1 + "',`numero2`='" + numero2 + "',`numero3`='" + numero3 + "',`nombreDepartamento`='" + departamento + "' WHERE idDireccion = " + idDireccion + ";";
                try {
                    st.executeUpdate(queryActualizar);
                    this.dispose();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    
    /*
    //String queryIdSucursal = "SELECT idSucursal, idDireccion FROM sucursal INNER JOIN direccion WHERE direccion.idDireccion = sucursal.FK_idDireccion AND nombreSucursal = '" + sucursal + "';";
    String sucursal = txtSucursal.getText();
    String queryIdSucursal = "SELECT idSucursal, idDireccion FROM sucursal INNER JOIN direccion WHERE FK_idDireccion = idDireccion AND nombreSucursal = '" + sucursal + "';";
    try {
      connection = conexion.getConnection();
      st = connection.createStatement();
      rs = st.executeQuery(queryIdSucursal);
      while (rs.next()) {
        int idSucursal = rs.getInt("idSucursal");
        int idDireccion = rs.getInt("idDireccion");
        String numero1 = txtNumero1.getText();
        String numero2 = txtNumero2.getText();
        String numero3 = txtNumero3.getText();
        String departamento = cbDepartamento.getSelectedItem().toString();
        String tipoCalle = cbTipoCalle.getSelectedItem().toString();
        String zona = cbZona.getSelectedItem().toString();
        //String queryActualizarDireccion = "UPDATE `direccion` SET `zona`='"+zona+"',`tipoCalle`='"+tipoCalle+"',`numero1`='"+numero1+"', `numero2`='"+numero2+"', `numero3`='"+numero3+"', nombreDepartamento = '"+departamento+"' WHERE idDireccion = "+idDireccion+";";
        String queryActualizarDireccion = "UPDATE `direccion` SET `zona`='" + zona + "',`tipoCalle`='" + tipoCalle + "',`numero1`='" + numero1 + "', `numero2`='" + numero2 + "', `numero3`='" + numero3 + "', nombreDepartamento = '" + departamento + "' WHERE idDireccion = " + idDireccion + ";";
        System.out.println(queryActualizarDireccion);
        try {
          st.executeUpdate(queryActualizarDireccion);
        } catch (SQLException e) {
          System.out.println(e);
        }
        String nuevaSucursal = txtNuevaSucursal.getText();
        if (!nuevaSucursal.isEmpty()) {
          //String queryActualizarSucursal = "UPDATE sucursal SET nombreSucursal = '" +nuevaSucursal + "' WHERE idSucursal = "+idSucursal+";";
          String queryActualizarSucursal = "UPDATE sucursal SET nombreSucursal = '" + nuevaSucursal + "' WHERE idSucursal = " + idSucursal + ";";
          System.out.println(queryActualizarSucursal);
          try {
            st.executeUpdate(queryActualizarDireccion);
          } catch (SQLException e) {
            System.out.println(e);
          }
        }
      }

    } catch (SQLException e) {
      System.out.println(e);
    } */
  }

  public void eliminarSucursalDireccion() {
    String sucursal = txtSucursal.getText();
    String queryIdSucursal = "SELECT idSucursal, idDireccion FROM sucursal INNER JOIN direccion WHERE FK_idDireccion = idDireccion AND nombreSucursal = '" + sucursal + "';";
    try {
      connection = conexion.getConnection();
      st = connection.createStatement();
      rs = st.executeQuery(queryIdSucursal);
      while (rs.next()) {
        int idSucursal = rs.getInt("idSucursal");
        int idDireccion = rs.getInt("idDireccion");
        String queryEliminarSucursal = "DELETE FROM sucursal WHERE idSucursal = " + idSucursal + ";";
        try {
          st.executeUpdate(queryEliminarSucursal);
          String queryEliminarDireccion = "DELETE FROM direccion WHERE idDireccion = " + idDireccion + ";";
          try {
            st.executeUpdate(queryEliminarDireccion);
          } catch (SQLException e) {
            System.out.println(e);
          }
        } catch (SQLException e) {
          System.out.println(e);
        }
      }
    } catch (SQLException e) {
      System.out.println(e);
    }

  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    txtSucursal = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    txtNuevaSucursal = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    cbDepartamento = new javax.swing.JComboBox<>();
    jLabel4 = new javax.swing.JLabel();
    cbZona = new javax.swing.JComboBox<>();
    jLabel5 = new javax.swing.JLabel();
    cbTipoCalle = new javax.swing.JComboBox<>();
    txtNumero1 = new javax.swing.JTextField();
    jLabel6 = new javax.swing.JLabel();
    txtNumero2 = new javax.swing.JTextField();
    jLabel7 = new javax.swing.JLabel();
    txtNumero3 = new javax.swing.JTextField();
    btnActualizar = new javax.swing.JButton();
    btnEliminar = new javax.swing.JButton();
    btnCancelar = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jPanel1.setBackground(new java.awt.Color(204, 204, 204));

    jLabel1.setText("Sucursal");

    txtSucursal.setEditable(false);
    txtSucursal.setText(" ");

    jLabel2.setText("Nuevo nombre sucursal");

    txtNuevaSucursal.setText(" ");

    jLabel3.setText("Departamento");

    cbDepartamento.setModel(enumDepartamentos);

    jLabel4.setText("Zona");

    cbZona.setModel(enumZonas);

    jLabel5.setText("Tipo calle");

    cbTipoCalle.setModel(enumTipoCalles);

    jLabel6.setText("#No.");

    jLabel7.setText("-");

    btnActualizar.setText("Actualizar");
    btnActualizar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnActualizarActionPerformed(evt);
      }
    });

    btnEliminar.setText("Eliminar");
    btnEliminar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnEliminarActionPerformed(evt);
      }
    });

    btnCancelar.setText("Cancelar");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
            .addGap(98, 98, 98)
            .addComponent(btnActualizar)
            .addGap(18, 18, 18)
            .addComponent(btnEliminar)
            .addGap(18, 18, 18)
            .addComponent(btnCancelar))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(28, 28, 28)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(jLabel5)
              .addComponent(jLabel3)
              .addComponent(jLabel2)
              .addComponent(jLabel1))
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(txtSucursal)
                .addComponent(txtNuevaSucursal)
                .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(58, 58, 58)
                  .addComponent(jLabel4)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                  .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cbTipoCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        .addContainerGap(30, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGap(31, 31, 31)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(txtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(txtNuevaSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(36, 36, 36)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel4)
          .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel5)
          .addComponent(cbTipoCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel6)
          .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel7)
          .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnActualizar)
          .addComponent(btnEliminar)
          .addComponent(btnCancelar))
        .addGap(24, 24, 24))
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

  private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
    actualizarSucursalDireccion();
    JOptionPane.showMessageDialog(this, "Informacion actualizada con exito");
    this.dispose();
  }//GEN-LAST:event_btnActualizarActionPerformed

  private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    eliminarSucursalDireccion();
    JOptionPane.showMessageDialog(this, "La sucursal ha sido eliminada");
    this.dispose();
  }//GEN-LAST:event_btnEliminarActionPerformed

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
      java.util.logging.Logger.getLogger(GestionarSucursalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(GestionarSucursalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(GestionarSucursalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(GestionarSucursalesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>
    /* Create and display the dialog */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        GestionarSucursalesForm dialog = new GestionarSucursalesForm(new javax.swing.JFrame(), true);
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
  private javax.swing.JButton btnActualizar;
  private javax.swing.JButton btnCancelar;
  private javax.swing.JButton btnEliminar;
  private javax.swing.JComboBox<String> cbDepartamento;
  private javax.swing.JComboBox<String> cbTipoCalle;
  private javax.swing.JComboBox<String> cbZona;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JTextField txtNuevaSucursal;
  private javax.swing.JTextField txtNumero1;
  private javax.swing.JTextField txtNumero2;
  private javax.swing.JTextField txtNumero3;
  private javax.swing.JTextField txtSucursal;
  // End of variables declaration//GEN-END:variables
}
