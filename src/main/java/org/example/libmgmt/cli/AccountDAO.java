package org.example.libmgmt.cli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public Account getAccountFromUsername(String username) {
        Account acc = null;
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "SELECT * FROM accountdb.account WHERE Username = ?";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                acc = new Account();
                acc.setUsername(rs.getString("Username"));
                acc.setPassword(rs.getString("HashedPassword"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return acc;
    }

    public void addAccount(Account account) {
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "INSERT INTO accountdb.account (Username, HashedPassword) VALUES (?, ?)";
            PreparedStatement ps = db.prepareStatement(sql); // query may generate A_I key
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(Account account) {
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "UPDATE accountdb.account SET Password = ? WHERE Username = ?";
            PreparedStatement ps = db.prepareStatement(sql); // query may generate A_I key
            ps.setString(1, account.getPassword());
            ps.setString(2, account.getUsername());

            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
