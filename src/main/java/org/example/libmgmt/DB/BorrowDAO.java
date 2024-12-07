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
        br.setDocID(rs.getInt("docID"));
        br.setBorrowingDate(rs.getDate("borrowingDate"));
        br.setDueDate(rs.getDate("dueDate"));
        br.setReturnDate(rs.getDate("returnDate"));

        borrowList.add(br);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return borrowList;
  }

  public void addBorrow(Borrow br) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "INSERT INTO borrow (UID, docID, borrowingDate, dueDate, returnDate) VALUES (?, ?, ?, ?, ?)";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, br.getUID());
      ps.setInt(2, br.getDocID());
      ps.setDate(3, br.getBorrowingDate());
      ps.setDate(4, br.getDueDate());
      ps.setDate(5, br.getReturnDate());

      ps.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void deleteBorrow(String column, int value) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "DELETE FROM borrow where" + column + " = ?";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, value);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
