package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Update these values with your actual SQL Server details!
    private static final String  URL = "jdbc:sqlserver://BADRELDEEN:1433;"
           + "databaseName=Users;"
           + "encrypt=false;"
           + "trustServerCertificate=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa_2006";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
