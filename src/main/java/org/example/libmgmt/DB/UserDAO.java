package org.example.libmgmt.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;
    private UserDAO(){};

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUID(rs.getInt("UID"));
        user.setName(rs.getString("name"));
        user.setSex(rs.getString("sex"));
        user.setDob(rs.getDate("dob"));
        user.setAddress(rs.getString("address"));
        user.setPhone(rs.getString("phoneNumber"));
        user.setEmail(rs.getString("email"));
        user.setSSN(rs.getString("ssn"));
        user.setLibrarian(rs.getBoolean("isLibrarian"));
        user.setAvatar(rs.getBlob("avatar"));
        return user;
    }

    public User getUserFromUID(int UID) {
        User user = null;
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "SELECT * FROM user WHERE UID = ?";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setInt(1, UID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
                //user.setPassword(rs.getString("password"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserFromUsername(String username) {
        User user = null;
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "SELECT * FROM user WHERE username = ?";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
                //user.setPassword(rs.getString("password"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public int addUser(User user) {
        int userUID = 0;

        try {
            Connection db = LibraryDB.getConnection();
            String sql = "INSERT INTO user (name, sex, DOB, address, phoneNumber, email, socialSecurityNumber, userType, userName) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // query may generate A_I key
            ps.setString(1, user.getName());
            ps.setString(2, user.getSex());
            ps.setDate(3, user.getDob());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getSSN());
            ps.setBoolean(8, user.isLibrarian());
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

    public void deleteUser(int UID) {
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "DELETE FROM user where UID = ?";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setInt(1,UID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User updated) {
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "UPDATE user SET name = ?, sex = ?, dob = ?, "
                    + "address = ?, phoneNumber = ?, email = ?, socialSecurityNumber = ?, userType = ?, userName = ? WHERE UID = ?";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, updated.getName());
            ps.setString(2, updated.getSex());
            ps.setDate(3, updated.getDob());
            ps.setString(4, updated.getAddress());
            ps.setString(5, updated.getPhone());
            ps.setString(6, updated.getEmail());
            ps.setString(7, updated.getSSN());
            ps.setBoolean(8, updated.isLibrarian());

            ps.setInt(10, updated.getUID()); // Assuming Document has a method getId() to get the document ID

            int rowAffected = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> searchUser(String text) {
        return null;
    }
}
