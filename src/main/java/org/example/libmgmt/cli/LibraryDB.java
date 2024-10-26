package org.example.libmgmt.cli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LibraryDB {
    private static Connection db;

    public static Connection getConnection() throws Exception {
        if (db == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            db = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb", "root", "");
        }
        return db;
    }
}
