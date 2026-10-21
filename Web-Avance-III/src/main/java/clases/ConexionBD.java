
package clases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
        
public class ConexionBD {
    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String CLAVE = "123456789";
    private static final String URL = "jdbc:mysql://localhost:3306/virtualstore";
    
    private Connection cnn;
    
    //constructor
    public ConexionBD() {
        cnn = null;
    }
    
    //metodo conectar
    public Connection Conectar() {
        try {
            Class.forName(DRIVER);
            cnn = (Connection)DriverManager.getConnection(URL, USER, CLAVE);
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return cnn;
    }
    
    //metodo Conexion
    public static Connection getConexion() {
    try {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USER, CLAVE);
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        return null;
        }
    }
 
    //metodo desconectar
    public void Desconectar() {
        try {
            cnn.close();
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }    
}