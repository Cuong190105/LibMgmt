package org.example.libmgmt.DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class AccountDB {
    private static Connection db;
    private static boolean isTesting;

    public static Connection getConnection() throws Exception {
        if (db == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbName = (isTesting) ? "testdb" : "accountdb";
            db = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root", "");
        }
        return db;
    }

    public static void setTesting() {
        isTesting = true;
    }
}
