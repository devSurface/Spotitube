package han.dea.spotitube.dylan.datasource;

import han.dea.spotitube.dylan.datasource.util.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection con;


    public Connection getConnection() {
        DatabaseProperties databaseProperties = new DatabaseProperties();
            try {
                con = DriverManager.getConnection(databaseProperties.getProperties());
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
                System.out.println(ex);
            }

        return con;
    }
}