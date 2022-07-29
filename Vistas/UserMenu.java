package Vistas;

import Controlador.EnumZona;
import Controlador.EnumDepartamento;
import Controlador.EnumTipoCalle;
import Modelo.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UserMenu extends javax.swing.JFrame {

  Conexion conexion = new Conexion();
  Connection connection;
  Statement st; //ejecucion de los query
  ResultSet rs;//trae los resultados de conexion cuando lance los query
  DefaultTableModel contenidoTablaEmpleados, contenidoTablaDepartamentos;
  ComboBoxModel enumDepartamentos, enumZona, enumTipoCalle;

  public UserMenu() {

    //inicializar comboboxModel y obtenre la informacion
    enumDepartamentos = new DefaultComboBoxModel(EnumDepartamento.values());
    enumZona = new DefaultComboBoxModel(EnumZona.values());
    enumTipoCalle = new DefaultComboBoxModel(EnumTipoCalle.values());
    initComponents();
    this.setLocationRelativeTo(this);
    listarEmpleados();
    listarDepartamentos();

  }

  private void listarDepartamentos() {
    String filtro = txtSearchSucursal.getText();
    if (filtro.isEmpty()) {
      //haga una consulta en la vase de datos 
      String query = "SELECT nombreSucursal, nombreDepartamento, CONCAT('Zona', zona, '. ', tipoCalle, ' ', numero1, ' #No. ', numero2, ' - ', numero3) AS direccion FROM `sucursal` INNER JOIN `direccion` WHERE idDireccion = FK_idDireccion GROUP BY nombreDepartamento, nombreSucursal;";
      try {
        connection = conexion.getConnection();
        st = connection.createStatement();
        rs = st.executeQuery(query);
        //crear objeto para listar la tabla 
        Object[] departamento = new Object[3];//2 es de dos campos
        contenidoTablaDepartamentos = (DefaultTableModel) tblDepartamentos.getModel();
        while (rs.next()) {
          departamento[0] = rs.getString("nombreSucursal");
          departamento[1] = rs.getString("nombreDepartamento");
          departamento[2] = rs.getString("direccion");
          //guardar a informacion 
          contenidoTablaDepartamentos.addRow(departamento);
          tblDepartamentos.setModel(contenidoTablaDepartamentos);

        }

      } catch (SQLException e) {
        System.out.println(e);
      }
      //////////////////////////////////////////////////////////

    }//aqui cierra el if
    else {
      String filtroSucursales = "SELECT nombreSucursal, nombreDepartamento, CONCAT('Zona', zona, '. ', tipoCalle, ' ', numero1, ' #No. ', numero2, ' - ', numero3) AS direccion FROM `sucursal` INNER JOIN `direccion` WHERE idDireccion = FK_idDireccion AND nombreDepartamento LIKE '%" + filtro + "%' GROUP BY nombreDepartamento, nombreSucursal;";
      System.out.println(filtroSucursales);

      try {
        connection = conexion.getConnection();
        st = connection.createStatement();
        rs = st.executeQuery(filtroSucursales);
        Object[] departamento = new Object[3];//2 es de dos campos
        contenidoTablaDepartamentos = (DefaultTableModel) tblDepartamentos.getModel();
        while (rs.next()) {
          departamento[0] = rs.getString("nombreSucursal");
          departamento[1] = rs.getString("nombreDepartamento");
          departamento[2] = rs.getString("direccion");
          //guardar a informacion 
          contenidoTablaDepartamentos.addRow(departamento);
          tblDepartamentos.setModel(contenidoTablaDepartamentos);

        }

      } catch (SQLException e) {
        System.out.println(e);
      }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  }

  public void borrarRegistrosTablaDepartamentos() {
    //getRowCount() devuelve la cantidad de filas que tiene la tabla 
    for (int i = 0; i < tblDepartamentos.getRowCount(); i++) {
      contenidoTablaDepartamentos.removeRow(i);
      // cada vez que se elimina una fila debe quedar menos filas por eliminar
      i = i - 1;

    }
    //video 3
    txtNumero1.setText("");
    txtNumero2.setText("");
    txtNumero3.setText("");
    cbDepartamento.setSelectedIndex(0);
    cbTipoCalle.setSelectedIndex(0);
    cbZona.setSelectedIndex(0);
  }

  //metodo de visibilidad privada
  private void listarEmpleados() {
    String filtroBusqueda = txtSearch.getText();
    //si no hay nada en el campo de busqueda se cargaran todos los empleados
    if (filtroBusqueda.isEmpty()) {
      String query = "SELECT nombreEmp, apellidos, tipoDocumento, documento, correo, nombreSucursal FROM empleado INNER JOIN sucursal ON empleado.FK_idSucursal = sucursal.idSucursal; ";//query de consulta
      //establecer conexion con la tabla y retorna respuesta
      try {
        //establecer conexion
        connection = conexion.getConnection();
        //creamos la consulta query para la base de datos
        st = connection.createStatement();
        //la ejecutamos
        rs = st.executeQuery(query);//esperamos que nos devuelva 4 empleados
        //asignar en un objeto los datos que devuelve de cada registro
        Object[] empleado = new Object[6];
        contenidoTablaEmpleados = (DefaultTableModel) tblEmpleados.getModel();
        // el resultado de la consulta del query  nos determinara la cantidad de empleados que existen
        //vamos a iterar sobre la cantidad de empleados que hay en la consulta mediante while
        while (rs.next()) { //cuando ya no hay next es que ya no hay empleados

          empleado[0] = rs.getString("nombreEmp");
          empleado[1] = rs.getString("apellidos");
          empleado[2] = rs.getString("tipoDocumento");
          empleado[3] = rs.getString("documento");
          empleado[4] = rs.getString("correo");
          empleado[5] = rs.getString("nombreSucursal");
          //actualizar el contenido de la tabla 
          //en la tabla creamos una nueva fila con los 5 atributos del objeto empleado
          contenidoTablaEmpleados.addRow(empleado);
          tblEmpleados.setModel(contenidoTablaEmpleados);

        }

      } catch (SQLException e) { //excepcion de MySQL con la letra e
        System.out.println("No se pudo cargar la informacion de los empleados");

      }

    } else {
      String query = "SELECT * FROM empleados WHERE nombreEmp like '%" + filtroBusqueda + "%' OR apellidos like '%" + filtroBusqueda + "%';";
      try {
        //establecer conexion
        connection = conexion.getConnection();
        //creamos la consulta query para la base de datos
        st = connection.createStatement();
        //la ejecutamos
        rs = st.executeQuery(query);//esperamos que nos devuelva 4 empleados
        //asignar en un objeto los datos que devuelve de cada registro
        Object[] empleado = new Object[6];
        contenidoTablaEmpleados = (DefaultTableModel) tblEmpleados.getModel();
        // el resultado de la consulta del query  nos determinara la cantidad de empleados que existen
        //vamos a iterar sobre la cantidad de empleados que hay en la consulta mediante while
        while (rs.next()) { //cuando ya no hay next es que ya no hay empleados
          empleado[0] = rs.getString("nombreEmp");
          empleado[1] = rs.getString("apellidos");
          empleado[2] = rs.getString("tipoDocumento");
          empleado[3] = rs.getString("documento");
          empleado[4] = rs.getString("correo");
          empleado[5] = rs.getString("nombreSucursal");
          //actualizr el contenido de la tabla 
          //en la tabla creamos una nueva fila con los 5 atributos del objeto empleado
          contenidoTablaEmpleados.addRow(empleado);
          tblEmpleados.setModel(contenidoTablaEmpleados);

        }

      } catch (SQLException e) { //excepcion de MySQL con la letra e
        System.out.println("No se pudo cargar la informacion de los empleados");

      }

    }

  }

  public void borrarRegistrosTabla() {
    //getRowCount() devuelve la cantidad de filas que tiene la tabla 
    for (int i = 0; i < tblEmpleados.getRowCount(); i++) {
      contenidoTablaEmpleados.removeRow(i);
      // cada vez que se elimina una fila debe quedar menos filas por eliminar
      i = i - 1;

    }

  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jTabbedPane1 = new javax.swing.JTabbedPane();
    jPanel4 = new javax.swing.JPanel();
    jPanel5 = new javax.swing.JPanel();
    jPanel6 = new javax.swing.JPanel();
    jLabel5 = new javax.swing.JLabel();
    cbDepartamento = new javax.swing.JComboBox<>();
    jLabel6 = new javax.swing.JLabel();
    cbZona = new javax.swing.JComboBox<>();
    jLabel7 = new javax.swing.JLabel();
    cbTipoCalle = new javax.swing.JComboBox<>();
    txtNumero1 = new javax.swing.JTextField();
    txtNumero2 = new javax.swing.JTextField();
    jLabel8 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    btnGuardar = new javax.swing.JButton();
    jLabel10 = new javax.swing.JLabel();
    txtNumero3 = new javax.swing.JTextField();
    jScrollPane2 = new javax.swing.JScrollPane();
    tblDepartamentos = new javax.swing.JTable();
    txtSearchSucursal = new javax.swing.JTextField();
    btnSearchSucursal = new javax.swing.JButton();
    jLabel11 = new javax.swing.JLabel();
    btnAddEmpleado = new javax.swing.JButton();
    jPanel1 = new javax.swing.JPanel();
    jPanel2 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    tblEmpleados = new javax.swing.JTable();
    btnAddUser = new javax.swing.JButton();
    btnShow = new javax.swing.JButton();
    btnEdit = new javax.swing.JButton();
    btnRemove = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    txtSearch = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    btnSearch = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jPanel5.setBackground(new java.awt.Color(204, 204, 204));

    jLabel5.setText("Departamento");

    cbDepartamento.setModel(enumDepartamentos);

    jLabel6.setText("Zona");

    cbZona.setModel(enumZona);

    jLabel7.setText("Calle");

    cbTipoCalle.setModel(enumTipoCalle);

    txtNumero1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        txtNumero1ActionPerformed(evt);
      }
    });

    txtNumero2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        txtNumero2ActionPerformed(evt);
      }
    });

    jLabel8.setText("-");

    jLabel9.setText("N°");

    btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/confirmIcon.png"))); // NOI18N
    btnGuardar.setText("Añadir");
    btnGuardar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnGuardarActionPerformed(evt);
      }
    });

    jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    jLabel10.setText("Direccion sucursal");

    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
      jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel6Layout.createSequentialGroup()
        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel6Layout.createSequentialGroup()
            .addGap(14, 14, 14)
            .addComponent(jLabel5)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel6)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jLabel7)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cbTipoCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel9)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel8)
            .addGap(18, 18, 18)
            .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel10))
          .addGroup(jPanel6Layout.createSequentialGroup()
            .addGap(248, 248, 248)
            .addComponent(btnGuardar)))
        .addContainerGap(39, Short.MAX_VALUE))
    );
    jPanel6Layout.setVerticalGroup(
      jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel6Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel10)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel5)
          .addComponent(cbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel6)
          .addComponent(cbZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel7)
          .addComponent(cbTipoCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(txtNumero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel9)
          .addComponent(txtNumero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel8)
          .addComponent(txtNumero3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addComponent(btnGuardar)
        .addGap(76, 76, 76))
    );

    tblDepartamentos.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {
        "Sucursal", "Departamento", "Direccion"
      }
    ));
    tblDepartamentos.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        tblDepartamentosMouseClicked(evt);
      }
    });
    jScrollPane2.setViewportView(tblDepartamentos);

    btnSearchSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/showUser.png"))); // NOI18N
    btnSearchSucursal.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnSearchSucursalActionPerformed(evt);
      }
    });

    jLabel11.setText("Departamento");

    btnAddEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
    btnAddEmpleado.setForeground(new java.awt.Color(153, 153, 153));
    btnAddEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/newUser.png"))); // NOI18N
    btnAddEmpleado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    btnAddEmpleado.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnAddEmpleadoActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
      jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel5Layout.createSequentialGroup()
        .addGap(28, 28, 28)
        .addComponent(jLabel11)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(txtSearchSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(btnSearchSucursal)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      .addGroup(jPanel5Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 8, Short.MAX_VALUE))
      .addGroup(jPanel5Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane2)
        .addGap(28, 28, 28)
        .addComponent(btnAddEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(36, 36, 36))
    );
    jPanel5Layout.setVerticalGroup(
      jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel5Layout.createSequentialGroup()
        .addGap(43, 43, 43)
        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(txtSearchSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel11))
          .addComponent(btnSearchSucursal))
        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel5Layout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(jPanel5Layout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAddEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(57, 57, 57)))
        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(49, 49, 49))
    );

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
      jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel4Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanel4Layout.setVerticalGroup(
      jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel4Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );

    jTabbedPane1.addTab("Sucursales", jPanel4);

    jPanel2.setBackground(new java.awt.Color(204, 204, 204));

    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logo.png"))); // NOI18N

    tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {
        "Nombre", "Apellidos", "Tipo documento", "Documento", "Correo", "Sucursal"
      }
    ) {
      boolean[] canEdit = new boolean [] {
        false, false, false, false, false, true
      };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    tblEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        tblEmpleadosMouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(tblEmpleados);

    btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/avatar.png"))); // NOI18N
    btnAddUser.setText("Añadir");
    btnAddUser.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnAddUserActionPerformed(evt);
      }
    });

    btnShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/showUser.png"))); // NOI18N
    btnShow.setText("Consultar");
    btnShow.setPreferredSize(new java.awt.Dimension(38, 38));
    btnShow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnShowActionPerformed(evt);
      }
    });

    btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/editUser.png"))); // NOI18N
    btnEdit.setText("Editar");
    btnEdit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnEditActionPerformed(evt);
      }
    });

    btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/deleteUser.png"))); // NOI18N
    btnRemove.setText("Eliminar");
    btnRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnRemoveActionPerformed(evt);
      }
    });

    jLabel2.setText("EMPLEADOS");

    jLabel3.setText("MisionTIC 2022");

    txtSearch.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        txtSearchActionPerformed(evt);
      }
    });

    jLabel4.setText("Nombre");

    btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/showUser.png"))); // NOI18N
    btnSearch.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnSearchActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel3Layout.createSequentialGroup()
        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(43, 43, 43)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))))
          .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel3)
              .addComponent(jLabel2))
            .addGap(108, 108, 108)
            .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(152, 152, 152)
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnSearch)))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel3Layout.createSequentialGroup()
        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1))
          .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(26, 26, 26)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnAddUser)
              .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)))))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel4)
          .addComponent(btnSearch))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(btnEdit)
          .addComponent(btnRemove))
        .addGap(25, 25, 25))
    );

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );

    jTabbedPane1.addTab("Empleados", jPanel1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jTabbedPane1)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
    // TODO add your handling code here:
    //llamar la instancia de AddUserForm
    AddUserForm addUserForm = new AddUserForm(this, true);
    //hacerlo visible
    addUserForm.setVisible(true);
    //actualizar la informacion de la tabla
    borrarRegistrosTabla();
    listarEmpleados();


  }//GEN-LAST:event_btnAddUserActionPerformed

  private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_btnRemoveActionPerformed

  private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_txtSearchActionPerformed

  private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
    // TODO add your handling code here:
    borrarRegistrosTabla();
    listarEmpleados();
  }//GEN-LAST:event_btnSearchActionPerformed

  private void tblEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadosMouseClicked
    int filaSeleccionada = tblEmpleados.getSelectedRow(); //captura la informacion de esa fila
    System.out.println("Fila seleccionada " + filaSeleccionada);
    String nombreEmp = tblEmpleados.getValueAt(filaSeleccionada, 0).toString();
    String apellidos = tblEmpleados.getValueAt(filaSeleccionada, 1).toString();
    String tipoDocumento = tblEmpleados.getValueAt(filaSeleccionada, 2).toString();
    String documento = tblEmpleados.getValueAt(filaSeleccionada, 3).toString();
    String correo = tblEmpleados.getValueAt(filaSeleccionada, 4).toString();
    String sucursal = tblEmpleados.getValueAt(filaSeleccionada, 5).toString();

    String queryIdEmpleado = "SELECT idEmp, nombreSucursal FROM empleado INNER JOIN sucursal WHERE sucursal.idSucursal = empleado.FK_idSucursal AND documento = '" + documento + "';";
    try {
      connection = conexion.getConnection();
      st = connection.createStatement();
      rs = st.executeQuery(queryIdEmpleado);
      while (rs.next()) {
        int idEmpleado = rs.getInt("idEmp");
        String nombreSucursal = rs.getString("nombreSucursal");
        ShowUserForm showUserForm = new ShowUserForm(this, true);
        showUserForm.recibirDatosDeUserMenu(idEmpleado, nombreSucursal, nombreEmp, apellidos, tipoDocumento, documento, correo);
        showUserForm.setVisible(true);
        this.borrarRegistrosTabla();
        this.listarEmpleados();
      }

    } catch (SQLException e) {
      System.out.println(e);
    }


  }//GEN-LAST:event_tblEmpleadosMouseClicked

  private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed

  }//GEN-LAST:event_btnShowActionPerformed

  private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_btnEditActionPerformed

  private void txtNumero2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumero2ActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_txtNumero2ActionPerformed

  private void txtNumero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumero1ActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_txtNumero1ActionPerformed

  private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

    String departamento = cbDepartamento.getSelectedItem().toString();
    String zona = cbZona.getSelectedItem().toString();
    String tipoCalle = cbTipoCalle.getSelectedItem().toString();
    String numero1 = txtNumero1.getText();
    String numero2 = txtNumero2.getText();
    String numero3 = txtNumero3.getText();

    if (departamento.isEmpty() || numero1.isEmpty() || numero2.isEmpty() || numero3.isEmpty() || zona.equals("SeleccionaUnaOpcion") || tipoCalle.equals("SeleccionaUnaOpcion") || departamento.equals("SeleccionaUnaOpcion")) {
      JOptionPane.showMessageDialog(this, "Faltan campos por diligenciar", "", JOptionPane.ERROR_MESSAGE);

    } else {
      String query = "INSERT INTO `direccion`(`zona`, `tipoCalle`, `numero1`, `numero2`, `numero3`, `nombreDepartamento`) VALUES ('" + zona + "','" + tipoCalle + "','" + numero1 + "','" + numero2 + "','" + numero3 + "','" + departamento + "')";
      System.out.println(query);
      try {
        connection = conexion.getConnection();
        st = connection.createStatement();
        st.executeUpdate(query);
        String queryIdDireccion = "SELECT idDireccion FROM `direccion` WHERE nombreDepartamento = '" + departamento + "' AND zona = '" + zona + "' AND tipoCalle = '" + tipoCalle + "' AND numero1 = '" + numero1 + "' AND numero2 = '" + numero2 + "' AND numero3 = '" + numero3 + "' ";
        System.out.println(queryIdDireccion);
        try {
          rs = st.executeQuery(queryIdDireccion);
          while (rs.next()) { //mientras encuentre resultados en la base de datos 
            int idDireccion = rs.getInt("idDireccion");
            SucursalForm sucursalForm = new SucursalForm(this, true);
            sucursalForm.setVisible(true);
            sucursalForm.recibeIdDireccion(idDireccion);
            JOptionPane.showMessageDialog(this, "La sucursal ha sido creada correctamente");
            borrarRegistrosTablaDepartamentos();
            listarDepartamentos();
          }

        } catch (SQLException e) {
          System.out.println(e);
        }
      } catch (SQLException e) {
        System.out.println();
        JOptionPane.showMessageDialog(this, "No fue posible crear la direccion ", "", JOptionPane.ERROR_MESSAGE);
      }

    }

  }//GEN-LAST:event_btnGuardarActionPerformed


  private void btnSearchSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchSucursalActionPerformed
    borrarRegistrosTablaDepartamentos();
    listarDepartamentos();

    /*
    String filtro = txtSearchSucursal.getText();
    String filtroSucursales = "SELECT DISTINCT nombreSucursal, nombreDepartamento, idDireccion, FK_idDireccion FROM `sucursal` INNER JOIN `direccion` ON idDireccion = FK_idDireccion AND nombreSucursal LIKE '%"+filtro+"%' OR nombreDepartamento LIKE '%"+filtro+"%';";
    System.out.println(filtroSucursales);
    try {  
      connection = conexion.getConnection();
      st = connection.createStatement();
      rs = st.executeQuery(filtroSucursales);
      Object[] departamento = new Object[2];//2 es de dos campos
      contenidoTablaDepartamentos = (DefaultTableModel) tblDepartamentos.getModel();
      while (rs.next()) {
        departamento[0] = rs.getString("nombreSucursal");
        departamento[1] = rs.getString("nombreDepartamento");
        //guardar a informacion 
        contenidoTablaDepartamentos.addRow(departamento);
        tblDepartamentos.setModel(contenidoTablaDepartamentos);

      }
      
    } catch (SQLException e) {
      System.out.println(e);
    }*/

  }//GEN-LAST:event_btnSearchSucursalActionPerformed

  private void tblDepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDepartamentosMouseClicked
    int filaSeleccionada = tblDepartamentos.getSelectedRow();
    if (filaSeleccionada > -1) {
      String sucursal = tblDepartamentos.getValueAt(filaSeleccionada, 0).toString();
      String departamento = tblEmpleados.getValueAt(filaSeleccionada, 1).toString();
      String queryDireccion = "SELECT `idDireccion`, `zona`, `tipoCalle`, `numero1`, `numero2`, `numero3` FROM `direccion` INNER JOIN sucursal WHERE direccion.idDireccion = sucursal.FK_idDireccion AND nombreSucursal = '" + sucursal + "';";
      try {
        connection = conexion.getConnection();
        st = connection.createStatement();
        rs = st.executeQuery(queryDireccion);
        while (rs.next()) {
          int idDireccion = rs.getInt("idDireccion");
          String zona = rs.getString("zona");
          String tipoCalle = rs.getString("tipoCalle");
          String numero1 = rs.getString("numero1");
          String numero2 = rs.getString("numero2");
          String numero3 = rs.getString("numero3");
          GestionarSucursalesForm gestionarSucursales = new GestionarSucursalesForm(this, true);
          gestionarSucursales.recibeDatosDireccion(idDireccion, departamento, sucursal, zona, tipoCalle, numero1, numero2, numero3);
          gestionarSucursales.setVisible(true);
          

        }
        borrarRegistrosTablaDepartamentos();
        listarDepartamentos();
      } catch (SQLException e) {
        System.out.println(e);
      }

    }


  }//GEN-LAST:event_tblDepartamentosMouseClicked

  private void btnAddEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmpleadoActionPerformed
    // TODO add your handling code here:
    int filaSeleccionada = tblDepartamentos.getSelectedRow(); //saber que fila seleccione
    if (filaSeleccionada == -1) {
      JOptionPane.showMessageDialog(this, "Debes seleccionar una sucursal", "Gestion sucursales", JOptionPane.ERROR_MESSAGE);

    } else {

      String sucursal = tblDepartamentos.getValueAt(filaSeleccionada, 0).toString();
      String queryIdSucursal = "SELECT idSucursal FROM `sucursal` WHERE nombreSucursal = '" + sucursal + "';";
      System.out.println(queryIdSucursal);
      try {
        connection = conexion.getConnection();
        st = connection.createStatement();
        rs = st.executeQuery(queryIdSucursal);
        while (rs.next()) {
          int idSucursal = rs.getInt("idSucursal");
          System.out.println("Envia : " + idSucursal);
          EmpleadosLista empleadosLista = new EmpleadosLista(this, true);
          empleadosLista.setVisible(true);
          empleadosLista.recibeIdSucursal(idSucursal);

        }
      } catch (SQLException e) {
        System.out.println(e);
      }

    }

  }//GEN-LAST:event_btnAddEmpleadoActionPerformed

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
      java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new UserMenu().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnAddEmpleado;
  private javax.swing.JButton btnAddUser;
  private javax.swing.JButton btnEdit;
  private javax.swing.JButton btnGuardar;
  private javax.swing.JButton btnRemove;
  private javax.swing.JButton btnSearch;
  private javax.swing.JButton btnSearchSucursal;
  private javax.swing.JButton btnShow;
  private javax.swing.JComboBox<String> cbDepartamento;
  private javax.swing.JComboBox<String> cbTipoCalle;
  private javax.swing.JComboBox<String> cbZona;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTable tblDepartamentos;
  private javax.swing.JTable tblEmpleados;
  private javax.swing.JTextField txtNumero1;
  private javax.swing.JTextField txtNumero2;
  private javax.swing.JTextField txtNumero3;
  private javax.swing.JTextField txtSearch;
  private javax.swing.JTextField txtSearchSucursal;
  // End of variables declaration//GEN-END:variables
}
