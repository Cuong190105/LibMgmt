package org.example.libmgmt.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {
  private static BorrowDAO instance;
  private BorrowDAO() {
  }

  public static BorrowDAO getInstance() {
    if (instance == null) {
      instance = new BorrowDAO();
    }
    return instance;
  }

  public List<Borrow> borrowHistory(int UID) {
    List<Borrow> borrowList = new ArrayList<>();

    try {
      Connection db = LibraryDB.getConnection();
      String sql = "SELECT * FROM borrow WHERE UID=? ORDER BY borrowingDate DESC";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, UID);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Borrow br = new Borrow();
        br.setUID(UID);
        br.setDocID(rs.getInt("DocID"));
        br.setBorrowingDate(rs.getDate("BorrowingDate"));
        br.setReturnDate(rs.getDate("ReturnDate"));
        borrowList.add(br);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return borrowList;
  }

  public List<Borrow> allBorrow() {
    List<Borrow> borrowList = new ArrayList<>();

    try {
      Connection db = LibraryDB.getConnection();
      String sql = "SELECT * FROM borrow ORDER BY borrowingDate DESC";
      PreparedStatement ps = db.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Borrow br = new Borrow();
        br.setUID(rs.getInt("UID"));
        br.setDocID(rs.getInt("DocID"));
        br.setBorrowingDate(rs.getDate("BorrowingDate"));
        br.setReturnDate(rs.getDate("ReturnDate"));
        borrowList.add(br);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return borrowList;
  }
}
