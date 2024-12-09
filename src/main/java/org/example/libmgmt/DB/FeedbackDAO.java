package org.example.libmgmt.DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO implements Extractor<Feedback> {
  private static FeedbackDAO instance;

  private FeedbackDAO() {
  }

  public static FeedbackDAO getInstance() {
    if (instance == null) {
      instance = new FeedbackDAO();
    }
    return instance;
  }

  @Override
  public Feedback extract(ResultSet rs) throws SQLException {
    Feedback fb = new Feedback();
    fb.setNumber(rs.getInt("number"));
    fb.setDate(rs.getDate("date"));
    fb.setSender(rs.getString("sender"));
    fb.setContent(rs.getString("content"));
    fb.setImportant(rs.getBoolean("important"));
    return fb;
  }

  public List<Feedback> allFeedback() {
    List<Feedback> feedbackList = new ArrayList<>();

    try {
      Connection db = LibraryDB.getConnection();
      String sql = "SELECT * FROM feedback ORDER BY date DESC";
      PreparedStatement ps = db.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Feedback fb = extract(rs);
        feedbackList.add(fb);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return feedbackList;
  }

  public void addFeedback(Feedback fb) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "INSERT INTO feedback (number, date, sender, content, important) VALUES (?, ?, ?, ?, ?)";
      PreparedStatement ps = db.prepareStatement(sql); // query may generate A_I key
      ps.setInt(1, fb.getNumber());
      ps.setDate(2, fb.getDate());
      ps.setString(3, fb.getSender());
      ps.setString(4, fb.getContent());
      ps.setBoolean(5, fb.isImportant());
      ps.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void deleteFeedback(String column, int value) {
    try {
      Connection db = LibraryDB.getConnection();
      String sql = "DELETE FROM feedback WHERE" + column + " = ?";
      PreparedStatement ps = db.prepareStatement(sql);
      ps.setInt(1, value);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
