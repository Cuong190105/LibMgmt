package org.example.libmgmt.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO implements Extractor<Account> {
  private static AccountDAO instance;

  private AccountDAO() {
  }


  public static AccountDAO getInstance() {
    if (instance == null) {
      instance = new AccountDAO();
    }
    return instance;
  }

  @Override
  public Account extract(ResultSet rs) throws SQLException {
    Account acc = new Account();
    acc.setUsername(rs.getString("Username"));
    acc.setPassword(rs.getString("HashedPassword"));
    acc.setUID(rs.getInt("UID"));
    return acc;
  }

  public Account getAccountFromUsername(String username) throws DatabaseConnectionException {
    Account acc = null;
    try {
      Connection db = AccountDB.getConnection();
      String sql = "SELECT * FROM account WHERE Username = ?";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        acc = extract(rs);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new DatabaseConnectionException("Couldn't connect to the Account database");
    }
    return acc;
  }

  public void addAccount(Account account) {
    try {
      Connection db = AccountDB.getConnection();
      String sql = "INSERT INTO account (UID, Username, HashedPassword) VALUES (?, ?, ?)";
      PreparedStatement ps = db.prepareStatement(sql); // query may generate A_I key
      ps.setInt(1, account.getUID());
      ps.setString(2, account.getUsername());
      ps.setString(3, account.getPassword());

      ps.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void changePassword(Account account) {
    try {
      Connection db = AccountDB.getConnection();
      String sql = "UPDATE account SET hashedPassword = ? WHERE Username = ?";
      PreparedStatement ps = db.prepareStatement(sql); // query may generate A_I key
      ps.setString(1, account.getPassword());
      ps.setString(2, account.getUsername());

      ps.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void deleteAccount(int UID) {
    try {
      Connection db = AccountDB.getConnection();
      String sql = "DELETE FROM account where UID = ?";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, UID);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
