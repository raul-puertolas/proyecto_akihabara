package AkihabaraDB.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Esta clase se encarga de conectar con la base de datos de Akihabara
public class DatabaseConnection {
    // Aquí se pone la URL para conectar con la base de datos local, en este caso es "akihabara_db"
    private static final String URL = "jdbc:mysql://localhost:3306/akihabara_db";
    
    // Nombre de usuario para acceder a la base de datos (en este caso es el típico "root")
    private static final String USUARIO = "akihabara";
    
    // Contraseña para acceder a la base de datos (sí, es la de campusfp)
    private static final String CONTRASENIA = "campusfp";

    // Este método devuelve una conexión a la base de datos usando los datos de arriba
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
    }
}
