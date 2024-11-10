package org.example.libmgmt.cli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class UserDAO {
    private static UserDAO instance;
    private UserDAO(){};

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public User getUserFromUsername(String username) {
        User user = null;
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "SELECT * FROM librarydb.user WHERE Username = ?";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUID(rs.getInt("UID"));
                user.setUsername(rs.getString("Username"));
                //user.setPassword(rs.getString("password"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public int addUser(String username) {
        int userUID = 0;

        try {
            Connection db = LibraryDB.getConnection();
            String sql = "INSERT INTO librarydb.user (Username) VALUES (?)";
            PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // query may generate A_I key
            ps.setString(1, username);
            //ps.setString(2, password);
            int rowAffected = ps.executeUpdate();

            if (rowAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    // Check the column count and names // debug
//                    ResultSetMetaData metaData = rs.getMetaData();
//                    int columnCount = metaData.getColumnCount();
//                    System.out.println("Column count: " + columnCount);
//                    for (int i = 1; i <= columnCount; i++) {
//                        System.out.println("Column " + i + ": " + metaData.getColumnName(i));
//                    }
                    // Now retrieve the UID
                    userUID = rs.getInt(1);
                }
//                if (rs.next()) {
//                    userUID = rs.getInt(1);
//                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
//        System.out.println(userUID);
        return userUID;
    }
}
