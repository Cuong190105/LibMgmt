package org.example.libmgmt;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
    public static Connection connection;

    public static Connection getConnection() throws Exception{
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryDB", "root", "");
        }
        return connection;
    }
}
