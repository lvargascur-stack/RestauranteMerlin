package dao.leo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para manejar la conexión a la base de datos SQL Server.
 * Utiliza PreparedStatement para evitar SQL injection.
 * @author Vargas Curisinche, Leonel Wilverth
 * @version 1.0
 */
public class ConexionBD {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RestauranteMerlin;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "tu_contraseña";

    /**
     * Establece la conexión con la base de datos.
     * @return Objeto Connection para interactuar con la BD
     * @throws SQLException Si ocurre un error de conexión
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }
}
