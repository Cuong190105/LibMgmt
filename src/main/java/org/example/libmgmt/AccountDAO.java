package org.example.libmgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {
    private static AccountDAO instance;
    private AccountDAO(){};

    public static AccountDAO getInstance() {
        if (instance == null) {
            instance = new AccountDAO();
        }
        return instance;
    }

    public Account extractAccount(ResultSet rs) throws SQLException {
        Account acc = new Account();
        acc.setUsername(rs.getString("Username"));
        acc.setPassword(rs.getString("HashedPassword"));
        acc.setUID(rs.getInt("UID"));
        return acc;
    }

    public Account getAccountFromUsername(String username) {
        Account acc = null;
        try {
            Connection db = AccountDB.getConnection();
            String sql = "SELECT * FROM account WHERE Username = ?";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                acc = extractAccount(rs);
            }

        } catch(Exception e) {
            e.printStackTrace();
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

        } catch(Exception e) {
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

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
