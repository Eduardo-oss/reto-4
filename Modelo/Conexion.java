package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

  Connection connection;
  String driver = "com.mysql.cj.jdbc.Driver"; //llamar a las clases que tiene el proyecto  conectar con la base de datos 
  String cadenaConexion = "jdbc:mysql://localhost:3306/reto1_g55_db"; //cadena de conexion con la db
  String usuario = "root"; //usuario que se cra por defecto: usuario root
  String password = "";//no hay contraseña cuando instala XAMPP

  public Conexion() {
    //busqueda de clase por el nombre dentro de un bloque try catch para el control de excepciones
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(cadenaConexion, usuario, password);
      if (connection != null) {
        System.out.println("Conexion Exitosa con BD");

      }

    } catch (ClassNotFoundException | SQLException e) {
      System.out.println("No se pudo establecer conexion");

    }

  }

  public Connection getConnection() {
    //retornar el valor de conexion de la clase Connection
    return connection; //hubo conexion exitosa o no
  }

}
