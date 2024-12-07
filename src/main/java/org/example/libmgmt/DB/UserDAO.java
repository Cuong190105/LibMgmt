
package org.example.libmgmt.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements Extractor<User> {
  private static UserDAO instance;

  private UserDAO() {
  }

  /**
   * Singleton.
   */
  public static UserDAO getInstance() {
    if (instance == null) {
      instance = new UserDAO();
    }
    return instance;
  }

  @Override
  public User extract(ResultSet rs) throws SQLException {
    User user = new User();
    user.setUid(rs.getInt("UID"));
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
        user = extract(rs);
      }

    } catch (Exception e) {
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
        user = extract(rs);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return user;
  }

  public int addUser(User user) {
    int userUID = 0;

    try {
      Connection db = LibraryDB.getConnection();
      String sql = "INSERT INTO user (name, sex, DOB, address, phoneNumber, email, ssn, isLibrarian, userName) VALUES (?,?,?,?,?,?,?,?,?)";
      PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // query may generate A_I key
      ps.setString(1, user.getName());
      ps.setString(2, user.getSex());
      ps.setDate(3, user.getDob());
      ps.setString(4, user.getAddress());
      ps.setString(5, user.getPhone());
      ps.setString(6, user.getEmail());
      ps.setString(7, user.getSSN());
      ps.setBoolean(8, user.isLibrarian());
      int rowAffected = ps.executeUpdate();

      if (rowAffected > 0) {
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
          userUID = rs.getInt(1);
          user.setUid(userUID);
        }

      }
    } catch (Exception e) {
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
      ps.setInt(1, UID);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateUser(User updated) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "UPDATE user SET name = ?, sex = ?, dob = ?, "
              + "address = ?, phoneNumber = ?, email = ?, ssn = ?, isLibrarian = ? WHERE UID = ?";

      PreparedStatement ps = db.prepareStatement(sql);
      ps.setString(1, updated.getName());
      ps.setString(2, updated.getSex());
      ps.setDate(3, updated.getDob());
      ps.setString(4, updated.getAddress());
      ps.setString(5, updated.getPhone());
      ps.setString(6, updated.getEmail());
      ps.setString(7, updated.getSSN());
      ps.setBoolean(8, updated.isLibrarian());

      ps.setInt(9, updated.getUid());

      int rowAffected = ps.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<User> searchUser(String text, int filter, int sort) throws DatabaseConnectionException{
    List<User> user = new ArrayList<>();
    try {
      Connection db = LibraryDB.getConnection();

      String[] sortBy = {"UID", "name"};
      String[] direction = {"ASC", "DESC"};
      String[] bool = {"FALSE", "TRUE"};
      String sortCmd = "ORDER BY " + sortBy[sort & 3] + " " + direction[(sort & 4) / 4];

      String sql = "SELECT * FROM user WHERE ";
      if (filter != 0) {
        sql += "isLibrarian IS " + bool[filter - 1] + " AND ";
      }
      sql += "(UID = ? OR name LIKE ?) " + sortCmd;
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setString(1, text);
      ps.setString(2, "%" + text + "%");
      ResultSet rs = ps.executeQuery();
      System.out.println((ps.toString()));
      while (rs.next()) {
        user.add(extract(rs));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return user;
  }
}