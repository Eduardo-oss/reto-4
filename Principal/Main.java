package Principal;

import Vistas.*;
import Modelo.*;

/**
 *
 * @author Eduardo Chana
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // 2. Crear instancia de la clase conexion
    Conexion conexion = new Conexion();
    conexion.getConnection(); // getConnection(), esperamos tener si hubo conexion exitosa
// 1. Crear instancia del JFrame login 
    Login login = new Login();
    //hacerlas visibles
    login.setVisible(true);

  }

}
