package demo;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDatabase {
        public static void main(String[] args) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            System.out.println("[+] Successfully connected to SQL Server!");
            System.out.println("Database: " + conn.getCatalog());
        } catch (SQLException e) {
            System.err.println("[-] Connection failed!");
            e.printStackTrace();
        }
    }
}
