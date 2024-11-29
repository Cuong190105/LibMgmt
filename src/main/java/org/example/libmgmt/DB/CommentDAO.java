package org.example.libmgmt.DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private static CommentDAO instance;
    private CommentDAO() {};

    public static CommentDAO getInstance() {
        if (instance == null) {
            instance = new CommentDAO();
        }
        return instance;
    }

    public List<Comment> comments(int docID) {
        List<Comment> commentList = new ArrayList<>();

        try {
            Connection db = LibraryDB.getConnection();
            String sql = "SELECT * FROM comment WHERE docID=? ORDER BY date DESC";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setInt(1, docID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comment cmt = new Comment();
                cmt.setUID(rs.getInt("UID"));
                cmt.setDocID(docID);
                cmt.setComment(rs.getString("content"));
                cmt.setRating(rs.getInt("rating"));
                cmt.setDate(rs.getDate("Date"));

                commentList.add(cmt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentList;
    }

    public void addComment(Comment cmt) {
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "INSERT INTO comment (UID, docID, content, rating, date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = db.prepareStatement(sql); // query may generate A_I key
            ps.setInt(1, cmt.getUID());
            ps.setInt(2, cmt.getDocID());
            ps.setString(3, cmt.getComment());
            ps.setInt(4, cmt.getRating());
            ps.setDate(5, Date.valueOf(cmt.getDate()));

            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBorrow(String column, int value) {
        try {
            Connection db = LibraryDB.getConnection();
            String sql = "DELETE FROM comment where" + column + " = ?";
            PreparedStatement ps = db.prepareStatement(sql);
            ps.setInt(1,value);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
