package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // URL de conexión a la base de datos MySQL (nombre de la BD: tienda_mascota)
    private static final String URL = "jdbc:mysql://localhost:3306/tienda_mascota?useSSL=false&serverTimezone=UTC";

    // Usuario de la base de datos
    private static final String USUARIO = "root";

    // Contraseña del usuario de la base de datos
    private static final String CONTRASENA = "2556229";

    // Método estático que devuelve una conexión a la base de datos
    public static Connection getConnection() {
        Connection conexion = null;
        try {
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión a la base de datos
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            // Error si no se encuentra el driver
            System.out.println("Error: Driver JDBC no encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            // Error si no se logra conectar a la BD
            System.out.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
        }
        return conexion; // Devuelve el objeto Connection
    }
}
