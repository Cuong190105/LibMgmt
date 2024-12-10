package org.example.libmgmt.DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO implements Extractor<Borrow> {
  private static BorrowDAO instance;

  private BorrowDAO() {
  }

  public static BorrowDAO getInstance() {
    if (instance == null) {
      instance = new BorrowDAO();
    }
    return instance;
  }

  @Override
  public Borrow extract(ResultSet rs) throws SQLException {
    Borrow br = new Borrow();
    br.setUID(rs.getInt("UID"));
    br.setDocID(rs.getInt("docID"));
    br.setBorrowingDate(rs.getDate("borrowingDate"));
    br.setDueDate(rs.getDate("dueDate"));
    br.setReturnDate(rs.getDate("returnDate"));
    return br;
  }

  public List<Borrow> allBorrow() {
    List<Borrow> borrowList = new ArrayList<>();

    try {
      Connection db = LibraryDB.getConnection();
      String sql = "SELECT * FROM borrow ORDER BY borrowingDate DESC";
      PreparedStatement ps = db.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Borrow br = extract(rs);
        borrowList.add(br);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return borrowList;
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
        Borrow br = extract(rs);
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
      sql = "UPDATE document SET quantity = "
          + "(SELECT quantity FROM document WHERE docID = ?) - 1 WHERE DocID = ?";
      ps = db.prepareStatement(sql);
      ps.setInt(1, br.getDocID());
      ps.setInt(2, br.getDocID());
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void deleteBorrow(String column, int value) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "DELETE FROM borrow WHERE " + column + " = ?";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, value);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int returnDocument(int userId, int docId) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "UPDATE borrow SET ReturnDate = ? WHERE UID = ? AND DocID = ? AND returnDate IS NULL";
      PreparedStatement ps = db.prepareStatement(sql);
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDateTime now = LocalDateTime.now();
      String date = dtf.format(now);
      ps.setDate(1, Date.valueOf(date));
      ps.setInt(2, userId);
      ps.setInt(3, docId);
      int row = ps.executeUpdate();
      if (row != 0) {
        sql = "UPDATE document SET quantity = "
            + "(SELECT quantity FROM document WHERE docID = ?) + 1 WHERE DocID = ?";
        ps = db.prepareStatement(sql);
        ps.setInt(1, docId);
        ps.setInt(2, docId);
        ps.executeUpdate();
      }
      return row;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }
}
